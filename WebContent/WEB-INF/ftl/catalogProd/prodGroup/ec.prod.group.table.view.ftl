<#assign s=JspTaglibs["/struts-tags"]>
<link type="text/css" rel="stylesheet" href="<@s.url value="/" />html/css/fancybox/jquery.fancybox-1.3.4.css" />
<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.fancybox-1.3.4.js"></script>
<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.floatingscroll.min.js"></script>
<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/clipboard.min.js"></script> 

    <div class="container-prodmanage">

        <!-- 次目錄導覽列 開始 -->
        <!-- hidden 隱藏所有牙齒 -->
        <!-- hidetabs 只顯示第一顆牙齒 -->
        <!-- tab1 tab2 tab3 tab4 tab5 牙齒由左至右底線 -->
        <div class="nav-wrap pos-relative tab3">
            <div class="nav-box pos-relative">
                <ul class="txt-table">
                    <li class="txt-cell pos-relative p-r10">
                        <span class="icon-box list arrow-right">
                            <a href="#">所有商品目錄</a>
                            <em class="icon-arrow-r"></em>
                        </span>
                        <div class="select-box">
                            <select id="catalog">
                            	<#list pfpCatalogList as catalogList> 
	                            	<#if catalogList.catalogSeq == catalogSeq >
										<option value="${catalogList.catalogSeq!}" selected >${catalogList.catalogName!}</option>
									<#else>
										<option value="${catalogList.catalogSeq!}" >${catalogList.catalogName!}</option>
									</#if>
                                </#list>
                            </select>
                        </div>
                    </li>
                    <li class="txt-cell pos-relative"><a href="#">商品清單</a></li>
                    <li class="txt-cell pos-relative"><a href="#">商品組合</a></li>
                    <li class="txt-cell pos-relative"><a href="#">商品資料</a></li>
                    <li class="txt-cell pos-relative"><a href="#">設定</a></li>
                </ul>
                <div class="altername-box pos-absolute pos-right pos-top"><span>帳戶：</span>${customerInfoTitle!}</div>
            </div>
        </div>
        <!-- 次目錄導覽列 結束 -->

        <div class="content-wrap p-lr60 transition-all">
            <div class="content-box p-none">

                <!-- 表格目錄功能列 開始 -->
                <div class="nav-wrap prodtable" id="catalogGroupSeqId"  data-groupseq=${catalogGroupSeq!}>
                    <div class="nav-box pos-relative">
                        <ul class="txt-table txt-regular">
                            <li class="txt-cell p-r20">
                                <span class="txt-portfolio"><u>商品組合：</u>${catalogGroupName!}</span>
                                <span class="txt-portfolio">共<em class="txt-quantity">${totalCount!}</em>件商品</li></span>
                        </ul>
                        
                        <ul class="txt-table txt-regular pos-absolute pos-right pos-top">
                        	<li class="txt-cell p-l20 txt-noselect">
                            	<div class="select-box pos-relative">
                                    <span class="pos-absolute pointer-events-none">顯示</span>
                                    <select id="pageSizeSelect">
                                    	<option value="5" <#if pageSizeSelected==5>selected</#if> >5則</option>
                                        <option value="10" <#if pageSizeSelected==10>selected</#if> >10則</option>
                                        <option value="20" <#if pageSizeSelected==20>selected</#if> >20則</option>
                                        <option value="30" <#if pageSizeSelected==30>selected</#if> >30則</option>
                                        <option value="40" <#if pageSizeSelected==40>selected</#if> >40則</option>
                                        <option value="50" <#if pageSizeSelected==50>selected</#if> >50則</option>
                                    </select>
                            	</div>
                       		</li>
                        </ul> 

                    </div>
                </div>
                <!-- 表格目錄功能列 結束 -->

                <!-- 表格內容 開始 -->
                <div class="prodtable-wrap m-b30 floatingscroll">

                    <div class="prodtable-box txt-noselect">
                        
                        <!-- 表格欄位標題 -->
                        <div class="txt-row header">
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

                        <!-- row1 -->
                        <div id="prodListDiv">
                        	<#if prodList?exists>
                        		<#if prodList?size != 0>
                        			<#list prodList as prods>
				                        <div class="txt-row">
				                            <div class="txt-cell col-serial     ">${prods.catalogProdSeq!}</div>
				                            <div class="txt-cell col-prodname   ">${prods.ecName!}</div>
				                            <div class="txt-cell col-listprice  "><span>$</span>${prods.ecPrice!}</div>
				                            <div class="txt-cell col-promoprice "><span>$</span>${prods.ecDiscountPrice!}</div>
				                            <div class="txt-cell col-supplement ">${prods.ecStockStatus!}</div>
				                            <div class="txt-cell col-neworused  ">${prods.ecUseStatus!}</div>
				                            <div class="txt-cell col-picture    "><img src="http://showstg.pchome.com.tw/pfp/${prods.ecImg!}"></div>
				                            <div class="txt-cell col-class      ">${prods.ecCategory!}</div>
				                            <div class="txt-cell col-weburl     "><a href=${prods.ecUrl!} target="_blank"></a></div>
				                        </div>
			                        </#list>
					    		</#if>
	                    	</#if>	
                        </div>
                        

                    </div>
                </div>
                <!-- 表格內容 結束 -->

                <!-- 頁碼 pagination 開始 -->
                <!-- data-order: 目前頁碼 -->
                <!-- data-quantity: 頁數 -->
                <div id="pageData" class="pagination-wrap txt-noselect m-b30" data-order=${currentPage!} data-quantity=${pageCount!}>
                    <!-- data-num: 頁碼 -->
                    <ul class="pagination-box txt-table">
                        <li class="txt-cell txt-left">
                            <a data-num="1" id="firstPageBtn" class="pagination-button left" href="#"></a>
                        </li>
                        <li class="txt-cell">
                            <a id="previousPageBtn" class="pagination-button prev" href="#" title="上10頁"></a>
                            <span class="pagination-buttongroup">
                                <a data-num="none" class="pagination-button" href="#"></a>
                                <a data-num="none" class="pagination-button" href="#"></a>
                                <a data-num="none" class="pagination-button" href="#"></a>
                                <a data-num="none" class="pagination-button" href="#"></a>
                                <a data-num="none" class="pagination-button" href="#"></a>
                                <a data-num="none" class="pagination-button" href="#"></a>
                                <a data-num="none" class="pagination-button" href="#"></a>
                                <a data-num="none" class="pagination-button" href="#"></a>
                                <a data-num="none" class="pagination-button" href="#"></a>
                                <a data-num="none" class="pagination-button" href="#"></a>
                            </span>
                            <!-- <a data-num="..." class="pagination-button ellipsis" href="#"></a> -->
                            <a id="nextPageBtn" class="pagination-button next" href="#" title="下10頁"></a>
                        </li>
                        <li class="txt-cell txt-right">
                            <a data-num="" id="finalPageBtn" class="pagination-button right" href="#"></a>
                        </li>
                    </ul>
                   
                   
                </div>
                <!-- 頁碼 pagination 結束 -->

            </div>
        </div>
    </div>
	<input id="catalogSeqData" type="hidden" value="${catalogSeq}">
	