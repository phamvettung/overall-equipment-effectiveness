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
	const date = new Date(dateTimeElement);
	var dateFormat = [date.getFullYear(),
			               date.getMonth()  + 1,
			               date.getDate()].join('-') +' ' +
			              [date.getHours(),
			               date.getMinutes(),
			               date.getSeconds()].join(':');    
	
	var row = factorTable.insertRow(2);
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
	btnDelete.innerHTML = "<button onclick='deleteRow(this)'>Bỏ chọn</button>";
	
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
		for(var i=2; i< rows.length; i++){
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
		
		var paramJson=JSON.stringify(listOfObjects);
		$.ajax({
		    url:"input-page",
		    dataType:"html",
		    type:"POST",
		    data: {param: paramJson},
		    success: function(result){
		    	factorTable.innerHTML = "";
		    	factorTable.innerHTML += result;
		    	alert("Đã thêm dữ liệu.");
		    }
		});
}

