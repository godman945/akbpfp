<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<script language="JavaScript" src="<@s.url value="/" />html/js/catalogProd/commonCatalogProd.js?t=20181023011"></script>
<script language="JavaScript" src="<@s.url value="/" />html/js/catalogProd/catalogUpload/manualInput.js?t=20181031005"></script>

<input type="file" serialize id="fileupload" name="fileupload" style="display:none;" accept="image/jpeg, image/png, image/gif" onchange="selectImg(this)">
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

	<div id="loadingWaitBlock">
	    <#-- 選擇商品資料來源 開始 -->
	    <div class="content-wrap bg-white">
	        <div class="content-box bg-white w-900">
	
	            <#-- 手動建立資料 開始 -->
	            <div class="section-box">
	                <p class="h2 m-b10">建立商品資料</p>               
	
	                <div class="manualadd-box">
	
	                    <#--右欄輸入資料 開始-->
	                    <div class="txt-cell manual-inputbox">
	                        <div class="manual-item">
	                            <i class="input-tit">商品名稱</i>
	                            <div class="input-text">
	                               <input type="text" id="ecName" name="ecName" maxlength="20" value="" required="" placeholder="限20字內"> 
	                               <p class="txt-descript" id="ecNameErrMsg"></p>
	                            </div>
	                        </div><#--manual-item end-->
	
	                        <div class="manual-item">
	                            <i class="input-tit">商品網址</i>
	                            <div class="input-text">
	                               <input type="text" id="ecUrl" name="ecUrl" maxlength="2048" value="" required="" placeholder=""> 
	                               <p class="txt-descript" id="ecUrlErrMsg"></p>
	                            </div>
	                        </div><#--manual-item end-->
	
	                        <div class="manual-item">
	                            <i class="input-tit">原價</i>
	                            <div class="input-number">
	                                NT<input type="text" id="ecPrice" name="ecPrice" maxlength="6" value="" onkeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))">
	                                <span>元</span>
	                                <p class="txt-descript" id="ecPriceErrMsg"></p>
	                            </div>
	                        </div><#--manual-item end-->
	
	                        <div class="manual-item">
	                            <i class="input-tit">特價</i>
	                            <div class="input-number">
	                            	<#-- onkeypress 只能輸入數字-->
	                                NT<input type="text" id="ecDiscountPrice" name="ecDiscountPrice" maxlength="6" value="" required onkeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))">
	                                <span>元</span>
	                                <p class="txt-descript" id="ecDiscountPriceErrMsg"></p>
	                            </div>
	                        </div><#--manual-item end-->
	
	                        <div class="manual-item">
	                            <i class="input-tit">供應情況</i>
	                            <div class="select-box">
	                                <select id="ecStockStatus" name="ecStockStatus">
	                                    <option value="1">有庫存</option>
	                                    <option value="0">無庫存</option>
	                                    <option value="2">預購</option>
	                                    <option value="3">停售</option>
	                                </select>
	                            </div>
	                        </div><#--manual-item end-->
	
	                        <div class="manual-item">
	                            <i class="input-tit">使用狀況</i>
	                            <div class="select-box">
	                                <select id="ecUseStatus" name="ecUseStatus">
	                                    <option value="0">全新</option>
	                                    <option value="2">福利品</option>
	                                    <option value="1">二手</option>
	                                </select>
	                            </div>
	                        </div><#--manual-item end-->
	
	                        <div class="manual-item">                            
	                            <i class="input-tit">商品編號</i>                            
	                            <div class="input-text no-invalid">                               
	                               <input type="text" id="catalogProdSeq" name="catalogProdSeq" maxlength="30" value="" required="" placeholder="限30字內">
	                               <p class="txt-descript" id="catalogProdSeqErrMsg"></p>
	                            </div>
	                            <div class="msg-btn" onclick="$(this).children('em').fadeToggle('fast');">
	                                <em style="display: none;">您可以透過ID來辨識每個商品，建議使用商品的SKU做為ID的值。</em>
	                            </div>
	                        </div><#--manual-item end-->
	
	                        <div class="manual-item">                            
	                            <i class="input-tit">商品類別</i>                            
	                            <div class="input-text no-invalid">                               
	                               <input type="text" id="ecCategory" name="ecCategory" maxlength="15" value="" placeholder="限15字內">
	                               <p class="txt-descript" id="ecCategoryErrMsg"></p>
	                            </div> 
	                            <em class="note">非必填</em>                           
	                        </div><#--manual-item end-->
	
	                    </div>
	                    <#--右欄輸入資料 結束   -->
	
	                    <#--左欄上傳圖片 開始-->
	                    <div class="txt-cell manual-upload">
	
	                        <#-- select  :選擇圖片   -->
	                        <#-- success :圖片已上傳 -->
	                        <div class="pdimgupload-box select">
	
	                            <#--左欄上傳圖片:選擇圖片-->
	                            <div class="selectimg">
	                                <div class="txt-cell" id="dropImg" ondrop="drop(event)">
		                                <div class="svg-box icon-uploadpic">
		                                    <object data="<@s.url value="/" />/html/img/catalogProd/catalogUpload/prodpic-upload.svg" type="image/svg+xml"></object>
		                                </div>
	                                	<u>將商品圖拖放到這裡<br>或 <a onclick="openFileLoad(this)">選擇要上傳的檔案</a></u>                  
	                                </div>
	                            </div>
	
	                            <#--左欄上傳圖片:圖片已上傳-->
	                            <div class="imguploadbox success">                                  
	                                <div class="txt-cell delbtn" onclick="deleteImg()">
	                                    <u class="svg-box icon-delete">
	                                        <object data="<@s.url value="/" />/html/img/catalogProd/catalogUpload/deleteimg.svg" type="image/svg+xml"></object>
	                                    </u>
	                                </div>
	                                <div class="txt-cell">
	                                    <img id="successImg" src="">
	                                    <#-- https://adpic.pchome.com.tw/adpics/pic_1186426_688580.jpg -->
	                                </div>
	                            </div>
	                        </div>
	                    </div>
	                    <#-- 左欄上傳圖片 結束-->
	                                  
	                </div>                 
	                
	                <div class="button-box w-900 txt-left p-b30 p-t10">                        
	                    <div class="input-button manual"><input type="button" id="addProd" value="新增商品"></div>
	                </div>
	            </div>
	            <#-- 手動建立資料 結束 -->
	
	            <#--商品列表 開始-->
	            <div class="manualprod-box p-t30">
	                <p class="h2">建立 <span data-value="0"></span> 筆商品</p>
	
	                <#--<div class="prodlist">
	                    <div class="txt-cell prod-pic">
	                        <img src="https://b.ecimg.tw/items/DGBQ3VA9008VXQO/000001_1535968931.jpg">
	                    </div>
	                    <div class="txt-cell prod-detail">
	                        <h5 class="h2 txt-ellipsis" data-pdName="超值搶購↘9折 de第一化粧品 玻尿酸保濕化粧水500ML 玻尿酸保濕化粧水500ML 玻尿酸保濕化粧水500ML"></h5>
	                        <small class="prodNum">編號<em data-prodnum="21702069230204"></em></small>
	                        <small class="prod-opt">
	                            <i>原價NT$ <em class="price" data-info="13,999"></em></i>
	                            <i>特價NT$ <em class="price" data-info="999"></em></i>
	                            <i><em data-info="有庫存"></em></i>
	                            <i><em data-info="全新"></em></i>
	                            <i><em data-info="臉部身體保養"></em></i>
	                        </small>
	                        <small class="prodLink txt-ellipsis" data-prodlink="https://b.ecimg.tw//b.ecimg.tw//b.ecimg.tw/items/DGBQ3VA9008VXQO/000001_1535968931/DGBQ3VA9008VXQO/000001_1535968931.jpg"></small>
	                    </div>
	                    <div class="txt-cell p-l10">
	                        <u class="icon-delete"></u>
	                    </div>
	                </div><#--prodlist END-->
	
	                <#--<div class="prodlist">
	                    <div class="txt-cell prod-pic">
	                        <img src="http://img.pcstore.com.tw/pch_ad/00/49/16/00491628.jpg">
	                    </div>
	                    <div class="txt-cell prod-detail">
	                        <h5 class="h2 txt-ellipsis" data-pdName="【HEAD 海德】專業瑜珈墊/運動墊12mm"></h5>
	                        <small class="prodNum">編號<em data-prodnum="21702069230204"></em></small>
	                        <small class="prod-opt">
	                            <i class="hide">原價NT$ <em class="price" data-info="13,999"></em></i>
	                            <i>特價NT$ <em class="price" data-info="999"></em></i>
	                            <i><em data-info="有庫存"></em></i>
	                            <i class="hide"><em data-info=""></em></i>
	                            <i><em data-info="臉部身體保養"></em></i>
	                        </small>
	                        <small class="prodLink txt-ellipsis" data-prodlink="https://b.ecimg.tw//b.ecimg.tw//b.ecimg.tw/items/DGBQ3VA9008VXQO/000001_1535968931/DGBQ3VA9008VXQO/000001_1535968931.jpg"></small>
	                    </div>
	                    <div class="txt-cell p-l10">
	                        <u class="icon-delete"></u>
	                    </div>
	                </div><#--prodlist END-->
	
	                <#--<div class="prodlist">
	                    <div class="txt-cell prod-pic">
	                        <img src="https://f.ecimg.tw/items/DICMI5A9008HH2X/000001_1508486283.jpg">
	                    </div>
	                    <div class="txt-cell prod-detail">
	                        <h5 class="h2 txt-ellipsis" data-pdName="13吋 簡約纖薄 信封式避震袋 內袋 (DH192) 灰色"></h5>
	                        <small class="prodNum">編號<em data-prodnum="21702069230204"></em></small>
	                        <small class="prod-opt">
	                            <i>原價NT$ <em class="price" data-info="13,999"></em></i>
	                            <i>特價NT$ <em class="price" data-info="999"></em></i>
	                            <i><em data-info="有庫存"></em></i>
	                            <i><em data-info="全新"></em></i>
	                            <i><em data-info="筆電內袋/殼"></em></i>
	                        </small>
	                        <small class="prodLink txt-ellipsis" data-prodlink="https://24h.pchome.com.tw/prod/DICMI5-A9008HH2X"></small>
	                    </div>
	                    <div class="txt-cell p-l10">
	                        <u class="icon-delete"></u>
	                    </div>
	                </div><#--prodlist END-->     
	             </div>
	            <#--商品列表 結束-->
	
	        </div>
	
	        <div class="button-box w-900 txt-center p-tb60">
	            <div class="link-button"><a href="javascript:history.go(-1);">上一步</a></div>
	            <div class="input-button"><input type="button" onclick="manualInputFinish();" value="送出審核"></div>
	        </div>
	
	    </div>
	    <#-- 選擇商品資料來源 結束 -->
	</div>

</div>