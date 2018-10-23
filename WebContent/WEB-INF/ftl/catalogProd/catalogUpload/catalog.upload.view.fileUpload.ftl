<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<script language="JavaScript" src="<@s.url value="/" />html/js/catalogProd/commonCatalogProd.js?t=20181023004"></script>
<script language="JavaScript" src="<@s.url value="/" />html/js/catalogProd/catalogUpload/fileUpload.js?t=20181023004"></script>

<form id="fileUploadForm" name="fileUploadForm" method="post" enctype="multipart/form-data" action="catalogUploadCheckFileData.html">
	<input type="file" id="fileUpload" name="fileUpload" accept=".csv" onchange="checkFile(this)" >
	
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
		
		            <#-- 上傳CSV 開始 -->
		            <div class="section-box">
		                <p class="title-box h2">建立商品資料</p>
		                <div class="scvupload-wrap">
		                    <#-- select  :選擇檔案  -->
		                    <#-- progress:上 傳 中  -->
		                    <#-- success :上傳成功  -->
		                    <#-- failure :上傳失敗  -->
		                    <div class="scvupload-box select">
		
		                        <#-- 上傳檔案 畫面1 選擇檔案 -->
		                        <div class="txt-table init select">
		                            <div class="txt-cell">
		                                <div class="svg-box medium">
		                                    <object class="svg-img" data="<@s.url value="/" />/html/img/catalogProd/catalogUpload/icon-doc-csv.svg" type="image/svg+xml"></object>
		                                </div>
		                                <u>將檔案拖放到這裡，或 <a href="javascript:void(0);" onclick="fileUpload();">選擇要上傳的檔案</a></u>
		                                <br><small>支援檔案格式為 .csv，請參考範例檔案。<br>檔案上傳成功後，請至商品清單查看結果。</small>
		                            </div>
		                        </div>
		
		                        <#-- 上傳檔案 畫面2 上傳中 -->
		                        <div class="txt-table progress">
		                            <div class="txt-cell">
		                                <div class="step-box txt-left">
		                                    <div class="svg-box medium">
		                                        <object class="svg-img" data="<@s.url value="/" />/html/img/catalogProd/catalogUpload/upload-csv.svg" type="image/svg+xml"></object>
		                                    </div>
		                                    <div class="txt-filename"></div>
		                                    <em class="icon-check"></em>
		                                </div>
		                                <div class="progress-bar"><b id="progressBar" style="width:0%"></b></div>
		                                <small>上傳中...(<em id="uploadPercentage">0</em>% )</small>
		                            </div>
		                        </div>
		
		                        <#-- 上傳檔案 畫面3 上傳成功 -->
		                        <div class="txt-table success">
		                            <div class="txt-cell">
		                                <div class="step-box txt-left">
		                                    <div class="svg-box medium">
		                                        <object class="svg-img" data="<@s.url value="/" />/html/img/catalogProd/catalogUpload/upload-csv.svg" type="image/svg+xml"></object>
		                                    </div>
		                                    <div class="txt-filename"></div>
		                                    <em class="icon-check ok"></em>
		                                </div>
		                                <div class="progress-bar" data-percent"100%"><b style="width:100%"></b></div>
		                                <u>檔案上傳成功</u><br><small>若要重新上傳，請將檔案拖放到這裡，或<a href="javascript:void(0);" onclick="fileUpload();">選擇要上傳的檔案</a></small>
		                            </div>
		                        </div>
		
		                        <#-- 上傳檔案 畫面4 上傳失敗 -->
		                        <div class="txt-table failure">
		                            <div class="txt-cell">
		                                <div class="step-box txt-left">
		                                    <div class="svg-box medium">
		                                        <object class="svg-img" data="<@s.url value="/" />/html/img/catalogProd/catalogUpload/icon-doc-csv.svg" type="image/svg+xml"></object>
		                                    </div>
		                                    <div class="txt-filename"></div>
		                                    <em class="icon-check error"></em>
		                                </div>
		                                <div class="progress-bar" data-percent"0%"><b style="width:0%"></b></div>
		                                <u>檔案格式錯誤</u><br><small>重新上傳檔案，請將檔案拖放到這裡，或<a href="javascript:void(0);" onclick="fileUpload();">選擇要上傳的檔案</a></small>
		                            </div>
		                        </div>
		
		                    </div>
		                    <div class="link-download p-t5 txt-right"><a href="catalogSampleFileDownload.html"><em>下載範例</em></a></div>
		                </div>
		            </div>
		            <#-- 上傳方式 結束 -->
		
		            <div class="p-t30">
						<div class="datarewrite-wrap">
						    <div class="inputradio-box txt-noselect">
						        <div class="input-radio">
						            <input type="radio" value="1" id="pvLimitSelect1" name="updateWay" checked>
						            <label for="pvLimitSelect1"></label>
						            <span>取代 <u>- 新檔案會完全取代目前的檔案，未在新檔案中的商品將會被刪除。</u></span>
						        </div>
						    </div>
						
						    <div class="inputradio-box txt-noselect">
						        <div class="input-radio">
						            <input type="radio" value="2" id="pvLimitSelect2" name="updateWay">
						            <label for="pvLimitSelect2"></label>
						            <span>更新 <u>- 新檔案會新增或更新現有的商品，不會刪除商品。</u></span>
						        </div>
						    </div>
						</div>
		            </div>
		        </div>
		
		        <div class="button-box w-900 txt-center p-tb60">
		            <div class="link-button"><a href="javascript:history.go(-1);">上一步</a></div>
		            <div class="input-button"><input type="button" onclick="fileUploadFinish();" value="送出審核"></div>
		        </div>
		
		    </div>
		    <#-- 選擇商品資料來源 結束 -->
		</div>
	
	</div>
</form>