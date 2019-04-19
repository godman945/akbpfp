<#assign s=JspTaglibs["/struts-tags"]>

<form method="post" id="formAuthorization" name="formAuthorization" action="agreeAuthorization.html">

<div class="cont" style="width:800px;">
	<div style="font-size:18px;color:#000;line-height:30px;font-family:Verdana">請先設定您的PChome廣告帳戶資料，並詳閱下方條款與規範。<br>勾選同意後，按下「儲存！開始使用服務」，即可使用您的廣告刊登帳戶</div>
	<h2><img vspace="12" hspace="2" align="absmiddle" src="<@s.url value="/" />html/img/iconcr.gif">建立總管理者資料</h2>
	<div class="grtba">
		<span class="t_s01">* </span>為必填欄位
		<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
			<tbody>
				<tr>
					<th height="35"><span class="t_s02">* </span>姓名<br></th>
			  		<td><input type="text" id="memberName" name="memberName" value="<#if memberVO??>${memberVO.memberName!}</#if>" autocomplete="off"  style="width:100px" /></td>
				</tr>
				<tr>
					<th height="35"><span class="t_s02">* </span>性別</th>
					<td>
						<input type="radio" id="sexMale" name="memberSex" value="M" <#if memberVO?? && memberVO.memberSex=="M">checked</#if> />
						<label>男 </label>
			    		<input type="radio" id="sexFemale" name="memberSex" value="F" <#if memberVO?? && memberVO.memberSex=="F">checked</#if> />
			    		<label>女 </label>
					</td>
				</tr>
				<tr>
					<th height="35"><span class="t_s02">* </span>生日</th>
					<td><input id="memberBirthday" name="memberBirthday" value="<#if memberVO?exists>${memberVO.memberBirthday!}</#if>" size="12" readonly="readonly"></td>
				</tr>
				<tr>
					<th height="35"><span class="t_s02">* </span>連絡電話</th>
					<td>
						<input type="text" id="memberTelephone" name="memberTelephone" value="<#if memberVO?exists>${memberVO.memberTelephone!}</#if>" autocomplete="off" maxlength="16" style="width:100px"/>
					</td>
				</tr>
				<tr>
					<th height="35"><span class="t_s02">* </span>手機號碼</th>
					<td>
						<input type="text" id="memberMobile" name="memberMobile" value="<#if memberVO?exists>${memberVO.memberMobile!}</#if>" autocomplete="off" maxlength="10" style="width:100px"/>
					</td>
				</tr>
				<tr>
					<th height="35"><span class="t_s02">* </span>聯絡地址</th>
					<td><div id="city"></div></td>
				</tr>
			</tbody>
		</table>   
	</div>
</div>
<input type="hidden" id="memberZip" value="<#if memberVO??>${memberVO.memberZip!}</#if>" />
<input type="hidden" id="memberAddress" value="<#if memberVO??>${memberVO.memberAddress!}</#if>" />
</form>
</div>
	<center style="margin:10px">
		<input type="checkbox" id="accept" name="accept" />
  		<strong>我同意接受<a href="http://show.pchome.com.tw/faq.html?fid=4&qid=2" target="_blank">約定條款</a>和<a href="http://show.pchome.com.tw/faq.html?fid=4&qid=5" target="_blank">刊登規範</a></strong>
  		<p>
		<input type="button" id="save" value="儲存!開始使用服務" disabled="true" />
	</center>
</div>
