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
			<div class = "container-fluid">
				<div class = "row">
					<div class = "row">
						<div class="col-sm-3 p-3 text-black">
							<div class="input-group mb-3">
							    <span class="input-group-text">Chọn tên máy</span>
							    <select class="form-select select-machine" 
							    	onchange="if (this.selectedIndex) selectMachineOnChange();">
							    	<option>None</option>
							    <c:forEach items="${lstMachine}" var="machine">
							    	<option value="${machine.id}">${machine.name}</option>
							    </c:forEach>
							    	<option value="all">Tất cả</option>
								</select>
							</div>
						</div>
					    <div class="col-sm-8 p-8 text-black">
					    	<div class="input-group mb-3" style="margin-top: 17px">
							    <span class="input-group-text">Ngày bắt đầu</span>
							    <input type="datetime-local" id="startDate">
							    <span class="input-group-text">Ngày kết thúc</span>
							    <input type="datetime-local" id="endDate">
							    <button type="button" onclick="btnSearchOnClick()" 
							    style="background-color: green;
							     color: white;
							     width: 100px;
							     height: 40px;
							     border-bottom-right-radius: 5px;
							     border-top-right-radius: 5px;
							     border-width: 0px">Đồng ý</button>
							</div>
					    </div>
					</div>
					<div class = "row">
						
					</div>
				</div>
				<div class="row">
					<div class="table-responsive" style = "max-height: 65vh; overflow-y: scroll; width: 100%">
						    <table class="table table-bordered" id="oeeTable">
						      <thead>
						        <tr>
						          <th>Ngày</th>
						          <th>Tên máy</th>
						          <c:forEach items="${lstFactor}" var="factor">
						          	<th abbr="${factor.id}">${factor.name} (phút)</th>
						          </c:forEach>
						          <th>Số ngày nhập liệu</th>
						          <th>A(H1) %</th>
						          <th>P(H2) %</th>
						          <th>Q(H3) %</th>
						          <th>Oee %</th>
						          <th>Tổng TG dừng máy (phút)</th>
						          <th>Tổng TG máy làm việc (phút)</th>
						        </tr>
						      </thead>
						      <tbody id="oeeRows">
						        <c:forEach items="${lstOee}" var="oee">
						        		<tr onclick="trGetDateOnClick(this)">
								          <td onclick="tdGetFactorIdOnClick(this)" ondblclick="redirectToDetail()">${oee.date}</td>
								          <td onclick="tdGetFactorIdOnClick(this)" ondblclick="redirectToDetail()">${oee.name}</td>
								          <td onclick="tdGetFactorIdOnClick(this)" ondblclick="redirectToDetail()">${oee.f00}</td>
								          <td onclick="tdGetFactorIdOnClick(this)" ondblclick="redirectToDetail()">${oee.f1}</td>
								          <td onclick="tdGetFactorIdOnClick(this)" ondblclick="redirectToDetail()">${oee.f10}</td>
								          <td onclick="tdGetFactorIdOnClick(this)" ondblclick="redirectToDetail()">${oee.f2}</td>
								          <td onclick="tdGetFactorIdOnClick(this)" ondblclick="redirectToDetail()">${oee.f3}</td>
								          <td onclick="tdGetFactorIdOnClick(this)" ondblclick="redirectToDetail()">${oee.f4}</td>
								          <td onclick="tdGetFactorIdOnClick(this)" ondblclick="redirectToDetail()">${oee.f5}</td>
								          <td onclick="tdGetFactorIdOnClick(this)" ondblclick="redirectToDetail()">${oee.f6}</td>
								          <td onclick="tdGetFactorIdOnClick(this)" ondblclick="redirectToDetail()">${oee.f7}</td>
								          <td onclick="tdGetFactorIdOnClick(this)" ondblclick="redirectToDetail()">${oee.f8}</td>
								          <td onclick="tdGetFactorIdOnClick(this)" ondblclick="redirectToDetail()">${oee.f9}</td>
								          <td onclick="tdGetFactorIdOnClick(this)" ondblclick="redirectToDetail()">${oee.din}</td>
								          <td onclick="tdGetFactorIdOnClick(this)" ondblclick="redirectToDetail()">${oee.a}</td>
								          <td onclick="tdGetFactorIdOnClick(this)" ondblclick="redirectToDetail()">${oee.p}</td>
								          <td onclick="tdGetFactorIdOnClick(this)" ondblclick="redirectToDetail()">${oee.q}</td>
								          <td onclick="tdGetFactorIdOnClick(this)" ondblclick="redirectToDetail()">${oee.oee}</td>
								          <td onclick="tdGetFactorIdOnClick(this)" ondblclick="redirectToDetail()">${oee.downTime}</td>
								          <td onclick="tdGetFactorIdOnClick(this)" ondblclick="redirectToDetail()">${oee.runTime}</td>
						        		</tr>
						        	</c:forEach>      
						      </tbody>
						    </table>
						  </div>	
						  <div class="container mt-3 detail-form" id="detailForm">
							  	<div class="detail-content">
								  <h2>CHI TIẾT DỪNG MÁY</h2>
								  <p>Xem chi tiết các mốc thời gian dừng máy theo ngày</p>
								  <table class="table">
								    <thead>
								      <tr>
								        <th>Id</th>
								        <th>Tên máy</th>
								        <th>Ngày</th>
								        <th>Thời gian</th>
								        <th>Loại dừng máy</th>
								        <th>Giá trị (phút)</th>			
								        <th>Tác vụ</th>			       							        
								      </tr>
								    </thead>
								    <tbody id="inputRows">
								      <tr>
								      </tr>      							      
								    </tbody>
								  </table>
								  <button type="button" class="btn btn-warning" onclick="closeDetailForm()">Đóng</button>
							</div>
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
	
	
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script type="text/javascript">
	let selectMachine = document.querySelector(".select-machine");


	var ajaxRequest;
	var machineIdSelected = "", machineNameSelected = "";
	function selectMachineOnChange(){
		machineIdSelected = selectMachine.value;
		machineNameSelected = selectMachine.options[selectMachine.selectedIndex].text;
	}
	
	function btnSearchOnClick(){
		if(machineIdSelected == ""){
			alert("Bạn chưa chọn tên máy");
		}
		var start = document.getElementById("startDate").value;
		var end = document.getElementById("endDate").value;
		if(start == "" || end == ""){
			alert("Thời gian không hợp lệ");
			return;
		}
		
		var startDate = new Date(start), endDate = new Date(end);	
		var startFormat = [startDate.getFullYear(),
			               startDate.getMonth()  + 1,
			               startDate.getDate()].join('-') +' ' +
			              [startDate.getHours(),
			               startDate.getMinutes(),
			               startDate.getSeconds()].join(':');              
		var endFormat = [endDate.getFullYear(),
			               endDate.getMonth()  + 1,
			               endDate.getDate()].join('-') +' ' +
			              [endDate.getHours(),
			               endDate.getMinutes(),
			               endDate.getSeconds()].join(':');		
		
		ajaxRequest = getXMLHttpRequest();
		if (!ajaxRequest) {
			document.getElementById("oeeTable").innerHTML = "Request error!";
			return;
		}
		
		var url = "data-page";
		ajaxRequest.onreadystatechange = ajaxResponse;
		ajaxRequest.open("POST", url, true);
		ajaxRequest.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		ajaxRequest.send("mid=" + machineIdSelected + "&start=" + startFormat + "&end=" + endFormat + "");
	}
	
	function ajaxResponse() {
			if (ajaxRequest.readyState != 4) {
				return;
			} else {
				if (ajaxRequest.status == 200) {
					document.getElementById("oeeRows").innerHTML = "";
					document.getElementById("oeeRows").innerHTML = ajaxRequest.responseText;
				} else {
					alert("Request failed: " + ajaxRequest.statusText);
				}
			}
		}
	
	function getXMLHttpRequest() {
		var request, err;
		try {
			request = new XMLHttpRequest();
		} catch (err) {
			try {
				request = new ActiveXObject("MSXML2.XMLHttp.6.0");
			} catch (err) {
				try {
					request = new ActiveXObject("MSXML2.XMLHttp.3.0");
				} catch (err) {
					request = false;
				}
			}
		}
		return request;
	}
	
	//The program handle detail feature.
	var oeeTable = document.getElementById("oeeTable");
	var detailForm = document.getElementById("detailForm");
	var i = -1, j = -1;
	var factorId, date;
	function trGetDateOnClick(x){
		i = x.rowIndex;
		var rows = oeeTable.getElementsByTagName("tr");
		var cells = rows[i].getElementsByTagName("td");
		date = cells[0].innerText;
	}
	function tdGetFactorIdOnClick(y){
		j = y.cellIndex;
		var rows = oeeTable.getElementsByTagName("tr");
		var cells = rows[0].getElementsByTagName("th");
		factorId = cells[j].abbr
	}
	function redirectToDetail(){
		if (machineIdSelected == null) {
			alert("Bạn chưa chọn Tên máy!");
			return;
		}			
		detailForm.style.display = "block";
		getInputTable(date, factorId, machineIdSelected);
	}


	function closeDetailForm(){
		detailForm.style.display = "none";
	}
	
	var ajaxRequestDeital;
	function getInputTable(date, fid, mid) {
		ajaxRequestDeital = getXMLHttpRequest();
		if (!ajaxRequestDeital) {
			document.getElementById("detailForm").innerHTML = "Request error!";
			return;
		}
		var url = "data-page/detail-form";
		ajaxRequestDeital.onreadystatechange = ajaxResponseDetail;
		ajaxRequestDeital.open("POST", url, true);
		ajaxRequestDeital.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		ajaxRequestDeital.send("date=" + date + "&fid=" + fid + "&mid=" + mid + "");
				
	}
	
	function ajaxResponseDetail() {
		if (ajaxRequestDeital.readyState != 4) {
			return;
		} else {
			if (ajaxRequestDeital.status == 200) {				
				document.getElementById("inputRows").innerHTML = "";
				document.getElementById("inputRows").innerHTML += ajaxRequestDeital.responseText;
			} else {
				alert("Request failed: " + ajaxRequestDeital.statusText);
			}
		}
	}	
	
	

	function deleteInputById(id){
		$.ajax({
		    url:"data-page/delete-input/" + id,
		    dataType:"html",
		    type:"POST",
		    data: {param: ""},
		    success: function(result){
		    	alert("Đã xóa. Hãy tải lại trang web để cập nhật.");
		    	closeDetailForm();
		    	
		    }
		});
	}
	
	</script>
</body>
<style>
	/* The Form (background) */
	.detail-form {
	  display: none; /* Hidden by default */
	  position: fixed; /* Stay in place */
	  z-index: 1; /* Sit on top */
	  padding-top: 100px; /* Location of the box */
	  left: 0;
	  top: 0;
	  width: 100%; /* Full width */
	  height: 100%; /* Full height */
	  overflow: auto; /* Enable scroll if needed */
	  background-color: rgb(0,0,0); /* Fallback color */
	  background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
	}
	
	/* Modal Content */
	.detail-content {
	  background-color: #fefefe;
	  margin: auto;
	  padding: 20px;
	  border: 1px solid #888;
	  width: 80%;
	}
</style>
</html>