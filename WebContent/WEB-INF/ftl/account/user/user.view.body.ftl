<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<#assign s=JspTaglibs["/struts-tags"]>

<div class="cont">

<div id="dialog"></div>

<h2>
<div class="cal">帳戶名稱：${customer_info_title}</div>
<img src="<@s.url value="/" />html/img/iconcr.gif" hspace="2" vspace="12" align="absmiddle" />帳戶使用者管理
</h2>

<form method="post" id="userForm" name="userForm" action="accountUserSerach.html">

<div class="grtba">
<table cellspacing="3" cellpadding="0" border="0" class="srchtb" style="float:left;margin-bottom:10px">
<tbody>
<tr>
    <td>
        <select name="status" id="status">
        	<option value="" selected>全部</option>
        	<option value="0">關閉</option>
            <option value="1">開啟</option>
            <option value="2">邀請已過期</option>
            <option value="3">尚未接受邀請</option>
        </select>
    </td>
    <td>
        <input type="text" id="keyword" name="keyword" >
    </td>
    <td><a href="#" id="search"><img border="0" src="<@s.url value="/" />html/img/srchbtn.gif"></a></td>
</tr>
</tbody>
</table>
	<div style="float:right;margin-top: 10px;"><input type="button" id="userInvite" name="inviteUser" value="新增使用者"></div>

	            
<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb01">
<tbody>
<tr>
    <th height="35">使用者暱稱</th>
    <th>信箱</th>
    <th>狀態</th>
    <th>權限</th>
    <th>管理</th>
    <th>邀請日期</th>
    <th>上次登入日期</th>
</tr>

<#if userList?exists>

	<#list userList as userVO>	
	<tr id="tr_${userVO.userId!}" height="35">
		<td>${userVO.userName!}</td>
		<td>${userVO.userEmail!}</td>
		<#list userStatus as userStatus>
			<#if userVO.status == userStatus.statusId>
				<#if userVO.otherActivate>
					<td><label id="lab_${userVO.userId!}">該使用者mail信箱已有關鍵字廣告帳戶</label></td>
				<#else>
					<#if userVO.inviteFail=="Y">					
						<td>
							<label id="lab_${userVO.userId!}">邀請已過期</label>
							<br>
							<a href="#" id="a_${userVO.userId!}" onClick="reinvite('${userVO.userId!}', '${userVO.userEmail!}');">再次傳送邀請</a>
						</td>
					<#else>
						<td>${userStatus.chName!}</td>
					</#if>
				</#if>
				
			</#if>			
		</#list>
		
		<#list privilegeModel as privilege>	
			<#if userVO.privilegeId==privilege.privilegeId>
				<td>${privilege.chName!}</td>
			</#if>
		</#list>

		
		<#if userVO.privilegeId==0 && userVO.status=="1">
			<td><a href="#" onClick="checkAdm('${userVO.userId!}');" >編輯</a></td>
		<#else>
			<td>
				<#if userVO.status!="7">
					<a href="#" onClick="modifyUser('${userVO.userId!}');" >編輯</a><br>
				</#if>
				<#if userVO.userId != user_id>
					<a href="#" onClick="checkDelete('${userVO.userId!}');" >移除使用者</a>
				</#if>
			</td>
		</#if>
		<td>${userVO.createDate!}</td>
		<#if userVO.loginDate !="">
			<td>${userVO.loginDate!}</td>			
		<#else>
			<td>無登入記錄</td>
		</#if>		
	</tr>
	</#list>  
	
</#if> 
           
</tbody></table>

</div> 
<input type="hidden" id="userId" name="userId" />
</form">