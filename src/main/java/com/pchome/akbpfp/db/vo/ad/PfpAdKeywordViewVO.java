package com.pchome.akbpfp.db.vo.ad;

public class PfpAdKeywordViewVO {

	private String adActionSeq = "";
	private String adActionName = "";
	private String adType = "";
	private String adGroupSeq = "";
	private String adGroupName = "";
	private String adKeywordSeq = "";
	private String adKeyword = "";
	private int adKeywordStatus = 0;
	private String adKeywordStatusDesc = "";
	private float adActionMax = 0;					//最大出價
	
	//廣泛比對
	private int adKeywordOpen = 0;					//比對開起狀態
	private float adKeywordSearchPrice = 0;			//出價
	private float suggestPrice = 0;					//建議出價
	private int adKeywordPv = 0;					//曝光數
	private int adKeywordClk = 0;					//點選次數
	private float adKeywordClkRate = 0;				//點選率
	private float invalidClk = 0;					//無效點選次數
	private float adKeywordClkPriceAvg = 0;			//平均點選費用
	private float adKeywordClkPrice = 0;			//費用
	private float adKeywordRankAvg = 0;				//平均廣告排名
	private String adKeywordType = "";				//排行狀態
	
	//詞組比對
	private int adKeywordPhraseOpen = 0;			//比對開起狀態
	private float adKeywordSearchPhrasePrice = 0;	//出價
	private float suggestPhrasePrice = 0;			//建議出價
	private int adKeywordPhrasePv = 0;				//曝光數
	private int adKeywordPhraseClk = 0;				//點選次數
	private float adKeywordPhraseClkRate = 0;		//點選率
	private float phraseInvalidClk = 0;				//無效點選次數
	private float adKeywordPhraseClkPriceAvg = 0;	//平均點選費用
	private float adKeywordPhraseClkPrice = 0;		//費用
	private float adKeywordPhraseRankAvg = 0;		//平均廣告排名
	private String adKeywordPhraseType = "";		//排行狀態
	
	//精準比對
	private int adKeywordPrecisionOpen = 0;			//比對開起狀態
	private float adKeywordSearchPrecisionPrice = 0;//出價
	private float suggestPrecisionPrice = 0;		//建議出價
	private int adKeywordPrecisionPv = 0;			//曝光數
	private int adKeywordPrecisionClk = 0;			//點選次數
	private float adKeywordPrecisionClkRate = 0;	//點選率
	private float precisionInvalidClk = 0;			//無效點選次數
	private float adKeywordPrecisionClkPriceAvg = 0;//平均點選費用
	private float adKeywordPrecisionClkPrice = 0;	//費用
	private float adKeywordPrecisionRankAvg = 0;	//平均廣告排名
	private String adKeywordPrecisionType = "";		//排行狀態
	
	//總計
	private int adKeywordPvSum = 0;					//曝光數
	private int adKeywordClkSum = 0;				//點選次數
	private float adKeywordClkRateSum = 0;			//點選率
	private float invalidClkSum = 0;				//無效點選次數
	private float adKeywordClkPriceSumAvg = 0;		//平均點選費用
	private float adKeywordClkPriceSum = 0;			//費用
	
