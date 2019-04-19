<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="copyright" content="COPYRIGHT (C) PChome" />
<meta property="og:image" content="http://www.pchome.com.tw/img/pchomelogo.gif">
<link rel="stylesheet" type="text/css" href="<@s.url value="/" />html/open/css/base.css" />
<link rel="stylesheet" type="text/css" href="<@s.url value="/" />html/open/css/layout.css" />



<title>新手上路</title>

<script src="<@s.url value="/" />html/open/js/jquery.min.js"></script>

<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-44528352-1', 'pchome.com.tw');
  ga('send', 'pageview');

</script>

<style>
a{ cursor:pointer}
</style>
</head>

<body>
<div style="background:url(<@s.url value="/" />html/open/img/bg_line.png) repeat-x;heigth:10px"><img src="<@s.url value="/" />html/open/img/10.png"></div>
<div style="width:980px;text-align:center;margin:0 auto">    
  <div style="height:200px;">
        <div id="header-top">
        	<a href="<@s.url value="/" />redirect.html" style="position: absolute; left: 762px; top: -11px;"><img src="<@s.url value="/" />html/open/img/btn_go_s.png"></a>
            <a href="http://www.pchome.com.tw/" class="logo" target="_blank"><img src="<@s.url value="/" />html/open/img/top_pchome.png" alt="PChome" /></a>
            <a href="http://classifieds.pchome.com.tw/" class="logo4c" target="_blank"><img src="<@s.url value="/" />html/open/img/top_4c.png" alt="PChome" /></a> 
      		<img src="<@s.url value="/" />html/open/img/top_tit.png" style="position: absolute; left: 28px; top: 39px;"> </div> 
    </div>
    <div id="content">
        <ul class="list-c" id="navBoxes" >
        <li class="what-we-do"><a href="<@s.url value="/" />faq.html?qid=1&fid=1" target="_blank"></a></li>
        <li class="team"><a href="<@s.url value="/" />faq.html?qid=21&fid=1" target="_blank"></a></li>
        <li class="faq"><a href="<@s.url value="/" />faq.html?qid=3&fid=1" target="_blank"></a></li>
        <li class="contact"><a href="<@s.url value="/" />faq.html?qid=7&fid=3" target="_blank"></a></li>
        </ul>
    </div>

<img src="<@s.url value="/" />html/open/img/foot.png" style="margin-top:-100px"><br> 
<a href="<@s.url value="/" />redirect.html" style="margin-top:10px;display:block"><img src="<@s.url value="/" />html/open/img/btn_go_l.png" width="300" height="75" /></a>
</div>
</body>
