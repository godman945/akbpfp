<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<link type="text/css" rel="stylesheet" href="<@s.url value="/html/css/ad/adPlugInStyle.css" />" />
<link type="text/css" rel="stylesheet" href="<@s.url value="/html/css/ad/adAdAddManyURL.css" />" /> 
<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adActionFastPublishUrl.js" ></script>



<#if adStyle == "TXT">
	<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adAddTxt.js" ></script>
<#elseif adStyle == "TMG">
	<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adAddTmg.js?t=20180221001" ></script>
<#elseif adStyle == "VIDEO">
	<@t.insertAttribute name="includeJs" />
</#if>

<div class="cont">
<#if adStyle == "TXT">
    <form method="post" id="modifyForm" name="modifyForm" action="doAdAdAddTxt.html">
<#elseif adStyle == "TMG">
    <form method="post" id="modifyForm" name="modifyForm" enctype="multipart/form-data" action="doAdAdAddTmg.html">
<#elseif adStyle == "VIDEO">
	<form method="post" id="modifyForm" name="modifyForm" enctype="multipart/form-data" action="doAdAdAddVideo.html">
</#if>
		<h1 class="adtitle">廣告：${adActionName!} > 分類：${adGroupName!}</h1>
		<h2>
			<div class="cal">帳戶名稱：${customer_info_title!}</div>
			<img vspace="12" hspace="2" align="absmiddle" src="<@s.url value="/" />html/img/iconcr.gif">新增廣告
		</h2>

		<!-- 快速網址刊登 START-->
		<div class="addN-container">
				<div class="addN-card-piece selected-stat">
					<div class="tag fastURLAdAdd">多筆網址刊登</div>
				</div>
				<div class="ultext" style="">僅需提供您的商品賣場網址或單一商品網址，系統自動會幫您載回商品資訊輕鬆上稿</div>
		</div>
		<!-- 遮罩開始 -->
		<div id="loadingWaitBlock">
			<!-- 廣告活動設定開始 -->
			<div class="grtba">
					<h4 style="display:none;">建立廣告</h4>
					<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02" style="display:none;">
						<tbody>
							<tr>
								<th height="35"><span class="t_s02">* </span>選擇廣告分類</th>
								<td>
									<select id="adClass" name="adClass">
										<option value="0">選擇分類</option>
										<option value="1" selected="">分類1</option>
										<option value="2">分類2</option>
										<option value="3">分類3</option>
										<option value="4">分類4</option>
										<option value="5">分類5</option>
										<option value="6">分類6</option>
									</select>
								</td>
							</tr>
							<tr>
								<th height="35"><span class="t_s02">* </span>廣告樣式</th>
								<td>
									<input type="radio" id="adStyle" name="adStyle" value="TXT" onclick="setAdStyle(this.value);">文字廣告
									<input type="radio" id="adStyle" name="adStyle" value="TMG" onclick="setAdStyle(this.value);" checked="">圖文廣告
								</td>
							</tr>
						</tbody>
					</table>
	<!-- adTmg start -->
				<!-- IE 沒有 placeholder 效果，用此 code 模擬 placeholder(Jack指導版) --> 
				<!--[if IE]>
				<script language="JavaScript" src="/html/js/ad/simuPlaceholderTmg.js" ></script>
				<![endif]-->
				<!-- IE 沒有 placeholder 效果，用此 code 模擬 placeholder(Jack指導版) --> 
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
														<option value="${pfpAdAction.adActionName!}">${pfpAdAction.adActionName!}</option>
													</#list>
					                        	</select>
					                        	<br>
												<#else>
												CCC
											</#if>
						                    
						                    <input class="adnuAD_but" style="color: #ff0000;margin: 12px 0 0px 0" type="button" value="新增廣告" id="">
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
				                        		<option value="0">多媒體廣告</option>
				                        		<option value="1">影音廣告</option>
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
	
		
				
	<!-- adTmg end -->
	 
	
				<!-- adKeyword start -->
					<div id="keywordBody">
					<script language="JavaScript" src="/html/js/ad/adKeywordAdd.js"></script>
	
			<!-- adKeyword start -->
			<script language="JavaScript" src="/html/js/ad/adKeywordAction.js"></script>
			<!-- adKeyword end -->
			<!-- IE 沒有 placeholder 效果，用此 code 模擬 placeholder(Jack指導版) --> 
			<!--[if IE]>
			<script language="JavaScript" src="/html/js/ad/simuPlaceholderKeyword.js" ></script>
			<![endif]-->
			<!-- IE 沒有 placeholder 效果，用此 code 模擬 placeholder(Jack指導版) --> 
			<div style="clear:both;height:10px"></div>
	
	
	
		    <h4>廣告走期及花費設定</h4>
	        <table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
	                <tbody>
	                    <tr>
	                        <th height="35"><span class="t_s02">* </span>廣告開始日期</th>
	                        <td>
	                        	<input id="adActionStartDate" name="adActionStartDate" value="2018-02-02" readonly="true" class="hasDatepicker"><img class="ui-datepicker-trigger" src="html/img/icon_cal.gif" alt="" title="">
	                       	</td>
	                    </tr>
	                    <tr>
	                        <th height="35"><span class="t_s02">* </span>廣告結束日期</th>
	                        <td>
	                            <input type="radio" value="N" id="selAdActionEndDate" name="selAdActionEndDate" onclick="cleanEndDate();" checked="">無  
	                            <input type="radio" value="Y" id="selAdActionEndDate" name="selAdActionEndDate">  <input value="" id="adActionEndDate" name="adActionEndDate" readonly="true" class="hasDatepicker"><img class="ui-datepicker-trigger" src="html/img/icon_cal.gif" alt="" title=""><span id="chkEndDate" name="chkEndDate" style="float:righ;color:red"></span>
	                        </td>
	                    </tr>
	                    <tr>
	                        <th height="35">
								<span class="t_s02">* </span>每日廣告預算 <a style="cursor:pointer;" onclick="opennots(1)"><img src="http://show.pchome.com.tw/html/img/question.gif" align="absmiddle"></a><br>
								<div id="shownotes1" style="visibility: hidden;" class="adnoticepop">
									<h4>每日預算設定</h4>
									<div class="adpopcont">每日廣告的實際花費，會依搜尋量的變化，與每日設定的廣告預算有小差異。</div>
									<a onclick="closenots(1)" style="cursor:pointer;" class="adpopclose">關閉</a>
								</div>
							</th>
	                        <td>
	                        	每日花費 NT$ <input type="text" id="adActionMax" name="adActionMax" maxlength="6" value="500">
	                        	<input type="hidden" id="remain" name="remain" value="0">
	                        </td>
	                    </tr>
	                </tbody>
	            </table>
	
	            <div style="clear:both;height:10px"></div>
	
	           <h4>分類出價<span class="t_s01">(分類出價是指：您願意支付每次點擊廣告的最高金額！最低出價NT$3)</span> </h4>
	
	            <table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
		        	<tbody>
		        		
		                <tr id="channelTr">
		                	<th height="35">
								<span class="t_s02">* </span>聯播網廣告出價 <a style="cursor:pointer;" onclick="opennots(2)"><img src="http://show.pchome.com.tw/html/img/question.gif"></a><br>
								
		                	</th>
		                    <td>
		                    	<b>系統建議出價NT$<input type="text" style="width:50px" id="adGroupChannelPrice" name="adGroupChannelPrice" value="14" maxlength="6">，系統預估播出率:<span id="showRate" name="showRate">2.16%</span></b>
		                    	<div class="exp">出價金額會決定廣告播出率。系統會依每次廣告的競價結果分析出最佳的播出率，實際支付的廣告點擊費用，會小於或等於您的出價金額。</div>
		                    </td>
		                </tr>
		            </tbody>
		        </table>
	
	            <div style="clear:both;height:10px"></div>
	</div>
	<!-- 廣告活動設定開始 -->
	</div>
	<!-- 遮罩結束 -->		

		<center style="margin-top:10px;">
			<input type="button" id="cancel" value="取 消"> 
			<input type="button" id="save" style="color: #1d5ed6" value="送出審核">
		</center>
		<input type="hidden" id="adGroupSeq" name="adGroupSeq" value="ag_201711220004">
		<input type="hidden" id="saveAndNew" name="saveAndNew" value="">
		<input type="hidden" id="ulTmpName" name="ulTmpName" value="ermeVqXI1wai8Bubq9Ft00RfwUucCb">
		<input type="hidden" id="imgFile" name="imgFile" value="">
        <input type="hidden" id="backPage" name="backPage" value="adActionView.html">
        <input type="hidden" id="adType" name="adType" value="0">
        
        
        <input type="" id="defaultAdType" name="defaultAdType" value="${defaultAdType!}">
        <input type="" id="defaultAdOperatingRule" name="defaultAdOperatingRule" value="${defaultAdOperatingRule!}">
        <input type="" id="defaultAdDevice" name="defaultAdDevice" value="${defaultAdDevice!}">
</div>

	</form>
</div>
<input type="hidden" id="messageId" value="${message!!}">