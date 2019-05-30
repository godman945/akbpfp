package com.pchome.akbpfp.db.service.report;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.pchome.akbpfp.db.dao.ad.IPfpAdActionDAO;
import com.pchome.akbpfp.db.dao.ad.IPfpAdGroupDAO;
import com.pchome.akbpfp.db.dao.ad.IPfpAdKeywordDAO;
import com.pchome.akbpfp.db.dao.report.AdGroupReportVO;
import com.pchome.akbpfp.db.dao.report.AdKeywordReportVO;
import com.pchome.akbpfp.db.dao.report.IAdKeywordReportDAO;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdKeyword;
import com.pchome.enumerate.ad.EnumAdKeywordType;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.utils.CommonUtils;

public class AdKeywordReportService implements IAdKeywordReportService {

	private IAdKeywordReportDAO adKeywordReportDAO;
	private IPfpAdKeywordDAO pfpAdKeywordDAO;
	private IPfpAdActionDAO pfpAdActionDAO;
	private IPfpAdGroupDAO pfpAdGroupDAO;

	public void setAdKeywordReportDAO(IAdKeywordReportDAO adKeywordReportDAO) {
		this.adKeywordReportDAO = adKeywordReportDAO;
	}

	public void setPfpAdKeywordDAO(IPfpAdKeywordDAO pfpAdKeywordDAO) {
		this.pfpAdKeywordDAO = pfpAdKeywordDAO;
	}

	public void setPfpAdActionDAO(IPfpAdActionDAO pfpAdActionDAO) {
		this.pfpAdActionDAO = pfpAdActionDAO;
	}

	public void setPfpAdGroupDAO(IPfpAdGroupDAO pfpAdGroupDAO) {
		this.pfpAdGroupDAO = pfpAdGroupDAO;
	}

//	public List<AdKeywordReportVO> loadReportDate(String sqlType, String adGroupId, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String startDate, String endDate, int page, int pageSize) {
//		return adKeywordReportDAO.getReportList(sqlType, adGroupId, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate, page, pageSize);
//	}

