<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/top.jsp" />

<style>
	ul li {
		list-style-type: none;
	}
	
	#bbs {
		width: 80%;
		margin: 1.5em auto;
	}
	
	#boardF li{
		padding: 8px 5px;
		text-align: left;
	}
	
	#subject, #content, #Filename {
		width: 95%;
	}
	
	#boardF input, textarea {
		padding: 5px;
	}
	
	#boardF li:last-child {
		text-align: center;
	}
	
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>

<%-- CKEditor --%>
<script src="https://cdn.ckeditor.com/4.21.0/standard/ckeditor.js"></script>
<!-- https://ckeditor.com/docs/ckeditor4/latest/guide/dev_installation.html#using-the-cdn -->
<script>
	$(function(){
		CKEDITOR.replace('content');
	})

	const board_check = function() {
		if(!$('#subject').val()) {
			alert('제목을 입력하세요.');
			$('#subject').focus();
			return false;
		}
		/*
		if (!$('#content').val()) {
			alert('글 내용을 입력하세요.');
			$('#content').focus();
			return false;
		}
		*/
		
		if (!CKEDITOR.instances.content.getData()) {
			alert('글 내용을 입력하세요.');
			CKEDITOR.instances.content.focus();
			return false;
		}
		
		return true;
	}
</script>

<div class="container m2">
	<h1>Board 글 수정</h1>
	
	<c:if test="${board == null}">
		<script>
			alert('해당 글은 없습니다.');
			history.back();
		</script>
	</c:if>
	
	<c:if test="${board != null}">
		<div id="bbs">
			<!-- 
			파일 업로드 주의사항
			1) method= post여야 함. 
			2) post방식일 겅우 인고딩 방식(enctype)이 2가지가 있는데
				1. application/x-www-form-urlencoded (디폴트)
					=> 이 경우는 첨부파일명만 서버에 전송된다.
				2. multipart/form-data ===> 외우기!!
					=> 파일 이름과 함께 파일 데이터가 서버에 전송된다.
		 	-->
		
			<form name="boardF" id="boardF" action="boardEdit.do" method="post"
			enctype="multipart/form-data" onsubmit="return board_check()">
			<!-- hidden data ----------------------------------------- -->
			<input type="hidden" name="num" value="${board.num}">
			<!-- ----------------------------------------------------- -->
				<ul>
					<li><b>제목</b></li>
					<li>
						<input type="text" name="subject" value="${board.subject}" 
						id="subject" placeholder="Subject">
					</li>
					<li><b>글 내용</b></li>
					<li>
						<textarea name="content" id="content" placeholder="Content" 
						rows="10" cols="50">${board.content}</textarea>
					</li>
					
					<li><b>첨부파일</b></li>
					<li>
						${board.filename} [${board.filesize} bytes]
						<!--  예전에 첨부했던 파일 -------------------------------- -->
						<input type="text" name="old_file" value="${board.filename}">
						
						<!-- 새로 첨부하는 파일 ---------------------------------- -->
						<input type="file" name="filename" id="filename"
						placeholder="Attach File">
						<!-- ------------------------------------------------ -->
					</li>
					<li>
						<button>글 수정</button>
						<button type="reset">다시 쓰기</button>
					</li>
				</ul>
			</form>
		</div>
	</c:if>
	
</div>

<jsp:include page="/foot.jsp" />