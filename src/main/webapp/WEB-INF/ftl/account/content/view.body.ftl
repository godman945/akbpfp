<#assign s=JspTaglibs["/struts-tags"]>

<div class="cont">


<form method="post" id="modifyForm" name="modifyForm" action="accountContentUpdate.html">
<h2>
<div class="cal">帳戶名稱：${customer_info_title}</div>
<img src="<@s.url value="/" />html/img/iconcr.gif" hspace="2" vspace="12" align="absmiddle" />修改帳戶聯絡人資料 
</h2>
<div class="grtba"><span class="t_s01">* </span>為必填欄位

<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
<tbody>
	<tr>
        <th height="35"><span class="t_s02">* </span>姓名<br></th>
      	<td><input type="text" id="memberName" name="memberName" value="${memberVO.memberName}" autocomplete="off" maxlength="20"></td>
    </tr>
    <tr>
        <th height="35"><span class="t_s02">* </span>性別</th>
        <td>
        	<#if memberVO.memberSex != "">
			<input type="radio" id="sexMale" name="memberSex" value="M" <#if memberVO.memberSex=="M">checked</#if> /> 
      		男  
    		<input type="radio" id="sexFemale" name="memberSex" value="F" <#if memberVO.memberSex=="F">checked</#if> /> 
    		女
    		<#else>
    		<input type="radio" id="sexMale" name="memberSex" value="M" checked /> 
      		男  
    		<input type="radio" id="sexFemale" name="memberSex" value="F" /> 
    		女
    		</#if>	
		</td>
    </tr>
    <tr>
      <th height="35"><span class="t_s02">* </span>生日</th>
      <td><input id="memberBirthday" name="memberBirthday" value="${memberVO.memberBirthday}" size="12" readonly="readonly"></td>
    </tr>    
    <tr>
        <th height="35"><span class="t_s02">* </span>連絡電話</th>
        <td>
			<input type="text" id="memberTelephone" name="memberTelephone" value="${memberVO.memberTelephone}" autocomplete="off" maxlength="16" style="width:100px"/>    	
		</td>
    </tr>
    <tr>
        <th height="35"><span class="t_s02">* </span>手機號碼</th>
        <td>
			<input type="text" id="memberMobile" name="memberMobile" value="${memberVO.memberMobile}" autocomplete="off" maxlength="10" style="width:100px"/><br/>
		</td>
    </tr>
    <tr>
        <th height="35"><span class="t_s02">* </span>聯絡地址</th>
    	<td><div id="city"></div></td>
    </tr>
</tbody>
</table>

<center style="margin:10px">
 	<input type="button" id="save" value="儲存修改">&nbsp; 
 	<input type="button" id="cancel" value="取消">
</center>   
<input type="hidden" id="memberZip" value="${memberVO.memberZip}" />
<input type="hidden" id="memberAddress" value="${memberVO.memberAddress}"  />

</form>

</div>

