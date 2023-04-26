<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>
<!-- errorAlert.jsp -->

<script>
	alert('<%= exception.getMessage() %>');
	history.back();
</script>