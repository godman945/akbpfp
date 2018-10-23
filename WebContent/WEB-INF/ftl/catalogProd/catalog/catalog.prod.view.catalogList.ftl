<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.ba-dotimeout.js"></script>
<script language="JavaScript" src="<@s.url value="/" />html/js/catalogProd/catalog/catalog.js?t=20181018039"></script>

<div class="container-prodmanage">
	<input type="hidden" id="pageNo" name="pageNo" value="${pageNo!}">
	<input type="hidden" id="pageCount" name="pageCount" value="${pageCount!}">
	
	<#-- 次目錄導覽列 開始 -->
	<div class="nav-wrap pos-relative hidetabs tab1">
	    <div class="nav-box pos-relative">
	        <ul class="txt-table">
	            <li class="txt-cell pos-relative p-r10">
	                <span class="icon-box list arrow-right">
	                    <a href="javascript:void(0);">所有商品目錄</a>
	                </span>
	            </li>
	        </ul>
	        <div class="altername-box pos-absolute pos-right pos-top">
	        	<span>帳戶：</span>${customer_info_title}
			</div>
	    </div>
	</div>
	<#-- 次目錄導覽列 結束 -->
	
	<div class="content-wrap p-lr60 transition-all">
        <div class="content-box p-none">

            <#-- 表格目錄功能列 開始 -->
            <div class="nav-wrap prodtable">
                <div class="nav-box pos-relative">
                    
                    <#-- 新增按鈕 開始 -->
                    <div class="btn-create pos-absolute pos-right">
                        <a id="addCatalog" href="addCatalog.html"><em></em></a>
                        <style>.altername-box{right:80px}</style>
                    </div>
                    <#-- 新增按鈕 結束 -->

                    <ul class="txt-table txt-regular">
                        <li class="txt-cell p-r20">共<em class="txt-quantity">${totalCount}</em>件商品目錄</li>
                        <li class="txt-cell p-r20">
                            <div class="select-box pos-relative">
                                <span class="pos-absolute">顯示</span>
                                <select id="pageSizeSelect">
                                    <option value="10" <#if pageSize == 10>selected</#if> >10則</option>
                                    <option value="20" <#if pageSize == 20>selected</#if> >20則</option>
                                    <option value="30" <#if pageSize == 30>selected</#if> >30則</option>
                                    <option value="40" <#if pageSize == 40>selected</#if> >40則</option>
                                    <option value="50" <#if pageSize == 50>selected</#if> >50則</option>
                                </select>
                            </div>
                        </li>
                        <li class="txt-cell">
                            <div class="input-text">
                                <input type="text" id="queryString" name="queryString" maxlength="20" required placeholder="尋找目錄" value="${queryString}">
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <#-- 表格目錄功能列 結束 -->

            <#-- 表格內容 開始 -->
            <div class="prodtable-wrap m-b30">
                <div class="prodtable-box txt-noselect">
                    <#-- 表格欄位標題 -->
                    <div class="txt-row header">
                        <div class="txt-cell col-cataname">目錄</div>
                        <div class="txt-cell col-catagory">目錄類型</div>
                        <div class="txt-cell col-upload">資料更新方式</div>
                        <div class="txt-cell col-source">資料來源</div>
                        <div class="txt-cell col-success">成功筆數</div>
                        <div class="txt-cell col-failure">失敗筆數</div>
                        <div class="txt-cell col-renew">檔案更新</div>
                        <div class="txt-cell col-delete"></div>
                    </div>

					<#list dataList as vo>
						<div class="txt-row">
							<div class="txt-cell col-cataname">
	                            <a href="prodListCardStyleView.html?catalogSeq=${vo.catalogSeq!}&currentPage=1&pageSizeSelected=10">${vo.catalogName!}</a>
	                            <small>目錄編號：${vo.catalogSeq!}</small>
	                        </div>
	                        
	                        <div class="txt-cell col-catagory">
								${vo.catalogTypeName!}
							</div>
							
							<div class="txt-cell col-upload">
								<#if vo.catalogUploadTypeName??>
	                            	${vo.catalogUploadTypeName!}
	                            <#else>
	                            	-
	                            </#if>
                        	</div>
                        	
                        	<div class="txt-cell col-source">
                        		<#if vo.uploadContent??>
	                            	${vo.uploadContent!}
	                            	<small>最近更新：${vo.updateDatetime!}</small>
	                            <#else>
	                            	-
	                            </#if>
	                        </div>
                        	
                        	<div class="txt-cell col-success">
                        		<#if vo.successNum??>
	                            	${vo.successNum!}<em>筆</em>
	                            <#else>
	                            	-
	                            </#if>
	                        </div>
	                        
	                        <div class="txt-cell col-failure">
	                        	<#if vo.errorNum?? && vo.errorNum != "0">
	                        		<a href="catalogProdUploadErrLog.html?catalogUploadLogSeq=${vo.catalogUploadLogSeq!}">${vo.errorNum!}<em>筆</em></a>
	                        	<#else>
	                            	-
	                            </#if>
	                        </div>
	                        
	                        <div class="txt-cell col-renew">
	                        	<#if vo.catalogUploadType == "2" || vo.catalogUploadType == "3">
	                        		<small>下次更新時間</small>
		                            ${vo.nextUpdateDatetime!}
								<#else>
									<a href="selectUpload.html?catalogSeq=${vo.catalogSeq!}">上傳檔案</a>
								</#if>
	                        </div>
	                        
	                        <div class="txt-cell col-delete">
	                            <a href="javascript:deletePfpCatalog('${vo.catalogSeq!}')"></a>
	                        </div>
                        	
						</div>
					</#list>

                </div>
            </div>
            <#-- 表格內容 結束 -->

            <#-- 頁碼 pagination 開始 -->
            <#-- data-order: 目前頁碼 -->
            <#-- data-quantity: 頁數 -->
            <div class="pagination-wrap txt-noselect m-b30" data-order="79" data-quantity="150">
                <#-- data-num: 頁碼 -->
                <ul class="pagination-box txt-table">
                    
                    <#-- 留者查看用，不需要可刪
                    <li class="txt-cell txt-left">
                        <a data-num="1" class="pagination-button left" href="#"></a>
                    </li>
                    <li class="txt-cell">
                        <a class="pagination-button prev" href="#" title="上10頁"></a>
                        <span class="pagination-buttongroup">
                            <a data-num="" class="pagination-button" href="#"></a>
                            <a data-num="" class="pagination-button" href="#"></a>
                            <a data-num="" class="pagination-button" href="#"></a>
                            <a data-num="" class="pagination-button" href="#"></a>
                            <a data-num="" class="pagination-button" href="#"></a>
                            <a data-num="" class="pagination-button" href="#"></a>
                            <a data-num="" class="pagination-button" href="#"></a>
                            <a data-num="" class="pagination-button" href="#"></a>
                            <a data-num="" class="pagination-button" href="#"></a>
                            <a data-num="" class="pagination-button" href="#"></a>
                        </span>
                        <#-- <a data-num="..." class="pagination-button ellipsis" href="#"></a> -->
                    <#-- <a class="pagination-button next" href="#" title="下10頁"></a>
                    </li>
                    <li class="txt-cell txt-right">
                        <a data-num="" class="pagination-button right" href="#"></a>
                    </li> -->
                    
                </ul>
            </div>
            <#-- 頁碼 pagination 結束 -->
            
        </div>
    </div>

</div>