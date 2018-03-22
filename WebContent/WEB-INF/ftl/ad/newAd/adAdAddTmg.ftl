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
						<th height="35" style="width:12%"><a name="errAdImg"></a>廣告圖片</th>
						<td style="background:#f9f9f9;">
							<span class="imgar" style="width:100%;">
								<span style="display:block;width:115px;float:left"><img id="imghead" style="width:90px;height:90px;" src="<@s.url value="/" />html/img/upl9090.gif" onerror="this.src='<@s.url value="/" />html/img/upl9090.gif'"></span>                    	
								<!--上傳圖片start-->
								<div style="text-align:left;">請上傳250x250以上，1024Kb以內jpg、png或gif格式的正方形圖片<br>
									<input type="hidden" id="adDetailID" name="adDetailID" value="img">
									<input type="hidden" id="adDetailName" name="adDetailName" value="廣告圖片">
									<input type="hidden" id="adDetailContent" name="adDetailContent" value="">
									<input type="button" id="fileButton" name="fileButton" value="瀏覽檔案" onclick="fileLoad()" >
									<input type="file" id="uploadFile" name="uploadFile" onchange="previewImage(this)"></br>
									<span id="chkFile" name="chkFile" style="color:red;size:5"></span><br />
									<input type="button" name="delImg" id="delImg" value="刪除圖片" onclick="deleteImage();" />
									<input type="hidden" id="imgType" name="imgType" value="">
									<div style="color:red;display:none;" id="sizeCheckDiv">請檢查檔案大小是否大於1024KB</div>
									<div style="color:red;display:none;" id="uploadCheckDiv">請上傳一個檔案</div>
								</div>
							</span>
						</td>
						<td style="background:#dadada;" rowspan="7" width="550">
							<!--播放預覽start-->
							<div style="display:block;" class="boxpreview">
								<h5>廣告預覽</h5>
								<!--<center><a href="3">參考其他人撰寫關鍵字廣告</a></center>-->
								<div class="rstli" style="width:500px;margin:0 auto">
									<span><img id="previewImg" src="<@s.url value="/" />html/img/upl9090.gif" onerror="this.src='<@s.url value="/" />html/img/upl9090.gif'"></span>
									<h2 style="background:none"><a href="#"  id="previewTitle" name="previewTitle" title="PChome關鍵字廣告 全新登場">PChome關鍵字廣告 全新登場</a></h2>
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
						<th height="35" style="width:12%"><a name="errAdTitle"></a><span class="t_s02">* </span>廣告標題</th>
						<td style="min-width:250px;">
							<input type="hidden" id="adDetailID" name="adDetailID" value="title">
							<input type="hidden" id="adDetailContent" name="adDetailContent" value="adTitle"><br>
							<input type="text" class="inputPlaceholderTmg" id="adTitle" name="adTitle" style="width:96%;" placeholder="PChome關鍵字廣告 全新登場" maxlength="17"><br>
							<span id="chkAdTitle" name="chkAdTitle" style="float:left;color:red;size:5"></span><span style="float:right" id="spanAdTitle">已輸入0字，剩17字</span>
							<div id="shownotes1" style="display: none;" class="adnoticepop">
								<h4>廣告標題編輯建議</h4>
								<div class="adpopcont">廣告標題太短，將影響您的廣告效果。<br><br>廣告標題應重點說明您推廣的產品、活動、服務，依您的目標客群，撰寫他們有興趣的廣告標題。</div>
								<a onclick="closenots(1)" style="cursor:pointer;" class="adpopclose">關閉</a>
							</div>
						</td>
					</tr>
					<tr>
						<th height="35" style="width:12%"><a name="errAdContent"></a><span class="t_s02">* </span>廣告內容</th>
						<td style="min-width:250px;">
							<input type="hidden" id="adDetailID" name="adDetailID" value="content">
							<input type="hidden" id="adDetailContent" name="adDetailContent">
							<textarea  style="width:96%;" class="inputPlaceholderTmgTextarea" id="adContent" name="adContent" maxlength="36" onkeypress="if(event.keyCode==13) return false;" placeholder="讓您的廣告受到世界矚目、訂單多到接不完！立即使用PChome關鍵字廣告‎。"></textarea><br>
	              			<span id="chkAdContent" name="chkAdContent" style="float:left;color:red;size:5"></span><span style="float:right" id="spanAdContent">已輸入0字，剩36字</span>
							<div id="shownotes2" style="display: none;" class="adnoticepop">
								<h4>廣告內容編輯建議</h4>
								<div class="adpopcont">廣告內容太短、說明不清，都將影響您的廣告效果。<br><br>建議用直接、吸睛的文字，清晰具體的描述產品、服務、活動，才能增加您的廣告吸引力，提升廣告成效。</div>
								<a onclick="closenots(2)" style="cursor:pointer;" class="adpopclose">關閉</a>
							</div>
						</td>
					</tr>
					<tr>
						<th height="35" style="width:12%">商品原價</th>
						<td style="min-width:250px;">
							<input type="hidden" id="adDetailID" name="adDetailID" value="sales_price">
							<input type="hidden" id="adDetailName" name="adDetailName" value="商品原價">
							<input type="hidden" id="adDetailContent" name="adDetailContent">
                        	NT$ <input type="text" maxlength="8" id="salesPrice" name="salesPrice">
						</td>
					</tr>
					<tr>
						<th height="35" style="width:12%">商品促銷價</th>
						<td style="min-width:250px;">
							<input type="hidden" id="adDetailID" name="adDetailID" value="promotional_price">
							<input type="hidden" id="adDetailName" name="adDetailName" value="商品促銷價">
							<input type="hidden" id="adDetailContent" name="adDetailContent">
                        	NT$ <input type="text" maxlength="8" id="promotionalPrice" name="promotionalPrice">
						</td>
					</tr>
					<tr>
						<th height="35" style="width:12%;"><a name="errAdLinkURL"></a><span class="t_s02">* </span>廣告連結網址</th>
						<td style="min-width:250px;">
							<input type="hidden" id="adDetailID" name="adDetailID" value="real_url">
							<input type="hidden" id="adDetailName" name="adDetailName" value="廣告連結網址">
							<input type="hidden" id="adDetailContent" name="adDetailContent">
							<input type="text" class="inputPlaceholderTmg" id="adLinkURL" name="adLinkURL" style="width:96%;" placeholder="show.pchome.com.tw"  maxlength=""><br>
							<span id="chkLinkURL" name="chkLinkURL" style="color:red"></span><span style="float:right" id="spanAdLinkURL">已輸入0字，剩1024字</span>
						</td>
					</tr>
					<tr>
						<th height="35" style="width:12%;"><a name="errAdShowURL"></a><span class="t_s02">* </span>廣告顯示網址</th>
						<td style="min-width:250px;">
							<input type="hidden" id="adDetailID" name="adDetailID" value="show_url">
							<input type="hidden" id="adDetailName" name="adDetailName" value="廣告顯示網址">
							<input type="hidden" id="adDetailContent" name="adDetailContent">
							<input type="checkbox" id="sameRealUrl" name="sameRealUrl">廣告對外顯示網址同廣告連結網址網域<br>
							<input type="text" class="inputPlaceholderTmg" id="adShowURL" name="adShowURL" style="width:96%;" placeholder="show.pchome.com.tw" maxlength="30"><br>
							<span id="chkShowURL" name="chkShowURL" style="float:righ;color:red"></span>
							<span style="float:right" id="spanAdShowURL">已輸入0字，剩30字</span>
						</td>
					</tr>
				</tbody>
			</table>
			<span class="t_s01">提醒您，於網路中張貼或散佈色情圖片是觸法行為，違者可處二年以下有期徒刑、拘役或併科三萬元以下罰金</span>
