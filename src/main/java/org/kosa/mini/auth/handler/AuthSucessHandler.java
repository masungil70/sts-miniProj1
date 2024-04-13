package org.kosa.mini.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kosa.mini.login.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


@Component
public class AuthSucessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@Autowired
	private LoginMapper loginMapper;
	
	@Override
    public void onAuthenticationSuccess(
    		HttpServletRequest request
    		, HttpServletResponse response
    		, Authentication authentication //로그인한 사용자 정보가 있는 객체 
    		) throws IOException, ServletException {
        
		//로그인 한 마지막 시간 수정 
		loginMapper.updateMemberLastLogin(authentication.getName());
		//로그인 실패시 카운트를 초기화 한다 
		loginMapper.loginCountClear(authentication.getName());

		System.out.println("authentication ->" + authentication);
		
		//성공시 이동할 주소 
        //setDefaultTargetUrl("/");
        
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
