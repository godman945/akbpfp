<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
    <div class="container-prodmanage">


        <!-- 次目錄導覽列 開始 -->
        <!-- hidden 隱藏所有牙齒 -->
        <!-- hidetabs 只顯示第一顆牙齒 -->
        <!-- tab1 tab2 tab3 tab4 tab5 牙齒由左至右底線 -->
        <div class="nav-wrap pos-relative hidetabs tab6">
            <div class="nav-box pos-relative">
                <ul class="txt-table">
                    <li class="txt-cell pos-relative p-r10">
                        <span class="icon-box list arrow-right">
                            <a href="catalogProd.html">所有商品目錄</a>
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
                    <li class="txt-cell pos-relative p-r10">
                        <span class="icon-box tab-logomanage p-r10">
                            <a href="#">LOGO管理<em class="icon-error"></em></a>                                                        
                        </span>
                    </li>
                    <div class="p-b10 note-text">提醒您，必須完整建立商品目錄與LOGO才能投放商品廣告</div>
                </ul>
				<div class="altername-box pos-absolute pos-right pos-top"><span>帳戶：</span>${customer_info_title}</div>
            </div>
        </div>
        <!-- 次目錄導覽列 結束 -->


        <!-- 建立商品目錄 開始 -->
        <div class="content-wrap">
            <p class="title-box h1 txt-center logomanagetit">
               <br>
               
            </p>
            
            <div class="content-box bg-white w-900 txt-table">

                <!--正方形LOGO 開始-->
                    <!--data-tyep:select   =選擇logo-->
                    <!--data-tyep:review   =logo審核中-->
                    <!--data-tyep:success  =logo審核成功-->
                <div class="txt-cell logomanage-wrap" data-type="review">
                    <span class="h2">正方形LOGO</span>

                    <!--選擇logo-->
                    <div class="logoupload-box select" style="display:block;">
                        <div class="txt-cell logobox">
                            <div class="txt-cell squarelogo-box selectimg" ondrop="drop(event,this)">
                                <u>將商品圖拖放到這裡<br>或 <a onclick="openFileLoad(this)">選擇要上傳的檔案</a></u>
                            </div>
                        </div>
                        <div class="notebox">
                            <span class="txt-descript txt-center">請上傳 圖片解析度250x250、180kb 以內<br>jpg或png格式的正方形圖片</span>
                            <br>
                            <div id="errMsg" style="color:red;"></div>
                        </div>
                    </div>

                    <!--logo審核中-->
                    <div class="logoupload-box review" style="display:none;">
                        <div class="txt-cell logobox squarelogo">
                            <div class="txt-cell squarelogo-box " ondrop="drop(event,this)">
                                <div class="delbtn ali-middle">
                                <div class="logoreupload-box" onclick="openFileLoad(this)">
                                    <div class="svg-box icon-logoreupload">
                                        <object data="html/img/icon-logoreupload.svg" type="image/svg+xml"></object>
                                    </div>
                                    <i class="text">重新上傳</i>
                                </div>
                                </div>
                                <div class="logomanage-imgbox ali-middle">
                                    <img src="">
                                </div>
                            </div>
                        </div>
                        <div class="notebox">審核中</div>
                    </div>
                    
                    

                    <!--logo審核成功-->
                    <div class="logoupload-box success" style="display:none;">
                        <div class="txt-cell logobox squarelogo">
                            <div class="txt-cell squarelogo-box " ondrop="drop(event,this)">
                                <div class="delbtn ali-middle">
                                <div class="logoreupload-box" onclick="openFileLoad(this)">
                                    <div class="svg-box icon-logoreupload">
                                        <object data="html/img/icon-logoreupload.svg" type="image/svg+xml"></object>
                                    </div>
                                    <i class="text">重新上傳</i>
                                </div>
                                </div>
                                <div class="logomanage-imgbox ali-middle">
                                    <img src="">
                                </div>
                            </div>
                        </div>
                        <div class="notebox">審核成功</div>
                    </div>
                </div>
                <!--正方形LOGO 結束-->

                

                <!--長方形LOGO 開始-->
                    <!--data-tyep:select   =選擇logo-->
                    <!--data-tyep:review   =logo審核中-->
                    <!--data-tyep:success  =logo審核成功-->
                <div class="txt-cell logomanage-wrap rightwrap" data-type="success">
                    <span class="h2">長方形LOGO</span>

                    <!--選擇logo-->
                    <div class="logoupload-box select" style="display:block;">                        
                        <div class="txt-cell logobox">                        
                            <div class="txt-cell rectanglelogo-box selectimg" ondrop="drop(event,this)">  
                                <u>將商品圖拖放到這裡<br>或 <a onclick="openFileLoad(this)">選擇要上傳的檔案</a></u>
                            </div>
                        </div>
                        <div class="notebox">
                            <span class="txt-descript txt-center">請上傳 圖片解析度1000x250、180kb 以內<br>jpg或png格式的長方形圖片</span>
                            <br>
                            <div id="errMsg2" style="color:red;"></div>
                        </div>
                    </div>


                    <!--logo審核中-->
                    <div class="logoupload-box review" style="display:none;">
                        <div class="txt-cell logobox rectanglelogo">
                            <div class="txt-cell rectanglelogo-box" ondrop="drop(event,this)">
                                <div class="delbtn ali-middle">
                                <div class="logoreupload-box" onclick="openFileLoad(this)">
                                    <div class="svg-box icon-logoreupload">
                                        <object data="html/img/icon-logoreupload.svg" type="image/svg+xml"></object>
                                    </div>
                                    <i class="text">重新上傳</i>
                                </div>
                                </div>
                                <div class="logomanage-imgbox ali-middle">
                                    <img src="">
                                </div>
                            </div>
                        </div>
                        <div class="notebox">審核中</div>
                    </div>

                    <!--logo審核成功-->
                    <div class="logoupload-box success" style="display:none;">
                        <div class="txt-cell logobox rectanglelogo">
                            <div class="txt-cell rectanglelogo-box" ondrop="drop(event,this)">
                                <div class="delbtn ali-middle">
                                <div class="logoreupload-box" onclick="openFileLoad(this)">
                                    <div class="svg-box icon-logoreupload">
                                        <object data="html/img/icon-logoreupload.svg" type="image/svg+xml"></object>
                                    </div>
                                    <i class="text">重新上傳</i>
                                </div>
                                </div>
                                <div class="logomanage-imgbox ali-middle">
                                    <img src="">
                                </div>
                            </div>
                        </div>
                        <div class="notebox">審核成功</div>
                    </div>
                   </div>
                </div>                
                <!--長方形LOGO 結束-->
            </div>

            <div class="button-box w-900 txt-center p-tb30">
                <div class="link-button"><a href="catalogProd.html">取消</a></div>
                <div class="input-button"><input type="button" id="submitBtn" value="送出審核" onclick="saveLogo()"></div>
            </div>

        </div>
        <!-- 建立商品目錄 結束 -->


    </div>
<input type="file" serialize id="fileupload" name="fileupload"  style="display:none;">
<textarea style="display:none;" id="saveLogoImg">${logoDataObj!}</textarea>





<div id="shownotes1" style="visibility:hidden ;" class="adnoticepop">
	<h4>每日預算設定</h4>
	<div class="adpopcont">每日廣告的實際花費，會依搜尋量的變化，與每日設定的廣告預算有小差異。</div>
	<a onclick="closenots(1)" style="cursor:pointer;" class="adpopclose">關閉</a>
</div>
