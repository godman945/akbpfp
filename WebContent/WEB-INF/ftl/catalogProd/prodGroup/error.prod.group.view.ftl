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
                    <li class="txt-cell pos-relative"><a href="prodListCardStyleView.html?catalogSeq=${catalogSeq}&currentPage=1&pageSizeSelected=10">商品清單</a></li>
                    <li class="txt-cell pos-relative"><a href="queryCatalogGroup.html?catalogSeq=${catalogSeq}">商品組合</a></li>
                    <li class="txt-cell pos-relative"><a href="selectUpload.html?catalogSeq=${catalogSeq}">商品資料</a></li>
                    <li class="txt-cell pos-relative"><a href="setup.html?catalogSeq=${catalogSeq}">設定</a></li>
                </ul>
                <div class="altername-box pos-absolute pos-right pos-top"><span>帳戶：</span>${customerInfoTitle!}</div>
            </div>
        </div>
        <!-- 次目錄導覽列 結束 -->


        <!-- 選擇商品資料來源 開始 -->
        <div class="content-wrap bg-white">
            <div class="content-box bg-white w-900">
                <div class="portfolio-header pos-relative transition-all">
                    <p class="portfolio-title p-t15 p-b25">商品組合<small>使用篩選條件建立商品組合，以決定廣告中的商品項目。</small></p>
                    ${returnMsg!}
                </div>
            </div>
        </div>
        <!-- 選擇商品資料來源 結束 -->
    </div>

	<input id="catalogSeqData" type="hidden" value="${catalogSeq}">
