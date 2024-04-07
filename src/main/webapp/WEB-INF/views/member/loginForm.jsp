<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
</head>
<body>
    <h1>
        로그인 
    </h1>
    <form id="rForm" action="" method="post">
        <label>아이디: </label><input type="text" id="member_id" name="member_id" required="required" placeholder="아이디를 입력해주세요"><br/>
        <label>비번: </label> <input type="text" id="member_id" name="member_id" required="required" placeholder="비밀번호를 입력해주세요"><br/>
	    <div>
	        <input type="submit" value="로그인">
	        <a href="javascript:history(-1)">취소</a>
	    </div>
    
    </form>
    
</body>
</html>