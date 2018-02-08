<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Fingerprintjs2 test</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <!--  <script src="http://showstg.pchome.com.tw/pfp/pchomeFingerprint.js"></script>--> 
  <!---->   
  <script src="http://alex.pchome.com.tw:8080/akbpfp/pchomeFingerprint.js"></script>
  <script src="http://alex.pchome.com.tw:8080/akbpfp/jquery-1.8.3.min.js"></script>
  <style>
    body{
      font-family: sans-serif;
      max-width: 48em;
      margin: auto;
      padding: 0 5%;
      background: #222;
      color: #fff;
    }

    h1 {
      margin: 2em 0 0;
    }

    p {
      font-size: 1.2em
    }

    button {
      border: none;
      color: #fff;
      font-size: 1.2em;
      background: #27e;
      padding: 0.5em 0.75em 0.6em;
      border-radius: 3px;
      box-shadow: 0 3px 0 #05c;
      outline: none;
    }

    button:active {
      transform: translateY(3px);
      box-shadow: none;
    }

    strong {
      display: block;
      letter-spacing: 1px;
      word-wrap: break-word;
    }

    @media (min-width: 32em) {
      h1 {
        font-size: 4em;
      }
      strong {
        font-size: 1.5em;
      }
    }
  </style>
</head>
<body>
  <div id="container"></div>
  <h1>Fingerprintjs2</h1>
  <p>數位指紋: <strong id="fp"></strong></p>
  <!--  <button type="button" id="btn">Get my fingerprint</button> -->
  <script>
  window.onload=function(){
	var a = new PCHOMEFingerprint().getCanvasFp();
    var c = new PCHOMEFingerprint().x64hash128(a,31);
    document.getElementById("fp").innerHTML = c;
    
    
    var fp = new PCHOMEFingerprint();
     fp.get(function(result) {
       var d2 = new Date();
     	document.getElementById("fp").innerHTML = result;
     });
    
  }
   
  </script>
</body>
</html>
