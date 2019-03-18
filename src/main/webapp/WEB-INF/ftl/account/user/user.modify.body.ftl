<#assign s=JspTaglibs["/struts-tags"]>

<div class="cont">
<form method="post" id="modifyForm" name="modifyForm" action="accountUserUpdate.html">
	<h2><img src="<@s.url value="/" />html/img/iconcr.gif" hspace="2" vspace="12" align="absmiddle" />編輯帳戶使用者</h2>
  	<div class="grtba"><span class="t_s01">* </span>為必填欄位
  	
<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
	<tbody>
	<tr>
  		<th height="35"><span class="t_s02">* </span>使用者暱稱</th>
  		<td><input type="text" id="nickName" name="nickName" value="${pfpUser.userName!}" autocomplete="off" maxlength="20"></td>
	</tr>
	<tr>
		<th height="35"><span class="t_s02">* </span>使用者狀態</th>
		<td>
			<#if pfpUser.status=="4" || pfpUser.status=="5">
				<input type="hidden" name="status"  value="${pfpUser.status!}" >帳號未開通
			<#else>
				<#if pfpUser.userId == user_id>
					開啟
				<#else>
					<input type="radio" id="start" name="status"  value="1" <#if pfpUser.status=="1">checked</#if> >開啟　
					<input type="radio" id="close" name="status"  value="0" <#if pfpUser.status=="0">checked</#if> >關閉
				</#if>				
			</#if>				
		</td>
	</tr>
	<tr>
		<th height="35"><span class="t_s02">* </span>使用者權限</th>	
		<td>
			<#if pfpUser.userId == user_id>
				<#list privilegeModel as privilege>	
	  				<#if privilege.privilegeId == pfpUser.privilegeId >
  						${privilege.chName!}
  					</#if>
			    </#list>
			<#else>
				<select id="privilege" name="privilege">
					<#list privilegeModel as privilege>	
		  				<#if privilege.privilegeId != 0 >
		  					<#if privilege.privilegeId == pfpUser.privilegeId >
		  						<option value="${privilege.privilegeId!}" selected>${privilege.chName!}</option>
		  					<#else>
		  						<option value="${privilege.privilegeId!}">${privilege.chName!}</option>
		  					</#if>
		  				</#if>
				    </#list>
			    </select>
			</#if>
			
  		</td>
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
