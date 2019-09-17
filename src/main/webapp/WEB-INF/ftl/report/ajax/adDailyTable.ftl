<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<span style="float:left"> 
<table border="0" cellspacing="3" cellpadding="0" class="srchtb"> 
  <tr> 
  
	   <td>
		    <select id="adShowWay"> 
		        <#list adShowWayMap?keys as skey>
		  		    <option value="${skey}">${adShowWayMap[skey]}</option>
		  	    </#list>
	      	</select>
	    </td>

	   <td>
		    <select id="adPvclkDevice"> 
		        <#list adPvclkDeviceMap?keys as skey>
		  		    <option value="${skey}">${adPvclkDeviceMap[skey]}</option>
		  	    </#list>
	      	</select>
	    </td>

	    <td>
	     	<select id="adSearchWay"> 
		        <#list adSearchWayMap?keys as skey>
		  		    <option value="${skey}">${adSearchWayMap[skey]}</option>
		  	    </#list>
	      	</select>
	    </td>
	    
	    <td>
	    	<input type="text" id="searchText"/>
	    </td>
	    
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
    <#list pageSizeList as ps>
        <option>${ps}</option>
    </#list>
</select> 
行
</span> 

<#if (tableDataList?size > 0) > 

<br>

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
         <#assign index = 0>
		 <tr height="30">
		<#list td as tdin>
			<#if index = 6 || index = 7 || index = 8  || index = 10 || index = 12>
				<td align="${align_data[index]}">NT$ ${tdin}</td>	
			<#elseif index = 5 || index = 11 || index = 13>
				<td align="${align_data[index]}">${tdin}%</td>
			<#else>	
				<td align="${align_data[index]}">${tdin}</td>
			</#if>
			<#assign index = index + 1>
 		</#list>
 	    </tr>
 	</#list>
 	</tbody>

 	<tfoot>
 	<tr height="35">
   		<#list tableDataTotalList as th>
			<#if th_index = 6 || th_index = 7 || th_index = 8 || th_index = 10 || th_index = 12>
				<th height="30" align="right">NT$ ${th}</th>
			<#elseif th_index = 5 || th_index = 11 || th_index = 13>
				<th height="30" align="right">${th}%</th>
			<#elseif th_index = 0>
				<th height="30" align="center">${th}</th>
			<#else>	
				<th height="30" align="right">${th}</th>
			</#if>
    	</#list>
   	</tr> 
   	</tfoot>

</table> 
<#else>
<div style="clear:both;line-height:30px;text-align:right;font-size:15px"></div> 
<div style="clear:both;line-height:30px;text-align:left;font-size:12px"></div>
<table id="excerptTable" border="0" cellpadding="0" cellspacing="1" class="tablesorter" > 
  	<tfoot>
    	<tr height="35"> 
    		
				<td>無此廣告成效</td>
    		
    	</tr> 
		</tfoot>
     
    
 
 	
  
</table> 
</#if>
<form id="excerptFrom" name="excerptFrom" action="reportAdDailyDownload.html" method="post">
	<input type="hidden" id="formPage" name="page" value="${page}">	
	<input type="hidden" id="fpageSize" name="pageSize" value="${pageSize}">
	<input type="hidden" id="ftotalPage" name="totalPage" value="${totalPage}">
	<input type="hidden" id="foptionSelect" name="optionSelect" value="${optionSelect}">
	<input type="hidden" id="foptionNotSelect" name="optionNotSelect" value="${optionNotSelect}">
	<input type="hidden" id="fstartDate" name="startDate" value="${startDate}">
	<input type="hidden" id="fendDate" name="endDate" value="${endDate}">
	<input type="hidden" id="fadPvclkDevice" name="adPvclkDevice" value="${adPvclkDevice}">
	<input type="hidden" id="fadType" name="adType" value="${adType}">
	<input type="hidden" id="fadSearchWay" name="adSearchWay" value="${adSearchWay}">
	<input type="hidden" id="fadShowWay" name="adShowWay" value="${adShowWay}">
	<input type="hidden" id="fsearchText" name="searchText" value="${searchText}">
	<input type="hidden" id="fsearchId" name="searchId" value="${searchId}">
	<input type="hidden" id="downloadFlag" name="downloadFlag" value="yes">
	<input type="hidden" id="contentPath" name="contentPath" value="<@s.url value="/html/img/"/>">
	
</form>