<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Overall Equipment Effectiveness - Intech Group</title>
</head>
<body>
<%
    String redirectURL = "http://192.168.1.251:9050/Oee2024-2.0/home-page";
    response.sendRedirect(redirectURL);
%>
		<header>
		<%@ include file="header.jsp" %>
	</header>
	<div class="main_content">
		<aside class="left">
			<%@ include file="sidebar.jsp" %>
		</aside>
		<main>
			<div>

			</div>
		</main>
		<aside class="right">
		</aside>
	</div>
	<footer>
		<%@ include file="footer.jsp" %>
	</footer>
</body>
</html>