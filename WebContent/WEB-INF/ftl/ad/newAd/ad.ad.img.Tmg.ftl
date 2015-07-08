<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<div class="cont">
    <form method="post" id="modifyForm" name="modifyForm" enctype="multipart/form-data" action="doAdAdAddTmg.html">
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
			<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
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
						<th height="35" style="width:12%"><span class="t_s02">* </span>廣告圖片<br><a name="errAdImg" onclick="approveSize();">支援規格查詢</a></th>
						<td style="background:#f9f9f9;">
							<span class="imgar" style="width:100%;">
                            <!--上傳圖片start-->
                            <div style="float:left;text-align:left;line-height:30px;color:#000"> 
                                <input type="hidden" id="adDetailID" name="adDetailID" value="img">
                                <input type="hidden" id="adDetailName" name="adDetailName" value="廣告圖片">
                                <input type="hidden" id="adDetailContent" name="adDetailContent" value="">
                                <!--<input type="file" id="uploadFile" name="uploadFile" onchange="previewImage(this)">-->
                                <input id="fileupload" type="file" name="files[]" multiple onchange="createPreViewArea(this)">
                                <span id="chkFile" name="chkFile" style="color:red;size:5"></span><br />已上傳 0/100									
                            </div>
                            <!--上傳圖片end-->
						  </span>
					  </td>
					</tr>					
				</tbody>
			</table>
            <!--上傳訊息start 預設是隱藏，有資料才顯示-->
            <div class="aduplodulbg" style="" >
                <ul class="aduplodul">
                    <!--上傳失敗start-->
                    <li class="failbox">    
                        <p class="addel"  onclick="removeImg(this);"></p> 
                        <em>上傳失敗!</em>
                        <ul>
                        <li class="yes"><i>尺寸</i><b>300 x 100</b></li>
                        <li class="name"><i>檔名</i><b>ehdk.PNG</b></li>
                        <li class="yes"><i>大小</i><b>50KB</b></li>        
                        </ul> 
                        <div class="adboxdv">
                        <span><i>說明：</i>檔案空白</span>
                        <span class="adinf">系統無法上傳檔案!</span>  
                        </div>
                    </li>
                    <!--上傳失敗end-->
                    
            </div>
            <!--上傳訊息end-->
<!-- adTmg end -->
		</div>
		
		<input type="hidden" id="adGroupSeq" name="adGroupSeq" value="ag_201505050001">
		<input type="hidden" id="saveAndNew" name="saveAndNew" value="">
		<input type="hidden" id="ulTmpName" name="ulTmpName" value="mpjxRl0rewSuBhH3madQgOsPImwqLs">
		<input type="hidden" id="imgFile" name="imgFile" value="">
        <input type="hidden" id="backPage" name="backPage" value="adGroupAdd.html?adGroupSeq=ag_201505050001">
	</form>
	<iframe id="uploadIMG" name="uploadIMG" style="display:none;height:150px;width:600px"></iframe>
	<iframe id="doAdd" name="doAdd" style="display:none;height:150px;width:600px"></iframe>
</div>

<!-- 預覽畫面
<div style="display:;width:70%;"  id="preViewImgDiv" >
	<div style="margin:0px auto;text-align:center; background:url(03.jpg) no-repeat;height:1617px;background-position:center top;"> 
		<div id="alex99" name="alex99"></div>
	</div>
</div>
 -->