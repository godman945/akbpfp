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
        <div class="nav-wrap pos-relative tab2">
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

                <!-- (商品清單) 表格目錄功能列 開始 -->
                <!-- data-filter:               篩選按鈕底色切換 -->
                <!-- data-filter='all'          所有商品 -->
                <!-- data-filter='enable'       啟用商品 -->
                <!-- data-filter='sealed'       封存商品 -->

                <!-- data-menu:                 顯示或隱藏進階按鈕 -->
                <!-- data-menu='default'        顯示預設按鈕 -->
                <!-- data-menu='detail'         顯示進階按鈕 -->

                <!-- data-list:                 商品排列 -->
                <!-- data-list='row'            資料以條列顯示 -->
                <!-- data-list='box'            資料以卡片顯示 -->
                <div class="nav-wrap prodtable pos-relative" data-filter="all" data-menu="default" data-list="box">
                    <div class="nav-box pos-relative">
                        <!--左-->
                        <ul class="txt-table txt-regular">
                            <li class="txt-cell group-default p-r10">共<em class="txt-quantity">35</em>件商品目錄</li>
                            <li class="txt-cell group-default p-r10"><a class="btn-filter" href="javascript:void(0)" data-filter="all">全部商品</a></li>
                            <li class="txt-cell group-default p-r10"><a class="btn-filter" href="javascript:void(0)" data-filter="enable">已啟用</a></li>
                            <li class="txt-cell group-default "><a class="btn-filter" href="javascript:void(0)" data-filter="sealed">已封存</a></li>
                            
                            <!--左 全選 取消全選 功能 預設隱藏-->
                            <li class="txt-cell group-detail p-r10">已選取<em class="txt-quantity">6</em>項</li>
                            <li class="txt-cell group-detail toseal p-r10"><a class="btn-toseal" href="javascript:void(0)">封存</a></li>
                            <li class="txt-cell group-detail toenable p-r10"><a class="btn-toenable" href="javascript:void(0)">啟用</a></li>
                            <li class="txt-cell group-detail p-r10"><a class="btn-selectnone" href="javascript:void(0)">取消選取</a></li>
                            <li class="txt-cell group-detail "><a class="btn-selectall" href="javascript:void(0)">全選</a></li>
                        </ul>


                        <!--右-->
                        <ul class="txt-table txt-regular pos-absolute pos-right pos-top">
                            <li class="txt-cell w-200">
                                <div class="input-text">
                                    <input type="text" name="" maxlength="20" value="" required placeholder="尋找商品">
                                </div>
                            </li>

                            <!-- 篩選下拉選單 -->
                            <li class="txt-cell select-menu w-130 p-l20">
                                <div class="select-box pos-relative">
                                    <span class="pos-absolute pointer-events-none">篩選商品資訊</span>
                                    <div class="menu-box" onclick="$('.menu-items').toggle()"></div>
                                    <ul class="menu-items pos-absolute pos-left" style="display:none">
                                        <li data-filetr-name="prodname"     data-select="true"  ><em></em>名稱</li>
                                        <li data-filetr-name="listprice"    data-select="false" ><em></em>原價</li>
                                        <li data-filetr-name="promoprice"   data-select="true" ><em></em>促銷價</li>
                                        <li data-filetr-name="serial"       data-select="false" ><em></em>ID</li>
                                        <li data-filetr-name="supplement"   data-select="false" ><em></em>供應情況</li>
                                        <li data-filetr-name="neworused"    data-select="false"  ><em></em>使用狀況</li>
                                        <li data-filetr-name="class"        data-select="false"  ><em></em>類別</li>
                                        <li data-filetr-name="weburl"       data-select="false" ><em></em>連結網址</li>
                                    </ul>
                                </div>
                            </li>

                            <li class="txt-cell p-l20 txt-noselect">
                                <div class="select-box pos-relative">
                                    <span class="pos-absolute pointer-events-none">顯示</span>
                                    <select>
                                        <option value="1">10則</option>
                                        <option value="2">20則</option>
                                        <option value="3">30則</option>
                                        <option value="4">40則</option>
                                        <option value="5">50則</option>
                                    </select>
                                </div>
                            </li>
                            <li class="txt-cell p-l20">
                                <a class="btn-listtype" href="12商品清單-表格.html" data-list="row"></a>
                                <a class="btn-listtype" href="12商品清單-卡片.html" data-list="box"></a>
                            </li>
                        </ul>

                        <script>
                            //參考用
                            $(function(){
                                //牙齒底色 && 表格內容篩選                              
                                var $btn_filter=$('.btn-filter');
                                $btn_filter.on('click',function() {
                                    var filtertype=$(this).attr('data-filter');
                                    $('.nav-wrap.prodtable, .prodtable-wrap, .prodcard-wrap').attr('data-filter',filtertype);
                                });

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
                                var $btn_selectinfomenu=$('.select-menu li');
                                $btn_selectinfomenu.on('click',function(){
                                    $(this).attr('data-select',function(_, attr){return!(attr=='true')});
                                    var _datafiltername= $(this).attr('data-filetr-name');
                                    var _dataselect= $(this).attr('data-select');
                                    showhideInfo(_datafiltername,_dataselect);
                                });
                                    
                                function showhideInfo(datainfoname,dataselect){
                                     console.log(datainfoname,dataselect);
                                    if (dataselect=="true"){
                                        $('.prodcard-infobox div[data-info-name*='+ datainfoname + ']').css('display','inline-block');
                                    }else{
                                        $('.prodcard-infobox div[data-info-name*='+ datainfoname + ']').css('display','none');
                                    }
                                }
                                
                                function init(){
                                    showhideInfo('prodname','true');
                                    showhideInfo('listprice','false');
                                    showhideInfo('promoprice','true');
                                    showhideInfo('serial','false');
                                    showhideInfo('supplement','false');
                                    showhideInfo('neworused','false');
                                    showhideInfo('class','false');
                                    showhideInfo('weburl','false');
                                }
                                 init();
                            });
                        </script>
                    </div>
                </div>
                <!-- (商品清單) 表格目錄功能列 結束 -->

                <!-- 卡片內容 開始 -->
                <!-- data-filter:               商品篩選 -->
                <!-- data-filter='all'          顯示所有商品 -->
                <!-- data-filter='enable'       只顯示啟用商品 -->
                <!-- data-filter='sealed'       只顯示封存商品 -->

                <!-- data-type:                 商品列型態 -->
                <!-- data-type='enable'         已啟用商品列 -->
                <!-- data-type='sealed'         已封存商品列 -->
                <div class="prodcard-wrap m-b30" data-filter="all">
                    
                    <!--隱藏區塊-->
                    <!-- checkbox-checkAll -->
                    <div class="input-check" style="display:none">
                        <input type="checkbox" id="checkAll" value="checkAll"><label for="checkAll"></label>
                    </div>
                    <!-- 圖像廣告預覽畫面 -->
                    <div style="display:none;" id="detailDiv">
                        <div class="detail-wrap txt-noselect">
                            <div class="detail-box">
                                <div class="img-box">
                                    <img src="https://e.ecimg.tw/items/DICY17A90081WF0/000001_1492588581.jpg">
                                </div>
                                <div class="info-box">
                                    <div class="serial">product_201807301</div>
                                    <div class="prodname">Samsonite新秀麗 20吋Lite Shock極輕Curv四 輪拉桿頂級硬殼箱(黑)</div>
                                    <div class="group-box">
                                        <div class="listprice">原價$12,999</div>
                                        <div class="promoprice">特價$11,000</div>
                                        <div class="supplement">有庫存</div>
                                        <div class="neworused">福利品</div>
                                        <div class="class">硬殼行李箱</div>
                                    </div>
                                    <div class="weburl">https://24h.pchome.com.tw/prod/DICY17-A90081WF0?fq=/S/DIBKPH</div>
                                </div>
                            </div>
                        </div>
                    </div>


                    <!--item1-->
                    <#if prodList?exists>
	                    <#if prodList?size != 0>
	                    	<#list prodList as prods>	
			                    <div class="prodcard-box txt-noselect pos-relative" data-type="enable">
			
			                        <div class="prodcard-checkbox"><div class="input-check"><input type="checkbox" id="check1"><label for="check1"></label></div></div>
			
			                        <div onclick="viewDetail()">
			                            <div class="prodcard-imgbox">
			                                <img src="http://showstg.pchome.com.tw/pfp/${prods.ecImg!}">
			                            </div>
			                            <div class="prodcard-infobox">
			                                <div class="group g1">
			                                    <div data-info-name="prodname">${prods.ecName!}</div>
			                                </div>
			                                <div class="group g2">
			                                    <div data-info-name="listprice">原價<span>$</span>${prods.ecPrice!}</div>
			                                    <div data-info-name="promoprice">特價<span>$</span><i>${prods.ecDiscountPrice!}</i></div>
			                                </div>
			                                <div class="group g3">
			                                    <div data-info-name="serial">${prods.catalogProdSeq!}</div>
			                                    <div data-info-name="supplement">${prods.ecStockStatus!}</div>
			                                    <div data-info-name="neworused">${prods.ecUseStatus!}</div>
			                                    <div data-info-name="class">${prods.ecCategory!}</div>
			                                </div>
			                                <div class="group g4">
			                                    <div data-info-name="weburl"><a href=${prods.ecUrl!} target="_blank"></a></div>
			                                </div>
			                            </div>
			                        </div>
			                    </div>
	   						</#list>
	   					</#if>
	   				</#if>		
                </div>

                <script>
                    //參考用
                    $(function () {

                        var $checkall = $('#checkAll');
                        var $menu = $('.nav-wrap.prodtable');
                        var $checkbox =$('.txt-row input[type="checkbox"], .prodcard-box input[type="checkbox"]');
                        var $checkbox_enable =$('.txt-row[data-type="enable"] input[type="checkbox"], .prodcard-box[data-type="enable"] input[type="checkbox"]');
                        var $checkbox_sealed =$('.txt-row[data-type="sealed"] input[type="checkbox"], .prodcard-box[data-type="sealed"] input[type="checkbox"]');
                        
                        // table 第一列 目錄的全選核取按鈕功能 + 被選取的物件改底色為白色
                        $checkall.change(function() {
                            var _filter = $('.prodtable-wrap, .prodcard-wrap').attr('data-filter');
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

                        // 被選取的物件改style
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

                    function viewDetail(){
                        $.fancybox(
                            $('#detailDiv').html(),
                            {
                                'autoDimensions'	: false,
                                'width'         	: 700,
                                'height'        	: 385,
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
                <!-- 卡片內容 結束 -->

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