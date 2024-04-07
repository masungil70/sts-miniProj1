<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="kr">
<head>
    <meta charset="UTF-8">
    <title>등록화면</title>
    <%@ include file="/WEB-INF/views/include/css.jsp" %>
    <%@ include file="/WEB-INF/views/include/js.jsp" %>
</head>
<body>
    <%@ include file="/WEB-INF/views/include/header.jsp" %>
    
    <h1>
        게시물 등록양식 
    </h1>
	<h3>로그인 : ${loginVO.member_name} </h3>
    
    <form id="rForm" action="insert" method="post">
        <label>제목 : </label><input type="text" id="btitle" name="btitle" required="required"><br/>
        <label>내용 : </label><input type="text" id="bcontent" name="bcontent" required="required"><br/>
    <div>
        <input type="submit" value="등록">
        <a href="javascript:history(-1)">취소</a>
    </div>
    
    </form>
    
<script type="text/javascript">
menuActive("board_link");

    const rForm = document.getElementById("rForm");
    rForm.addEventListener("submit", e => {
    	//서버에 form data를 전송하지 않는다 
    	e.preventDefault();
    	
		myFetch("insert", "rForm", json => {
			switch(json.status) {
			case 0:
				//성공
				alert("게시물을 등록 하였습니다");
				location = "list";
				break;
			default:
				alert(json.statusMessage);
			}
		});
    });
    
</script>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>