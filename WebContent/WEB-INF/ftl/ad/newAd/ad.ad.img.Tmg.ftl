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
							<input type="hidden" id="adDetailName" name="adDetailName" value="廣告連結網址">
							<input type="hidden" id="adDetailContent" name="adDetailContent">
							<input type="text" class="inputPlaceholderTmg" id="adLinkURL" name="adLinkURL" style="width:96%;" placeholder="show.pchome.com.tw" maxlength="1024"><br>
							<span id="chkLinkURL" name="chkLinkURL" style="color:red"></span><span style="float:right" id="spanAdLinkURL">已輸入0字，剩1024字</span>
						</td>
					</tr>
                    <tr>
						<th height="35" style="width:12%"><span class="t_s02">* </span>廣告圖片<br>
							<a id="errAdImg" name="errAdImg" style="cursor: pointer;" onclick="approveSize('approveSizeDiv');">支援規格查詢</a>
							<#if adType == '0' || adType == '2' >
							<br/><a id="errAdImg" name="errAdImg" style="cursor: pointer;" onclick="approveSize('html5SizeDiv');">html5規格查詢</a>
							</#if>
						</th>
						<td style="background:#f9f9f9;">
							<span class="imgar" style="width:100%;">
                            <!--上傳圖片start-->
                            <div style="float:left;text-align:left;line-height:30px;color:#000"> 
                                <input type="hidden" id="adDetailID" name="adDetailID" value="img">
                                <input type="hidden" id="adDetailName" name="adDetailName" value="廣告圖片">
                                <input type="hidden" id="adDetailContent" name="adDetailContent" value="">
   	  							<input type="hidden" id="adGroupSeq" name="adGroupSeq" value="${adGroupSeq}">
   	  							<input type="button" id="fileButton" name="fileButton" value="瀏覽檔案" onclick="fileLoad()" >
                                <input id="fileupload" type="file"  name="fileupload" multiple ><!--onchange="createImgDom(this)"-->
                                <span id="chkFile" name="chkFile" style="color:red;size:5"></span><br /> <div>已上傳 <div id="fileUploadIndex" style="display:inline;">0</div>/<div id="fileUploadSize" style="display:inline;">0</div> <div id="finalCount"  style="display:inline;color:blue;"></div></div>									
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

<#if channelMobileSizeList?exists>
<div style="display:none;"  id="approveSizeDiv">
	<div class="noticepop" style="width:auto;"><h4>廣告圖片支援規格查詢</h4><div>
    <table width="90%" cellspacing="1" cellpadding="0" border="0" class="tb02" style="margin:10px auto;line-height:18px;">
        <tbody>
            <tr>
                <th height="20">格式</th>
                <td>
                 JPG, PNG, GIF
                </td>
            </tr>
            <tr>
                <th height="20">大小上限</th>
                <td>
                 180 KB 
              </td>
            </tr>
            <tr>
              <th height="20">尺寸</th>
              <td>
               <div id="adSizeDiv" style="height:200px;overflow:auto;">
                 <div>
                 	<#if adType == '0' || adType == '1' >
                 	<div style="color:#ff3300;width:230px;float:left;"><b>僅支援搜尋廣告</b></div>
             		<#if adDevice == '0' || adDevice == '1' >
             		<div style="width:110px;float:left;" >
             			<#if searchPCSizeList?size != 0>
	                 	<#list searchPCSizeList as searchPCSize>
				 			<p>${searchPCSize.width!} x ${searchPCSize.height!}(電腦)</p>
						</#list>
						</#if>
					</div>
					</#if>
					<#if adDevice == '0' || adDevice == '2' >
					<div style="width:120px;float:left;" >
						<#if searchMobileSizeList?size != 0>
	                 	<#list searchMobileSizeList as searchMobileSize>
				 			<p>${searchMobileSize.width!} x ${searchMobileSize.height!}(行動裝置)</p>
						</#list>
						</#if>
					</div>
					</#if>
                 	</#if>
                 </div>
                 <div>
                 	<#if adType == '0' || adType == '2' >
                 	<div style="color:#ff3300;width:230px;float:left;"><b>僅支援聯播網廣告</b></div>
             		<#if adDevice == '0' || adDevice == '1' >
             		<div style="width:110px;float:left;">
             			<#if channelPCSizeList?size != 0>
	                 	<#list channelPCSizeList as channelPCSize>
				 			<p><#if channelPCSize.width?length == 2 >${channelPCSize.width!
				 			}<#else>${channelPCSize.width!
				 			}</#if> x <#if channelPCSize.height?length == 2 >${channelPCSize.height!
				 			} &nbsp;<#else>${channelPCSize.height!}</#if>(電腦)</p>
						</#list>
						</#if>
					</div>
					</#if>
					<#if adDevice == '0' || adDevice == '2' >
					<div style="width:120px;float:left;">
						<#if channelMobileSizeList?size != 0>
	                 	<#list channelMobileSizeList as channelMobileSize>
				 			<p>${channelMobileSize.width!} x ${channelMobileSize.height!}(行動裝置)</p>
						</#list>
						</#if>
					</div>
					</#if>
                 	</#if>
                 </div>
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

