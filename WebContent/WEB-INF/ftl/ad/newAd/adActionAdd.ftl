<#assign s=JspTaglibs["/struts-tags"]>

<link href="<@s.url value="/html/css/ad/adPlugInStyle.css" />" rel="stylesheet" type="text/css" /> 
<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adActionAdd.js" ></script>

<div style="display: none;">
	<img id="calImg" src="<@s.url value="/html/img/"/>icon_cal.gif" alt="Popup" class="trigger">
</div>


<div class="cont">
    <form method="post" id="modifyForm" name="modifyForm" action="doAdActionAdd.html">
        <input type="hidden" name="type" id="type">
        <h2>
			<div class="cal">帳戶名稱：${customer_info_title!}</div>
        	<img vspace="12" hspace="2" align="absmiddle" src="<@s.url value="/" />html/img/iconcr.gif">新增廣告
		</h2>
        <div class="steps"><b>輸入廣告基本設定</b> &gt; 建立分類及出價  &gt; 製作廣告及關鍵字設定  &gt; 廣告完成 </div>
        <div class="grtba">
            <h4>廣告基本設定</h4>
            <table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
                <tbody>
                    <tr>
                        <th height="35"><span class="t_s02">* </span>廣告名稱</th>
                        <td><input type="text" id="adActionName" name="adActionName" maxlength="20" value="${adActionName!}"></td>
                    </tr>
                    <tr>
                        <th height="35"><span class="t_s02">* </span>廣告播放類型</th>
                        <td>
                        	<select id="adType" name="adType">
		                    	<#list adTypeList as data>
		                    		<#if '${data.type?c!}' == adType >
		                    		<option value="${data.type!}" selected >${data.typeName!}(${data.explanation!})</option>
		                    		<#else>
		                    		<option value="${data.type!}">${data.typeName!}(${data.explanation!})</option>
		                    		</#if>
		                        </#list>
		                    </select>
                        </td>
                    </tr>
                    <tr>
                        <th height="35"><span class="t_s02">* </span>廣告播放裝置</th>
                        <td>
                        	<select id="adDevice" name="adDevice">
		                    	<#list adDeviceList as data2>
		                    		<#if '${data2.devType?c!}' == adDevice >
		                    		<option value="${data2.devType!}" selected >${data2.devTypeName!}</option>
		                    		<#else>
		                    		<option value="${data2.devType!}">${data2.devTypeName!}</option>
		                    		</#if>
		                        </#list>
		                    </select>
                        </td>
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
                        	<input id="adActionStartDate" name="adActionStartDate" value="${adActionStartDate!}" readonly="true"/>
                       	</td>
                    </tr>
                    <tr>
                        <th height="35"><span class="t_s02">* </span>廣告結束日期</th>
                        <td>
                            <input type="radio" value="N" id="selAdActionEndDate" name="selAdActionEndDate" onclick="cleanEndDate();" <#if selAdActionEndDate == "N">checked</#if>>無  
                            <input type="radio" value="Y" id="selAdActionEndDate" name="selAdActionEndDate" <#if selAdActionEndDate == "Y">checked</#if>>  <input value="${adActionEndDate!}" id="adActionEndDate" name="adActionEndDate" readonly="true"/><span id="chkEndDate" name="chkEndDate" style="float:righ;color:red"></span>
                        </td>
                    </tr>
                    <tr>
                        <th height="35">
							<span class="t_s02">* </span>每日廣告預算 <a style="cursor:pointer;" onclick="opennots(1)"><img src="<@s.url value="/" />html/img/question.gif" align="absmiddle"></a><br>
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
                <input type="button" id="cancel" value="取 消">&nbsp; 
                <input type="button" id="save" value="下一步! 新增分類">
            </center>
        </div>
        <#if aid??>
        <input type="hidden" id="aid" name="aid" value="${aid!}">
        </#if>
        <#if adActionSeq??>
        <input type="hidden" id="adActionSeq" name="adActionSeq" value="${adActionSeq!}">
        </#if>
        <input type="hidden" id="backPage" name="backPage" value="${backPage!}">
    </form>
</div>
<div id="introDialog" style="display:none"></div>
<input type="hidden" id="messageId" value="${message!!}">
