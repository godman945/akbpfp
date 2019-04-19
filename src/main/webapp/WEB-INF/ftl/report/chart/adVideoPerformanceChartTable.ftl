<table width="100%" border="0" cellpadding="8" cellspacing="1" bgcolor="#FFFFFF" class="tb01"> 
  <tr> 
    <th>
                       圖表格式：
      	<select id="selectChartPic"> 
      		<option value="lineChart" selected>趨勢圖</option> 
      		<option value="barChart">直條圖</option> 
      	</select>
                     度量:
      	<select id="selectChartType"> 
      		<option value="pv">曝光數</option> 
      		<option value="adView">收視數</option>
      		<option value="adViewRatings">收視率</option>
      		<option value="singleAdViewCost">單次收視費用</option>
      		<option value="thousandsCost">千次曝光費用</option>
      		<option value="cost">費用</option>
      		<option value="adVideoProcess100Ratings">影片完整播放率</option>
      		<option value="click">點選次數</option>
      		<option value="adVideoUniqSum">收視人數(不重複)</option>
      		<option value="adVideoMusicSum">聲音開啟次數</option>
      		<option value="adVideoReplaySum">重播次數</option>
      	</select>
      	<input type="button" value="重繪圖表" id="reloadFlash">
      </th> 
  </tr> 
  <tr> 
  <td>
   
    <div id="hcharts_bx" style="max-width:1000px; height:300px; margin:0 auto;" data-highcharts-chart="0">
	</div>
	
	<div id="debug"></id>
    
    </td>
  </tr> 
</table> 