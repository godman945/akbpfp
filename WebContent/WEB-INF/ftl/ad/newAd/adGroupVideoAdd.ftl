<#assign s=JspTaglibs["/struts-tags"]>

<link href="<@s.url value="/html/css/ad/adPlugInStyle.css" />" rel="stylesheet" type="text/css" /> 
<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adGroup.js" ></script>
<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adGroupAdd.js" ></script>

<div class="cont">
    <form method="post" id="modifyForm" name="modifyForm" action="doAdGroupAdd.html">
		<h1 class="adtitle"><span>廣告：</span> ${adActionName!}</h1>
		<h2>
			<div class="cal">帳戶名稱：${customer_info_title!}</div>
			<img vspace="12" hspace="2" align="absmiddle" src="<@s.url value="/" />html/img/iconcr.gif">新增分類
		</h2>
		<div class="steps">輸入廣告基本設定 &gt; <b>建立分類及出價</b>  &gt; 製作影音廣告  &gt; 廣告完成 </div>
		<div class="grtba">
			<h4>分類</h4>
			<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
	        	<tbody>
	        		<tr>
	                    <th height="35"><span class="t_s02">* </span>分類名稱</th>
	                    	<td><input type="text" maxlength="20" id="adGroupName" name="adGroupName" value="${adGroupName!}" maxlength="20"><div style='color:red;display:inline;padding-left:5px' id='adGroupNameMsg'></div></td>
	                </tr>
	            </tbody>
	        </table>
	        <div style="clear:both;height:10px"></div>
	        <h4>分類出價<span class="t_s01"></span> </h4>
			<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
	        	<tbody>
	                <tr id="channelTr">
	                	<th height="35">
							<span class="t_s02">* </span>影音廣告出價 <a style="cursor:pointer;" onclick="opennots(2)"><img src="<@s.url value="/" />html/img/question.gif"></a><br>
							<div id="shownotes2" style="visibility: hidden;" class="adnoticepop">
								<h4>聯播網廣告出價 說明</h4>
								<div class="adpopcont">出價金額會決定廣告播出率。系統會依每次廣告的競價結果分析出最佳的播出率，實際支付的廣告點擊費用，會小於或等於您的出價金額。</div>
								<a onclick="closenots(2)" style="cursor:pointer;" class="adpopclose">關閉</a>
							</div>
	                	</th>
	                    <td>
	                    	<select id="adPriceType" name="adPriceType">
                        		<#if pfpAdPriceTypeVOList?exists>
                        			<#list pfpAdPriceTypeVOList as pfpAdPriceTypeVO>
                        				<#if pfpAdPriceTypeVO.type != 2>
                        					<option value="${pfpAdPriceTypeVO.type!}" >${pfpAdPriceTypeVO.typeName!}</option>
                        				</#if>
                        			</#list>
								</#if>
		                    </select>
		                    <div class="msg"></div>
	                    	<br>
	                    		<div class="errMsg"></div>
	                    	<br>
		                    	<b>系統建議出價NT$<input type="number" min="0" step="0.1" id="adPrice" name="adPrice" style="width:50px" maxlength="6" value="<#if adGroupSeq?exists>${adGroupChannelPrice!}<#else>${sysChannelPrice?number?string('#,###.##')!}</#if>">，系統預估播出率:<span id="showRate" name="showRate">${AdAsideRate!}%</b><div id="errorMsg" style="color:red;margin-left:10px;display:inline;"></div></span>
	                    		<div class="exp">出價金額會決定廣告播出率。系統會依每次影片的競價結果分析出最佳的播出率，實際支付的廣告收視費用，會小於或等於您的出價金額。</div>
	                    </td>
	                </tr>
	            </tbody>
	        </table>
	        <center style="margin:25px 10px 20px 10px">
	            <input type="button" id="cancel" value="上一步">&nbsp;&nbsp;&nbsp;&nbsp; 
	            <input type="button" id="" style="color: #1d5ed6" value="繼 續" onclick="doAdGroupaddSubmit();"> &nbsp; 
	 		</center>
	    </div>
		<input type="hidden" id="adActionSeq" name="adActionSeq" value="${adActionSeq!}">
		<input type="hidden" id="adGroupSeq" name="adGroupSeq" value="${adGroupSeq!}">
		<input type="hidden" id="adActionMax" name="adActionMax" value="${adActionMax!}">
        <input type="hidden" id="backPage" name="backPage" value="${backPage!}">
        <input type="hidden" id="showSearchPrice" name ="showSearchPrice" value="${showSearchPrice!}" />
	    <input type="hidden" id="showChannelPrice" name ="showChannelPrice" value="${showChannelPrice!}" />
	    <input type="hidden" id="adOperatingRule" name ="adOperatingRule" value="${adOperatingRule!}" />
	</form>
</div>
<input type="hidden" id="messageId" value="${message!!}">
<input type="hidden" id="adUserAmount" value="${adUserAmount!}">
<input type="hidden" id="adPriceTypeValue" name ="adPriceTypeValue" value="${adPriceTypeValue!}" />
