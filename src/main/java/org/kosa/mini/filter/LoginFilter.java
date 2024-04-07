package org.kosa.mini.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.kosa.mini.entity.MemberVO;

import lombok.extern.slf4j.Slf4j;

/**
 * Servlet Filter implementation class LoginFilter
 */
@Slf4j
public class LoginFilter implements Filter {
	Set<String> actionSet = new HashSet<String>();
	Set<String> webSet = new HashSet<String>();
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public LoginFilter() {
        super();
        
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//사용자가 요청한 URL 얻기 
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		MemberVO loginVO = (MemberVO) session.getAttribute("loginVO");
		String url = req.getRequestURI();

		log.info("loginFilter url = " + url);
		if (!actionSet.contains(url) && !webSet.stream().anyMatch(webUrl -> url.startsWith(webUrl))) {
			if (loginVO == null) {
				//로그인 되지 않았으면 로그인 페이지로 이동한다
				resp.sendRedirect("/mini/member/loginForm");
				return;
			}
		}
		//정상 처리 
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		actionSet.add("/mini/");
		actionSet.add("/mini/member/loginForm");
		actionSet.add("/mini/member/login");
		actionSet.add("/mini/member/insertForm");
		actionSet.add("/mini/member/insert");
		webSet.add("/mini/resources");
	}

}