<div style="display:none;"  id="html5SizeDiv">
	<div class="noticepop" style="width:auto;"><h4>HTML5規格查詢</h4><div>
	<div style="height:400px;overflow:auto;">
    <table width="90%" cellspacing="1" cellpadding="0" border="0" class="tb02" style="margin:10px auto;line-height:18px;">
        <tbody>
            <tr>
              <th height="20">html5素材呎吋</th>
              <td>
               	<div id="adHtml5SizeDiv">
	                 <div>
	                 	<#if adType == '0' || adType == '2' >
	             		<#if adDevice == '0' || adDevice == '1' >
		             		<div style="width:110px;float:left;">
		             			<#if channelPCSizeList?size != 0>
			                 	<#list channelPCSizeList as channelPCSize>
						 			<p><#if channelPCSize.width?length == 2 >${channelPCSize.width!
						 			}<#else>${channelPCSize.width!
						 			}</#if> x <#if channelPCSize.height?length == 2 >${channelPCSize.height!
						 			} &nbsp;<#else>${channelPCSize.height!}</#if>(電腦)</p>
								</#list>
								</#if>
							</div>
							</#if>
							<#if adDevice == '0' || adDevice == '2' >
							<div style="width:120px;float:left;">
								<#if channelMobileSizeList?size != 0>
			                 	<#list channelMobileSizeList as channelMobileSize>
						 			<p>${channelMobileSize.width!} x ${channelMobileSize.height!}(行動裝置)</p>
								</#list>
								</#if>
							</div>
						</#if>
	                 	</#if>
	                 </div>
			   	</div>
              </td>
            </tr>
            <tr>
            	<th>規範</th>
                <td>
                	HTML5 廣告必須包含：<br/>
					&lt;!DOCTYPE html&gt; 宣告<br/>
					&lt;html&gt; 代碼<br/>
					&lt;body&gt; 代碼<br/>
					&lt;head&gt; 代碼內的廣告格式大小中繼標記，例如：<br/>
					&lt;meta name="ad.size" content="width=300,height=250"&gt;<br/>
					明確的結束代碼 (不可使用內嵌結束代碼)：<br/>
					有效結束代碼範例：&lt;path>&lt;/path&gt;<br/>
					無效結束代碼範例：&lt;path&gt; 或 &lt;path /&gt;<br/>
					另外，您也可以將內嵌 SVG 解壓縮到個別檔案中
                </td>
            </tr>
            <tr>
            	<th>檔案規格</th>
                <td>
                	1.上傳檔案規格:請將所有素材及相關的程式碼，壓縮成一個完整的zip壓縮檔後提供。壓縮檔內，主要播放廣告的HTML檔名請設定為index.html 
					(請減少zip檔中的檔案數量請移除html中多餘的字元及空格, 並請依據使用需求壓縮圖片大小和影片大小)<br/>
					2.圖檔規格 jpg、gif
                </td>
            </tr>	
            <tr>
            	<th>聲音</th>
                <td>
                	載入廣告時不能自動播放聲音，需手動開啟聲音開關
                </td>
            </tr>
            <tr>
            	<th>外部檔案</th>
                <td>
                	外部檔案（代碼庫、web字體…等等）僅能PChome認證過的3rd Party Ad Servers呼叫，且會被視為初始載入的一部份。因為這會影響廣告載入的性能表現。而由使用者互動而載入的檔案不會被算在初始載入的檔案大小。
                </td>
            </tr>
            <tr>
            	<th>安全性審核</th>
                <td>
                	不可使用跨domain載入的檔案。<br/>
                	1. 不可使用.json的檔案。 <br/>
					2. 不建議使用jQuery的檔案。因為於行動裝置上會有性能不佳的情況。不必要的檔案k數：大多數的函數庫功能是不需要的。
                </td>
            </tr>
            <tr>
            	<th>離線觀看</th>
                <td>
                	開啟此檔案時，即使在未連上網路的狀況下，也能正常觀看素材。
                </td>
            </tr>
            <tr>
            	<th>字體需求</th>
                <td>
                	一些像是外部庫或是web字體的檔案如果有需要，可以從別的位置載入。然而，總檔案大小應包括所有壓縮的素材，且外部呼叫的檔案也應算在初始檔案大小。
                </td>
            </tr>
            <tr>
            	<th>附註</th>
                <td>
                	1.HTML5瀏覽器支援：HTML5支援絕大部份瀏覽器。但為了讓舊的瀏覽器上也能有正確的呈現，請照以下說明設置CSS display屬性。
					如下<br/>
					header, section, footer, aside, nav, main, article, figure {display: block;}<br/>
					2.注意事項，非 ASCII 字元必須使用 UTF-8<br/>
					3.上傳 .zip 資料夾時，最多可以夾帶 40 個檔案<br/>
					4.所有素材內含的圖像/影音/語法都必須經過PChome審核
                </td>
            </tr>					
        </tbody>
    </table>
    </div>
    <center><input onclick="closeBtn();" class="popbtn" type="button" value="關閉"></center>
    </div>
    <a href="#" onclick="closeBtn();" class="popclose">關閉</a>
   </div>
</div>

<div id="preview"></div>
