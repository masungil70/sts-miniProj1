<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!
</h1>
	<a href="<c:url value='/member/loginForm'/>" >로그인 양식</a> <br/>
	<a href="<c:url value='/board/list'/>" >게시물 목록</a> <br/>

	<a href="javascript:json1()" >json1 호출</a> <br/>
	<a href="javascript:json2()" >json2 호출</a> <br/>
	<a href="javascript:json3()" >json3 호출</a> <br/>
	<a href="javascript:json4()" >json4 호출</a> <br/>
	<a href="javascript:json51()" >json51 호출</a> <br/>
	<a href="javascript:json52()" >json52 호출</a> <br/>
	<a href="javascript:jsonPath()" >json/abc 호출</a> <br/>
	<a href="javascript:json10()" >json10 호출(200)</a> <br/>
	<a href="javascript:json11()" >json11 호출(500에러)</a> <br/>
	<hr>
	<a href="javascript:all()" >board/all 호출</a> <br/>
	<a href="javascript:findBoard()" >board/findBoard 호출</a> <br/>
	<a href="javascript:addBoard()" >board/addBoard 호출</a> <br/>
	<a href="javascript:updateBoard()" >board/updateBoard 호출</a> <br/>
	<a href="javascript:deleteBoard()" >board/deleteBoard 호출</a> <br/>

<script type="text/javascript" src="<c:url value='/resources/js/common.js'/>"></script>
<script>
function json1() {
	myFetch("<c:url value='/json1'/>", {}, json => {
		console.log("json1->", json);
	})
}

function json2() {
	myFetch("<c:url value='/json2'/>", {}, json => {
		console.log("json2->", json);
	})
}

function json3() {
	myFetch("<c:url value='/json3'/>", {}, json => {
		console.log("json3->", json);
	})
}

function json4() {
	myFetch("<c:url value='/json4'/>", {}, json => {
		console.log("json4->", json);
	})
}

function json51() {
	myFetch("<c:url value='/json5'/>", {}, json => {
		console.log("json5->", json);
	})
}

function json52() {
	myFetch("<c:url value='/json5'/>", {name:"이순신", age:30}, json => {
		console.log("json5->", json);
	})
}

function json10() {
	myFetch("<c:url value='/json10'/>", {}, json => {
		console.log("json10->", json);
	})
}

function json11() {
	myFetch("<c:url value='/json11'/>", {}, json => {
		console.log("json11->", json);
	})
}

function all(){
	fetch("<c:url value='/board/all'/>", {
		method:"GET",
		headers : {"Content-type" : "application/json; charset=utf-8"}
	}).then(res => res.json())
	.then(json => {
		//서버로 부터 받은 결과를 사용해서 처리 루틴 구현  
		console.log("all", json);
	}).catch(error => {
	    console.error(`error: ${error.message}`);
	});
}

function findBoard(){
	fetch("<c:url value='/board/1004'/>", {
		method:"GET",
		headers : {"Content-type" : "application/json; charset=utf-8"}
	}).then(res => res.json())
	.then(json => {
		//서버로 부터 받은 결과를 사용해서 처리 루틴 구현  
		console.log("findBoard", json);
	}).catch(error => {
	    console.error(`error: ${error.message}`);
	});
}

function addBoard(){
	const param = JSON.stringify({writer:"홍길동", title:"제목", content:"내용"});
	fetch("<c:url value='/board'/>", {
			method:"POST",
			body : param,
			headers : {"Content-type" : "application/json; charset=utf-8"}
	}).then(res => res.text())
	.then(text => {
		//서버로 부터 받은 결과를 사용해서 처리 루틴 구현  
		console.log("text", text );
	}).catch(error => {
	    console.error(`error: ${error.message}`);
	});
}

function updateBoard(){
	const param = JSON.stringify({bno:"1004", writer:"홍길동", title:"제목", content:"내용"});
	fetch("<c:url value='/board/1004'/>", {
			method:"PUT",
			body : param,
			headers : {"Content-type" : "application/json; charset=utf-8"}
	}).then(res => res.text())
	.then(text => {
		//서버로 부터 받은 결과를 사용해서 처리 루틴 구현  
		console.log("text", text );
	}).catch(error => {
	    console.error(`error: ${error.message}`);
	});
}

function deleteBoard(){
	fetch("<c:url value='/board/1004'/>", {
			method:"DELETE",
			headers : {"Content-type" : "application/json; charset=utf-8"}
	}).then(res => res.text())
	.then(text => {
		//서버로 부터 받은 결과를 사용해서 처리 루틴 구현  
		console.log("text", text );
	}).catch(error => {
	    console.error(`error: ${error.message}`);
	});
}

</script>
<P>  The time on the server is ${serverTime}. </P>
</body>
</html>
