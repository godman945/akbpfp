<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<div class="container-prodmanage">

<#if returnMsg?exists && returnMsg!="">
	${returnMsg!}
<#else>
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
            <p class="title-box h1 txt-center">編輯 再行銷追蹤代碼</p>
            <div class="content-box w-900">
                
                <!-- 代碼名稱 開始 -->
                <div class="section-box p-b60">
                    <p class="title-box h2">代碼名稱</p>
                    <div class="input-text">
                        <input id="retargetingName" type="text" name="" maxlength="20" value="${retargetingTrackingResultBean.trackingName!}" required placeholder="填寫代碼名稱，最多20字">
                        <div id="retargetingNameMsg" class="msg-error" style="display:none;">請輸入名稱</div>
                    </div>
                </div>
                <!-- 代碼名稱 結束 -->

                <!-- 選擇代碼類型 開始 -->
                <div class="section-box p-b60">
                    <p class="title-box h2">選擇代碼類型</p>

                    <div class="topic-wrap txt-center txt-noselect w-360">
                    	<#if retargetingTrackingResultBean.codeType == "0" >
                        	<div class="topic-box selected" onclick="changeTopicboxStyle(this,0)">
                        <#else>
                        	<div class="topic-box" onclick="changeTopicboxStyle(this,0)">
                        </#if>
	                            <img src="<@s.url value="/" />html/img/catalogProd/retargeting/ptagtype1.png">
	                            <p>一般網頁追蹤</p>
	                            <span>一般網頁追蹤適用於所有廣告類型</span>
	                        </div>
                    </div>

                    <div class="topic-wrap txt-center txt-noselect w-360">
                    	<#if retargetingTrackingResultBean.codeType == "1" >
                        	<div class="topic-box selected" onclick="changeTopicboxStyle(this,1)">
                        <#else>	
                        	<div class="topic-box" onclick="changeTopicboxStyle(this,1)">
                        </#if>
	                            <img src="<@s.url value="/" />html/img/catalogProd/retargeting/ptagtype2.png">
	                            <p>動態商品廣告追蹤</p>
	                            <span>廣告中的商品將與消費者所接觸過的商品頁相關</span>
	                        </div>
                    </div>

                </div>
                <!-- 選擇代碼類型 結束 -->


                <!-- 追蹤行銷效期 開始 -->
                <div class="section-box p-b60">
                    <div class="trackingperiod-wrap">
                    
                        <p class="title-box h2 w-150 txt-inlineblock txt-bottom">追蹤行銷效期</p>

                        <div class="trackingperiod-box txt-inlineblock">
                            <span>1天</span><span>7天</span><span></span><span></span><span>28天</span>
                            <div class="trackingperiod-slider">
                                <em class="slider-pointer type1"></em>
                                <em class="slider-pointer type2"></em>
                                <em class="slider-pointer type3"></em>
                                <div class="slider-track"><div class="track-handler"></div></div>
                                <b>追蹤行銷效期：用戶與店家網站互動後，成為再行銷對象的時間長度</b>
                            </div>
                            
                        </div>

                    </div>
                </div>
                <!-- 追蹤行銷效期 結束 -->


                <!-- 追蹤代碼 開始 -->
                <div class="section-box p-b60">
                    <p class="title-box h2">追蹤代碼<small>請將下方代碼複製並貼在您網站上的每個網頁</small></p>
                    <div class="code-wrap pos-relative">
                        <a class="btn-copyto code-copy pos-absolute pos-right pos-top txt-noselect" data-clipboard-action="copy" data-clipboard-target="#code1"></a>
                        <div class="code-box">
                            <pre class="snippet">
                            
                            <#if retargetingTrackingResultBean.codeType == "0" >
                                <textarea id="code1" readonly>
<script  id="pcadscript" language="javascript" async src="https://pacl.pchome.com.tw/js/ptag.js"></script>
<script>
   window.dataLayer = window.dataLayer || [];
   function ptag(){dataLayer.push(arguments);}
   ptag({'paid':'${retargetingTrackingResultBean.paId!}'});
   ptag('event','tracking',{
   'tracking_id':'${retargetingTrackingResultBean.trackingSeq!}'
   });   
</script>
                                </textarea>
                            <#else>
                                                                <textarea id="code1" readonly>
<script  id="pcadscript" language="javascript" async src="https://pacl.pchome.com.tw/js/ptag.js"></script>
<script>
   window.dataLayer = window.dataLayer || [];
   function ptag(){dataLayer.push(arguments);}
   ptag({'paid':'${retargetingTrackingResultBean.paId!}'});
   ptag('event','tracking',{
   'tracking_id':'${retargetingTrackingResultBean.trackingSeq!}',
   'prod_id':'',
   'prod_price':'',
   'prod_dis':''   
   });   
</script>
                                </textarea>
                             </#if>
                                
                            </pre>
                        </div>
                    </div>
                    <div class="link-copyto p-t5 txt-right">
                        <a class="btn-copyto" data-clipboard-action="copy" data-clipboard-target="#code1"><em>複製代碼</em></a>
                    </div>
                    
                    <div class="copySuccess txt-center" style="display:none;">複製成功</div>
                    
                    
		                <div id="dynamicAdDescription" class="copyDescription" style="display;">
	                    	<small>
								● prod_id：可填入商品ID，以建立網頁與商品的配對。<br>
	                            ● prod_price：可填入商品原價，廣告中會優先顯示填入的價格。<br>
	                            ● prod_dis：可填入商品促銷價，廣告中會優先顯示填入的價格。<br>
	                        </small>
						</div>
					
					

                    <!-- clipboard.js -->
                    <!-- A modern approach to copy text to clipboard -->
                    <script src="js/clipboard.min.js"></script>
                    <script>
                        var clipboard = new ClipboardJS('.btn-copyto');
                        clipboard.on('success', function(e) {
                            console.log(e);
                            e.clearSelection();
                        });
                        clipboard.on('error', function(e) {
                            console.log(e);
                        });
                    </script>
                </div>
                <!-- 追蹤代碼 結束 -->

                
                <!-- 電子郵件寄送代碼 開始 -->
                <div class="section-box p-b60">
                    <p class="title-box h2">以電子郵件寄送代碼<small>若有多個地址請以分號分隔</small></p>
                    <div class="input-text inputemail">
                        <input id="mailReceivers" type="text" name="" maxlength="200" value="" required placeholder="you@email.com">
				        <input name="" id ="sendMail" type="button" value="寄出">
                        <div id="emailMsgError" class="msg-error" style="display:none">錯誤訊息</div>
                    </div>
                </div>
                <!-- 電子郵件寄送代碼 結束 -->

            </div>
        </div>
        <!-- 新增再行銷追蹤代碼 結束 -->

        <!-- 按鈕 下一步 -->
        <div class="button-box w-900 txt-center p-tb30">
            <div class="input-button"><input id="editRetargetingTracking" type="button" value="儲存"></div>
        </div>
        
        
        <input type="hidden" id="paid" value="${retargetingTrackingResultBean.paId!}">
	    <input type="hidden" id="trackingSeq" value="${retargetingTrackingResultBean.trackingSeq!}">
	    <input type="hidden" id="codeType" value="${retargetingTrackingResultBean.codeType!}">
	    <input type="hidden" id="trackingRangeDate" value="${retargetingTrackingResultBean.trackingRangeDate!}">

</#if> 
</div>
    
   
    
    
    