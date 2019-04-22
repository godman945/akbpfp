<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<div>
    <form id="formImg" action=""  method="post"  target="hidden_frame" enctype="multipart/form-data">
        <div>
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
							<input type="radio" id="adStyle" name="adStyle" value="TXT" onclick="setAdStyle(this.value);" >文字廣告
							<input type="radio" id="adStyle" name="adStyle" value="TMG" onclick="setAdStyle(this.value);" checked>圖文廣告
						</td>
					</tr>
				</tbody>
			</table>

<!-- adTmg start -->
  			
			<h4>製作廣告</h4>
			<table width="100%" id="alex" cellspacing="1" cellpadding="0" border="0" class="tb02">
				<tbody>
					<tr>
						<th height="35" style="width:12%;"><a name="errAdLinkURL"></a><span class="t_s02">* </span>廣告連結網址</th>
						<td style="min-width:250px;">
							<input type="hidden" id="adDetailID" name="adDetailID" value="real_url">
							<input type="hidden" id="adGroupSeq" name="adGroupSeq" value="${adGroupSeq}">
							<input type="hidden" id="adDetailSeq" name="adDetailSeq" value="${adDetailSeq[0]!}">
							<input type="hidden" id="adDetailContent" name="adDetailContent" value="${adDetailContent[0]!}">
							<input type="text" class="inputPlaceholderTmg" id="adLinkURL" name="adLinkURL" style="width:96%;" value="${adDetailContent[0]!}" placeholder="show.pchome.com.tw" maxlength="1024"><br>
							<span id="chkLinkURL" name="chkLinkURL" style="color:red"></span><span style="float:right" id="spanAdLinkURL">已輸入0字，剩1024字</span>
						</td>
					</tr>
					
					<tr class="" style="display: ;">
                        <th style="">
                           		 第三方曝光追蹤代碼<a style="cursor:pointer;" onclick="opennots(3)"><img src="http://show.pchome.com.tw/html/img/question.gif" align="absmiddle"></a><br>
                            <div id="shownotes3" style="z-index:500;visibility:hidden;" class="adnoticepop">
                                <h4>第三方曝光追蹤代碼</h4>
                                <div class="adpopcont">僅支援 1x1 像素(pixel) 格式的第三方曝光追蹤代碼</div>
                                <a onclick="closenots(3)" style="cursor:pointer;" class="adpopclose">關閉</a>
                            </div>
                        </th>
                        <td>
                          <div class="code-box">
                              <textarea rows="1" placeholder="請輸入第三方曝光追蹤代碼" id="thirdCode" name="thirdCode" style="resize:none;margin: 0px 310px 0px 0px; height: 137px; width: 632px;">${thirdCode!}</textarea>
                          		<input type="hidden" id="adDetailSeq" name="adDetailSeq" value="${adDetailSeq[2]!}">
                          		<input type="hidden" id="adDetailSeq" name="adDetailContent" value="${adDetailContent[2]!}">
                          </div>
                        </td>
                    </tr>
				</tbody>
			</table>
			 
			<div id="notSuppotDiv" style="display:none;">
				您的瀏覽器不支援唷! 請更換IE10以上或其它瀏覽器 
				<img src="<@s.url value="/" />html/main/img/tt_pp2.png" class="tt_pp">
			</div>
			
            <!--上傳訊息start 預設是隱藏，有資料才顯示-->
            <div class="aduplodulbg" style="" >
                <ul id="AG" class="aduplodul">
                	<li class="okbox" style="padding: 0 0 20px 0;" id="${adDetailSeq[1]!}">
                		<div class="adboxdv">
                			<#if html5Flag == 'Y' >
                				<div style="background-color:#FFFFFF;margin:50px 0px 0px 0px;line-height:80px;font-size:16px;" >${zipTitle!}</div>
                				<p class="fancy adinf" onclick="preViewHtml5('${imgWidth!}','${imgHeight!}','${imgFile!}');" alt="預覽">預覽</p>
                			<#else>
	                			<img src="<@s.url value="/" />${imgFile!}" >
	                			<p class="fancy adinf" onclick="preViewImg('${imgFile!}','${imgWidth!}','${imgHeight!}');" alt="預覽">預覽</p>
                			</#if>
                		</div>
                		<ul>
                			<li><i>名稱</i><b>${imgTitle!}</b></li>
							<input type="hidden" id="adDetailTitleSeq" name="adDetailTitleSeq" value="${adDetailTitleSeq!}">
                			<li class="yes"><i>尺寸</i><b>${imgWidth!} x ${imgHeight!}</b></li>
                			<li class="yes"><i>大小</i><b>${imgSize!}</b></li>
                			<li class="yes"><i>格式</i><b>${imgTypeName!}</b></li>
                		</ul>
                	</li>
                </ul>
            </div>
            <!--上傳訊息end-->
<!-- adTmg end -->
		</div>
		<input type="hidden" id="saveAndNew" name="saveAndNew" value="">
		<input type="hidden" id="ulTmpName" name="ulTmpName" value="mpjxRl0rewSuBhH3madQgOsPImwqLs">
		<input type="hidden" id="imgFile" name="imgFile" value="">
	</form>
	<iframe style="display:none;" name='hidden_frame' id="hidden_frame" ></iframe>
	<iframe id="uploadIMG" name="uploadIMG" style="display:none;height:150px;width:600px"></iframe>
	<iframe id="doAdd" name="doAdd" style="display:none;height:150px;width:600px"></iframe>
</div>

<!-- 預覽畫面 -->
<div style="display:none;width:70%;"  id="preViewImgDiv" >
	<div style="margin:0px auto;text-align:center; background:url(03.jpg) no-repeat;height:1617px;background-position:center top;"> 
		<div id="preDiv" name="preDiv"></div>
	</div>
</div>

<div style="display:none;width:70%;"  id="preViewImgDiv" >
	<div style="margin:0px auto;text-align:center; background:url(03.jpg) no-repeat;height:1617px;background-position:center top;"> 
		<div id="sizeDiv" name="sizeDiv">
		
		</div>
	</div>
</div>

<#if pfbSizeList?exists>
<div style="display:none;"  id="approveSizeDiv">
	<div class="noticepop" style="width:auto;"><h4>廣告圖片支援規格查詢</h4><div>
    <table width="90%" cellspacing="1" cellpadding="0" border="0" class="tb02" style="margin:10px auto;line-height:18px;">
        <tbody>
            <tr>
                <th height="20">格式</th>
                <td>
                 JPG, PNG
                </td>
            </tr>
            <tr>
                <th height="20">大小上限</th>
                <td>
                 150 KB 
              </td>
            </tr>
            <tr>
              <th height="20">尺寸</th>
              <td>
               <div id="adSizeDiv" style="height:200px;overflow:auto;">
              <#list pfbSizeList as pfbSizeVO>
	 			<p>${pfbSizeVO.width!} x ${pfbSizeVO.height!}<br></p>
			  </#list>
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
</#if>

<div id="preview"></div>
