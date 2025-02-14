<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<!-- <meta name="viewport" content="width=device-width, initial-scale=1.0"> -->
	<title>Overall Equipment Effectiveness - Intech Group</title>
	<link rel="stylesheet" href="css/styles.css">
	<!--  <link rel="shortcut icon" href="/favicon.ico" type="imgs/intechicon.ico"> -->
	<link href="imgs/intechicon.ico" rel="shortcut icon" />
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>
<body>
	<header>
		<%@ include file="header.jsp" %>
	</header>
	<div class="main_content">
		<aside class="left">
			<%@ include file="sidebar.jsp" %>
		</aside>
		<main id = "container">
			<%@
				page import="java.sql.Date, java.time.LocalDate, java.util.*, vn.intech.dto.OeeDto, vn.intech.service.impl.*"
			 %>
			 <%! 
			 	
			 %>
			 <%						 
				//OeeServiceImpl oeeService = OeeServiceImpl.getInstance();
			 %>
			<c:forEach items="${listMachine}" var="machine">
				<c:set var="id" value="${machine.id}"/>
				<c:set var="name" value="${machine.name}"/>
				<%		
					String machineId = (String)pageContext.getAttribute("id");
					String machineName = (String)pageContext.getAttribute("name");
					//lstOee = oeeService.readOeeTable(machineId, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now());
				%>
				<c:set var="machineExisted" value="0" />	
				<c:forEach items="${lstOeeOfMonth}" var="oeeOfMonth">
					<c:if test="${oeeOfMonth.idMachine == machine.id}">
						<c:set var="machineExisted" value="1" />
						<c:set var="oeeDto" value="${oeeOfMonth}"/>	
						<div class="oee">	
							<div class="oee__top">
								<h3 class="oee__top--title">${machine.name}</h3>			
								<button class="oee__top--button" onClick="goToInputPage('<%=machineId%>', '<%=machineName%>')">${machine.id}</button>
							</div>
							<div class="oe__center">				
								<div class="oee__center--time">
									<div class="oe__center--time production-time">
										<c:set var="TotalProductionTime" value="${Math.round((oeeDto.f0/60)*100)/100}"/>
										<p>Tổng TG sản xuất</p> <p>${TotalProductionTime} (giờ)</p>
									</div>
									<div class="oe__center--time run-time">
										<c:set var="RunTime" value="${Math.round((oeeDto.runTime/60)*100)/100}"/>
										<p>Tổng TG chạy máy</p> <p>${RunTime} (giờ)</p>
									</div>
									<div class="oee__center--time down-time">
										<c:set var="DownTime" value="${Math.round((oeeDto.downTime/60)*100)/100}"/>
										<p>Tổng TG dừng máy</p> <p>${DownTime} (giờ)</p>
									</div>
								</div>
								<div class="oee__center--index">	
									<div class="oee-index">
										<div class="circular-progress">
											<span class="progress-value">
												<c:set var="a" value="${oeeDto.oee}"/>	
												<%
													float oee = (float)pageContext.getAttribute("a");
												%>
												<%=(double)Math.round(oee*100)/100  + "%"%>			
											</span>
										</div>
										<p class="progress-text">OEE</p>
										<input class="oee-value" type="hidden" value= "${oeeDto.oee}">
									</div>				
									<div class="apq-index">
										<div class="apq-index__w3-container">
											<p class="apq-index__w3-container--a">Availability(H1):
												<c:set var="a" value="${oeeDto.a}"/>
												<%
													float a = (float)pageContext.getAttribute("a");
												%>
												<%= (double)Math.round(a*100)/100 + "%"%>									
											</p>
											<div class="w3-border" style="width: 200px">
												<div class=<%if(a>=90){%><%="w3-blue"%><%}else if(a>=50 && a<90){%><%="w3-yellow"%><%}else if(a<50){%><%="w3-red"%><%}%>
												 style="height: 24px; width: <%=a%>%"></div>
											</div>
										</div>
										<div class="apq-index__w3-container">
											<p class="apq-index__w3-container--a">Performance(H2):
												<c:set var="p" value="${oeeDto.p}"/>
												<%
												float p = (float)pageContext.getAttribute("p");
												%>
												<%= (double)Math.round(p*100)/100  + "%"%>
											</p>
											<div class="w3-border" style="width: 200px">
												<div class=<%if(p >= 90){%><%="w3-blue"%><%}else if(p >= 50 && p < 90){%><%="w3-yellow"%><%}else if(p < 50){%><%="w3-red"%><%}%> 
												style="height: 24px; width: <%=p%>%"></div>
											</div>
										</div>
										<div class="apq-index__w3-container">
											<p class="apq-index__w3-container--a">Quality(H3):
												<c:set var="q" value="${oeeDto.q}"/>
												<%
												float q = (float)pageContext.getAttribute("q");
												%>
												<%=(double)Math.round(q*100)/100  + "%"%>
											</p>
											<div class="w3-border" style="width: 200px">
												<div class=<%if(q >= 90){%><%="w3-blue"%><%}else if(q >= 50 && q < 90){%><%="w3-yellow"%><%}else if(q < 50){%><%="w3-red"%><%}%> 
												style="height: 24px; width: <%=q%>%"></div>
											</div>
										</div>
									</div>
								</div>
							</div>	
							<div class="oee__bottom">
							</div>	
						</div>
					</c:if>						
				</c:forEach>	
				<c:if  test="${machineExisted == 0}">
					<div class="oee">	
						<div class="oee__top">
							<h3 class="oee__top--title">${machine.name}</h3>			
							<button class="oee__top--button" onClick="goToInputPage('<%=machineId%>', '<%=machineName%>')">${machine.id}</button>
						</div>
						<div class="oe__center">				
							<div class="oee__center--time">
								<div class="oe__center--time production-time">
									<p>Tổng TG sản xuất</p> <p>0 (giờ)</p>
								</div>
								<div class="oe__center--time run-time">
									<p>Tổng TG chạy máy</p> <p>0 (giờ)</p>
								</div>
								<div class="oee__center--time down-time">
									<p>Tổng TG dừng máy</p> <p>
										0 (giờ)
									</p>
								</div>
							</div>
							<div class="oee__center--index">	
								<div class="oee-index">
									<div class="circular-progress">
										<span class="progress-value">	
											0 %		
										</span>
									</div>
									<p class="progress-text">OEE</p>
									<input class="oee-value" type="hidden" value= "0">
								</div>				
								<div class="apq-index">
									<div class="apq-index__w3-container">
										<p class="apq-index__w3-container--a">Availability(H1):
											<%
												float a = 0;
											%>
											<%= a + "%"%>									
										</p>
										<div class="w3-border" style="width: 200px">
											<div class=<%if(a>=90){%><%="w3-blue"%><%}else if(a>=50 && a<90){%><%="w3-yellow"%><%}else if(a<50){%><%="w3-red"%><%}%>
											 style="height: 24px; width: <%=a%>%"></div>
										</div>
									</div>
									<div class="apq-index__w3-container">
										<p class="apq-index__w3-container--a">Performance(H2):
											<%
											float p = 0;
											%>
											<%= p  + "%"%>
										</p>
										<div class="w3-border" style="width: 200px">
											<div class=<%if(p >= 90){%><%="w3-blue"%><%}else if(p >= 50 && p < 90){%><%="w3-yellow"%><%}else if(p < 50){%><%="w3-red"%><%}%> 
											style="height: 24px; width: <%=p%>%"></div>
										</div>
									</div>
									<div class="apq-index__w3-container">
										<p class="apq-index__w3-container--a">Quality(H3):
											<%
											float q = 0;
											%>
											<%= q  + "%"%>
										</p>
										<div class="w3-border" style="width: 200px">
											<div class=<%if(q >= 90){%><%="w3-blue"%><%}else if(q >= 50 && q < 90){%><%="w3-yellow"%><%}else if(q < 50){%><%="w3-red"%><%}%> 
											style="height: 24px; width: <%=q%>%"></div>
										</div>
									</div>
								</div>
							</div>
						</div>	
						<div class="oee__bottom">
						</div>	
					</div>
				</c:if>										
			</c:forEach>			
		</main>
		<aside class="right">
		</aside>
	</div>
	<footer>
		<%@ include file="footer.jsp" %>
	</footer>
	
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/homescript.js"></script>
	<script type="text/javascript">
		function goToInputPage(machineId, machineName){
			document.location.href = "${pageContext.request.contextPath}/input-page?mid="+ machineId +"&mname="+ machineName +"";
		}		
	</script>
	
</body>
</html>