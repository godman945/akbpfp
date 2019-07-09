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
					<tr class="txt-row header typevideo">

						<#-- th下拉選單 選取狀態: data-select="true" -->
						<th data-info-name="" class="col-w40">狀態</th>
						<th data-info-name="" class="col-w250">影音廣告</th>
                        <th data-info-name="" class="col-w150">廣告活動</th>
                        <th data-info-name="" class="col-w150">廣告分類</th>
                        
                        <th data-info-name="adClkPriceType" class="col-w120">
                            <div class="sortbx-selectTH pos-relative txt-left">
                                <span class="sort-tit pos-relative">計價方式</span>
                                <ul class="sort-item pos-absolute pos-left">
                                    <li data-select="true" data-where="adClkPriceType-all">全部</li>
                                    <li data-select="" data-where="adClkPriceType-CPV">單次收視CPV</li>
                                    <li data-select="" data-where="adClkPriceType-CPM">千次曝光CPM</li>
                                </ul>
                            </div>
                        </th>
						
						<th data-info-name="" class="col-w70">
                            <div class="sortbx-selectTH pos-relative txt-left">
                                <span class="sort-tit pos-relative">裝置</span>
                                <ul class="sort-item pos-absolute pos-left">
                                    <li data-select="true" data-where="adDevice-all">全部</li>
                                    <li data-select="" data-where="adDevice-PCandMobile">電腦 + 行動</li>
                                    <li data-select="" data-where="adDevice-mobile">行動</li>
                                    <li data-select="" data-where="adDevice-PC">電腦</li>
                                </ul>
                            </div>
                        </th>
                        
                        <th data-info-name="" class="col-w100">
                             <div class="sortbx-selectTH pos-relative">
                                <span class="sort-tit pos-relative">廣告尺寸</span>
                                <ul class="sort-item pos-absolute pos-right">
                                    <li data-select="true" data-where="adSize-all">全部</li>
                                    <#list sizeSelectMap?keys as itemKey>
						            	<#assign item = sizeSelectMap[itemKey]>
										<li data-select="" data-where="adSize-${item}">${itemKey}</li>
								    </#list>
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
								<span class="sort-tit pos-relative">收視數</span>
								<ul class="sort-item pos-absolute pos-right">
									<li data-select="" data-sort="adViewSum-DESC">從大到小</li>
									<li data-select="" data-sort="adViewSum-ASC">從小到大</li>
								</ul>
							</div>
						</th>
						<th data-info-name="" class="col-w120">
							<div class="sortbx-selectTH pos-relative txt-right">
								<span class="sort-tit pos-relative">收視率</span>
								<ul class="sort-item pos-absolute pos-right">
									<li data-select="" data-sort="adViewRatings-DESC">從大到小</li>
									<li data-select="" data-sort="adViewRatings-ASC">從小到大</li>
								</ul>
							</div>
						</th>
						<th data-info-name="" class="col-w120">
							<div class="sortbx-selectTH pos-relative txt-right">
								<span class="sort-tit pos-relative">單次收視費用</span>
								<ul class="sort-item pos-absolute pos-right">
									<li data-select="" data-sort="singleAdViewCost-DESC">從大到小</li>
									<li data-select="" data-sort="singleAdViewCost-ASC">從小到大</li>
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
						
						<th data-info-name="adVideoProcess" class="col-w500 txt-center">
                            <span class="colspan-4">影片播放進度</span>
                            <div class="sortbx-selectTH pos-relative txt-right colspan-4item">
                                <span class="sort-tit pos-relative">25%</span>
                                <ul class="sort-item pos-absolute pos-right">
                                    <li data-select="" data-sort="adVideoProcess25Sum-DESC">從大到小</li>
                                    <li data-select="" data-sort="adVideoProcess25Sum-ASC">從小到大</li>
                                </ul>
                            </div>

                            <div class="sortbx-selectTH pos-relative txt-right colspan-4item">
                                <span class="sort-tit pos-relative">50%</span>
                                <ul class="sort-item pos-absolute pos-right">
                                    <li data-select="" data-sort="adVideoProcess50Sum-DESC">從大到小</li>
                                    <li data-select="" data-sort="adVideoProcess50Sum-ASC">從小到大</li>
                                </ul>
                            </div>

                            <div class="sortbx-selectTH pos-relative txt-right colspan-4item">
                                <span class="sort-tit pos-relative">75%</span>
                                <ul class="sort-item pos-absolute pos-right">
                                    <li data-select="" data-sort="adVideoProcess75Sum-DESC">從大到小</li>
                                    <li data-select="" data-sort="adVideoProcess75Sum-ASC">從小到大</li>
                                </ul>
                            </div>

                            <div class="sortbx-selectTH pos-relative txt-right colspan-4item">
                                <span class="sort-tit pos-relative">100%</span>
                                <ul class="sort-item pos-absolute pos-right">
                                    <li data-select="" data-sort="adVideoProcess100Sum-DESC">從大到小</li>
                                    <li data-select="" data-sort="adVideoProcess100Sum-ASC">從小到大</li>
                                </ul>
                            </div>
                        </th>
						
						<th data-info-name="adVideoProcess100Ratings" class="col-w120 txt-right">
                            <div class="sortbx-selectTH pos-relative txt-right">
                                <span class="sort-tit pos-relative">影片完整播放率</span>
                                <ul class="sort-item pos-absolute pos-right">
                                    <li data-select="" data-sort="adVideoProcess100Ratings-DESC">從大到小</li>
                                    <li data-select="" data-sort="adVideoProcess100Ratings-ASC">從小到大</li>
                                </ul>
                            </div>
                        </th>
                        
                        <th data-info-name="adClkSum" class="col-w120">
                            <div class="sortbx-selectTH pos-relative txt-right">
                                <span class="sort-tit pos-relative">點選次數</span>
                                <ul class="sort-item pos-absolute pos-right">
                                    <li data-select="" data-sort="adClkSum-DESC">從大到小</li>
                                    <li data-select="" data-sort="adClkSum-ASC">從小到大</li>
                                </ul>
                            </div>
                        </th>

                        <th data-info-name="adVideoUniqSum" class="col-w120">
                            <div class="sortbx-selectTH pos-relative txt-right">
                                <span class="sort-tit pos-relative thbreak">收視人數-不重複</span>
                                <ul class="sort-item pos-absolute pos-right">
                                    <li data-select="" data-sort="adVideoUniqSum-DESC">從大到小</li>
                                    <li data-select="" data-sort="adVideoUniqSum-ASC">從小到大</li>
                                </ul>
                            </div>
                        </th>

                        <th data-info-name="adVideoMusicSum" class="col-w120">
                            <div class="sortbx-selectTH pos-relative txt-right">
                                <span class="sort-tit pos-relative">聲音開啟次數</span>
                                <ul class="sort-item pos-absolute pos-right">
                                    <li data-select="" data-sort="adVideoMusicSum-DESC">從大到小</li>
                                    <li data-select="" data-sort="adVideoMusicSum-ASC">從小到大</li>
                                </ul>
                            </div>
                        </th>

                        <th data-info-name="adVideoReplaySum" class="col-w120">
                            <div class="sortbx-selectTH pos-relative txt-right">
                                <span class="sort-tit pos-relative">重播次數</span>
                                <ul class="sort-item pos-absolute pos-right">
                                    <li data-select="" data-sort="adVideoReplaySum-DESC">從大到小</li>
                                    <li data-select="" data-sort="adVideoReplaySum-ASC">從小到大</li>
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
                                        <div class="txt-cell txt-left prod-detail">
	                                        <div class="hiddenVal" style="display: none;">
	                                			<input type="hidden" id="width" name="width" value="${row.templateProductWidth!}">
	                                			<input type="hidden" id="height" name="height" value="${row.templateProductHeight!}">
	                                			<input type="hidden" id="adPreviewVideoURL" name="adPreviewVideoURL" value="${row.videoUrl!}">
	                                			<input type="hidden" id="adPreviewVideoBgImg" name="adPreviewVideoBgImg" value="<#if row.adImg != 'img/public/na.gif" style="display:none'>${row.adImg!}</#if>">
	                                			<input type="hidden" id="realUrl" name="realUrl" value="${row.adLinkUrl!}">
	                                			<input type="hidden" id="resize" name="resize" value="false">
	                                		</div>
                                            <a class="preview pos-absolute" onclick="preView('VIDEO', this)">預覽</a>
                                            <h5 class="h2 txt-ellipsis" data-pdName="${row.title!}"></h5>
                                            <small>尺寸：<em data-pcadsize="${row.templateProductWidth!}x${row.templateProductHeight!}"></em></small>
                                            <small>時間：<em data-pcadtime="00:${row.adVideoSec!}"></em></small>
                                            <small class="prodLink txt-ellipsis" data-prodlink="${row.adLinkUrl!}"></small>
                                        </div>
                                    </div>
                                    <!--prodlist END-->
                                </td>
                                
                                <td data-info-name="">${row.adActionName!}</td>
                                <td data-info-name="">${row.adGroupName!}</td>
                                <td data-info-name="adClkPriceType">${row.adClkPriceType!}</td>
								<td data-info-name="">${row.adDevice!}</td>
								<td data-info-name="">${row.templateProductWidth!}x${row.templateProductHeight!}</td><!--廣告尺寸-->
								<td data-info-name="" class="txt-right"><span class="">${row.adPvSum?string("#,###,###.##")!}</span></td><!--曝光數-->
								<td data-info-name="" class="txt-right"><span class="">${row.adViewSum?string("#,###,###.##")!}</span></td><!--收視數-->
								<td data-info-name="" class="txt-right"><span class="data-percentage">${row.adViewRatings?string("#,###,###.##")!}</span></td><!--收視率-->
								<td data-info-name="" class="txt-right"><span class="cost-dollarmark">${row.singleAdViewCost?string("#,###,###.##")!}</span></td><!--單次收視費用-->
								<td data-info-name="" class="txt-right"><span class="cost-dollarmark">${row.kiloCost?string("#,###,###.##")!}</span></td><!--千次曝光費用-->
								<td data-info-name="" class="txt-right"><span class="cost-dollarmark">${row.adPriceSum?string("#,###,###.###")!}</span></td><!--費用-->
								<td data-info-name="adVideoProcess" class="txt-right">
                                    <div class="colspan-4item-TD">${row.adVideoProcess25Sum?string("#,###,###.##")!}</div>
                                    <div class="colspan-4item-TD">${row.adVideoProcess50Sum?string("#,###,###.##")!}</div>
                                    <div class="colspan-4item-TD">${row.adVideoProcess75Sum?string("#,###,###.##")!}</div>
                                    <div class="colspan-4item-TD">${row.adVideoProcess100Sum?string("#,###,###.##")!}</div>
                                </td>
                                <td data-info-name="adVideoProcess100Ratings" class="txt-right"><span class="data-percentage">${row.adVideoProcess100Ratings?string("#,###,###.##")!}</span></td><!--影片完整播放率-->
                                <td data-info-name="adClkSum" class="txt-right"><span class="">${row.adClkSum?string("#,###,###.##")!}</span></td><!--點選次數-->
                                <td data-info-name="adVideoUniqSum" class="txt-right">${row.adVideoUniqSum?string("#,###,###.##")!}</td><!--收視人數-不重複-->
                                <td data-info-name="adVideoMusicSum" class="txt-right"><span class="">${row.adVideoMusicSum?string("#,###,###.##")!}</span></td><!--聲音開啟次數-->
                                <td data-info-name="adVideoReplaySum" class="txt-right"><span class="">${row.adVideoReplaySum?string("#,###,###.##")!}</span></td><!--重播次數-->
							</tr>
						</#list>
						
						<#-- 總計列資料 -->
						<#list resultSumData as rowSum>
							<tr class="txt-row summaryRow">
								<td data-info-name="" class="txt-center"></td>
								<td data-info-name="">
									<div class="txt-left pos-middle">
										<span class="amount-event">${rowSum.rowCount?string("#,###,###.##")!}筆</span>
									</div>
								</td>
								<td data-info-name=""></td>
                                <td data-info-name=""></td>
                                <td data-info-name="adClkPriceType"></td>
								<td data-info-name=""></td>
								<td data-info-name=""></td><!--廣告尺寸-->
								<td data-info-name="" class="txt-right"><span class="">${rowSum.adPvSum?string("#,###,###.##")!}</span></td><!--曝光數-->
								<td data-info-name="" class="txt-right"><span class="">${rowSum.adViewSum?string("#,###,###.##")!}</span></td><!--收視數-->
								<td data-info-name="" class="txt-right"><span class="data-percentage">${rowSum.adViewRatings?string("#,###,###.##")!}</span></td><!--收視率-->
								<td data-info-name="" class="txt-right"><span class="cost-dollarmark">${rowSum.singleAdViewCost?string("#,###,###.##")!}</span></td><!--單次收視費用-->
								<td data-info-name="" class="txt-right"><span class="cost-dollarmark">${rowSum.kiloCost?string("#,###,###.##")!}</span></td><!--千次曝光費用-->
								<td data-info-name="" class="txt-right"><span class="cost-dollarmark">${rowSum.adPriceSum?string("#,###,###.###")!}</span></td><!--費用-->
								<td data-info-name="adVideoProcess" class="txt-right">
                                    <div class="colspan-4item-TD">${rowSum.adVideoProcess25Sum?string("#,###,###.##")!}</div>
                                    <div class="colspan-4item-TD">${rowSum.adVideoProcess50Sum?string("#,###,###.##")!}</div>
                                    <div class="colspan-4item-TD">${rowSum.adVideoProcess75Sum?string("#,###,###.##")!}</div>
                                    <div class="colspan-4item-TD">${rowSum.adVideoProcess100Sum?string("#,###,###.##")!}</div>
                                </td>
                                <td data-info-name="adVideoProcess100Ratings" class="txt-right"><span class="data-percentage">${rowSum.adVideoProcess100Ratings?string("#,###,###.##")!}</span></td><!--影片完整播放率-->
                                <td data-info-name="adClkSum" class="txt-right"><span class="">${rowSum.adClkSum?string("#,###,###.##")!}</span></td><!--點選次數-->
                                <td data-info-name="adVideoUniqSum" class="txt-right">${rowSum.adVideoUniqSum?string("#,###,###.##")!}</td><!--收視人數-不重複-->
                                <td data-info-name="adVideoMusicSum" class="txt-right"><span class="">${rowSum.adVideoMusicSum?string("#,###,###.##")!}</span></td><!--聲音開啟次數-->
                                <td data-info-name="adVideoReplaySum" class="txt-right"><span class="">${rowSum.adVideoReplaySum?string("#,###,###.##")!}</span></td><!--重播次數-->
							</tr>
						</#list>
					<#else>
						<tr class="txt-row">
							<td data-info-name="" class="txt-center">
                                <div class="w-full h-full">
                                    <u class="pos-middle adstatus-icon"></u>
                                </div>
                            </td>
                            <td data-info-name="">
                                <div class="prodlist tablebox txt-table">
                                    <div class="txt-cell txt-left prod-detail">
                                       	 無資料
                                    </div>
                                </div>
                            </td>
                            
                            <td data-info-name=""></td>
                            <td data-info-name=""></td>
                            <td data-info-name="adClkPriceType" class="whereNoData-adClkPriceType">全部</td>
							<td data-info-name="" class="whereNoData-adDevice">全部</td>
							<td data-info-name="" class="whereNoData-adSize">全部</td><!--廣告尺寸-->
							<td data-info-name=""><span></span></td><!--曝光數-->
							<td data-info-name=""><span></span></td><!--收視數-->
							<td data-info-name=""><span></span></td><!--收視率-->
							<td data-info-name=""><span></span></td><!--單次收視費用-->
							<td data-info-name=""><span></span></td><!--千次曝光費用-->
							<td data-info-name=""><span></span></td><!--費用-->
							<td data-info-name="adVideoProcess">
                                <div class="colspan-4item-TD"></div>
                                <div class="colspan-4item-TD"></div>
                                <div class="colspan-4item-TD"></div>
                                <div class="colspan-4item-TD"></div>
                            </td>
                            <td data-info-name="adVideoProcess100Ratings"><span></span></td><!--影片完整播放率-->
                            <td data-info-name="adClkSum"><span></span></td><!--點選次數-->
                            <td data-info-name="adVideoUniqSum"></td><!--收視人數-不重複-->
                            <td data-info-name="adVideoMusicSum"><span></span></td><!--聲音開啟次數-->
                            <td data-info-name="adVideoReplaySum"><span></span></td><!--重播次數-->
						</tr>
					</#if>
				</tbody>
			</table>
		</div>
	</div>

</div>