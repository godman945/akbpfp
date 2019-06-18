<div class="highchart-wrap m-b30 p-t30">
    <div class="chartnav txt-table p-l10 p-r10  pos-relative">
        <div class="txt-cell txt-left">
            <div class="select-box">
                <select id="selectChartType" name="selectChartType" aria-controls="">
                    <option value="pv">曝光數</option>
                    <option value="click">互動數</option>
                    <option value="ctr">互動率</option>
                    <option value="invClk">無效點選次數</option>
                    <option value="avgCost">單次互動費用</option>
                    <option value="kiloCost">千次曝光費用</option>
                    <option value="cost">費用</option>
                    <option value="convertCount">轉換數</option>
                    <option value="convertCTR">轉換率</option>
                    <option value="convertPriceCount">總轉換價值</option>
                    <option value="convertCost">平均轉換成本</option>
                    <option value="convertInvestmentCost">廣告投資報酬率</option>           
                </select>
            </div>
            <div class="select-box">
                <select id="selectChartPic" name="selectChartPic" aria-controls="">
                    <option value="lineChart" selected>趨勢圖</option>
                    <option value="barChart">直條圖</option>           
                </select>
            </div>                            
        </div>
        <p class="chartsToggle pos-absolute pos-right" onclick="$('.highchart-box').fadeToggle('fast')">開關圖表</p>
    </div><!--chartnav 結束-->

    <!--趨勢圖 : chart_basicLine.html-->
    <!--直調圖 : chart_basicColumn.html-->
    <div id="hcharts_bx" class="highchart-box">
       <!-- <iframe src="img/highcharts/chart_basicLine.html" height="420" width="100%" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" align="ceneter"></iframe>-->
    </div>
</div>