	//暫時沒用到的
	private float adKeywordChannelPrice = 0;
	private String adKeywordOrder = "";
	private float adKeywordPvPrice = 0;
	
	
	public String getAdActionSeq() {
		return adActionSeq;
	}
	public void setAdActionSeq(String adActionSeq) {
		this.adActionSeq = adActionSeq;
	}
	public String getAdActionName() {
		return adActionName;
	}
	public void setAdActionName(String adActionName) {
		this.adActionName = adActionName;
	}
	public String getAdType() {
		return adType;
	}
	public void setAdType(String adType) {
		this.adType = adType;
	}
	public String getAdGroupSeq() {
		return adGroupSeq;
	}
	public void setAdGroupSeq(String adGroupSeq) {
		this.adGroupSeq = adGroupSeq;
	}
	public String getAdGroupName() {
		return adGroupName;
	}
	public void setAdGroupName(String adGroupName) {
		this.adGroupName = adGroupName;
	}
	public String getAdKeywordSeq() {
		return adKeywordSeq;
	}
	public void setAdKeywordSeq(String adKeywordSeq) {
		this.adKeywordSeq = adKeywordSeq;
	}
	public String getAdKeyword() {
		return adKeyword;
	}
	public void setAdKeyword(String adKeyword) {
		this.adKeyword = adKeyword;
	}
	public int getAdKeywordStatus() {
		return adKeywordStatus;
	}
	public void setAdKeywordStatus(int adKeywordStatus) {
		this.adKeywordStatus = adKeywordStatus;
	}
	public String getAdKeywordStatusDesc() {
		return adKeywordStatusDesc;
	}
	public void setAdKeywordStatusDesc(String adKeywordStatusDesc) {
		this.adKeywordStatusDesc = adKeywordStatusDesc;
	}
	public float getAdKeywordSearchPrice() {
		return adKeywordSearchPrice;
	}
	public void setAdKeywordSearchPrice(float adKeywordSearchPrice) {
		this.adKeywordSearchPrice = adKeywordSearchPrice;
	}
	public float getAdKeywordChannelPrice() {
		return adKeywordChannelPrice;
	}
	public void setAdKeywordChannelPrice(float adKeywordChannelPrice) {
		this.adKeywordChannelPrice = adKeywordChannelPrice;
	}
	public String getAdKeywordOrder() {
		return adKeywordOrder;
	}
	public void setAdKeywordOrder(String adKeywordOrder) {
		this.adKeywordOrder = adKeywordOrder;
	}
	public String getAdKeywordType() {
		return adKeywordType;
	}
	public void setAdKeywordType(String adKeywordType) {
		this.adKeywordType = adKeywordType;
	}
	public int getAdKeywordPv() {
		return adKeywordPv;
	}
	public void setAdKeywordPv(int adKeywordPv) {
		this.adKeywordPv = adKeywordPv;
	}
	public int getAdKeywordClk() {
		return adKeywordClk;
	}
	public void setAdKeywordClk(int adKeywordClk) {
		this.adKeywordClk = adKeywordClk;
	}
	public float getAdKeywordClkRate() {
		return adKeywordClkRate;
	}
	public void setAdKeywordClkRate(float adKeywordClkRate) {
		this.adKeywordClkRate = adKeywordClkRate;
	}
	public float getAdKeywordPvPrice() {
		return adKeywordPvPrice;
	}
	public void setAdKeywordPvPrice(float adKeywordPvPrice) {
		this.adKeywordPvPrice = adKeywordPvPrice;
	}
	public float getAdKeywordClkPrice() {
		return adKeywordClkPrice;
	}
	public void setAdKeywordClkPrice(float adKeywordClkPrice) {
		this.adKeywordClkPrice = adKeywordClkPrice;
	}
	public float getAdKeywordClkPriceAvg() {
		return adKeywordClkPriceAvg;
	}
	public void setAdKeywordClkPriceAvg(float adKeywordClkPriceAvg) {
		this.adKeywordClkPriceAvg = adKeywordClkPriceAvg;
	}
	public float getAdActionMax() {
		return adActionMax;
	}
	public void setAdActionMax(float adActionMax) {
		this.adActionMax = adActionMax;
	}
	public float getSuggestPrice() {
		return suggestPrice;
	}
	public void setSuggestPrice(float suggestPrice) {
		this.suggestPrice = suggestPrice;
	}
	public float getAdKeywordRankAvg() {
		return adKeywordRankAvg;
	}
	public void setAdKeywordRankAvg(float adKeywordRankAvg) {
		this.adKeywordRankAvg = adKeywordRankAvg;
	}
	public float getInvalidClk() {
		return invalidClk;
	}
	public void setInvalidClk(float invalidClk) {
		this.invalidClk = invalidClk;
	}
	public int getAdKeywordOpen() {
		return adKeywordOpen;
	}
	public void setAdKeywordOpen(int adKeywordOpen) {
		this.adKeywordOpen = adKeywordOpen;
	}
	public int getAdKeywordPhraseOpen() {
		return adKeywordPhraseOpen;
	}
	public void setAdKeywordPhraseOpen(int adKeywordPhraseOpen) {
		this.adKeywordPhraseOpen = adKeywordPhraseOpen;
	}
	public float getAdKeywordSearchPhrasePrice() {
		return adKeywordSearchPhrasePrice;
	}
	public void setAdKeywordSearchPhrasePrice(float adKeywordSearchPhrasePrice) {
		this.adKeywordSearchPhrasePrice = adKeywordSearchPhrasePrice;
	}
	public float getSuggestPhrasePrice() {
		return suggestPhrasePrice;
	}
	public void setSuggestPhrasePrice(float suggestPhrasePrice) {
		this.suggestPhrasePrice = suggestPhrasePrice;
	}
	public int getAdKeywordPhrasePv() {
		return adKeywordPhrasePv;
	}
	public void setAdKeywordPhrasePv(int adKeywordPhrasePv) {
		this.adKeywordPhrasePv = adKeywordPhrasePv;
	}
	public int getAdKeywordPhraseClk() {
		return adKeywordPhraseClk;
	}
	public void setAdKeywordPhraseClk(int adKeywordPhraseClk) {
		this.adKeywordPhraseClk = adKeywordPhraseClk;
	}
	public float getAdKeywordPhraseClkRate() {
		return adKeywordPhraseClkRate;
	}
	public void setAdKeywordPhraseClkRate(float adKeywordPhraseClkRate) {
		this.adKeywordPhraseClkRate = adKeywordPhraseClkRate;
	}
	public float getPhraseInvalidClk() {
		return phraseInvalidClk;
	}
	public void setPhraseInvalidClk(float phraseInvalidClk) {
		this.phraseInvalidClk = phraseInvalidClk;
	}
	public float getAdKeywordPhraseClkPriceAvg() {
		return adKeywordPhraseClkPriceAvg;
	}
	public void setAdKeywordPhraseClkPriceAvg(float adKeywordPhraseClkPriceAvg) {
		this.adKeywordPhraseClkPriceAvg = adKeywordPhraseClkPriceAvg;
	}
	public float getAdKeywordPhraseClkPrice() {
		return adKeywordPhraseClkPrice;
	}
	public void setAdKeywordPhraseClkPrice(float adKeywordPhraseClkPrice) {
		this.adKeywordPhraseClkPrice = adKeywordPhraseClkPrice;
	}
	public float getAdKeywordPhraseRankAvg() {
		return adKeywordPhraseRankAvg;
	}
	public void setAdKeywordPhraseRankAvg(float adKeywordPhraseRankAvg) {
		this.adKeywordPhraseRankAvg = adKeywordPhraseRankAvg;
	}
	public String getAdKeywordPhraseType() {
		return adKeywordPhraseType;
	}
	public void setAdKeywordPhraseType(String adKeywordPhraseType) {
		this.adKeywordPhraseType = adKeywordPhraseType;
	}
	public int getAdKeywordPrecisionOpen() {
		return adKeywordPrecisionOpen;
	}
	public void setAdKeywordPrecisionOpen(int adKeywordPrecisionOpen) {
		this.adKeywordPrecisionOpen = adKeywordPrecisionOpen;
	}
	public float getAdKeywordSearchPrecisionPrice() {
		return adKeywordSearchPrecisionPrice;
	}
	public void setAdKeywordSearchPrecisionPrice(float adKeywordSearchPrecisionPrice) {
		this.adKeywordSearchPrecisionPrice = adKeywordSearchPrecisionPrice;
	}
	public float getSuggestPrecisionPrice() {
		return suggestPrecisionPrice;
	}
	public void setSuggestPrecisionPrice(float suggestPrecisionPrice) {
		this.suggestPrecisionPrice = suggestPrecisionPrice;
	}
	public int getAdKeywordPrecisionPv() {
		return adKeywordPrecisionPv;
	}
	public void setAdKeywordPrecisionPv(int adKeywordPrecisionPv) {
		this.adKeywordPrecisionPv = adKeywordPrecisionPv;
	}
	public int getAdKeywordPrecisionClk() {
		return adKeywordPrecisionClk;
	}
	public void setAdKeywordPrecisionClk(int adKeywordPrecisionClk) {
		this.adKeywordPrecisionClk = adKeywordPrecisionClk;
	}
	public float getAdKeywordPrecisionClkRate() {
		return adKeywordPrecisionClkRate;
	}
	public void setAdKeywordPrecisionClkRate(float adKeywordPrecisionClkRate) {
		this.adKeywordPrecisionClkRate = adKeywordPrecisionClkRate;
	}
	public float getPrecisionInvalidClk() {
		return precisionInvalidClk;
	}
	public void setPrecisionInvalidClk(float precisionInvalidClk) {
		this.precisionInvalidClk = precisionInvalidClk;
	}
	public float getAdKeywordPrecisionClkPriceAvg() {
		return adKeywordPrecisionClkPriceAvg;
	}
	public void setAdKeywordPrecisionClkPriceAvg(float adKeywordPrecisionClkPriceAvg) {
		this.adKeywordPrecisionClkPriceAvg = adKeywordPrecisionClkPriceAvg;
	}
	public float getAdKeywordPrecisionClkPrice() {
		return adKeywordPrecisionClkPrice;
	}
	public void setAdKeywordPrecisionClkPrice(float adKeywordPrecisionClkPrice) {
		this.adKeywordPrecisionClkPrice = adKeywordPrecisionClkPrice;
	}
	public float getAdKeywordPrecisionRankAvg() {
		return adKeywordPrecisionRankAvg;
	}
	public void setAdKeywordPrecisionRankAvg(float adKeywordPrecisionRankAvg) {
		this.adKeywordPrecisionRankAvg = adKeywordPrecisionRankAvg;
	}
	public String getAdKeywordPrecisionType() {
		return adKeywordPrecisionType;
	}
	public void setAdKeywordPrecisionType(String adKeywordPrecisionType) {
		this.adKeywordPrecisionType = adKeywordPrecisionType;
	}
	public int getAdKeywordPvSum() {
		return adKeywordPvSum;
	}
	public void setAdKeywordPvSum(int adKeywordPvSum) {
		this.adKeywordPvSum = adKeywordPvSum;
	}
	public int getAdKeywordClkSum() {
		return adKeywordClkSum;
	}
	public void setAdKeywordClkSum(int adKeywordClkSum) {
		this.adKeywordClkSum = adKeywordClkSum;
	}
	public float getAdKeywordClkRateSum() {
		return adKeywordClkRateSum;
	}
	public void setAdKeywordClkRateSum(float adKeywordClkRateSum) {
		this.adKeywordClkRateSum = adKeywordClkRateSum;
	}
	public float getInvalidClkSum() {
		return invalidClkSum;
	}
	public void setInvalidClkSum(float invalidClkSum) {
		this.invalidClkSum = invalidClkSum;
	}
	public float getAdKeywordClkPriceSumAvg() {
		return adKeywordClkPriceSumAvg;
	}
	public void setAdKeywordClkPriceSumAvg(float adKeywordClkPriceSumAvg) {
		this.adKeywordClkPriceSumAvg = adKeywordClkPriceSumAvg;
	}
	public float getAdKeywordClkPriceSum() {
		return adKeywordClkPriceSum;
	}
	public void setAdKeywordClkPriceSum(float adKeywordClkPriceSum) {
		this.adKeywordClkPriceSum = adKeywordClkPriceSum;
	}	
}
