<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
			<!-- IE 沒有 placeholder 效果，用此 code 模擬 placeholder(Jack指導版) --> 
			<!--[if IE]>
			<script language="JavaScript" src="<@s.url value="/" />html/js/ad/simuPlaceholderTmg.js" ></script>
			<![endif]-->
			<!-- IE 沒有 placeholder 效果，用此 code 模擬 placeholder(Jack指導版) --> 
  			<div style="clear:both;height:0px"></div>
			<h4>製作廣告</h4>
			<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
                <tbody>
                <tr>
                        <th height="35" style="width:12%;"><a name="errAdLinkURL"></a><span class="t_s02">* </span>影片<br><a href="#" target="" onclick="approveSize('approveSizeDiv');">支援規格查詢</a></th>
                        <td style="min-width:200px;">
                            <input type="hidden" id="adDetailID" name="adDetailID" value="real_url">
                            <input type="hidden" id="adDetailName" name="adDetailName" value="影片">
                            <input type="hidden" id="adDetailContent" name="adDetailContent">
                            <input type="text" class="inputPlaceholderTmg" id="adVideoURL" name="adVideoURL" style="width:96%;" placeholder="請輸入YouTube影片網址" maxlength=""><br>
                            <span id="adVideoURLMsg" name="adVideoURLMsg" style="color: rgb(255, 0, 0);"></span><span style="float:right;margin-right: 3%" id="adVideoUrlWordController">已輸入0字，剩<div id="checkWord" style="display:inline;">1024</div>字</span>
						  <td style="" rowspan="5" width="50%">
                            <!--播放預覽start-->

                            <div style="display:block;" class="boxpreview">
                                <h5>廣告預覽 / 曝光示意圖</h5>
                            <!--<center><a href="3">參考其他人撰寫關鍵字廣告</a></center>-->
                                <div class="swiper-container swiper-container-horizontal">
	                                <div class="swiper-button-next swiper-button-rtl "></div>
	                                <div class="swiper-button-prev swiper-button-rtl swiper-button-disabled"></div>

	                                <!--電腦版 - 300x250 start-->
	                                <div style="display: none;">
	                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">電腦版 - 300x250 - 樣式A - 無背景
	                                </div>
	                                    <div class="video_adpreviewbg">
	                                        <div class="video_boxpreview">
	                                        <div class="pc300250_preview">
	                                        <div class="video_box">
	                                        <div class="iframe_style_pc300250">
	                                        <iframe width="300" height="168" src="" frameborder="0" allowfullscreen></iframe>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                    </div>
	                                </div>
	
	                                <div style="display: none;">
	                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">電腦版 - 300x250 - 樣式B - 星空背景
	                                </div>
	                                    <div class="video_adpreviewbg">
	                                        <div class="video_boxpreview">
	                                        <div class="pc300250_preview videobg_on">
	                                        <div class="video_box">
	                                        <div class="iframe_style_pc300250">
	                                        <iframe width="300" height="168" src="" frameborder="0" allowfullscreen></iframe>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                    </div>
	                                </div>
	
	
	                                <!--電腦版 - 300x600 start-->
	                                <div style="display: none;">
	                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">電腦版 - 300x600 - 樣式A - 無背景
	                                </div>
	                                    <div class="video_adpreviewbg">
	                                        <div class="video_boxpreview">
	                                        <div class="pc300600_preview pc300600_0img">
	                                         <div class="video_box">
	                                         <div class="iframe_style_pc300600">
	                                         <iframe width="300" height="168" src="" frameborder="0" allowfullscreen></iframe>
	                                         </div>
	                                         </div>
	                                        </div>
	                                        </div>
	                                    </div>
	                                </div>
	
	
	                                <div style="display: none;">
	                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">電腦版 - 300x600 - 樣式B - 星空背景
	                                </div>
	                                    <div class="video_adpreviewbg">
	                                        <div class="video_boxpreview">
	                                        <div class="pc300600_preview pc300600_0img videobg_on">
	                                         <div class="video_box">
	                                         <div class="iframe_style_pc300600">
	                                         <iframe width="300" height="168" src="" frameborder="0" allowfullscreen></iframe>
	                                         </div>
	                                         </div>
	                                        </div>
	                                        </div>
	                                    </div>
	                                </div>
	
	
	                                <div style="display: none;">
	                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">電腦版 - 300x600 - 樣式C - 1圖(300250)無背景
	                                </div>
	                                    <div class="video_adpreviewbg">
	                                        <div class="video_boxpreview">
	                                        <div class="pc300600_preview pc300600_1img">
	                                         <div class="video_box">
	                                         <div class="iframe_style_pc300600">
	                                         <iframe width="300" height="168" src="" frameborder="0" allowfullscreen></iframe>
	                                         </div>
	                                         <img border="0" src="img/v_300600_300250.gif">
	                                         </div>
	                                        </div>
	                                        </div>
	                                    </div>
	                                </div>
	
	                                <div style="display: none;">
	                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">電腦版 - 300x600 - 樣式D - 1圖(300250)星空背景
	                                </div>
	                                    <div class="video_adpreviewbg">
	                                        <div class="video_boxpreview">
	                                        <div class="pc300600_preview pc300600_1img videobg_on">
	                                         <div class="video_box">
	                                         <div class="iframe_style_pc300600">
	                                         <iframe width="300" height="168" src="" frameborder="0" allowfullscreen></iframe>
	                                         </div>
	                                         <img border="0" src="img/v_300600_300250.gif">
	                                         </div>
	                                        </div>
	                                        </div>
	                                    </div>
	                                </div>
	
	                                <div style="display: none;">
	                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">電腦版 - 300x600 - 樣式E - 1圖(滿300600)
	                                </div>
	                                    <div class="video_adpreviewbg">
	                                        <div class="video_boxpreview">
	                                        <div class="pc300600_preview pc300600img">
	                                         <div class="video_box">
	                                         <div class="iframe_style_pc300600_fullimg">
	                                         <iframe width="300" height="168" src="" frameborder="0" allowfullscreen></iframe>
	                                         </div>
	                                         <img border="0" src="img/v_300600.gif">
	                                         </div>
	                                        </div>
	                                        </div>
	                                    </div>
	                                </div>
	
	
	                                <!--電腦版 - 336x280 start-->
	                                <div style="display: none;">
	                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">電腦版 - 336x280 - 樣式A - 無背景
	                                </div>
	                                    <div class="video_adpreviewbg">
	                                        <div class="video_boxpreview">
	                                        <div class="pc336280_preview">
	                                        <div class="video_box">
	                                        <div class="iframe_style_pc300250">
	                                        <iframe width="300" height="168" src="" frameborder="0" allowfullscreen></iframe>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                    </div>
	                                </div>
	
	                                <div style="display: none;">
	                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">電腦版 - 336x280 - 樣式B - 星空背景
	                                </div>
	                                    <div class="video_adpreviewbg">
	                                        <div class="video_boxpreview">
	                                        <div class="pc336280_preview videobg_on">
	                                        <div class="video_box">
	                                        <div class="iframe_style_pc300250">
	                                        <iframe width="300" height="168" src="" frameborder="0" allowfullscreen></iframe>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                    </div>
	                                </div>
	
	
	                                <!--電腦版 - 970x250 start-->
	                                <div style="display: none;">
	                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">電腦版 - 970x250 - 樣式A - 無背景
	                                </div>
	                                    <div class="video_adpreviewbg">
	                                        <div class="video_boxpreview">
	                                        <div class="pc970250_preview">
	                                        <div class="video_box">
	                                        <div class="iframe_style_pc970250">
	                                        <iframe width="300" height="168" src="" frameborder="0" allowfullscreen></iframe>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                    </div>
	                                </div>
	
	                                <div style="display: none;">
	                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">電腦版 - 970x250 - 樣式B - 星空背景
	                                </div>
	                                    <div class="video_adpreviewbg">
	                                        <div class="video_boxpreview">
	                                        <div class="pc970250_preview videobg_on">
	                                        <div class="video_box">
	                                        <div class="iframe_style_pc970250">
	                                        <iframe width="300" height="168" src="" frameborder="0" allowfullscreen></iframe>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                    </div>
	                                </div>
	
	                                <div style="display: none;">
	                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">電腦版 - 970x250 - 樣式C - 1圖(滿970250)
	                                </div>
	                                    <div class="video_adpreviewbg">
	                                        <div class="video_boxpreview">
	                                        <div class="pc970250_preview">
	                                        <div class="video_box">
	                                        <div class="iframe_style_pc970250_fullimg">
	                                         <iframe width="300" height="168" src="" frameborder="0" allowfullscreen></iframe>
	                                         </div>
	                                         <img border="0" src="./html/img/v_970250.gif">
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                    </div>
	
	                                <div style="display: none;">
	                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">電腦版 - 970x250 - 樣式D - 1圖(300250)無背景
	                                </div>
	                                    <div class="video_adpreviewbg">
	                                        <div class="video_boxpreview">
	                                        <div class="pc970250_preview pc970250_1img">
	                                        <div class="video_box">
	                                        <div class="iframe_style_pc970250_1img">
	                                         <iframe width="300" height="168" src="" frameborder="0" allowfullscreen></iframe>
	                                         </div>
	                                         <img border="0" src="img/v_300600_300250.gif">
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                    </div>
	
	                                <div>
	                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">電腦版 - 970x250 - 樣式D - 1圖(300250)星空背景
	                                </div>
	                                    <div class="video_adpreviewbg">
	                                        <div class="video_boxpreview">
	                                        <div class="pc970250_preview pc970250_1img videobg_on">
	                                        <div class="video_box">
	                                        <div class="iframe_style_pc970250_1img">
	                                         <iframe width="300" height="168" id="bessie" src="" frameborder="0" allowfullscreen></iframe>
	                                         </div>
	                                         <img border="0" src="" style="display: none;">
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                    </div>
	                                <!--手機版 - 300x250 start-->
	                                <div style="display: none;">
	                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">手機版 - 300x250 - 樣式A - 無背景
	                                </div>
	                                    <div class="video_adpreviewbg">
	                                        <div class="video_boxpreview">
	                                        <div class="mobil_preview ">
	                                        <div class="video_box m_mobil_bg">
	                                        <div class="iframe_style_m300250 ">
	                                        <iframe width="300" height="168" src="" frameborder="0" allowfullscreen></iframe>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                    </div>
	
	                                <div style="display: none;">
	                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">手機版 - 300x250 - 樣式B - 星空背景
	                                </div>
	                                    <div class="video_adpreviewbg">
	                                        <div class="video_boxpreview">
	                                        <div class="mobil_preview">
	                                        <div class="video_box m_mobil_bg">
	                                        <div class="iframe_style_m300250 m_videobg_on">
	                                        <iframe width="300" height="168" src="" frameborder="0" allowfullscreen></iframe>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                    </div>
	
	                                <!--手機版 - 320x480 start-->
	                                <div style="display: none;">
	                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">手機版 - 320x480 - 樣式A - 無背景
	                                </div>
	                                    <div class="video_adpreviewbg">
	                                        <div class="video_boxpreview">
	                                        <div class="mobil_preview ">
	                                        <div class="video_box m_mobil_bg">
	                                        <div class="iframe_style_m320480">
	                                        <iframe width="300" height="168" src="" frameborder="0" allowfullscreen></iframe>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                    </div>
	
	                                <div style="display: none;">
	                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">手機版 - 320x480 - 樣式B - 星空背景
	                                </div>
	                                    <div class="video_adpreviewbg">
	                                        <div class="video_boxpreview">
	                                        <div class="mobil_preview">
	                                        <div class="video_box m_mobil_bg">
	                                        <div class="iframe_style_m320480 m_videobg_on">
	                                        <iframe width="300" height="168" src="" frameborder="0" allowfullscreen></iframe>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                    </div>
	
	                                <div style="display: none;">
	                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">手機版 - 320x480 - 樣式C - 1圖(300250) 無背景
	                                </div>
	                                    <div class="video_adpreviewbg">
	                                        <div class="video_boxpreview">
	                                        <div class="mobil_preview">
	                                        <div class="video_box m_mobil_bg">
	                                         <div class="m320480_1img_style">
	                                        <div class="m320480_1img"><img src="img/v_300600_300250.gif"></div>
	                                        <div class="iframe_style_m320480_1img">
	                                        <iframe width="300" height="168" src="" frameborder="0" allowfullscreen></iframe>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                    </div>
	
	                                <div style="display: none;">
	                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">手機版 - 320x480 - 樣式C - 1圖(300250) 無背景
	                                </div>
	                                    <div class="video_adpreviewbg">
	                                        <div class="video_boxpreview">
	                                        <div class="mobil_preview">
	                                        <div class="video_box m_mobil_bg">
	                                         <div class="m320480_1img_style">
	                                        <div class="m320480_1img"><img src="img/v_300600_300250.gif"></div>
	                                        <div class="iframe_style_m320480_1img m_videobg_on">
	                                        <iframe width="300" height="168" src="" frameborder="0" allowfullscreen></iframe>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                    </div>
	
	                                <div style="display: none;">
	                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">手機版 - 320x480 - 樣式D - 1圖(滿320480)
	                                </div>
	                                    <div class="video_adpreviewbg">
	                                        <div class="video_boxpreview">
	                                        <div class="mobil_preview">
	                                        <div class="video_box m_mobil_bg">
	                                        <div class="iframe_style_m320480_fullimg">
	                                        <iframe width="300" height="168" src="" frameborder="0" allowfullscreen></iframe>
	                                        <div class="m320480_fullimg"><img src="img/v_m320480.gif"></div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                    </div>
	
	                                <!--手機版 - 336x280 (與300250同) start-->
	                                <div style="display: none;">
	                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">手機版 - 300x250 - 樣式A - 無背景
	                                </div>
	                                    <div class="video_adpreviewbg">
	                                        <div class="video_boxpreview">
	                                        <div class="mobil_preview ">
	                                        <div class="video_box m_mobil_bg">
	                                        <div class="iframe_style_m300250">
	                                        <iframe width="300" height="168" src="" frameborder="0" allowfullscreen></iframe>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                </div>
	
	                                <div style="display: none;">
	                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">手機版 - 300x250 - 樣式B - 星空背景
	                                </div>
	                                    <div class="video_adpreviewbg">
	                                        <div class="video_boxpreview">
	                                        <div class="mobil_preview">
	                                        <div class="video_box m_mobil_bg">
	                                        <div class="iframe_style_m300250 m_videobg_on">
	                                        <iframe width="300" height="168" src="" frameborder="0" allowfullscreen></iframe>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                        </div>
	                                </div>
                                <!--廣告預覽end-->
                        </td>



                    </tr>
                        <tr>
                        <th height="35" style="width:12%;"><a name="errAdLinkURL"></a><span class="t_s02">* </span>影片到達網址</th>
                        <td style="min-width:200px;">
                            <input type="hidden" id="adDetailID" name="adDetailID" value="real_url">
                            <input type="hidden" id="adDetailName" name="adDetailName" value="影片到達網址">
                            <input type="hidden" id="adDetailContent" name="adDetailContent">
                            <input type="text" class="inputPlaceholderTmg" id="adLinkURL" name="adLinkURL" style="width:96%;" placeholder="請輸入影片到達網址" maxlength=""><br>
                            <span id="chkLinkURL" name="chkLinkURL" style="color:red"></span><span style="float:right;clear: both;;margin-right: 3%" id="spanAdLinkURL">已輸入0字，剩1024字</span>
                        </td>
                    </tr>
                    <tr>
                        <th height="35" style="width:12%"><a name="errAdImg"></a><span class="t_s02">* </span>Banner廣告圖<br><a href="#" target="" onclick="approveSize('bannerDiv');">支援規格查詢 </a></th><td style="min-width:200px;">
                            <span class="imgar" style="width:100%;">
                                <!--上傳圖片start-->
									<span style="color:red;float:left;text-align:left;">請提供300x250、300x600、320x480、970x250，四種尺寸的廣告圖各一張。</span><br>
	                              	<span style="float:left;text-align:left;margin-bottom: 5px">若位提供，個影音廣告尺寸版行會自動帶入系統預設的廣告圖。</span>
	                               	<input type="button" style="display:block; clear: both " id="fileButton" name="fileButton" value="瀏覽檔案" onclick="fileLoad()">
	                               	<div id="errMsg" style="text-align:-webkit-left;"></div>
	                               	<input type="file" serialize id="fileupload" name="fileupload"  style="display:none ;" multiple="" >
                              </span>
                        </td>
                    </tr>
                </tbody>
            </table>
			<div id="notSuppotDiv" style="display:none;">
                	您的瀏覽器不支援唷! 請更換IE10以上或其它瀏覽器
                <img src="http://show.pchome.com.tw/html/main/img/tt_pp2.png" class="tt_pp">
            </div>

			<!--上傳訊息start 上傳後顯示-->
			<div class="aduplodulbg">
				<ul id="AG" class="aduplodul" style="width: 96%; padding-left: 25px;">
			       <!--[if !IE 9]> -->
	                <!--
		                <li class="okbox" id=""><div class="adboxdv"><img id="imghead_1" src="<@s.url value="/" />html/img/upl9090.gif"></div><ul style="padding-top: 5px"><li class=""><b style="font-size: 18px;text-align:center;margin-top: 30px;">300 x 250</b></li></ul></li>
		                <li class="okbox" id=""><div class="adboxdv"><img id="imghead_2" src="<@s.url value="/" />html/img/upl9090.gif"></div><ul style="padding-top: 5px"><li class=""><b style="font-size: 18px;text-align:center;margin-top: 30px;">300 x 600</b></li></ul></li>
		                <li class="okbox" id=""><div class="adboxdv"><img id="imghead_3" src="<@s.url value="/" />html/img/upl9090.gif"></div><ul style="padding-top: 5px"><li class=""><b style="font-size: 18px;text-align:center;margin-top: 30px;">320 x 480</b></li></ul></li>
		                <li class="okbox" id=""><div class="adboxdv"><img id="imghead_4" src="<@s.url value="/" />html/img/upl9090.gif"></div><ul style="padding-top: 5px"><li class=""><b style="font-size: 18px;text-align:center;margin-top: 30px;">970 x 250</b></li></ul></li>
	                -->
            	<![endif]-->
            	<!--[if IE 9]>
            		<img id="" style="" src="http://static.ettoday.net/images/1744/1744834.jpg">
            	<![endif]-->
			                </ul>
			            </div>
			<!--上傳訊息end-->
			 <span class="t_s01">提醒您，於網路中張貼或散佈色情圖片是觸法行為，違者可處二年以下有期徒刑、拘役或併科三萬元以下罰金</span>
        	</div>


