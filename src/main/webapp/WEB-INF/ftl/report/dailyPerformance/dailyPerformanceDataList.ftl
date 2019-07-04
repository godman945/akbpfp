<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>


<#-- 隱藏欄位 -->
<input type="hidden" id="page" name="page" value="${page!}"> <#-- 目前頁數 -->
<input type="hidden" id="totalPage" name="totalPage" value="${totalPage!}"> <#-- 總頁數 -->
<#-- 隱藏欄位 結束 -->

<div class="prodtable-box txt-noselect">

	<div id="table-listing">
		<div class="table-scrollable floatingscroll transition-all">
			<table class="table" data-fixed-columns="1" cellpadding="0" cellspacing="0">
				<thead class="header">                                        
					<tr class="txt-row header">
						<th data-info-name="" class="col-w200">日期</th>
						
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
								<td data-info-name="">
                                    <div class="w-full h-full">
                                        <div class="txt-left pos-middle">
                                            ${row.reportDate?string("yyyy-MM-dd")}
                                        </div>
                                    </div>
                                </td>
                                
                                <td data-info-name="adType">${row.adType!}</td>
                                <td data-info-name="adOperatingRule">${row.adOperatingRule!}</td>
                                <td data-info-name="adClkPriceType">${row.adClkPriceType!}</td>
								<td data-info-name="">${row.adDevice!}</td>
								<td data-info-name="" class="txt-right"><span class="">${row.adPvSum?string("#,###,###.##")!}</span></td><!--曝光數-->
								<td data-info-name="" class="txt-right"><span class="">${row.adClkSum?string("#,###,###.##")!}</span></td><!--互動數-->
								<td data-info-name="" class="txt-right"><span class="data-percentage">${row.ctr?string("#,###,###.##")!}</span></td><!--互動率-->
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
								<td data-info-name="">
									<div class="w-full h-full">
										<div class="txt-left pos-middle">
											<span class="amount-event">${rowSum.rowCount?string("#,###,###.##")!}筆</span>
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
							<td data-info-name="">
                                <div class="w-full h-full">
                                    <div class="txt-left pos-middle">
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
							<td data-info-name=""><span></span></td><!--單次互動費用-->
							<td data-info-name=""><span></span></td><!--千次曝光費用-->
							<td data-info-name=""><span></span></td><!--費用-->
							<td data-info-name="convertCount"><span></span></td><!--轉換次數-->
							<td data-info-name="convertCTR"><span></span></td><!--轉換率-->
							<td data-info-name="convertPriceCount"><span></span></td><!--總轉換價值-->
							<td data-info-name="convertCost"><span></span></td><!--平均轉換成本-->
							<td data-info-name="convertInvestmentCost"><span></span></td><!--廣告投資報酬率-->
						</tr>
					</#if>
				</tbody>
			</table>
		</div>
	</div>

</div>