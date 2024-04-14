package org.kosa.mini.board;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.kosa.mini.code.CodeService;
import org.kosa.mini.entity.BoardFileVO;
import org.kosa.mini.entity.BoardImageFileVO;
import org.kosa.mini.entity.BoardVO;
import org.kosa.mini.entity.MemberVO;
import org.kosa.mini.page.PageRequestVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

	//xml 또는 어노터이션 처리하면 스프링 
	//어노터이션 처리하면 스프링 부트   
	private final BoardService boardService;
	private final CodeService codeService;
	private final ServletContext application;

	@RequestMapping("list")
	public String list(@Valid PageRequestVO pageRequestVO, BindingResult bindingResult, Model model) throws ServletException, IOException {
		log.info("목록");
		
		log.info(pageRequestVO.toString());

        if(bindingResult.hasErrors()){
        	pageRequestVO = PageRequestVO.builder().build();
        }
        
		//2. jsp출력할 값 설정
		model.addAttribute("pageResponseVO", boardService.getList(pageRequestVO));
		//model.addAttribute("sizes", new int[] {10, 20, 50, 100});
		model.addAttribute("sizes", codeService.getList());
//		model.addAttribute("sizes", "10,20,50,100");
		
		return "board/list";
	}
	
	@RequestMapping("view")
	public String view(BoardVO board, Model model) throws ServletException, IOException {
		log.info("상세보기");
		
		model.addAttribute("board", boardService.view(board));
		
		return "board/view";
	}

	@RequestMapping("jsonBoardInfo")
	@ResponseBody
	public Map<String, Object> jsonBoardInfo(@RequestBody BoardVO board) throws ServletException, IOException {
		log.info("json 상세보기 -> {}", board);
		//1. 처리
		BoardVO resultVO = boardService.view(board);
		
		Map<String, Object> map = new HashMap<>();
		if (resultVO != null) { //성공
			map.put("status", 0);
			map.put("jsonBoard", resultVO);
		} else {
			map.put("status", -99);
			map.put("statusMessage", "게시물 정보 존재하지 않습니다");
		}
		
		return map;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestBody BoardVO board) throws ServletException, IOException {
		log.info("삭제 -> {}", board);
		//1. 처리
		int updated = boardService.delete(board);
		
		Map<String, Object> map = new HashMap<>();
		if (updated == 1) { //성공
			map.put("status", 0);
		} else {
			map.put("status", -99);
			map.put("statusMessage", "게시물 정보 삭제 실패하였습니다");
		}
		
		return map;
	}
	
	@RequestMapping("updateForm")
	public Object updateForm(BoardVO board, Model model) throws ServletException, IOException {
		System.out.println("수정화면");
		
		//2. jsp출력할 값 설정
		model.addAttribute("board", boardService.updateForm(board));
		
		return "board/updateForm"; 
	}

	@RequestMapping("update")
	@ResponseBody
	public Map<String, Object>  update(@RequestBody BoardVO board) throws ServletException, IOException {
		log.info("수정 board => {}", board);
		
		//1. 처리
		int updated = boardService.update(board);
		
		Map<String, Object> map = new HashMap<>();
		if (updated == 1) { //성공
			map.put("status", 0);
		} else {
			map.put("status", -99);
			map.put("statusMessage", "게시물 정보 수정 실패하였습니다");
		}
		
		return map;
	}
	
	@RequestMapping("insertForm")
	public Object insertForm(Model model) throws ServletException, IOException {
		System.out.println("등록화면");
		
		//게시물의 토큰을 생성하여 Model에 저장 한다 
		model.addAttribute("board_token", boardService.getBoardToken());
		
		//2. jsp출력할 값 설정
		return "board/insertForm";
	}
	
	// 만약 게시글 작성 중 서버에 이미지를 업로드한 후 작성중인 게시글을 저장하지 않고 
	// 취소 하고 나가는 경우 업로드한 이미지가 존재하게 된다 
	// 이런 경우 발생하면 서버에 의미 없는 업로드된 이미지가 존재하게 된다  
	// token 값은 시작시 발급 받고, 상태는 임시 작업 상태(0)임 
	// 게시물 등록 작업이 완료되면 token의 상태를 작업 완료(1)로 설정해야한다  
	// 만약 마지막 작업이 완료 되지 않은 경우 스토리지 서버에 저장된 파일을 
	// 삭제 할 수 있게 구현 해야 한다(현재는 사용하지 않음)
	@RequestMapping("insert")
	@ResponseBody
	public Object insert(BoardVO boardVO, Authentication authentication) throws ServletException, IOException {
		MemberVO loginVO = (MemberVO)authentication.getPrincipal();
		log.info("등록 BoardVO = {}\n loginVO = {}", boardVO, loginVO);
		
		Map<String, Object> map = new HashMap<>();
		map.put("status", -99);
		map.put("statusMessage", "게시물 등록에 실패하였습니다");
		
		//로그인한 사용자를 게시물 작성자로 설정한다 
		boardVO.setMember_id(loginVO.getMember_id());
		int updated = boardService.insert(boardVO);
		if (updated == 1) { //성공
			map.put("status", 0);
		}
		
		return map;
	}
	
	@PostMapping("boardImageUpload")
	@ResponseBody
	public Object boardImageUpload(BoardImageFileVO boardImageFileVO) throws ServletException, IOException {
		
		// ckeditor는 이미지 업로드 후 이미지 표시하기 위해 uploaded 와 url을 json 형식으로 받아야 함
		// ckeditor 에서 파일을 보낼 때 upload : [파일] 형식으로 해서 넘어옴, upload라는 키 이용하여 파일을 저장 한다
		MultipartFile file = boardImageFileVO.getUpload();
		String board_token = boardImageFileVO.getBoard_token();
		
		System.out.println("board_token = " + board_token);

		//이미지 첨부 파일을 저장한다 
		String board_image_file_id = boardService.boardImageFileUpload(board_token, file);
		

		// 이미지를 현재 경로와 연관된 파일에 저장하기 위해 현재 경로를 알아냄
		String uploadPath = application.getContextPath() + "/board/image/" + board_image_file_id;

		Map<String, Object> result = new HashMap<>();
		result.put("uploaded", true); // 업로드 완료
		result.put("url", uploadPath); // 업로드 파일의 경로
		
		return result;
	}
	
	//게시물 첨부 파일 다운로드 
	@GetMapping("fileDownload/{board_file_no}")
	public void downloadFile(@PathVariable("board_file_no") int board_file_no, HttpServletResponse response) throws Exception{
		OutputStream out = response.getOutputStream();
		
		BoardFileVO boardFileVO = boardService.getBoardFile(board_file_no);
		
		if (boardFileVO == null) {
			response.setStatus(404);
		} else {
			
			String originName = boardFileVO.getOriginal_filename();
			originName = URLEncoder.encode(originName, "UTF-8");
			//다운로드 할 때 헤더 설정
			response.setHeader("Cache-Control", "no-cache");
			response.addHeader("Content-disposition", "attachment; fileName="+originName);
			response.setContentLength((int)boardFileVO.getSize());
			response.setContentType(boardFileVO.getContent_type());
			
			//파일을 바이너리로 바꿔서 담아 놓고 responseOutputStream에 담아서 보낸다.
			FileInputStream input = new FileInputStream(new File(boardFileVO.getReal_filename()));
			
			//outputStream에 8k씩 전달
	        byte[] buffer = new byte[1024*8];
	        
	        while(true) {
	        	int count = input.read(buffer);
	        	if(count<0)break;
	        	out.write(buffer,0,count);
	        }
	        input.close();
	        out.close();
		}
	}
	
	//게시물 내용에 추가된 이미지 파일 다운로드 
	@GetMapping("image/{board_image_file_id}")
	public void image(@PathVariable("board_image_file_id") String board_image_file_id, HttpServletResponse response) throws Exception{
		OutputStream out = response.getOutputStream();
		
		BoardImageFileVO boardImageFileVO = boardService.getBoardImageFile(board_image_file_id);
		
		if (boardImageFileVO == null) {
			response.setStatus(404);
		} else {
			
			String originName = boardImageFileVO.getOriginal_filename();
			originName = URLEncoder.encode(originName, "UTF-8");
			//다운로드 할 때 헤더 설정
			response.setHeader("Cache-Control", "no-cache");
			response.addHeader("Content-disposition", "attachment; fileName="+originName);
			response.setContentLength((int)boardImageFileVO.getSize());
			response.setContentType(boardImageFileVO.getContent_type());
			
			//파일을 바이너리로 바꿔서 담아 놓고 responseOutputStream에 담아서 보낸다.
			FileInputStream input = new FileInputStream(new File(boardImageFileVO.getReal_filename()));
			
			//outputStream에 8k씩 전달
	        byte[] buffer = new byte[1024*8];
	        
	        while(true) {
	        	int count = input.read(buffer);
	        	if(count<0)break;
	        	out.write(buffer,0,count);
	        }
	        input.close();
	        out.close();
		}
	}		
	
}











