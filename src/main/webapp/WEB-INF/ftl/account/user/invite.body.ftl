<#assign s=JspTaglibs["/struts-tags"]>

<div class="cont">

<form method="post" id="inviteForm" name="inviteForm" action="inviteUser.html">

<h2>
<div class="cal">帳戶名稱：${customer_info_title}</div>
<img src="<@s.url value="/" />html/img/iconcr.gif" hspace="2" vspace="12" align="absmiddle" />新增帳戶使用者        
</h2>
<div class="grtba">

<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
<tbody>
	<tr>
  		<th height="35"><span class="t_s02">* </span>使用者mail信箱</th>
		<td><input type="text" id="memberEmail" name="memberEmail" value="${memberEmail!}" autocomplete="off" maxlength="50" style="width:250px;"> 
			<input type="button" id="checkEmail" name="checkEmail" value="檢查Email" >
		</td>
	</tr>
	<tr>
	<th height="35"><span class="t_s02">* </span>使用者暱稱<span></span></th>
		<td><input type="text" id="nickName" name="nickName" value="${nickName!}" autocomplete="off" maxlength="20" ></td>
	</tr>
	<tr>
		<th height="35"><span class="t_s02">* </span>使用者權限</th>
  		<td>  
	  		<select id="privilege" name="privilege">
	  		    <#list privilegeOptionsMap?keys as key>
                <option value="${key}" <#if privilege == key>selected</#if>>${privilegeOptionsMap[key]}</option>
		        </#list>
  			</select>
  		</td>
	</tr>
</tbody>
</table>

<center style="margin:10px">
<input type="button" id="invite" value="新增並寄發邀請通知" >&nbsp; 
<input type="button" id="cancel" value="取消" ><br><br>
</center> 

</div> 

</form>

<input type="hidden" id="accountId" value="${customer_info_id!}" />
<input type="hidden" id="messageId" value="${message!}">
