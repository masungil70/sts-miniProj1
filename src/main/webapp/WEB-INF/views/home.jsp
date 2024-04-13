<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Home</title>
    <%@ include file="/WEB-INF/views/include/meta.jsp" %>
    <%@ include file="/WEB-INF/views/include/css.jsp" %>
    <%@ include file="/WEB-INF/views/include/js.jsp" %>
</head>
<body>
    <%@ include file="/WEB-INF/views/include/header.jsp" %>
<h1>
	Hello world!
</h1>

<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>

<c:choose>
	<c:when test="${empty principal}">
		<ul class="navbar-nav">
			<li class="nav-item"><a class="nav-link" href="<c:url value='/login/loginForm'/>">로그인</a></li>
			<li class="nav-item"><a class="nav-link" href="<c:url value='/login/joinForm'/>">회원가입</a></li>
		</ul>
	</c:when>
	<c:otherwise>
		이름 : ${principal.member_name}
		<ul class="navbar-nav">
			<li class="nav-item"><a class="nav-link" href="<c:url value='/member/updateForm'/>">회원정보</a></li>
			<li class="nav-item"><a class="nav-link" href="<c:url value='/login/logout'/>">로그아웃</a></li>
			<li class="nav-item"><a class="nav-link" href="<c:url value='/board/list'/>">게시물 목록</a></li>
		</ul>
	</c:otherwise>
</c:choose>

<script>
//회사 홈 페이지
menuActive("home_link");

</script>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
