<#assign s=JspTaglibs["/struts-tags"]>

<!--
<link href="<@s.url value="/html/css/ad/adPlugInStyle.css" />" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="<@s.url value="/" />html/css/fancybox/jquery.fancybox-1.3.4.css" />
<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.fancybox-1.3.4.js"></script> 
<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adActionAdd.js" ></script>
<style type="text/css">
.level1 {width: 23px; height: 32px;}
.level2 {width: 38px; height: 32px;}
.level3 {width: 53px; height: 32px;}
.level4 {width: 68px; height: 32px;}
.selectTop:hover{text-decoration:underline}
</style>	
-->


<div class="container-prodmanage">

        <!-- 次目錄導覽列 開始 -->
        <!-- hidden 隱藏所有牙齒 -->
        <!-- hidetabs 只顯示第一顆牙齒 -->
        <!-- tab1 tab2 tab3 tab4 tab5 牙齒由左至右底線 -->
        <div class="nav-wrap pos-relative hidetabs tab1">
            <div class="nav-box pos-relative">
                <ul class="txt-table">
                    <li class="txt-cell pos-relative p-r10">
                        <span class="icon-box list arrow-right">
                            <a href="#">所有商品目錄</a>
                            <em class="icon-arrow-r"></em>
                        </span>
                        <div class="select-box">
                            <select>
                                <option value="1">2018春季特賣</option>
                                <option value="2">2018夏季特賣</option>
                                <option value="3">2018秋季特賣</option>
                                <option value="4">2018冬季特賣</option>
                                <option value="5">春季特賣春季特賣春季特賣春季特賣春季特賣</option>
                            </select>
                        </div>
                    </li>
                    <li class="txt-cell pos-relative"><a href="#">商品清單</a></li>
                    <li class="txt-cell pos-relative"><a href="#">商品組合</a></li>
                    <li class="txt-cell pos-relative"><a href="#">商品資料</a></li>
                    <li class="txt-cell pos-relative"><a href="#">設定</a></li>
                </ul>
                <div class="altername-box pos-absolute pos-right pos-top"><span>帳戶：</span>showad1234showad1234</div>
            </div>
        </div>
        <!-- 次目錄導覽列 結束 -->

        <div class="content-wrap p-lr60 transition-all">
            <div class="content-box p-none">

                <!-- 表格目錄功能列 開始 -->
                <div class="nav-wrap prodtable">
                    <div class="nav-box pos-relative">
                        
                        <!-- 新增按鈕 開始 -->
                        <div class="btn-create pos-absolute pos-right">
                            <a href="#"><em></em></a>
                            <style>.altername-box{right:80px}</style>
                        </div>
                        <!-- 新增按鈕 結束 -->

                        <ul class="txt-table txt-regular">
                            <li class="txt-cell p-r20">共<em class="txt-quantity">35</em>件商品目錄</li>
                            <li class="txt-cell p-r20">
                                <div class="select-box pos-relative">
                                    <span class="pos-absolute">顯示</span>
                                    <select>
                                        <option value="1">10則</option>
                                        <option value="2">20則</option>
                                        <option value="3">30則</option>
                                        <option value="4">40則</option>
                                        <option value="5">50則</option>
                                    </select>
                                </div>
                            </li>
                            <li class="txt-cell">
                                <div class="input-text">
                                    <input type="text" name="" maxlength="20" value="" required placeholder="尋找目錄">
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <!-- 表格目錄功能列 結束 -->

                <!-- 表格內容 開始 -->
                <div class="prodtable-wrap m-b30">
                    <div class="prodtable-box txt-noselect">
                        <!-- 表格欄位標題 -->
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

                        <!-- row1 -->
                        <div class="txt-row">
                            <div class="txt-cell col-cataname">
                                <a href="prodListCardStyleView.html?catalogSeq=PC201808220000000001&page=1&pageSize=10">2018春季新品外套外套春季新品外套外套外套</a>
                                <small>目錄編號：207668739900037</small>
                            </div>
                            <div class="txt-cell col-catagory">
                                一般購物
                            </div>
                            <div class="txt-cell col-upload">
                                排程自動更新
                            </div>
                            <div class="txt-cell col-source">
                                PAZZO春季新品上市2018080156-1111111111111111111111111111111111111111111111111111111111111111111.csv
                                <small>最近更新：2018/06/28 09:35AM</small>
                            </div>
                            <div class="txt-cell col-success">
                                2560009<em>筆</em>
                            </div>
                            <div class="txt-cell col-failure">
                                <a href="11所有商品目錄-錯誤列表.html">159<em>筆</em></a>
                            </div>
                            <div class="txt-cell col-renew">
                                <small>下次更新時間</small>
                                2018/06/29 01:00AM
                            </div>
                            <div class="txt-cell col-delete">
                                <a href="#"></a>
                            </div>
                        </div>

                        <!-- row2 -->
                        <div class="txt-row">
                            <div class="txt-cell col-cataname">
                                <a href="#">夏季運動會20180718</a>
                                <small>目錄編號：207668739900011</small>
                            </div>
                            <div class="txt-cell col-catagory">
                                一般購物
                            </div>
                            <div class="txt-cell col-upload">
                                檔案上傳
                            </div>
                            <div class="txt-cell col-source">
                                海妃丝街访商品0728.csv
                                <small>最近更新：2018/05/02 11:35AM</small>
                            </div>
                            <div class="txt-cell col-success">
                                159<em>筆</em>
                            </div>
                            <div class="txt-cell col-failure">
                                <a href="11所有商品目錄-錯誤列表.html">2560009<em>筆</em></a>
                            </div>
                            <div class="txt-cell col-renew">
                                <a href="#">上傳檔案</a>
                            </div>
                            <div class="txt-cell col-delete">
                                <a href="#"></a>
                            </div>
                        </div>

                        <!-- row3 -->
                        <div class="txt-row">
                            <div class="txt-cell col-cataname">
                                <a href="#">de | 第一化妝品</a>
                                <small>目錄編號：207668739900037</small>
                            </div>
                            <div class="txt-cell col-catagory">
                                一般購物
                            </div>
                            <div class="txt-cell col-upload">
                                賣場網址上傳
                            </div>
                            <div class="txt-cell col-source">
                                <a href="#"><img src="img/24h-logo.png">de | 第一化妝品</a>
                                <small>最近更新：2018/06/28 02:00AM</small>
                            </div>
                            <div class="txt-cell col-success">
                                25<em>筆</em>
                            </div>
                            <div class="txt-cell col-failure">
                                -
                            </div>
                            <div class="txt-cell col-renew">
                                <small>下次更新時間</small>
                                2018/06/29 02:00AM
                            </div>
                            <div class="txt-cell col-delete">
                                <a href="#"></a>
                            </div>
                        </div>

                        <!-- row4 -->
                        <div class="txt-row">
                            <div class="txt-cell col-cataname">
                                <a href="#">de | 第一化妝品</a>
                                <small>目錄編號：207668739900037</small>
                            </div>
                            <div class="txt-cell col-catagory">
                                一般購物
                            </div>
                            <div class="txt-cell col-upload">
                                賣場網址上傳
                            </div>
                            <div class="txt-cell col-source">
                                <a href="#"><img src="img/ruten-logo.png">de | 第一化妝品</a>
                                <small>最近更新：2018/06/28 02:00AM</small>
                            </div>
                            <div class="txt-cell col-success">
                                25<em>筆</em>
                            </div>
                            <div class="txt-cell col-failure">
                                -
                            </div>
                            <div class="txt-cell col-renew">
                                <small>下次更新時間</small>
                                2018/06/29 02:00AM
                            </div>
                            <div class="txt-cell col-delete">
                                <a href="#"></a>
                            </div>
                        </div>

                        <!-- row5 -->
                        <div class="txt-row">
                            <div class="txt-cell col-cataname">
                                <a href="#">de | 第一化妝品</a>
                                <small>目錄編號：207668739900037</small>
                            </div>
                            <div class="txt-cell col-catagory">
                                一般購物
                            </div>
                            <div class="txt-cell col-upload">
                                賣場網址上傳
                            </div>
                            <div class="txt-cell col-source">
                                <a href="#"><img src="img/pcstore-logo.png">de | 第一化妝品</a>
                                <small>最近更新：2018/06/28 02:00AM</small>
                            </div>
                            <div class="txt-cell col-success">
                                25<em>筆</em>
                            </div>
                            <div class="txt-cell col-failure">
                                -
                            </div>
                            <div class="txt-cell col-renew">
                                <small>下次更新時間</small>
                                2018/06/29 02:00AM
                            </div>
                            <div class="txt-cell col-delete">
                                <a href="#"></a>
                            </div>
                        </div>

                        <!-- row6 -->
                        <div class="txt-row">
                            <div class="txt-cell col-cataname">
                                <a href="#">de | 第一化妝品</a>
                                <small>目錄編號：207668739900037</small>
                            </div>
                            <div class="txt-cell col-catagory">
                                一般購物
                            </div>
                            <div class="txt-cell col-upload">
                                賣場網址上傳
                            </div>
                            <div class="txt-cell col-source">
                                <a href="#"><img src="img/sellerpcstore-logo.png">de | 第一化妝品</a>
                                <small>最近更新：2018/06/28 02:00AM</small>
                            </div>
                            <div class="txt-cell col-success">
                                25<em>筆</em>
                            </div>
                            <div class="txt-cell col-failure">
                                -
                            </div>
                            <div class="txt-cell col-renew">
                                <small>下次更新時間</small>
                                2018/06/29 02:00AM
                            </div>
                            <div class="txt-cell col-delete">
                                <a href="#"></a>
                            </div>
                        </div>

                        <!-- row7 -->
                        <div class="txt-row">
                            <div class="txt-cell col-cataname">
                                <a href="#">de | 第一化妝品</a>
                                <small>目錄編號：207668739900037</small>
                            </div>
                            <div class="txt-cell col-catagory">
                                一般購物
                            </div>
                            <div class="txt-cell col-upload">
                                賣場網址上傳
                            </div>
                            <div class="txt-cell col-source">
                                <a href="#"><img src="img/mallpchome-logo.png">de | 第一化妝品</a>
                                <small>最近更新：2018/06/28 02:00AM</small>
                            </div>
                            <div class="txt-cell col-success">
                                25<em>筆</em>
                            </div>
                            <div class="txt-cell col-failure">
                                -
                            </div>
                            <div class="txt-cell col-renew">
                                <small>下次更新時間</small>
                                2018/06/29 02:00AM
                            </div>
                            <div class="txt-cell col-delete">
                                <a href="#"></a>
                            </div>
                        </div>

                        <!-- row8 -->
                        <div class="txt-row">
                            <div class="txt-cell col-cataname">
                                <a href="#">夏季運動會20180718</a>
                                <small>目錄編號：207668739900011</small>
                            </div>
                            <div class="txt-cell col-catagory">
                                一般購物
                            </div>
                            <div class="txt-cell col-upload">
                                手動上傳
                            </div>
                            <div class="txt-cell col-source">
                                -
                            </div>
                            <div class="txt-cell col-success">
                                184<em>筆</em>
                            </div>
                            <div class="txt-cell col-failure">
                                -
                            </div>
                            <div class="txt-cell col-renew">
                                <a href="#">上傳檔案</a>
                            </div>
                            <div class="txt-cell col-delete">
                                <a href="#"></a>
                            </div>
                        </div>

                        <!-- row9 -->
                        <div class="txt-row">
                            <div class="txt-cell col-cataname">
                                <a href="#">2018春季新品外套外套春季新品外套外套外套</a>
                                <small>目錄編號：207668739900037</small>
                            </div>
                            <div class="txt-cell col-catagory">
                                一般購物
                            </div>
                            <div class="txt-cell col-upload">
                                排程自動更新
                            </div>
                            <div class="txt-cell col-source">
                                PAZZO春季新品上市2018080156.csv
                                <small>最近更新：2018/06/28 09:35AM</small>
                            </div>
                            <div class="txt-cell col-success">
                                2560009<em>筆</em>
                            </div>
                            <div class="txt-cell col-failure">
                                <a href="#">159<em>筆</em></a>
                            </div>
                            <div class="txt-cell col-renew">
                                <small>下次更新時間</small>
                                2018/06/29 01:00AM
                            </div>
                            <div class="txt-cell col-delete">
                                <a href="#"></a>
                            </div>
                        </div>

                        <!-- row10 -->
                        <div class="txt-row">
                            <div class="txt-cell col-cataname">
                                <a href="#">夏季運動會20180718</a>
                                <small>目錄編號：207668739900011</small>
                            </div>
                            <div class="txt-cell col-catagory">
                                一般購物
                            </div>
                            <div class="txt-cell col-upload">
                                檔案上傳
                            </div>
                            <div class="txt-cell col-source">
                                海妃丝街访商品0728.csv
                                <small>最近更新：2018/05/02 11:35AM</small>
                            </div>
                            <div class="txt-cell col-success">
                                159<em>筆</em>
                            </div>
                            <div class="txt-cell col-failure">
                                <a href="#">2560009<em>筆</em></a>
                            </div>
                            <div class="txt-cell col-renew">
                                <a href="#">上傳檔案</a>
                            </div>
                            <div class="txt-cell col-delete">
                                <a href="#"></a>
                            </div>
                        </div>

                    </div>
                </div>
                <!-- 表格內容 結束 -->

                <!-- 頁碼 pagination 開始 -->
                <!-- data-order: 目前頁碼 -->
                <!-- data-quantity: 頁數 -->
                <div class="pagination-wrap txt-noselect m-b30" data-order="79" data-quantity="150">
                    <!-- data-num: 頁碼 -->
                    <ul class="pagination-box txt-table">
                        <li class="txt-cell txt-left">
                            <a data-num="1" class="pagination-button left" href="#"></a>
                        </li>
                        <li class="txt-cell">
                            <a class="pagination-button prev" href="#"></a>
                            <span class="pagination-buttongroup">
                                <a data-num="" class="pagination-button" href="#"></a>
                                <a data-num="" class="pagination-button" href="#"></a>
                                <a data-num="" class="pagination-button" href="#"></a>
                                <a data-num="" class="pagination-button" href="#"></a>
                            </span>
                            <a data-num="..." class="pagination-button ellipsis" href="#"></a>
                            <a class="pagination-button next" href="#"></a>
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

