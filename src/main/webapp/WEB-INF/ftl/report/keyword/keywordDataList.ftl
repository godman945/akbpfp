<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>


<#-- 隱藏欄位 -->
<input type="hidden" id="page" name="page" value="${page!}"> <#-- 目前頁數 -->
<input type="hidden" id="totalPage" name="totalPage" value="${totalPage!}"> <#-- 總頁數 -->
<#-- 隱藏欄位 結束 -->

<div class="prodtable-box txt-noselect">

	<div id="table-listing">
		<div class="table-scrollable floatingscroll transition-all">
			<table class="table keywordTB" data-fixed-columns="3" cellpadding="0" cellspacing="0">
				<thead class="header">                                        
					<tr class="txt-row header">

						<#-- th下拉選單 選取狀態: data-select="true" -->
						<th data-info-name="" class="col-w40">狀態</th>
                        <th data-info-name="" class="col-w40 txt-center">
                            <div class="pos-middle icon-switchall"></div>
                        </th>
                        <th data-info-name="" class="col-w250">關鍵字</th>
                        <th data-info-name="" class="col-w150">廣告活動</th>
                        <th data-info-name="" class="col-w150">廣告分類</th>
                        
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
									<li data-select="" data-sort="kwPvSubtotal-DESC">從大到小</li>
									<li data-select="" data-sort="kwPvSubtotal-ASC">從小到大</li>
								</ul>
							</div>
						</th>
						<th data-info-name="" class="col-w120">
							<div class="sortbx-selectTH pos-relative txt-right">
								<span class="sort-tit pos-relative">互動數</span>
								<ul class="sort-item pos-absolute pos-right">
									<li data-select="" data-sort="kwClkSubtotal-DESC">從大到小</li>
									<li data-select="" data-sort="kwClkSubtotal-ASC">從小到大</li>
								</ul>
							</div>
						</th>
						<th data-info-name="" class="col-w120">
							<div class="sortbx-selectTH pos-relative txt-right">
								<span class="sort-tit pos-relative">互動率</span>
								<ul class="sort-item pos-absolute pos-right">
									<li data-select="" data-sort="kwCtrSubtotal-DESC">從大到小</li>
									<li data-select="" data-sort="kwCtrSubtotal-ASC">從小到大</li>
								</ul>
							</div>
						</th>
						<th data-info-name="" class="col-w120">
							<div class="sortbx-selectTH pos-relative txt-right">
								<span class="sort-tit pos-relative">單次互動費用</span>
								<ul class="sort-item pos-absolute pos-right">
									<li data-select="" data-sort="kwPriceAvgSubtotal-DESC">從大到小</li>
									<li data-select="" data-sort="kwPriceAvgSubtotal-ASC">從小到大</li>
								</ul>
							</div>
						</th>
						<th data-info-name="" class="col-w120">
							<div class="sortbx-selectTH pos-relative txt-right">
								<span class="sort-tit pos-relative">千次曝光費用</span>
								<ul class="sort-item pos-absolute pos-right">
									<li data-select="" data-sort="kwKiloCostSubtotal-DESC">從大到小</li>
									<li data-select="" data-sort="kwKiloCostSubtotal-ASC">從小到大</li>
								</ul>
							</div>
						</th>
						<th data-info-name="" class="col-w120">
							<div class="sortbx-selectTH pos-relative txt-right">
								<span class="sort-tit pos-relative">費用</span>
								<ul class="sort-item pos-absolute pos-right">
									<li data-select="" data-sort="kwPriceSubtotal-DESC">從大到小</li>
									<li data-select="" data-sort="kwPriceSubtotal-ASC">從小到大</li>
								</ul>
							</div>
						</th>
						<th data-info-name="rankAvg" class="col-w120">平均廣告排名</th>
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
								<#--狀態-->
								<td data-info-name="" class="txt-center">
                                    <div class="w-full">
                                        <u class="pos-middle adstatus-icon <#if row.adStatusOnOff>open<#else>clz</#if>"></u>
                                    </div>
                                    <div class="subitem-box">
                                    	<#if row.kwRowShowHidden>
                                        	<div></div>
                                        </#if>
                                        <#if row.kwPhrRowShowHidden>
                                        	<div></div>
                                        </#if>
                                        <#if row.kwPreRowShowHidden>
                                        	<div></div>
                                        </#if>
                                    </div>
                                </td>
                                
                                <#--開關-->
                                <td data-info-name="" class="txt-center">
                                    <div class="w-full">
                                        <div class="pos-middle icon-switchKW"></div>
                                    </div>
                                    <div class="subitem-box">
                                        <#if row.kwRowShowHidden>
                                        	<div></div>
                                        </#if>
                                        <#if row.kwPhrRowShowHidden>
                                        	<div></div>
                                        </#if>
                                        <#if row.kwPreRowShowHidden>
                                        	<div></div>
                                        </#if>
                                    </div>
                                </td>
								
								<#--關鍵字-->
                                <td data-info-name="" class="txt-left">
                                    <div class="w-full">
                                        <div class="pos-middle">${row.adKeyword!}</div>
                                    </div>
                                    <div class="subitem-box">
                                    	<#if row.kwRowShowHidden>
                                        	<div>廣泛比對<u class="adstatus-icon <#if row.kwOpen>open<#else>clz</#if>"></u></div>
                                        </#if>
                                        <#if row.kwPhrRowShowHidden>
                                        	<div>詞組比對<u class="adstatus-icon <#if row.kwPhrOpen>open<#else>clz</#if>"></u></div>
                                        </#if>
                                        <#if row.kwPreRowShowHidden>
                                        	<div>精準比對<u class="adstatus-icon <#if row.kwPreOpen>open<#else>clz</#if>"></u></div>
                                        </#if>
                                    </div>
                                </td>
								
								<#--活動-->
                                <td data-info-name="" class="txt-left">
                                    <div class="w-full">
                                        <div class="pos-middle">${row.adActionName!}</div>
                                    </div>
                                    <div class="subitem-box">
                                        <#if row.kwRowShowHidden>
                                        	<div></div>
                                        </#if>
                                        <#if row.kwPhrRowShowHidden>
                                        	<div></div>
                                        </#if>
                                        <#if row.kwPreRowShowHidden>
                                        	<div></div>
                                        </#if>
                                    </div>
                                </td>
                                
                                <#--分類-->
                                <td data-info-name="" class="txt-left">
                                    <div class="w-full">
                                        <div class="pos-middle">${row.adGroupName!}</div>
                                    </div>
                                    <div class="subitem-box">
                                        <#if row.kwRowShowHidden>
                                        	<div></div>
                                        </#if>
                                        <#if row.kwPhrRowShowHidden>
                                        	<div></div>
                                        </#if>
                                        <#if row.kwPreRowShowHidden>
                                        	<div></div>
                                        </#if>
                                    </div>
                                </td>
                                
								<#--裝置-->
                                <td data-info-name="" class="txt-left">
                                    <div class="w-full">
                                        <div class="pos-middle">${row.adDevice!}</div>
                                    </div>
                                    <div class="subitem-box">
                                        <#if row.kwRowShowHidden>
                                        	<div></div>
                                        </#if>
                                        <#if row.kwPhrRowShowHidden>
                                        	<div></div>
                                        </#if>
                                        <#if row.kwPreRowShowHidden>
                                        	<div></div>
                                        </#if>
                                    </div>
                                </td>
                                
                                <#--曝光數-->
                                <td data-info-name="" class="txt-right">
                                    <div class="w-full">
                                        <div class="pos-middle">${row.kwPvSubtotal?string("#,###,###.##")!}</div>
                                    </div>
                                    <div class="subitem-box">
                                    	<#if row.kwRowShowHidden>
                                        	<div>${row.kwPvSum?string("#,###,###.##")!}</div>
                                        </#if>
                                        <#if row.kwPhrRowShowHidden>
                                        	<div>${row.kwPhrPvSum?string("#,###,###.##")!}</div>
                                        </#if>
                                        <#if row.kwPreRowShowHidden>
                                        	<div>${row.kwPrePvSum?string("#,###,###.##")!}</div>
                                        </#if>
                                    </div>
                                </td>
                                
                                <#--互動數-->
                                <td data-info-name="" class="txt-right">
                                    <div class="w-full">
                                        <div class="pos-middle">${row.kwClkSubtotal?string("#,###,###.##")!}</div>
                                    </div>
                                    <div class="subitem-box">
                                    	<#if row.kwRowShowHidden>
                                        	<div>${row.kwClkSum?string("#,###,###.##")!}</div>
                                        </#if>
                                        <#if row.kwPhrRowShowHidden>
                                        	<div>${row.kwPhrClkSum?string("#,###,###.##")!}</div>
                                        </#if>
                                        <#if row.kwPreRowShowHidden>
                                        	<div>${row.kwPreClkSum?string("#,###,###.##")!}</div>
                                        </#if>
                                    </div>
                                </td>    
								
								<#--互動率-->
                                <td data-info-name="" class="txt-right">
                                    <div class="w-full">
                                        <div class="pos-middle"><span class="data-percentage">${row.kwCtrSubtotal?string("#,###,###.##")!}</span></div>
                                    </div>
                                    <div class="subitem-box">
                                    	<#if row.kwRowShowHidden>
                                        	<div><span class="data-percentage">${row.kwCtrSum?string("#,###,###.##")!}</span></div>
                                        </#if>
                                        <#if row.kwPhrRowShowHidden>
                                        	<div><span class="data-percentage">${row.kwPhrCtrSum?string("#,###,###.##")!}</span></div>
                                        </#if>
                                        <#if row.kwPreRowShowHidden>
                                        	<div><span class="data-percentage">${row.kwPreCtrSum?string("#,###,###.##")!}</span></div>
                                        </#if>
                                    </div>
                                </td>
								
								<#--單次互動費用-->
                                <td data-info-name="" class="txt-right">
                                    <div class="w-full">
                                        <div class="pos-middle"><span class="cost-dollarmark">${row.kwPriceAvgSubtotal?string("#,###,###.##")!}</span></div>
                                    </div>
                                    <div class="subitem-box">
                                    	<#if row.kwRowShowHidden>
                                        	<div><span class="cost-dollarmark">${row.kwPriceAvgSum?string("#,###,###.##")!}</span></div>
                                        </#if>
                                        <#if row.kwPhrRowShowHidden>
                                        	<div><span class="cost-dollarmark">${row.kwPhrPriceAvgSum?string("#,###,###.##")!}</span></div>
                                        </#if>
                                        <#if row.kwPreRowShowHidden>
                                        	<div><span class="cost-dollarmark">${row.kwPrePriceAvgSum?string("#,###,###.##")!}</span></div>
                                        </#if>
                                    </div>
                                </td>

								<#--千次曝光費用-->
                                <td data-info-name="" class="txt-right">
                                    <div class="w-full">
                                        <div class="pos-middle"><span class="cost-dollarmark">${row.kwKiloCostSubtotal?string("#,###,###.##")!}</span></div>
                                    </div>
                                    <div class="subitem-box">
                                    	<#if row.kwRowShowHidden>
                                        	<div><span class="cost-dollarmark">${row.kwKiloCost?string("#,###,###.##")!}</span></div>
                                        </#if>
                                        <#if row.kwPhrRowShowHidden>
                                        	<div><span class="cost-dollarmark">${row.kwPhrKiloCost?string("#,###,###.##")!}</span></div>
                                        </#if>
                                        <#if row.kwPreRowShowHidden>
                                        	<div><span class="cost-dollarmark">${row.kwPreKiloCost?string("#,###,###.##")!}</span></div>
                                        </#if>
                                    </div>
                                </td>
								
								<#--費用-->
                                <td data-info-name="" class="txt-right">
                                    <div class="w-full">
                                        <div class="pos-middle"><span class="cost-dollarmark">${row.kwPriceSubtotal?string("#,###,###.###")!}</span></div>
                                    </div>
                                    <div class="subitem-box">
                                    	<#if row.kwRowShowHidden>
                                        	<div><span class="cost-dollarmark">${row.kwPriceSum?string("#,###,###.###")!}</span></div>
                                        </#if>
                                        <#if row.kwPhrRowShowHidden>
                                        	<div><span class="cost-dollarmark">${row.kwPhrPriceSum?string("#,###,###.###")!}</span></div>
                                        </#if>
                                        <#if row.kwPreRowShowHidden>
                                        	<div><span class="cost-dollarmark">${row.kwPrePriceSum?string("#,###,###.###")!}</span></div>
                                        </#if>
                                    </div>
                                </td>
								
								<#--平均廣告排名-->
                                <td data-info-name="rankAvg" class="txt-right">
                                    <div class="w-full">
                                        <div class="pos-middle"><span class=""></span></div>
                                    </div>
                                    <div class="subitem-box">
                                    	<#if row.kwRowShowHidden>
                                        	<div><span class="">${row.kwRankAvg?string("#,###,###.##")!}</span></div>
                                        </#if>
                                        <#if row.kwPhrRowShowHidden>
                                        	<div><span class="">${row.kwPhrRankAvg?string("#,###,###.##")!}</span></div>
                                        </#if>
                                        <#if row.kwPreRowShowHidden>
                                        	<div><span class="">${row.kwPreRankAvg?string("#,###,###.##")!}</span></div>
                                        </#if>
                                    </div>
                                </td>
							</tr>
						</#list>
						
						<#-- 總計列資料 -->
						<#list resultSumData as rowSum>
							<tr class="txt-row summaryRow">
								<td data-info-name="" style="height:70px"></td><!--狀態-->
                                <td data-info-name="" style="height:70px"></td><!--開關-->
                                <!--關鍵字-->
								<td data-info-name="">
									<div class="w-full h-full">
										<div class="txt-left pos-middle">
											<span class="amount-event">${rowSum.rowCount?string("#,###,###.##")!}筆</span>
										</div>
									</div>
								</td>
								
								<td data-info-name=""></td><!--活動-->
                                <td data-info-name=""></td><!--分類-->
                                <td data-info-name=""></td><!--裝置-->
								<td data-info-name="" class="txt-right"><span class="">${rowSum.pvTotal?string("#,###,###.##")!}</span></td><!--曝光數-->
								<td data-info-name="" class="txt-right"><span class="">${rowSum.clkTotal?string("#,###,###.##")!}</span></td><!--互動數-->
								<td data-info-name="" class="txt-right"><span class="data-percentage">${rowSum.ctrTotal?string("#,###,###.##")!}</span></td><!--互動率-->
								<td data-info-name="" class="txt-right"><span class="cost-dollarmark">${rowSum.priceAvgTotal?string("#,###,###.##")!}</span></td><!--單次互動費用-->
								<td data-info-name="" class="txt-right"><span class="cost-dollarmark">${rowSum.kiloCostTotal?string("#,###,###.##")!}</span></td><!--千次曝光費用-->
								<td data-info-name="" class="txt-right"><span class="cost-dollarmark">${rowSum.priceTotal?string("#,###,###.###")!}</span></td><!--費用-->
								<td data-info-name="rankAvg" class="txt-right"><span class=""></span></td><!--平均廣告排名-->
							</tr>
						</#list>
					<#else>
						<tr class="txt-row">
							<td data-info-name="" colspan="20" align="center">
								無此廣告成效
							</td>
						</tr>
					</#if>
				</tbody>
			</table>
		</div>
	</div>

</div>