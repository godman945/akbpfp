<div class="container-prodmanage">

    <!-- 次目錄導覽列 開始 -->
    <!-- hidden 隱藏所有牙齒 -->
    <!-- hidetabs 只顯示第一顆牙齒 -->
    <!-- tab1 tab2 tab3 tab4 tab5 牙齒由左至右底線 -->
    <div class="nav-wrap pos-relative bg-white hidden">
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


    <!-- 新增再行銷追蹤代碼 開始 -->
    <div class="content-wrap bg-white">
        <p class="title-box h1 txt-center">建立 轉換追蹤代碼</p>
        <div class="content-box w-900">

            <!-- 代碼名稱 開始 -->
            <div class="section-box p-b60">
                <p class="title-box h2">代碼名稱</p>
                <div class="input-text">
                    <input id="convertName" type="text" name="" maxlength="20" value=${convertTrackingBean.convertName!} required placeholder="填寫代碼名稱，最多20字">
                    <div id="convertNameMsg" class="msg-error" style="display:none;">最多20字</div>
                </div>
            </div>
            <!-- 代碼名稱 結束 -->

            <!--選擇轉換追蹤條件 開始-->
            <div class="section-box p-b60">
                <p class="title-box h2">選擇轉換追蹤條件</p>
                <div class="transselect-group value">
                    <div class="inputradio-box txt-noselect">
                        <div class="input-radio">
                            <input type="radio" value="1" id="valueselect4" name="urlfilter" onclick="
                            $('.urlfilter-wrap').css('display','none');$('#customConvertPriceDiv').css('display','');">
                            <label for="valueselect4"></label>
                            <span>標準轉換追蹤(預設)</span>
                            <small> 追蹤每個使用此代碼的網頁</small>
                        </div>
                    </div>
                    <div class="inputradio-box txt-noselect">
                        <div class="input-radio">
                            <input type="radio" value="2" id="valueselect5" name="urlfilter" onclick="
                            $('.urlfilter-wrap').fadeIn('fast');$('#customConvertPriceDiv').css('display','none');"">
                            <label for="valueselect5"></label>
                            <span>自訂轉換追蹤條件</span>
                            <small> 僅追蹤符合以下網址條件的轉換事件</small>
                        </div>
                    </div>

 					
 					<!--選擇轉換追蹤條件(標準or自訂)-->
					<#if convertTrackingBean.convertType == "1"  >
	                    <!--網址篩選 開始-->
	                    <div class="urlfilter-wrap p-l20" style="display:none;">
	                        <div class="filter-wrap">
	                            <div class="filter-group type2">
	                                <span>轉換追蹤條件</span>
	                                <div class="select-box" data-level="">
	                                    <select>
	                                        <option value="1">包含</option>
	                                        <option value="2">不包含</option>
	                                        <option value="3">等於</option>
	                                    </select>
	                                </div>
	                                <div class="txt-inlineblock" data-level="">
	                                    <div class="input-text" data-level="1">
	                                        <input type="text" name="" maxlength="" value="" placeholder="網址">
	                                        <div class="msg-error urlMsg" style="display:none">請填寫網址</div>
	                                    </div>
	                                </div>                               
	                            </div>
	                            
	                            <div class="filter-group type2">
	                                <span>也必須符合</span>
	                                <div class="select-box" data-level="">
	                                    <select>
	                                        <option value="1">包含</option>
	                                        <option value="2">不包含</option>
	                                        <option value="3">等於</option>
	                                    </select>
	                                </div>
	                                <div class="txt-inlineblock" data-level="">
	                                    <div class="input-text" data-level="1">
	                                        <input type="text" name="" maxlength="" value="" placeholder="網址">
	                                        <div class="msg-error urlMsg" style="display:none">請填寫網址</div>
	                                    </div>
	                                </div>
	                                <div class="icon-kill" onclick="$(this).parent().remove()"></div>
	                            </div>
	                        </div>
	                        <a class="link-addfilter">＋增加篩選條件</a>
	                    </div>
	 					<!--網址篩選 結束-->
 					<#elseif convertTrackingBean.convertType == "2">
						<!--網址篩選 開始-->
	                    <div class="urlfilter-wrap p-l20" >
	                        <div class="filter-wrap">
	                        
	                        	<#assign index = 1>
	                        
	                        	<#list convertTrackingRuleList as ruleList>
		                            <div class="filter-group type2">
		                            	<#if index == 1>
		                                	<span>轉換追蹤條件</span>
		                                <#else>
		                                	<span>也必須符合</span>
		                                </#if>
			                                <div class="select-box" data-level="">
			                                    <select>
				                                	<option value="1" <#if ruleList.convertRuleWay == '1' >selected</#if> >包含</option>
				                                    <option value="2" <#if ruleList.convertRuleWay == '2' >selected</#if> >不包含</option>
				                                    <option value="3" <#if ruleList.convertRuleWay == '3' >selected</#if> >等於</option>
			                                    </select>
			                                </div>
			                                <div class="txt-inlineblock" data-level="">
			                                    <div class="input-text" data-level="1">
			                                        <input type="text" name="" maxlength="" value="${ruleList.convertRuleValue!}" placeholder="網址">
			                                        <div class="msg-error urlMsg" style="display:none">請填寫網址</div>
			                                    </div>
			                                </div>  
			                            <#if index != 1>    
			                                <div class="icon-kill" onclick="$(this).parent().remove()"></div>    
			                            </#if>            
			                            </div>	
		                            <#assign index = index + 1>
	                            </#list>
	                        </div>
	                        <a class="link-addfilter">＋增加篩選條件</a>
	                    </div>
	 					<!--網址篩選 結束-->
					</#if>
 					
 					
 					
 					
 					
 					
                </div>
            </div>
            <!--選擇轉換追蹤條件 結束-->

            <!-- 選擇轉換類型 開始 -->
            <div class="section-box p-b60">
                <p class="title-box h2">選擇轉換類型</p>

                <div class="transselect-group type">
                    <div class="inputradio-box txt-noselect">
                        <div class="input-radio">
                            <input type="radio" value="1" id="typeselect1" name="typeselect">
                            <label for="typeselect1"></label>
                            <span>查看內容</span>
                        </div>
                    </div>

                    <div class="inputradio-box txt-noselect">
                        <div class="input-radio">
                            <input type="radio" value="2" id="typeselect2" name="typeselect">
                            <label for="typeselect2"></label>
                            <span>搜尋</span>
                        </div>
                    </div>

                    <div class="inputradio-box txt-noselect">
                        <div class="input-radio">
                            <input type="radio" value="3" id="typeselect3" name="typeselect">
                            <label for="typeselect3"></label>
                            <span>加到購物車</span>
                        </div>
                    </div>

                    <div class="inputradio-box txt-noselect">
                        <div class="input-radio">
                            <input type="radio" value="4" id="typeselect4" name="typeselect">
                            <label for="typeselect4"></label>
                            <span>加到購物清單</span>
                        </div>
                    </div>

                    <div class="inputradio-box txt-noselect">
                        <div class="input-radio">
                            <input type="radio" value="5" id="typeselect5" name="typeselect">
                            <label for="typeselect5"></label>
                            <span>開始結帳</span>
                        </div>
                    </div>

                    <div class="inputradio-box txt-noselect">
                        <div class="input-radio">
                            <input type="radio" value="6" id="typeselect6" name="typeselect">
                            <label for="typeselect6"></label>
                            <span>新增付款資料</span>
                        </div>
                    </div>

                    <div class="inputradio-box txt-noselect">
                        <div class="input-radio">
                            <input type="radio" value="7" id="typeselect7" name="typeselect">
                            <label for="typeselect7"></label>
                            <span>購買</span>
                        </div>
                    </div>

                    <div class="inputradio-box txt-noselect">
                        <div class="input-radio">
                            <input type="radio" value="8" id="typeselect8" name="typeselect">
                            <label for="typeselect8"></label>
                            <span>完成註冊</span>
                        </div>
                    </div>

                </div>
            </div>
            <!-- 選擇轉換類型 結束 -->



            <!-- 轉換價值 開始 -->
            <div class="section-box p-b60">
                <p class="title-box h2">轉換價值</p>
                <div class="transselect-group value">

                    <div class="inputradio-box txt-noselect">
                        <div class="input-radio">
                            <input type="radio" value="1" id="valueselect1" name="valueselect">
                            <label for="valueselect1"></label>
                            <span>不指定價值</span>
                        </div>
                    </div>

                    <div class="inputradio-box txt-noselect">
                        <div class="input-radio">
                            <input type="radio" value="2" id="valueselect2" name="valueselect">
                            <label for="valueselect2"></label>
                            <span>統一轉換價值</span>
                        </div>
                        <div class="input-number" data-width="130">
                            NT<input id="unifiedConvertPrice" type="text" name="" maxlength="6"  <#if convertTrackingBean.convertPriceType=="2" >value=${convertTrackingBean.convertPrice!}<#else>value=""</#if> >
                            <span>元</span>
                            <div id="unifiedConvertPriceMsg" class="msg-error" style="display:none;">請填寫非0整數</div>
                        </div>
                        <div class="msg-btn" onclick="$(this).children('em').fadeToggle('fast');">
                            <em style="display: none;">您所設定的轉換價值將會套用在所有轉換上</em>
                        </div>
                    </div>

                    <div id="customConvertPriceDiv" class="inputradio-box txt-noselect">
                        <div class="input-radio">
                            <input type="radio" value="3" id="valueselect3" name="valueselect">
                            <label for="valueselect3"></label>
                            <span>自訂轉換價值</span>
                        </div>
                        <div class="input-number" data-width="165">
                            預設NT<input id="customConvertPrice" type="text" name="" maxlength="6" <#if convertTrackingBean.convertPriceType=="3" >value=${convertTrackingBean.convertPrice!}<#else>value=""</#if> >
                            <span>元</span>
                            <div id="customConvertPriceMsg" class="msg-error" style="display:none;">請填寫非0整數</div>
                        </div>
                        <div class="msg-btn" onclick="$(this).children('em').fadeToggle('fast');">
                            <em style="display: none;">您可以透過程式碼決定每一個轉換所帶來的價值，而程式碼沒有包含轉換價值的狀況下，則會套用您所設定的「預設」值。</em>
                        </div>
                    </div>

                </div>
            </div>
            <!-- 轉換價值 結束 -->



            <div class="section-box p-b60">
                <!-- 互動後轉換追溯 開始 -->
                <div class="trackingperiod-wrap" style="border:none">
                    <p class="title-box h2 w-150 txt-inlineblock txt-bottom">互動後轉換追溯</p>
                    <div class="trackingperiod-box txt-inlineblock">
                        <span>1天</span><span>7天</span><span></span><span></span><span>28天</span>
                        <div class="trackingperiod-slider">
                            <em id="clickRangeDate1" class="slider-pointer type1"></em>
                            <em id="clickRangeDate2" class="slider-pointer type2"></em>
                            <em id="clickRangeDate3" class="slider-pointer type3"></em>
                            <div class="slider-track">
                                <div class="track-handler"></div>
                            </div>
                            <div class="msg-btn" onclick="$(this).children('em').fadeToggle('fast');">
                                <em style="display: none;">互動後轉換追溯是指轉換被歸因在互動的有效期限，若轉換發生在最後一次互動後的有效期限內，則該轉換會被歸因在最後一次互動上。</em>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 互動後轉換追溯 結束 -->

                <!-- 瀏覽後轉換追溯 開始 -->
                <div class="trackingperiod-wrap">
                    <p class="title-box h2 w-150 txt-inlineblock txt-bottom">瀏覽後轉換追溯</p>
                    <div class="trackingperiod-box txt-inlineblock">
                        <span>1天</span><span>7天</span><span></span><span></span><span>28天</span>
                        <div class="trackingperiod-slider">
                            <em id="impRangeDate1" class="slider-pointer type1"></em>
                            <em id="impRangeDate2" class="slider-pointer type2"></em>
                            <em id="impRangeDate3" class="slider-pointer type3"></em>
                            <div class="slider-track">
                                <div class="track-handler"></div>
                            </div>
                            <div class="msg-btn" onclick="$(this).children('em').fadeToggle('fast');">
                                <em style="display: none;">瀏覽後轉換追溯是指轉換被歸因在曝光的有效期限，若該消費者在沒有廣告互動的情況下，於追溯期限內發生轉換，則該轉換會被歸因於最後一次曝光上。若該消費者有發生廣告互動，則轉換會優先被歸因在互動上。</em>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 瀏覽後轉換追溯 結束 -->
            </div>

            <div class="section-box p-b60">

            </div>




            <!-- 追蹤代碼 開始 -->
            <div class="section-box p-b60">
                <p class="title-box h2">追蹤代碼<small>請將下方代碼複製並貼在您網站上的每個網頁</small></p>
                <div class="code-wrap pos-relative">
                    <a class="btn-copyto code-copy pos-absolute pos-right pos-top txt-noselect" data-clipboard-action="copy"
                        data-clipboard-target="#code1"></a>
                    <div class="code-box">
                        <pre class="snippet">
                                <textarea id="code1" readonly>
