<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="/top.jsp" />

<div class="container">
	<h1>hello.jsp</h1>
	<h2 style='color: red'> <%= request.getAttribute("msg") %></h2>
	
</div>

<jsp:include page="/foot.jsp" />