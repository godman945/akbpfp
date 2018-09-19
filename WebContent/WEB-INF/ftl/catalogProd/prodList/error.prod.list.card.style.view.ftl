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

		<br>
		${returnMsg!}
		<br>
				
            </div>
        </div>
    </div>