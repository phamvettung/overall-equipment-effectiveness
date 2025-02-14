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
					      title : 'Biểu đồ Thống kê Hiệu suất máy CNC theo thời gian',
					      vAxis: {title: 'Giá trị (DVT: Giờ)'},
					      hAxis: {title: 'Tên máy'},
					      seriesType: 'bars',
					      series: {2: {type: 'line'}}
					    };
	            	 google.charts.setOnLoadCallback(drawVisualization(oeeTrendData, options));
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
					      title : 'Biểu đồ Thống kê Thời gian lãng phí máy '+ machineNameSelected + ' theo thời gian',
					      vAxis: {title: 'Giá trị (DVT: Giờ)'},
					      hAxis: {title: 'Tên thời gian dừng máy'},
					      seriesType: 'bars'
					    };
	            	 google.charts.setOnLoadCallback(drawVisualization(oeeTrendData, options));
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
		alert("Sorry! The feature do not supported.");
	  }