<#assign s=JspTaglibs["/struts-tags"]>


<div class="cont"> 
  
<br/>
<form method="post" id="formChange" name="formChange" action="changeAccount.html">
<table id="tableList" width="100%" border="1" cellpadding="0" cellspacing="1" class="tablesorter tb01">
<thead>
  <tr>
	<th style="width:10%" height="35" >帳戶編號</th>
	<th style="width:10%">會員帳號</th>
    <th style="width:10%">帳戶名稱</th>
    <th style="width:10%">帳戶狀態</th>
    <th style="width:10%">付款方式</th>
    <th style="width:10%">帳戶餘額</th>
    <th style="width:10%">負責業務員</th>
    <th style="width:10%">經銷商</th>
    <th style="width:10%">近七日廣告費</th>
    <th style="width:10%">選擇帳戶</th>
  </tr>
</thead>
<tbody>
	<#list vos as vo>
	  <tr>
		<th height="35">${vo.customerInfoId!}</th>
		<th style="width:10%">${vo.memberId!}</th>
	    <th style="width:10%">${vo.customerInfoName!}</th>
	    <th style="width:10%">${vo.customerInfoStatus!}</th>
	    <th style="width:10%">${vo.customerInfoPayType!}</th>
	    <th style="width:10%"><#if vo.customerInfoRemain??>${vo.customerInfoRemain?string('#,###,###')!}</#if></th>
	    <th style="width:10%"><#if vo.managerMemberId?? && vo.managerMemberId != ''>${vo.managerMemberId!} ( ${vo.managerName!} )</#if></th>
	    <th style="width:10%">${vo.pfdCustomerInfoName!}</th>
	    <th style="width:10%"><#if vo.oneWeekAdCost??>${vo.oneWeekAdCost?string('#,###,###')!}</#if></th>
	    <th style="width:10%"><input type="button" value="切換帳戶" onClick="changeAccount('${vo.customerInfoId!}');" /></th>
	  </tr>	
  	</#list>
</tbody>	
</table>
<input type="hidden" id="accountId" name="accountId" />
</form>
</div>