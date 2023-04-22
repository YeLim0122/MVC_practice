<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="/top.jsp" />

<div class="container m2">
	<h1>파일 업로드 테스트</h1>
	<br><br>
	<!-- 
		파일 업로드 주의사항
		1) method= post여야 함. 
		2) post방식일 겅우 인고딩 방식(enctype)이 2가지가 있는데
			1. application/x-www-form-urlencoded (디폴트)
			2. multipart/form-data ===> 외우기!!
				=> 파일 이름과 함께 파일 데이터가 서버에 전송된다.
	 -->
	<form name="f" id="f" method="post" enctype="multipart/form-data"
		action="uploadEnd.do">
		<label>올린이: </label>
		<input type="text" name="writer"><br><br>
		<label>첨부파일: </label>
		<input type="file" name="fname"><br><br>
		<button>Upload</button>
	
	</form>
</div>

<jsp:include page="/foot.jsp" />