<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<div class="cont">
    <form id="formImg" action=""  method="post"  target="hidden_frame" enctype="multipart/form-data">
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
							<input type="hidden" id="adDetailName" name="adDetailName" value="廣告連結網址">
							<input type="hidden" id="adDetailContent" name="adDetailContent">
							<input type="text" class="inputPlaceholderTmg" id="adLinkURL" name="adLinkURL" style="width:96%;" placeholder="show.pchome.com.tw"  maxlength=""><br>
							<span id="chkLinkURL" name="chkLinkURL" style="color:red"></span><span style="float:right" id="spanAdLinkURL">已輸入0字，剩1024字</span>
						</td>
					</tr>
                    <tr>
						<th height="35" style="width:12%"><span class="t_s02">* </span>廣告圖片<br><a href="#" name="errAdImg" onclick="approveSize();">支援規格查詢</a></th>
						<td style="background:#f9f9f9;">
							<span class="imgar" style="width:100%;">
                            <!--上傳圖片start-->
                            <div style="float:left;text-align:left;line-height:30px;color:#000"> 
                                <input type="hidden" id="adDetailID" name="adDetailID" value="img">
                                <input type="hidden" id="adDetailName" name="adDetailName" value="廣告圖片">
                                <input type="hidden" id="adDetailContent" name="adDetailContent" value="">
   	  							<input type="hidden" id="adGroupSeq" name="adGroupSeq" value="${adGroupSeq}">
                                <input id="fileupload" type="file"  name="fileupload" multiple ><!--onchange="createImgDom(this)"-->
                                <span id="chkFile" name="chkFile" style="color:red;size:5"></span><br /> <div>已上傳 <div id="fileUploadIndex" style="display:inline;">0</div>/<div id="fileUploadSize" style="display:inline;">0</div></div>									
                            </div>
                            <!--上傳圖片end-->
						  </span>
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
