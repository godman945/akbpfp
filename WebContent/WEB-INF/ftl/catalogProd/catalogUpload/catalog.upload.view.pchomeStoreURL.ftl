<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<script language="JavaScript" src="<@s.url value="/" />html/js/catalogProd/commonCatalogProd.js?t=20181023004"></script>
<script language="JavaScript" src="<@s.url value="/" />html/js/catalogProd/catalogUpload/pchomeStoreURL.js?t=20181023004"></script>

<div class="container-prodmanage">

    <#-- 次目錄導覽列 開始 -->
    <#-- hidden 隱藏所有牙齒 -->
    <#-- hidetabs 只顯示第一顆牙齒 -->
    <#-- tab1 tab2 tab3 tab4 tab5 牙齒由左至右底線 -->
    <div class="nav-wrap pos-relative tab4">
        <div class="nav-box pos-relative">
            <ul class="txt-table">
                <li class="txt-cell pos-relative p-r10">
                    <span class="icon-box list arrow-right">
                        <a href="catalogProd.html">所有商品目錄</a>
                        <em class="icon-arrow-r"></em>
                    </span>
                    <div class="select-box">
                        <select id="catalogSeq" name="catalogSeq">
                            <#list catalogList as vo>
                        		<option value="${vo.catalogSeq!}" <#if vo.catalogSeq == catalogSeq>selected</#if> >${vo.catalogName!}</option>
                        	</#list>
                        </select>
                    </div>
                </li>
                <li class="txt-cell pos-relative"><a href="prodListCardStyleView.html?catalogSeq=${catalogSeq!}&currentPage=1&pageSizeSelected=10">商品清單</a></li>
                <li class="txt-cell pos-relative"><a href="queryCatalogGroup.html?catalogSeq=${catalogSeq!}">商品組合</a></li>
                <li class="txt-cell pos-relative"><a href="#">商品資料</a></li>
                <li class="txt-cell pos-relative"><a href="setup.html?catalogSeq=${catalogSeq!}">設定</a></li>
            </ul>
            <div class="altername-box pos-absolute pos-right pos-top"><span>帳戶：</span>${customer_info_title}</div>
        </div>
    </div>
    <#-- 次目錄導覽列 結束 -->

	<input type="text" id="uploadContent" name="uploadContent" value="${uploadContent!}">
	<div id="loadingWaitBlock">    
		<#-- 選擇商品資料來源 開始 -->
	    <div class="content-wrap bg-white">
	        <div class="content-box bg-white w-900">
	
	            <#-- 網址建立資料 開始 -->
	            <div class="section-box">
	                <p class="h2 m-b10">商品資料來源</p>
	                <span class="txt-descript">使用PChome 24h購物、PChome商店街、PChome商店街 個人賣場、露天拍賣的網址上傳商品資訊，系統每日AM 2:00自動執行更新一次，請至商品清單查看每日更新結果。</span>
	
	                <#-- select  :填寫檔案URL  -->
	                <#-- success :URL成功  -->
	                <#-- failure :URL錯誤  -->
	                <#-- datatype :資料來源型態  -->
	                <div class="urlupload-wrap select">
	
	                    <#-- 賣場網址-填寫檔案URL -->
	                    <div class="urlupload-box init select">
	                        <div class="txt-cell">
	                            <div class="input-text inputurl">
	                                <input type="text" id="pchomeStoreURL" name="pchomeStoreURL" maxlength="2048" value="" required="" placeholder="請輸入網址">
	                            </div>
	                        </div>
	                    </div>
	
	
	                    <#-- 賣場網址-URL錯誤 -->
	                    <div class="urlupload-box failure">
	                        <div class="txt-cell">
	                            <div class="input-text failure">
	                                <input type="text" id="errPchomeStoreURL" name="errPchomeStoreURL" maxlength="2048" value="https://blog.akanelee.me/2018/08/15/for-rwd-beginner/"
	                                    required="" placeholder="">
	                            </div>
	                            <span class="errortxt txt-center p-t5" id="errContent" ><em class="icon-error"></em>網址內容錯誤</span>
	                        </div>
	
	                    </div>
	
	                    <#-- 賣場網址-URL成功 -->
	                    <div class="urlupload-box transition-all success">
	                        <div class="txt-cell p-l10">
	                            <div class="datainfo-url txt-noselect storebox">
	                                <div class="storelogo-box">
	                                    <img class="logoIcon ruten" src="<@s.url value="/" />/html/img/catalogProd/catalogUpload/ruten-logo.png" style="display: none;"> 
	                                    <img class="logoIcon pcstore" src="<@s.url value="/" />/html/img/catalogProd/catalogUpload/pcstore-logo.png" style="display: none;">
	                                    <img class="logoIcon sellerPcstore" src="<@s.url value="/" />/html/img/catalogProd/catalogUpload/sellerpcstore-logo.png" style="display: none;">
	                                    <img class="logoIcon 24h" src="<@s.url value="/" />/html/img/catalogProd/catalogUpload/24h-logo.png" style="display: none;">
	                                    <img src="<@s.url value="/" />/html/img/catalogProd/catalogUpload/mallpchome-logo.png" style="display: none;">
	                                </div>
	                                <p>
	                                    <span class="txt-ellipsis" id="successContent">網址：
	                                        <em data-fileUrl="https://www.mywebsite/www.mywebsite/www.mywebsite/www.mywebsite.com/www.mywebsite.com/feed/feedJune112917.csv"></em>
	                                    </span>
	                                </p>
	                            </div>
	                        </div>
	                        <div class="txt-cell p-r10">
	                            <u class="icon-delete"></u>
	                        </div>
	                    </div>
	
	                    <#-- 賣場網址-資料來源型態  -->
	                    <div class="urlupload-box datatype">
	                        <div class="txt-cell p-l10">
	                            <div class="datainfo-url txt-noselect storebox">
	                                <div class="storelogo-box">
	                                    <img class="logoIcon ruten" src="<@s.url value="/" />/html/img/catalogProd/catalogUpload/ruten-logo.png" style="display: none;"> 
	                                    <img class="logoIcon pcstore" src="<@s.url value="/" />/html/img/catalogProd/catalogUpload/pcstore-logo.png" style="display: none;">
	                                    <img class="logoIcon sellerPcstore" src="<@s.url value="/" />/html/img/catalogProd/catalogUpload/sellerpcstore-logo.png" style="display: none;">
	                                    <img class="logoIcon 24h" src="<@s.url value="/" />/html/img/catalogProd/catalogUpload/24h-logo.png" style="display: none;">
	                                    <img src="<@s.url value="/" />/html/img/catalogProd/catalogUpload/mallpchome-logo.png" style="display: none;">
	                                </div>
	                                <p>
	                                    <span class="txt-ellipsis" id="dataTypeContent">網址：
	                                        <em data-fileUrl="https://www.mywebsite/www.mywebsite/www.mywebsite/www.mywebsite.com/www.mywebsite.com/feed/feedJune112917.csv"></em>
	                                    </span>
	                                </p>
	                            </div>
	                        </div>
	                        <div class="txt-cell">
	                            <#--按下編輯網址，顯示"賣場網址-填寫檔案URL"區塊-->
	                            <u class="txt-descript txt-center editUrl">編輯網址</u>
	                        </div>
	                    </div>
	
	                </div>
	
	            </div>
	            <#-- 網址建立資料 結束 -->
	
				<div class="datarewrite-wrap">
				    <div class="inputradio-box txt-noselect">
				        <div class="input-radio">
				            <input type="radio" value="1" id="pvLimitSelect1" name="updateWay" checked>
				            <label for="pvLimitSelect1"></label>
				            <span>取代 <u>- 新檔案會完全取代目前的檔案，未在新檔案中的商品將會被刪除。</u></span>
				        </div>
				    </div>
				
				    <div class="inputradio-box txt-noselect">
				        <div class="input-radio">
				            <input type="radio" value="2" id="pvLimitSelect2" name="updateWay">
				            <label for="pvLimitSelect2"></label>
				            <span>更新 <u>- 新檔案會新增或更新現有的商品，不會刪除商品。</u></span>
				        </div>
				    </div>
				</div>
	
	        </div>
	
	        <div class="button-box w-900 txt-center p-tb60">
	            <div class="link-button"><a href="javascript:history.go(-1);">上一步</a></div>
	            <div class="input-button"><input type="button" onclick="pchomeStoreURLFinish();" value="送出審核"></div>
	        </div>
	
	    </div>
	    <#-- 選擇商品資料來源 結束 -->
	</div>
</div>