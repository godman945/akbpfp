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
                            <div id="adTitle"></div>
                            <span id="adVideoURLMsg" name="adVideoURLMsg" style="color: rgb(255, 0, 0);"></span><span style="float:right;margin-right: 3%" id="adVideoUrlWordController">已輸入0字，剩<div id="checkWord" style="display:inline;">1024</div>字</span><br>
						</td>
					</tr>
					<tr>
                        <th height="35" style="width:12%;"><a name="errAdLinkURL"></a><span class="t_s02">* </span>
                        	影片到達網址
                        	<!-- 第三方偵測暫不上線
                        	<br>
                              <b class="thirdpty-togglebtn"><span class="swap">＋</span>第三方曝光追蹤代碼</b>
                              -->
						</td>
                        
                        </th>
                        <td style="min-width:200px;">
                            <input type="hidden" id="adDetailID" name="adDetailID" value="real_url">
                            <input type="hidden" id="adDetailName" name="adDetailName" value="影片到達網址">
                            <input type="hidden" id="adDetailContent" name="adDetailContent">
                            <input type="text" class="inputPlaceholderTmg" id="adLinkURL" name="adLinkURL" style="width:96%;" placeholder="請輸入影片到達網址" maxlength=""><br>
                            <span id="chkLinkURL" name="chkLinkURL" style="color:red"></span><span style="float:right;clear: both;;margin-right: 3%" id="spanAdLinkURL">已輸入0字，剩1024字</span>
                        </td>
                    </tr>
                    <tr class="thirdptybx">
                        <th style="width:12%">
                           	 第三方曝光追蹤代碼<a style="cursor:pointer;" onclick="opennots(4)"><img src="http://show.pchome.com.tw/html/img/question.gif" align="absmiddle"></a><br>
                            <div id="shownotes4" style="visibility:hidden;" class="adnoticepop">
                                <h4>第三方曝光追蹤代碼</h4>
                                <div class="adpopcont">僅支援 1x1 像素(pixel) 格式的第三方曝光追蹤代碼</div>
                                <a onclick="closenots(4)" style="cursor:pointer;" class="adpopclose">關閉</a>
                            </div>
                        </th>
                        <td>
                          	     <div class="code-box">
                                    <textarea rows='1' placeholder='請輸入第三方曝光追蹤代碼' id="thirdCode" name="thirdCode"></textarea>
                                 </div>
                        </td>
                    </tr>

                    <tr id="notVerticalAdTr">
                        <th height="35" style="width:12%"><a name="errAdImg"></a><span class="t_s02">* </span>Banner廣告圖<br><a href="#" target="" onclick="approveSize('bannerDiv');">支援規格查詢 </a></th><td style="min-width:200px;">
                             <table border="0" cellspacing="0" cellpadding="0">
                            	<tbody>
									<tr>
                                       <td width="100" align="center" valign="middle">
                                       	<input type="button" style="display:block; clear: both " id="fileButton" name="fileButton" value="瀏覽檔案" onclick="fileLoad()">
	                               		<div id="errMsg" style="text-align:-webkit-left;"></div>
	                               		<input type="file" serialize id="fileupload" name="fileupload"  style="display:none ;" multiple="" >
                                       </td>
                                       <td>
                                         <table border="0" cellspacing="0" cellpadding="0" >
                                            <tbody>
                                              <tr>
                                                <td style="line-height: 10px;font-size: 13px;color:red">若要播放以下尺寸的影音廣告，請上傳所搭配的Banner廣告圖尺寸一張。</td>
                                              </tr>
                                              <tr>
                                                <td style="line-height: 10px;font-size: 13px">300x600：需上傳 300x600 Banner廣告圖 (<a href="#" onclick="showBanner('banner300600');">範例</a>)</td>
                                              </tr>
                                              <tr>
                                                <td style="line-height: 10px;font-size: 13px">320x480：需上傳 320x480 Banner廣告圖 (<a href="#" onclick="showBanner('banner320480');">範例</a>)</td>
                                              </tr>
                                              <tr>
                                                <td style="line-height: 10px;font-size: 13px">950x390：需上傳 310x390 Banner廣告圖 (<a href="#" onclick="showBanner('banner950390');">範例</a>)</td>
                                              </tr>
                                              <tr>
                                                <td style="line-height: 10px;font-size: 13px">970x250：需上傳 579x250 Banner廣告圖 (<a href="#" onclick="showBanner('banner970250');">範例</a>)</td>
                                              </tr>
                                            </tbody>
                                          </table>
                                      </td>
									</tr>
                            		</tbody>
                          		</table>
                            	<span class="imgar" style="width:100%;">
                                <!--上傳圖片start
									<span style="color:red;float:left;text-align:left;">請提供300x250、300x600、320x480、970x250，四種尺寸的廣告圖各一張。</span><br>
	                              	<span style="float:left;text-align:left;margin-bottom: 5px">若位提供，個影音廣告尺寸版行會自動帶入系統預設的廣告圖。</span>
	                               	<input type="button" style="display:block; clear: both " id="fileButton" name="fileButton" value="瀏覽檔案" onclick="fileLoad()">
	                               	<div id="errMsg" style="text-align:-webkit-left;"></div>
	                               	<input type="file" serialize id="fileupload" name="fileupload"  style="display:none ;" multiple="" >
                              </span>
                              -->
                        </td>
                    </tr>
                </tbody>
            </table>
            </div>
            <!-- adTmg end -->
            
            <div style="height: 15px"></div>
           <!--上傳預覽區塊_圖片start--><!--上傳訊息start 預設是隱藏，有資料才顯示-->
        <div class="grtba" id="imgPreview">
            <h4>Banner廣告圖預覽</h4>
            <div style="">
            <div class="" style="">
                <ul id="AG" class="aduplodul_p"></ul>
            </div>


            </div>
        </div>
