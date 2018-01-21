<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<script type="text/javascript">
var canvas = document.createElement('canvas');
var ctx = canvas.getContext('2d');
ctx.fillText("alex",0,0);
var b64 = canvas.toDataURL().replace("data:image/png;base64,","");
document.write('ID  ------------------------------<br>'+b64);
document.write('<br>');


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

if(cookieValue != ''){
	document.write(cookieValue);
	document.write('<br>');
	document.write(cookieValue == b64);
}else{
	document.write('no cookie');
}



document.cookie = 'alex='+b64; 



</script>
</body>
</html>