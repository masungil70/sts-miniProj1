package org.kosa.mini.board;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.validation.Valid;

import org.kosa.mini.code.CodeService;
import org.kosa.mini.entity.BoardVO;
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
	private static final long serialVersionUID = 1L;

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
//	
//	public Object insertForm(HttpServletRequest request) throws ServletException, IOException {
//		System.out.println("등록화면");
////		//1. 처리 아래 부분은 LoginFilter에 처리함 
////		HttpSession session = request.getSession();
////		UserVO loginVO = (UserVO) session.getAttribute("loginVO");
////		if (loginVO == null) {
////			return "redirect:user.do?action=loginForm";
////		}
//		
//		//2. jsp출력할 값 설정
//		return "insertForm";
//	}
//	
//	public Object insert(HttpServletRequest request, BoardVO board) throws ServletException, IOException {
//		System.out.println("등록");
//		Map<String, Object> map = new HashMap<>();
//		
//		//전처리로 세션정보를 얻는다
//		HttpSession session = request.getSession();
//		System.out.println("게시물등록시 sessionId = " + session.getId());
//		//로그인 사용자 설정 
//		UserVO loginVO = (UserVO) session.getAttribute("loginVO");
//		if (loginVO != null) {
//			//로그인한 사용자를 게시물 작성자로 설정한다 
//			board.setBwriter(loginVO.getUsername());
//		}
//		
//		//1. 처리
//		int updated = boardService.insert(board);
//		
//		if (updated == 1) { //성공
//			map.put("status", 0);
//		} else {
//			map.put("status", -99);
//			map.put("statusMessage", "회원 가입이 실패하였습니다");
//		}
//		return map;
//	}
	
}











