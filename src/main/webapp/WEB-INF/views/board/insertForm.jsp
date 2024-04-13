<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html lang="kr">
<head>
    <title>등록화면</title>
    <%@ include file="/WEB-INF/views/include/meta.jsp" %>
    <%@ include file="/WEB-INF/views/include/css.jsp" %>
    <%@ include file="/WEB-INF/views/include/js.jsp" %>
    <%-- 부트스트랩5 css --%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <%-- ckeditor 관련 자바 스크립트  --%>
	<script src="https://cdn.ckeditor.com/ckeditor5/12.4.0/classic/ckeditor.js"></script>
	<script src="https://ckeditor.com/apps/ckfinder/3.5.0/ckfinder.js"></script>
    <style type="text/css">
    	#rForm {
    		text-align:center;
    	}
	    .btitle {
			width: 80%;
			max-width: 800px;
			margin: 0 auto;
		}
		
	    .ck.ck-editor {
			width: 80%;
			max-width: 800px;
			margin: 0 auto;
		}
		
		.ck-editor__editable {
			height: 80vh;
		}
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/views/include/header.jsp" %>
    
    <h1>
        게시물 등록양식 
    </h1>
	<%-- 로그인 사용자 정보 출력 --%>
	<h3>로그인 : ${principal.member_name} </h3>
    
    <form id="rForm" action="insert" method="post" enctype="multipart/form-data">
        <input class="btitle" id="btitle" name="btitle" required="required" placeholder="게시물 제목을 입력해주세요"><br/>
        <textarea id="bcontent" name="bcontent" required="required" placeholder="게시물 내용을 입력해주세요">
        </textarea>
		<div id="div_file">
			<input  type='file' name='file' />
		</div>
        <br/>
	    <div>
	        <input type="submit" value="등록">
	        <a href="javascript:history(-1)">취소</a>
	    </div>
    
    </form>
    
<script type="text/javascript">
	//상단 메뉴에서 게시물이 선택되게 함  
	menuActive("board_link");
	
	//cfeditor관련 설정 
	let bcontent; //cfeditor의 객체를 저장하기 위한 변수 
	ClassicEditor.create(document.querySelector('#bcontent'))
	.then(editor => {
		console.log('Editor was initialized');
		//ckeditor객체를 전역변수 bcontent에 설정함 
		window.bcontent = editor;
	})
	.catch(error => {
		console.error(error);
	});

    const rForm = document.getElementById("rForm");
    rForm.addEventListener("submit", e => {
    	//서버에 form data를 전송하지 않는다 
    	e.preventDefault();
    	
		myFileFetch("insert", "rForm", json => {
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