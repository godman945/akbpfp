<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
			<!-- IE 沒有 placeholder 效果，用此 code 模擬 placeholder(Jack指導版) --> 
			<!--[if IE]>
			<script language="JavaScript" src="<@s.url value="/" />html/js/ad/simuPlaceholderTmg.js" ></script>
			<![endif]-->
			<!-- IE 沒有 placeholder 效果，用此 code 模擬 placeholder(Jack指導版) --> 
  			<div style="clear:both;height:0px"></div>
			<h4>製作廣告</h4>
			<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
				<tbody>
					<tr>
						<th height="35" style="width:12%"><a name="errAdTitle"></a><span class="t_s02">* </span>影音廣告</th>
						<td style="min-width:250px;">
							<input type="hidden" id="adDetailID" name="adDetailID" value="title">
							<input type="hidden" id="adDetailContent" name="adDetailContent" value="adTitle"><br>
							<input type="text" class="inputPlaceholderTmg" id="videoUrl" name="videoUrl" style="width:96%;" placeholder="請輸入youTube影片網址"><br>
							<div id="shownotes1" style="display: none;" class="adnoticepop">
								<h4>廣告標題編輯建議</h4>
								<div class="adpopcont">廣告標題太短，將影響您的廣告效果。<br><br>廣告標題應重點說明您推廣的產品、活動、服務，依您的目標客群，撰寫他們有興趣的廣告標題。</div>
								<a onclick="closenots(1)" style="cursor:pointer;" class="adpopclose">關閉</a>
							</div>
						</td>
						<td style="background:#dadada;" rowspan="5" width="550">
							<!--播放預覽start-->
							<div style="display:block;" class="boxpreview">
								<h5>廣告預覽</h5>
								<!--<center><a href="3">參考其他人撰寫關鍵字廣告</a></center>-->
								<div class="rstli" style="width:500px;margin:0 auto">
									<span><img id="previewImg" src="<@s.url value="/" />html/img/upl9090.gif" onerror="this.src='<@s.url value="/" />html/img/upl9090.gif'"></span>
									<h2 style="background:none"><a href="#"  id="previewTitle" name="previewTitle" title="PChome關鍵字廣告 全新登">PChome關鍵字廣告 全新登場</a></h2>
									<span id="previewContent" name="previewContent">讓您的廣告受到世界矚目、訂單多到接不完！立即使用PChome關鍵字廣告‎。</span>
									<h5 id="previewURL">show.pchome.com.tw</h5>
								</div>
								<div class="adpreviewbg" >
									<h5>曝光示意圖</h5>
									<div  class="adpreview"><img width="300" border="0" src="<@s.url value="/" />html/img/searchpic02.jpg"></div>
								</div>
							</div>
							<!--廣告預覽end-->
						</td>
					</tr>
					<tr>
						<th height="35" style="width:12%;"><a name="errAdLinkURL"></a><span class="t_s02">* </span>廣告連結網址</th>
						<td style="min-width:250px;">
							<input type="hidden" id="adDetailID" name="adDetailID" value="real_url">
							<input type="hidden" id="adDetailName" name="adDetailName" value="廣告連結網址">
							<input type="hidden" id="adDetailContent" name="adDetailContent">
							<input type="text" class="inputPlaceholderTmg" id="adLinkURL" name="adLinkURL" style="width:96%;" placeholder="show.pchome.com.tw"  maxlength=""><br>
						</td>
					</tr>
				</tbody>
			</table>
			<span class="t_s01">提醒您，於網路中張貼或散佈色情圖片是觸法行為，違者可處二年以下有期徒刑、拘役或併科三萬元以下罰金</span>
