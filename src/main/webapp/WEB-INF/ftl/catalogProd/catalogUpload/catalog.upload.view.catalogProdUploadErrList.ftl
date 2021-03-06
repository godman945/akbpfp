<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<script language="JavaScript" src="<@s.url value="/" />html/js/catalogProd/commonCatalogProd.js?t=20181228001"></script>
<script language="JavaScript" src="<@s.url value="/" />html/js/catalogProd/catalogUpload/catalogProdUploadErrList.js?t=20181228001"></script>

<div class="container-prodmanage">
	<input type="hidden" id="pageNo" name="pageNo" value="${pageNo!}">
	<input type="hidden" id="pageCount" name="pageCount" value="${pageCount!}">
	<input type="hidden" id="catalogUploadLogSeq" name="catalogUploadLogSeq" value="${catalogUploadLogSeq!}">
	
    <#-- 次目錄導覽列 開始 -->
    <#-- hidden 隱藏所有牙齒 -->
    <#-- hidetabs 只顯示第一顆牙齒 -->
    <#-- tab1 tab2 tab3 tab4 tab5 牙齒由左至右底線 -->
    <div class="nav-wrap pos-relative">
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
                <li class="txt-cell pos-relative"><a href="selectUpload.html?catalogSeq=${catalogSeq!}">商品資料</a></li>
                <li class="txt-cell pos-relative"><a href="setup.html?catalogSeq=${catalogSeq!}">設定</a></li>
            </ul>
            <div class="altername-box pos-absolute pos-right pos-top"><span>帳戶：</span>${customer_info_title}</div>
        </div>
    </div>
    <#-- 次目錄導覽列 結束 -->

    <div class="content-wrap p-lr60 transition-all">
        <div class="content-box p-none">

            <#-- 表格目錄功能列 開始 -->
            <div class="nav-wrap prodtable">
                <div class="nav-box pos-relative">
                    <ul class="txt-table txt-regular">
                        <li class="txt-cell p-r20" style="font-size:16px">共<em class="txt-quantity">${totalCount}</em>筆上傳失敗商品</li>
                    </ul>
                </div>
            </div>
            <#-- 表格目錄功能列 結束 -->

            <#-- 表格內容 開始 -->
            <div class="prodtable-wrap m-b30 floatingscroll">
                <div class="prodtable-box txt-noselect">
                    <div class="txt-table w-full">
                        
                        <#-- 表格欄位標題 -->
                        <div class="txt-row header">
                            <div class="txt-cell col-number     ">項目</div>
                            <div class="txt-cell col-serial     ">商品編號</div>
                            <div class="txt-cell col-prodname   ">商品名稱</div>
                            <div class="txt-cell col-listprice  ">原價</div>
                            <div class="txt-cell col-promoprice ">特價</div>
                            <div class="txt-cell col-supplement ">供應情況</div>
                            <div class="txt-cell col-neworused  ">使用狀況</div>
                            <div class="txt-cell col-picture    ">商品圖</div>
                            <div class="txt-cell col-class      ">類別</div>
                            <div class="txt-cell col-weburl     ">商品網址</div>
                        </div>
						
						<#list catalogProdUploadErrDataList as vo>
							<div class="txt-row" data-type="error">
								<div class="txt-cell col-number     ">${vo.catalogProdErrItem!}</div>
								
								<#if vo.catalogProdSeqErrstatus == "isErr">
	                            	<div class="txt-cell col-serial     "><b class="txt-error">${vo.catalogProdSeq!}</b></div>
	                            <#else>
	                            	<div class="txt-cell col-serial     ">${vo.catalogProdSeq!}</div>
	                            </#if>

								<#if vo.ecNameErrstatus == "isErr">
									<div class="txt-cell col-prodname   "><b class="txt-error">${vo.ecName!}</b></div>
								<#else>
	                            	<div class="txt-cell col-prodname   ">${vo.ecName!}</div>
	                            </#if>
	                            
	                            <#if vo.ecPriceErrstatus == "isErr">
	                            	<div class="txt-cell col-listprice  "><b class="txt-error">${vo.ecPrice!}</b></div>
								<#elseif vo.ecPrice != "">
									<div class="txt-cell col-listprice  "><span>$</span>${vo.ecPrice?number?string('#,###')!}</div>
								<#else>
									<div class="txt-cell col-listprice  "></div>
								</#if>
	                            
	                            <#if vo.ecDiscountPriceErrstatus == "isErr">
	                            	<div class="txt-cell col-promoprice "><b class="txt-error">${vo.ecDiscountPrice!}</b></div>
								<#else>
									<div class="txt-cell col-promoprice "><span>$</span>${vo.ecDiscountPrice?number?string('#,###')!}</div>
								</#if>
	                            
	                            <#if vo.ecStockStatusErrstatus == "isErr">
	                            	<div class="txt-cell col-supplement "><b class="txt-error">${vo.ecStockStatus!}</b>
	                            		<div class="notice-btn" onclick="$(this).children('em').fadeToggle('fast');">
                                        	<em style="display: none;">僅支援:有庫存/預購/無庫存/停售。</em>
                                    	</div>
	                            	</div>
								<#else>
									<div class="txt-cell col-supplement ">${vo.ecStockStatus!}</div>
								</#if>
	                            
	                            <#if vo.ecUseStatusErrstatus == "isErr">
	                            	<div class="txt-cell col-neworused  "><b class="txt-error">${vo.ecUseStatus!}</b>
	                            		<div class="notice-btn" onclick="$(this).children('em').fadeToggle('fast');">
                                        	<em style="display: none;">僅支援:全新/福利品/二手。</em>
                                    	</div>
	                            	</div>
								<#else>
									<div class="txt-cell col-neworused  ">${vo.ecUseStatus!}</div>
								</#if>
	                            
	                            <#if vo.ecImgErrstatus == "isErr">
	                            	<div class="txt-cell col-picture    "><b class="txt-error">${vo.ecImg!}</b></div>
								<#else>
									<div class="txt-cell col-picture    "><img src="${vo.ecImg!}"></div>
								</#if>
	                            
	                            <#if vo.ecCategoryErrstatus == "isErr">
	                            	<div class="txt-cell col-class      "><b class="txt-error">${vo.ecCategory!}</b></div>
								<#else>
									<div class="txt-cell col-class      ">${vo.ecCategory!}</div>
								</#if>
	                            
	                            <#if vo.ecUrlErrstatus == "isErr">
	                            	<div class="txt-cell col-weburl     "><b class="txt-error">${vo.ecUrl!}</b></div>
								<#else>
									<div class="txt-cell col-weburl     "><a href="${vo.ecUrl!}"></a></div>
								</#if>
	                            
							</div>
						</#list>

                    </div>
                </div>
            </div>
            <#-- 表格內容 結束 -->

            <#-- 頁碼 pagination 開始 -->
            <#-- data-order: 目前頁碼 -->
            <#-- data-quantity: 頁數 -->
            <div class="pagination-wrap txt-noselect m-b30" data-order="79" data-quantity="150">
                <#-- data-num: 頁碼 -->
                <ul class="pagination-box txt-table">
                
                </ul>
            </div>
            <#-- 頁碼 pagination 結束 -->

        </div>
    </div>
</div>