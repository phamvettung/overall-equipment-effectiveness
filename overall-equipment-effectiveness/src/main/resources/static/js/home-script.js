/**
 * 
 */
let circularProgressAll = document.querySelectorAll(".progress-circle");
var oeeValueAll = document.querySelectorAll(".oee-value");

var colorType = '';
var oeeValueConv = 0;

for(var i = 0; i < circularProgressAll.length; i++){
	let oeeValue = oeeValueAll[i].value;
	
	if(oeeValue < 0)
		oeeValueConv = 0;
	else if(oeeValue > 100)
		oeeValueConv = 100;
	else 
		oeeValueConv = oeeValue;
			
	if(oeeValueConv >= 0 && oeeValueConv <= 30)
		colorType = '#ff0000';
	else if(oeeValueConv > 30 && oeeValueConv <= 50)
		colorType = '#ff9933';
	else if(oeeValueConv > 50 && oeeValueConv <= 70)
		colorType = '#ffff00';
	else if(oeeValueConv > 70 && oeeValueConv <= 90)
		colorType = '#66ccff';
	else if(oeeValueConv > 90 && oeeValueConv <= 100)
		colorType = '#00ff00';
	circularProgressAll[i].style.background = 'conic-gradient('+ colorType +' '+ (oeeValueConv*3.6).toString()+'deg, #ededed 0deg)';
}


//Animation
var mainBox = document.getElementById('mainBox');
var width = mainBox.clientWidth;
var height = mainBox.clientHeight - 200;
var quantity = 40;
var bordersArr = ['50%', '20%'];
var blursArr = ['0','50%'];
var colorsArr = ['#e6ffff', '#00ffff', '#ffe6e6'];

function createAnimation(){
	for(var i = 0; i < quantity; i++){
		var randomLeft = Math.floor(Math.random()*width);
		var randomTop = Math.floor(Math.random()*height/2);
		var randomColor = Math.floor(Math.random()*3)
		var randomWidth = Math.floor(Math.random()*5) + 5;
		var randomHeight = Math.floor(Math.random()*5) + 5;
		var randomBorder = Math.floor(Math.random()*2);
		var randomBlur = Math.floor(Math.random()*2);
		var randomTimeAnimation = Math.floor(Math.random()*12) + 8;
		
		
		var div = document.createElement("div");
		div.style.position = 'absolute';
		div.style.backgroundColor = colorsArr[randomColor];
		div.style.width = randomWidth + 'px';
		div.style.height = randomHeight + 'px';
		div.style.marginLeft = randomLeft + 'px';
		div.style.marginTop = randomTop + 'px';
		div.style.borderRadius = bordersArr[randomBorder];
		div.style.filter = 'blur('+ blursArr[randomBlur] +')';
		div.style.animation = 'move '+ randomTimeAnimation+'s ease-in infinite';
		mainBox.appendChild(div);
	}
}
createAnimation();
