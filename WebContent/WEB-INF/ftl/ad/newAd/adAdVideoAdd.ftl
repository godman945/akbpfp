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

                                <div>
                                	<div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">電腦版 - 300x600
                                </div>
                                    <div class="video_adpreviewbg">
                                        <div class="video_boxpreview">
                                        <div class="pc300600_preview">
                                        <!--pchome logo ico start here-->
                                        <style>        /* logo 位置隨 iframe width 置右上 or 靠左上 */        /* right:0px -->置右 */        /* left:0px  -->靠左 */        .p8044_ad_icon{text-indent:-10000px;position:absolute;top:0px;right:0px;z-index:auto;width:20px;height:18px;line-height:18px;cursor:pointer;text-rendering:geometricPrecision;} .p8044_ad_icon{background:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAASCAYAAABb0P4QAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAShJREFUeNpi/P//PwM1ARMDlQELugAjIyOIYgZiQSzqfwLxVyD+B+Jg8x0LDotYMzMz5ZuamorRJV6/fn3h06dPCy0sLF5i1QmyBRlDAe/27dsj/uMA//79ewek1NH1gjCuMGSVk5NTQuLfBxryEClYQMGRQ0qksAoKCsoj8bOYmZl1b968WYskpkiSgdzc3HAX7tmz5ykokv7+/fsPSc0jomIZ5COQgRwcHHADZWVl/YGuY1ZWVs5AUreb2Ehh1dDQUAOy//zHDY4BMTOxkcJaWFioAE2L6OA7EE8EYk9gxPwl1susOjo6yBGyAIingMIMaMhrknMKyEAJCQnkJHMcaNBZSvIyKx8fH7ILb5KUmbHlFCB9ACkCpInVC8KMg774orqBAAEGAOSN3py5SBQXAAAAAElFTkSuQmCC) #84a7de 0 0 no-repeat;}        .p8044_ad_icon:hover{text-indent:0px;padding-left:52px;width:62px;font-size:12px;color:#FFF;text-shadow:-1px -1px #84a7de}        .p8044_ad_icon:hover{background:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAASCAYAAAAZk42HAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAuJJREFUeNrclmGEVFEUx9/MjmGZxLIsj6FMIpYlStnEZlM2rWFZyow+1ZdWkWiJlCilFJFW2dWHGCKtVqs0Wo2W5TEso2WIIZZhGYYYxnQOv8vxvGWnT2sOf3PPe/eec/7nnHvmxTqdjtcLksjn871BxKz3CW6F3v8VLAMrScGY4IjAFxQFXwVbXfofwc4CZ6cEJcGQoMy7Iv5GWU/z67Pnsp6xREZ5GJZrgqeCG+gnBfMQd6LnTgk+43SPoLkDImcEi4JxQUEwQfAvDVENOiNImTg1hjn0Y2ojboweNOsCbC0ZNXRe8CVEwkkLEn92SMJVPGsqviKoCTbwl+b5gKDCepU9Vfypr6atSMYEdFHQFnyHfR9G5zmsbTAj+CDYK3hiMrbeRWs9D+kLocRcZ10yz9+FzsyE74irSBUSSfrQI/AsmVHRCbFksqrEb5rzE9hTor+Njyme13lXJxHT2Fml6lrV9yRX22+Ndyr92PGpjHZPO6oiavgj/Zk2WRhnXTMkvIjzGshV1g8E+8nuN8Gw2a9VPE5yXgkaVN5Vdon70o9+VLCJnYxp5QuCc+6O+MaATzCOhN6VO+ZebG7TJq6igeA0vZvE6VtIrNEuFfzdNee2GBjrJo5JMwlVf4292yQhoPqZRCibHlOiSqlLtEAb3SMgn/K7UTxgbMxyWRsE20c11cZZE9iz0LkCRGv4mGOk19nXpEIq94GTVCJiYt1jeoSlyD4t9Q8zIi8JrkCuRRJSRm9CRokMss6aSzxsKu+ZypfZ63RnR+0fYD2C3XJURarbtM5DAhjC+KOIPW5QZIxe5sLrmV9mr7bQY8FP9A2Cyxg9bUZ6mV99/4lk6UR9oZMrHqpI07RMWLTkJyIueoCzgFZwc9/pLVpqkXbTAN9wHxoggLQPwRVacJB3y7T2JDbHSOisG9GxXC6nv4domVYX/wOHIVffTd9alf84G+ymj8ZYr3zGx70ekZ4h8k+AAQCoOcTt/fuWhQAAAABJRU5ErkJggg==) #84a7de 0 0 no-repeat;}
                                        </style>
                                        <a href="http://show.pchome.com.tw" target="_blank" style="text-decoration:none"><div class="p8044_ad_icon">廣告聯播網</div></a>
                                        <div class="p8044_ad_icon">廣告聯播網</div>

                                         <div class="video_box">
                                         <div class="iframe_style">
                                         	<iframe width="300" height="168" src="" frameborder="0" allowfullscreen=""></iframe>
                                         </div>
                                         	<img border="0" src="./html/img/v_300250.gif">
                                         </div>
                                        </div>
                                        </div>
                                    </div>
                                </div>

                                <div style="display: none;">
                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">電腦版 - 300x250
                                </div>
                                    <div class="video_adpreviewbg">
                                        <div class="video_boxpreview"><img border="0" src="./html/img/v_300250.jpg"></div>
                                    </div>
                                </div>

                                <div style="display: none;">
                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">電腦版 - 300x600
                                </div>
                                    <div class="video_adpreviewbg">
                                        <div class="video_boxpreview"><img border="0" ./html/img/v_300600.jpg"></div>
                                    </div>
                                </div>

                                <div style="display: none;">
                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">電腦版 - 336x280
                                </div>
                                    <div class="video_adpreviewbg">
                                        <div class="video_boxpreview"><img border="0" ./html/img/v_336280.jpg"></div>
                                    </div>
                                </div>

                                <div style="display: none;">
                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">電腦版 - 970x250
                                </div>
                                    <div class="video_adpreviewbg">
                                        <div class="video_boxpreview"><img border="0" ./html/img/v_970250.jpg"></div>
                                    </div>
                                </div>

                                <div style="display: none;">
                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">手機版 - 300x250
                                </div>
                                        <div class="video_adpreviewbg">
                                        <div class="m_preview_box">
                                        <div class="m_preview"><img border="0" ./html/img/v_300250.jpg"></div>
                                        <div class="video_boxpreview"><img border="0" ./html/img/mb_bgimg.png"></div>
                                    </div>
                                </div>
                                </div>

                               <div style="display: none;">
                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">手機版 - 320x480
                                </div>
                                    <div class="video_adpreviewbg">
                                    <div class="m_preview_box">
                                        <div class="m_preview"><img border="0" ./html/img/v_320480.jpg"></div>
                                        <div class="video_boxpreview"><img border="0" ./html/img/mb_bgimg.png"></div>
                                    </div>
                                </div>
                                </div>

                                <div style="display: none;">
                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">手機版 - 336x280
                                </div>
                                    <div class="video_adpreviewbg">
                                    <div class="m_preview_box">
                                        <div class="m_preview"><img border="0" ./html/img/v_336280.jpg"></div>
                                        <div class="video_boxpreview"><img border="0" ./html/img/mb_bgimg.png"></div>
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
	                               	<input type="file" serialize id="fileupload" name="fileupload" onchange="previewImage(this)" style="display:none ;" multiple="" >
                              </span>
                        </td>
                        
                    </tr>
                </tbody>
            </table>
			
			<!--上傳訊息start 預設圖-->
			<ul id="AG" class="aduplodul" style="width: 96%; padding-left: 25px;">
                <!--[if !IE 9]> -->
	                <li class="okbox" id=""><div class="adboxdv"><img id="imghead_1" src="<@s.url value="/" />html/img/upl9090.gif"></div><ul style="padding-top: 5px"><li class=""><b style="font-size: 18px;text-align:center;margin-top: 30px;">300 x 250</b></li></ul></li>
	                <li class="okbox" id=""><div class="adboxdv"><img id="imghead_2" src="<@s.url value="/" />html/img/upl9090.gif"></div><ul style="padding-top: 5px"><li class=""><b style="font-size: 18px;text-align:center;margin-top: 30px;">300 x 600</b></li></ul></li>
	                <li class="okbox" id=""><div class="adboxdv"><img id="imghead_3" src="<@s.url value="/" />html/img/upl9090.gif"></div><ul style="padding-top: 5px"><li class=""><b style="font-size: 18px;text-align:center;margin-top: 30px;">320 x 480</b></li></ul></li>
	                <li class="okbox" id=""><div class="adboxdv"><img id="imghead_4" src="<@s.url value="/" />html/img/upl9090.gif"></div><ul style="padding-top: 5px"><li class=""><b style="font-size: 18px;text-align:center;margin-top: 30px;">970 x 250</b></li></ul></li>
            	<![endif]-->
            	<!--[if IE 9]>
            		<img id="" style="" src="http://static.ettoday.net/images/1744/1744834.jpg">
            	<![endif]-->
            </ul>
			<span class="t_s01">提醒您，於網路中張貼或散佈色情圖片是觸法行為，違者可處二年以下有期徒刑、拘役或併科三萬元以下罰金</span>


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

<input type="text" id="adGroupSeq" value="${adGroupSeq!}">
<input type="text" id="adOperatingRule" value="${adOperatingRule!}">
<input type="text" id="adStyle" value="${adStyle!}">
<input type="text" id="adClass" value="1">
