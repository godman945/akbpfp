package com.pchome.enumerate.report;

public enum EnumReport {
	ADPVCLKDEVICE_PC("PC"),
	ADPVCLKDEVICE_MOBILE("mobile"),
	ADPVCLKOS_ALL("all"),
	ADPVCLKOS_PC("PC"),
	ADPVCLKOS_ANDROID("Android"),
	ADPVCLKOS_WINDOWS("Windows"),
	ADPVCLKOS_OTHER("其他"),
	ADTYPE_CUSTINFO("adtype_custinfo"),
    ADTYPE_ACTIVITY("adtype_activity"),
    ADTYPE_GROUP("adtype_group"),
    ADTYPE_KEYWORD("adtype_keyword"),
    ADTYPE_AD("adtype_ad"),
    ADSEARCH_INCLUDE("adsearch_include"),
    ADSEARCH_BEGIN("adsearch_begin"),
    ADSEARCH_SAME("adsearch_same"),
    ADSHOW_ALL("adshow_all"),
    ADSHOW_GENERAL("adshow_general"),
    ADSHOW_CONTENT("adshow_content"),
    DETAIL_ACTIVITY("detail_activity"),
    DETAIL_GROUP("detail_group"),
    DETAIL_HQLTYPE_DAILY_CHART("DetailDailyChart"),

    REPORT_CHART_TYPE_PV("pv"),
    REPORT_CHART_TYPE_CLICK("click"),
    REPORT_CHART_TYPE_CTR("ctr"),
    REPORT_CHART_TYPE_AVGCOST("avgCost"),
    REPORT_CHART_TYPE_COST("cost"),
    REPORT_CHART_TYPE_ADSORT("avgAdSort"),
    REPORT_CHART_TYPE_INVALID("invalid"),
    REPORT_CHART_TYPE_INVALID_COST("invalidCost"),
    REPORT_CHART_TYPE_CTRINVALID("ctrInvalid"),
    REPORT_CHART_TYPE_LIMITDAY("limitDay"),

    REPORT_CHART_TYPE_CW_PV("曝光數"),
    REPORT_CHART_TYPE_CW_CLICK("點選次數"),
    REPORT_CHART_TYPE_CW_CTR("點選率"),
    REPORT_CHART_TYPE_CW_AVGCOST("平均點選費用"),
    REPORT_CHART_TYPE_CW_COST("費用"),
    REPORT_CHART_TYPE_CW_ADSORT("平均廣告排名"),
    REPORT_CHART_TYPE_CW_INVALID("無效點選數"),
    REPORT_CHART_TYPE_CW_CTRINVALID("無效點選率"),
    REPORT_CHART_TYPE_CW_LIMITDAY("每日花費上限"),

    REPORT_HQLTYPE_EXCERPT("ReportExperpt"),
    REPORT_HQLTYPE_EXCERPT_CHART("ReportExperptChart"),
    REPORT_HQLTYPE_EXCERPT_COUNT("ReportExperptCount"),
    REPORT_HQLTYPE_DAILY("ReportDaily"),
    REPORT_HQLTYPE_DAILY_COUNT("ReportDailyCount"),
    REPORT_HQLTYPE_DAILY_CHART("ReportDailyChart"),
    REPORT_HQLTYPE_ADVERTISE("ReportAdvertise"),
    REPORT_HQLTYPE_ADVERTISE_COUNT("ReportAdvertiseCount"),
    REPORT_HQLTYPE_ADVERTISE_CHART("ReportDAdvertiseChart"),
    REPORT_HQLTYPE_KEYWORD("ReportKeyword"),
    REPORT_HQLTYPE_KEYWORD_COUNT("ReportKeywordCount"),
    REPORT_HQLTYPE_KEYWORD_CHART("ReportKeywordChart"),
    REPORT_HQLTYPE_DAYCOST("ReportCost"),
    REPORT_HQLTYPE_DAYCOST_COUNT("ReportCostCount"),
    REPORT_HQLTYPE_DAYCOST_CHART("ReportCostChart"),
    REPORT_HQLTYPE_URL("ReportUrl"),
    REPORT_HQLTYPE_URL_COUNT("ReportUrlCount"),
    REPORT_HQLTYPE_URL_CHART("ReportUrlChart"),
    REPORT_HQLTYPE_CHECK("ReportCheck"),
    REPORT_HQLTYPE_CHECK_COUNT("ReportCheckCount"),
    REPORT_HQLTYPE_CHECK_CHART("ReportCheckChart"),
    REPORT_HQLTYPE_CUSTCHECK("ReportCustCheck"),
    REPORT_HQLTYPE_CUSTCHECK_COUNT("ReportCustCheckCount"),
    REPORT_HQLTYPE_CUSTCHECK_CHART("ReportCustCheckChart"),
    REPORT_HQLTYPE_CAMPAGIN("ReportCamapgin"),
    REPORT_HQLTYPE_CAMPAGIN_COUNT("ReportCamapginCount"),
    REPORT_HQLTYPE_CAMPAGIN_CHART("ReportCamapginChart"),
    REPORT_HQLTYPE_ADGROUP("ReportAdgroup"),
    REPORT_HQLTYPE_ADGROUP_COUNT("ReportAdgroupCount"),
    REPORT_HQLTYPE_ADGROUP_CHART("ReportAdgroupChart"),
    REPORT_HQLTYPE_ADMOBILE("ReportAdMobile"),
    REPORT_HQLTYPE_ADMOBILE_COUNT("ReportAdMobileCount"),
    REPORT_HQLTYPE_ADMOBILE_CHART("ReportAdMobileChart"),
	REPORT_HQLTYPE_CUSTINVOICE("ReportCustInvoice"),
    REPORT_HQLTYPE_CUSTINVOICE_COUNT("ReportCustInvoiceCount"),
    REPORT_HQLTYPE_CUSTINVOICE_MONTHDATA("ReportCustInvoiceMonthData"),
    REPORT_HQLTYPE_CUSTINVOICE_PREMONTHSUM("ReportCustInvoicePreMonthSum"),
    REPORT_HQLTYPE_CUSTVALUE("ReportCustValue"),
    REPORT_HQLTYPE_CUSTVALUE_COUNT("ReportCustValueCount"),
	REPORT_PAYTYPE_CARD("1"),
	REPORT_PAYTYPE_AD("2"),
	REPORT_PAYTYPE_FAILCLICK("3"),
	REPORT_PAYTYPE_CW_CARD("信用卡金額"),
	REPORT_PAYTYPE_CW_AD("廣告點閱費"),
	REPORT_PAYTYPE_CW_FAILCLICK("費用調整(無效點擊)");
	
    
	
    
    private String textValue;
    
    EnumReport(String textValue) {
        this.textValue = textValue;
    }

    public String getTextValue() {
        return textValue;
    }
}
