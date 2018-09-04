<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <script src="http://showstg.pchome.com.tw/pfp/pchomeFingerprint.js"></script>
</head>
<body>
  <script>
  window.onload=function(){
	var a = new PCHOMEFingerprint().getCanvasFp();
    var c = new PCHOMEFingerprint().x64hash128(a,1024);
    document.write('<p>數位指紋: <strong id="fp">'+c+'</strong></p>');
  }
  </script>
</body>
</html>
