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
                <div class="nav-wrap prodtable">
                    <div class="nav-box pos-relative">
                        
                        <ul class="txt-table txt-regular">
                            <li class="txt-cell p-r20">
                                <span class="txt-portfolio"><u>商品組合：</u>${catalogGroupName!}</span>
                                <span class="txt-portfolio">共<em class="txt-quantity">${totalCount!}</em>件商品</li></span>
                        </ul>

                    </div>
                </div>
                <!-- 表格目錄功能列 結束 -->

                <!-- 表格內容 開始 -->
                <div class="prodtable-wrap m-b30 floatingscroll">

                    <script>
                        //參考用
                        $(function () {
                            //floating scrollbar
                            $('.floatingscroll').floatingScrollbar();
                        });
                    </script>
                    
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
                        <div class="txt-row">
                            <div class="txt-cell col-serial     ">product_201807301</div>
                            <div class="txt-cell col-prodname   ">Samsonite新秀麗 20吋Lite Shock極輕Curv四 輪拉桿頂級硬殼箱(黑)</div>
                            <div class="txt-cell col-listprice  "><span>$</span>12,999</div>
                            <div class="txt-cell col-promoprice "><span>$</span>11,000</div>
                            <div class="txt-cell col-supplement ">有庫存</div>
                            <div class="txt-cell col-neworused  ">福利品</div>
                            <div class="txt-cell col-picture    "><img src="https://e.ecimg.tw/items/DICY17A90081WF0/000001_1492588581.jpg"></div>
                            <div class="txt-cell col-class      ">硬殼箱</div>
                            <div class="txt-cell col-weburl     "><a href="https://24h.pchome.com.tw/prod/DICY17-A90081WF0?fq=/S/DIBKPH" target="_blank"></a></div>
                        </div>

                        <!-- row2 -->
                        <div class="txt-row">
                            <div class="txt-cell col-serial     ">product_201807302</div>
                            <div class="txt-cell col-prodname   ">Samsonite新秀麗 20吋(石墨黑)</div>
                            <div class="txt-cell col-listprice  "><span>$</span>10,900</div>
                            <div class="txt-cell col-promoprice "><span>$</span>4,000</div>
                            <div class="txt-cell col-supplement ">預購</div>
                            <div class="txt-cell col-neworused  ">全新</div>
                            <div class="txt-cell col-picture    "><img src="https://d.ecimg.tw/items/DICY17A90087LLH/000001_1528700717.jpg"></div>
                            <div class="txt-cell col-class      ">硬殼箱</div>
                            <div class="txt-cell col-weburl     "><a href="https://24h.pchome.com.tw/prod/DIY17-A90087LLH" target="_blank"></a></div>
                        </div>

                        <!-- row3 -->
                        <div class="txt-row">
                           <div class="txt-cell col-serial     ">product_201807304</div>
                            <div class="txt-cell col-prodname   ">Samsonite新秀麗 20吋Starfire飛機輪TSA防刮耐磨PC登機箱(石墨黑)</div>
                            <div class="txt-cell col-listprice  "><span>$</span>8,840</div>
                            <div class="txt-cell col-promoprice "><span>$</span>4,840</div>
                            <div class="txt-cell col-supplement ">有庫存</div>
                            <div class="txt-cell col-neworused  ">全新</div>
                            <div class="txt-cell col-picture    "><img src="https://d.ecimg.tw/items/DICY17A9007XRRS/000001_1488518863.jpg"></div>
                            <div class="txt-cell col-class      ">登機箱</div>
                            <div class="txt-cell col-weburl     "><a href="https://24h.pchome.com.tw/prod/DICY17-A9007XRRS" target="_blank"></a></div>
                        </div>

                        <!-- row4 -->
                        <div class="txt-row">
                            <div class="txt-cell col-serial     ">product_201807303</div>
                            <div class="txt-cell col-prodname   ">Samsonite新秀麗 20吋Gary立體流線可擴充硬殼TSA登機箱(極光藍)</div>
                            <div class="txt-cell col-listprice  "><span>$</span>10,900</div>
                            <div class="txt-cell col-promoprice "><span>$</span>4,000</div>
                            <div class="txt-cell col-supplement ">預購</div>
                            <div class="txt-cell col-neworused  ">全新</div>
                            <div class="txt-cell col-picture    "><img src="https://d.ecimg.tw/items/DICY17A90087LN3/000001_1532398078.jpg"></div>
                            <div class="txt-cell col-class      ">硬殼箱</div>
                            <div class="txt-cell col-weburl     "><a href="https://24h.pchome.com.tw/prod/DIY17-A90087LN3" target="_blank"></a></div>
                        </div>

                    </div>
                </div>
                <!-- 表格內容 結束 -->

                <!-- 頁碼 pagination 開始 -->
                <!-- data-order: 目前頁碼 -->
                <!-- data-quantity: 頁數 -->
                <div class="pagination-wrap txt-noselect m-b30" data-order="1" data-quantity="13">
                    <!-- data-num: 頁碼 -->
                    <ul class="pagination-box txt-table">
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
                            <!-- <a data-num="..." class="pagination-button ellipsis" href="#"></a> -->
                            <a class="pagination-button next" href="#" title="下10頁"></a>
                        </li>
                        <li class="txt-cell txt-right">
                            <a data-num="" class="pagination-button right" href="#"></a>
                        </li>
                    </ul>

                    <script>
                        //參考用
                        $(function () {
                            var pagedata = $('.pagination-wrap').data();
                            var order = pagedata.order;
                            var quantity = pagedata.quantity;

                            //設定頁碼
                            setPagination(order, quantity);

                            function setPagination(order, quantity){
                                var $buttongroup=$('.pagination-buttongroup a');
                                var $buttonright=$('a.pagination-button.right');
                                //計算頁碼起始顯示值 +修改目前所在頁碼按鈕底色
                                var _length=$buttongroup.length;
                                var _order=(order<=_length)?1:order;
                                    _order=(quantity-order<=_length)?quantity-_length+1:_order;
                                    _order=(_order>_length &&_order%_length!=0)?(_order-_order%_length+1):_order;
                                //寫入頁碼
                                for(var i=0;i<_length;i++){
                                    $buttongroup.eq(i).attr('data-num',_order+i);
                                    if((_order+i)==order) $buttongroup.eq(i).addClass('active');
                                }
                                //寫入最後一頁頁碼 + 最後一頁時修改按鈕底色
                                $buttonright.attr('data-num', quantity);
                                if(order==quantity) $buttonright.addClass('active');
                            }
                        });
                    </script>
                </div>
                <!-- 頁碼 pagination 結束 -->

            </div>
        </div>
    </div>

	<!-- footer start -->
	<div class="footer">
		．<a href="http://www.pchome.com.tw/" target="_blank">PChome</a>
		．<a href="http://show.pchome.com.tw/" target="">廣告刊登</a>
		．<a href="faq.html?qid=2&fid=4" target="_blank">約定條款</a> 
		．<a href="faq.html?fid=4&qid=5" target="_blank">刊登規範</a> 
		．<a href="http://faq.pchome.com.tw/service/user_reply.html?ch=show" target="_blank">客服中心</a> 
		．<a href="http://faq.pchome.com.tw/faq_solution.html?q_id=16&c_nickname=member&f_id=4" target="_blank">隱私權聲明</a> 
		<br>網路家庭版權所有、轉載必究 Copyright&copy;  PChome Online
	</div>
	<!-- footer end -->
</body>
</html>