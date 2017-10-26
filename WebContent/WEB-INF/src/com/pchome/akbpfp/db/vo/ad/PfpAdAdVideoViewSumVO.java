package com.pchome.akbpfp.db.vo.ad;

public class PfpAdAdVideoViewSumVO {

	// 查詢總筆數
	private int totalSize;
	// 曝光數總數
	private int adPvSum;
	// 收視數總數
	private int adViewSum;
	// 收視率
	private double adViewRatings;
	// 單次收視費用
	private double singleAdViewCost;
	// 千次曝光費用
	private double thousandsCost;
	// 費用總數
	private double costSum;

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getAdPvSum() {
		return adPvSum;
	}

	public void setAdPvSum(int adPvSum) {
		this.adPvSum = adPvSum;
	}

	public int getAdViewSum() {
		return adViewSum;
	}

	public void setAdViewSum(int adViewSum) {
		this.adViewSum = adViewSum;
	}

	public double getAdViewRatings() {
		return adViewRatings;
	}

	public void setAdViewRatings(double adViewRatings) {
		this.adViewRatings = adViewRatings;
	}

	public double getSingleAdViewCost() {
		return singleAdViewCost;
	}

	public void setSingleAdViewCost(double singleAdViewCost) {
		this.singleAdViewCost = singleAdViewCost;
	}

	public double getThousandsCost() {
		return thousandsCost;
	}

	public void setThousandsCost(double thousandsCost) {
		this.thousandsCost = thousandsCost;
	}

	public double getCostSum() {
		return costSum;
	}

	public void setCostSum(double costSum) {
		this.costSum = costSum;
	}

}
