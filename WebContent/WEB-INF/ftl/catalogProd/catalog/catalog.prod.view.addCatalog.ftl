<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<script language="JavaScript" src="<@s.url value="/" />html/js/catalogProd/catalog/catalogAdd.js?t=20180914001"></script>

<form id="savePfpCatalogFrom" action="savePfpCatalog.html" method="post">
	
	<div class="container-prodmanage">
		<#-- 次目錄導覽列 開始 -->
        <#-- hidden 隱藏所有牙齒 -->
        <#-- hidetabs 只顯示第一顆牙齒 -->
        <#-- tab1 tab2 tab3 tab4 tab5 牙齒由左至右底線 -->
        <div class="nav-wrap pos-relative hidden">
            <div class="nav-box pos-relative">
                <ul class="txt-table">
                    <li class="txt-cell pos-relative p-r10">
                        <span class="icon-box list arrow-right">
                            <a href="#">所有商品目錄</a>
                            <em class="icon-arrow-r"></em>
                        </span>
                        <div class="select-box">
                            <select>
                                <option value=""></option>
                            </select>
                        </div>
                    </li>
                    <li class="txt-cell pos-relative"><a href="#">商品清單</a></li>
                    <li class="txt-cell pos-relative"><a href="#">商品組合</a></li>
                    <li class="txt-cell pos-relative"><a href="#">商品資料</a></li>
                    <li class="txt-cell pos-relative"><a href="#">設定</a></li>
                </ul>
                <div class="altername-box pos-absolute pos-right pos-top"><span>帳戶：</span></div>
            </div>
        </div>
        <#-- 次目錄導覽列 結束 -->
		
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
	</div>
</form>