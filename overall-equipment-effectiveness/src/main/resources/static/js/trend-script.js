/**
 * 
 */

google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawVisualization);

let selectedMachine = document.querySelector(".machine-select");
var machineId = "", machineName = "";

function machineOnChange(){
	machineId = selectedMachine.value;
	machineName = selectedMachine.options[selectedMachine.selectedIndex].text;
}


var options, oeeTrendData;
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
		
		  $.ajax({
		    url:"trend",
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

function btnDowntimeOnClick(){
	var start = document.getElementById("startDate").value;
	var end = document.getElementById("endDate").value;
	if(start != "" && end != "" && machineId != ""){
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
			mid : machineId,
			start : startFormat,
			end : endFormat
		}
		const paramJson = JSON.stringify(paramObj);
    	  $.ajax({
  		    url:"trend",
  		    dataType:"json",
  		    type:"POST",
  		    data: {param: paramJson},
  			//if received a response from the server
            success: function( dataReceived, textStatus, jqXHR) { 
				 oeeTrendData= dataReceived;
				 options = {
				      title : 'Biểu đồ thống kê Thời gian lãng phí máy "'+ machineName + '" từ ngày "'+startFormat+'" đến "'+endFormat+'"',
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


function btnRuntimeOnClick(){
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
			mid : machineId,
			start : startFormat,
			end : endFormat
		}
		const paramJson = JSON.stringify(paramObj);
    	  $.ajax({
  		    url:"trend",
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

