<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<@t.insertAttribute name="includeSource" />
	<div class="cont">
	<@t.insertAttribute name="head" />
    <form method="post" id="modifyForm" name="modifyForm" enctype="multipart/form-data" action="doAdAdAddTmg.html">
    	<div class="grtba">

			<!-- IE 沒有 placeholder 效果，用此 code 模擬 placeholder(Jack指導版) --> 
			<!--[if IE]>
			<script language="JavaScript" src="/html/js/ad/simuPlaceholderTmg.js" ></script>
			<![endif]-->
			<!-- IE 沒有 placeholder 效果，用此 code 模擬 placeholder(Jack指導版) --> 
  			<div style="clear:both;height:0px"></div>
			<h4>製作廣告</h4>

			<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02 newtb"><tbody>
				<tr>
					<th>商品廣告名稱</th>
					<td colspan="2">
						<input id="adName" type="text" placeholder="" value="" maxlength="17">
						<em class="notetext">請輸入廣告名稱</em>
						<span id="checkHintAdName" class="charactertext">已輸入0字，剩17字</span>
					</td>
				</tr>
				<tr>
					<th>選擇商品目錄</th>
					<td colspan="2">
						<div class="inputselect">
							<select name="" id="catalogSelect">
								<#if alex?exists>
									<#list alex as adReportVO>
										<option value="${adReportVO.catalogSeq!}">${adReportVO.catalogName!}</option>
									</#list>
								</#if>			
							</select>
						</div>												
					</td>
				</tr>
				<tr>
					<th>選擇商品組合</th>
					<td colspan="2">
						<div class="inputselect">
							<select name="" id="groupSelect" onchange="getProdGroup(this)">
								<option value ="">請選擇</option>
								<#if alex?exists>
									<#list alex as pfpCatalog>
										<#if pfpCatalog.pfpCatalogGroups?exists>
											<#list pfpCatalog.pfpCatalogGroups as pfpCatalogGroup>
												<#if pfpCatalog.pfpCatalogSetups?exists>
														<#if pfpCatalog.pfpCatalogSetups?exists>
															<#list pfpCatalog.pfpCatalogSetups as pfpCatalogSetup>
																<#if pfpCatalogSetup.catalogSetupKey == 'img_proportiona'>
																	<option value="${pfpCatalog.catalogSeq!}_${pfpCatalogGroup.catalogGroupSeq!}_${pfpCatalogSetup.catalogSetupValue!}">${pfpCatalogGroup.catalogGroupName!}</option>
																</#if>
															</#list>
														</#if>	
												</#if>
											</#list>
										</#if>
									</#list>
								</#if>	
							</select>
						</div>												
					</td>
				</tr>
				
				<tr>
					<th>連結網址</th>
					<td colspan="2">
						<input id="adurl" type="text" placeholder="" maxlength="">
						<em id="checkAdurl" class="notetext">請輸入廣告連結網址</em>
					</td>
				</tr>


				<tr>
					<th>品牌LOGO</th>
					<td>
						<ul class="newtbsublist">
							<li>
								<span class="newtbsubliststyle">●</span>								
								<span class="colorpickr">LOGO 推薦顏色：<br>
									<input onclick="clickColor(this);" readonly id="" style ="width:70px;background-color:#FFFFFF;color:white" value="">
									<input onclick="clickColor(this);" readonly id="" style ="width:70px;background-color:#FFFFFF;color:white" value="">
									<input onclick="clickColor(this);" readonly id="" style ="width:70px;background-color:#FFFFFF;color:white" value="">
									<br>
									<input onclick="clickColor(this);" readonly id="" style ="width:70px;background-color:#FFFFFF;color:white" value="">
									<input onclick="clickColor(this);" readonly id="" style ="width:70px;background-color:#FFFFFF;color:white" value="">
									<input onclick="clickColor(this);" readonly id="" style ="width:70px;background-color:#FFFFFF;color:white" value="">
								</span>
							</li>
							<li>
								<span class="newtbsubliststyle">●</span>								
								<span class="colorpickr"> 商標區塊底色：
									<input id="logoBgColor" onchange="changeBackgroundColor(this)" class="color {pickerPosition:'right'}" value="#FFFFFF">
								</span>
							</li>

							<li>
								<span class="newtbsubliststyle">●</span> 選擇LOGO樣式：	
								<ul class="adlogostylebx">
									<li class="transition"><label class="optradio">
										<input type="radio" value="type1" name="options" id="option1" autocomplete="off" checked>
										<p class="logostyle style1">正方形LOGO</p> 
									</label></li>
									<li class="transition"><label class="optradio">
										<input type="radio" value="type2" name="options" id="option2" autocomplete="off">
										<p class="logostyle style2">長方形LOGO</p> 
									</label></li>
									<li class="transition">
										<label class="optradio">
											<input type="radio" name="options" value="type3" id="option3" autocomplete="off">
											<p class="logostyle style3">正方形LOGO + 標題文字</p> 				</label>
										<div class="setadtitle">
											<div>
												<span class="tit">標題文字：</span>
												<input id="logoText" type="text" placeholder="限17字" maxlength="17">
												<span id="checkHintLogoText" class="charactertext">已輸入0字，剩17字</span>
												<em class="notetext">請輸入文字</em>
											</div>

											<div>
												<span class="tit">標題文字顏色：</span>
												<span class="colorpickr" style="display: block;">
													<input id="logoFontColor" onchange="changeBackgroundColor(this)" class="color {pickerPosition:'right'}" value="#000000">
												</span>
											</div>
										</div>	
									</li>
								</ul>			
								
							</li>														
						</ul><!--newtbsublist end -->
						
						<div class="logobannerbx">

							<b class="logobnr-togglebtn">進階選項</b>

							<div class="uploadbx" id="logoImgArea">

								<ul class="adlogostylebx">
									<li>									
										<p class="lgbnrtit">行銷圖像 
											<a href="javascript:void(0);" onclick="opennots('1');">
											<img src="<@s.url value="/" />html/img/question.gif"></a>：
											<input type="button" value="瀏覽檔案" onclick="openFancyfileLoad('logo')">
										</p> 
										<a href="javascript:void(0);" target="" onclick="approveSize(this);" style="font-size: 12px; margin-left: 5px">支援規格查詢 </a>
									</p></li>
								</ul>

								<ul class="adbannerpicbx">
								</ul>
							</div>
						</div>
						<div id="shownotes1" style="visibility: hidden;margin-left:-10px;" class="adnoticepop">
							<h4>行銷圖像設定</h4>
							<div class="adpopcont">針對不同廣告尺寸設計行銷圖像。請注意，行銷圖像會覆蓋整個LOGO區塊。</div>
							<a onclick="closenots(1)" style="cursor:pointer;" class="adpopclose">關閉</a>
						</div>
					</td>

					<td class="adpreview" rowspan="3" width="650">
						<div class="adpreviewbx" id="adpreviewbx">	
						<div class="previewselectsize">
							<span>預覽廣告尺寸</span>
							<div class="adsizeselect">
								<select name="" id="adSize">
									<option value="1">120 x 600</option>	
									<option value="2">140 x 300</option>	
									<option value="3">160 x 240</option>
									<option value="4">160 x 600</option>
									<option value="5">180 x 150</option>
									<option value="6">250 x 80</option>	
									<option value="7">300 x 100</option>	
									<option value="8" selected>300 x 250</option>
									<option value="9">300 x 600</option>
									<option value="10">320 x 480</option>	
									<option value="11">336 x 280</option>	
									<option value="12">640 x 390</option>	
									<option value="13">728 x 90</option>
									<option value="14">950 x 390</option>
									<option value="15">970 x 250</option>							
								</select>
								
							</div>
						</div>
						<a href="#" class="previewarw-left"><i></i></a>
						<a href="#" class="previewarw-right"><i></i></a>
						<!--廣告預覽 START-->
						<div class="adcontainr">
							<div class="adcontent">	
															
									<iframe class="akb_iframe" scrolling="no" frameborder="0" marginwidth="0" marginheight="0" vspace="0" hspace="0" id="pchome8044_ad_frame1" allowtransparency="true" allowfullscreen="true" src=""></iframe>						
															
															
															
								

							</div>							
						</div>
						<!--廣告預覽 END-->
						</div>
					</td>
				</tr>


				<tr>
					<th>按鈕</th>
					<td>
						<ul class="newtbsublist">
							<li>
								<span class="newtbsubliststyle">●</span>按鈕文字：
								<div class="inputselect">
									<select name="" id="btnTxt" onchange="changeActive(this)">
										<option value="1">立即購買</option>
										<option value="2">立即預訂</option>
										<option value="3">立即下載</option>
										<option value="4">了解更多</option>
										<option value="5">開啟連結</option>
										<option value="6">立即註冊</option>					
									</select>
								</div>
								
							</li>
							<li>
								<span class="newtbsubliststyle">●</span>								
								<span class="colorpickr">文字顏色：
									<input id="btnFontColor" onchange="changeBackgroundColor(this)" class="color {pickerPosition:'right'}" value="#055dcb">
								</span>
							</li>
							<li>
								<span class="newtbsubliststyle">●</span>								
								<span class="colorpickr">按鈕底色：
									<input id="btnBgColor" onchange="changeBackgroundColor(this)" class="color {pickerPosition:'right'}" value="#FFFFFF">
								</span>
							</li>
						</ul>									
					</td>
				</tr>

				<tr>
					<th>折扣標籤</th>
					<td>
						<ul class="newtbsublist">
							<li>
								
								<div class="inputselect">
									<select name="" id="disTxtType" onchange="changeActive(this)">
										<option value="1">無</option>
										<option value="2">中文折數 ( ex : 75折 )</option>
										<option value="3">百分比折數 ( ex : -25% )</option>				
									</select>
								</div>
								
							</li>
							<li>
								<span class="newtbsubliststyle">●</span>								
								<span class="colorpickr">文字顏色：
									<input id="disFontColor" onchange="changeBackgroundColor(this)" class="color {pickerPosition:'right'}" value="#FFFFFF">
								</span>
							</li>
							<li>
								<span class="newtbsubliststyle">●</span>								
								<span class="colorpickr">按鈕底色：
									<input id="disBgColor" onchange="changeBackgroundColor(this)" class="color {pickerPosition:'right'}" value="#0090e7">
								</span>
							</li>
						</ul>									
					</td>
				</tr>
			</tbody></table>



			<div class="advancedtbbx">

			<b class="logobnr-togglebtn">進階選項 <i></i></b>

			<div class="advancedtb">

			<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02 newtb"><tbody>
				<!-- 主標題內容,副標題內容 先隱藏 20180911 
				<tr>
					<th>主標題內容</th>
					<td>
						<div class="inputselect">
							<select name="" id="">
								<option value="1">商品名稱</option>											
							</select>
						</div>												
					</td>

					<td rowspan="3" width="650" style="padding: 0; background: none;"></td>
				</tr>

				<tr>
					<th>副標題內容</th>
					<td>
						<div class="inputselect">
							<select name="" id="">
								<option value="1">價格</option>	
								<option value="2">商品供應情況</option>
								<option value="3">商品使用狀況</option>										
							</select>
						</div>												
					</td>
				</tr>
				-->
				<tr>
					<th>結尾行銷圖像</th>
					<td>

						<div class="uploadbx" id="salesEndImgArea">

								<ul class="adlogostylebx" style="padding: 0;">
									<li>									
										<p class="lgbnrtit adendpic">結尾行銷圖像
											<a href="javascript:void(0);" onclick="opennots('2');">
											<img src="<@s.url value="/" />html/img/question.gif"></a>：								
											<input type="button" id="saleEndImgUploadBtn" disabled name="fileButton" value="瀏覽檔案" onclick="openFancyfileLoad('endSales')" >
										</p>
										<a href="javascript:void(0);" target="" onclick="approveSize(this);" style="font-size: 12px; margin-left: 5px">支援規格查詢 </a>
									</li>
								</ul>

								<ul class="adbannerpicbx" style="display:grid;">
								</ul>
							</div>
							<div id="shownotes2" style="visibility: hidden;margin-left:-10px;" class="adnoticepop">
								<h4>結尾行銷圖像設定</h4>
								<div class="adpopcont">商品輪播結尾的行銷圖像，圖片尺寸大小須與廣告版型大小一致。</div>
								<a onclick="closenots(2)" style="cursor:pointer;" class="adpopclose">關閉</a>
							</div>								
					</td>
				</tr>

			</tbody></table>
			</div>			
			
			
		</div>

		</div>
		<!--grtba end-->
		<span class="t_s01">※※※ 提醒您，您的廣告將在3工作天(周一到周五)審核完成(不含例假日)，並於廣告審核完成後開始播放 ※※※</span>
		<center style="margin-top:10px;width:70%">	            
            <input type="button" id="cancel" value="取 消" class="btn-cancel"> 
            <input type="button" id="save" value="送出審核" class="btn-save" onclick="adEditSubmit();">
		</center>
	</form>
