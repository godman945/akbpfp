package com.pchome.soft.depot.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;

import jofc2.model.Chart;
import jofc2.model.axis.Label;
import jofc2.model.axis.XAxis;
import jofc2.model.axis.YAxis;
import jofc2.model.elements.BarChart;
import jofc2.model.elements.LineChart;
import jofc2.model.elements.BarChart.Bar;
import jofc2.model.elements.LineChart.Dot;

import com.pchome.enumerate.report.EnumReport;
import com.pchome.soft.util.DateValueUtil;

public class SpringOpenFlashUtil {

	private Log log = LogFactory.getLog(getClass());

	public String getChartData(String context){

		Chart cht=null;

		cht = new Chart(context);


		// 產生 BarChart 並將資料新增到 BarChart
		BarChart bc = new BarChart(BarChart.Style.NORMAL);
		for(int i=0; i<20; i++)
			bc.addValues((int) (Math.random() * 10) + 1);

		cht.addElements(bc);

		return cht.toString();
	}

	public String getChartDataForMap(String charPic,String charType,String startDate,String endDate,Map<Date,Float> flashDataMap){

		String chartTypeText="";
		String chartYlegendText="";

		NumberFormat numFormat = null;

		if (charType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
			chartTypeText=EnumReport.REPORT_CHART_TYPE_CW_PV.getTextValue();
			chartYlegendText="單位(次)";
			numFormat = new DecimalFormat("###,###,###,###");
		} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
			chartTypeText=EnumReport.REPORT_CHART_TYPE_CW_CLICK.getTextValue();
			chartYlegendText="單位(次)";
			numFormat = new DecimalFormat("###,###,###,###");
		} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
			chartTypeText=EnumReport.REPORT_CHART_TYPE_CW_CTR.getTextValue();
			chartYlegendText="單位(%)";
			numFormat = new DecimalFormat("###,###,###,###.##");
		} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
			chartTypeText=EnumReport.REPORT_CHART_TYPE_CW_AVGCOST.getTextValue();
			chartYlegendText="單位(NT$)";
			numFormat = new DecimalFormat("###,###,###,###.##");
		} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
			chartTypeText=EnumReport.REPORT_CHART_TYPE_CW_COST.getTextValue();
			chartYlegendText="單位(NT$)";
			numFormat = new DecimalFormat("###,###,###,###");
		} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_ADSORT.getTextValue())) {
			chartTypeText=EnumReport.REPORT_CHART_TYPE_CW_ADSORT.getTextValue();
			chartYlegendText="單位(名)";
			numFormat = new DecimalFormat("###,###,###,###");
		} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_LIMITDAY.getTextValue())) {
			chartTypeText=EnumReport.REPORT_CHART_TYPE_CW_LIMITDAY.getTextValue();
			chartYlegendText="單位(NT$)";
			numFormat = new DecimalFormat("###,###,###,###");
		} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
			chartTypeText=EnumReport.REPORT_CHART_TYPE_CW_INVALID.getTextValue();
			chartYlegendText="單位(次)";
			numFormat = new DecimalFormat("###,###,###,###");
		} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CTRINVALID.getTextValue())) {
			chartTypeText=EnumReport.REPORT_CHART_TYPE_CW_CTRINVALID.getTextValue();
			chartYlegendText="單位(次)";
			numFormat = new DecimalFormat("###,###,###,###");
		}
		
		chartTypeText+="-"+chartYlegendText;

		int scale = 2;//小數點位數 

		int roundingMode = 4;//表示四射五入
		
		//x 軸兩日期間所相差的天數
		long maxValueX = DateValueUtil.getInstance().getDateDiffDay(startDate, endDate);

		int x_count = 0; //x軸要分成幾個區間
		if (maxValueX >= 365) {
			x_count = Math.round(maxValueX/365) * 30;
		} else if (maxValueX >= 30 && maxValueX<365){
			x_count = Math.round(maxValueX/30) * 3;
		} else {
			x_count = (int) maxValueX;
		}

		if (x_count > 12) {
			x_count = 12;
		}

		//一個區間是幾天
		int stepValueX = (int) Math.ceil((double) maxValueX / (double) x_count);

		List<Label> xLabel = new ArrayList<Label>();
		Date pDate=null;
		Label l;
		
		for(int d=0;d<maxValueX;d++){
			pDate=DateValueUtil.getInstance().getDateForStartDateAddDay(startDate, d);
			
			String strTmp = DateValueUtil.getInstance().getChartXlabelDate(pDate);
			String[] arrayTmp = strTmp.split("/");
			strTmp = arrayTmp[1] + "/" + arrayTmp[0];
			l=new Label(strTmp);

			if(d%stepValueX==0){
				l.setVisible(true);
			}else{
				l.setVisible(false);
			}
			
			xLabel.add(l);	
		}
		
		XAxis labels = new XAxis();

		labels.addLabels(xLabel);
		//間格線長度
		labels.setStroke(3);
		labels.setColour("#6d6d6d");
		labels.setGridColour("#cfcfcf");
		labels.setStroke(1);
		labels.setSteps(2);
		
		
		//設置y軸 取最大值
		int maxValueY = (int) Math.ceil(getMapMaxValue(flashDataMap));

		if (maxValueY == 0) {
			maxValueY = 1;
		}

		//間隔
		int stepValueY = getFloatNum((maxValueY/5),scale,roundingMode); 

		maxValueY += stepValueY;

		YAxis range = new YAxis();
		range.setRange(0, maxValueY, stepValueY);
		range.setColour("#6d6d6d");
		range.setGridColour("#cfcfcf");

		//chart 設定
		Chart cht = new Chart();
		cht.setBackgroundColour("#F4F4F4");
		cht.setXAxis(labels);
		cht.setYAxis(range);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(charPic.trim().equals("lineChart")){ //趨勢圖
			
			LineChart lineChart=null;
			lineChart = new LineChart(LineChart.Style.NORMAL);
			lineChart.setColour("#fe3333");
			lineChart.setFontSize(14);
			lineChart.setText(chartTypeText);
			
			//總共天數
			//從開始天數開始到結束天數
			//將每一天送到map 看是否有值
			for(int d=0;d<maxValueX;d++){
				pDate=DateValueUtil.getInstance().getDateForStartDateAddDay(startDate, d);
				if(flashDataMap.containsKey(pDate)){
					Dot dot = new Dot(flashDataMap.get(pDate));
					dot.setTooltip(numFormat.format(flashDataMap.get(pDate)) + " (" + sdf.format(pDate) + ")");
					//dot.setTooltip(flashDataMap.get(pDate) + " (" + sdf.format(pDate) + ")");
					lineChart.addDots(dot);
				}else{
					lineChart.addValues(0);
				}
			}
	
			cht.addElements(lineChart);
		
		}else{ //直條圖
		
			BarChart barChart=null;
			barChart = new BarChart(BarChart.Style.GLASS);
			barChart.setFontSize(14);
			barChart.setText(chartTypeText);
			
			//總共天數
			for(int d=0;d<maxValueX;d++){
				pDate=DateValueUtil.getInstance().getDateForStartDateAddDay(startDate, d);
				if(flashDataMap.containsKey(pDate)){
					Bar bar = new Bar(flashDataMap.get(pDate));
					bar.setTooltip(numFormat.format(flashDataMap.get(pDate)) + " (" + sdf.format(pDate) + ")");
					barChart.addBars(bar);
				}else{
					barChart.addValues(0);
				}
			}

			cht.addElements(barChart);

		}
		
		return cht.toString();

	}

	public String getChartDataForArray(String charType,String startDate,String endDate,Map<Date,Float> flashDataMap){
		int scale = 0;		//设置位数

		if (charType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
			scale = 0;
		} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
			scale = 0;
		} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
			scale = 2;
		} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
			scale = 2;
		} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
			scale = 0;
		} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_ADSORT.getTextValue())) {
			scale = 0;
		} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_LIMITDAY.getTextValue())) {
			scale = 0;
		} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_INVALID.getTextValue())) {
			scale = 0;
		} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CTRINVALID.getTextValue())) {
			scale = 0;
		}
		//x 軸兩日期間所相差的天數
		long maxValueX = DateValueUtil.getInstance().getDateDiffDay(startDate, endDate);
		Date pDate=null;
		List<Double> dataList = new ArrayList<Double>();
		
		for(int d=0;d<maxValueX;d++){
			pDate=DateValueUtil.getInstance().getDateForStartDateAddDay(startDate, d);
			if(flashDataMap.containsKey(pDate)){
				BigDecimal bd = new BigDecimal((double)flashDataMap.get(pDate));
				bd = bd.setScale(scale,4);
				dataList.add(bd.doubleValue());
			}else{
				dataList.add((double) 0);
			}
		}
		
		JSONArray array = new JSONArray(dataList);
		
		return array.toString();
	}
	
	private int getFloatNum(float num,int scale,int roundingMode){
		BigDecimal bd;
		bd = new BigDecimal((float)num); 
		bd = bd.setScale(scale,roundingMode); 
		return bd.intValue(); 
	}

	private Float getMapMaxValue(Map<Date,Float> flashDataMap){

		float maxResult=0.0f;

		float max=0.0f;
		float temp=0.0f;

		for(Date key:flashDataMap.keySet()){

			temp=flashDataMap.get(key);
			if(temp > max){
				max=temp;
			}
		}

		maxResult=max;

		return maxResult;
	}
}
