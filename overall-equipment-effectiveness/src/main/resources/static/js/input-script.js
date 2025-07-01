/**
 * 
 */

let downtimeSelect = document.querySelector(".downtime-select");
let inputTable = document.querySelector(".input-table");

var downtimeType = "", downtimeName = "";
function downtimeSelectOnChange(){
	downtimeType = downtimeSelect.value; 
	downtimeName = downtimeSelect.options[downtimeSelect.selectedIndex].text;
}

var count = 1;
function insertToTable(){
	var time = document.getElementById("time").value;
	var dateTime = document.getElementById("dateTime").value;
	
	if(downtimeType == ""){
		alert("Bạn chưa chọn Loại dừng máy.");
		return;
	}

	if(downtimeType != "f0" && time > 450){
		alert("Giá trị nhập liệu phải nhỏ hơn Tổng thời gian sản xuất (450 phút).");
		return;
	}		
	
	const date = new Date(dateTime);
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
	
						   					   
	var row = inputTable.insertRow(1);
	var order = row.insertCell(0);
	var dateTime = row.insertCell(1);	
	var factorId = row.insertCell(2);
	var factorName = row.insertCell(3);
	var timeValue = row.insertCell(4);
	var btnDelete = row.insertCell(5);

	order.innerHTML = count;
	dateTime.innerHTML = dateFormat;
	factorId.innerHTML = downtimeType;	
	factorName.innerHTML = downtimeName;
	timeValue.innerHTML = time;
	btnDelete.innerHTML = "<button class=\"btn btn-info\" onclick='deleteRow(this)'>Remove</button>";
	
	count++; 
}

function deleteRow(r) {
	var i = r.parentNode.parentNode.rowIndex;
	inputTable.deleteRow(i);
}

function saveInput(){
	let listOfObjects = new Array();
	var type, val, dateTime;
	var rows = inputTable.getElementsByTagName("tr");
	
	for(var i = 1; i < rows.length; i++){
		  var cells = rows[i].getElementsByTagName("td");
		  for (var j=0; j< cells.length ; j++) {
			  if(j == 1){
				    dateTime = cells[j].innerText;
			  }
			  if(j == 2){
				    type = cells[j].innerText;
			  }
			  if(j == 4){
				    val = cells[j].innerText;
			  }

		  }
		  
		  let object = {
					date: dateTime,
					type: type,
					value: val,
		  }
		  listOfObjects.push(object);
	}

	var paramJson=JSON.stringify(listOfObjects);
	
	$.ajax({
	    url:"input",
	    type:"POST",
		dataType: "html",
	    data: {param: paramJson},
	    success: function(result){	
	    	location.reload();
	    	console.log("succeeded" + rows.length);
	    	alert("succeeded.");
	    },
		//If there was no resonse from the server
        error: function(jqXHR, textStatus, errorThrown){
			console.log("error:" + textStatus); // Shows 403 if CSRF fails
        },

        //capture the request before it was sent to server
        beforeSend: function(jqXHR, settings){
			
        },

        //this is called after the response or error functions are finished
        //so that we can take some action
        complete: function(jqXHR, textStatus){
			console.log("complete:" + textStatus);
        }
	});
}