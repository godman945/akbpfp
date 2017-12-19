<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>


<span class="pages"><@t.insertAttribute name="page" /></span>

<div style="clear:both;height:10px"></div>
<table id="listTable" width="100%" border="0" cellpadding="0" cellspacing="1" class="tablesorter tb01">
<thead>
  <tr>
	<th height="35">廣告明細</th>
    <th style="width:10%">狀態</th>
    <th style="width:10%">計價方式</th>
    <th style="width:10%">曝光數</th>
    <th style="width:10%"><a style="float: left; margin-top: 3px;"><img src="./html/img/question.gif" title="互動數欄位:計算不同廣告樣式所產生的主要動作次數"></a>互動數</th>
    <th style="width:10%">互動率</th>
    <th style="width:10%">單次互動費用</th>
    <th style="width:10%">千次曝光費用</th>
    <th style="width:10%">費用</th>
  </tr>
</thead>
<tbody>
<#if adLayerVO?exists>
	<#list adLayerVO as vo>
	  <tr> 	
		<td height="35" class="td02">
		<#if "IMG" == vo.adStyle  && "N" == vo.html5Tag>
 			<div class="adreportdv">
				<span class="adboxdvimg"><a href="${vo.realUrl!}" target="_blank"><img src="${vo.originalImg!}" /></a></span>
				<span class="adboxdvinf">
					<span>
						<b>${vo.title!}</b><br>
						<i>尺寸</i><b>${vo.imgWidth!} x ${vo.imgHeight!}</b><br>
						<span>${vo.showUrl!}</span><br>
						<a class="fancy" style="cursor:pointer" onclick="preview('${vo.originalImg!}')" alt="預覽">預覽</a>
					</span>
				</span>
			</div>
		<#elseif "IMG" == vo.adStyle  && "Y" == vo.html5Tag>
			<div class="adreportdv">
        		<span class="adboxdvimg">${vo.zipTitle!}</span>
	        	<span class="adboxdvinf">
			        <span>
			        	<b>${vo.title!}</b><br>
			            <i>尺寸</i><b>${vo.imgWidth!} x ${vo.imgHeight!}</b><br>
			            <span>${vo.showUrl!}</span><br>
			            <a class="fancy" style="cursor:pointer" onclick="preViewHtml5('${vo.imgWidth!}','${vo.imgHeight!}','${vo.originalImg!}','${vo.realUrl!}')" alt="預覽">預覽</a>
		            </span>
	        	</span>
        	</div>
		<#elseif "VIDEO" == vo.adStyle>
				<div class="adreportdv">
					<iframe class="akb_iframe" scrolling="no" frameborder="0" marginwidth="0" marginheight="0" vspace="0" hspace="0" id="pchome8044_ad_frame1" width="${vo.adWidth!}" height="${vo.adHeight!}" allowtransparency="true" allowfullscreen="true" src="adVideoModel.html?adPreviewVideoURL=${vo.videoUrl!}&adPreviewVideoBgImg=<#if vo.img = 'img/public/na.gif" style="display:none'><#else>http://showstg.pchome.com.tw/pfp/${vo.img!}</#if>&realUrl=${vo.realUrl!}"></iframe>
					<span class="adboxdvinf"><div style=" text-align: left; line-height: 20px; padding: 10px; ">${vo.name!}<br>${vo.adWidth!}x${vo.adHeight!}<br>00:${vo.adVideoSec!}<br>
				<a href="#" target="_blank">${adVideoPerformanceReportVO.realUrl!}</a><br>
			</div></span>
				</div>
			<#else>
				<span>
					<iframe height="120" width="350" src="adModel.html?adNo=${vo.seq!}&tproNo=${vo.templateNo!}" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" align="ceneter" class="akb_iframe"></iframe>
					</iframe>
				</span>
		</#if>
		</td>
	    <td class="td03">${vo.statusChName!}</td>
	    <td class="td03">${vo.adPriceType!}</td>
	    <td class="td01">${vo.pv?string('#,###')!}</td>
	    <td class="td01">${vo.clk?string('#,###')!}</td>	  
	    <td class="td01">${vo.clkRate?string('#.##')!}%</td>
	    <td class="td01">NT$ ${vo.avgClkCost?number?string('#,###.##')!}</td>
	    <td class="td01">NT$ ${vo.thousandsCost?number?string('#,###.##')!}</td>
	    <td class="td01">NT$ ${vo.clkCost?number?string('#,###.##')!}</td>
	  </tr> 		
	</#list>
<#else>
	<tr>
		<td colspan="8">
			沒有廣告成效資訊
		</td>
	</tr>
</#if>
</tbody>	
</table>

