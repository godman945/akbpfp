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
      		<option value="click">互動數</option>
      		<option value="ctr">互動率</option> 
      		<option value="invalid">無效點選次數</option> 
      		<option value="avgCost">單次互動費用</option>
      		<option value="kiloCost">千次曝光費用</option>
      		<option value="cost">費用</option> 
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