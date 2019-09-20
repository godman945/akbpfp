<div class="highchart-wrap m-b30 p-t30">
    <div class="chartnav txt-table p-l10 p-r10  pos-relative">
        <div class="txt-cell txt-left">
            <div class="select-box">
                <select id="selectChartType" name="selectChartType" aria-controls="">
                    <option value="pv">曝光數</option>
                    <option value="adView">收視數</option>
		      		<option value="adViewRatings">收視率</option>
		      		<option value="singleAdViewCost">單次收視費用</option>
                    <option value="kiloCost">千次曝光費用</option>
                    <option value="cost">費用</option>
                    <option value="adVideoProcess100Ratings">影片完整播放率</option>
		      		<option value="click">點選次數</option>
		      		<option value="adVideoUniqSum">收視人數(不重複)</option>
		      		<option value="adVideoMusicSum">聲音開啟次數</option>
		      		<option value="adVideoReplaySum">重播次數</option>
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