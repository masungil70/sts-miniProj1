<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>수정화면</title>
    <%@ include file="/WEB-INF/views/include/css.jsp" %>
    <%@ include file="/WEB-INF/views/include/js.jsp" %>
</head>
<body>
    <%@ include file="/WEB-INF/views/include/header.jsp" %>
    <h1>
        게시물 수정 수정양식 
    </h1>
    <form id="rForm" action="" method="post">
        <label>게시물 번호: </label> <input type="text" id="bno" name="bno" value="${board.bno}" readonly="readonly"> <br/>
        <label>제목 : </label><input type="text" id="btitle" name="btitle" value="${board.btitle}"><br/>
        <label>내용: </label> <input type="text" id="bcontent" name="bcontent" value="${board.bcontent}"><br/>
	    <div>
	        <input type="submit" value="수정">
	        <a href="javascript:history(-1)">취소</a>
	    </div>
    
    </form>

<script type="text/javascript">
menuActive("board_link");

const rForm = document.getElementById("rForm");
rForm.addEventListener("submit", e => {
	//서버에 form data를 전송하지 않는다 
	e.preventDefault();
	
	myFetch("update", "rForm", json => {
		if(json.status == 0) {
			//성공
			alert("게시물 정보 수정을 성공 하였습니다");
			location = "view?bno=" + bno.value;
		} else {
			alert(json.statusMessage);
		}
	});
});

</script>    
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>