<div class="highchart-wrap m-b30 p-t30">
    <div class="chartnav txt-table p-l10 p-r10  pos-relative">
        <div class="txt-cell txt-left">
            <div class="select-box">
                <select id="selectChartType" name="selectChartType" aria-controls="">
                    <option value="pv">陳列次數</option><#-- 曝光數 -->
                    <option value="click">商品點選數</option><#-- 互動數 -->
                    <option value="ctr">商品點選率</option><#-- 互動率 -->
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