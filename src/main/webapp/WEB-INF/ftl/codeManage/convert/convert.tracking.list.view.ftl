<div class="container-prodmanage">


<#if convertList?exists && (convertList?size>0)>
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
                                    <select id="pageSizeSelect">
                                      	<option value="2" <#if pageSizeSelected==2>selected</#if> >2則</option>
                                 		<option value="10" <#if pageSizeSelected==10>selected</#if> >10則</option>
                                        <option value="20" <#if pageSizeSelected==20>selected</#if> >20則</option>
                                        <option value="30" <#if pageSizeSelected==30>selected</#if> >30則</option>
                                        <option value="40" <#if pageSizeSelected==40>selected</#if> >40則</option>
                                        <option value="50" <#if pageSizeSelected==50>selected</#if> >50則</option>
                                    </select>
                                </div>
                            </li>
                            <li class="txt-cell group-default">
                                <div class="input-text">
                                    <input type="text" id="txtProdName" name="" maxlength="20" value="${convertName!}" required placeholder="尋找目錄">
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

                        <!-- 表格欄位標題 -->
                        
                            <div class="txt-row header">
                                <div class="txt-cell col-ptagcheckbox   ">狀態</div>
                                <div class="txt-cell col-ptagname-trans ">名稱</div>
                                <div class="txt-cell col-ptagcode-trans ">追蹤代碼</div>
                                <div class="txt-cell col-ptagstatus-trans">認證狀態</div>
                                <div class="txt-cell col-ptagstatus-trans">轉換條件</div>
                                <div class="txt-cell col-type-transfer  ">轉換類型</div>
                                <div class="txt-cell col-days-interactive"><span>點擊後</span><span>轉換追溯</span></div>
                                <div class="txt-cell col-days-browse     "><sapn>瀏覽後</span><span>轉換追溯</span></div>
                                <div class="txt-cell col-value-transfer  ">轉換價值</div>
                                <div class="txt-cell col-click-transfer  "><span>點擊後</span><span>轉換數</span></div>
                                <div class="txt-cell col-browse-transfer "><span>瀏覽後</span><span>轉換數</span></div>
                                <div class="txt-cell col-all-transfer    ">所有轉換</div>
                                <div class="txt-cell col-ptagcheckbox txt-center">刪除</div>
                                
                            </div>
						
                            <!-- row1 -->
                            <div id="convertListDiv">
                            	<#if convertList?exists>
	                        		<#if convertList?size != 0>
	                        			<#assign index1 = 1>
	                        			<#list convertList as converts>
				                            <div class="txt-row txt-row-data" data-type="enable">
												<div class="txt-cell col-ptagcheckbox">
				                                    <label class="pos-middle switch-adstatus">
				                                    	<#if converts.convertStatus == "0">
					                                        <input type="checkbox" id=${converts.convertSeq!}>
					                                    <#elseif converts.convertStatus == "1">
				                                        	<input type="checkbox" checked="checked" id=${converts.convertSeq!}>
				                                        </#if>
					                                        <span class="slider"></span>
				                                    </label>
				                                </div>
				                                <div class="txt-cell col-ptagname-trans     "><a href="editConvertTrackingView.html?convertSeq=${converts.convertSeq!}">${converts.convertName!?replace(" ", "&nbsp;")}</a><br><small>ID：${converts.convertSeq!}</small></div>
				                                <div class="txt-cell col-ptagcode-trans     "><a href="javascript:void(0)" onclick="getpTag('${converts.paId!}','${converts.convertSeq!}','${converts.convertName!}','${converts.convertCodeType!}')">取得代碼</a></div>
				                                <#if converts.verifyStatus == "1">
				                                	<div class="txt-cell col-ptagstatus-trans   "><span data-certificated="true">已認證</span></div>
				                                <#else>   
					                                <div class="txt-cell col-ptagstatus-trans   "><span data-certificated="false">未認證</span></div>
				                                </#if>
				                                <div class="txt-cell col-type-transfer      ">${converts.convertTypeDesc!}</div>
				                                <div class="txt-cell col-type-transfer      ">${converts.convertClassDesc!}</div>
				                                <div class="txt-cell col-days-interactive   ">${converts.clickRangeDate!}天</div>
				                                <div class="txt-cell col-days-browse        ">${converts.impRangeDate!}天</div>
				                                <div class="txt-cell col-value-transfer     ">${converts.transConvertPrice!}</div>
				                                <div class="txt-cell col-click-transfer     ">${converts.transCKConvertCount!}</div>
				                                <div class="txt-cell col-browse-transfer    ">${converts.transPVConvertCount!}</div>
				                                <div class="txt-cell col-all-transfer       ">${converts.transAllConvertCount!}</div>
				                                <div class="txt-cell col-ptagcheckbox col-delete p-none"><a href="javascript:void(0)" onclick="deleteConvertAjax('${converts.convertSeq!}')"></a></div>
				                            </div>
				                        <#assign index1 = index1 + 1>   
			                            </#list>
		                            </#if>	
	                            </#if>	
							</div>
							
							
							
                            <!-- summary -->
	                        <div id="sumConvertCountDiv" class="txt-row summary">
	                            <#if convertList?size != 0>
	                                <div class="txt-cell col-ptagcheckbox       "><p class="summary-tit pos-absolute pos-left pos-top ali-middle">總計：所有轉換動作</p></div>
	                                <div class="txt-cell col-ptagname-trans     "></div>
	                                <div class="txt-cell col-ptagcode-trans     "></div>
	                                <div class="txt-cell col-ptagstatus-trans   "></div>                                
	                                <div class="txt-cell col-type-transfer      "></div>
	                                <div class="txt-cell col-type-transfer      "></div>
	                                <div class="txt-cell col-days-interactive   "></div>
	                                <div class="txt-cell col-days-browse        "></div>
	                                <div class="txt-cell col-value-transfer     "></div>
	                                <div class="txt-cell col-click-transfer     ">${sumConvertCount.transSumCKConvertCount!}</div>
	                                <div class="txt-cell col-browse-transfer    ">${sumConvertCount.transSumPVConvertCount!}</div>
	                                <div class="txt-cell col-all-transfer       ">${sumConvertCount.transSumAllConvertCount!}</div>
	                                <div class="txt-cell col-ptagcheckbox"></div>
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
<#else>
	<#if returnMsg?exists && returnMsg!="">
		${returnMsg!}
	</#if>
</#if> 

</div>