<div id="approveSizeDiv" style="display:none;">
	<div class="noticepop" style="width:auto;"><h4>影音廣告支援規格查詢</h4><div>
	    <table width="97%" cellspacing="1" cellpadding="0" border="0" class="tb02" style="margin:10px auto;line-height:18px;">
	        <tbody>
	            <tr>
	                <th height="20">影音格式</th>
	                <td>
	                 目前僅支援</td>
	            </tr>
	            <tr>
	                <th height="20">影片長度</th>
	                <td>
	                 最長30秒</td>
	            </tr>
	            <tr>
	              <th height="20">尺寸</th>
	              <td>
	               <div id="adSizeDiv" style="height:200px;overflow:auto;">
	                 <div>
	                    <div style="color:#ff3300;width:230px;float:left;"><b>影片會自動帶入以下廣告尺寸播放</b></div>
	                    <div style="width:110px;float:left;">
	                            <p>300 x 250(電腦)</p>
	                            <p>300 x 600(電腦)</p>
	                            <p>336 x 280(電腦)</p>
	                            <p>970 x 250(電腦)</p>
	                    </div>
	                    <div style="width:120px;float:left;">
	                    		<p>300 x 250(行動裝置)</p>
	                    		<p>320 x 480(行動裝置)</p>
	                    		<p>336 x 280(行動裝置)</p>
	                    </div>
	                 </div>
	                 <div></div>
	               </div>
	              </td>
	            </tr>
	        </tbody>
	    </table>
	    <center><input onclick="closeBtn();" class="popbtn" type="button" value="關閉"></center>
	    </div>
	    <a href="#" onclick="closeBtn();" class="popclose">關閉</a>
	   </div>
