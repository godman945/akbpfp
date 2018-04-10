<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adAddTmgManyURL.js?t=20180410001"></script>

<div style="clear:both;height:0px"></div>
<h4>製作廣告</h4>

<div id="loadingWaitBlock">

	<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
		<tbody>
			<tr>
				<th height="35" style="width:12%">店家刊登商品網址</th>
				<td style="min-width:250px;background:#f9f9f9">
					<span style="color:#777; padding: 3px 0">* 請輸入店家賣場商品頁，系統自動偵測網站商品內容! (僅限PChome24h購物、商店街、個人賣場(商店街)、露天賣家。)</span><br>
					<input type="text" class="inputPlaceholderTmg" id="storeProductURL" name="storeProductURL" style="width:67.7%;" placeholder="Ex:http://show.pchome.com.tw">
					<input type="button" id="topConfirmAddURLbtn" name="topConfirmAddURLbtn" value="確認新增"><br>
					<span id="chkStoreProductURL" name="chkStoreProductURL" style="color:rgb(255, 0, 0); display:none;"></span>
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
	
	<div class="queryResult" id="tableList" style="display:none;">
		<div>
			<span class="apply-element-txt">已選擇: <b class="checkboxCount-up">0</b> 筆商品物件刊登</span>
		
			<span class="pages">
				<input type="hidden" id="contentPath" name="contentPath" value="<@s.url value="/html/img/"/>">
			    <img style="vertical-align:middle" id="fpage" src="<@s.url value="/html/img/"/>page_first.gif" />
				<img style="vertical-align:middle" id="ppage" src="<@s.url value="/html/img/"/>page_pre.gif" />
					<span id="page"></span>/<span id="totalPage"></span> 
				<img style="vertical-align:middle" id="npage" src="<@s.url value="/html/img/"/>page_next.gif" />
				<img style="vertical-align:middle" id="epage" src="<@s.url value="/html/img/"/>page_end.gif" />
				
				&nbsp&nbsp
				
				顯示 
				<select id="pageSizeSelect" name="pageSizeSelect" style="vertical-align:middle" >
					<option>5</option>
					<option>10</option>
					<option>20</option>
					<option>50</option>
				</select> 
				筆
			</span>			
		</div>
	
		<div style="clear:both;height:50%"></div>
	
		<table id="tableView" class="tableManager" width="100%" border="0" cellpadding="0" cellspacing="1" role="grid">
			<thead>
				<tr role="row">
					<th width="50" data-column="0" scope="col" role="columnheader" aria-disabled="true" unselectable="on" aria-sort="none" aria-label="全選: No sort applied, sorting is disabled" style="user-select: none;">
						<div class="tablesorter-header-inner">
							<a href="javascript:void(0)" onclick="checkAll()">全選</a>
						</div>
					</th>
					<th>廣告明細預覽</th>
					<th style="width: 36%; user-select: none;" aria-label="連結網址"><div>連結網址</div></th>
					<th style="width: 9%; user-select: none;" aria-label="原價"><div>原價</div></th>
					<th style="width: 9%; user-select: none;" aria-label="促銷價"><div>促銷價</div></th>
				</tr>
			</thead>
			
			<tbody class="dataDetailTable" aria-live="polite" aria-relevant="all">
				<#-- 每筆資料塞入部分  -->

			</tbody>
		</table>
		
		<#--新增商品網址button start-->
		<span style="padding:10px;display:block;">
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
						<span id="chkConfirmAddURL" name="chkConfirmAddURL" style="color:rgb(255, 0, 0); display:none;"></span>
					</td>
				</tr>
			</tbody>
		</table>     
		<#--新增網址 end-->
		
		<div style="clear:both; height:10px;></div>
		<#-- 選擇上稿商品 end-->
	</div>
	
	<div class="queryResult" style="height:50px;display:none;">
		<span class="apply-element-txt">已選擇: <b class="checkboxCount-down">0</b> 筆商品物件刊登</span>
	</div>
	
	<div class="adnoticepop_24h" id="notes" >
	</div>
</div>