	/**
	 * 關鍵字成效(明細)
	 * @throws Exception 
	 */
	@Override
	public List<AdKeywordReportVO> queryReportAdKeywordData(AdKeywordReportVO vo) throws Exception {
		List<Map<String, Object>> adKeywordList = adKeywordReportDAO.getAdKeywordList(vo);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		long nowTime = new Date().getTime();
		
		Map<String, String> adStatusMap = CommonUtils.getInstance().getAdStatusMap();

		List<AdKeywordReportVO> adKeywordVOList = new ArrayList<>();
//		List<AdKeywordReportVO> subtotalList = new ArrayList<>(); // 小計列
//		List<AdKeywordReportVO> detailList = new ArrayList<>(); // 明細列
		
		for (Map<String, Object> dataMap : adKeywordList) {
			AdKeywordReportVO adKeywordReportVO = new AdKeywordReportVO();
			
			// 查詢平均廣告排名值，帳號、開始日期、結束日期
			adKeywordReportVO.setCustomerInfoId(vo.getCustomerInfoId());
			adKeywordReportVO.setStartDate(vo.getStartDate());
			adKeywordReportVO.setEndDate(vo.getEndDate());
			
			String adActionSeq = (String) dataMap.get("ad_action_seq");
			PfpAdAction pfpAdAction = pfpAdActionDAO.getPfpAdActionBySeq(adActionSeq);
			int adActionStatus = pfpAdAction.getAdActionStatus();
			
			String adGroupSeq = (String) dataMap.get("ad_group_seq");
			PfpAdGroup pfpAdGroup = pfpAdGroupDAO.getPfpAdGroupBySeq(adGroupSeq);
			int adGroupStatus = pfpAdGroup.getAdGroupStatus();
			
			String adKeywordSeq = (String) dataMap.get("ad_keyword_seq");
			PfpAdKeyword pfpAdKeyword = pfpAdKeywordDAO.getPfpAdKeywordBySeq(adKeywordSeq);
			int adKeywordStatus = pfpAdKeyword.getAdKeywordStatus();
			
			//廣告狀態為開啟的話必須判斷走期( 待播放 or 走期中 or 已結束 )
			String adActionStartDate = dateFormat2.format(pfpAdAction.getAdActionStartDate());
			String adActionEndDate = dateFormat2.format(pfpAdAction.getAdActionEndDate());
			if (adActionStatus == EnumStatus.Open.getStatusId()) {
				long startDate = (dateFormat.parse(adActionStartDate + " 00:00:00")).getTime();
				long endDate = (dateFormat.parse(adActionEndDate + " 23:59:59")).getTime();
				if (nowTime < startDate) {
					adActionStatus = EnumStatus.Waitbroadcast.getStatusId();
				} else if (nowTime > endDate) {
					adActionStatus = EnumStatus.End.getStatusId();
				} else {
					adActionStatus = EnumStatus.Broadcast.getStatusId();
				}
			}

			//播放狀態
			String alter = "";
			if ((adActionStatus == EnumStatus.Broadcast.getStatusId())
					&& (adGroupStatus == EnumStatus.Open.getStatusId())
					&& (adKeywordStatus == EnumStatus.Open.getStatusId())) {
				alter = "走期中";
				adKeywordReportVO.setAdStatusOnOff(true); // on亮綠燈
			} else if ((adActionStatus == EnumStatus.Broadcast.getStatusId())
					&& (adGroupStatus == EnumStatus.Open.getStatusId())
					&& (adKeywordStatus != EnumStatus.Open.getStatusId())) {
				alter = "關鍵字" + adStatusMap.get(Integer.toString(adKeywordStatus));
			} else if ((adActionStatus == EnumStatus.Broadcast.getStatusId())
					&& (adGroupStatus != EnumStatus.Open.getStatusId())
					&& (adKeywordStatus != EnumStatus.Open.getStatusId())) {
				alter = "分類" + adStatusMap.get(Integer.toString(adGroupStatus)) + 
						"，關鍵字" + adStatusMap.get(Integer.toString(adKeywordStatus));
			} else if ((adActionStatus == EnumStatus.Broadcast.getStatusId())
					&& (adGroupStatus != EnumStatus.Open.getStatusId())
					&& (adKeywordStatus == EnumStatus.Open.getStatusId())) {
				alter = "分類" + adStatusMap.get(Integer.toString(adGroupStatus));
			} else if ((adActionStatus != EnumStatus.Broadcast.getStatusId())
					&& (adGroupStatus == EnumStatus.Open.getStatusId())
					&& (adKeywordStatus == EnumStatus.Open.getStatusId())) {
				alter = "廣告" + adStatusMap.get(Integer.toString(adActionStatus));
			} else if ((adActionStatus != EnumStatus.Broadcast.getStatusId())
					&& (adGroupStatus == EnumStatus.Open.getStatusId())
					&& (adKeywordStatus != EnumStatus.Open.getStatusId())) {
				alter = "廣告" + adStatusMap.get(Integer.toString(adActionStatus)) + 
						"，關鍵字" + adStatusMap.get(Integer.toString(adKeywordStatus));
			} else if ((adActionStatus != EnumStatus.Broadcast.getStatusId())
					&& (adGroupStatus != EnumStatus.Open.getStatusId())
					&& (adKeywordStatus == EnumStatus.Open.getStatusId())) {
				alter = "廣告" + adStatusMap.get(Integer.toString(adActionStatus)) + 
						"，分類" + adStatusMap.get(Integer.toString(adGroupStatus));
			} else if ((adActionStatus != EnumStatus.Broadcast.getStatusId())
					&& (adGroupStatus != EnumStatus.Open.getStatusId())
					&& (adKeywordStatus != EnumStatus.Open.getStatusId())) {
				alter = "廣告" + adStatusMap.get(Integer.toString(adActionStatus)) + 
						"，分類" + adStatusMap.get(Integer.toString(adGroupStatus)) + 
						"，關鍵字" + adStatusMap.get(Integer.toString(adKeywordStatus));
			}
			adKeywordReportVO.setAdStatusName(alter); // 產excel報表使用
			
			// 關鍵字
			adKeywordReportVO.setAdKeyword((String) dataMap.get("ad_keyword"));
			
			// 廣告活動
			adKeywordReportVO.setAdActionName(pfpAdAction.getAdActionName());
			
			// 廣告分類
			adKeywordReportVO.setAdGroupName(pfpAdGroup.getAdGroupName());
			
			// 裝置
			String adDevice = "全部";
			if (vo.getWhereMap() != null) {
				adDevice = CommonUtils.getInstance().getAdDeviceName(vo.getWhereMap());
			}
			adKeywordReportVO.setAdDevice(adDevice);
			
			// 廣泛比對-開啟狀態
			if(pfpAdKeyword.getAdKeywordOpen() == 1) {
				adKeywordReportVO.setKwOpen(true);
				adKeywordReportVO.setKwOpenName("開啟");
			}
			
			// 廣泛比對-曝光數
			BigDecimal adKeywordPvSum = (BigDecimal) dataMap.get("ad_keyword_pv_sum");
			adKeywordReportVO.setKwPvSum(adKeywordPvSum);
			
			// 廣泛比對-互動數
			BigDecimal adKeywordClkSum = (BigDecimal) dataMap.get("ad_keyword_clk_sum");
			adKeywordReportVO.setKwClkSum(adKeywordClkSum);
			
			// 廣泛比對-互動率,互動率 = 總互動數 / 總曝光數 * 100
			adKeywordReportVO.setKwCtrSum(CommonUtils.getInstance().getCalculateDivisionValue(adKeywordClkSum, adKeywordPvSum, 100));
			
			// 廣泛比對-費用
			BigDecimal adKeywordClkPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_keyword_clk_price_sum"));
			adKeywordReportVO.setKwPriceSum(adKeywordClkPriceSum.doubleValue());
			
			// 廣泛比對-單次互動費用,單次互動費用 = 總費用 / 總互動次數
			adKeywordReportVO.setKwPriceAvgSum(CommonUtils.getInstance().getCalculateDivisionValue(adKeywordClkPriceSum, adKeywordClkSum));
			
			// 廣泛比對-千次曝光費用,千次曝光費用 = 總費用 / 曝光數 * 1000
			Double adKeywordKiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adKeywordClkPriceSum, adKeywordPvSum, 1000);
			BigDecimal bigDecimal = BigDecimal.valueOf(adKeywordKiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
			adKeywordReportVO.setKwKiloCost(bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
			
			adKeywordReportVO.setAdKeywordSeq(adKeywordSeq); // 關鍵字序號(取得平均廣告排名sql用)
			
			// 廣泛比對-平均廣告排名
			adKeywordReportVO.setKeywordStyle(EnumAdKeywordType.WIDELY.getStyle()); // 取得平均廣告排名sql用
			List<Map<String, Object>> kwRankAvg = adKeywordReportDAO.getAdKeywordRank(adKeywordReportVO);
			adKeywordReportVO.setKwRankAvg((Double) kwRankAvg.get(0).get("ad_rank_avg"));
			
			if (adKeywordReportVO.getKwPvSum().compareTo(BigDecimal.ZERO) >= 1
					|| adKeywordReportVO.getKwClkSum().compareTo(BigDecimal.ZERO) >= 1
					|| BigDecimal.valueOf(adKeywordReportVO.getKwCtrSum()).compareTo(BigDecimal.ZERO) >= 1
					|| BigDecimal.valueOf(adKeywordReportVO.getKwPriceAvgSum()).compareTo(BigDecimal.ZERO) >= 1
					|| BigDecimal.valueOf(adKeywordReportVO.getKwKiloCost()).compareTo(BigDecimal.ZERO) >= 1
					|| BigDecimal.valueOf(adKeywordReportVO.getKwPriceSum()).compareTo(BigDecimal.ZERO) >= 1
					|| BigDecimal.valueOf(adKeywordReportVO.getKwRankAvg()).compareTo(BigDecimal.ZERO) >= 1) {
				// 廣泛比對列有值才顯示該列
				adKeywordReportVO.setKwRowShowHidden(true);
			}
			
			
			// 詞組比對-開啟狀態
			if(pfpAdKeyword.getAdKeywordPhraseOpen() == 1) {
				adKeywordReportVO.setKwPhrOpen(true);
				adKeywordReportVO.setKwPhrOpenName("開啟");
			}
			
			// 詞組比對-曝光數
			BigDecimal adKeywordPhrasePvSum = (BigDecimal) dataMap.get("ad_keyword_phrase_pv_sum");
			adKeywordReportVO.setKwPhrPvSum(adKeywordPhrasePvSum);
			
			// 詞組比對-互動數
			BigDecimal adKeywordPhraseClkSum = (BigDecimal) dataMap.get("ad_keyword_phrase_clk_sum");
			adKeywordReportVO.setKwPhrClkSum(adKeywordPhraseClkSum);
			
			// 詞組比對-互動率,互動率 = 總互動數 / 總曝光數 * 100
			adKeywordReportVO.setKwPhrCtrSum(CommonUtils.getInstance().getCalculateDivisionValue(adKeywordPhraseClkSum, adKeywordPhrasePvSum, 100));
			
			// 詞組比對-費用
			BigDecimal adKeywordPhraseClkPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_keyword_phrase_clk_price_sum"));
			adKeywordReportVO.setKwPhrPriceSum(adKeywordPhraseClkPriceSum.doubleValue());
			
			// 詞組比對-單次互動費用,單次互動費用 = 總費用 / 總互動次數
			adKeywordReportVO.setKwPhrPriceAvgSum(CommonUtils.getInstance().getCalculateDivisionValue(adKeywordPhraseClkPriceSum, adKeywordPhraseClkSum));
			
			// 詞組比對-千次曝光費用,千次曝光費用 = 總費用 / 曝光數 * 1000
			Double adKeywordPhraseKiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adKeywordPhraseClkPriceSum, adKeywordPhrasePvSum, 1000);
			BigDecimal phraseBigDecimal = BigDecimal.valueOf(adKeywordPhraseKiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
			adKeywordReportVO.setKwPhrKiloCost(phraseBigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
			
			// 詞組比對-平均廣告排名
			adKeywordReportVO.setKeywordStyle(EnumAdKeywordType.PHRASE.getStyle()); // 取得平均廣告排名sql用
			List<Map<String, Object>> kwPhrRankAvg = adKeywordReportDAO.getAdKeywordRank(adKeywordReportVO);
			adKeywordReportVO.setKwPhrRankAvg((Double) kwPhrRankAvg.get(0).get("ad_rank_avg"));
			
			if (adKeywordReportVO.getKwPhrPvSum().compareTo(BigDecimal.ZERO) >= 1
					|| adKeywordReportVO.getKwPhrClkSum().compareTo(BigDecimal.ZERO) >= 1
					|| BigDecimal.valueOf(adKeywordReportVO.getKwPhrCtrSum()).compareTo(BigDecimal.ZERO) >= 1
					|| BigDecimal.valueOf(adKeywordReportVO.getKwPhrPriceAvgSum()).compareTo(BigDecimal.ZERO) >= 1
					|| BigDecimal.valueOf(adKeywordReportVO.getKwPhrKiloCost()).compareTo(BigDecimal.ZERO) >= 1
					|| BigDecimal.valueOf(adKeywordReportVO.getKwPhrPriceSum()).compareTo(BigDecimal.ZERO) >= 1
					|| BigDecimal.valueOf(adKeywordReportVO.getKwPhrRankAvg()).compareTo(BigDecimal.ZERO) >= 1) {
				// 詞組比對列有值才顯示該列
				adKeywordReportVO.setKwPhrRowShowHidden(true);
			}
			
			
			// 精準比對-開啟狀態
			if(pfpAdKeyword.getAdKeywordPrecisionOpen() == 1) {
				adKeywordReportVO.setKwPreOpen(true);
				adKeywordReportVO.setKwPreOpenName("開啟");
			}
			
			// 精準比對-曝光數
			BigDecimal adKeywordPrecisionPvSum = (BigDecimal) dataMap.get("ad_keyword_precision_pv_sum");
			adKeywordReportVO.setKwPrePvSum(adKeywordPrecisionPvSum);
			
			// 精準比對-互動數
			BigDecimal adKeywordPrecisionClkSum = (BigDecimal) dataMap.get("ad_keyword_precision_clk_sum");
			adKeywordReportVO.setKwPreClkSum(adKeywordPrecisionClkSum);
			
			// 精準比對-互動率,互動率 = 總互動數 / 總曝光數 * 100
			adKeywordReportVO.setKwPreCtrSum(CommonUtils.getInstance().getCalculateDivisionValue(adKeywordPrecisionClkSum, adKeywordPrecisionPvSum, 100));
			
			// 精準比對-費用
			BigDecimal adKeywordPrecisionClkPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_keyword_precision_clk_price_sum"));
			adKeywordReportVO.setKwPrePriceSum(adKeywordPrecisionClkPriceSum.doubleValue());
			
			// 精準比對-單次互動費用,單次互動費用 = 總費用 / 總互動次數
			adKeywordReportVO.setKwPrePriceAvgSum(CommonUtils.getInstance().getCalculateDivisionValue(adKeywordPrecisionClkPriceSum, adKeywordPrecisionClkSum));
			
			// 精準比對-千次曝光費用,千次曝光費用 = 總費用 / 曝光數 * 1000
			Double adKeywordPrecisionKiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adKeywordPrecisionClkPriceSum, adKeywordPrecisionPvSum, 1000);
			BigDecimal precisionBigDecimal = BigDecimal.valueOf(adKeywordPrecisionKiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
			adKeywordReportVO.setKwPreKiloCost(precisionBigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
			
			// 精準比對-平均廣告排名
			adKeywordReportVO.setKeywordStyle(EnumAdKeywordType.PRECISION.getStyle()); // 取得平均廣告排名sql用
			List<Map<String, Object>> kwPreRankAvg = adKeywordReportDAO.getAdKeywordRank(adKeywordReportVO);
			adKeywordReportVO.setKwPreRankAvg((Double) kwPreRankAvg.get(0).get("ad_rank_avg"));
			
			if (adKeywordReportVO.getKwPrePvSum().compareTo(BigDecimal.ZERO) >= 1
					|| adKeywordReportVO.getKwPreClkSum().compareTo(BigDecimal.ZERO) >= 1
					|| BigDecimal.valueOf(adKeywordReportVO.getKwPreCtrSum()).compareTo(BigDecimal.ZERO) >= 1
					|| BigDecimal.valueOf(adKeywordReportVO.getKwPrePriceAvgSum()).compareTo(BigDecimal.ZERO) >= 1
					|| BigDecimal.valueOf(adKeywordReportVO.getKwPreKiloCost()).compareTo(BigDecimal.ZERO) >= 1
					|| BigDecimal.valueOf(adKeywordReportVO.getKwPrePriceSum()).compareTo(BigDecimal.ZERO) >= 1
					|| BigDecimal.valueOf(adKeywordReportVO.getKwPreRankAvg()).compareTo(BigDecimal.ZERO) >= 1) {
				// 精準比對列有值才顯示該列
				adKeywordReportVO.setKwPreRowShowHidden(true);
			}
			
			
			// 小計-曝光數
			BigDecimal kwPvSubtotal = new BigDecimal(0);
			kwPvSubtotal = kwPvSubtotal.add(adKeywordPvSum).add(adKeywordPhrasePvSum).add(adKeywordPrecisionPvSum);
			adKeywordReportVO.setKwPvSubtotal(kwPvSubtotal);
			
			// 小計-互動數
			BigDecimal kwClkSubtotal = new BigDecimal(0);
			kwClkSubtotal = kwClkSubtotal.add(adKeywordClkSum).add(adKeywordPhraseClkSum).add(adKeywordPrecisionClkSum);
			adKeywordReportVO.setKwClkSubtotal(kwClkSubtotal);
			
			// 小計-互動率,互動率 = 總互動數 / 總曝光數 * 100
			adKeywordReportVO.setKwCtrSubtotal(CommonUtils.getInstance().getCalculateDivisionValue(kwClkSubtotal, kwPvSubtotal, 100));
			
			// 小計-費用
			BigDecimal kwPriceSubtotal = new BigDecimal(0);
			kwPriceSubtotal = kwPriceSubtotal.add(adKeywordClkPriceSum).add(adKeywordPhraseClkPriceSum).add(adKeywordPrecisionClkPriceSum);
			adKeywordReportVO.setKwPriceSubtotal(kwPriceSubtotal);
			
			// 小計-單次互動費用,單次互動費用 = 總費用 / 總互動次數
			adKeywordReportVO.setKwPriceAvgSubtotal(CommonUtils.getInstance().getCalculateDivisionValue(kwPriceSubtotal, kwClkSubtotal));
			
			// 小計-千次曝光費用,千次曝光費用 = 總費用 / 曝光數 * 1000
			Double kwKiloCostSubtotal = CommonUtils.getInstance().getCalculateDivisionValue(kwPriceSubtotal, kwPvSubtotal, 1000);
			BigDecimal subtotalBigDecimal = BigDecimal.valueOf(kwKiloCostSubtotal); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
			adKeywordReportVO.setKwKiloCostSubtotal(subtotalBigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
			
//			detailList.add(adKeywordReportVO);
//			subtotalList.add(adKeywordReportVO);
			adKeywordVOList.add(adKeywordReportVO);
		}
		
		// 處理排序
		if (StringUtils.isNotBlank(vo.getSortBy())) {
//			CommonUtils.getInstance().sort(subtotalList, vo.getSortBy().split("-")[0], vo.getSortBy().split("-")[1]);
			CommonUtils.getInstance().sort(adKeywordVOList, vo.getSortBy().split("-")[0], vo.getSortBy().split("-")[1]);
		}
		
//		Map<String, List<AdKeywordReportVO>> resultData = new LinkedHashMap<>();
//		resultData.put("subtotalList", subtotalList); // 小計列
//		resultData.put("detailList", detailList); // 明細列
		
//		return resultData;
		
		return adKeywordVOList;
	}

	/**
	 * 關鍵字成效(加總)
	 */
	@Override
	public List<AdKeywordReportVO> queryReportAdKeywordSumData(AdKeywordReportVO vo) {
		List<Map<String, Object>> adKeywordListSum = adKeywordReportDAO.getAdKeywordListSum(vo);
		
		// 畫面查詢結果總計列用
		BigDecimal pvTotal = new BigDecimal(0); // 曝光數
		BigDecimal clkTotal = new BigDecimal(0); // 互動數
		BigDecimal priceTotal = new BigDecimal(0); // 費用
		
		// 報表總計列用
		BigDecimal reportKwPvTotal = new BigDecimal(0); // 廣泛比對-曝光數
		BigDecimal reportKwPhrPvTotal = new BigDecimal(0); // 詞組比對-曝光數
		BigDecimal reportKwPrePvTotal = new BigDecimal(0); // 精準比對-曝光數
		BigDecimal reportKwClkTotal = new BigDecimal(0); // 廣泛比對-互動數
		BigDecimal reportKwPhrClkTotal = new BigDecimal(0); // 詞組比對-互動數
		BigDecimal reportKwPreClkTotal = new BigDecimal(0); // 精準比對-互動數
		BigDecimal reportKwPriceTotal = new BigDecimal(0); // 廣泛比對-費用
		BigDecimal reportKwPhrPriceTotal = new BigDecimal(0); // 詞組比對-費用
		BigDecimal reportKwPrePriceTotal = new BigDecimal(0); // 精準比對-費用
		
		List<AdKeywordReportVO> adKeywordVOListSum = new ArrayList<>();
		// 加總
		for (Map<String, Object> dataMap : adKeywordListSum) {
			BigDecimal adKeywordPvSum = (BigDecimal) dataMap.get("ad_keyword_pv_sum"); // DB資料廣泛比對-曝光數
			BigDecimal adKeywordPhrasePvSum = (BigDecimal) dataMap.get("ad_keyword_phrase_pv_sum"); // DB資料詞組比對-曝光數
			BigDecimal adKeywordPrecisionPvSum = (BigDecimal) dataMap.get("ad_keyword_precision_pv_sum"); // DB資料精準比對-曝光數
			BigDecimal adKeywordClkSum = (BigDecimal) dataMap.get("ad_keyword_clk_sum"); // DB資料廣泛比對-互動數
			BigDecimal adKeywordPhraseClkSum = (BigDecimal) dataMap.get("ad_keyword_phrase_clk_sum"); // DB資料詞組比對-互動數
			BigDecimal adKeywordPrecisionClkSum = (BigDecimal) dataMap.get("ad_keyword_precision_clk_sum"); // DB資料精準比對-互動數
			BigDecimal adKeywordClkPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_keyword_clk_price_sum")); // DB資料廣泛比對-費用
			BigDecimal adKeywordPhraseClkPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_keyword_phrase_clk_price_sum")); // DB資料詞組比對-費用
			BigDecimal adKeywordPrecisionClkPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_keyword_precision_clk_price_sum")); // DB資料精準比對-費用
			
			// 畫面查詢結果總計列用
			// 曝光數總計 = 廣泛比對-曝光數 + 詞組比對-曝光數 + 精準比對-曝光數
			pvTotal = pvTotal.add(adKeywordPvSum).add(adKeywordPhrasePvSum).add(adKeywordPrecisionPvSum);
			// 互動數總計 = 廣泛比對-互動數 + 詞組比對-互動數 + 精準比對-互動數
			clkTotal = clkTotal.add(adKeywordClkSum).add(adKeywordPhraseClkSum).add(adKeywordPrecisionClkSum);
			// 費用總計 = 廣泛比對-費用 + 詞組比對-費用 + 精準比對-費用
			priceTotal = priceTotal.add(adKeywordClkPriceSum).add(adKeywordPhraseClkPriceSum).add(adKeywordPrecisionClkPriceSum);
			
			// 報表總計列用
			if (vo.isDownloadOrIsNotCuttingPagination()) {
				reportKwPvTotal = reportKwPvTotal.add(adKeywordPvSum); // 廣泛比對-曝光數總計
				reportKwPhrPvTotal = reportKwPhrPvTotal.add(adKeywordPhrasePvSum); // 詞組比對-曝光數總計
				reportKwPrePvTotal = reportKwPrePvTotal.add(adKeywordPrecisionPvSum); // 精準比對-曝光數總計
				reportKwClkTotal = reportKwClkTotal.add(adKeywordClkSum); // 廣泛比對-互動數總計
				reportKwPhrClkTotal = reportKwPhrClkTotal.add(adKeywordPhraseClkSum); // 詞組比對-互動數總計
				reportKwPreClkTotal = reportKwPreClkTotal.add(adKeywordPrecisionClkSum); // 精準比對-互動數總計
				reportKwPriceTotal = reportKwPriceTotal.add(adKeywordClkPriceSum); // 廣泛比對-費用總計
				reportKwPhrPriceTotal = reportKwPhrPriceTotal.add(adKeywordPhraseClkPriceSum); // 詞組比對-費用總計
				reportKwPrePriceTotal = reportKwPrePriceTotal.add(adKeywordPrecisionClkPriceSum); // 精準比對-費用總計
			}
		}
		
		AdKeywordReportVO adKeywordReportVO = new AdKeywordReportVO();
		
		// 畫面查詢結果總計列用
		adKeywordReportVO.setPvTotal(pvTotal); // 曝光數
		adKeywordReportVO.setClkTotal(clkTotal); // 互動數
		// 互動率 = 總互動數 / 總曝光數 * 100
		adKeywordReportVO.setCtrTotal(CommonUtils.getInstance().getCalculateDivisionValue(clkTotal, pvTotal, 100));
		adKeywordReportVO.setPriceTotal(priceTotal.doubleValue()); // 費用
		// 單次互動費用 = 總費用 / 總互動次數
		adKeywordReportVO.setPriceAvgTotal(CommonUtils.getInstance().getCalculateDivisionValue(priceTotal, clkTotal));
		// 千次曝光費用 = 總費用 / 曝光數 * 1000
		Double kiloCostTotal = CommonUtils.getInstance().getCalculateDivisionValue(priceTotal, pvTotal, 1000);
		BigDecimal bg = BigDecimal.valueOf(kiloCostTotal); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
		adKeywordReportVO.setKiloCostTotal(bg.setScale(2, RoundingMode.HALF_UP).doubleValue());
		
		// 報表總計列用
		if (vo.isDownloadOrIsNotCuttingPagination()) {
			adKeywordReportVO.setReportKwPvTotal(reportKwPvTotal); // 廣泛比對-曝光數總計
			adKeywordReportVO.setReportKwPhrPvTotal(reportKwPhrPvTotal); // 詞組比對-曝光數總計
			adKeywordReportVO.setReportKwPrePvTotal(reportKwPrePvTotal); // 精準比對-曝光數總計
			adKeywordReportVO.setReportKwClkTotal(reportKwClkTotal); // 廣泛比對-互動數總計
			adKeywordReportVO.setReportKwPhrClkTotal(reportKwPhrClkTotal); // 詞組比對-互動數總計
			adKeywordReportVO.setReportKwPreClkTotal(reportKwPreClkTotal); // 精準比對-互動數總計
			// 互動率 = 總互動數 / 總曝光數 * 100
			adKeywordReportVO.setReportKwCtrTotal(CommonUtils.getInstance().getCalculateDivisionValue(reportKwClkTotal, reportKwPvTotal, 100)); // 廣泛比對-互動率
			adKeywordReportVO.setReportKwPhrCtrTotal(CommonUtils.getInstance().getCalculateDivisionValue(reportKwPhrClkTotal, reportKwPhrPvTotal, 100)); // 詞組比對-互動率
			adKeywordReportVO.setReportKwPreCtrTotal(CommonUtils.getInstance().getCalculateDivisionValue(reportKwPreClkTotal, reportKwPrePvTotal, 100)); // 精準比對-互動率
			// 單次互動費用 = 總費用 / 總互動次數
			adKeywordReportVO.setReportKwPriceAvgTotal(CommonUtils.getInstance().getCalculateDivisionValue(reportKwPriceTotal, reportKwClkTotal)); // 廣泛比對-單次互動費用
			adKeywordReportVO.setReportKwPhrPriceAvgTotal(CommonUtils.getInstance().getCalculateDivisionValue(reportKwPhrPriceTotal, reportKwPhrClkTotal)); // 詞組比對-單次互動費用
			adKeywordReportVO.setReportKwPrePriceAvgTotal(CommonUtils.getInstance().getCalculateDivisionValue(reportKwPrePriceTotal, reportKwPreClkTotal)); // 精準比對-單次互動費用
			adKeywordReportVO.setReportKwPriceTotal(reportKwPriceTotal.doubleValue()); // 廣泛比對-費用總計
			adKeywordReportVO.setReportKwPhrPriceTotal(reportKwPhrPriceTotal.doubleValue()); // 詞組比對-費用總計
			adKeywordReportVO.setReportKwPrePriceTotal(reportKwPrePriceTotal.doubleValue()); // 精準比對-費用總計
		}
		
		// 總計幾筆
		adKeywordReportVO.setRowCount(adKeywordListSum.size());
		vo.setRowCount(adKeywordListSum.size()); // 計算底下頁碼用
		
		adKeywordVOListSum.add(adKeywordReportVO);
		
		return adKeywordVOListSum;
	}

	/**
	 * 關鍵字成效(圖表)
	 */
	@Override
	public List<AdKeywordReportVO> queryReportAdKeywordChartData(AdKeywordReportVO vo) {
		List<Map<String, Object>> adKeywordList = adKeywordReportDAO.getAdKeywordListChart(vo);
		
		List<AdKeywordReportVO> adKeywordVOList = new ArrayList<>();
		
		for (Map<String, Object> dataMap : adKeywordList) {
			AdKeywordReportVO adKeywordReportVO = new AdKeywordReportVO();
			
			// 日期
			adKeywordReportVO.setReportDate((Date) dataMap.get("ad_keyword_pvclk_date"));
			
			// 廣泛比對-曝光數
			BigDecimal adKeywordPvSum = (BigDecimal) dataMap.get("ad_keyword_pv_sum");
			adKeywordReportVO.setKwPvSum(adKeywordPvSum);
			
			// 廣泛比對-互動數
			BigDecimal adKeywordClkSum = (BigDecimal) dataMap.get("ad_keyword_clk_sum");
			adKeywordReportVO.setKwClkSum(adKeywordClkSum);
			
			// 廣泛比對-互動率,互動率 = 總互動數 / 總曝光數 * 100
			adKeywordReportVO.setKwCtrSum(CommonUtils.getInstance().getCalculateDivisionValue(adKeywordClkSum, adKeywordPvSum, 100));
			
			// 廣泛比對-費用
			BigDecimal adKeywordClkPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_keyword_clk_price_sum"));
			adKeywordReportVO.setKwPriceSum(adKeywordClkPriceSum.doubleValue());
			
			// 廣泛比對-單次互動費用,單次互動費用 = 總費用 / 總互動次數
			adKeywordReportVO.setKwPriceAvgSum(CommonUtils.getInstance().getCalculateDivisionValue(adKeywordClkPriceSum, adKeywordClkSum));
			
			// 廣泛比對-千次曝光費用,千次曝光費用 = 總費用 / 曝光數 * 1000
			Double adKeywordKiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adKeywordClkPriceSum, adKeywordPvSum, 1000);
			BigDecimal bigDecimal = BigDecimal.valueOf(adKeywordKiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
			adKeywordReportVO.setKwKiloCost(bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
			
			// 廣泛比對-平均廣告排名
			adKeywordReportVO.setKeywordStyle(EnumAdKeywordType.WIDELY.getStyle()); // 取得平均廣告排名sql用
			List<Map<String, Object>> kwRankAvg = adKeywordReportDAO.getAdKeywordRank(adKeywordReportVO);
			adKeywordReportVO.setKwRankAvg((Double) kwRankAvg.get(0).get("ad_rank_avg"));
			

			
			// 詞組比對-曝光數
			BigDecimal adKeywordPhrasePvSum = (BigDecimal) dataMap.get("ad_keyword_phrase_pv_sum");
			adKeywordReportVO.setKwPhrPvSum(adKeywordPhrasePvSum);
			
			// 詞組比對-互動數
			BigDecimal adKeywordPhraseClkSum = (BigDecimal) dataMap.get("ad_keyword_phrase_clk_sum");
			adKeywordReportVO.setKwPhrClkSum(adKeywordPhraseClkSum);
			
			// 詞組比對-互動率,互動率 = 總互動數 / 總曝光數 * 100
			adKeywordReportVO.setKwPhrCtrSum(CommonUtils.getInstance().getCalculateDivisionValue(adKeywordPhraseClkSum, adKeywordPhrasePvSum, 100));
			
			// 詞組比對-費用
			BigDecimal adKeywordPhraseClkPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_keyword_phrase_clk_price_sum"));
			adKeywordReportVO.setKwPhrPriceSum(adKeywordPhraseClkPriceSum.doubleValue());
			
			// 詞組比對-單次互動費用,單次互動費用 = 總費用 / 總互動次數
			adKeywordReportVO.setKwPhrPriceAvgSum(CommonUtils.getInstance().getCalculateDivisionValue(adKeywordPhraseClkPriceSum, adKeywordPhraseClkSum));
			
			// 詞組比對-千次曝光費用,千次曝光費用 = 總費用 / 曝光數 * 1000
			Double adKeywordPhraseKiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adKeywordPhraseClkPriceSum, adKeywordPhrasePvSum, 1000);
			BigDecimal phraseBigDecimal = BigDecimal.valueOf(adKeywordPhraseKiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
			adKeywordReportVO.setKwPhrKiloCost(phraseBigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
			
			// 詞組比對-平均廣告排名
			adKeywordReportVO.setKeywordStyle(EnumAdKeywordType.PHRASE.getStyle()); // 取得平均廣告排名sql用
			List<Map<String, Object>> kwPhrRankAvg = adKeywordReportDAO.getAdKeywordRank(adKeywordReportVO);
			adKeywordReportVO.setKwPhrRankAvg((Double) kwPhrRankAvg.get(0).get("ad_rank_avg"));
			
			
			
			
			// 精準比對-曝光數
			BigDecimal adKeywordPrecisionPvSum = (BigDecimal) dataMap.get("ad_keyword_precision_pv_sum");
			adKeywordReportVO.setKwPrePvSum(adKeywordPrecisionPvSum);
			
			// 精準比對-互動數
			BigDecimal adKeywordPrecisionClkSum = (BigDecimal) dataMap.get("ad_keyword_precision_clk_sum");
			adKeywordReportVO.setKwPreClkSum(adKeywordPrecisionClkSum);
			
			// 精準比對-互動率,互動率 = 總互動數 / 總曝光數 * 100
			adKeywordReportVO.setKwPreCtrSum(CommonUtils.getInstance().getCalculateDivisionValue(adKeywordPrecisionClkSum, adKeywordPrecisionPvSum, 100));
			
			// 精準比對-費用
			BigDecimal adKeywordPrecisionClkPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_keyword_precision_clk_price_sum"));
			adKeywordReportVO.setKwPrePriceSum(adKeywordPrecisionClkPriceSum.doubleValue());
			
			// 精準比對-單次互動費用,單次互動費用 = 總費用 / 總互動次數
			adKeywordReportVO.setKwPrePriceAvgSum(CommonUtils.getInstance().getCalculateDivisionValue(adKeywordPrecisionClkPriceSum, adKeywordPrecisionClkSum));
			
			// 精準比對-千次曝光費用,千次曝光費用 = 總費用 / 曝光數 * 1000
			Double adKeywordPrecisionKiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adKeywordPrecisionClkPriceSum, adKeywordPrecisionPvSum, 1000);
			BigDecimal precisionBigDecimal = BigDecimal.valueOf(adKeywordPrecisionKiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
			adKeywordReportVO.setKwPreKiloCost(precisionBigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
			
			// 精準比對-平均廣告排名
			adKeywordReportVO.setKeywordStyle(EnumAdKeywordType.PRECISION.getStyle()); // 取得平均廣告排名sql用
			List<Map<String, Object>> kwPreRankAvg = adKeywordReportDAO.getAdKeywordRank(adKeywordReportVO);
			adKeywordReportVO.setKwPreRankAvg((Double) kwPreRankAvg.get(0).get("ad_rank_avg"));
			
			
			
			// 小計-曝光數
			BigDecimal kwPvSubtotal = new BigDecimal(0);
			kwPvSubtotal = kwPvSubtotal.add(adKeywordPvSum).add(adKeywordPhrasePvSum).add(adKeywordPrecisionPvSum);
			adKeywordReportVO.setKwPvSubtotal(kwPvSubtotal);
			
			// 小計-互動數
			BigDecimal kwClkSubtotal = new BigDecimal(0);
			kwClkSubtotal = kwClkSubtotal.add(adKeywordClkSum).add(adKeywordPhraseClkSum).add(adKeywordPrecisionClkSum);
			adKeywordReportVO.setKwClkSubtotal(kwClkSubtotal);
			
			// 小計-互動率,互動率 = 總互動數 / 總曝光數 * 100
			adKeywordReportVO.setKwCtrSubtotal(CommonUtils.getInstance().getCalculateDivisionValue(kwClkSubtotal, kwPvSubtotal, 100));
			
			// 小計-費用
			BigDecimal kwPriceSubtotal = new BigDecimal(0);
			kwPriceSubtotal = kwPriceSubtotal.add(adKeywordClkPriceSum).add(adKeywordPhraseClkPriceSum).add(adKeywordPrecisionClkPriceSum);
			adKeywordReportVO.setKwPriceSubtotal(kwPriceSubtotal);
			
			// 小計-單次互動費用,單次互動費用 = 總費用 / 總互動次數
			adKeywordReportVO.setKwPriceAvgSubtotal(CommonUtils.getInstance().getCalculateDivisionValue(kwPriceSubtotal, kwClkSubtotal));
			
			// 小計-千次曝光費用,千次曝光費用 = 總費用 / 曝光數 * 1000
			Double kwKiloCostSubtotal = CommonUtils.getInstance().getCalculateDivisionValue(kwPriceSubtotal, kwPvSubtotal, 1000);
			BigDecimal subtotalBigDecimal = BigDecimal.valueOf(kwKiloCostSubtotal); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
			adKeywordReportVO.setKwKiloCostSubtotal(subtotalBigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
			
			adKeywordVOList.add(adKeywordReportVO);
		}
		
		return adKeywordVOList;
	}
}