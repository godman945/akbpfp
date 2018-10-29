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
                            <a href="catalogProd.html">所有商品目錄</a>
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


        <!-- 建立商品組合 開始 -->
        <div class="content-wrap bg-white">

            <div class="content-box bg-white w-900">
                <div class="portfolio-header pos-relative">
                    <p class="portfolio-title p-t15 p-b25">商品組合<small>使用篩選條件建立商品組合，以決定廣告中的商品項目。</small></p>

                    <!-- 建立商品組合-內容 開始 --> 
                    <div class="createportfolio-wrap">

                        <!--組合條件選單-->
                        <div class="createportfolio-box">

                            <!-- 組合名稱 -->
                            <p>商品組合名稱</p>
                            <div class="input-text">
                                <input type="text" id="catalogGroupName" name="" maxlength="20" value="" required placeholder="填寫組合名稱，最多20字">
                                <div id="groupNameMsgError" class="msg-error" style="display:none">請填寫組合名稱</div>
                            </div>

                            <!-- 篩選條件 -->
                            <div class="p-t40 filter-wrap">
                                <p>篩選條件</p>
                                
                                <#if ProdGroupFilterItemList?exists>
                        			<#if ProdGroupFilterItemList?size != 0>
                        				<#list ProdGroupFilterItemList as filterItem>
			                                <!-- 條件1 -->
			                                <#if filterItem.catalogGroupItemField == 'catalog_prod_seq' >
			                                	<div class="filter-group" data-level1="1" data-level2="1" data-level3="1">
			                                <#elseif filterItem.catalogGroupItemField == 'ec_name' >
			                                	<div class="filter-group" data-level1="2" data-level2="1" data-level3="1">
			                               	<#elseif filterItem.catalogGroupItemField == 'ec_price' >
			                                	<div class="filter-group" data-level1="3" data-level2="2" data-level3="2">
			                                <#elseif filterItem.catalogGroupItemField == 'ec_discount_price' >
			                                	<div class="filter-group" data-level1="4" data-level2="2" data-level3="2">
			                                <#elseif filterItem.catalogGroupItemField == 'ec_stock_status' >
			                                	<div class="filter-group" data-level1="5" data-level2="3" data-level3="3">
			                                <#elseif filterItem.catalogGroupItemField == 'ec_use_status' >
			                                	<div class="filter-group" data-level1="6" data-level2="3" data-level3="4">
			                                <#elseif filterItem.catalogGroupItemField == 'ec_category' >
			                                	<div class="filter-group" data-level1="7" data-level2="3" data-level3="1">	
			                                </#if>    
			                     
			                                    <!-- 篩選第一層 -->
			                                    <div class="txt-inlineblock level1">
			                                        <div class="select-box">
														<select class="selector" onchange="filterDisplayRule($(this).val(),$(this).parent().parent().parent());">
					                                    	<option value="catalog_prod_seq" <#if filterItem.catalogGroupItemField == 'catalog_prod_seq' >selected</#if> >ID</option>
					                                       	<option value="ec_name" <#if filterItem.catalogGroupItemField == 'ec_name' >selected</#if>>商品名稱</option>
					                                        <option value="ec_price" <#if filterItem.catalogGroupItemField == 'ec_price' >selected</#if>>原價</option>
					                                        <option value="ec_discount_price" <#if filterItem.catalogGroupItemField == 'ec_discount_price' >selected</#if>>特價</option>
					                                       	<option value="ec_stock_status" <#if filterItem.catalogGroupItemField == 'ec_stock_status' >selected</#if>>供應情況</option>
					                                        <option value="ec_use_status" <#if filterItem.catalogGroupItemField == 'ec_use_status' >selected</#if>>使用狀況</option>
					                                        <option value="ec_category" <#if filterItem.catalogGroupItemField == 'ec_category' >selected</#if>>類別</option>
														</select>
			                                        </div>
			                                    </div>
			
			
			
			
			                                    <!-- 篩選第二層 -->
			                                    <div class="txt-inlineblock level2">
				                                        <div class="select-box" data-level="1">
				                                            <select>
																<option value="like" <#if filterItem.catalogGroupItemCondition == 'like' >selected</#if>>包含</option>
																<option value="notlike" <#if filterItem.catalogGroupItemCondition == 'notlike' >selected</#if>>不包含</option>
				                                            </select>
				                                        </div>
				                                        <div class="select-box" data-level="2">
				                                            <select>
					                                                <option value="gt" <#if filterItem.catalogGroupItemCondition == 'gt' >selected </#if> >大於</option>
					                                                <option value="lt" <#if filterItem.catalogGroupItemCondition == 'lt' >selected </#if>>小於</option>
					                                                <option value="eq" <#if filterItem.catalogGroupItemCondition == 'eq' >selected </#if>>等於</option>
					                                                <option value="neq" <#if filterItem.catalogGroupItemCondition == 'neq' >selected </#if>>不等於</option>
					                                             
				                                            </select>
				                                        </div>
				                                        <div class="select-box" data-level="3">
				                                            <select>
																<option value="eq" <#if filterItem.catalogGroupItemCondition == 'eq' >selected</#if>  >屬於</option>
				                                                <option value="neq" <#if filterItem.catalogGroupItemCondition == 'neq' >selected</#if>  >不屬於</option>
				                                            </select>
				                                        </div>
			                                    </div>
			                                    
			                                    
			                                    <!-- 篩選第三層 -->
			                                    <div class="txt-inlineblock level3">
				                                        <div class="input-text" data-level="1" >
				                                            <input type="text" name="" maxlength="6" value="${filterItem.catalogGroupItemValue!}">
				                                            <div class="msg-error" style="display:none">請填寫篩選條件</div>
				                                        </div>
				                                        <div class="input-number" data-level="2">
				                                            NT<input type="text" name="" maxlength="6" value="${filterItem.catalogGroupItemValue!}">元
				                                            <div class="msg-error" style="display:none">請填寫數字</div>
				                                        </div>
				                                        <div class="select-box" data-level="3">
				                                            <select>
				                                            	<option value="0" <#if filterItem.catalogGroupItemValue == '0' >selected</#if> >無庫存</option>
				                                            	<option value="1" <#if filterItem.catalogGroupItemValue == '1' >selected</#if> >有庫存</option>
				                                             	<option value="2" <#if filterItem.catalogGroupItemValue == '2' >selected</#if> >預購</option>
				                                             	<option value="3" <#if filterItem.catalogGroupItemValue == '3' >selected</#if> >停售</option>
				                                            </select>
				                                        </div>
			                                    </div>
			
			                                    <!--刪除篩選 -->
			                                    <div class="icon-kill" onclick="deleteFilterCondition(event,$(this))"></div>
			                                </div> <!-- 條件1 -->
                                		 </#list>
					    			</#if>
	                    		</#if>	
                                
                                
                                
                                
                            </div> <!-- 篩選條件 -->

                            <a class="link-addfilter">＋增加篩選條件</a>



                            <!--符合條件商品列表-->
                            <div class="p-t40" >
                                <p>共<span id="totalCount">${totalCount!}</span>筆商品
								<div style="text-align:right;">
									第<span id="currentPage">${currentPage!}</span>頁 / 
									 共<span id="pageCount">${pageCount!}</span>頁</p>
								<div>

                                <div class="prodslider-wrap">
                                    <div class="prodslider-box">
                                        <div class="prodslider-items">
                                            
                                             
                                            <!--slide1-->
                                            <div class="prodslider-item">
                                                <!--item1-->
                                                <div id="prodListDiv">
                                                	<#if prodList?exists>
	                    								<#if prodList?size != 0>
	                    									<#list prodList as prods>
				                                               <div class="prodcard-box txt-noselect">
				                                                    <div class="prodcard-imgbox">
				                                                        <img src="http://showstg.pchome.com.tw/pfp/${prods.ecImg!}">
				                                                    </div>
				                                                    <div class="prodcard-infobox">
				                                                        <div class="group g1">
				                                                            <div data-info-name="prodname">${prods.ecName!}</div>
				                                                        </div>
				                                                        <div class="group g2">
				                                                            <div data-info-name="promoprice">特價<span>$</span><i>$</span><i>${prods.ecDiscountPrice!}</i></div>
				                                                        </div>
				                                                    </div>
				                                             	</div>
	                                            			</#list>
	   													</#if>
	   												</#if>	
                                                </div>
                                            </div><!--slide1-->


                                        </div>
                                    </div>
                                    <div class="prodslider-navbtn prev"></div>
                                    <div class="prodslider-navbtn next"></div>
                                </div>



                            </div>

                        </div>

                        


                    </div>
                    <!-- 建立商品組合-內容 結束 -->

                </div>
            </div>


            <!-- 建立or取消按鈕 開始 -->            
            <div class="button-box w-900 txt-center p-tb60">
                <div class="link-button"><a href="queryCatalogGroup.html?catalogSeq=${catalogSeq}">取消</a></div>
                <div class="input-button" id = "addCatalogProdGroup"><input type="button" value="建立商品組合"></div>
            </div>
            <!-- 建立or取消按鈕 結束 --> 

        </div>
        <!-- 建立商品組合 結束 -->
    </div>

	<input id="catalogSeqData" type="hidden" value="${catalogSeq}">