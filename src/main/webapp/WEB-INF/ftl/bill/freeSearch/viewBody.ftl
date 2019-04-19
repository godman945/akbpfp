<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<div class="cont"> 

<h2>
<div class="cal">帳戶名稱：${customer_info_title}</div>

<img vspace="12" hspace="2" align="absmiddle" src="<@s.url value="/" />html/img/iconcr.gif">廣告禮金明細查詢</h2> 

<@t.insertAttribute name="dateRangeSelect" />
 
	 	<div class="btn_dlex" >
	        <div class="cala01" style="margin-top:20px;">
	        	<input id="IT_dateRange" /> <img src="<@s.url value="/html/img/"/>icon_cal.gif" border="0" align="absmiddle" id="dateRangeSelect"/>
			</div>
	    </div>
 
<div style="clear:both;height:10px"></div> 
 
<div class="grtba"> 

<div class="cont" id="reportTableOut" style="position: relative;">
<div id="reportTable">

<div style="clear:both;line-height:30px;text-align:right;font-size:15px"></div> 
<div style="clear:both;line-height:30px;text-align:left;font-size:12px"></div>

<div id="tableList" class="scrollwrap"></div>

            </div>
		</div>
	</div> 
</div>