</div>

<div  style="display:none;">
	<div id="bannerDiv" style="width:460px;height:auto;overflow: hidden;position:relative;">
	    <div class="noticepop" style="width:auto;"><h4>Banner廣告圖支援規格查詢</h4><div>
	    <table width="90%" cellspacing="1" cellpadding="0" border="0" class="tb02" style="margin:10px auto;line-height:18px;">
	        <tbody>
	            <tr>
	                <th height="20">圖片格式</th>
	                <td>JPG、PNG、GIF檔</td>
	            </tr>
	            <tr>
	                <th height="20">影片長度</th>
	                <td>
	                 最長30秒</td>
	            </tr>
	            <tr>
	              <th height="20">圖片大小</th>
	              <td>
	               <div id="adSizeDiv" style="height:200px;overflow:auto;">
	                 <div>
	                   
	                    <div style="width:110px;float:left;">
	                            <p>300 x 250</p>
	                            <p>300 x 600</p>
	                            <p>320 x 480</p>
	                            <p>970 x 250</p>
	                    </div>
	                    <div style="width:120px;float:left;">
	                    </div>
	                 </div>
	                 <div></div>
	               </div>
	              </td>
	            </tr>
	        </tbody>
	    </table>
	    <center><input onclick="closeBtn();" class="popbtn" type="button" value="關閉"></center>
	    </div>
	    <a href="#" onclick="closeBtn();" class="popclose">關閉</a>
	   </div>
	</div>
