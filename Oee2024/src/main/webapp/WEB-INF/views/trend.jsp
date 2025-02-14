<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Overall Equipment Effectiveness - Intech Group</title>
	<link rel="stylesheet" href="css/styles.css">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<header>
		<%@ include file="header.jsp" %>
	</header>
	<div class="main_content">
		<aside class="left">
			<%@ include file="sidebar.jsp" %>
		</aside>
		<main>
			<div class="container--fluid">
				<div class="input-group mb-3" style="margin-top: 17px">
				    <span class="input-group-text">Ngày bắt đầu</span>
				    <input type="datetime-local" id="startDate">
				    <span class="input-group-text">Ngày kết thúc</span>
				    <input type="datetime-local" id="endDate">
				    <span class="input-group-text">Tên máy</span>
				    <select class="form-select" 
				    	onchange="if (this.selectedIndex) selectMachineOnChange();">
				    	<option>None</option>
				    <c:forEach items="${lstMachine}" var="machine">
				    	<option value="${machine.id}">${machine.name}</option>
				    </c:forEach>
				    	<option value ="all">Tất cả</option>
					</select>
				</div>
				<div class="row">
					  <div class="btn-group">
					    <button type="button" class="btn btn-success" onclick="btnOeeOnClick()">Hiệu suất máy OEE</button> &nbsp
					    <button type="button" class="btn btn-success" onclick="btnLossedTimeOnClick()">Lãng phí dừng máy</button> &nbsp
					    <button type="button" class="btn btn-success" onclick="btnWorkingTimeOnClick()">Thời gian làm việc/không làm việc</button>
					  </div>
				</div>
				<div class="row">
					<div id="chart_div" style="width: 1610px; height: 720px; border-radius: 10%">
					</div>
				</div>
			</div>
		</main>
		<aside class="right">
		</aside>
	</div>
	<footer>
		<%@ include file="footer.jsp" %>
	</footer>
	<script type="text/javascript">
	google.charts.load('current', {'packages':['corechart']});
	google.charts.setOnLoadCallback(drawVisualization);
	let selectMachine = document.querySelector(".form-select");
	var machineIdSelected = "", machineNameSelected = "";
	  function selectMachineOnChange(){
		machineIdSelected = selectMachine.value;
		machineNameSelected = selectMachine.options[selectMachine.selectedIndex].text;
	  }
	  
	  
	var oeeTrendData;
	var options;
	  function drawVisualization(oeeData, option) {
	    var data = google.visualization.arrayToDataTable(oeeData);
	    var options = option;
	    var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
	    chart.draw(data, options);
	  }
	  
	  function btnOeeOnClick(){
		var start = document.getElementById("startDate").value;
		var end = document.getElementById("endDate").value;
		if(start != "" && end != ""){
			var startDate = new Date(start), endDate = new Date(end);	
			var startFormat = [startDate.getFullYear(),
				               startDate.getMonth() + 1,
				               startDate.getDate()].join('-') +' ' +
				              [startDate.getHours(),
				               startDate.getMinutes(),
				               startDate.getSeconds()].join(':');              
			var endFormat = [endDate.getFullYear(),
				               endDate.getMonth() + 1,
				               endDate.getDate()].join('-') +' ' +
				              [endDate.getHours(),
				               endDate.getMinutes(),
				               endDate.getSeconds()].join(':');
			const paramObj = {
				trendtype : 0,
				mid : "",
				start : startFormat,
				end : endFormat
			}

			const paramJson = JSON.stringify(paramObj);
			console.log(paramJson.toString());	
			
	    	  $.ajax({
	  		    url:"trending-page",
	  		    dataType:"json",
	  		    type:"POST",
	  		    data: {param: paramJson},
	  			//if received a response from the server
	            success: function( dataReceived, textStatus, jqXHR) { 
					 oeeTrendData= dataReceived;
					 options = {
					      title : 'Biểu đồ thống kê Hiệu suất máy CNC từ ngày "'+startFormat+'" đến "'+endFormat+'"',
					      animation:{
					          duration: 1000,
					          easing: 'out',
					          startup: true
					        },
					      vAxes: {
					    	  0 :{title: 'Giá trị (DVT: giờ)'},
					    	  1 :{title: 'OEE (DVT: %)'},
					    	  
					      },
					      hAxis: {
					    	  title: 'Tên máy',
					    	  gridlines: {color: '#333', minSpacing: 20, count: 0}
					      },
					      seriesType: 'bars',
					      series: {
					    	  0: {type: 'bars', targetAxisIndex: 0},
					    	  1: {type: 'bars', targetAxisIndex: 0},
					    	  2: {type: 'line', targetAxisIndex: 1},
					    	  3: {type: 'line', targetAxisIndex: 1}
					      }
					    };
					 try{
		            	 google.charts.setOnLoadCallback(drawVisualization(oeeTrendData, options));
					 }catch(err){
						 alert(err);
					 }

	            },

	            //If there was no resonse from the server
	            error: function(jqXHR, textStatus, errorThrown){
					console.log("error:" + textStatus);
	            },

	            //capture the request before it was sent to server
	            beforeSend: function(jqXHR, settings){

	            },

	            //this is called after the response or error functions are finished
	            //so that we can take some action
	            complete: function(jqXHR, textStatus){

	            }
	  		    
	  		});

		}else alert("Thời gian không hợp lệ");
	  }
	  function btnLossedTimeOnClick(){
		var start = document.getElementById("startDate").value;
		var end = document.getElementById("endDate").value;
		if(start != "" && end != "" && machineIdSelected != ""){
			var startDate = new Date(start), endDate = new Date(end);	
			var startFormat = [startDate.getFullYear(),
				               startDate.getMonth() + 1,
				               startDate.getDate()].join('-') +' ' +
				              [startDate.getHours(),
				               startDate.getMinutes(),
				               startDate.getSeconds()].join(':');              
			var endFormat = [endDate.getFullYear(),
				               endDate.getMonth() + 1,
				               endDate.getDate()].join('-') +' ' +
				              [endDate.getHours(),
				               endDate.getMinutes(),
				               endDate.getSeconds()].join(':');
			const paramObj = {
				trendtype : 1,
				mid : machineIdSelected,
				start : startFormat,
				end : endFormat
			}
			const paramJson = JSON.stringify(paramObj);
	    	  $.ajax({
	  		    url:"trending-page",
	  		    dataType:"json",
	  		    type:"POST",
	  		    data: {param: paramJson},
	  			//if received a response from the server
	            success: function( dataReceived, textStatus, jqXHR) { 
					 oeeTrendData= dataReceived;
					 options = {
					      title : 'Biểu đồ thống kê Thời gian lãng phí máy "'+ machineNameSelected + '" từ ngày "'+startFormat+'" đến "'+endFormat+'"',
					      animation:{
					          duration: 1000,
					          easing: 'out',
					          startup: true
					        },
					      vAxis: {title: 'Giá trị (DVT: giờ)'},
					      hAxis: {title: 'Tên thời gian dừng máy', gridlines: {color: '#333', minSpacing: 20, count: 0}},
					      seriesType: 'bars'
					    };
					 try{
		            	 google.charts.setOnLoadCallback(drawVisualization(oeeTrendData, options));
					 }catch(err){
						 alert("Không tìm thấy dữ liệu cho máy vừa chọn. " + err);
					 }

	            },

	            //If there was no resonse from the server
	            error: function(jqXHR, textStatus, errorThrown){
					console.log("error:" + textStatus);
	            },

	            //capture the request before it was sent to server
	            beforeSend: function(jqXHR, settings){

	            },

	            //this is called after the response or error functions are finished
	            //so that we can take some action
	            complete: function(jqXHR, textStatus){

	            }
	  		    
	  		});

		}else alert("Thời gian không hợp lệ hoặc bạn chưa chọn tên máy.");
	  }
	  
	  
	  function btnWorkingTimeOnClick(){
		  var start = document.getElementById("startDate").value;
			var end = document.getElementById("endDate").value;
			if(start != "" && end != ""){
				var startDate = new Date(start), endDate = new Date(end);	
				var startFormat = [startDate.getFullYear(),
					               startDate.getMonth() + 1,
					               startDate.getDate()].join('-') +' ' +
					              [startDate.getHours(),
					               startDate.getMinutes(),
					               startDate.getSeconds()].join(':');              
				var endFormat = [endDate.getFullYear(),
					               endDate.getMonth() + 1,
					               endDate.getDate()].join('-') +' ' +
					              [endDate.getHours(),
					               endDate.getMinutes(),
					               endDate.getSeconds()].join(':');
				const paramObj = {
					trendtype : 2,
					mid : machineIdSelected,
					start : startFormat,
					end : endFormat
				}
				const paramJson = JSON.stringify(paramObj);
		    	  $.ajax({
		  		    url:"trending-page",
		  		    dataType:"json",
		  		    type:"POST",
		  		    data: {param: paramJson},
		  			//if received a response from the server
		            success: function( dataReceived, textStatus, jqXHR) { 
						 oeeTrendData= dataReceived;
						 options = {
						      title : 'Biểu đồ thống kê Thời gian làm việc/không làm việc từ ngày "'+startFormat+'" đến "'+endFormat+'"',
						      animation:{
						          duration: 1000,
						          easing: 'out',
						          startup: true
						        },
						      vAxis: {title: 'Giá trị (DVT: giờ)'},
						      hAxis: {title: 'Tên máy', gridlines: {color: '#333', minSpacing: 20, count: 0}},
						      seriesType: 'bars'
						    };
						 try{
			            	 google.charts.setOnLoadCallback(drawVisualization(oeeTrendData, options));
						 }catch(err){
							 alert("Không tìm thấy dữ liệu cho máy vừa chọn. " + err);
						 }

		            },

		            //If there was no resonse from the server
		            error: function(jqXHR, textStatus, errorThrown){
						console.log("error:" + textStatus);
		            },

		            //capture the request before it was sent to server
		            beforeSend: function(jqXHR, settings){

		            },

		            //this is called after the response or error functions are finished
		            //so that we can take some action
		            complete: function(jqXHR, textStatus){

		            }
		  		    
		  		});

			}else alert("Thời gian không hợp lệ.");
	  }
	</script>
</body>
<style>
.button {
  transition-duration: 0.4s;
  cursor: pointer;
}
.btn-success:hover {
  background-color: orange;
  color: white;
}
</style>
</html>
