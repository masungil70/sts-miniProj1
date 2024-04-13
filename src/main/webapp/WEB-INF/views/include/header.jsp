<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%-- 로그인 사용자 정보 --%>
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>

<ul class="nav nav-pills nav-justified">
  <li class="nav-item">
    <a class="nav-link"  href="<c:url value='/'/>" id="home_link">회사소개</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="<c:url value='/board/list'/>" id="board_link">게시물</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="<c:url value='/member/list'/>" id="member_link">회원관리</a>
  </li>
  	<c:choose>
  		<c:when test="${empty loginVO}">
			<li class="nav-item">
		    	<a class="nav-link" href="<c:url value='/member/loginForm'/>" id="login_link">로그인</a>
			</li>
  		</c:when>
  		<c:otherwise>
			<li class="nav-item">
			    <a class="nav-link" href="<c:url value='/member/logout'/>" id="login_link">${loginVO.member_name}</a>
			</li>
			<li class="nav-item">
			    <a class="nav-link" href="<c:url value='/member/mypage'/>" id="mypage_link">나의정보</a>
			</li>
  		</c:otherwise>
  	</c:choose>
</ul>