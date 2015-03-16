<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>



<input type="hidden" id="adType" name="adType" value="">	
<input type="hidden" id="adSearchWay" name="adSearchWay" value="">	
<input type="hidden" id="searchText" name="searchText" value="">	
<input type="hidden" id="adShowWay" name="adShowWay" value="">	
<input type="hidden" id="btnSearchDo" name="btnSearchDo" value="">	
<input type="hidden" id="btnSearchReset" name="btnSearchReset" value="">	




<span class="pages">

<img style="vertical-align:middle" id="fpage" src="<@s.url value="/html/img/"/>page_first.gif" />
<img style="vertical-align:middle" id="ppage" src="<@s.url value="/html/img/"/>page_pre.gif" />
${page}/${totalPage}
<img style="vertical-align:middle" id="npage" src="<@s.url value="/html/img/"/>page_next.gif" />
<img style="vertical-align:middle" id="epage" src="<@s.url value="/html/img/"/>page_end.gif" />

&nbsp&nbsp

顯示 

<select id="pageSizeSelect" style="vertical-align:middle" > 
    
    <option>10</option> 
    <option>20</option> 
    <option>50</option> 
    <option>100</option> 
    <option>200</option> 
  </select> 
行
</span> 






<#if (tableDataList?size > 0) > 
<div style="clear:both;line-height:30px;text-align:right;font-size:15px"><a href="#" id="fieldSelect">自訂欄位</a></div> 


<table id="excerptTable" width="100%" border="0" cellpadding="0" cellspacing="1" class="tablesorter" > 
  	<thead>
    	<tr height="35"> 
    		<#list tableHeadList as th>
				<th>${th}</th>
    		</#list>
    	</tr> 
	</thead>
     
     <tbody>
     <#list tableDataList as td>
		 <tr>
		<#list td as tdin>
			<td height="30">${tdin}</td>
 		</#list>
 	    </tr>
 	</#list>
 	</tbody>
 	<tfoot>
 	<tr height="35"> 
   		<#list tableDataTotalList as th>
			<th>${th}</th>
    	</#list>
   	</tr> 
   	</tfoot>
 
 	
  
</table> 
<#else>
<div style="clear:both;line-height:30px;text-align:right;font-size:15px"></div> 
<div style="clear:both;line-height:30px;text-align:left;font-size:12px"></div>
<table id="excerptTable" width="100%" border="0" cellpadding="0" cellspacing="1" class="tablesorter" > 
  	<tfoot>
    	<tr height="35"> 
    		
				<td>沒有資料</td>
    		
    	</tr> 
		</tfoot>
     
    
 
 	
  
</table> 
</#if>
<form id="excerptFrom" name="excerptFrom" action="reportCostDownload.html" method="post">
	<input type="hidden" id="formPage" name="page" value="${page}">	
	<input type="hidden" id="fpageSize" name="pageSize" value="${pageSize}">
	<input type="hidden" id="ftotalPage" name="totalPage" value="${totalPage}">
	<input type="hidden" id="foptionSelect" name="optionSelect" value="${optionSelect}">
	<input type="hidden" id="foptionNotSelect" name="optionNotSelect" value="${optionNotSelect}">
	<input type="hidden" id="fstartDate" name="startDate" value="${startDate}">
	<input type="hidden" id="fendDate" name="endDate" value="${endDate}">
	<input type="hidden" id="fadType" name="adType" value="${adType}">
	<input type="hidden" id="fadSearchWay" name="adSearchWay" value="${adSearchWay}">
	<input type="hidden" id="fadShowWay" name="adShowWay" value="${adShowWay}">
	<input type="hidden" id="fsearchText" name="searchText" value="${searchText}">
	<input type="hidden" id="fsearchId" name="searchId" value="${searchId}">
	<input type="hidden" id="downloadFlag" name="downloadFlag" value="yes">
	<input type="hidden" id="contentPath" name="contentPath" value="<@s.url value="/html/img/"/>">
	
</form>
