<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<link type="text/css" rel="stylesheet" href="<@s.url value="/html/css/ad/adPlugInStyle.css" />" />
<link type="text/css" rel="stylesheet" href="<@s.url value="/html/css/ad/adAdAddManyURL.css" />" />
<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.fancybox-1.3.4.js"></script> 
<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adActionFastPublishUrl.js" ></script>
<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.datepick-zh-TW.js"></script>
<script language="JavaScript" src="<@s.url value="/" />html/js/ad/dateRangeSelect.js" ></script>

<div class="cont">
    <form method="post" id="modifyForm" name="modifyForm" enctype="multipart/form-data" action="doAdFastPublistAddTmg.html">
		<h2>
			<div class="cal">帳戶名稱：${customer_info_title!}</div>
			<img vspace="12" hspace="2" align="absmiddle" src="<@s.url value="/" />html/img/iconcr.gif">新增廣告
		</h2>

		<!-- 快速網址刊登 START-->
		<div class="addN-container">
			<div class="addN-card-piece selected-stat">
	            <div class="tag adAdd">多筆網址刊登</div>
	        </div>
			<div class="ultext" style="">僅需提供您的商品賣場網址或單一商品網址，系統自動會幫您載回商品資訊輕鬆上稿</div>
		</div>
		
		<!-- 遮罩開始 -->
		<div id="loadingWaitBlock">
			<!-- 廣告活動設定開始 -->
			<div class="grtba">
	  			<div style="clear:both;height:0px"></div>

				<table id="excerptTable" width="100%" border="0" cellpadding="0" cellspacing="1" class="tablesorter">
				  	<tfoot>
				    	<tr height="35">
							<td>走期活動確認</td>
				    	</tr>
					</tfoot>
				</table>
				
	            <h4>廣告基本設定</h4>
				<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
					<tbody>
						<tr>
							<th height="35"><span class="t_s02">* </span>廣告名稱</th>
					    	<td>
								<#if (pfpAdActionList?size > 0)>
					        	<select id="adActionNameSelect">
						        	<#list pfpAdActionList as pfpAdAction>
									<option value="${pfpAdAction.adActionSeq!}">${pfpAdAction.adActionName!}</option>
									</#list>
						    	</select>
						    	<br>
								</#if>
								<input class="adnuAD_but" id="setAdActionName" name="setAdActionName" style="display:none;" type="text" maxlength="20" value="" placeholder="建立新的廣告名稱">
								<input class="adnuAD_but" id="addAdActionName" name="addAdActionName" style="color: #ff0000;margin: 12px 0 0px 0" type="button" value="新增廣告" onclick="addAdaction()">
							    <div id="actionNameErrorMsg" style="color: red;display:none;"></div>
							    <br>
							    <p class="addnew_AD ad-mod-hide" style="margin: 0"><input type="text" id="adActionName" name="adActionName" maxlength="20" value="建立新的廣告名稱">
							    <input type="button" id="" value="確定" onclick=""></p>
					        </td>
					    </tr>
	                    <tr>
	                        <th height="35"><span class="t_s02">* </span>廣告播放類型</th>
	                        <td>
	                        	<select id="adType" name="adType">
		                    		<option value="2" selected="">聯播網廣告(PChome的合作網站聯播網)</option>
		                    		<option value="0">搜尋廣告+聯播網廣告(觸及廣告族群最廣泛)</option>
		                    		<option value="1">搜尋廣告(PChome找東西搜尋和搜尋夥伴)</option>
		                    	</select>
	                        </td>
	                    </tr>
	                    <tr>
	                        <th height="35"><span class="t_s02">* </span>廣告樣式</th>
	                        <td>
	                        	<select id="adOperatingRuleSelect" name="adOperatingRuleSelect">
	                        		<option value="MEDIA">多媒體廣告</option>
			                    </select>
	                        </td>
	                    </tr>
	                    <tr>
	                        <th height="35"><span class="t_s02">* </span>廣告播放裝置</th>
	                        <td>
	                        	<select id="adDevice" name="adDevice">
									<option value="0">電腦+行動裝置</option>
									<option value="1">電腦</option>
									<option value="2">行動裝置</option>
								</select>
	                        </td>
	                    </tr>
				    </tbody>
				</table>

				<h4>廣告走期及花費設定</h4>
				<table id="setAdTimeAndMaxPriceTable" width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
					<tbody>
	                    <tr>
	                        <th height="35"><span class="t_s02">* </span>廣告開始日期</th>
	                        <td>
	                        	<input id="adActionStartDate" name="adActionStartDate" value="${adActionStartDate!}" readonly="true" >
	                       	</td>
	                    </tr>
	                    <tr>
	                        <th height="35"><span class="t_s02">* </span>廣告結束日期</th>
	                        <td>
	                            <input type="radio" value="N" name="selAdActionEndDate" onclick="cleanEndDate();" checked>無  
	                            <input type="radio" value="Y" name="selAdActionEndDate" >  <input value="<#if adActionEndDate != "3000-12-31">${adActionEndDate!}</#if>" id="adActionEndDate" name="adActionEndDate" readonly="true"/><span id="chkEndDate" name="chkEndDate" style="float:righ;color:red"></span>
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
	                        	每日花費 NT$ <input type="number" min="100" step="1" id="adActionMax" name="adActionMax" maxlength="6" value="${adActionMax!}">
	                        	<div id="adActionMaxErrorMsg" style="color: red;display:none;"></div>
	                        </td>
	                    </tr>
                	</tbody>
	            </table>
	
	            <div style="clear:both;height:10px"></div>
	
	            <h4>分類出價<span class="t_s01">(分類出價是指：您願意支付每次點擊廣告的最高金額！最低出價NT$3)</span> </h4>
	
	            <table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
		        	<tbody>
		        		<tr>
	                    	<th height="35"><span class="t_s02">* </span>分類名稱</th>
		                    <td>
		                    	<input id="setGroupName" name="setGroupName" style="display:none;" type="text" maxlength="20" value="" placeholder="建立新的分類名稱">
		                    	<div id="setGroupNameErrorMsg" style="color: red;display:none;"></div>
								<#if (pfpAdActionList?size > 0)>
									<select id="adGroupNameSelect"> 
					                	<#list pfpAdGroupList as pfpAdGroup>
											<option value="${pfpAdGroup.adGroupSeq!}_${pfpAdGroup.adGroupChannelPrice!}">${pfpAdGroup.adGroupName!}</option>
										</#list>
					                </select>
					                <br>
								</#if>
							</td>
	                	</tr>
	                	
	                	
	                	<tr id="searchTr">
		                	<th height="35">
								<span class="t_s02">* </span>搜尋廣告出價 <a style="cursor:pointer;" onclick="opennots(1)"><img src="<@s.url value="/" />html/img/question.gif"></a><br>
								<div id="shownotes1" style="visibility: hidden;" class="adnoticepop">
									<h4>搜尋廣告出價 說明</h4>
									<div class="adpopcont">設定最高單次點擊出價，來決定廣告的排名、以及廣告被點擊時您所支付的最高金額。請先設定初始出價，出價金額隨時可依廣告成效進行調整。</div>
									<a onclick="closenots(1)" style="cursor:pointer;" class="adpopclose">關閉</a>
								</div>
		                	</th>
		                    <td onselectstart="return false">
		                    	<input type="radio" disabled name="adGroupSearchPriceType" value="1" <b>使用系統建議出價，在我的預算內設定最高的排名出價</b></BR>
								<div class="exp">你在下一步所建立的關鍵字，都以建立時的每組關鍵字最高的排名價格設為出價金額(出價金額會控制在您每日的廣告預算內)<br>範例：你設定的關鍵字「電腦」，廣告排名較高出價為$5，系統即為您設定出價為$5</div>
								<input type="radio" disabled name="adGroupSearchPriceType" value="2" <b>自行設定分類出價金額NT$<input type="text" style="width:50px" disabled id="adGroupSearchPrice" name="adGroupSearchPrice" value="${defSearchPrice!}" maxlength="6"></b><div id="searchPricEerrorMsg" style="color:red;margin-left:10px;display:inline;"></div><br/>
								<div class="exp">此出價會套用在您下一步所建立的每組關鍵字中</div>
								<span class="t_s01"><br />(二種出價方式都可在廣告新增完成後，在檢視關鍵字列表頁，可個別修改關鍵字的出價金額)</span>
		                    </td>
	                	</tr>
	                
	                
	                
		                <tr id="channelTr">
		                	<th height="35">
								<span class="t_s02">* </span>聯播廣告出價 <a style="cursor:pointer;" onclick="opennots(2)"><img src="<@s.url value="/" />html/img/question.gif"></a><br>
								<div id="shownotes2" style="visibility: hidden;" class="adnoticepop">
									<h4>聯播廣告出價 說明</h4>
									<div class="adpopcont">出價金額會決定廣告播出率。系統會依每次廣告的競價結果分析出最佳的播出率，實際支付的廣告點擊費用，會小於或等於您的出價金額。</div>
									<a onclick="closenots(2)" style="cursor:pointer;" class="adpopclose">關閉</a>
								</div>
		                	</th>
		                   	<td>
		                    	<b><div id ="priceType" style="display:inline;">系統建議出價</div>NT$<input type="number" style="width:50px" id="adGroupChannelPrice" name="adGroupChannelPrice" value="${sysChannelPrice!}" min="3" max="999999"><div id="errorMsg" style="color:red;margin-left:10px;display:inline;"></div> 系統預估播出率:<span id="showRate" name="showRate">${adAsideRate!}%</span></b>
		                    	<div class="exp">出價金額會決定廣告播出率。系統會依每次廣告的競價結果分析出最佳的播出率，實際支付的廣告點擊費用，會小於或等於您的出價金額。</div>
	                    	</td>
		                </tr>
		            </tbody>
		        </table>
	
	            <div style="clear:both;height:10px"></div>
			</div>
			<!-- 廣告活動設定結束 -->
		</div>
		<!-- 遮罩結束 -->		

		<center style="margin-top:10px;">
			<input type="button" id="cancel" value="取 消"> 
			<input id="fastPublishSave" type="button" style="color: #1d5ed6" value="送出審核" onclick="alex();">
		</center>
        
        <input type="hidden" id="defaultAdType" name="defaultAdType" value="${defaultAdType!}">
        <input type="hidden" id="defaultAdOperatingRule" name="defaultAdOperatingRule" value="${defaultAdOperatingRule!}">
        <input type="hidden" id="defaultAdDevice" name="defaultAdDevice" value="${defaultAdDevice!}">
        <input type="hidden" id="defaultAdActionMax" name="defaultAdActionMax" value="${adActionMax!}">
        <input type="hidden" id="defaultAdActionEndDate" name="defaultAdActionEndDate" value="${adActionEndDate!}">
        <input type="hidden" id="defaultAdActionStartDate" name="defaultAdActionStartDate" value="${adActionStartDate!}">
        <input type="hidden" id="defaultHasActionRecord" name="defaultHasActionRecord" value="${hasActionRecord!}">
        <input type="hidden" id="defaultSysPrice" name="defaultSysPrice" value="${sysChannelPrice!}">
        <input type="hidden" id="userCategory" name="userCategory" value="${userCategory!}">
        <input type="hidden" id="defaultAdGroupSearchPriceType" name="defaultAdGroupSearchPriceType" value="${defaultAdGroupSearchPriceType!}">
        <input type="hidden" id="defaultAdGroupSearchPrice" name="defaultAdGroupSearchPrice" value="${defaultAdGroupSearchPrice!}">
        <input type="hidden" id="defaultAdGroupChannelPrice" name="defaultAdGroupChannelPrice" value="${defaultAdGroupChannelPrice!}">
	</form>
</div>

<input type="hidden" id="messageId" value="${message!}">