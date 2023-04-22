<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%
	// 컨텍스트명 알아내기
	String myctx = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>homepage</title>
    <link rel="stylesheet" type="text/css" href="<%= myctx %>/css/layout.css">
    <!-- 경로에 /를 붙이고 경로를 넣으면 (http://localhost:9090)까지 잡힘. -->
	<!-- ===> "/" => "webapps/ROOT" -->
    
</head>
<body>
    <div id="wrap">
        <!-- header: 로고이미지, 검색폼, 목차 등 -->
        <header>
            <!-- header -->
            <a href="<%=myctx%>/index.do">
                <img src="<%= myctx %>/images/logo.png">
            </a>
        </header>
        <div class="cls"></div>
        <!-- nav: 네비게이션바, 메뉴 -->
        <nav>
            <!-- nav -->
            <ul>
                <li><a href="<%= myctx %>/index.do">HOME</a></li>
                
                <li><a href="<%= myctx %>/join.do">Signup</a></li>
                <li><a href="<%= myctx %>/login.do">Signin</a></li>
          
                <li><a href="<%= myctx %>/logout.do">Logout</a></li>
      
                <li><a href="<%= myctx %>/user/boardForm.do">Board 쓰기</a></li>
                            
                <li><a href="<%= myctx %>/boardList.do">Board 목록</a></li>
            
                <li id="login">
                	<a href="#" style='color: white'>a님 로그인 중 ...</a>
                </li>
               
                
            </ul>
        </nav>
        <div class="cls"></div>
        <!-- article: 주로 컨텐츠가 들어감 -->
        <article>