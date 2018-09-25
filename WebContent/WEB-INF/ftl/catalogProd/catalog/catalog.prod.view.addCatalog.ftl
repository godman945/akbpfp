<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<script language="JavaScript" src="<@s.url value="/" />html/js/catalogProd/catalog/catalogAdd.js?t=20180914001"></script>

<form id="savePfpCatalogFrom" action="savePfpCatalog.html" method="post">
	
	<#-- 建立商品目錄 開始 -->
	<div class="content-wrap">
	    <p class="title-box h1 txt-center">建立商品目錄</p>
	    <div class="content-box bg-white w-900">
	        
	        <#-- 目錄名稱 開始 -->
	        <div class="section-box p-b60">
	            <p class="title-box h2">目錄名稱</p>
	            <div class="input-text">
	                <input type="text" id="catalogName" name="catalogName" maxlength="20" value="" required placeholder="填寫目錄名稱，最多20字">
	                <div class="msg-error">聯播廣告出價最少為3元</div>
	            </div>
	        </div>
	        <#-- 目錄名稱 結束 -->
	
	        <#-- 選擇目錄類型 開始 -->
	        <div class="section-box p-b60">
	            <p class="title-box h2">選擇目錄類型</p>
	            <div class="topic-wrap txt-center txt-noselect">
	                <div class="topic-box selected">
	                    <p>一般購物</p>
	                    <div class="svg-box">
	                        <object class="svg-img" data="<@s.url value="/" />/html/img/catalogProd/addCatalog/topic-shopping.svg" type="image/svg+xml"></object>
	                    </div>
	                </div>
	                <div class="link-download p-t10"><a href="catalogSampleFileDownload.html"><em>下載範例</em></a></div>
	            </div>
	            <div class="topic-wrap txt-center txt-noselect disable">
	                <div class="topic-box">
	                    <p>訂房住宿</p>
	                    <div class="svg-box">
	                        <object class="svg-img" data="<@s.url value="/" />/html/img/catalogProd/addCatalog/topic-hotel.svg" type="image/svg+xml"></object>
	                    </div>
	                </div>
	                <div class="link-download p-t10">即將推出</div>
	            </div>
	            <div class="topic-wrap txt-center txt-noselect disable">
	                <div class="topic-box">
	                    <p>交通航班</p>
	                    <div class="svg-box">
	                        <object class="svg-img" data="<@s.url value="/" />/html/img/catalogProd/addCatalog/topic-ticket.svg" type="image/svg+xml"></object>
	                    </div>
	                </div>
	                <div class="link-download p-t10">即將推出</div>
	            </div>
	            <div class="topic-wrap txt-center txt-noselect disable">
	                <div class="topic-box">
	                    <p>房產租售</p>
	                    <div class="svg-box">
	                        <object class="svg-img" data="<@s.url value="/" />/html/img/catalogProd/addCatalog/topic-realestate.svg" type="image/svg+xml"></object>
	                    </div>
	                </div>
	                <div class="link-download p-t10">即將推出</div>
	            </div>
	        </div>
	        <#-- 選擇目錄類型 結束 -->
	        
	    </div>
	
	    <div class="button-box w-900 txt-center p-tb30">
	        <div class="input-button"><input type="button" onclick="addPfpCatalog();" value="建立目錄"></div>
	    </div>
	
	</div>
	<#-- 建立商品目錄 結束 -->

</form>