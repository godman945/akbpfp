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
            <li class="m01"><a onClick="return(false)" style="background-position: 0px 0px;" >圖像廣告</a></li>
            <!-- <li class="m03"><a href="#">影音廣告</a></li>-->
        </ul>
        <div class="grtba" style="padding:1px 10px;margin-bottom:10px;">
			<h4 >廣告狀態：${adStatusDesc!}
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

<#if adStyle == "TXT">
<!-- adTxt start -->
<@t.insertAttribute name="adTxt" />
<!-- adTxt end -->
<#elseif adStyle == "IMG">
<!-- adTmg start -->
<@t.insertAttribute name="adTmg" />
<!-- adTmg end -->
</#if>
		</div>

		<#if adStyle == "TXT" || adStyle == "TMG" || adStyle == "IMG">
		<!-- adKeyword start -->
		<div id=keywordBody>
		<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adKeywordAdd.js" ></script>
		<@t.insertAttribute name="adKeyword" />
		</div>
		<!-- adKeyword end -->
		</#if>
		<span class="t_s01">※※※ 提醒您，您的廣告將在3工作天(周一到周五)審核完成(不含例假日)，並於廣告審核完成後開始播放 ※※※</span>
		<center style="margin-top:10px;">
			<input type="button" id="cancel"  onclick="cancerSubmit();" value="取 消"> 
			<#if adStatus != 6>
			<input type="button" id="submitBtn" onclick="multipartImgUuploadSubmit();" value="送出審核">
			</#if> 
		</center>
		<input type="hidden" id="adSeq" name="adSeq" value="${adSeq!}">
		<input type="hidden" id="ulTmpName" name="ulTmpName" value="${ulTmpName!}">
		<input type="hidden" id="imgFile" name="imgFile" value="${imgFile!}">
		<input type="hidden" id="adStatus" name="adStatus" value="${adStatus!}">
	</form>
	<iframe id="uploadIMG" name="uploadIMG" style="display:none;height:150px;width:600px"></iframe>
	<iframe id="doAdd" name="doAdd" style="display:none;height:150px;width:600px"></iframe>
</div>
<input type="hidden" id="messageId" value="${message!}">