</div>

<!-- 預覽畫面 -->
<div style="display:none;width:70%;"  id="preViewImgDiv" >
	<div style="margin:0px auto;text-align:center; background:url(03.jpg) no-repeat;height:1617px;background-position:center top;"> 
		<div id="preDiv" name="preDiv"></div>
	</div>
</div>

<input type="hidden" id="adGroupSeq" value="${adGroupSeq!}">
<input type="hidden" id="adOperatingRule" value="${adOperatingRule!}">
<input type="hidden" id="adStyle" value="${adStyle!}">
<input type="hidden" id="adClass" value="1">




























<style>
	/*video::-webkit-media-controls{display:none;}*/
	body, html{margin:0;padding:0;}
	.home-banner{display:block;cursor:pointer;}
	.video-wrapper{position:relative;display:table;overflow:hidden;background-color:#000;}
	.video-playbtn{position:absolute;top:50%;left:50%;margin-left:-49px;margin-top:-35px;z-index:1002;width:78px;height:50px;padding:20px;cursor:pointer;}
	.video-soundbtn{position:absolute;bottom:0.3em;right:0.3em;z-index:1000;cursor:pointer;}
	.video-countdown{position:absolute;bottom:0.3em;right:2.0em;z-index:1000;display:block;height:22px;line-height:22px;color:#fff;text-align:right;letter-spacing:1px;font-size:12px;font-family:"微軟正黑體";text-shadow: 2px 2px #000;}
	.video-adlinkbtn{position:absolute;top:0;left:0;z-index:1001;width:100%;height:100%;background-color:rgba(0,0,0,.5);}
</style>


<div id="video-over">
<span class="video-wrapper">
	<svg id="video-playbtn" class="video-playbtn" style="display:block" x="0px" y="0px" width="78px" height="50px" viewBox="0 0 78 50">
		<path fill="#000" opacity="0.650" d="M8,0h62c4.418,0,8,3.582,8,8v34c0,4.418-3.582,8-8,8H8c-4.418,0-8-3.582-8-8V8C0,3.582,3.582,0,8,0z"/>
		<path fill="#fff" d="M51.5,24.5c0,0.997-0.529,1.867-1.316,2.34v0.001L30.573,38.604c-0.007,0.004-0.014,0.008-0.021,0.012l-0.01,0.007h0C30.142,38.861,29.676,39,29.179,39c-1.479,0-2.679-1.217-2.679-2.719l0,0V12.719l0,0c0-1.501,1.199-2.719,2.679-2.719c0.498,0,0.963,0.138,1.362,0.378l0,0l0.01,0.006c0.007,0.004,0.014,0.008,0.021,0.013l19.611,11.762v0C50.971,22.633,51.5,23.503,51.5,24.5z"/>
	</svg>
	<svg id="video-replaybtn" class="video-playbtn" style="display:none" x="0px" y="0px" width="78px" height="50px" viewBox="0 0 78 50">
		<path fill="#000" opacity="0.650" d="M8,0h62c4.418,0,8,3.582,8,8v34c0,4.418-3.582,8-8,8H8c-4.418,0-8-3.582-8-8V8C0,3.582,3.582,0,8,0z"/>
		<path id="video-replaybtn" fill="#fff" d="M53.025,24.439h-0.961h-1.936h-2.904h-5.865l4.551-4.547c-1.533-2.483-4.258-4.153-7.394-4.153c-4.81,0-8.709,3.896-8.709,8.7s3.899,8.7,8.709,8.7c3.396,0,6.304-1.961,7.742-4.792v0.203l0,0c0.207-0.329,0.465-0.601,0.783-0.823c0.107-0.08,0.213-0.159,0.332-0.224c0.117-0.061,0.23-0.11,0.355-0.154c0.297-0.111,0.609-0.191,0.949-0.191c0.324,0,0.623,0.076,0.908,0.184c0.182,0.061,0.344,0.144,0.508,0.238c0.012,0.006,0.023,0.009,0.035,0.016v0.007c0.852,0.506,1.451,1.395,1.451,2.456c0,0.514-0.17,0.971-0.404,1.39h0.002C48.709,35.928,43.998,39,38.516,39C30.499,39,24,32.508,24,24.5S30.499,10,38.516,10c4.72,0,8.871,2.285,11.523,5.77L54,11.813v12.627H53.025z"/>
	</svg>
	<svg id="video-soundoff" class="video-soundbtn" style="display:block" x="0px" y="0px" width="20px" height="20px" viewBox="0 0 20 20">
		<path fill="#000" opacity="0.500" d="M5,0h10c2.762,0,5,2.238,5,5v10c0,2.762-2.238,5-5,5H5c-2.761,0-5-2.238-5-5V5C0,2.238,2.239,0,5,0z"/>
		<path fill="#fff" d="M15.8,5.943L4.698,15.069c-0.072,0.06-0.158,0.095-0.251,0.095c-0.248,0-0.449-0.245-0.449-0.546c0-0.188,0.079-0.354,0.199-0.453l11.102-9.126c0.072-0.059,0.158-0.094,0.252-0.094c0.248,0,0.449,0.244,0.449,0.546C15.999,5.68,15.92,5.846,15.8,5.943z M5.374,8.363c0-0.602,0.401-1.089,0.896-1.09V7.271h2.977l3.042-3.023l0.002,0.004C12.445,4.097,12.641,4,12.856,4c0.468,0,0.848,0.437,0.89,0.991l-8.372,6.873V8.363z M13.755,10.909V12v1.091v1.818c0,0.603-0.402,1.091-0.898,1.091c-0.197,0-0.379-0.08-0.527-0.211l-0.001,0.002l-3.056-2.7H8.69l5.064-4.156V10.909z"/>
	</svg>
	<svg id="video-soundon" class="video-soundbtn" style="display:none" x="0px" y="0px" width="20px" height="20px" viewBox="0 0 20 20">
		<path fill="#000" opacity="0.650" d="M5,0h10c2.762,0,5,2.238,5,5v10c0,2.762-2.238,5-5,5H5c-2.761,0-5-2.238-5-5V5C0,2.238,2.239,0,5,0z"/>
		<path fill="#fff" d="M14.544,14.858l-0.813-0.949c0.703-1.026,1.127-2.326,1.127-3.742c0-1.417-0.424-2.717-1.127-3.743l0.813-0.948C15.452,6.747,16,8.382,16,10.167S15.452,13.587,14.544,14.858z M11.143,15.5c-0.188,0-0.361-0.073-0.502-0.193l-0.001,0.002l-2.917-2.476H4.857c-0.473,0-0.857-0.447-0.857-1V8.5c0-0.552,0.383-0.998,0.855-1V7.498h2.842L10.6,4.726l0.002,0.004C10.75,4.588,10.937,4.5,11.143,4.5C11.616,4.5,12,4.947,12,5.5v3v2.333v1v1V14.5C12,15.053,11.616,15.5,11.143,15.5z"/>
	</svg>
	<span id="video-countdown" class="video-countdown"></span>
	<a id="video-adlinkbtn" class="video-adlinkbtn" style="display:none" href="http://www.pchome.com.tw?videoad" target="_blank"></a>

  	<video width="300" height="168" class="home-banner" controls autoplay loop muted preload="metadata" poster="">
		<source src="http://vstreamdev.mypchome.com.tw/20170119001/20170119001-0240p-0300k-vp8.webm"></source>  
		<source src="http://vstreamdev.mypchome.com.tw/20170119001/20170119001-1000k_640x360.mp4" ></source>
		<source src=""></source>
	</video>
</span>
</div>


<script>
	/*影片屬性&&點擊*/
	var video=document.getElementsByTagName('video')[0];
	video.style.backgroundImage   = "http://a1.att.hudong.com/86/38/300001140423130278388082725_950.jpg";
	video.removeAttribute('controls');
	video.removeAttribute('autoplay');
	video.removeAttribute('loop');
	video.onclick=function(){video[video.paused?'play':'pause']();};

	/*播放&&重新播放按鈕*/
	var pauseTime=0;
	var playbtn=document.getElementById('video-playbtn');
	var replaybtn=document.getElementById('video-replaybtn');
	playbtn.onclick=function(){
		video.play();
	};
	replaybtn.onclick=function(){
		video.play();
	};

	/*聲音&&靜音按鈕*/
	var sndonbtn=document.getElementById('video-soundon');
	var sndoffbtn=document.getElementById('video-soundoff');
	sndoffbtn.onclick=function(){
		video.muted=false;
		this.style.display='none';
		sndonbtn.style.display='block';

	};
	sndonbtn.onclick=function(){
		video.muted=true;
		this.style.display='none';
		sndoffbtn.style.display='block';
	};

	/*廣告連結按鈕*/
	var adlinkbtn=document.getElementById('video-adlinkbtn');

	/*廣告秒數倒數*/
	var adcountdown=document.getElementById('video-countdown');

	video.addEventListener('progress',ProgresHandler,false);
	video.addEventListener('durationchange',DurationchangeHandler,false);
	video.addEventListener('loadedmetadata',MetadataHandler,false);
	video.addEventListener('canplay',CanplayHandler,false);
	video.addEventListener('play',PlayHandler,false);
	video.addEventListener('pause',PauseHandler,false);
	video.addEventListener('timeupdate',TimeupdateHandler,false);
	video.addEventListener('ended',EndedHandler,false);

	//document.addEventListener("fullscreenchange",FsHandler);
	//document.addEventListener("webkitfullscreenchange",FsHandler);
	//document.addEventListener("mozfullscreenchange",FsHandler);
	//document.addEventListener("MSFullscreenChange",FsHandler);
	
	function ProgresHandler(){
		/*影片下載處理中*/
	}
	function DurationchangeHandler(){

	}
	function MetadataHandler(){
		/*讀取影片資訊*/
		var metadatas=[this.duration,this.videoWidth,this.videoHeight];
		console.log('總秒數: '+metadatas[0]+'\n寬度: '+metadatas[1]+'\n高度: '+metadatas[2]);
	}
	function CanplayHandler(){
		/*可開始播放*/
		/*send pv*/
	}
	function PlayHandler(){
		/*播放影片*/
		/*send click*/
		this.currentTime=pauseTime;
		playbtn.style.display='none';
		replaybtn.style.display='none';
		adlinkbtn.style.display='none';
		adcountdown.style.display='block';
	}
	function PauseHandler(){
		/*暫停影片*/
		/*send click*/
		pauseTime=this.currentTime;
		replaybtn.style.display='block';
		//this.load();
	}
	function EndedHandler(){
		/*影片結束*/
		/*send complete*/
		pauseTime=0;
		video.load();
		adlinkbtn.style.display='block';
		adcountdown.style.display='none';
		//exitFS();
	}
	function TimeupdateHandler(){
		/*cue點更新*/
		/*send second record*/
		console.log(this.currentTime);
		var ttime=Math.ceil(this.duration-this.currentTime);
		adcountdown.innerHTML=formatSecond(ttime);
	}
	function formatSecond(secs) {          
         var min=Math.floor(secs/60);
         var sec=parseInt(secs-(min*60));
         if (sec<10) {sec='0'+sec;}
         return min+':'+sec;
     }

	function FsHandler(){
		if(document.fullscreenElement||document.webkitFullscreenElement||document.mozFullScreenElement||document.msFullscreenElement){
		}else{
		}
	}
	function exitFS(){
		if(document.exitFullscreen){
			document.exitFullscreen();
		}else if(document.webkitExitFullscreen){
			document.webkitExitFullscreen();
		}else if(
			document.mozCancelFullScreen){
			document.mozCancelFullScreen();
		}else if(
			document.msExitFullscreen){
			document.msExitFullscreen();
		}
	}

</script>
