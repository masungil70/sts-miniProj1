package org.kosa.mini.board;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.kosa.mini.code.CodeService;
import org.kosa.mini.entity.BoardFileVO;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public Object insertForm() throws ServletException, IOException {
		System.out.println("등록화면");
		
		//2. jsp출력할 값 설정
		return "board/insertForm";
	}
	
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
}











