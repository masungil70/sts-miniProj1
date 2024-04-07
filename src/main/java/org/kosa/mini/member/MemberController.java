package org.kosa.mini.member;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.kosa.mini.entity.BoardVO;
import org.kosa.mini.entity.MemberVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	//xml 또는 어노터이션 처리하면 스프링 
	//어노터이션 처리하면 스프링 부트   
	private final MemberService memberService;

	
	@RequestMapping("loginForm")
	public Object loginForm(BoardVO board, Model model) throws ServletException, IOException {
		log.info("로그인 화면 ");
		
		return "member/loginForm"; 
	}

	@RequestMapping("login")
	@ResponseBody
	public Map<String, Object> login(@RequestBody MemberVO memberVO, HttpSession session) throws ServletException, IOException {
		log.info("삭제 -> {}", memberVO);
		//1. 처리
		MemberVO loginVO = memberService.login(memberVO);
		
		Map<String, Object> map = new HashMap<>();
		if (loginVO != null) { //성공
			session.setAttribute("loginVO", loginVO);
			map.put("loginVO", loginVO);
			map.put("status", 0);
		} else {
			map.put("status", -99);
			map.put("statusMessage", "아이디 또는 비밀 번호가 잘못되었습니다");
		}
		
		return map;
	}
	
}











