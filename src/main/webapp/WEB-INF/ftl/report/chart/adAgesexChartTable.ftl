<div class="highchart-wrap m-b30 p-t30">
    <div class="chartnav txt-table p-l10 p-r10  pos-relative">
        <div class="txt-cell txt-left">
            <div class="select-box">
                <select id="selectChartType" name="selectChartType" aria-controls="">
                    <option value="pv">曝光數</option>
                    <option value="click">互動數</option>
                    <option value="convertCount">轉換次數</option>
                </select>
            </div>
        </div>
        <p class="chartsToggle pos-absolute pos-right" onclick="$('.highchart-box').fadeToggle('fast')">開關圖表</p>
    </div><!--chartnav 結束-->

    <div id="hcharts_bx" class="highchart-box">
       <!-- <iframe src="img/highcharts/chart_phone.html" height="420" width="100%" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" align="ceneter"></iframe> -->
    </div>
</div>