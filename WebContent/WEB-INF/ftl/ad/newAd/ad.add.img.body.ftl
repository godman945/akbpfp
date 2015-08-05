<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<@t.insertAttribute name="includeJs" />
<div class="cont">
		<h1 class="adtitle">廣告：${adActionName!} > 分類：${adGroupName!}</h1>
		<h2>
			<div class="cal">帳戶名稱：${customer_info_title!}</div>
			<img vspace="12" hspace="2" align="absmiddle" src="<@s.url value="/" />html/img/iconcr.gif">新增廣告
		</h2>
		<div class="steps">輸入廣告基本設定 &gt; 建立分類及出價  &gt; <b>製作廣告及關鍵字設定</b>  &gt; 廣告完成 </div>
		<ul class="admenuul">
            <li class="m01"><a href="adAdImg.html?adGroupSeq=${adGroupSeq!}" class="active" class="active">圖像廣告</a></li>
            <li class="m02"><a href="adAdAdd.html?adGroupSeq=${adGroupSeq!}">圖文廣告</a></li>
            <!-- <li class="m03"><a href="#">影音廣告</a></li>-->
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
							<input type="radio" id="adStyle" name="adStyle" value="TXT" onclick="setAdStyle(this.value);" <#if adStyle == "TXT">checked</#if>>文字廣告
							<input type="radio" id="adStyle" name="adStyle" value="TMG" onclick="setAdStyle(this.value);" <#if adStyle == "TMG">checked</#if>>圖文廣告
						</td>
					</tr>
				</tbody>
			</table>

<#if adStyle == "TXT">
<!-- adTxt start -->
<@t.insertAttribute name="adTxt" />
<!-- adTxt end -->
<#elseif adStyle == "TMG">
<!-- adTmg start -->
<@t.insertAttribute name="adTmg" />
<!-- adTmg end -->
</#if>
		</div>

		<#if adStyle == "TXT" || adStyle == "TMG">
		<!-- adKeyword start -->
		<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adKeywordAdd.js" ></script>
		<@t.insertAttribute name="adKeyword" />
		<!-- adKeyword end -->
		</#if>
		<span class="t_s01">※※※ 提醒您，您的廣告將在3工作天(周一到周五)審核完成(不含例假日)，並於廣告審核完成後開始播放 ※※※</span>
		<center style="margin-top:10px;">
			<input type="button" id="cancel"  onclick="cancerSubmit();" value="取 消"> 
			<input type="button" id="submitBtn" onclick="multipartImgUuploadSubmit();" value="送出審核"> 
			<!-- <input type="button" id="saveNew" value="儲存後再新增廣告"> --> 
		</center>
		<input type="hidden" id="adGroupSeq" name="adGroupSeq" value="${adGroupSeq!}">
		<input type="hidden" id="saveAndNew" name="saveAndNew" value="${saveAndNew!}">
		<input type="hidden" id="ulTmpName" name="ulTmpName" value="${ulTmpName!}">
		<input type="hidden" id="imgFile" name="imgFile" value="${imgFile!}">
        <input type="hidden" id="backPage" name="backPage" value="${backPage!}">
	</form>
	<iframe id="uploadIMG" name="uploadIMG" style="display:none;height:150px;width:600px"></iframe>
	<iframe id="doAdd" name="doAdd" style="display:none;height:150px;width:600px"></iframe>
</div>
<input type="hidden" id="messageId" value="${message!!}">



