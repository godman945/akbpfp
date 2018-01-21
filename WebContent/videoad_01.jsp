<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
</head>
<body>

<script type="text/javascript">
var canvas = document.createElement('canvas');
var ctx = canvas.getContext('2d');
ctx.fillText("alex",0,0);
var b64 = canvas.toDataURL().replace("data:image/png;base64,","");
document.write('b64:<br>'+b64);
document.write('長度:<br>'+b64.length);
</script>
</body>
</html>