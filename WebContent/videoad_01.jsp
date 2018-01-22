<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>



<script type="text/javascript">

var canvas = document.createElement('canvas');
var ctx= canvas.getContext("2d");
ctx.beginPath();
ctx.arc(100,75,50,0,2*Math.PI);
ctx.stroke();
document.body.append(canvas);

var b64 = canvas.toDataURL().replace("data:image/png;base64,","");
var bin = atob (b64); 
var crc = bin2hex (bin.slice ( - 16 , - 12 ));
document.write('縮短編碼:'+crc);
document.write('<br>');


document.write('<br>');
var x = canvas.offsetLeft;  
var y = canvas.offsetTop;  
document.write('x:'+x+', y:'+y);  


/*
  var name = "alex" + "=";
 var ca = document.cookie.split(';');
 var cookieValue = '';
 
for(var i=0; i<ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0)==' ') c = c.substring(1);
    if (c.indexOf(name) == 0){
    	cookieValue = c.substring(name.length,c.length);
    	
    } 
}


var fileName = "alex.csv";
var data = 'alex\n '+b64;
var blob = new Blob([data], {
   type : "application/octet-stream"
 });
 var href = URL.createObjectURL(blob);
 var link = document.createElement("a");
 document.body.appendChild(link);
 link.href = href;
 link.download = fileName;
 link.click();


*/

//if(cookieValue != ''){
//	document.write(cookieValue);
//	document.write('<br>');
//	document.write(cookieValue == b64);
	
//}else{
//	document.write('no cookie');
//}



//document.cookie = 'alex='+b64; 



</script>
</body>
</html>