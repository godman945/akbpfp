<#assign s=JspTaglibs["/struts-tags"]>

<link href="<@s.url value="/html/css/ad/adPlugInStyle.css" />" rel="stylesheet" type="text/css" /> 
<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adGroup.js" ></script>
<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adFreeGroupAdd.js" ></script>

<div class="cont">
	<h2>
		<div class="cal">帳戶名稱：${customer_info_title!}</div>
	</h2>
	<div class="steps">輸入廣告基本設定 &gt; <b>建立分類及出價</b>  &gt; 製作廣告及關鍵字設定  &gt; 廣告完成 </div>
</div>

<#if pfpAdGroups?exists>
<div id="oldGroup" class="cont">
	<h2>
		<img vspace="12" hspace="2" align="absmiddle" src="<@s.url value="/" />html/img/iconcr.gif">已建立廣告分類
	</h2>
    <table width="100%" cellspacing="1" cellpadding="0" border="0">
    	<tbody>
    		<tr>
    			<td width="450px">
					<select id="existGroup" name="existGroup" size="10" style="width:400px">
					    <option value="">請選擇廣告分類.....</option>
						<#list pfpAdGroups as pfpAdGroup>
					    <option value="${pfpAdGroup.adGroupSeq}">${pfpAdGroup.adGroupName}</option>
						</#list>
					</select>
    			</td>
    			<td valign="top">
    				<div id="existAd" style="display:none">
        				<span style="font-size:150%">分類名稱：</span><span id="groupName" style="font-size:150%"></span><br>
        				<span style="font-size:150%">搜尋廣告出價：</span><span id="groupSearchPrice" style="font-size:150%"></span><br>
        				<span style="font-size:150%">聯播廣告出價：</span><span id="groupChannelPrice" style="font-size:150%"></span><br>
    				</div>
    			</td>
    		</tr>
    	</tbody>
    </table>
    <center id="btnExist" style="display:none">
		<input type="button" id="btnInitial" name="btnInitial" value="取消">
		<input type="button" id="btnGetGroup" name="btnGetGroup" value="前往製作廣告">
	</center>
</div>
</#if>

<div id="newGroup" class="cont">
    <form method="post" id="modifyForm" name="modifyForm" action="doAdFreeGroupAdd.html">
		<h1 class="adtitle"><span>廣告：</span> ${adActionName!}</h1>
		<h2>
			<img vspace="12" hspace="2" align="absmiddle" src="<@s.url value="/" />html/img/iconcr.gif">新增分類
		</h2>
		<div class="grtba">
			<h4>分類</h4>
			<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
	        	<tbody>
	        		<tr>
	                    <th height="35"><span class="t_s02">* </span>分類名稱</th>
	                    <td><input type="text" maxlength="20" id="adGroupName" name="adGroupName" value="${adGroupName}" maxlength="20" ></td>
	                </tr>
	            </tbody>
	        </table>
	        <div style="clear:both;height:10px"></div>
	        <h4>分類出價<span class="t_s01">(分類出價是指：您願意支付每次點擊廣告的最高金額！)</span> </h4>
			<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
	        	<tbody>
	        		<tr>
	                	<th height="35">
							<span class="t_s02">* </span>搜尋廣告出價 <a style="cursor:pointer;" onclick="opennots(1)"><img src="<@s.url value="/" />html/img/question.gif"></a><br>
							<div id="shownotes1" style="visibility: hidden;" class="adnoticepop">
								<h4>搜尋廣告出價 說明</h4>
								<div class="adpopcont">設定最高單次點擊出價，來決定廣告的排名、以及廣告被點擊時您所支付的最高金額。請先設定初始出價，出價金額隨時可依廣告成效進行調整。</div>
								<a onclick="closenots(1)" style="cursor:pointer;" class="adpopclose">關閉</a>
							</div>
	                	</th>
	                    <td>
	                    	<input type="radio" id="adGroupSearchPriceType" name="adGroupSearchPriceType" value="1" <#if adGroupSearchPriceType == "1">checked</#if>><b>使用系統建議出價，在我的預算內設定最高的排名出價</b></BR>
							<div class="exp">你在下一步所建立的關鍵字，都以建立時的每組關鍵字最高的排名價格設為出價金額(出價金額會控制在您每日的廣告預算內)<br>範例：你設定的關鍵字「電腦」，廣告排名較高出價為$5，系統即為您設定出價為$5</div>

							<input type="radio" id="adGroupSearchPriceType" name="adGroupSearchPriceType" value="2" <#if adGroupSearchPriceType == "2">checked</#if>><b>自行設定分類出價金額NT$<input type="text" style="width:50px" id="adGroupSearchPrice" name="adGroupSearchPrice" value="${defSearchPrice!}" maxlength="6"></b><br />
							<div class="exp">此出價會套用在您下一步所建立的每組關鍵字中</div>
							<span class="t_s01"><br />(二種出價方式都可在廣告新增完成後，在檢視關鍵字列表頁，可個別修改關鍵字的出價金額)</span>
	                    </td>
	                </tr>
	                <tr>
	                	<th height="35">
							<span class="t_s02">* </span>聯播廣告出價 <a style="cursor:pointer;" onclick="opennots(2)"><img src="<@s.url value="/" />html/img/question.gif"></a><br>
							<div id="shownotes2" style="visibility: hidden;" class="adnoticepop">
								<h4>聯播廣告出價 說明</h4>
								<div class="adpopcont">出價金額會決定廣告播出率。系統會依每次廣告的競價結果分析出最佳的播出率，實際支付的廣告點擊費用，會小於或等於您的出價金額。</div>
								<a onclick="closenots(2)" style="cursor:pointer;" class="adpopclose">關閉</a>
							</div>
	                	</th>
	                    <td>
	                    	<b>聯播廣告出價金額NT$<input type="text" style="width:50px" id="adGroupChannelPrice" name="adGroupChannelPrice" value="${adGroupChannelPrice!}" maxlength="6"> 系統預估播出率:<span id="showRate" name="showRate">${AdAsideRate!}%</span>，系統建議出價NT$ ${sysChannelPrice!} </b>
	                    	<div class="exp">出價金額會決定廣告播出率。系統會依每次廣告的競價結果分析出最佳的播出率，實際支付的廣告點擊費用，會小於或等於您的出價金額。</div>
	                    </td>
	                </tr>
	            </tbody>
	        </table>
	        <center style="margin:10px">
	            <input type="button" id="cancel" value="取消" onclick="javascript:window.history.back();">&nbsp; 
	            <input type="button" id="save" value="下一步! 製作廣告"> &nbsp; 
	 		</center>
	    </div>
		<input type="hidden" id="adActionSeq" name="adActionSeq" value="${adActionSeq!}">
		<input type="hidden" id="adGroupSeq" name="adGroupSeq" value="${adGroupSeq!}">
		<input type="hidden" id="adActionMax" name="adActionMax" value="${adActionMax!}">
        <input type="hidden" id="backPage" name="backPage" value="${backPage!}">
	</form>
</div>
<input type="hidden" id="messageId" value="${message!}">
