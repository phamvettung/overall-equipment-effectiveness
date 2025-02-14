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
			<div class="container">
				<div class="row">
				
					<div class="row">
						<div class="input-group mb-3">
							<span class="input-group-text">Tên máy</span>
							<input type="text" value="${machineName}" readonly>
						    <span class="input-group-text">Loại dừng máy</span>
						    <select class="form-select" onchange="if (this.selectedIndex) selectFactorOnChange();">
						    <option>None</option>
						    <c:forEach items="${lstFactor}" var="factor">
							  <option value="${factor.id}">${factor.name}</option>
							 </c:forEach>
							</select>
						  </div>
					</div>
					<div class="row">
						<div class="input-group mb-3">
						    <span class="input-group-text">Thời gian</span>
						    <input type="datetime-local" id="dateTime">
						    <span class="input-group-text">Giá trị (phút)</span>
						    <input type="number" value="0" id="timeValue">
						    <button type="button" class="btn btn-warning" onclick="insertToList()">Thêm vào danh sách</button>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="table-responsive" style="max-height: 55vh; overflow-y: scroll; width: 100%">
						    <table class="table table-bordered factorTable" id="factorTableId">
							      <thead>
							        <tr>
							          <th>STT</th>
							          <th>Ngày giờ</th>
							          <th>ID</th>
							          <th>Loại dừng máy</th>
							          <th>Giá trị (phút)</th>
							          <th>Tác vụ</th>
							        </tr>
							      </thead>
							      <tbody id="factorTableBody">		  
							      </tbody>
						    </table>
						  </div>
					<div class = "row">
						<button type="button" class="btn btn-danger" style="width: 150px; margin-left: 1170px" onclick="insertToDB()">Đồng ý</button>
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
	let select = document.querySelector(".form-select");
	let factorTable = document.querySelector(".factorTable");

	var factorIdSelected = "", factorNameSelected = "";
	function selectFactorOnChange(){
		factorIdSelected = select.value; 
		factorNameSelected = select.options[select.selectedIndex].text;
	}

	var count = 1;
	function insertToList(){
		var timeValueElement = document.getElementById("timeValue").value;
		var dateTimeElement = document.getElementById("dateTime").value;
		if(factorIdSelected == ""){
			alert("Bạn chưa chọn Loại dừng máy.");
			return;
		}

		if(factorIdSelected != "f0" && timeValueElement > 450){
			alert("Giá trị nhập liệu phải nhỏ hơn Tổng thời gian sản xuất (450 phút).");
			return;
		}		
		
		const date = new Date(dateTimeElement);
		if(isNaN(date)){
			alert("Thời gian không hợp lệ.");
			return;
		}
		var dateFormat = [date.getFullYear(),
				               date.getMonth()  + 1,
				               date.getDate()].join('-') +' ' +
				              [date.getHours(),
				               date.getMinutes(),
				               date.getSeconds()].join(':');    
		
		var row = factorTable.insertRow(1);
		var order = row.insertCell(0);
		var dateTime = row.insertCell(1);	
		var factorId = row.insertCell(2);
		var factorName = row.insertCell(3);
		var timeValue = row.insertCell(4);
		var btnDelete = row.insertCell(5);

		order.innerHTML = count;
		dateTime.innerHTML = dateFormat;
		factorId.innerHTML = factorIdSelected;	
		factorName.innerHTML = factorNameSelected;
		timeValue.innerHTML = timeValueElement;
		btnDelete.innerHTML = "<button class=\"btn btn-info\" onclick='deleteRow(this)'>Bỏ chọn</button>";
		
		count++; 
	}

	function deleteRow(r) {
		var i = r.parentNode.parentNode.rowIndex;
		factorTable.deleteRow(i);
	}

	function insertToDB(){
		let listOfObjects = new Array();
		var fId, val, dateTime;
		var rows = factorTable.getElementsByTagName("tr");
		console.log("pre" + rows.length);
		for(var i = 1; i < rows.length; i++){
			  var cells = rows[i].getElementsByTagName("td");
			  for (var j=0; j< cells.length ; j++) {
				  if(j == 1){
					    dateTime = cells[j].innerText;
				  }
				  if(j == 2){
					    fId = cells[j].innerText;
				  }
				  if(j == 4){
					    val = cells[j].innerText;
				  }
	
			  }
			  
			  let object = {
						date: dateTime,
						factorId: fId,
						value: val,
			  }
			  listOfObjects.push(object);
		}
		
		console.log(listOfObjects);
		
		var paramJson=JSON.stringify(listOfObjects);
		$.ajax({
		    url:"input-page",
		    dataType:"html",
		    type:"POST",
		    data: {param: paramJson},
		    success: function(result){	
		    	location.reload();
		    	console.log("success" + rows.length);
		    	alert("Đã thêm dữ liệu.");
		    }
		});
	}
	</script>
</body>
</html>