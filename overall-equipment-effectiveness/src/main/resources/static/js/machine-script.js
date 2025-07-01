/**
 * 
 */

var machineCode, machineName, ipAddress, port;

function addNewMachine(){
	machineCode = document.getElementById("machineCode").value;
	machineName = document.getElementById("machineName").value;
	ipAddress = document.getElementById("ipAddress").value;
	port = document.getElementById("port").value;
	
	let object = {
			machineCode: machineCode,
			machineName: machineName,
			ipAddress: ipAddress,
			port: port
	}
	
	var paramJson=JSON.stringify(object);
	
	$.ajax({
		    url:"machine",
		    type:"POST",
			dataType: "html",
		    data: {param: paramJson},
		    success: function(result){							
				document.getElementById("machineBody").innerHTML = "";
				document.getElementById("machineBody").innerHTML = result;
		    	alert("added.");
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

function removeMachine(id){
	$.ajax({
	    url:"machine/remove-machine/" + id,
	    dataType:"html",
	    type:"POST",
	    data: {param: ""},
	    success: function(result){
			document.getElementById("machineBody").innerHTML = "";
			document.getElementById("machineBody").innerHTML = result;
	    	alert("Removed."); 	
	    },
		error: function(jqXHR, textStatus, errorThrown){
			console.log("error:" + textStatus); // Shows 403 if CSRF fails
        },
		beforeSend: function(jqXHR, settings){
			
        },
		complete: function(jqXHR, textStatus){
			console.log("complete:" + textStatus);
        }
	});
}

function showEditMachineForm(id, code, name, ipAddress, port){
	document.getElementById('mId').value = id;	
	document.getElementById('mCode').value = code;
	document.getElementById('mName').value = name;
	document.getElementById('mIpAddress').value = ipAddress;
	document.getElementById('mPort').value = port;
	machineForm.style.display = "block";
}


function closeMachineForm(){
	machineForm.style.display = "none";
}

function saveMachine(){
	var machineId = document.getElementById('mId').value;
	var machineCode = document.getElementById('mCode').value;
	var machineName = document.getElementById('mName').value;
	var ipAddress = document.getElementById('mIpAddress').value;
	var port = document.getElementById('mPort').value;
	
	let object = {
			machineId: machineId,
			machineCode: machineCode,
			machineName: machineName,
			ipAddress: ipAddress,
			port: port
	}	
	var paramJson=JSON.stringify(object);	
	$.ajax({
		    url:"machine/update-machine",
		    dataType:"html",
		    type:"POST",
		    data: {param: paramJson},
		    success: function(result){
				document.getElementById("machineBody").innerHTML = "";
				document.getElementById("machineBody").innerHTML = result;
				machineForm.style.display = "none";
		    	alert("Updated."); 
		    },
			error: function(jqXHR, textStatus, errorThrown){
				console.log("error:" + textStatus); // Shows 403 if CSRF fails
	        },
			beforeSend: function(jqXHR, settings){
				
	        },
			complete: function(jqXHR, textStatus){
				console.log("complete:" + textStatus);
	        }
		});
			
}