<script  id="pcadscript" language="javascript" async src="https://kdpic.pchome.com.tw/js/ptag.js"></script>
<script>
   window.dataLayer = window.dataLayer || [];
   function ptag(){dataLayer.push(arguments);}
   ptag({'paid':${convertTrackingBean.paId!}});
   ptag('event','convert',{
   'convert_id':${convertTrackingBean.convertSeq!},
   'convert_price':'',
   'op1':'',
   'op2':''});   
</script>
                                </textarea>
                            </pre>
                    </div>
                </div>
                <div class="link-copyto p-t5 txt-right">
                    <a class="btn-copyto" data-clipboard-action="copy" data-clipboard-target="#code1"><em>複製代碼</em></a>
                </div>

            </div>
            <!-- 追蹤代碼 結束 -->


            <!-- 電子郵件寄送代碼 開始 -->
            <div class="section-box p-b60">
                <p class="title-box h2">以電子郵件寄送代碼<small>若有多個地址請以逗號分隔</small></p>
                <div class="input-text inputemail">
                    <input id="mailReceivers" type="text" name="" maxlength="200" value="" required placeholder="you@email.com">
                    <div class="button-box w-900 txt-center p-tb30">
                    	<div class="input-button"><input id ="sendMail" type="button" value="發送mail"></div>
                    </div>
                    <div id="emailMsgError" class="msg-error" style="display:none">錯誤訊息</div>
                </div>
            </div>
            <!-- 電子郵件寄送代碼 結束 -->


        </div>
    </div>
    <!-- 新增再行銷追蹤代碼 結束 -->

    <!-- 按鈕 完成 -->
    <div class="button-box w-900 txt-center p-tb30">
        <div class="input-button"><input id="editConvertTracking" type="button" value="儲存"></div>
    </div>

</div>


  <input type="hidden" id="paid" value="${convertTrackingBean.paId!}">
  <input type="hidden" id="convertSeq" value="${convertTrackingBean.convertSeq!}">
  <input type="hidden" id="convertType" value="${convertTrackingBean.convertType!}">
  <input type="hidden" id="convertClass" value="${convertTrackingBean.convertClass!}">
  <input type="hidden" id="convertPriceType" value="${convertTrackingBean.convertPriceType!}">
  <input type="hidden" id="convertPrice" value="${convertTrackingBean.convertPrice!}">
  <input type="hidden" id="convertRuleNum" value="${convertTrackingBean.convertRuleNum!}">
  <input type="hidden" id="clickRangeDate" value="${convertTrackingBean.clickRangeDate!}">
  <input type="hidden" id="impRangeDate" value="${convertTrackingBean.impRangeDate!}">

