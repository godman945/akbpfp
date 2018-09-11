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
							<select name="" id="">
								<option value="1">夏季運動會20180718</option>
								<option value="2">夏季運動會</option>
								<option value="3">20180718夏季運動會</option>				
							</select>
						</div>												
					</td>
				</tr>

				<tr>
					<th>選擇商品組合</th>
					<td colspan="2">
						<div class="inputselect">
							<select name="" id="">
								<option value="1">夏季運動會20180718</option>
								<option value="2">夏季運動會</option>
								<option value="3">20180718夏季運動會</option>				
							</select>
						</div>												
					</td>
				</tr>
				
				<tr>
					<th>連結網址</th>
					<td colspan="2">
						<input type="text" placeholder="" maxlength="17">
						<em id="checkAdurl" class="notetext">請輸入廣告連結網址</em>
					</td>
				</tr>


				<tr>
					<th>品牌LOGO</th>
					<td>
						<ul class="newtbsublist">
							<li>
								<span class="newtbsubliststyle">●</span>								
								<span class="colorpickr"> 商標區塊底色：
									<input onchange="changeBackgroundColor(this)" class="color {pickerPosition:'right'}" value="#FFFFFF">
								</span>
							</li>

							<li>
								<span class="newtbsubliststyle">●</span> 選擇LOGO樣式：	
								<ul class="adlogostylebx">
									<li class="transition"><label class="optradio">
										<input type="radio" name="options" id="option1" autocomplete="off">
										<p class="logostyle style1">長方形LOGO</p> 
									</label></li>
									<li class="transition"><label class="optradio">
										<input type="radio" name="options" id="option2" autocomplete="off">
										<p class="logostyle style2">正方形LOGO</p> 
									</label></li>
									<li class="transition">
										<label class="optradio">
											<input type="radio" name="options" id="option3" autocomplete="off">
											<p class="logostyle style3">正方形LOGO + 標題文字 </p> 				</label>
										<div class="setadtitle">
											<div>
												<span class="tit">標題文字：</span>
												<input type="text" placeholder="限17字" maxlength="17">
												<span class="charactertext">已輸入0字，剩17字</span>
												<em class="notetext">請輸入文字</em>
											</div>

											<div>
												<span class="tit">標題文字顏色：</span>
												<span class="colorpickr" style="display: block;">
													<input onchange="changeBackgroundColor(this)" class="color {pickerPosition:'right'}" value="#000000">
												</span>
											</div>
										</div>	
									</li>
								</ul>			
								
							</li>														
						</ul><!--newtbsublist end -->
						
						<div class="logobannerbx">

							<b class="logobnr-togglebtn">進階選項1</b>

							<div class="uploadbx">

								<ul class="adlogostylebx">
									<li>									
										<p class="lgbnrtit">行銷圖像 <a href="#"><img src="<@s.url value="/" />html/img/question.gif"></a>：
											<input type="button" id="fileButton" name="fileButton" value="瀏覽檔案" onclick="fileLoad()" >
										</p> 
										<a href="#" target="" onclick="approveSize('bannerDiv');" style="font-size: 12px; margin-left: 5px">支援規格查詢 </a>
									</p></li>
								</ul>

								<ul class="adbannerpicbx">


									<li class="transition">
										<div class="picuploadtb">
											<div class="del transition"></div>
											<div class="picuploadcell">
												<img src="https://f.ecimg.tw/items/DICA40A90081ZJS/000001_1492659986.jpg">
											</div>
										</div>
										<p class="adbnrinfo">
											summerhappy_happyhappy.png
											<span>120 x 50 ‧ PNG</span>
										</p>
									</li>

									<li class="transition">
										<div class="picuploadtb">
											<div class="del transition"></div>
											<div class="picuploadcell">
												<img src="https://e.ecimg.tw/img/h24/v3/layout/index/1/20180824100622_b3-b1-250x165.jpg">
											</div>
										</div>
										<p class="adbnrinfo">
											summerhappy_happyhappy.png
											<span>120 x 50 ‧ PNG</span>
										</p>
									</li>

									<li class="transition">
										<div class="picuploadtb">
											<div class="del transition"></div>
											<div class="picuploadcell">
												<img src="https://media-01.creema.net/user/1259923/exhibits/3290416/1_a247a91c1e6a8c9896515338df861d3b_583x585.jpg">
											</div>
										</div>
										<p class="adbnrinfo">
											summerhappy_happyhappy.png
											<span>120 x 50 ‧ PNG</span>
										</p>
									</li>

									<li class="transition">
										<div class="picuploadtb">
											<div class="del transition"></div>
											<div class="picuploadcell">
												<img src="https://adpic.pchome.com.tw/adpics/pic_1186234_688541.jpg">
											</div>
										</div>
										<p class="adbnrinfo">
											summerhappy_happyhappy.png
											<span>120 x 50 ‧ PNG</span>
										</p>
									</li>

									<li class="transition">
										<div class="picuploadtb">
											<div class="del transition"></div>
											<div class="picuploadcell">
												<img src="https://e.ecimg.tw/img/h24/v3/layout/index/1/20180824100622_b3-b1-250x165.jpg">
											</div>
										</div>
										<p class="adbnrinfo">
											summerhappy_happyhappy.png
											<span>120 x 50 ‧ PNG</span>
										</p>
									</li>
								</ul>
							</div>
						</div>
					</td>

					<td class="adpreview" rowspan="3" width="650">
						<div class="adpreviewbx" id="adpreviewbx">	
						<div class="previewselectsize">
							<span>預覽廣告尺寸</span>
							<div class="adsizeselect">
								<select name="" id="">
									<option value="1">120 x 600</option>	
									<option value="2">140 x 300</option>	
									<option value="3">160 x 240</option>
									<option value="4">160 x 600</option>
									<option value="5">180 x 150</option>

									<option value="6">250 x 80</option>	
									<option value="7">300 x 100</option>	
									<option value="8">300 x 250</option>
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

						<div class="adcontainr">
							<div class="adcontent">	

								<!--ad preview start-->							
								<div style="width: 300px; height: 250px;">
									<img src="img/ad300250.gif">
								</div>
								<!--ad preview end-->	

							</div>							
						</div>
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
									<select name="" id="">
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
									<input onchange="changeBackgroundColor(this)" class="color {pickerPosition:'right'}" value="#055dcb">
								</span>
							</li>
							<li>
								<span class="newtbsubliststyle">●</span>								
								<span class="colorpickr">按鈕底色：
									<input onchange="changeBackgroundColor(this)" class="color {pickerPosition:'right'}" value="#FFFFFF">
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
									<select name="" id="">
										<option value="1">無</option>
										<option value="2">中文折數 ( ex : 75折 )</option>
										<option value="3">百分比折數 ( ex : -25% )</option>				
									</select>
								</div>
								
							</li>
							<li>
								<span class="newtbsubliststyle">●</span>								
								<span class="colorpickr">文字顏色：
									<input onchange="changeBackgroundColor(this)" class="color {pickerPosition:'right'}" value="#055dcb">
								</span>
							</li>
							<li>
								<span class="newtbsubliststyle">●</span>								
								<span class="colorpickr">按鈕底色：
									<input onchange="changeBackgroundColor(this)" class="color {pickerPosition:'right'}" value="#FFFFFF">
								</span>
							</li>
						</ul>									
					</td>
				</tr>
			</tbody></table>



			<div class="advancedtbbx open">

			<b class="logobnr-togglebtn">進階選項 <i></i></b>

			<div class="advancedtb">

			<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02 newtb"><tbody>
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

				<tr>
					<th>結尾行銷圖像</th>
					<td>

						<div class="uploadbx">

								<ul class="adlogostylebx" style="padding: 0;">
									<li>									
										<p class="lgbnrtit adendpic">結尾行銷圖像 <a href="#"><img src="<@s.url value="/" />html/img/question.gif"></a>：										
											<input type="button" id="fileButton" name="fileButton" value="瀏覽檔案" onclick="fileLoad()" >
											<input type="file" serialize id="fileupload" name="fileupload"  style="display:none ;" multiple="">
										</p>
										<a href="#" target="" onclick="approveSize('bannerDiv');" style="font-size: 12px; margin-left: 5px">支援規格查詢 </a>
									</li>
								</ul>

								<ul class="adbannerpicbx">

									<li class="transition">
										<div class="picuploadtb">
											<div class="del transition"></div>
											<div class="picuploadcell">
												<img src="https://f.ecimg.tw/items/DICA40A90081ZJS/000001_1492659986.jpg">
											</div>
										</div>
										<p class="adbnrinfo">
											summerhappy_happyhappy.png
											<span>120 x 50 ‧ PNG</span>
										</p>
									</li>

									<li class="transition">
										<div class="picuploadtb">
											<div class="del transition"></div>
											<div class="picuploadcell">
												<img src="https://e.ecimg.tw/img/h24/v3/layout/index/1/20180824100622_b3-b1-250x165.jpg">
											</div>
										</div>
										<p class="adbnrinfo">
											summerhappy_happyhappy.png
											<span>120 x 50 ‧ PNG</span>
										</p>
									</li>

									<li class="transition">
										<div class="picuploadtb">
											<div class="del transition"></div>
											<div class="picuploadcell">
												<img src="https://media-01.creema.net/user/1259923/exhibits/3290416/1_a247a91c1e6a8c9896515338df861d3b_583x585.jpg">
											</div>
										</div>
										<p class="adbnrinfo">
											summerhappy_happyhappy.png
											<span>120 x 50 ‧ PNG</span>
										</p>
									</li>

									<li class="transition">
										<div class="picuploadtb">
											<div class="del transition"></div>
											<div class="picuploadcell">
												<img src="https://adpic.pchome.com.tw/adpics/pic_1186234_688541.jpg">
											</div>
										</div>
										<p class="adbnrinfo">
											summerhappy_happyhappy.png
											<span>120 x 50 ‧ PNG</span>
										</p>
									</li>
									
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
            <input type="button" id="cancel" value="取 消" class="btn-cancel"> 
            <input type="button" id="save" value="送出審核" class="btn-save">
		</center>
	</form>
	<iframe id="uploadIMG" name="uploadIMG" style="display:none;height:150px;width:600px"></iframe>
	<iframe id="doAdd" name="doAdd" style="display:none;height:150px;width:600px"></iframe>

<input type="hidden" id="messageId" value="">
  
</div>


CCCCCCCCC>${alex!}
<input type="hidden" id="messageId" value="${message!!}">