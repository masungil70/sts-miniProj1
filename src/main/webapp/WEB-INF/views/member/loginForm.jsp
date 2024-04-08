<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
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
    <form id="rForm" action="" method="post">
        <label>아이디: </label><input type="text" id="member_id" name="member_id" required="required" placeholder="아이디를 입력해주세요"><br/>
        <label>비번: </label> <input type="password" id="member_pwd" name="member_pwd" required="required" placeholder="비밀번호를 입력해주세요"><br/>
	    <div>
	        <input type="submit" value="로그인">
	        <a href="javascript:history(-1)">취소</a>
	    </div>
    
    </form>

<script type="text/javascript">
menuActive("login_link");

const rForm = document.getElementById("rForm");
rForm.addEventListener("submit", e => {
	//서버에 form data를 전송하지 않는 
	e.preventDefault();
	
	myFetch("login", "rForm", json => {
		if(json.status == 0) {
			//성공
			alert(json.loginVO.member_name + " 회원님 로그인 감사합니다");
			//location = "<c:url value='/board/list'/>"; //절대 경로, 컨텍스트명 자동 변경 
			//location = "/mini/board/list"; //절대 경로 
			location = "../board/list"; //상대경로 
		} else {
			alert(json.statusMessage);
		}
	});
});

</script>    
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>