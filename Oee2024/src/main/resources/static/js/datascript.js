let selectMachine = document.querySelector(".select-machine");


		var ajaxRequest;
		var machineIdSelected, machineNameSelected;
		function selectMachineOnChange(){
			machineIdSelected = selectMachine.value;
			machineNameSelected = selectMachine.options[selectMachine.selectedIndex].text;
		}
		
		function btnSearchOnClick(){
			var start = document.getElementById("startDate").value;
			var end = document.getElementById("endDate").value;
			
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

