<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<@t.insertAttribute name="includeJs" />
<div class="cont">
		<h1 class="adtitle">廣告：${adActionName!} > 分類：${adGroupName!}</h1>
		<h2>
			<div class="cal">帳戶名稱：${customer_info_title!}</div>
			<img vspace="12" hspace="2" align="absmiddle" src="<@s.url value="/" />html/img/iconcr.gif">修改廣告
		</h2>
		<ul class="admenuul">
            <li class="m03"><a href="javascript:void();" class="active">影音廣告</a></li>
        </ul>
        <div class="grtba" style="padding:1px 10px;margin-bottom:10px;">
			<h4>廣告狀態：${adStatusDesc!}</h4>
			<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
                <tbody>
                	<tr>
                        <th height="35" style="width:12%;"><a name="errAdLinkURL"></a><span class="t_s02">* </span>影片<br><a href="#" target="" onclick="approveSize('approveSizeDiv');">支援規格查詢</a></th>
                        <td style="min-width:200px;color:#939393">
                      		${adVideoURL}
                        </td>
                        <td style="" rowspan="5" width="50%">
                            <!--播放預覽start-->

                            <div style="display:block;" class="boxpreview">
                                <h5>廣告預覽 / 曝光示意圖</h5>
                            <!--<center><a href="3">參考其他人撰寫關鍵字廣告</a></center>-->
                                <!--按鈕 <div class="swiper-container swiper-container-horizontal">
                                <div class="swiper-button-next swiper-button-black"></div>
                                <div class="swiper-button-prev swiper-button-black swiper-button-disabled"></div>-->

                                <!--電腦版 - 300x250 start-->
                                <div>
                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">電腦版 - 300x250 - 樣式A - 無背景
                                </div>
                                    <div class="video_adpreviewbg">
                                        <div class="video_boxpreview">
                                        <div class="pc300250_preview">
                                        <div class="video_box">
                                        <div class="iframe_style_pc300250">
                                        <iframe width="300" height="168" src="https://www.youtube.com/embed/x0WSucyB5hU" frameborder="0" allowfullscreen=""></iframe>
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
                                        <iframe width="300" height="168" src="https://www.youtube.com/embed/Ja-vnNquM-8" frameborder="0" allowfullscreen=""></iframe>
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
                                        <div class="pc300600_preview pc300600_0img ">
                                         <div class="video_box">
                                         <div class="iframe_style_pc300600">
                                         <iframe width="300" height="168" src="https://www.youtube.com/embed/Ja-vnNquM-8" frameborder="0" allowfullscreen=""></iframe>
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
                                         <iframe width="300" height="168" src="https://www.youtube.com/embed/Ja-vnNquM-8" frameborder="0" allowfullscreen=""></iframe>
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
                                         <iframe width="300" height="168" src="https://www.youtube.com/embed/Ja-vnNquM-8" frameborder="0" allowfullscreen=""></iframe>
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
                                         <iframe width="300" height="168" src="https://www.youtube.com/embed/Ja-vnNquM-8" frameborder="0" allowfullscreen=""></iframe>
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
                                         <iframe width="300" height="168" src="https://www.youtube.com/embed/Ja-vnNquM-8" frameborder="0" allowfullscreen=""></iframe>
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
                                        <iframe width="300" height="168" src="https://www.youtube.com/embed/Ja-vnNquM-8" frameborder="0" allowfullscreen=""></iframe>
                                        </div>
                                        </div>
                                        </div>
                                        </div>
                                    </div>
                                </div>

                                <div style="display: none;">
                                <div style="margin: 0 auto; font-size: 16px;background-color: #0083d5;;color: #fff;line-height: 26px;font-weight: 600;text-align: center;">電腦版 - 336x280 - 樣式B - 無背景
                                </div>
                                    <div class="video_adpreviewbg">
                                        <div class="video_boxpreview">
                                        <div class="pc336280_preview videobg_on">
                                        <div class="video_box">
                                        <div class="iframe_style_pc300250">
                                        <iframe width="300" height="168" src="https://www.youtube.com/embed/Ja-vnNquM-8" frameborder="0" allowfullscreen=""></iframe>
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
                                        <iframe width="300" height="168" src="https://www.youtube.com/embed/Ja-vnNquM-8" frameborder="0" allowfullscreen=""></iframe>
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
                                        <iframe width="300" height="168" src="https://www.youtube.com/embed/Ja-vnNquM-8" frameborder="0" allowfullscreen=""></iframe>
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
                                         <iframe width="300" height="168" src="https://www.youtube.com/embed/Ja-vnNquM-8" frameborder="0" allowfullscreen=""></iframe>
                                         </div>
                                         <img border="0" src="img/v_970250.gif">
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
                                        <iframe width="300" height="168" src="https://www.youtube.com/embed/Ja-vnNquM-8" frameborder="0" allowfullscreen=""></iframe>
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
                                        <iframe width="300" height="168" src="https://www.youtube.com/embed/Ja-vnNquM-8" frameborder="0" allowfullscreen=""></iframe>
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
                                        <iframe width="300" height="168" src="https://www.youtube.com/embed/Ja-vnNquM-8" frameborder="0" allowfullscreen=""></iframe>
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
                                        <iframe width="300" height="168" src="https://www.youtube.com/embed/Ja-vnNquM-8" frameborder="0" allowfullscreen=""></iframe>
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
                                        <iframe width="300" height="168" src="https://www.youtube.com/embed/Ja-vnNquM-8" frameborder="0" allowfullscreen=""></iframe>
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
                                        <iframe width="300" height="168" src="https://www.youtube.com/embed/Ja-vnNquM-8" frameborder="0" allowfullscreen=""></iframe>
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
                                        <iframe width="300" height="168" src="https://www.youtube.com/embed/Ja-vnNquM-8" frameborder="0" allowfullscreen=""></iframe>
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
                                        <iframe width="300" height="168" src="https://www.youtube.com/embed/Ja-vnNquM-8" frameborder="0" allowfullscreen=""></iframe>
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
                                        <iframe width="300" height="168" src="https://www.youtube.com/embed/Ja-vnNquM-8" frameborder="0" allowfullscreen=""></iframe>
                                        </div>
                                        </div>
                                        </div>
                                        </div>
                                        </div>
                                </div>

                            <!--廣告預覽end-->
                        </div></td>
                    </tr>
                        <tr>
                        <th height="35" style="width:12%;"><a name="errAdLinkURL"></a><span class="t_s02">* </span>影片到達網址</th>
                        <td style="min-width:200px;">
                            <input type="hidden" id="adDetailID" name="adDetailID" value="real_url">
                            <input type="hidden" id="adDetailName" name="adDetailName" value="影片到達網址">
                            <input type="hidden" id="adDetailContent" name="adDetailContent">
                            <input type="text" class="inputPlaceholderTmg" id="adLinkURL" name="adLinkURL" value="${adLinkURL}" style="width:96%;" placeholder="https://www.youtube.com/" maxlength=""><br>
                            <span id="chkLinkURL" name="chkLinkURL" style="color:red"></span><span style="float:right; margin-right: 4%;" id="spanAdLinkURL">已輸入0字，剩1024字</span>
                        </td>
                    </tr>
                    <tr>

                </tr></tbody>
            </table>


			<span class="t_s01"> 
				<#assign reason>
  						${adVerifyRejectReason?replace("客服中心","<a href='http://faq.pchome.com.tw/service/user_reply.html?ch=show' target='_blank'>客服中心</a>")}
				</#assign>
				<#assign reason2>
  						${reason?replace("廣告規範","<a href='https://show.pchome.com.tw/faq.html?fid=4&qid=5' target='_blank'>廣告規範</a>")}
				</#assign>
				<#assign reason3>
  						${reason2?replace("相關規範","<a href='https://show.pchome.com.tw/faq.html?fid=4&qid=5' target='_blank'>相關規範</a>")}
				</#assign>
		        ${reason3!}
			</span>
			</h4>
		</div>
		<div class="grtba">
			<h4 style="display:none;">建立廣告</h4>
			<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02" style="display:none;">
				<tbody>
					<tr>
						<th height="35"><span class="t_s02">* </span>選擇廣告分類</th>
						<td>
							<select id="adClass" name="adClass">
								<option value="0">選擇分類</option>
								<option value="1" selected>分類1</option>
								<option value="2">分類2</option>
								<option value="3">分類3</option>
								<option value="4">分類4</option>
								<option value="5">分類5</option>
								<option value="6">分類6</option>
							</select>
						</td>
					</tr>
					<tr>
						<th height="35"><span class="t_s02">* </span>廣告樣式</th>
						<td>
							<input type="radio" id="adStyle" name="adStyle" value="TXT" onclick="setAdStyle(this.value);" <#if adStyle == "TXT">checked</#if>>文字廣告
							<input type="radio" id="adStyle" name="adStyle" value="TMG" onclick="setAdStyle(this.value);" <#if adStyle == "TMG">checked</#if>>圖文廣告
						</td>
					</tr>
				</tbody>
			</table>
		</div>

		<span class="t_s01">※※※ 提醒您，您的廣告將在3工作天(周一到周五)審核完成(不含例假日)，並於廣告審核完成後開始播放 ※※※</span>
		<center style="margin-top:10px;">
			<input type="button" id="cancel"  onclick="cancerSubmit();" value="取 消"> 
			<#if adStatus != 6 && adStatus != 10 && adGroupStatus != 10 && adActionStatus != 10>
			<input type="button" id="submitBtn" onclick="multipartImgUuploadSubmit();" value="送出審核">
			</#if> 
		</center>
		<input type="hidden" id="adSeq" name="adSeq" value="${adSeq!}">
		<input type="hidden" id="ulTmpName" name="ulTmpName" value="${ulTmpName!}">
		<input type="hidden" id="imgFile" name="imgFile" value="${imgFile!}">
		<input type="hidden" id="adStatus" name="adStatus" value="${adStatus!}">
		<input type="hidden" id="adType" name="adType" value="${adType!}">
	</form>
	<iframe id="uploadIMG" name="uploadIMG" style="display:none;height:150px;width:600px"></iframe>
	<iframe id="doAdd" name="doAdd" style="display:none;height:150px;width:600px"></iframe>
</div>
<input type="hidden" id="messageId" value="${message!}">



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