<!--上傳預覽區塊_圖片end-->

       <div style="height: 20px"></div>     
                 <div class="grtba" style="display:block;">
          <h4>選擇廣告版型與尺寸</h4>
        <span style="font-size: 14px;margin-top: 15px;margin-bottom: 10px; float: right; display: block;">廣告尺寸：
        	<select id="adViseoSize" name="adViseoSize">
             	<option value="0">全部尺寸</option>
            </select>
            </span>
        <div style="clear:both;margin:15px auto;border-bottom:dotted 1px #ccc;"></div>
            <span class="adVideoCheckArea" style="margin-left: 10px;font-size:14px;display:none;">請勾選您想要的廣告版型與尺寸</span>
            <span class="adVideoCheckArea" style="font-size: 14px;margin-top: 5px;margin-left: 10px;display: block; color: #1d5ed6;display:none;">
            	<input type="checkbox" name="checkboxAll" id="checkboxAll" checked/>選擇全部</span>
                <div class="aduplodul_v">
                <div id="preViewArea" style="padding: 10px">
                 
                   
                </div>
                </div>
            <span id="bannerMsgSpan" style="font-size: 13px;line-height: 30px; text-align: center; color: red; clear: both;display: block;">請先輸入您的影片網址與上傳Banner廣告圖</span>
            <span id="sizeMsgSpan" style="font-size: 13px;line-height: 30px;  text-align: center; clear: both;display: block;"">若您沒有上傳任何Banner廣告圖，則僅支援300x250、336x280、640x390 三種影音廣告尺寸。</span>
        </div>
		<!--上傳預覽區塊_影音end-->
            
            
            
            
			<div id="notSuppotDiv" style="display:none;">
                	您的瀏覽器不支援唷! 請更換IE10以上或其它瀏覽器
                <img src="http://show.pchome.com.tw/html/main/img/tt_pp2.png" class="tt_pp">
            </div>

<div id="approveSizeDiv" style="display:none;">
	<div class="noticepop" style="width:auto;"><h4>影音廣告支援規格查詢</h4><div>
	    <table width="97%" cellspacing="1" cellpadding="0" border="0" class="tb02" style="margin:10px auto;line-height:18px;">
	        <tbody>
	            <tr>
	                <th height="20">影音格式</th>
	                <td>
	                 目前僅支援Youtube影片連結上稿
	                </td>
	            </tr>
	            <tr>
	                <th height="20">影音長寬比</th>
	                <td>
	               		16：9、9：16
	               	</td>
	            </tr>
	           	<tr>
	                <th height="20">影音長度</th>
	                <td>
	               		30秒以內
	               	</td>
	            </tr>
	            <tr>
	              <th height="20">一般影音廣告尺寸</th>
	              <td>
	               <div id="adSizeDiv" style="height:200px;">
	                 <div>
	                    <div style="width:110px;float:left;">
	                    		<p>300 x 250(電腦)</p>
	                    		<p>300 x 600(電腦)</p>
	                    		<p>336 x 280(電腦)</p>
	                    		<p>640 x 390(電腦)</p>
	                    		<p>950 x 390(電腦)</p>
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
	            <tr>
	             <th height="20">直立式影音廣告尺寸</th>
	              <td>
	               <div id="adSizeDiv" style="height:50px;">
	                 <div>
	                    <div style="width:110px;float:left;">
	                    		<p>300 x 600(電腦)</p>
	                    </div>
	                    <div style="width:120px;float:left;">
	                    		<p>320 x 480(行動裝置)</p>
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
	                <th height="20">圖片大小上限</th>
	                <td>
	                	 180 KB
	                </td>
	            </tr>
	            <tr>
	              <th height="20">圖片大小</th>
	              <td>
	               <div id="adSizeDiv" style="height:200px;overflow:auto;">
	                 <div>
	                    <div style="width:110px;float:left;">
	                            <p>300 x 600</p>
	                            <p>320 x 480</p>
	                            <p>310 x 390</p>
	                            <p>579 x 250</p>
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


