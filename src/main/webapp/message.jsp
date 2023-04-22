<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
	String msg = (String)request.getAttribute("msg");
	String loc = (String)request.getAttribute("loc");
	
	el 표현식: ${key 값}
--%>

<c:if test="${msg != null && loc != null }">
	<script>
		alert('${requestScope.msg}');	// 정석으로 쓰는 방법
		// 		ㄴ> request에 저장된 msg 키 값에 해당하는 value값을 출력하라는 의미.
		// 			sessionScope.msg => 세션에 저장된 msg키에 해당하는 값
		
		location.href='${loc}';		// 생략해서 쓰기 가능
	</script>
</c:if>

<%-- else문은 따로 없음. --%>
<c:if test="${msg == null && loc != null}">
	<script>
		location.href='${loc}'
	</script>
</c:if>