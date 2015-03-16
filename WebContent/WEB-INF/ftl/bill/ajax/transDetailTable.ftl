<div class="tablescroll">
<table id="transTable" width="100%" cellspacing="1" cellpadding="3" border="0" class="tablesorter"> 
  	<thead>
    	<tr height="35"> 
				<th class="header">日期</th>
				<th class="header">明細內容</th>
				<th class="header">加值金額</th>
				<th class="header">稅金</th>
				<th class="header">廣告支出回收</th>
				<th class="header">廣告支出</th>
				<th class="header">帳戶餘額</th>
    	</tr> 
	</thead>
	
	<#if billVOList??>
    <tbody>
    
    <#list billVOList.billVOs as vo>
  		<tr class="even">
			<td height="30" class="td02">${vo.transDate!}</td>
			<td height="30" class="td02">${vo.transContents!}</td>			
			<td height="30" class="td01">${vo.saveMoney?string('#,###')!}</td>
			<td height="30" class="td01">${vo.taxMoney?string('#,###')!}</td>
			<td height="30" class="td01">${vo.returnMoney?string('#,###')!}</td>
			<td height="30" class="td01">${vo.adSpentMoney?string('#,###')!}</td>
			<td height="30" class="td01">${vo.remain?string('#,###')!}</td>
 	    </tr>		
	</#list>
 	</tbody>
 	<tfoot>
 	<tr height="35"> 
			<th>　</th>
			<th><span class="header" id="totalCost">總費用</span></th>
			<th><span class="header" id="totalAdd">加值：${billVOList.totalSaveMoney?string('#,###')!}</span></th>
			<th><span class="header" id="totalAdd">稅金：${billVOList.totalTaxMoney?string('#,###')!}</span></th>
			<th><span class="header" id="totalIncome">廣告支出回收：${billVOList.totalReturnMoney?string('#,###')!}</span></th>
			<th><span class="header" id="totalExpense">廣告支出：${billVOList.totalAdSpentMoney?string('#,###')!}</span></th>
			<th><span class="header" id="totalRemain">帳戶餘額：${billVOList.remain?string('#,###')!}</span></th>
   	</tr> 
   	</tfoot>
   	</#if>
</table>
</div>

<form id="excerptFrom" name="excerptFrom" action="transReportDownload.html" method="post">
	<input type="hidden" id="fstartDate" name="startDate" >	
	<input type="hidden" id="fendDate" name="endDate" >
	<input type="hidden" id="transDetail" value="${billVOList!}" >
</form>