</div>
<input type="file" serialize id="fileupload" name="fileupload"  style="display:none;">


<div id ="test" class="test" style="display:none;">
</div>

<div id ="test2" class="test2" style="display:none;">
</div>

<div id ="approveSize" style="display:none">
		<table width="90%" cellspacing="1" cellpadding="0" border="0" class="tb02" style="margin:10px auto;line-height:18px;">
	        <tbody>
	            <tr>
	                <th height="20">圖片格式</th>
	                <td>JPG、PNG、GIF檔</td>
	            </tr>
	            <tr>
	                <th height="20">圖片大小上限</th>
	                <td>
	                	 180 KB
	                </td>
	            </tr>
	        </tbody>
	    </table>
	</div>

<textarea style="display:none;" id="saveUserLogoPath">${userLogoPath!}</textarea>
<input type="hidden" id="saveProdLogoType" value="${prodLogoType!}">
<input type="hidden" id="messageId" value="${message!!}">
<input type="hidden" id="messageId" value="">
<input type="hidden" id="adSeq" value="${adSeq!}">
<input type="hidden" id="adGroupSeq" value="${adGroupSeq!}">
<input type="hidden" id="saveAdName" value="${adName!}">
<input type="hidden" id="saveAdLinkURL" value="${adLinkURL!}">
<input type="hidden" id="saveCatalogId" value="${catalogId!}">
<input type="hidden" id="saveCatalogGroupId" value="${catalogGroupId!}">
<input type="hidden" id="saveLogoType" value="${logoType!}">
<input type="hidden" id="saveLogoText" value="${logoText!}">
<input type="hidden" id="saveLogoBgColor" value="${logoBgColor!}">
<input type="hidden" id="saveLogoFontColor" value="${logoFontColor!}">
<input type="hidden" id="saveBtnTxt" value="${btnTxt!}">
<input type="hidden" id="saveBtnFontColor" value="${btnFontColor!}">
<input type="hidden" id="saveBtnBgColor" value="${btnBgColor!}">
<input type="hidden" id="saveDisTxtType" value="${disTxtType!}">
<input type="hidden" id="saveDisBgColor" value="${disBgColor!}">
<input type="hidden" id="saveDisFontColor" value="${disFontColor!}">
<input type="hidden" id="saveUserLogoType" value="${userLogoType!}">
<textarea style="display:none;" id="saveLogoSaleImg">${uploadLogoLog!}</textarea>
<textarea style="display:none;" id="saveSaleImg">${uploadLog!}</textarea>

