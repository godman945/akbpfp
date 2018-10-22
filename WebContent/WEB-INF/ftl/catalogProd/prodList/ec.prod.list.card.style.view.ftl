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
                    <li class="txt-cell pos-relative"><a href="queryCatalogGroup.html?catalogSeq=${catalogSeq}">商品組合</a></li>
                    <li class="txt-cell pos-relative"><a href="#">商品資料</a></li>
                    <li class="txt-cell pos-relative"><a href="setup.html?catalogSeq=${catalogSeq}">設定</a></li>
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
                            <li class="txt-cell group-default p-r10">共<em id="prodQuantityTxt" class="txt-quantity">${totalCount!}</em>件商品目錄</li>
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
                                    <input type="text" id="txtProdName" name="" maxlength="20" value="" required placeholder="尋找商品">
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
                                    <select id="pageSizeSelect">
                                        <option value="10" <#if pageSizeSelected==10>selected</#if> >10則</option>
                                        <option value="20" <#if pageSizeSelected==20>selected</#if> >20則</option>
                                        <option value="30" <#if pageSizeSelected==30>selected</#if> >30則</option>
                                        <option value="40" <#if pageSizeSelected==40>selected</#if> >40則</option>
                                        <option value="50" <#if pageSizeSelected==50>selected</#if> >50則</option>
                                    </select>
                                    
                                </div>
                            </li>
                            <li class="txt-cell p-l20">
                                <a id="tableView" class="btn-listtype" href="#" data-list="row"></a>
                                <a id="cardView" class="btn-listtype" href="#" data-list="box"></a>
                            </li>
                        </ul>
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
                    
                    


                  <!--item1-->
                  <div id="prodListDiv">
                  	<#if prodList?exists>
	                    <#if prodList?size != 0>
	                    	<#assign index1 = 1>
	                    	<#list prodList as prods>
								<#if prods.ecStatus == "1">
				                    <div class="prodcard-box txt-noselect pos-relative" data-type="enable">
								<#else>
									<div class="prodcard-box txt-noselect pos-relative" data-type="sealed">
								</#if>
				                        <div class="prodcard-checkbox"><div class="input-check"><input type="checkbox" id="check${index1}" value=${prods.id!} ><label for="check${index1}"></label></div></div>
				
				                        <div onclick="viewDetail(this)">
				                            <div class="prodcard-imgbox">
				                                <img src="http://showstg.pchome.com.tw/pfp/${prods.ecImg!}">
				                            </div>
				                            <div class="prodcard-infobox">
				                                <div class="group g1">
				                                    <div data-info-name="prodname" id="prodnameDetailData" >${prods.ecName!}</div>
				                                </div>
				                                <div class="group g2">
				                                    <div data-info-name="listprice" id="listpriceDetailData" >原價<span>$</span>${prods.ecPrice!}</div>
				                                    <div data-info-name="promoprice" id="promopriceDetailData" >特價<span>$</span><i>${prods.ecDiscountPrice!}</i></div>
				                                </div>
				                                <div class="group g3">
				                                    <div data-info-name="serial" id="serialDetailData" >${prods.catalogProdSeq!}</div>
				                                    <div data-info-name="supplement" id="supplementDetailData" >${prods.ecStockStatusDesc!}</div>
				                                    <div data-info-name="neworused" id="neworusedDetailData" >${prods.ecUseStatusDesc!}</div>
				                                    <div data-info-name="class" id="classDetailData" >${prods.ecCategory!}</div>
				                                </div>
				                                <div class="group g4">
				                                    <div data-info-name="weburl" id="weburlDetailData" ><a href=${prods.ecUrl!} target="_blank" ></a></div>
				                                </div>
				                            </div>
				                        </div>
				                        
				                        
				                        
				                        <#if prods.ecCheckStatus == "2">
					                        <div class="prodcard-verify" data-verify="reject">${prods.ecCheckStatusDesc!}
	                            				<div class="notice-btn up" onclick="$(this).children('em').fadeToggle('fast');">
	                               					 <em style="display:none">"error reason！"</em>
	                           					</div>
	                        				</div>
	                        			<#else>
	                        				<div class="prodcard-verify" data-verify="">${prods.ecCheckStatusDesc!}
	                            				<div class="notice-btn up" onclick="$(this).children('em').fadeToggle('fast');">
	                                				<em style="display:none"></em>
	                            				</div>
	                        				</div>
                        				</#if>	
                        				
                        				
				                        
				                    </div>
			                    <#assign index1 = index1 + 1>
			            	</#list>
	   					</#if>
	   				</#if>	
	   				</div>
	   				
                </div>
                <!-- 卡片內容 結束 -->

                <!-- 頁碼 pagination 開始 -->
                <!-- data-order: 目前頁碼 -->
                <!-- data-quantity: 頁數 -->
                 <div id="pageData"  class="pagination-wrap txt-noselect m-b30" data-order=${currentPage!} data-quantity=${pageCount!}> 
                <!--<div class="pagination-wrap txt-noselect m-b30" data-order="10000" data-quantity="10000"> -->
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
    