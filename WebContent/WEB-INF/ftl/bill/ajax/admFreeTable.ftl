<div class="tablescroll">
<table id="transTable" width="100%" cellspacing="1" cellpadding="3" border="0" class="tablesorter"> 
  	<thead>
    	<tr height="35"> 
				<th class="header" width="15%" >禮金啟用日期</th>
				<th class="header" width="20%" >活動日期</th>
				<th class="header" width="20%" >活動名稱</th>
				<th class="header" width="20%" >活動內容</th>
				<th class="header" width="15%" >贈送禮金</th>
				<th class="header" width="15%" >禮金失效日期</th>
    	</tr> 
	</thead>
	
	<#if admFreeVOList??>
    <tbody>
    
    <#list admFreeVOList as vo>
  		<tr class="even">
			<td height="30" class="td03">${vo.recordDate!}</td>
			<td height="30" class="td03">${vo.actionStartDate!} ~ ${vo.actionEndDate!}</td>			
			<td height="30" class="td03">${vo.actionName!}</td>
			<td height="30" class="td03">${vo.note!}</td>
			<td height="30" class="td01">NT$ ${vo.giftMoney?string('#,###')!}</td>
			<td height="30" class="td03">${vo.inviledDate!}</td>
 	    </tr>		
	</#list>
 	</tbody>
   	</#if>
</table>
</div>



