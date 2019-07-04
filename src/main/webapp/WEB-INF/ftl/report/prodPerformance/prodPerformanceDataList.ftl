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
						<th data-info-name="" class="col-w40">狀態</th>
                        <th data-info-name="" class="col-w250">商品明細</th>

						<th data-info-name="" class="col-w120">
	                        <div class="sortbx-selectTH pos-relative txt-right">
	                            <span class="sort-tit pos-relative">陳列次數</span>
	                            <ul class="sort-item pos-absolute pos-right">
	                                <li data-select="" data-sort="adPvSum-DESC">從大到小</li>
	                                <li data-select="" data-sort="adPvSum-ASC">從小到大</li>
	                            </ul>
	                        </div>
	                    </th>
	
	                    <th data-info-name="" class="col-w120">
	                        <div class="sortbx-selectTH pos-relative txt-right">
	                            <span class="sort-tit pos-relative">商品點選數</span>
	                            <ul class="sort-item pos-absolute pos-right">
	                                <li data-select="" data-sort="adClkSum-DESC">從大到小</li>
	                                <li data-select="" data-sort="adClkSum-ASC">從小到大</li>
	                            </ul>
	                        </div>
	                    </th>
	
	                    <th data-info-name="" class="col-w120">
	                        <div class="sortbx-selectTH pos-relative txt-right">
	                            <span class="sort-tit pos-relative">商品點選率</span>
	                            <ul class="sort-item pos-absolute pos-right">
	                                <li data-select="" data-sort="ctr-DESC">從大到小</li>
	                                <li data-select="" data-sort="ctr-ASC">從小到大</li>
	                            </ul>
	                        </div>
	                    </th>
						
					</tr>
				</thead>

				<!--className 狀態 adstatus-icon：<開啟+open> <關閉+clz> -->
				<!--className 單位 %            ：data-percentage-->
				<!--className 單位 $             ：cost-dollarmark-->
				<!--className 單位 次           ：cumulate-number-->
				<!--className 總計                ：amount-event-->
				<tbody class="results">
					<#if (resultData?size > 0) >
						<#-- 每列資料 -->
						<#list resultData as row>
							<tr class="txt-row detailRow">
								<td data-info-name="" class="txt-center">
                                    <div class="w-full h-full">
                                        <u class="pos-middle adstatus-icon <#if row.ecStatus == "1">open</#if>"></u>
                                    </div>
                                </td>
                                
                                <td data-info-name=""> 
                                    <div class="prodlist merchantsbox txt-table">
                                        <div class="txt-cell prod-pic">
                                            <img src="${row.prodImgPath!}">
                                        </div>
                                        <div class="txt-cell txt-left prod-detail">
                                            <h5 class="h2" data-pdName="${row.ecName!}"></h5>
                                        </div>
                                    </div><!--prodlist END-->
                                </td>
                                <td data-info-name="" class="txt-right">${row.adPvSum?string("#,###,###.##")!}</td><!--陳列次數-->
                                <td data-info-name="" class="txt-right">${row.adClkSum?string("#,###,###.##")!}</td><!--商品點選數-->
                                <td data-info-name="" class="txt-right"><span class="data-percentage">${row.ctr?string("#,###,###.##")!}</span></td><!--商品點選率-->

							</tr>
						</#list>
						
						<#-- 總計列資料 -->
						<#list resultSumData as rowSum>
							<tr class="txt-row summaryRow">
								<td data-info-name="" class="txt-center"></td>
                                <td data-info-name="">
                                    <div class="w-full h-full">
                                        <div class="txt-left pos-middle">
                                            <span class="amount-event">${rowSum.rowCount?string("#,###,###.##")!}筆商品</span>
                                        </div>
                                    </div>
                                </td>
                                <td data-info-name="" class="txt-right">${rowSum.adPvSum?string("#,###,###.##")!}</td><!--陳列次數-->
                                <td data-info-name="" class="txt-right">${rowSum.adClkSum?string("#,###,###.##")!}</td><!--商品點選數-->
                                <td data-info-name="" class="txt-right"><span class="data-percentage">${rowSum.ctr?string("#,###,###.##")!}</span></td><!--商品點選率-->
								
							</tr>
						</#list>
					<#else>
						<tr class="txt-row">
							<td data-info-name="" class="txt-center">
                                <div class="w-full h-full">
                                    <u class="pos-middle adstatus-icon clz"></u>
                                </div>
                            </td>
                            <td data-info-name="">
                                <div class="prodlist txt-table">
									無資料
                                </div>
                            </td>
                            <td data-info-name=""></td><!--陳列次數-->
                            <td data-info-name=""></td><!--商品點選數-->
                            <td data-info-name=""></td><!--商品點選率-->
						</tr>
					</#if>
				</tbody>
			</table>
		</div>
	</div>

</div>