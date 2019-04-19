<#assign s=JspTaglibs["/struts-tags"]>

<link href="<@s.url value="/html/css/ad/adPlugInStyle.css" />" rel="stylesheet" type="text/css" /> 
<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adActionAdd.js" ></script>
<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adFreeActionAdd.js" ></script>

<div style="display: none;">
	<img id="calImg" src="<@s.url value="/html/img/"/>icon_cal.gif" alt="Popup" class="trigger">
</div>

<div class="cont">
	<h2>
		<div class="cal">帳戶名稱：${customer_info_title!}</div>
	</h2>
    <div class="steps"><b>輸入廣告基本設定</b> &gt; 建立分類及出價  &gt; 製作廣告及關鍵字設定  &gt; 廣告完成 </div>
</div>

<#if pfpAdActions?exists>
<div id="oldAction" class="cont">
	<h2><img vspace="12" hspace="2" align="absmiddle" src="<@s.url value="/" />html/img/iconcr.gif">已建立廣告</h2>
    <table width="100%" cellspacing="1" cellpadding="0" border="0">
    	<tbody>
    		<tr>
    			<td width="450px">
					<select id="existAction" name="existAction" size="10" style="width:400px">
					    <option value="">請選擇廣告.....</option>
						    <#list pfpAdActions as pfpAdAction>
					    <option value="${pfpAdAction.adActionSeq}">${pfpAdAction.adActionName}</option>
						    </#list>
					</select>
    			</td>
    			<td valign="top">
    				<div id="existAd" style="display:none">
        				<span style="font-size:150%">廣告名稱：</span><span id="actionName" style="font-size:150%"></span><br>
        				<span style="font-size:150%">廣告內容：</span><span id="actionDesc" style="font-size:150%"></span><br>
        				<span style="font-size:150%">廣告走期：</span><span id="actionDate" style="font-size:150%"></span><br>
        				<span style="font-size:150%">每日預算：</span><span id="actionMax" style="font-size:150%"></span>
    				</div>
    			</td>
    		</tr>
    	</tbody>
    </table>
    <center id="btnExist" style="display:none">
		<input type="button" id="btnInitial" name="btnInitial" value="取消">
		<input type="button" id="btnGetAction" name="btnGetAction" value="前往新增分類">
	</center>
</div>
</#if>
<div id="newAction" class="cont">
    <form method="post" id="modifyForm" name="modifyForm" action="doAdFreeActionAdd.html">
        <input type="hidden" name="type" id="type">
        <h2><img vspace="12" hspace="2" align="absmiddle" src="<@s.url value="/" />html/img/iconcr.gif">新增廣告      </h2>
        <div class="grtba">
            <h4>廣告基本設定</h4>
            <table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
                <tbody>
                    <tr>
                        <th height="35"><span class="t_s02">* </span>廣告名稱</th>
                        <td><input type="text" id="adActionName" name="adActionName" data-value="${adActionName!}" maxlength="20" value="${adActionName!}"></td>
                    </tr>
                    <tr>
                        <th height="35"><span class="t_s02">* </span>廣告內容簡述</th>
                        <td><input type="text" id="adActionDesc" name="adActionDesc" data-value="${adActionDesc!}" maxlength="30" value="${adActionDesc!}"></td>
                    </tr>
                </tbody>
            </table>
            <div style="clear:both;height:10px"></div>
            <h4>廣告走期及花費設定</h4>
            <table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
                <tbody>
                    <tr>
                        <th height="35"><span class="t_s02">* </span>廣告開始日期</th>
                        <td>
                        	<input id="adActionStartDate" name="adActionStartDate" data-value="${adActionStartDate!}" value="${adActionStartDate!}" readonly="true"/>
                       	</td>
                    </tr>
                    <tr>
                        <th height="35"><span class="t_s02">* </span>廣告結束日期</th>
                        <td>
                            <input type="radio" value="N" id="selAdActionEndDate" name="selAdActionEndDate" onclick="cleanEndDate();" <#if selAdActionEndDate == "N">checked</#if>>無  
                            <input type="radio" value="Y" id="selAdActionEndDate" name="selAdActionEndDate" <#if selAdActionEndDate == "Y">checked</#if>>  <input value="${adActionEndDate}" id="adActionEndDate" name="adActionEndDate" readonly="true"/><span id="chkEndDate" name="chkEndDate" style="float:righ;color:red"></span>
                        </td>
                    </tr>
                    <tr>
                        <th height="35">
							<span class="t_s02">* </span>每日預算 <a style="cursor:pointer;" onclick="opennots(1)"><img src="<@s.url value="/" />html/img/question.gif" align="absmiddle"></a><br>
							<div id="shownotes1" style="visibility: hidden;" class="adnoticepop">
								<h4>每日預算設定</h4>
								<div class="adpopcont">每日廣告的實際花費，會依搜尋量的變化，與每日設定的廣告預算有小差異。</div>
								<a onclick="closenots(1)" style="cursor:pointer;" class="adpopclose">關閉</a>
							</div>
						</th>
                        <td>
                        	每日花費 NT$ <input type="text" id="adActionMax" name="adActionMax" maxlength="6" value="${adActionMax!}">
                        </td>
                    </tr>
                </tbody>
            </table>
            <center style="margin:10px">
                <input type="button" id="freeAdCancel" value="取 消">&nbsp; 
                <input type="button" id="save" value="下一步! 新增分類">
            </center>
        </div>
        <input type="hidden" id="aid" name="aid" value="${aid!}">
        <input type="hidden" id="adActionSeq" name="adActionSeq" value="${adActionSeq!}">
        <input type="hidden" id="backPage" name="backPage" value="${backPage!}">
    </form>
</div>
<div id="introDialog" style="display:none"></div>
<input type="hidden" id="messageId" value="${message!}">
