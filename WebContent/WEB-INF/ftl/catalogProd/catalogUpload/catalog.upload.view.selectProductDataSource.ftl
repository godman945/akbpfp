<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<script language="JavaScript" src="<@s.url value="/" />html/js/catalogProd/commonCatalogProd.js?t=20181023011"></script>
<script language="JavaScript" src="<@s.url value="/" />html/js/catalogProd/catalogUpload/catalogUpload.js?t=20180925001"></script>

<form id="confirmUpload" action="selectUpload.html" method="post">
	<input type="hidden" id="selectUploadFlag" name="selectUploadFlag" value="1"><#-- 選擇上傳方式flag -->
	
	<div class="container-prodmanage">
	    <#-- 次目錄導覽列 開始 -->
	    <#-- hidden 隱藏所有牙齒 -->
	    <#-- hidetabs 只顯示第一顆牙齒 -->
	    <#-- tab1 tab2 tab3 tab4 tab5 牙齒由左至右底線 -->
	    <div class="nav-wrap pos-relative tab4">
	        <div class="nav-box pos-relative">
	            <ul class="txt-table">
	                <li class="txt-cell pos-relative p-r10">
	                    <span class="icon-box list arrow-right">
	                        <a href="catalogProd.html">所有商品目錄</a>
	                        <em class="icon-arrow-r"></em>
	                    </span>
	                    <div class="select-box">
	                        <select id="catalogSeq" name="catalogSeq">
	                        	<#list catalogList as vo>
	                        		<option value="${vo.catalogSeq!}" <#if vo.catalogSeq == catalogSeq>selected</#if> >${vo.catalogName!}</option>
	                        	</#list>
	                        </select>
	                    </div>
	                </li>
	                <li class="txt-cell pos-relative"><a href="prodListCardStyleView.html?catalogSeq=${catalogSeq!}&currentPage=1&pageSizeSelected=10">商品清單</a></li>
	                <li class="txt-cell pos-relative"><a href="queryCatalogGroup.html?catalogSeq=${catalogSeq!}">商品組合</a></li>
	                <li class="txt-cell pos-relative"><a href="#">商品資料</a></li>
	                <li class="txt-cell pos-relative"><a href="setup.html?catalogSeq=${catalogSeq!}">設定</a></li>
	            </ul>
	            <div class="altername-box pos-absolute pos-right pos-top"><span>帳戶：</span>${customer_info_title}</div>
	        </div>
	    </div>
	    <#-- 次目錄導覽列 結束 -->

		<div id="loadingWaitBlock">
		    <#-- 選擇商品資料來源 開始 -->
		    <div class="content-wrap bg-white">
		        <div class="content-box bg-white w-900">
		
		            <#-- 上傳方式 開始 -->
		            <div id="dataupload" class="section-box">
		                <p class="title-box h2 txt-center">請選擇商品資料來源</p>
		                <div class="dataupload-wrap txt-noselect">
		                    <div class="dataupload-box selected">
		                        <div class="svg-box medium">
		                            <object class="svg-img" data="<@s.url value="/" />/html/img/catalogProd/catalogUpload/dataupload-csv.svg" type="image/svg+xml"></object>
		                        </div>
		                        <p>檔案上傳<span>支援檔案格式為 .csv，請參考範例檔案。檔案上傳成功後，請至商品清單查看結果。</span></p>
		                    </div>
		                </div>
		                <div class="dataupload-wrap txt-noselect">
		                    <div class="dataupload-box">
		                        <div class="svg-box medium">
		                            <object class="svg-img" data="<@s.url value="/" />/html/img/catalogProd/catalogUpload/dataupload-cloud.svg" type="image/svg+xml"></object>
		                        </div>
		                        <p>自動排程上傳<span>支援http、https，系統於每日 1:00 AM 執行商品目錄更新。請至商品清單查看每日更新結果。</span></p>
		                    </div>
		                </div>
		                <div class="dataupload-wrap txt-noselect">
		                    <div class="dataupload-box">
		                        <div class="svg-box medium">
		                            <object class="svg-img" data="<@s.url value="/" />/html/img/catalogProd/catalogUpload/dataupload-url.svg" type="image/svg+xml"></object>
		                        </div>
		                        <p>PChome賣場網址上傳<span>使用PChome 24h購物、PChome商店街、PChome商店街 個人賣場、露天拍賣的網址上傳商品資訊，系統每日AM 2:00 自動執行更新一次，請至商品清單查看每日更新結果。</span></p>
		                    </div>
		                </div>
		                <div class="dataupload-wrap txt-noselect">
		                    <div class="dataupload-box">
		                        <div class="svg-box medium">
		                            <object class="svg-img" data="<@s.url value="/" />/html/img/catalogProd/catalogUpload/dataupload-manual.svg" type="image/svg+xml"></object>
		                        </div>
		                        <p>手動上傳<span>手動輸入商品資訊，逐一上傳。</span></p>
		                    </div>
		                </div>
		            </div>
		            <#-- 上傳方式 結束 -->
		
		        </div>
		
		        <div class="button-box w-900 txt-center p-tb60">
		            <div class="input-button"><input type="button" onclick="catalogUploadNext();" value="下一步"></div>
		        </div>
		
		    </div>
		    <#-- 選擇商品資料來源 結束 -->
		</div>
	</div>
</form>