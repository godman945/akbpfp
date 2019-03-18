<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<span style="float:left"> 
<table border="0" cellspacing="3" cellpadding="0" class="srchtb"> 
  <tr> 
	  <td>
		    <select id="adShowWay"> 
	    	    <option selected="selected" value="adshow_all">全部</option> 
	      		<option value="adshow_general">找東西廣告</option> 
	      		<option value="adshow_content">PChome頻道廣告</option> 
	      	</select>
	    </td> 
	   <td>
	     	<select id="adSearchWay"> 
	        	<option selected="selected" value="adsearch_include">字詞包含</option> 
	        	<option value="adsearch_begin">開頭文字是</option> 
	        	<option value="adsearch_same">完全符合</option> 
	      	</select>
	    </td> 
	    <td>
	    	<input type="text" id="searchText"/></td>
	
	    <td>
	    	<img src="<@s.url value="/html/img/"/>srchbtn.gif" border="0" id="btnSearchDo"/>
	    </td>
	    <td>
	    	<a href="#" id="btnSearchReset">重設</a>
	    </td> 
    
    </tr> 
</table> 
</span>


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


<table id="excerptTable" border="0" cellpadding="0" cellspacing="1" class="tablesorter" > 
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
<table id="excerptTable"  border="0" cellpadding="0" cellspacing="1" class="tablesorter" > 
  	<tfoot>
    	<tr height="35"> 
    		
				<td>沒有資料</td>
    		
    	</tr> 
		</tfoot>
     
    
 
 	
  
</table> 
</#if>
<form id="excerptFrom" name="excerptFrom" action="reportUrlDownload.html" method="post">
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
