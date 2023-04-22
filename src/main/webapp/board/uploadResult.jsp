<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="/top.jsp" />

<div class="container">
	<h1>파일 업로드 결과</h1>
	<h2 style='color:blue'>${msg }</h2>
	<h2>올린이: ${writer }</h2>
	<h2>파일명: ${fname }</h2>
	<h2>파일크기: ${fsize } bytes</h2>
</div>

<jsp:include page="/foot.jsp" />