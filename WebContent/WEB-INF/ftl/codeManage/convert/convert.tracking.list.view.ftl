<div class="container-prodmanage">

        <!-- 次目錄導覽列 開始 -->
        <div class="nav-wrap pos-relative no-pseudo h-auto">
            <div class="nav-title">轉換追蹤</div>
        </div>
        <!-- 次目錄導覽列 結束 -->

        <div class="content-wrap p-lr60 transition-all">
            <div class="content-box p-none">

                <!-- (轉換追蹤代碼清單) 表格目錄功能列 開始 -->
                <!-- data-filter:               篩選按鈕底色切換 -->
                <!-- data-filter='all'          所有商品 -->
                <!-- data-filter='enable'       啟用商品 -->
                <!-- data-filter='sealed'       封存商品 -->

                <!-- data-menu:                 顯示或隱藏進階按鈕 -->
                <!-- data-menu='default'        顯示預設按鈕 -->
                <!-- data-menu='detail'         顯示進階按鈕 -->

                <!-- data-list:                 商品排列 -->
                <!-- data-list='row'            資料以條列顯示 -->
                <!-- data-list='box'            資料以區塊顯示 -->
                <div class="nav-wrap prodtable" data-filter="all" data-menu="default" data-list="row" >
                    <div class="nav-box pos-relative">
                        
                        <!-- 新增按鈕 開始 -->
                        <div class="btn-create pos-absolute pos-right">
                            <a href="addConvertTrackingView.html"><em></em></a>
                            <style>.altername-box{right:80px}</style>
                        </div>
                        <!-- 新增按鈕 結束 -->

                        <ul class="txt-table txt-regular">
                            <!-- <li class="txt-cell p-r20">共<em class="txt-quantity">35</em>件商品目錄</li> -->
                            <li class="txt-cell group-default p-r20">
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
                            <li class="txt-cell group-default">
                                <div class="input-text">
                                    <input type="text" name="" maxlength="20" value="" required placeholder="尋找目錄">
                                </div>
                            </li>

                            <!--左 全選 取消全選 功能 預設隱藏-->
                            <li class="txt-cell group-detail p-r10">已選取<em class="txt-quantity">6</em>項</li>
                            <li class="txt-cell group-detail p-r10"><a class="btn-todelete" href="javascript:void(0)">刪除</a></li>
                            <!-- <li class="txt-cell group-detail toseal p-r10"><a class="btn-toseal" href="javascript:void(0)">封存</a></li> -->
                            <!-- <li class="txt-cell group-detail toenable p-r10"><a class="btn-toenable" href="javascript:void(0)">啟用</a></li> -->
                            <li class="txt-cell group-detail p-r10"><a class="btn-selectnone" href="javascript:void(0)">取消選取</a></li>
                            <li class="txt-cell group-detail "><a class="btn-selectall" href="javascript:void(0)">全選</a></li>

                        </ul>

                        <script>
                            //參考用
                            $(function(){
                                //牙齒底色 && 表格內容篩選                              
                                // var $btn_filter=$('.btn-filter');
                                // $btn_filter.on('click',function() {
                                //     var filtertype=$(this).attr('data-filter');
                                //     $('.nav-wrap.prodtable, .prodtable-wrap, .prodcard-wrap').attr('data-filter',filtertype);
                                // });

                                // //牙齒底色 && 表格顯示切換 
                                // var $btn_listtype=$('.btn-listtype');
                                // $btn_listtype.on('click',function() {
                                //     var listtype=$(this).attr('data-list');
                                //     $('.nav-wrap.prodtable, .prodtable-wrap .prodcard-wrap').attr('data-list',listtype);
                                // });

                                //全部選取按鈕
                                var $btn_select=$('.btn-selectall');
                                $btn_select.on('click',function() {
                                    var $checkall = $('#checkAll');
                                    if(!$checkall.prop('checked')) {
                                        $checkall.trigger("click");
                                    }
                                });

                                //取消全選按鈕
                                var $btn_selectnone=$('.btn-selectnone');
                                $btn_selectnone.on('click',function() {
                                    
                                    var $menu = $('.nav-wrap.prodtable');
                                    $menu.attr('data-menu','default');

                                    var $checkbox =$('#checkAll, .txt-row input[type="checkbox"],.prodcard-box input[type="checkbox"]');
                                    $checkbox.each(function(){
                                    this.checked=false;
                                    $(this).parent().parent().parent().removeClass('selected');
                                    });
                                });


                                //篩選商品資訊 下拉選單
                                // var $btn_selectinfomenu=$('.select-menu li');
                                // $btn_selectinfomenu.on('click',function(){
                                //     $(this).attr('data-select',function(_, attr){return!(attr=='true')});
                                //     var _datafiltername= $(this).attr('data-filetr-name');
                                //     var _dataselect= $(this).attr('data-select');
                                //     showhideInfo(_datafiltername,_dataselect);
                                // });
                                    
                                // function showhideInfo(datainfoname,dataselect){
                                //      console.log(datainfoname,dataselect);
                                //     if (dataselect=="true"){
                                //         $('.prodcard-infobox div[data-info-name*='+ datainfoname + ']').css('display','inline-block');
                                //     }else{
                                //         $('.prodcard-infobox div[data-info-name*='+ datainfoname + ']').css('display','none');
                                //     }
                                // }
                                
                                // function init(){
                                //     showhideInfo('prodname','true');
                                //     showhideInfo('listprice','false');
                                //     showhideInfo('promoprice','true');
                                //     showhideInfo('serial','false');
                                //     showhideInfo('supplement','false');
                                //     showhideInfo('neworused','false');
                                //     showhideInfo('class','false');
                                //     showhideInfo('weburl','false');
                                // }
                                //  init();
                            });
                        </script>


                    </div>
                </div>
                <!-- (轉換追蹤代碼清單) 表格目錄功能列 結束 -->

                <!-- 表格內容 開始 -->
                <!-- data-filter:               商品篩選 -->
                <!-- data-filter='all'          顯示所有商品 -->
                <!-- data-filter='enable'       只顯示啟用商品 -->
                <!-- data-filter='sealed'       只顯示封存商品 -->

                <!-- data-type:                 商品列型態 -->
                <!-- data-type='enable'         已啟用商品列 -->
                <!-- data-type='sealed'         已封存商品列 -->
                <div class="prodtable-wrap m-b30 floatingscroll" data-filter="all">
                    <div class="prodtable-box txt-noselect">

                        <script>

                            //參考用
                            $(function () {
                                //floating scrollbar
                                $('.floatingscroll').floatingScrollbar();


                                var $checkall = $('#checkAll');
                                var $menu = $('.nav-wrap.prodtable');
                                var $checkbox =$('.txt-row input[type="checkbox"]');
                                var $checkbox_enable =$('.txt-row[data-type="enable"] input[type="checkbox"]');
                                var $checkbox_sealed =$('.txt-row[data-type="sealed"] input[type="checkbox"]');
                                
                                // table 第一列 目錄的全選核取按鈕功能 + 被選取的物件改底色為白色
                                $checkall.change(function() {
                                    var _filter = $('.prodtable-wrap').attr('data-filter');
                                    if (this.checked) {
                                        $menu.attr('data-menu','detail');
                                        if (_filter=='sealed'){
                                            $checkbox_sealed.each(function(){
                                                this.checked=true;
                                                $(this).parent().parent().parent().addClass('selected');
                                            });
                                        }else{
                                            $checkbox_enable.each(function(){
                                                this.checked=true;
                                                $(this).parent().parent().parent().addClass('selected');
                                            });
                                        }
                                    } else {
                                        $menu.attr('data-menu','default');
                                        if (_filter=='sealed'){
                                            $checkbox_sealed.each(function(){
                                                this.checked=false;
                                                $(this).parent().parent().parent().removeClass('selected');
                                            });
                                        }else{
                                            $checkbox_enable.each(function(){
                                                this.checked=false;
                                                $(this).parent().parent().parent().removeClass('selected');
                                            });
                                        }
                                    }
                                });

                                // 被選取的物件改底色為白色
                                $checkbox.change(function() {
                                    if (this.checked) {
                                        $(this).parent().parent().parent().addClass('selected');
                                    }else{
                                        $(this).parent().parent().parent().removeClass('selected');
                                    }
                                });
                                
                                // 已啟用物件選取
                                $checkbox_enable.click(function () {
                                    if ($(this).is(":checked")) {
                                        $menu.attr('data-menu','detail');
                                        var isAllChecked = 0;
                                        $checkbox_enable.each(function() {
                                            if(!this.checked){
                                                isAllChecked = 1;
                                            }
                                        });
                                        if(isAllChecked == 0){$checkall.prop("checked", true);}     
                                    }
                                    else {
                                        $checkall.prop("checked", false);
                                    }
                                });

                                // 已封存列物件選取
                                $checkbox_sealed.click(function () {
                                    if ($(this).is(":checked")) {
                                        $menu.attr('data-menu','detail');
                                        var isAllChecked = 0;
                                        $checkbox_sealed.each(function(){
                                            if(!this.checked){
                                                isAllChecked = 1;
                                            }
                                        });
                                        if(isAllChecked == 0){$checkall.prop("checked", true);}     
                                    }
                                    else {
                                        $checkall.prop("checked", false);
                                    }
                                });

                            });
                        </script>

                        <!-- 表格欄位標題 -->
                        <div class="txt-table w-full">
                            <div class="txt-row header">
                                <div class="txt-cell col-ptagcheckbox   "><div class="input-check"><input type="checkbox" id="checkAll" value="checkAll"><label for="checkAll"></label></div></div>
                                <div class="txt-cell col-ptagname-trans ">名稱</div>
                                <div class="txt-cell col-ptagcode-trans ">追蹤代碼</div>
                                <div class="txt-cell col-ptagstatus-trans">狀態</div>
                                <div class="txt-cell col-ptagstatus-trans">轉換條件</div>
                                <div class="txt-cell col-type-transfer  ">轉換類型</div>
                                <div class="txt-cell col-days-interactive"><span>互動後</span><span>轉換追溯</span></div>
                                <div class="txt-cell col-days-browse     "><sapn>瀏覽後</span><span>轉換追溯</span></div>
                                <div class="txt-cell col-value-transfer  ">轉換價值</div>
                                <div class="txt-cell col-click-transfer  "><span>點擊後</span><span>轉換數</span></div>
                                <div class="txt-cell col-browse-transfer "><span>瀏覽後</span><span>轉換數</span></div>
                                <div class="txt-cell col-all-transfer    ">所有轉換</div>
                            </div>

                            <!-- row1 -->
                            <div class="txt-row" data-type="enable">
                                <div class="txt-cell col-ptagcheckbox       "><div class="input-check"><input type="checkbox" id="check1"><label for="check1"></label></div></div>
                                <div class="txt-cell col-ptagname-trans     "><a href="editConvertTrackingView.html?convertSeq=CAC20181130000000002">轉換1</a><br><small>ID：CAC20181130000000002</small></div>
                                <div class="txt-cell col-ptagcode-trans     "><a href="javascript:void(0)" onclick="getpTag()">取得代碼</a></div>
                                <div class="txt-cell col-ptagstatus-trans   "><span data-certificated="true">已認證</span></div>
                                <div class="txt-cell col-type-transfer      ">標準轉換追蹤</div>
                                <div class="txt-cell col-type-transfer      ">加到購物車</div>
                                <div class="txt-cell col-days-interactive   ">28天</div>
                                <div class="txt-cell col-days-browse        ">28天</div>
                                <div class="txt-cell col-value-transfer     ">$4000</div>
                                <div class="txt-cell col-click-transfer     ">20</div>
                                <div class="txt-cell col-browse-transfer    ">8</div>
                                <div class="txt-cell col-all-transfer       ">28</div>
                            </div>

                            <!-- row2 -->
                            <div class="txt-row" data-type="enable">
                                <div class="txt-cell col-ptagcheckbox       "><div class="input-check"><input type="checkbox" id="check2"><label for="check2"></label></div></div>
                                <div class="txt-cell col-ptagname-trans     "><a href="editConvertTrackingView.html?convertSeq=CAC20181130000000003">轉換2</a><br><small>ID：CAC20181130000000003</small></div>
                                <div class="txt-cell col-ptagcode-trans     "><a href="javascript:void(0)" onclick="getpTag()">取得代碼</a></div>
                                <div class="txt-cell col-ptagstatus-trans   "><span data-certificated="true">已認證</span></div>
                                <div class="txt-cell col-type-transfer      ">自訂轉換追蹤</div>
                                <div class="txt-cell col-type-transfer      ">加到購物車</div>
                                <div class="txt-cell col-days-interactive   ">28天</div>
                                <div class="txt-cell col-days-browse        ">28天</div>
                                <div class="txt-cell col-value-transfer     ">$4000</div>
                                <div class="txt-cell col-click-transfer     ">20</div>
                                <div class="txt-cell col-browse-transfer    ">8</div>
                                <div class="txt-cell col-all-transfer       ">28</div>
                            </div>


                            <!-- summary -->
                            <div class="txt-row summary">
                                <div class="txt-cell col-ptagcheckbox       "><p class="summary-tit pos-absolute pos-left pos-top ali-middle">總計：所有轉換動作</p></div>
                                <div class="txt-cell col-ptagname-trans     "></div>
                                <div class="txt-cell col-ptagcode-trans     "></div>
                                <div class="txt-cell col-ptagstatus-trans   "></div>                                
                                <div class="txt-cell col-type-transfer      "></div>
                                <div class="txt-cell col-type-transfer      "></div>
                                <div class="txt-cell col-days-interactive   "></div>
                                <div class="txt-cell col-days-browse        "></div>
                                <div class="txt-cell col-value-transfer     "></div>
                                <div class="txt-cell col-click-transfer     ">20</div>
                                <div class="txt-cell col-browse-transfer    ">8</div>
                                <div class="txt-cell col-all-transfer       ">28</div>
                            </div>

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




                <!-- 轉換追蹤代碼 popup -->
                <div style="position:absolute;top:-1000%; left:-1000%; z-index:-1;" id="getpTagDIV">
                    <div class="getpTag-wrap">
                        <div class="getpTag-nav ali-middle"><span>轉換追蹤代碼 - 中秋節抽黑卡活動</span></div>
                        <div class="getpTag-box">
                            <span>請將下方代碼複製並貼在您網站上的每個網頁</span>
                            <div class="code-wrap pos-relative">
                                <a class="btn-copyto code-copy pos-absolute pos-right pos-top txt-noselect" data-clipboard-action="copy" data-clipboard-target="#code1"></a>
                                <div class="code-box">
                                <pre class="snippet">
