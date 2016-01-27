<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<#assign s=JspTaglibs["/struts-tags"]>

<div class="cont">

<form method="post" id="modifyForm" name="modifyForm" action="accountInfoUpdate.html">

<h2><img src="<@s.url value="/" />html/img/iconcr.gif" hspace="2" vspace="12" align="absmiddle" />修改帳戶資料 </h2>
<div class="grtba"><span class="t_s01">*</span> 為必填欄位
<table width="100%" border="0" cellpadding="0" cellspacing="1" class="tb02">
	<tr>
		<th height="35"><span class="t_s02">*</span> 帳戶名稱</th>
		<td>
			<input type="text" id="accountTitle" name="accountTitle" value="${pfpCustomerInfo.customerInfoTitle!}" autocomplete="off" maxlength="20" style="width:250px">
			<span style="float:right" id="spanAccountTitle">已輸入0字，剩20字</span>
		</td>
	</tr>
	<tr>
	    <th height="35"><span class="t_s02">*</span> 帳戶狀態</th>
	    <td>
	    	<#if pfpCustomerInfo.status !="2">
	    	<input type="radio" id="open" name="status" value="1" <#if pfpCustomerInfo.status=="1">checked</#if> />
	    	開啟　
	    	<input type="radio" id="close" name="status" value="0" <#if pfpCustomerInfo.status=="0">checked</#if> />
	    	關閉
	    	<#else>
	    	停權
	    	</#if>
	    </td>
	</tr>
	<tr>
	    <th height="35"><span class="t_s02">*</span> 帳戶類別</th>
	    <td>
	    	<input type="radio" id="customer" name="category" value="1" <#if pfpCustomerInfo.category=="1">checked</#if> /> 
			個人戶
	    	<input type="radio" id="company" name="category" value="2" <#if pfpCustomerInfo.category=="2">checked</#if> />
	    	公司戶
		</td>
	</tr>
	<tr>
	  <th height="35"><span class="t_s02">*</span> 公司名稱</th>
	  <td>
	  	<input type="text" id="companyTitle" name="companyTitle" value="${pfpCustomerInfo.companyTitle!}" autocomplete="off" maxlength="20" style="width:150px" >
	  </td>
	</tr>
	<tr>
	    <th height="35"><span class="t_s02">*</span> 統一編號</th>
	    <td>
	    	<input type="text" id="registration" name="registration" value="${pfpCustomerInfo.registration!}" autocomplete="off" maxlength="8" style="width:100px" >
	    </td>
	</tr>	
	<tr>
	    <th height="35"><span class="t_s02">*</span> 產業類別</th>
	    <td>
			<select id="industry" name="industry">
			<#list industryList as industry>
				<option value="${industry}" <#if pfpCustomerInfo.industry=="${industry!}">selected</#if> >${industry!}</option>
			</#list>	    
	    	</select>
	    </td>
	</tr>
	<tr>
	    <th height="35"><span class="t_s02">*</span> 廣告連結網址</th>
	    <td>
	    	<input type="text" id="urlAddress" name="urlAddress" value="${pfpCustomerInfo.urlAddress!}" autocomplete="off" maxlength="500" style="width:300px" />
			<input type="hidden" id="urlMsg" />
		</td>
	</tr>
</table>

</div> 

<center style="margin:10px">
	<input type="button" id="save" value="儲存修改" <#if pfpCustomerInfo.status =="2">disabled</#if>> &nbsp; 
	<input type="button" id="cancel" value="取消" <#if pfpCustomerInfo.status =="2">disabled</#if>>             
</center>
     
</form>

<input type="hidden" id="dbStatus" value="${pfpCustomerInfo.status!}" >
<input type="hidden" id="selectStatus" value="${pfpCustomerInfo.status!}" >
