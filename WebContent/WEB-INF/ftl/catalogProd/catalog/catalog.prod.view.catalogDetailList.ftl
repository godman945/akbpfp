<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<#-- 表格內容 開始 -->
<div class="prodtable-wrap m-b30">
	<input type="hidden" id="pageNo" name="pageNo" value="${pageNo!}">
	<input type="hidden" id="pageCount" name="pageCount" value="${pageCount!}">
	<input type="hidden" id="totalCount" name="totalCount" value="${totalCount!}">
    
    <div class="prodtable-box txt-noselect">
        <#-- 表格欄位標題 -->
        <div class="txt-row header">
            <div class="txt-cell col-cataname">目錄</div>
            <div class="txt-cell col-catagory">目錄類型</div>
            <div class="txt-cell col-upload">資料更新方式</div>
            <div class="txt-cell col-source">資料來源</div>
            <div class="txt-cell col-success">商品總數</div>
            <div class="txt-cell col-failure">失敗項目</div>
            <div class="txt-cell col-renew">檔案更新</div>
            <div class="txt-cell col-delete"></div>
        </div>

		<#list dataList as vo>
			<div class="txt-row">
				<div class="txt-cell col-cataname" catalogSeq="${vo.catalogSeq!}">
                    <a href="prodListCardStyleView.html?catalogSeq=${vo.catalogSeq!}&currentPage=1&pageSizeSelected=10&prodStatus=&prodName=">${vo.catalogName!}</a>
                    <small>目錄編號：${vo.catalogSeq!}</small>
                    <#if vo.uploadStatus == "1">
                    	<u class="uploading-txt process" uploadingCatalogSeq="${vo.catalogSeq!}">商品資料處理中</u>
                    </#if>
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
                    	${vo.catalogProdNum!}<em>項</em>
                </div>
                
                <div class="txt-cell col-failure">
                	<#if vo.errorNum?? && vo.errorNum != "0">
                		<a href="catalogProdUploadErrLog.html?catalogUploadLogSeq=${vo.catalogUploadLogSeq!}&catalogSeq=${vo.catalogSeq!}">${vo.errorNum!}<em>項</em></a>
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