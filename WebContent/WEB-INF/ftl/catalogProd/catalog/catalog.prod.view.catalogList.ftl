<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.ba-dotimeout.js"></script>
<script language="JavaScript" src="<@s.url value="/" />html/js/catalogProd/catalog/catalog.js?t=20181109005"></script>

<div class="container-prodmanage">
	
	<#-- 次目錄導覽列 開始 -->
	<div class="nav-wrap pos-relative hidetabs tab1">
	    <div class="nav-box pos-relative">
	        <ul class="txt-table">
	            <li class="txt-cell pos-relative p-r10">
	                <span class="icon-box list arrow-right">
	                    <a href="catalogProd.html">所有商品目錄</a>
	                </span>
	            </li>
	            
	            <li class="txt-cell pos-relative"><a href="#">商品清單</a></li>
                <li class="txt-cell pos-relative"><a href="#">商品組合</a></li>
                <li class="txt-cell pos-relative"><a href="#">商品資料</a></li>
                <li class="txt-cell pos-relative"><a href="#">設定</a></li>
	            
	            <li class="txt-cell pos-relative p-r10">
                    <span class="icon-box tab-logomanage p-r10">
                        <a href="logo.html">LOGO管理<em class="icon-error"></em></a>                                                        
                    </span>
                </li>
                <div class="p-b10 note-text">提醒您，必須完整建立商品目錄與LOGO才能投放商品廣告</div>
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

			<@t.insertAttribute name="catalogDetailList" />

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