<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- include는 서버 내부에서 이동하기 때문에 "/"하면 context 인식함. -->
<jsp:include page="/top.jsp" />
<div class="container m2">
	<script>
		const login_check = function() {
			if (!loginF.userid.value) {
				alert('아이디를 입력하세요!');
				loginF.userid.focus();
				return false;
			}
			if(!loginF.pwd.value) {
				alert('비밀번호를 입력하세요.');
				loginF.pwd.focus();
				return false;
			}
			return true;
		}
	</script>
	<%--						<%=request.getCookies()%> 와 동일 --%>
	<c:forEach var="ck" items="${pageContext.request.cookies}">
		<%-- ${ck.name} => ${ck.value} --%>
		<c:if test="${ck.name eq 'uid'}">
			<c:set var="uid" value="${ck.value}" />
		</c:if>
	</c:forEach>

	<h1 style='color:green'>Login</h1>
	<div id="loginDiv">
		<form name="loginF" action="loginEnd.do" method="post" onsubmit="return login_check()">
		<!--  onsubmit이벤트 핸들러는 form이 전송될때 실행된다. onsubmit에서 호출하는 함수의 반환값에 따라 
			전송 여부를 결정한다. true를 반환하면 전송하고, false를 반환하면 전송하지 않는다.
		-->
			<table border="1">
				<tr>
					<td width="20%" class="m1"><b>아이디</b></td>
					<td width="80%" class="m2">
						<input type="text" name="userid" 
						value="${uid}"
						id="userid" placeholder="ID">
					</td>
				</tr>
				<tr>
					<td width="20%" class="m1"><b>비밀번호</b></td>
					<td width="80%" class="m2">
						<input type="password" name="pwd" id="pwd" placeholder="Password">
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<label for="saveId">
							<input type="checkbox" name="saveId" 
							<c:if test="${uid ne null && not empty uid}">checked</c:if>
							id="saveId">아이디 저장
						</label>
						<button >로그인</button>
						<!-- submit 버튼 -->
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

<jsp:include page="/foot.jsp" />
