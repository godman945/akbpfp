<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adAddTmgManyURL.js?t=20180222001"></script>

<div style="clear:both;height:0px"></div>
<h4>製作廣告</h4>
<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
	<tbody>
		<tr>
			<th height="35" style="width:12%">店家刊登商品網址</th>
			<td style="min-width:250px;background:#f9f9f9">
				<span style="color: #ff3300;padding: 3px 0">* 請輸入店家賣場商品頁，系統自動偵測網站商品內容! (僅限PChome24h購物、商店街、個人賣場(商店街)、露天賣家。)</span><br>
				<input type="text" class="inputPlaceholderTmg" id="storeProductURL" name="storeProductURL" style="width:96%;" placeholder="Ex:http://show.pchome.com.tw"><br>
				<span id="chkStoreProductURL" name="chkStoreProductURL" style="color:rgb(255, 0, 0); display:none;">僅限PChome24h購物、商店街、個人賣場(商店街)、露天賣家網址!</span>
			</td>
			<td style="background:#dadada;" width="550">
				<#--播放預覽start-->
				<div class="boxpreview">
					<h5>廣告預覽</h5>
					<div class="rstli" style="width:500px;margin:0 auto">
						<span><img id="previewImg" src="<@s.url value="/" />html/img/upl9090.gif" onerror="this.src='<@s.url value="/" />html/img/upl9090.gif'"></span>
						<h2 style="background:none"><a href="javascript:void(0);" id="previewTitle" name="previewTitle" title="PChome關鍵字廣告 全新登場">PChome關鍵字廣告 全新登場</a></h2>
						<span id="previewContent" name="previewContent">讓您的廣告受到世界矚目、訂單多到接不完！立即使用PChome關鍵字廣告‎。</span>
						<h5 id="previewURL">show.pchome.com.tw</h5>
					</div>
					<div class="adpreviewbg">
						<h5>曝光示意圖</h5>
						<div class="adpreview">
							<img width="300" border="0" src="<@s.url value="/" />html/img/searchpic02.jpg">
						</div>
					</div>
				</div>
				<#--廣告預覽end-->
			</td>
		</tr>
	</tbody>
</table>
<span class="t_s01">提醒您，於網路中張貼或散佈色情圖片是觸法行為，違者可處二年以下有期徒刑、拘役或併科三萬元以下罰金</span><br>

<div class="queryResult" style="clear:both; height:10px; display:none;"></div>

<#-- 選擇上稿商品 start-->
<h4 class="queryResult" style="display:none;">選擇上稿商品</h4>

<#-- 搜尋尚未開放(待製作)
<div class="queryResult" style="clear:both">
	<table cellspacing="3" cellpadding="0" border="0" class="srchtb">
		<tbody>
			<tr>
				<td>
					<input type="text" id="keyword" value="搜尋賣場商品" name="" style="width:400px;">
				</td>
				<td><a href="#" id="search" muse_scanned="true"><img border="0" src="http://show.pchome.com.tw/html/img/srchbtn.gif"></a></td>
			</tr>
		</tbody>
	</table>
</div>
-->

<#-- ajax查詢的結果顯示區塊 -->
<div id="loadingWaitBlock">
	<div id="searchURLDetailsTable">
	</div>
</div>


<#--新增商品網址button start-->
<span class="queryResult" style="padding:10px;display:none;">
	<input type="button" id="addStoreProductURLbtn" name="addStoreProductURLbtn" onclick="showAddStoreProductURL();" value="新增商品網址">
</span>
<#--新增商品網址button end-->

<#--新增網址 start-->
<table class="tb02 addStoreProductURL" width="100%" cellspacing="1" cellpadding="0" border="0" style="display: none;">
	<tbody>
    	<tr>
			<td style="padding:15px;min-width:250px;background:#f9f9f9">
				<input type="text" class="inputPlaceholderKeyword" style="width:72%" data-value="請輸入店家商品網址" id="confirmAddURL" name="confirmAddURL" placeholder="請輸入店家商品網址">
				<input type="button" id="confirmAddURLbtn" name="confirmAddURLbtn" value="確認新增"><br>
				<span id="chkConfirmAddURL" name="chkConfirmAddURL" style="color:rgb(255, 0, 0); display:none;">僅限PChome24h購物、商店街、個人賣場(商店街)、露天賣家網址!</span>
			</td>
		</tr>
	</tbody>
</table>     
<#--新增網址 end-->

<div class="queryResult" style="clear:both; height:10px; display:none;"></div>
<#-- 選擇上稿商品 end-->

<div class="queryResult" style="height:50px; display:none;">
	<span class="apply-element-txt">已選擇: <b class="checkboxCount-down">0</b> 筆商品物件刊登</span>
</div>