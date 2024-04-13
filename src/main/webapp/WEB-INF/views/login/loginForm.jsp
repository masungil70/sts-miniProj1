<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>로그인</title>
    <%@ include file="/WEB-INF/views/include/css.jsp" %>
    <%@ include file="/WEB-INF/views/include/js.jsp" %>
</head>
<body>
    <%@ include file="/WEB-INF/views/include/header.jsp" %>
    <h1>
        로그인 
    </h1>

<form action="<c:url value='/login'/>" method="post">
	<%-- csrf 토큰 설정 --%>
	<sec:csrfInput/>
	아이디 : <input type="text" name="member_id" required="required" placeholder="아이디를 입력해주세요"/><br/>
	비밀번호 : <input type="password" name="member_pwd" required="required" placeholder="비밀번호를 입력해주세요"/><br/>
	<input type="submit" value="로그인">
</form>
<script>
	menuActive("login_link");
	window.addEventListener("load", e => {
		
		<%-- 로그인시 오류 메시지 출력 --%>
		msg = "${error ? exception : ''}"; 
		if (msg !== "")  {
			alert(msg);
		}
		
	})
</script>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>