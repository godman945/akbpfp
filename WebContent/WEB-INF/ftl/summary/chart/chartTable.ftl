<table width="100%" height="175" border="0" cellpadding="8" cellspacing="1" bgcolor="#FFFFFF" class="tb01"> 
  <tr> 
    <th height="30">
                       圖表格式：
      	<select id="selectChartPic"> 
      		<option value="lineChart" selected>趨勢圖</option> 
      		<option value="barChart">直條圖</option> 
      	</select>
                     度量:
      	<select id="selectChartType"> 
      		<option value="pv" selected>曝光數</option> 
      		<option value="click">點選次數</option>      		
      		<option value="cost">費用</option> 
      	</select>
      	<input type="button" value="重繪圖表" id="reloadChart">
      </th> 
  </tr> 
  <tr> 
  	<td height="104"style="padding: 0 0 0 0;" >
  		<div id="hcharts_bx" style="max-width:600px; height:100px;margin:0 auto;" data-highcharts-chart="0">
		</div>   
	    <!-- <div id="flashChart">
				<div class="movie"></div>
				<h1>Alternative content</h1>
				<p><a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player" /></a></p>
		</div> -->
    </td>
  </tr> 
</table> 