<div id="videoSize" style="display:none;">
	<#list adVideoSizeMap?keys as key>
		<#if key?index_of("BANNER") == 0 >
			<p>${adVideoSizeMap[key]}</p>	
		</#if>
	</#list>
</div>

<!-- 300x600 Banner廣告圖 說明   START-->
<div  style="display:none;">
	   	<table id="banner300600" border="0" align="center" cellpadding="1" cellspacing="0">
		  <tbody>
		    <tr>
		      <td bgcolor="#CDCDCD"><table border="0" cellspacing="0" cellpadding="8">
		        <tbody>
		          <tr>
		            <td align="center" bgcolor="#E7E7E7" style="font-size: 15px;">影音廣告 - 300x600 
		            	<span style="color: #1064cd">範例說明</span>
		            </td>
		          </tr>
		          <tr>
		            <td align="center" bgcolor="#FFFFFF" style="font-size: 15px;">需上傳300x600的Banner廣告圖，請參照以下指示製作素材。</td>
		          </tr>
		          <tr>
		            <td align="center" bgcolor="#FFFFFF"><img src="html/img/300600_size_p.gif" width="400" height="640" alt=""/></td>
		          </tr>
		        </tbody>
		      </table></td>
		    </tr>
		  </tbody>
		</table>
</div>
<!-- 300x600 Banner廣告圖 說明   END-->

<!-- 320x480 Banner廣告圖 說明   START-->
<div  style="display:none;">
<table id="banner320480" border="0" align="center" cellpadding="1" cellspacing="0">
  <tbody>
    <tr>
      <td bgcolor="#CDCDCD"><table border="0" cellspacing="0" cellpadding="8">
        <tbody>
          <tr>
			  <td align="center" bgcolor="#E7E7E7" style="font-size: 15px;">影音廣告 - 320x480 <span style="color: #1064cd">範例說明</span></td>
          </tr>
          <tr>
            <td align="center" bgcolor="#FFFFFF" style="font-size: 15px;">需上傳320x480的Banner廣告圖，請參照以下指示製作素材。</td>
          </tr>
          <tr>
            <td align="center" bgcolor="#FFFFFF"><img src="html/img/320480_size_p.gif" width="410" height="530" alt=""/></td>
          </tr>
        </tbody>
      </table></td>
    </tr>
  </tbody>
</table>
</div>
<!-- 320x480 Banner廣告圖 說明   END-->

<!-- 950x390 Banner廣告圖 說明   START-->
<div  style="display:none;">
<table id="banner950390" border="0" align="center" cellpadding="1" cellspacing="0">
  <tbody>
    <tr>
      <td bgcolor="#CDCDCD"><table border="0" cellspacing="0" cellpadding="8">
        <tbody>
          <tr>
            <td align="center" bgcolor="#E7E7E7" style="font-size: 15px;">影音廣告 - 950x390 <span style="color: #1064cd">範例說明</span></td>
          </tr>
          <tr>
            <td align="center" bgcolor="#FFFFFF" style="font-size: 15px;">需上傳310x390的Banner廣告圖，請參照以下指示製作素材。</td>
          </tr>
          <tr>
            <td align="center" bgcolor="#FFFFFF"><img src="html/img/950390_size_p.gif" width="1030" height="450" alt=""/></td>
          </tr>
        </tbody>
      </table></td>
    </tr>
  </tbody>
</table>
</div>
<!-- 950x390 Banner廣告圖 說明   END-->

<!-- 970x250 Banner廣告圖 說明   START-->
<div  style="display:none;">
   	<table id="banner970250" border="0" align="center" cellpadding="1" cellspacing="0">
	  <tbody>
	    <tr>
	      <td bgcolor="#CDCDCD"><table border="0" cellspacing="0" cellpadding="8">
	        <tbody>
	          <tr>
	            <td align="center" bgcolor="#E7E7E7" style="font-size: 15px;">影音廣告 - 970x250 <span style="color: #1064cd">範例說明</span>
	            </td>
	          </tr>
	          <tr>
	            <td align="center" bgcolor="#FFFFFF" style="font-size: 15px;">需上傳579x250的Banner廣告圖，請參照以下指示製作素材。</td>
	          </tr>
	          <tr>
	            <td align="center" bgcolor="#FFFFFF"><img src="html/img/970250_size_p.gif" width="1050" height="315" alt=""/></td>
	          </tr>
	        </tbody>
	      </table></td>
	    </tr>
	  </tbody>
	</table>
</div>
<!-- 970x250 Banner廣告圖 說明   END-->

<div id="shownotes3" style="visibility: hidden;" class="adnoticepop">
       <h4>第三方曝光追蹤代碼</h4>
       <div class="adpopcont">僅支援 1x1 像素(pixel) 格式的第三方曝光追蹤代碼</div>
       <a onclick="closenots(3)" style="cursor:pointer;" class="adpopclose">關閉</a>
</div>