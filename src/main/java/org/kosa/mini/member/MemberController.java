package org.kosa.mini.member;

import java.io.IOException;

import javax.servlet.ServletException;

import org.kosa.mini.entity.BoardVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
	
	@RequestMapping("loginForm")
	public Object loginForm(BoardVO board, Model model) throws ServletException, IOException {
		log.info("로그인 화면 ");
		
		return "member/loginForm"; 
	}
	
}











