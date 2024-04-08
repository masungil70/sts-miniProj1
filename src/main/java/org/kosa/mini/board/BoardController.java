package org.kosa.mini.board;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.kosa.mini.code.CodeService;
import org.kosa.mini.entity.BoardVO;
import org.kosa.mini.entity.MemberVO;
import org.kosa.mini.page.PageRequestVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	public Object insert(@RequestBody BoardVO boardVO, HttpSession session) throws ServletException, IOException {
		log.info("등록 {}", boardVO);
		Map<String, Object> map = new HashMap<>();
		map.put("status", -99);
		map.put("statusMessage", "회원 가입이 실패하였습니다");
		
		//전처리로 세션정보를 얻는다
		log.info("게시물등록시 sessionId = " + session.getId());
		//로그인 사용자 설정 
		MemberVO loginVO = (MemberVO) session.getAttribute("loginVO");
		if (loginVO != null) {
			//로그인한 사용자를 게시물 작성자로 설정한다 
			boardVO.setMember_id(loginVO.getMember_id());
			int updated = boardService.insert(boardVO);
			if (updated == 1) { //성공
				map.put("status", 0);
			}
		} else {
			map.put("status", -98);
			map.put("statusMessage", "로그인 정보가 존재하지 않습니다");
		}
		return map;
	}
	
}