<textarea id="code1" readonly>
<script id="pcadscript" language="javascript" async src="https://kdpic.pchome.com.tw/js/ptag.js"></script>
<script>
    window.dataLayer = window.dataLayer || [];

    function ptag() {
        dataLayer.push(arguments);
    }
    ptag({
        'paid': '1541043693437'
    });
    ptag('event', 'tracking', {
        'tracking_id': 'traceId002',
        'prod_id': 'P403010772700',
        'prod_price': '',
        'prod_dis': '',
        'ec_stock_status': '',
        'op1': 'tracking_op1',
        'op2': 'tracking_op2'
    });
</script>
</textarea>
                                    </pre>
                                </div>
                            </div>
                            <div class="link-copyto p-t5 txt-right">
                                <a class="btn-copyto" data-clipboard-action="copy" data-clipboard-target="#code1"><em>複製代碼</em></a>
                            </div>                          

                            <!-- 電子郵件寄送代碼 開始 -->
                            <div class="section-box">
                                <p class="title-box h2">以電子郵件寄送代碼<small>若有多個地址請以逗號分隔</small></p>
                                <div class="input-text inputemail">
                                    <input type="text" name="" maxlength="20" value="" required placeholder="you@email.com">
                                    <div class="msg-error">錯誤訊息</div>
                                </div>
                            </div>
                            <!-- 電子郵件寄送代碼 結束 -->
                            <!-- 按鈕 下一步 -->
                            <div class="button-box p-t10">
                                <div class="input-button"><input type="button" value="傳送至Email"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- clipboard.js -->
                <!-- A modern approach to copy text to clipboard -->
                <script src="js/clipboard.min.js"></script>
                <script>
                    var clipboard = new ClipboardJS('.btn-copyto');
                    clipboard.on('success', function (e) {
                        //console.log(e);
                        e.clearSelection();
                    });
                    clipboard.on('error', function (e) {
                        //console.log(e);
                    });
                </script>

                <script>
                    //參考用

                    function stopBubble(e){ 
                        if ( e && e.stopPropagation) {
                            e.stopPropagation(); 
                        }else{ 
                            window.event.cancelBubble = true; 
                        }
                    }

                    function getpTag(){
                        $.fancybox(
                            $('#getpTagDIV').html(),
                            {
                                'autoDimensions'	: false,
                                'width'         	: 720,
                                'height'        	: 590,
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
        </div>
    </div>