<#assign s=JspTaglibs["/struts-tags"]>

			<div style="clear:both;height:0px"></div>
			<h4>製作廣告</h4>
			<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
				<tbody>
					<tr id="imgTr" name="imgTr">
						<th height="35" style="width:12%;"><span class="t_s02">* </span>廣告標題</th>
						<td style="min-width:250px;">
							<input type="hidden" id="adDetailID" name="adDetailID" value="title">
							<input type="hidden" id="adDetailContent" name="adDetailContent" value="adTitle"><br>
							<input type="text" id="adTitle" name="adTitle" style="width:96%;" value="" placeholder="PChome關鍵字廣告 全新登場" maxlength="17"><br>
							<span style="float:right" id="spanAdTitle">已輸入0字，剩17字</span>
						</td>
						<td style="background:#dadada;" rowspan="4" width="550">
							<!--播放預覽start-->
							<div style="display:block;" class="boxpreview">
								<h5>廣告預覽</h5>
								<!--<center><a href="3">參考其他人撰寫關鍵字廣告</a></center>-->
								<div class="ADsys_text">
									<span>
										<a href="#" id="previewTitle" name="previewTitle">PChome關鍵字廣告 全新登場</a>
										<p id="previewContent" name="previewContent">讓您的廣告受到世界矚目、訂單多到接不完！立即使用PChome關鍵字廣告‎。</p>
										<a href="#" id="previewURL" class="link">show.pchome.com.tw</a>
									</span>
								</div>
								<div class="adpreviewbg">
		                        	<h5>曝光示意圖</h5>
		                        	<div  class="adpreview" style="width:300px"><img width="300px" border="0" src="<@s.url value="/" />html/img/searchpic01.jpg"></div>
		                        </div>
							</div>
							<!--廣告預覽end-->
						</td>
					</tr>
					<tr>
						<th height="35" style="width:12%"><span class="t_s02">* </span>廣告內容</th>
						<td style="min-width:250px;">
							<input type="hidden" id="adDetailID" name="adDetailID" value="content">
							<input type="hidden" id="adDetailContent" name="adDetailContent">
							<textarea  style="width:96%;" id="adContent" name="adContent" maxlength="38" onkeypress="if(event.keyCode==13) return false;" placeholder="讓您的廣告受到世界矚目、訂單多到接不完！立即使用PChome關鍵字廣告‎。"></textarea><br>
	              			<span style="float:right" id="spanAdContent">已輸入0字，剩38字</span>
	              		</td>
					</tr>
					<tr>
						<th height="35" style="width:12%;"><span class="t_s02">* </span>廣告連結網址</th>
						<td style="min-width:250px;">
							<input type="hidden" id="adDetailID" name="adDetailID" value="real_url">
							<input type="hidden" id="adDetailName" name="adDetailName" value="廣告連結網址">
							<input type="hidden" id="adDetailContent" name="adDetailContent">
							<input type="text" id="adLinkURL" name="adLinkURL" style="width:96%;" placeholder="show.pchome.com.tw"  maxlength="1024"><br>
							<span id="chkLinkURL" name="chkLinkURL" style="color:red"></span><span style="float:right" id="spanAdLinkURL">已輸入0字，剩1024字</span>
						</td>
					</tr>
					<tr>
						<th height="35" style="width:12%;"><span class="t_s02">* </span>廣告顯示網址</th>
						<td style="min-width:250px;">
							<input type="hidden" id="adDetailID" name="adDetailID" value="show_url">
							<input type="hidden" id="adDetailName" name="adDetailName" value="廣告顯示網址">
							<input type="hidden" id="adDetailContent" name="adDetailContent">
							<input type="checkbox" id="sameRealUrl" name="sameRealUrl">與廣告連結網址相同<br>
							<input type="text" id="adShowURL" name="adShowURL" style="width:96%;" placeholder="show.pchome.com.tw" maxlength="30">
							<span id="chkShowURL" name="chkShowURL" style="float:righ;color:red"></span>
							<span style="float:right" id="spanAdShowURL">已輸入0字，剩30字</span>
						</td>
					</tr>
				</tbody>
			</table>
			<span class="t_s01">提醒您，於網路中張貼或散佈色情圖片是觸法行為，違者可處二年以下有期徒刑、拘役或併科三萬元以下罰金</span>
