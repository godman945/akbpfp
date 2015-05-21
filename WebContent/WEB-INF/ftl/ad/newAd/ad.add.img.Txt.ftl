<#assign s=JspTaglibs["/struts-tags"]>

	<div class="cont">
    <form method="post" id="modifyForm" name="modifyForm" enctype="multipart/form-data" action="doAdAdAddTmg.html">
		<h1 class="adtitle">廣告：日本Marukan《貓樂園貓跳台CT-183》 > 分類：寵物用品</h1>
		<h2>
			<div class="cal">帳戶名稱：kate001</div>
			<img vspace="12" hspace="2" align="absmiddle" src="img/iconcr.gif">新增廣告
		</h2>
		<div class="steps" style="background:none;">輸入廣告基本設定 &gt; 建立分類及出價  &gt; <b>製作廣告及關鍵字設定</b>  &gt; 廣告完成 </div>
		<ul class="admenuul">
            <li class="m01"><a href="#" class="active">圖像廣告</a></li>
            <li class="m02"><a href="#">圖文廣告</a></li>
            <li class="m03"><a href="#">影音廣告</a></li>
        </ul>
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
						<th height="35" style="width:12%"><span class="t_s02">* </span>廣告圖片<br><a name="errAdImg">支援規格查詢</a></th>
						<td style="background:#f9f9f9;">
							<span class="imgar" style="width:100%;">
                            <!--上傳圖片start-->
                            <div style="float:left;text-align:left;line-height:30px;color:#000"> 
                                <input type="hidden" id="adDetailID" name="adDetailID" value="img">
                                <input type="hidden" id="adDetailName" name="adDetailName" value="廣告圖片">
                                <input type="hidden" id="adDetailContent" name="adDetailContent" value="">
                                <input type="file" id="uploadFile" name="uploadFile" onchange="previewImage(this)">
                                <span id="chkFile" name="chkFile" style="color:red;size:5"></span><br />
                                已上傳 0/100									
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
                        <a class="addel" href="#">丟</a> 
                        <em>錯誤的檔案類型!</em>
                        <ul>
                        <li class="yes"><i>尺寸</i><b>300 x 100</b></li>
                        <li class="name"><i>檔名</i><b>aa-bbaga5-貓dgfgf貓你過的好不好真可愛好想威威15313202gd6泥好泥好嗎1465131lsgsba-261301ehdk.psd</b></li>
                        <li class="yes"><i>大小</i><b>100KB</b></li>        
                        </ul> 
                        <div class="adboxdv">
                        <span><i>說明：</i>支援的檔案類型JPG、PNG</span>
                        <span class="adinf">系統無法上傳檔案!</span>  
                        </div>
                    </li>
                    <!--上傳失敗end-->
                    <!--上傳成功start-->
                    <li class="okbox">    
                        <div class="adboxdv">
                        <img src="img/300250.jpg">
                        <a class="fancy adinf" href="#" alt="預覽">預覽</a>
                        </div>
                        <ul>
                        <li><i>尺寸</i><b>300 x 100</b></li>
                        <li><i>大小</i><b>100KB</b></li>
                        <li><i>格式</i><b>PNG</b></li>
                        </ul>
                        <a class="addel" href="#">丟</a>   
                    </li>
                    <!--上傳成功end-->
                    <!--上傳失敗start-->
                    <li class="failbox">    
                        <a class="addel" href="#">丟</a> 
                        <em>檔案過大!</em>
                        <ul>
                        <li class="yes"><i>尺寸</i><b>604 x 480</b></li>
                        <li class="name"><i>檔名</i><b>pic_883982_558443.jpg</b></li>
                        <li class="no"><i>大小</i><b>128500KB</b></li>        
                        </ul> 
                        <div class="adboxdv">
                        <span><i>說明：</i>檔案大小上限150KB</span>
                        <span class="adinf">系統無法上傳檔案!</span>  
                        </div>
                    </li>
                    <!--上傳失敗end-->
                    <!--上傳失敗start-->
                    <li class="failbox">    
                        <a class="addel" href="#">丟</a> 
                        <em>錯誤的尺寸!</em>
                        <ul>
                        <li class="no"><i>尺寸</i><b>88522 x 99999</b></li>
                        <li class="name"><i>檔名</i><b>lsgsba-261.psd</b></li>
                        <li class="yes"><i>大小</i><b>100KB</b></li>        
                        </ul> 
                        <div class="adboxdv">
                        <span><i>說明：</i>請確定您的廣告採用了我們支援的大小和格式，然後再次上傳<a href="#">支援的尺寸和格式</a></span>
                        <span class="adinf">系統無法上傳檔案!</span>  
                        </div>
                    </li>
                    <!--上傳失敗end-->
                    <!--上傳失敗start-->
                    <li class="failbox">    
                        <a class="addel" href="#">丟</a> 
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
                    <!--上傳成功start-->    
                    <li class="okbox">    
                        <div class="adboxdv">
                        <img src="img/420114.jpg">
                        <a class="fancy adinf" href="#" alt="預覽">預覽</a></div>
                        <ul>
                        <li><i>尺寸</i><b>125 x 125</b></li>
                        <li><i>大小</i><b>100KB</b></li>
                        <li><i>格式</i><b>PNG</b></li>
                        </ul>
                        <a class="addel" href="#">丟</a>   
                    </li>
                    <!--上傳成功end-->
                    <!--上傳成功start-->  
                    <li class="okbox">    
                        <div class="adboxdv">
                        <img src="img/300250.jpg">
                        <a class="fancy adinf" href="#" alt="預覽">預覽</a></div>
                        <ul>
                        <li><i>尺寸</i><b>300 x 100</b></li>
                        <li><i>大小</i><b>100KB</b></li>
                        <li><i>格式</i><b>PNG</b></li>
                        </ul>
                        <a class="addel" href="#">丟</a>   
                    </li>
                    <!--上傳成功end-->
                    <!--上傳成功start-->  
                    <li class="okbox">    
                        <div class="adboxdv">
                        <img src="img/158234.jpg">
                        <a class="fancy adinf" href="#" alt="預覽">預覽</a></div>
                        <ul>
                        <li><i>尺寸</i><b>300 x 100</b></li>
                        <li><i>大小</i><b>100KB</b></li>
                        <li><i>格式</i><b>PNG</b></li>
                        </ul>
                        <a class="addel" href="#">丟</a>   
                    </li>
                    <!--上傳成功end-->
                    <!--上傳成功start-->  
                    <li class="okbox">    
                        <div class="adboxdv">
                        <img src="img/120120.jpg">
                        <a class="fancy adinf" href="#" alt="預覽">預覽</a></div>
                        <ul>
                        <li><i>尺寸</i><b>300 x 100</b></li>
                        <li><i>大小</i><b>100KB</b></li>
                        <li><i>格式</i><b>PNG</b></li>
                        </ul>
                        <a class="addel" href="#">丟</a>   
                    </li>
                    <!--上傳成功end-->
                    <!--上傳成功start-->  
                    <li class="okbox">    
                        <div class="adboxdv">
                        <img src="img/300250.jpg">
                        <a class="fancy adinf" href="#" alt="預覽">預覽</a></div>
                        <ul>
                        <li><i>尺寸</i><b>300 x 100</b></li>
                        <li><i>大小</i><b>100KB</b></li>
                        <li><i>格式</i><b>PNG</b></li>
                        </ul>
                        <a class="addel" href="#">丟</a>   
                    </li>
                    <!--上傳成功end-->
                    <!--上傳失敗start-->  
 					<li class="failbox">    
                        <a class="addel" href="#">丟</a> 
                        <em>錯誤的檔案類型!</em>
                        <ul>
                        <li class="yes"><i>尺寸</i><b>300 x 100</b></li>
                        <li class="name"><i>檔名</i><b>aa-bbaga5-貓貓真可愛好想威威15313202gd6泥好泥好嗎1465131lsgsba-261301ehdk.psd</b></li>
                        <li class="yes"><i>大小</i><b>100KB</b></li>        
                        </ul> 
                        <div class="adboxdv">
                        <span><i>說明：</i>支援的檔案類型JPG、PNG</span>
                        <span class="adinf">系統無法上傳檔案!</span>  
                        </div>
                    </li> 
                    <!--上傳失敗end-->
                    <!--上傳失敗start-->                    
                    <li class="failbox">    
                        <a class="addel" href="#">丟</a> 
                        <em>錯誤的檔案類型!</em>
                        <ul>
                        <li class="yes"><i>尺寸</i><b>300 x 100</b></li>
                        <li class="name"><i>檔名</i><b>aa-bbaga5-貓貓真可愛好想威威15313202gd6泥好泥好嗎1465131lsgsba-261301ehdk.psd</b></li>
                        <li class="yes"><i>大小</i><b>100KB</b></li>        
                        </ul> 
                        <div class="adboxdv">
                        <span><i>說明：</i>支援的檔案類型JPG、PNG</span>
                        <span class="adinf">系統無法上傳檔案!</span>  
                        </div>
                    </li> 
                    <!--上傳失敗end-->
                </ul>
            </div>
