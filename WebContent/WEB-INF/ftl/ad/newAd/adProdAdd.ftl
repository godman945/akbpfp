<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<@t.insertAttribute name="includeSource" />

	<div class="cont">
	<@t.insertAttribute name="head" />
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
						<input id="adName" type="text" placeholder="" value="" maxlength="20">
						<em id="adTextMsg" style="display:none;" class="notetext">請輸入廣告名稱</em>
						<span id="checkHintAdName" class="charactertext">已輸入0字，剩20字</span>
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
																	<#if pfpCatalogGroup.catalogGroupDeleteStatus == "0">
																		<option value="${pfpCatalog.catalogSeq!}_${pfpCatalogGroup.catalogGroupSeq!}_${pfpCatalogSetup.catalogSetupValue!}">${pfpCatalogGroup.catalogGroupName!}</option>
																	</#if>
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
						<em id="groupSelectMsg" style="display:none;" class="notetext"></em>											
					</td>
				</tr>
				
				<tr>
					<th>連結網址</th>
					<td colspan="2">
						<input id="adurl" type="text" placeholder="" maxlength="">
						<em id="checkAdurl"  class="notetext"></em>
					</td>
				</tr>


				<tr>
					<th>品牌LOGO</th>
					<td>
						<ul class="newtbsublist">
							<li>
								<span class="newtbsubliststyle">●</span><strong> LOGO區塊底色：</strong>							
								<div class="colorpickr_box">
									<i> 系統推薦底色：</i>
									<span class="colorpickr">
										<input onclick="clickColor(this);" readonly style ="background-color:#FFFFFF;color:white" value="" ">
										<input onclick="clickColor(this);" readonly style ="background-color:#FFFFFF;color:white" value="" >
										<input onclick="clickColor(this);" readonly style ="background-color:#FFFFFF;color:white" value="" >
										<input onclick="clickColor(this);" readonly style ="background-color:#FFFFFF;color:white" value="" >
										<input onclick="clickColor(this);" readonly style ="background-color:#FFFFFF;color:white" value="" >
										<input onclick="clickColor(this);" readonly style ="background-color:#FFFFFF;color:white" value="" >
									</span>
								</div>
								<div class="colorpickr_box">
									<i> 自訂底色：</i>
									<span class="colorpickr"> 
										<input id="logoBgColor" onchange="changeBackgroundColor(this)" class="color {pickerPosition:'right'}" value="#FFFFFF">
									</span>
								</div>		
							</li>
							<li>
								<span class="newtbsubliststyle">●</span> 選擇LOGO樣式：	
								<ul class="adlogostylebx">
									<li class="transition">
										<input type="radio" value="type2" name="options" id="option1" autocomplete="off">
										<p class="logostyle style1">長方形LOGO</p> 
									</li>

									<li  class="transition">
										<input type="radio" value="type1" name="options" id="option2" autocomplete="off" checked>
										<p class="logostyle style2">正方形LOGO</p> 
									</li>
									
									<li class="transition">
										<input type="radio" name="options" value="type3" id="option3" autocomplete="off">
										<p class="logostyle style3">正方形LOGO + 標題文字</p> 				
										<div class="setadtitle">
											<div>
												<span class="tit">標題文字：</span>
												<input id="logoText" type="text" placeholder="限17字" maxlength="17">
												<span id="checkHintLogoText" class="charactertext">已輸入0字，剩17字</span>
												<em id="logoTextMsg" class="notetext" style="display:none;"></em>
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
										<p class="lgbnrtit">行銷圖像 <a href="javascript:void(0);" onclick="approveSize('行銷圖像說明');">
											<img src="<@s.url value="/" />html/img/question.gif"></a>：
											<input type="button" value="上傳檔案" onclick="openFancyfileLoad('logo')">
										</p> 
											<a href="javascript:void(0);" target="" onclick="approveSize('行銷圖像支援規格查詢');" style="font-size: 12px; margin-left: 5px">支援規格查詢 </a>
										</p>
									</li>
								</ul>

								<ul class="adbannerpicbx">
								</ul>
							</div>
						</div>
					</td>

					<td class="adpreview" rowspan="3" width="650">
						<div class="adpreviewbx" id="adpreviewbx">	
						<div class="previewselectsize">
							<span>預覽廣告尺寸</span>
							<div class="adsizeselect">
								<select name="" id="adSize">
									<option value="tpro_120_600">120 x 600</option>	
									<option value="tpro_140_300">140 x 300</option>	
									<option value="tpro_160_240">160 x 240</option>
									<option value="tpro_160_600">160 x 600</option>
									<option value="tpro_180_150">180 x 150</option>
									<option value="tpro_250_80">250 x 80</option>	
									<option value="tpro_300_100">300 x 100</option>	
									<option value="tpro_300_250" selected>300 x 250</option>
									<option value="tpro_300_600">300 x 600</option>
									<option value="tpro_320_480">320 x 480</option>	
									<option value="tpro_336_280">336 x 280</option>	
									<option value="tpro_640_390">640 x 390</option>	
									<option value="tpro_728_90">728 x 90</option>
									<option value="tpro_950_390">950 x 390</option>
									<option value="tpro_970_250">970 x 250</option>							
								</select>
								
							</div>
						</div>
						
						<div class="previewbx-l"><a href="javascript:void(0)" onclick="changeTpro();" class="previewarw-left"><i></i></a></div>
						<div class="previewbx-r"><a href="javascript:void(0)" onclick="changeTpro();" class="previewarw-right"><i></i></a></div>
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
									<input id="btnFontColor" onchange="changeBackgroundColor(this)" class="color {pickerPosition:'right'}" value="#FFFFFF">
								</span>
							</li>
							<li>
								<span class="newtbsubliststyle">●</span>								
								<span class="colorpickr">按鈕底色：
									<input id="btnBgColor" onchange="changeBackgroundColor(this)" class="color {pickerPosition:'right'}" value="#000000">
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
									<input id="disBgColor" onchange="changeBackgroundColor(this)" class="color {pickerPosition:'right'}" value="#FF0000">
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

								<ul class="adlogostylebx">
									<li>									
										<p class="lgbnrtit adendpic">結尾行銷圖像
											<a href="javascript:void(0);" onclick="approveSize('結尾行銷圖像設定');">
											<img src="<@s.url value="/" />html/img/question.gif"></a>：
											<input type="button" id="saleEndImgUploadBtn" disabled name="fileButton" value="上傳檔案" onclick="openFancyfileLoad('endSales')" >
										</p>
										<a href="javascript:void(0);" target="" onclick="approveSize('結尾行銷圖像支援規格查詢');" style="font-size: 12px; margin-left: 5px">支援規格查詢 </a>
									</li>
								</ul>

								<ul class="adbannerpicbx" style="display:grid;">
								</ul>
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
            <input type="button" id="cancel" value="取 消" class="btn-cancel"  onclick="javascript:location.href='adAdView.html?adGroupSeq=${adGroupSeq!}'" > 
            <input type="button" id="save" value="送出審核" class="btn-save" onclick="adSubmit();">
		</center>

