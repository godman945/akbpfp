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
               	</tbody>
            </table>

			</h4>
		</div>
		<span class="t_s01">※※※ 提醒您，您的廣告將在3工作天(周一到周五)審核完成(不含例假日)，並於廣告審核完成後開始播放 ※※※</span>
		<center style="margin-top:10px;">
			<input type="button" id="cancel"  onclick="cancerSubmit();" value="取 消"> 
			<#if adStatus != 6 && adStatus != 10 && adGroupStatus != 10 && adActionStatus != 10>
				<input type="button" id="submitBtn" onclick="multipartImgUuploadSubmit();" value="送出審核">
			</#if> 
		</center>
		<input type="hidden" id="adSeq" name="adSeq" value="${adSeq!}">
		<input type="hidden" id="adGroupSeq" name="adGroupSeq" value="${adGroupSeq!}">
	</form>
</div>
<input type="hidden" id="messageId" value="${message!}">

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
	               		16:9
	               	</td>
	            </tr>
	           	<tr>
	                <th height="20">影音長度</th>
	                <td>
	               		30秒內
	               	</td>
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
	                    		<p>640 x 390(電腦)</p>
	                    		<p>950 x 390(電腦)</p>
	                    		<p>970 x 390(電腦)</p>
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
<input id="adGroupSeq" type='hidden' value='${adGroupSeq!}'>
