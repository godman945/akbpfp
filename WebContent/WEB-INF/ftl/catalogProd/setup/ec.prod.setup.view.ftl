<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<div class="container-prodmanage">
	<@t.insertAttribute name="prodlistMenu" />
	<@t.insertAttribute name="include.js" />
  		<!-- 選擇商品資料來源 開始 -->
        <div class="content-wrap bg-white">
            <div class="content-box bg-white w-900">
                <!-- 上傳方式 開始 -->
                <div id="dataupload" class="section-box">
                    <p class="title-box h2 txt-left">商品圖片展示設定</p>
                    <div class="dataupload-wrap txt-noselect">
                        <div class="displayimg-box selected" onclick="$('#dataupload .displayimg-box').removeClass('selected');$(this).addClass('selected')">
                            <div class="displayimg-iconbox">
                               <img src="./html/img/prodimgdisplay-default.png"> 
                            </div>
                            <p>圖片自動填滿 (預設)<span>圖片部分區域可能受到裁切</span></p>
                        </div>
                    </div>
                    <div class="dataupload-wrap txt-noselect">
                        <div class="displayimg-box" onclick="$('#dataupload .displayimg-box').removeClass('selected');$(this).addClass('selected')">
                            <div class="displayimg-iconbox">
                               <img src="./html/img/prodimgdisplay.png"> 
                            </div>
                            <p>圖片完全套入<span>商品展示區塊中部分區域可能留白</span></p>
                        </div>
                    </div>
                </div>
                <!-- 上傳方式 結束 -->
            </div>
            <div class="button-box w-900 txt-center p-tb60">                
                <div class="input-button"><input type="button" value="儲存" onclick="submitSetup();"></div>
            </div>
        </div>
</div>

<input id="catalogSeq" type="hidden" value=${catalogSeq!}>
<input id="cropType" type="hidden" value=${cropType!}>
