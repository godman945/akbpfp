<#assign s=JspTaglibs["/struts-tags"]>

<div class="cont">

<form method="post" id="modifyForm" name="modifyForm" action="accountAdmUpdate.html">

<h2><img src="<@s.url value="/" />html/img/iconcr.gif" hspace="2" vspace="12" align="absmiddle" />帳戶總管理者</h2>
<div class="grtba"><span class="t_s01">* </span>為必填欄位

<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
<tbody>
	<tr>
  		<th height="35"><span class="t_s02">* </span>使用者暱稱</th>
  		<td><input type="text" id="nickName" name="nickName" value="${pfpUser.userName!}" autocomplete="off" maxlength="20"></td>
	</tr>
	<tr>
    	<th height="35">使用者狀態</th>
    	<#list userStatus as userStatus>
			<#if pfpUser.status == userStatus.statusId>
				<td>${userStatus.chName!}</td>
			</#if>			
		</#list>
	</tr>
	<tr>
    	<th height="35">使用者權限</th>
		<#list privilegeModel as privilege>	
			<#if pfpUser.privilegeId==privilege.privilegeId>
				<td>${privilege.chName!}</td>
			</#if>
		</#list>
	</tr>
</tbody>
</table>

<center style="margin:10px">
	<input type="button" id="save" value="儲存修改">&nbsp; 
	<input type="button" id="cancel" value="取消"><br><br>
</center> 
	
</div> 
<input type="hidden" id="userId" name="userId" value="${pfpUser.userId!}">
</form>
