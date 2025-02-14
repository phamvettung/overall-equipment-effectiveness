<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Overall Equipment Effectiveness - Intech Group</title>
</head>
<body>
	<div class="wrapper wrapper-header" style="background-color: #ffffff; box-shadow: 0px 0px 0px #aaa; ">
		<img src="imgs/intech.png" width="150px" height="60px">
		<h3>PHẦN MỀM TÍNH TOÁN HIỆU SUẤT MÁY CNC - OEE Tháng <%=LocalDate.now().getMonthValue()+"/"+LocalDate.now().getYear()%> [<%= request.getAttribute("namePage")%>]</h3>
	</div>
</body>
</html>