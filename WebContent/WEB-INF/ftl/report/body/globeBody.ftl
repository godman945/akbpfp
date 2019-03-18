<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>


<div  class="cont"> 
 
    <div class="cal">帳戶名稱：${customer_info_title!}</div>
 
	<h2><img src="<@s.url value="/html/img/"/>iconcr.gif" hspace="2" vspace="12" align="absmiddle" />${reportTitle}</h2> 

    <div class="btn_dlex" >
        <a id="download">下載此報表</a>
        <div class="cala01" style="margin-top:20px;">
        	<input id="IT_dateRange" /> <img src="http://show.pchome.com.tw/html/img/icon_cal.gif" border="0" align="absmiddle" id="seDateSelect"/>
		</div>
    </div> 
 
	<div style="clear:both;height:10px"></div> 
 
	<div  class="grtba"> 


		<div id="reportTableOut" class="cont">
			<div id="reportTable" >
				<@t.insertAttribute name="reportTable" />
			</div> 
		</div>

       
         
		<h4>報表圖表 <span><a href="#" id="aReportChart">關閉</a></span></h4> 


		<div id="reportChart">

			<@t.insertAttribute name="chartTable" />
	
		</div>
		
	
 
	</div> 
 
	</div> 

	




<@t.insertAttribute name="optiontransferselect" />
<@t.insertAttribute name="seDateSelect" />


