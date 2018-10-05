<#assign s=JspTaglibs["/struts-tags"]>

<link type="text/css" rel="stylesheet" href="<@s.url value="/" />html/css/fancybox/jquery.fancybox-1.3.4.css" />
<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.fancybox-1.3.4.js"></script> 
<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.floatingscroll.min.js"></script>


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
                    <li class="txt-cell pos-relative"><a href="#">商品組合</a></li>
                    <li class="txt-cell pos-relative"><a href="#">商品資料</a></li>
                    <li class="txt-cell pos-relative"><a href="#">設定</a></li>
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
                    <p class="portfolio-name p-tb25">全部商品<small>${catalogProdAllNum!}項商品</small></p>

                    <!-- 新增按鈕 開始 -->
                    <div class="btn-create pos-absolute pos-right">
                        <a href="javascript:void(0)" onclick="createPortfolio()"><em></em></a>
                    </div>
                    <!-- 新增按鈕 結束 -->

                    <!-- 圖像廣告預覽畫面 -->
                    <div style="display:none;" id="createPortfolioDIV">
                        <div class="createportfolio-wrap">
                            <div class="createportfolio-box">
                                <p>建立商品組合</p>
                                <div class="input-text">
                                    <input type="text" name="" maxlength="20" value="" required placeholder="填寫組合名稱，最多20字">
                                    <div class="msg-error">請填寫組合名稱</div>
                                </div>

                                <div class="p-t40">
                                    <p>篩選條件</p>
                                    <!-- 條件1 -->
                                    <div class="filter-group">
                                        <div class="select-box">
                                            <select>
                                                <option value="1">供應情況</option>
                                                <option value="2">供應情況</option>
                                                <option value="3">供應情況</option>
                                            </select>
                                        </div>
                                        <div class="select-box">
                                            <select>
                                                <option value="1">不屬於</option>
                                                <option value="2">不屬於</option>
                                                <option value="3">不屬於</option>
                                            </select>
                                        </div>
                                        <div class="select-box">
                                            <select>
                                                <option value="1">預購</option>
                                                <option value="2">預購</option>
                                                <option value="3">預購</option>
                                            </select>
                                        </div>
                                        <div class="icon-kill" onclick="$(this).parent().css('display','none')"></div>
                                    </div>

                                    <!-- 條件2 -->
                                    <div class="filter-group">
                                        <div class="select-box">
                                            <select>
                                                <option value="1">價格</option>
                                                <option value="2">價格</option>
                                                <option value="3">價格</option>
                                            </select>
                                        </div>
                                        <div class="select-box">
                                            <select>
                                                <option value="1">小於</option>
                                                <option value="2">小於</option>
                                                <option value="3">小於</option>
                                            </select>
                                        </div>
                                        <div class="input-number">
                                            NT<input type="text" name="" maxlength="6" value="">元
                                            <p class="txt-descript"></p>
                                        </div>
                                        <div class="icon-kill" onclick="$(this).parent().css('display','none')"></div>
                                    </div>

                                    
                                    <a class="link-addfilter">＋增加篩選條件</a>
                                </div>
                                
                                <div class="txt-center">
                                    <div class="input-button"><input type="button" value="新增商品"></div>
                                </div>

                            </div>
                        </div>
                    </div>

                    <script>
                        //參考用

                        function stopBubble(e){ 
                        alert("111")
                            if ( e && e.stopPropagation) {
                                e.stopPropagation(); 
                            }else{ 
                                window.event.cancelBubble = true; 
                            }
                        }

                        function createPortfolio(){
                         alert("222")
                            $.fancybox(
                                $('#createPortfolioDIV').html(),
                                {
                                    'autoDimensions'	: false,
                                    'width'         	: 785,
                                    'height'        	: 435,
                                    'autoSize'			: false,
                                    'autoHeight'		: false,
                                    'autoScale'			: false,
                                    'padding'			: 0,
                                    'overlayOpacity'    : .70,
                                    'overlayColor'      : '#000',
                                    'scrolling'			: 'no' 
                                }
                            );
                        }
                    </script>


                </div>

                <div class="portfolio-wrap m-b30 transition-all">
                    <div class="portfolio-box txt-noselect p-b50 pos-relative">

                        <!-- 商品資料 1 -->
                        <#if pfpCatalogGroupVOList?exists>
                        	<#if pfpCatalogGroupVOList?size != 0>
                        		<#list pfpCatalogGroupVOList as dataVo>
			                        <div class="portfolio-item" onclick="$(this).toggleClass('opened')">
			                            <div class="txt-row">
			                                <div class="txt-cell col-protfolioname">
			                                    <em class="icon-arrow-r"></em>${dataVo.catalogGroupName!}
			                                </div>
			                                <div class="txt-cell col-quantity"><a href="queryProdGroupList.html?catalogGroupSeq=${dataVo.catalogGroupSeq!}&currentPage=1&pageSizeSelected=10">${dataVo.catalogProdNum!}項商品</a></div>
			                                <div class="txt-cell col-addnew"><a href="vascript:void(0)" onclick="stopBubble(event);createPortfolio()"></a></div>
			                                <div class="txt-cell col-delete"><a href="#" value="${dataVo.catalogGroupSeq!}" onclick="deleteCatalogGroupAjax(this)" ></a></div>
			                            </div>
			                            <div class="col-detail">
			                                <span>商品類型：<b><em>不屬於</em>硬殼行李箱</b></span>
			                                <span>價格：<b><em>低於</em>$3,000</b></span>
			                                <span>供應情況：<b><em>屬於</em>預售</b></span>
			                            </div>
			                        </div>
			                	</#list> 
			               	<#else>
			               		此目錄沒有商品群組
							</#if>
	                    </#if>	

                    </div>
                </div>

            </div>
        </div>
        <!-- 選擇商品資料來源 結束 -->
    </div>

	
