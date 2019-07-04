<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>


<#-- 隱藏欄位 -->
<input type="hidden" id="page" name="page" value="${page!}"> <#-- 目前頁數 -->
<input type="hidden" id="totalPage" name="totalPage" value="${totalPage!}"> <#-- 總頁數 -->
<#-- 隱藏欄位 結束 -->

<div class="prodtable-box txt-noselect">

	<div id="table-listing">
		<div class="table-scrollable floatingscroll transition-all">
			<table class="table" data-fixed-columns="2" cellpadding="0" cellspacing="0">
				<thead class="header">                                        
					<tr class="txt-row header">

						<#-- th下拉選單 選取狀態: data-select="true" -->
						<th data-info-name="" class="col-w40">狀態</th>
						<th data-info-name="" class="col-w360">廣告明細</th>

                        <th data-info-name="adType" class="col-w100">
                            <div class="sortbx-selectTH pos-relative txt-left">
                                <span class="sort-tit pos-relative">播放類型</span>
                                <ul class="sort-item pos-absolute pos-left">
                                    <li data-select="true" data-where="adType-all">全部</li>
                                    <li data-select="" data-where="adType-2">聯播網廣告</li>
                                    <li data-select="" data-where="adType-1">搜尋廣告</li>
                                </ul>
                            </div>
                        </th>
                        <th data-info-name="adOperatingRule" class="col-w100">
                            <div class="sortbx-selectTH pos-relative txt-left">
                                <span class="sort-tit pos-relative">廣告樣式</span>
                                <ul class="sort-item pos-absolute pos-left">
                                    <li data-select="true" data-where="adOperatingRule-all">全部</li>
                                    <li data-select="" data-where="adOperatingRule-MEDIA">多媒體廣告</li>
                                    <li data-select="" data-where="adOperatingRule-VIDEO">影音廣告</li>
                                    <li data-select="" data-where="adOperatingRule-PROD">商品廣告</li>
                                </ul>
                            </div>
                        </th>
                        <th data-info-name="adClkPriceType" class="col-w120">
                            <div class="sortbx-selectTH pos-relative txt-left">
                                <span class="sort-tit pos-relative">計價方式</span>
                                <ul class="sort-item pos-absolute pos-left">
                                    <li data-select="true" data-where="adClkPriceType-all">全部</li>
                                    <li data-select="" data-where="adClkPriceType-CPV">單次收視CPV</li>
                                    <li data-select="" data-where="adClkPriceType-CPM">千次曝光CPM</li>
                                    <li data-select="" data-where="adClkPriceType-CPC">單次點擊CPC</li>
                                </ul>
                            </div>
                        </th>
						
						<th data-info-name="" class="col-w70">
                            <div class="sortbx-selectTH pos-relative txt-left">
                                <span class="sort-tit pos-relative">裝置</span>
                                <ul class="sort-item pos-absolute pos-left">
                                    <li data-select="true" data-where="adDevice-all">全部</li>
                                    <li data-select="" data-where="adDevice-mobile">行動</li>
                                    <li data-select="" data-where="adDevice-PC">電腦</li>
                                </ul>
                            </div>
                        </th>
						<th data-info-name="" class="col-w120">
							<div class="sortbx-selectTH pos-relative txt-right">
								<span class="sort-tit pos-relative">曝光數</span>
								<ul class="sort-item pos-absolute pos-right">
									<li data-select="" data-sort="adPvSum-DESC">從大到小</li>
									<li data-select="" data-sort="adPvSum-ASC">從小到大</li>
								</ul>
							</div>
						</th>
						<th data-info-name="" class="col-w120">
							<div class="sortbx-selectTH pos-relative txt-right">
								<span class="sort-tit pos-relative">互動數</span>
								<ul class="sort-item pos-absolute pos-right">
									<li data-select="" data-sort="adClkSum-DESC">從大到小</li>
									<li data-select="" data-sort="adClkSum-ASC">從小到大</li>
								</ul>
							</div>
						</th>
						<th data-info-name="" class="col-w120">
							<div class="sortbx-selectTH pos-relative txt-right">
								<span class="sort-tit pos-relative">互動率</span>
								<ul class="sort-item pos-absolute pos-right">
									<li data-select="" data-sort="ctr-DESC">從大到小</li>
									<li data-select="" data-sort="ctr-ASC">從小到大</li>
								</ul>
							</div>
						</th>
						<th data-info-name="" class="col-w120">                        
                            <div class="sortbx-selectTH pos-relative txt-right">
                                <span class="sort-tit pos-relative">無效點選次數</span>
                                <ul class="sort-item pos-absolute pos-right">
                                    <li data-select="" data-sort="adInvClkSum-DESC">從大到小</li>
                                    <li data-select="" data-sort="adInvClkSum-ASC">從小到大</li>
                                </ul>
                            </div>
                        </th>
						<th data-info-name="" class="col-w120">
							<div class="sortbx-selectTH pos-relative txt-right">
								<span class="sort-tit pos-relative">單次互動費用</span>
								<ul class="sort-item pos-absolute pos-right">
									<li data-select="" data-sort="avgCost-DESC">從大到小</li>
									<li data-select="" data-sort="avgCost-ASC">從小到大</li>
								</ul>
							</div>
						</th>
						<th data-info-name="" class="col-w120">
							<div class="sortbx-selectTH pos-relative txt-right">
								<span class="sort-tit pos-relative">千次曝光費用</span>
								<ul class="sort-item pos-absolute pos-right">
									<li data-select="" data-sort="kiloCost-DESC">從大到小</li>
									<li data-select="" data-sort="kiloCost-ASC">從小到大</li>
								</ul>
							</div>
						</th>
						<th data-info-name="" class="col-w120">
							<div class="sortbx-selectTH pos-relative txt-right">
								<span class="sort-tit pos-relative">費用</span>
								<ul class="sort-item pos-absolute pos-right">
									<li data-select="" data-sort="adPriceSum-DESC">從大到小</li>
									<li data-select="" data-sort="adPriceSum-ASC">從小到大</li>
								</ul>
							</div>
						</th>
						<th data-info-name="convertCount" class="col-w120">
							<div class="sortbx-selectTH pos-relative txt-right">
								<span class="sort-tit pos-relative">轉換次數</span>
								<ul class="sort-item pos-absolute pos-right">
									<li data-select="" data-sort="convertCount-DESC">從大到小</li>
									<li data-select="" data-sort="convertCount-ASC">從小到大</li>
								</ul>
							</div>
						</th>
						<th data-info-name="convertCTR" class="col-w120">
							<div class="sortbx-selectTH pos-relative txt-right">
								<span class="sort-tit pos-relative">轉換率</span>
								<ul class="sort-item pos-absolute pos-right">
									<li data-select="" data-sort="convertCTR-DESC">從大到小</li>
									<li data-select="" data-sort="convertCTR-ASC">從小到大</li>
								</ul>
							</div>
						</th>
						<th data-info-name="convertPriceCount" class="col-w120">
							<div class="sortbx-selectTH pos-relative txt-right">
								<span class="sort-tit pos-relative">總轉換價值</span>
								<ul class="sort-item pos-absolute pos-right">
									<li data-select="" data-sort="convertPriceCount-DESC">從大到小</li>
									<li data-select="" data-sort="convertPriceCount-ASC">從小到大</li>
								</ul>
							</div>
						</th>
						<th data-info-name="convertCost" class="col-w120">
							<div class="sortbx-selectTH pos-relative txt-right">
								<span class="sort-tit pos-relative">平均轉換成本</span>
								<ul class="sort-item pos-absolute pos-right">
									<li data-select="" data-sort="convertCost-DESC">從大到小</li>
									<li data-select="" data-sort="convertCost-ASC">從小到大</li>
								</ul>
							</div>
						</th>
						<th data-info-name="convertInvestmentCost" class="col-w120">
							<div class="sortbx-selectTH pos-relative txt-right">
								<span class="sort-tit pos-relative">廣告投資報酬率</span>
								<ul class="sort-item pos-absolute pos-right">
									<li data-select="" data-sort="convertInvestmentCost-DESC">從大到小</li>
									<li data-select="" data-sort="convertInvestmentCost-ASC">從小到大</li>
								</ul>
							</div>
						</th>
					</tr>
				</thead>

				<!--className 狀態 adstatus-icon：<開啟+open> <關閉+clz> -->
				<!--className 單位 %            ：data-percentage-->
				<!--className 單位 $            ：cost-dollarmark-->
				<!--className 單位 次                                   ：cumulate-number-->
				<!--className 總計                                        ：amount-event-->
				<tbody class="results">
					<#if (resultData?size > 0) >
						<#-- 每列資料 -->
						<#list resultData as row>
							<tr class="txt-row detailRow">
								<td data-info-name="" class="txt-center">
                                    <div class="w-full h-full">
                                        <u class="pos-middle adstatus-icon <#if row.adStatusOnOff>open<#else>clz</#if>"></u>
                                    </div>
                                </td>
                                <td data-info-name="">
                                    <div class="prodlist tablebox txt-table">
                                    	<!-- adStyle:${row.adStyle!} --> 
                                    	<#if row.adStyle == 'IMG'><#-- IMG 圖像廣告 -->
                                    	
                                    		<div class="hiddenVal" style="display: none;">
                                    			<input type="hidden" id="img" name="img" value="${row.img!}">
                                    			<input type="hidden" id="width" name="width" value="${row.imgWidth!}">
                                    			<input type="hidden" id="height" name="height" value="${row.imgHeight!}">
                                    		</div>
                                			<a class="preview pos-absolute" onclick="preView('${row.adStyle!}', this)">預覽</a>
                                            <div class="txt-cell prod-pic">
                                            	<#if row.html5Flag == 'Y'> <#-- html5帶公版icon -->
                                                <img src="<@s.url value="/" />html/img/report/upload-html5.png">
                                                <#else>
	                                    		<img src="${row.img!}">
	                                    		</#if>
                                            </div>
                                            <div class="txt-cell txt-left prod-detail">
                                                <h5 class="h2 txt-ellipsis" data-pdName="${row.title!}"></h5>
                                                <small>尺寸：<em data-pcadsize="${row.imgWidth!}x${row.imgHeight!}"></em></small>
                                                <small class="prodLink txt-ellipsis" data-prodlink="${row.realUrl!}"></small>
                                            </div>
                                            
                                    	<#elseIf row.adStyle == 'VIDEO'> <#-- VIDEO 影音廣告 -->
                                    	
                                            <div class="hiddenVal" style="display: none;">
                                    			<input type="hidden" id="width" name="width" value="${row.videoWidth!}">
                                    			<input type="hidden" id="height" name="height" value="${row.videoHeight!}">
                                    			<input type="hidden" id="adPreviewVideoURL" name="adPreviewVideoURL" value="${row.videoUrl!}">
                                    			<input type="hidden" id="adPreviewVideoBgImg" name="adPreviewVideoBgImg" value="${row.img!}">
                                    			<input type="hidden" id="realUrl" name="realUrl" value="${row.realUrl!}">
                                    		</div>
                                            <a class="preview pos-absolute" onclick="preView('${row.adStyle!}', this)">預覽</a>
                                            <div class="txt-cell prod-pic">
                                                <img src="<@s.url value="/" />html/img/report/videoad-icon.png">
                                            </div>
                                            <div class="txt-cell txt-left prod-detail">
                                                <h5 class="h2 txt-ellipsis" data-pdName="${row.videoName!}"></h5>
                                                <small>尺寸：<em data-pcadsize="${row.videoWidth!}x${row.videoHeight!}"></em></small>
                                                <small>時間：<em data-pcadtime="00:${row.videoSec!}"></em></small>
                                                <small class="prodLink txt-ellipsis" data-prodlink="${row.realUrl!}"></small>
                                            </div>
                                            
                                    	<#elseIf row.adStyle == 'PROD'> <#-- PROD 商品廣告 -->
                                    	
                                    		<div class="hiddenVal" style="display: none;">
                                    			<textarea style="display:none;" id="productTemplateStr" name="productTemplateStr">${row.productTemplateStr!}</textarea><#-- 所有尺寸與同尺寸不同樣板 -->
                                    			<input type="hidden" id="width" name="width" value="${row.prodAdSizeWidth!}">
                                    			<input type="hidden" id="height" name="height" value="${row.prodAdSizeHeight!}">
                                    			<#-- 以下為iframe 會用到的參數 -->
                                    			<input type="hidden" id="catalogGroupId" name="catalogGroupId" value="${row.prodGroup!}">
                                    			<input type="hidden" id="disTxtType" name="disTxtType" value="${row.disTxtType!}">
                                    			<input type="hidden" id="disBgColor" name="disBgColor" value="${row.disBgColor!}">
                                    			<input type="hidden" id="disFontColor" name="disFontColor" value="${row.disFontColor!}">
                                    			<input type="hidden" id="btnTxt" name="btnTxt" value="${row.btnTxt!}">
                                    			<input type="hidden" id="btnFontColor" name="btnFontColor" value="${row.btnFontColor!}">
                                    			<input type="hidden" id="btnBgColor" name="btnBgColor" value="${row.btnBgColor!}">
                                    			<input type="hidden" id="logoText" name="logoText" value="${row.logoText!}">
                                    			<input type="hidden" id="logoBgColor" name="logoBgColor" value="${row.logoBgColor!}">
                                    			<input type="hidden" id="logoFontColor" name="logoFontColor" value="${row.logoFontColor!}">
                                    			<input type="hidden" id="prodLogoType" name="prodLogoType" value="${row.prodRadioLogoType!}">
                                    			<input type="hidden" id="adbgType" name="adbgType" value="${row.adbgType!}">
                                    			<input type="hidden" id="imgProportiona" name="imgProportiona" value="${row.imgProportiona!}">
                                    			<input type="hidden" id="userLogoPath" name="userLogoPath" value="${row.userLogoPath!}">
                                    			<input type="hidden" id="logoType" name="logoType" value="${row.logoType!}">
                                    			<input type="hidden" id="previewTpro" name="previewTpro" value="${row.previewTpro!}">
                                    			<input type="hidden" id="saleImg" name="saleImg" value="${row.saleImg!}">
                                    			<input type="hidden" id="saleEndImg" name="saleEndImg" value="${row.saleEndImg!}">
                                    			<input type="hidden" id="posterType" name="posterType" value="${row.posterType!}">
                                    			<input type="hidden" id="realUrlEncode" name="realUrlEncode" value="${row.realUrlEncode!}">
                                    		</div>
                                    		<a class="preview pos-absolute" onclick="preView('${row.adStyle!}', this)">預覽</a>
                                            <div class="txt-cell prod-pic">
                                                <img src="<@s.url value="/" />html/img/report/style-04.svg">
                                            </div>
                                            <div class="txt-cell txt-left prod-detail">
                                                <h5 class="h2 txt-ellipsis" data-pdName="${row.adName!}"></h5>
                                                <small class="prodLink txt-ellipsis" data-prodlink="${row.realUrl!}"></small>
                                                <small class="p-t5"><a href="javascript:previewProdAdDetail('${row.customerInfoId!}', '${row.adSeq!}', 'reportExcerpt')">查看成效</a></small>
                                            </div>
                                            
                                    	<#elseIf row.adStyle == 'TMG'> <#-- TMG 圖文廣告 -->
                                    	
                                    		<div class="hiddenVal" style="display: none;">
      											<#-- 以下為iframe 會用到的參數 -->
                                    			<input type="hidden" id="adSeq" name="adSeq" value="${row.adSeq!}">
                                    			<input type="hidden" id="width" name="width" value="300">
                                    			<input type="hidden" id="height" name="height" value="250">
                                    			<input type="hidden" id="tproNo" name="tproNo" value="c_x05_tp_tpro_0001">
                                    		</div>
                                    		<a class="preview pos-absolute" onclick="preView('${row.adStyle!}', this)">預覽</a>
                                            <div class="txt-cell prod-pic">
                                                <img src="${row.img!}">
                                            </div>
                                            <div class="txt-cell txt-left prod-detail">
                                                <h5 class="h2 txt-ellipsis" data-pdName="${row.title!}"></h5>
                                                <small class="txt-ellipsis" data-prodnum="${row.content!}"></small>
                                                <#if row.salesPrice != '' && row.promotionalPrice != ''>
                                                <small class="prod-opt">
                                                    <i>原價NT$ <em class="price" data-priceorig="${row.salesPrice!}"></em></i>
                                                    <i>特價NT$ <em class="price" data-pricesell="${row.promotionalPrice!}"></em></i>
                                                </small>
                                                </#if>
                                                <small class="prodLink txt-ellipsis" data-prodlink="${row.realUrl!}"></small>
                                            </div>
                                            
                                    	</#if>
                                    </div>
                                </td>
                                
                                <td data-info-name="adType">${row.adType!}</td>
                                <td data-info-name="adOperatingRule">${row.adOperatingRule!}</td>
                                <td data-info-name="adClkPriceType">${row.adClkPriceType!}</td>
								<td data-info-name="">${row.adDevice!}</td>
								<td data-info-name="" class="txt-right"><span class="">${row.adPvSum?string("#,###,###.##")!}</span></td><!--曝光數-->
								<td data-info-name="" class="txt-right"><span class="">${row.adClkSum?string("#,###,###.##")!}</span></td><!--互動數-->
								<td data-info-name="" class="txt-right"><span class="data-percentage">${row.ctr?string("#,###,###.##")!}</span></td><!--互動率-->
								<td data-info-name="" class="txt-right"><span class="">${row.adInvClkSum?string("#,###,###.##")!}</span></td><!--無效點選次數-->
								<td data-info-name="" class="txt-right"><span class="cost-dollarmark">${row.avgCost?string("#,###,###.##")!}</span></td><!--單次互動費用-->
								<td data-info-name="" class="txt-right"><span class="cost-dollarmark">${row.kiloCost?string("#,###,###.##")!}</span></td><!--千次曝光費用-->
								<td data-info-name="" class="txt-right"><span class="cost-dollarmark">${row.adPriceSum?string("#,###,###.###")!}</span></td><!--費用-->
								<td data-info-name="convertCount" class="txt-right"><span class="">${row.convertCount?string("#,###,###.##")!}</span></td><!--轉換次數-->                                        
								<td data-info-name="convertCTR" class="txt-right"><span class="data-percentage">${row.convertCTR?string("#,###,###.##")!}</span></td><!--轉換率-->
								<td data-info-name="convertPriceCount" class="txt-right"><span class="cost-dollarmark">${row.convertPriceCount?string("#,###,###.##")!}</span></td><!--總轉換價值-->
								<td data-info-name="convertCost" class="txt-right"><span class="cost-dollarmark">${row.convertCost?string("#,###,###.##")!}</span></td><!--平均轉換成本-->
								<td data-info-name="convertInvestmentCost" class="txt-right"><span class="data-percentage">${row.convertInvestmentCost?string("#,###,###.##")!}</span></td><!--廣告投資報酬率-->
							</tr>
						</#list>
						
						<#-- 總計列資料 -->
						<#list resultSumData as rowSum>
							<tr class="txt-row summaryRow">
								<td data-info-name=""></td>
								<td data-info-name="">
									<div class="w-full h-full">
										<div class="txt-left pos-middle">
											<span class="amount-event">${rowSum.rowCount?string("#,###,###.##")!}筆廣告分類</span>
										</div>
									</div>
								</td>
                                <td data-info-name="adType"></td>
                                <td data-info-name="adOperatingRule"></td>
                                <td data-info-name="adClkPriceType"></td>
								<td data-info-name=""></td>
								<td data-info-name="" class="txt-right"><span class="">${rowSum.adPvSum?string("#,###,###.##")!}</span></td><!--曝光數-->
								<td data-info-name="" class="txt-right"><span class="">${rowSum.adClkSum?string("#,###,###.##")!}</span></td><!--互動數-->
								<td data-info-name="" class="txt-right"><span class="data-percentage">${rowSum.ctr?string("#,###,###.##")!}</span></td><!--互動率-->
								<td data-info-name="" class="txt-right"><span class="">${rowSum.adInvClkSum?string("#,###,###.##")!}</span></td><!--無效點選次數-->
								<td data-info-name="" class="txt-right"><span class="cost-dollarmark">${rowSum.avgCost?string("#,###,###.##")!}</span></td><!--單次互動費用-->
								<td data-info-name="" class="txt-right"><span class="cost-dollarmark">${rowSum.kiloCost?string("#,###,###.##")!}</span></td><!--千次曝光費用-->
								<td data-info-name="" class="txt-right"><span class="cost-dollarmark">${rowSum.adPriceSum?string("#,###,###.###")!}</span></td><!--費用-->
								<td data-info-name="convertCount" class="txt-right"><span class="">${rowSum.convertCount?string("#,###,###.##")!}</span></td><!--轉換次數-->                                        
								<td data-info-name="convertCTR" class="txt-right"><span class="data-percentage">${rowSum.convertCTR?string("#,###,###.##")!}</span></td><!--轉換率-->
								<td data-info-name="convertPriceCount" class="txt-right"><span class="cost-dollarmark">${rowSum.convertPriceCount?string("#,###,###.##")!}</span></td><!--總轉換價值-->
								<td data-info-name="convertCost" class="txt-right"><span class="cost-dollarmark">${rowSum.convertCost?string("#,###,###.##")!}</span></td><!--平均轉換成本-->
								<td data-info-name="convertInvestmentCost" class="txt-right"><span class="data-percentage">${rowSum.convertInvestmentCost?string("#,###,###.##")!}</span></td><!--廣告投資報酬率-->
							</tr>
						</#list>
					<#else>
						<tr class="txt-row">
							<td data-info-name="" class="txt-center">
                                <div class="w-full">
                                    <u class="pos-middle adstatus-icon"></u>
                                </div>
                            </td>
                            <td data-info-name="">
                            	<div class="w-full">
                                    <div class="txt-left pos-middle">
                                	<!-- <div class="prodlist tablebox txt-table"> -->
                                	無資料
                                	</div>
                                </div>
                            </td>

                            <td data-info-name="adType" class="whereNoData-adType">全部</td>
                            <td data-info-name="adOperatingRule" class="whereNoData-adOperatingRule">全部</td>
                            <td data-info-name="adClkPriceType" class="whereNoData-adClkPriceType">全部</td>
							<td data-info-name="" class="whereNoData-adDevice">全部</td>
							<td data-info-name=""><span></span></td><!--曝光數-->
							<td data-info-name=""><span></span></td><!--互動數-->
							<td data-info-name=""><span></span></td><!--互動率-->
							<td data-info-name=""><span></span></td><!--無效點選次數-->
							<td data-info-name=""><span></span></td><!--單次互動費用-->
							<td data-info-name=""><span></span></td><!--千次曝光費用-->
							<td data-info-name=""><span></span></td><!--費用-->
							<td data-info-name="convertCount"><span></span></td><!--轉換次數-->
							<td data-info-name="convertCTR"><span></span></td><!--轉換率-->
							<td data-info-name="convertPriceCount"><span></span></td><!--總轉換價值-->
							<td data-info-name="convertCost"><span></span></td><!--平均轉換成本-->
							<td data-info-name="convertInvestmentCost"><span></span></td><!--廣告投資報酬率-->
							<td data-info-name=""></td>
						</tr>
					</#if>
				</tbody>
			</table>
		</div>
	</div>

</div>