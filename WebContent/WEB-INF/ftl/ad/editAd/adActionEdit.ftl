<#assign s=JspTaglibs["/struts-tags"]>

<link href="<@s.url value="/html/css/ad/adPlugInStyle.css" />" rel="stylesheet" type="text/css" /> 
<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adActionAdd.js" ></script>

<div style="display: none;">
	<img id="calImg" src="<@s.url value="/html/img/"/>icon_cal.gif" alt="Popup" class="trigger">
</div>


<div class="cont">
    <form method="post" id="modifyForm" name="modifyForm" action="doAdActionEdit.html">
        <input type="hidden" name="type" id="type">
        <h2>
			<div class="cal">帳戶名稱：${customer_info_title!}</div>
        	<img vspace="12" hspace="2" align="absmiddle" src="<@s.url value="/" />html/img/iconcr.gif">修改廣告
        </h2>
        <div class="grtba">
            <h4>廣告基本設定</h4>
            <table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
                <tbody>
                    <tr>
                        <th height="35"><span class="t_s02">* </span>廣告名稱</th>
                        <td><input type="text" id="adActionName" name="adActionName" maxlength="20" value="${adActionName!}"></td>
                    </tr>
                    <tr>
                        <th height="35"><span class="t_s02">* </span>廣告內容簡述</th>
                        <td><input type="text" id="adActionDesc" name="adActionDesc" maxlength="30" value="${adActionDesc!}"></td>
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
                        <#if !addFlag?exists || addFlag!="y">
                            ${adActionStartDate!}
                            <input type="hidden" name="adActionStartDate" value="${adActionStartDate!}" />
                        <#else>
                        	<input type="text" id="adActionStartDate" name="adActionStartDate" value="${adActionStartDate!}" readonly="true" />
                        </#if>
                       	</td>
                    </tr>
                    <tr>
                        <th height="35"><span class="t_s02">* </span>廣告結束日期</th>
                        <td>
                            <input type="radio" id="selAdActionEndDate" name="selAdActionEndDate" value="N" onclick="cleanEndDate();" <#if selAdActionEndDate == "N">checked</#if> <#if !addFlag?exists || addFlag!="y">disabled</#if> />無  
                            <input type="radio" id="selAdActionEndDate" name="selAdActionEndDate" value="Y" <#if selAdActionEndDate == "Y">checked</#if> <#if !addFlag?exists || addFlag!="y">disabled</#if> />
                            <#if !addFlag?exists || addFlag!="y">
                                ${adActionEndDate!}
                                <input type="hidden" name="adActionEndDate" value="${adActionEndDate!}" />
                            <#else>
                                <input type="text" id="adActionEndDate" name="adActionEndDate" value="${adActionEndDate!}" readonly="true" />
                            </#if>
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
                        	<input type="hidden" id="remain" name="remain" value="${tmpRemain!}">
                        </td>
                    </tr>
                </tbody>
            </table>
            <center style="margin:10px">
                <input type="button" value="取 消" onclick="history.go(-1);">&nbsp; 
                <input type="button" id="save" value="儲存變更">
            </center>
        </div>
		<input type="hidden" id="adActionSeq" name="adActionSeq" value="${adActionSeq!}">
		<input type="hidden" id="backPage" name="backPage" value="${backPage!}">
    </form>
</div>
<div id="introDialog" style="display:none"></div>
<input type="hidden" id="messageId" name="messageId" value="${message!!}">