<input type="hidden" id="messageId" value="">
<input type="hidden" id="adGroupSeq" value="${adGroupSeq!}">
<input type="hidden" id="userLogoType" value="${userLogoType!}">
<textarea style="display:none;" id="userLogoPath">${userLogoPath!}</textarea>

</div>
<input type="file" serialize id="fileupload" name="fileupload"  style="display:none;">
<input type="hidden" id="messageId" value="${message!!}">


<div id ="test" class="test" style="display:none;">
</div>

<div id ="test2" class="test2" style="display:none;">
</div>



	
<div id ="approveSize" style="display:none" >
		<div class="noticepop" style="width:auto;">
		<h4 id="title"></h4><div>
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
	   </div>
</div>

	
<div id ="note1" style="display:none" >
		<div class="noticepop" style="width:auto;">
		<h4 id="title"></h4><div>
	    <table width="90%" cellspacing="1" cellpadding="0" border="0" class="tb02" style="margin:10px auto;line-height:18px;">
	        <tbody>
	            <tr>
	                <td>針對不同廣告尺寸設計行銷圖像。請注意，行銷圖像會覆蓋整個LOGO區塊。</td>
	            </tr>
	        </tbody>
	    </table>
	    </div>
	   </div>
</div>

<div id ="note2" style="display:none" >
		<div class="noticepop" style="width:auto;">
		<h4 id="title"></h4><div>
	    <table width="90%" cellspacing="1" cellpadding="0" border="0" class="tb02" style="margin:10px auto;line-height:18px;">
	        <tbody>
	            <tr>
	                <td>商品輪播結尾的行銷圖像，圖片尺寸大小須與廣告版型大小一致。</td>
	            </tr>
	        </tbody>
	    </table>
	    </div>
	   </div>
</div>
