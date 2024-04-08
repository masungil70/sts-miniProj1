<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="/WEB-INF/views/include/css.jsp" %>
    <%@ include file="/WEB-INF/views/include/js.jsp" %>
</head>
<body>
    <%@ include file="/WEB-INF/views/include/header.jsp" %>
</head>
<body>
	<h1>게시물목록</h1>
	<h3>로그인 : ${loginVO.member_name} </h3>
    <form id="searchForm" action="list" method="post" >
        <select id="size" name="size" >
        	<c:forEach var="size" items="${sizes}">
        		<option value="${size.codeid}" ${pageRequestVO.size == size.codeid ? 'selected' : ''} >${size.name}</option>
        	</c:forEach>
        </select>
    	<label>제목</label>
    	<input type="text" id="searchKey" name="searchKey" value="${param.searchKey}">
    	<input type="submit" value="검색">
    </form>
    
    <table border="1">
        <tr>
            <th>게시물번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>작성일</th>
        </tr>
        <c:forEach var="board" items="${pageResponseVO.list}">
        <tr>
            <td style="cursor:pointer;"><a data-bs-toggle="modal" data-bs-target="#boardViewModel" data-bs-bno="${board.bno}">${board.bno}</a></td>
            <td>${board.btitle}</td>
            <td>${board.bwriter}</td>
            <td>${board.bdate}</td>
        </tr>
        </c:forEach>
    </table>
    
    <!--  페이지 네비게이션 바 출력  -->
    <div class="float-end">
        <ul class="pagination flex-wrap">
            <c:if test="${pageResponseVO.prev}">
                <li class="page-item">
                    <a class="page-link" data-num="${pageResponseVO.start -1}">이전</a>
                </li>
            </c:if>

            <c:forEach begin="${pageResponseVO.start}" end="${pageResponseVO.end}" var="num">
                <li class="page-item ${pageResponseVO.pageNo == num ? 'active':''} ">
                    <a class="page-link"  data-num="${num}">${num}</a></li>
            </c:forEach>

            <c:if test="${pageResponseVO.next}">
                <li class="page-item">
                    <a class="page-link"  data-num="${pageResponseVO.end + 1}">다음</a>
                </li>
            </c:if>
        </ul>

    </div>
    
<!-- 상세보기 Modal -->
<div class="modal fade" id="boardViewModel" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="staticBackdropLabel">게시물 상세보기</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
	      <label>게시물 번호:</label><span id="bno"></span><br/>
	      <label>제목 : </label><span id="btitle"></span><br/>
	      <label>내용 : </label><span id="bcontent"></span><br/>
	      <label>ViewCount :</label><span id="view_count"></span><br/>
	      <label>작성자 : </label><span id="bwriter"></span><br/>
	      <label>작성일 : </label><span id="bdate"></span><br/>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
      </div>
    </div>
  </div>
</div>
    
<script>
menuActive("board_link");


document.querySelector(".pagination").addEventListener("click", function (e) {
    e.preventDefault()

    const target = e.target

    if(target.tagName !== 'A') {
        return
    }
    //dataset 프로퍼티로 접근 또는 속성 접근 메서드 getAttribute() 사용 하여 접근 가능 
    //const num = target.getAttribute("data-num")
    const num = target.dataset["num"];
    
    //페이지번호 설정 
    searchForm.innerHTML += `<input type='hidden' name='pageNo' value='\${num}'>`;
    searchForm.submit();
});


document.querySelector("#size").addEventListener("change", e => {
    searchForm.submit();
});

const boardViewModel = document.querySelector("#boardViewModel");
const span_bno = document.querySelector(".modal-body #bno");
const span_btitle = document.querySelector(".modal-body #btitle");
const span_bcontent = document.querySelector(".modal-body #bcontent");
const span_view_count = document.querySelector(".modal-body #view_count");
const span_bwriter = document.querySelector(".modal-body #bwriter");
const span_bdate = document.querySelector(".modal-body #bdate");

boardViewModel.addEventListener('shown.bs.modal', function (event) {
	const a = event.relatedTarget;
	const bno = a.getAttribute('data-bs-bno'); //a.dataset["bs-bno"] //, a.dataset.bs-bno 사용안됨
	console.log("모달 대화 상자 출력... bno ", bno);
	
	span_bno.innerText = "";
	span_btitle.innerText = "";
	span_bcontent.innerText = "";
	span_view_count.innerText = "";
	span_bwriter.innerText = "";
	span_bdate.innerText = "";

	myFetch("jsonBoardInfo", { bno : bno }, json => {
		if(json.status == 0) {
			//성공
			const jsonBoard = json.jsonBoard; 
			span_bno.innerText = jsonBoard.bno;
			span_btitle.innerText = jsonBoard.btitle;
			span_bcontent.innerText = jsonBoard.bcontent;
			span_view_count.innerText = jsonBoard.view_count;
			span_bwriter.innerText = jsonBoard.bwriter;
			span_bdate.innerText = jsonBoard.bdate;
			
		} else {
			alert(json.statusMessage);
		}
	});

})
	
function jsView(bno) {
	//인자의 값을 설정한다 
//	bno.value = bno;
	
	//양식을 통해서 서버의 URL로 값을 전달한다
//	listForm.submit();
//	.Modal("#boardViewModel").model();
//	boardViewModel.show();
}
</script>      
    <div class="button-container">
        <a href="insertForm">등록</a>
    </div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>