<script type="text/javascript">
	    $(".fancy").live('click', function(event) {
	    	event.preventDefault();
	    	$.fancybox({
				'width'             : 300,
				'height'            : 250,
	    		'href'				:'showimg.html',
	    		'autoSize'			: true,
	    		'autoHeight'		: true,
	    		'autoScale'			: true,
	    		'transitionIn'		: 'none',
	    		'transitionOut'		: 'none',
	    		'type'				: 'iframe',
	    		'padding'			: 0,
				'overlayOpacity'    : .75,
				'overlayColor'      : '#fff',
	    		'scrolling'			: 'no'
	    	});
	    });
</script>
            <!--上傳訊息end-->
<!-- adTmg end -->

		</div>

		<div class="grtba">
			<h4>建立關鍵字</h4>
			<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
				<tbody>
					<tr>
						<th>
							<a name="errAdKeyword"></a><span class="t_s02">* </span>輸入關鍵字<br>
							<span class="t_s01">系統比對到您設定的關鍵字，您的廣告就有機會曝光</span>
						</th>
						<td>
							<input type="text" class="inputPlaceholderKeyword" data-value="請輸入關鍵字" id="adKeyword" name="adKeyword" maxlength="50" placeholder="請輸入關鍵字">
							<input type="button" id="addAdKeyword" name="addAdKeyword" value="確認">
							<input type="button" id="sugkw" name="sugkw" value="系統建議關鍵字">
							&nbsp;&nbsp;<span id="chkAdKeyword" name="chkAdKeyword" style="color:red"></span><br>
							<span class="t_s03">輸入後 按enter 或 確認鍵 來新增關鍵字；每個關鍵字限50個字，最多可設定500個關鍵字！</span><br>
							<a href="javascript:setBatch()">大量新增關鍵字</a><br>
							<!-- 批次新增關鍵字START -->
							<div id="divBatchWord" style="display:none;">
								<textarea id="batchkeywords" name="batchkeywords" rows="10" cols="50"></textarea>
								<input type="button" id="btnAddBatch" name="btnAddBatch" value="批次加入"><BR>
								<span class="t_s03">大量新增關鍵字請以小寫逗號(,)或換行，來區隔每組關鍵字。</span><br>
							</div>
							<!-- 批次新增關鍵字end -->
							<!-- 建議START -->
							<div id="divSuggestWord" class="sugestword" style="display:none;">
								<ul id="suggestWordUL">
								</ul>
							</div>
							<!-- 建議end -->
							<!-- 關鍵字START -->
							<div id="divKeyword" class="setword" style="display:block;">
								<h5>本次新增的關鍵字</h5><b id="bKW">已新增 0 個，還可輸入 <span class="t_s01">500</span> 個</b>
								<ul id="KeywordUL">
								</ul>
							</div>
							<!-- 關鍵字end -->
						</td>
					</tr>
				</tbody>
			</table>
			<div style="clear:both;height:10px"></div>
			<script type='text/javascript'>
            function show(obj, id)
            {
              var o=document.getElementById(id);
              if( o.style.display == 'none' )
              {
                o.style.display='';
                obj.innerHTML='隱藏';
              }
              else
              {
                o.style.display='none';
                obj.innerHTML='展開';
              }
            }
            </script>
			<h4>設定排除關鍵字<a onclick='show(this, "showinfdv")'  style="padding-left:5px;">隱藏</a></h4>
			<div id="showinfdv" style="">
			<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
				<tbody>
					<tr>
						<th>
							輸入排除關鍵字

							<span class="t_s01">系統比對到這些排除的關鍵字時，不會播出您的廣告</span>
						</th>
						<td>
							<input type="text" class="inputPlaceholderKeyword" data-value="請輸入排除關鍵字" id="adExcludeKeyword" name="adExcludeKeyword" maxlength="50" placeholder="請輸入排除關鍵字">
							<input type="button" id="addAdExcludeKeyword" name="addAdExcludeKeyword" value="確認">
							<span id="chkAdExcludeKeyword" name="chkAdExcludeKeyword" style="color:red"></span>
							<br>
							<span class="t_s03">輸入後 按enter 或 確認鍵 來新增要排除的關鍵字；每個要排除的關鍵字限50個字，最多可設定500個要排除的關鍵字！</span>
							<!--關鍵字START-->
							<div class="setword" style="display:block;">
								<b id="bExcludeKW">已新增 0 個，還可輸入 <span class="t_s01">500</span> 個</b>
								<ul id="ExcludeKeywordUL">
								</ul>
							</div>
							<!--關鍵字end-->
						</td>
					</tr>
				</tbody>
			</table>
            <div style="clear:both;height:10px"></div>
            </div>
			<h4>已建立的分類關鍵字</h4>
			<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
				<tbody>
					<tr>
						<th>已建立的分類關鍵字</th>
						<td>
							<select id="existKW" name="existKW" size="10" style="width:200px">
							</select>
						</td>
						<th>已建立的分類排除關鍵字</th>
						<td>
							<select id="existExcludeKW" name="existExcludeKW" size="10" style="width:200px">
							</select>
						</td>
					</tr>
				</tbody>
			</table>
		</div>


		<!-- adKeyword end -->
		<span class="t_s01">※※※ 提醒您，您的廣告將在3工作天(周一到周五)審核完成(不含例假日)，並於廣告審核完成後開始播放 ※※※</span>
		<center style="margin-top:10px;">
			<input type="button" id="cancel" value="取 消"> 
			<input type="button" id="save" value="送出審核"> 
			<!-- <input type="button" id="saveNew" value="儲存後再新增廣告"> --> 
		</center>
		<input type="hidden" id="adGroupSeq" name="adGroupSeq" value="ag_201505050001">
		<input type="hidden" id="saveAndNew" name="saveAndNew" value="">
		<input type="hidden" id="ulTmpName" name="ulTmpName" value="mpjxRl0rewSuBhH3madQgOsPImwqLs">
		<input type="hidden" id="imgFile" name="imgFile" value="">
        <input type="hidden" id="backPage" name="backPage" value="adGroupAdd.html?adGroupSeq=ag_201505050001">
	</form>
	<iframe id="uploadIMG" name="uploadIMG" style="display:none;height:150px;width:600px"></iframe>
	<iframe id="doAdd" name="doAdd" style="display:none;height:150px;width:600px"></iframe>
</div>

