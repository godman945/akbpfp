package com.pchome.enumerate.ad;

public enum EnumActivityType {
	
	NEW_CUSTOMER("FRA201307170001",500),
	IQ("FRA201312250001", 1500),
	EI("FRA201312250002", 1500),
	GIFT2014011000("FRA201402240001", 1500),
	GIFT2014022000("FRA201402240002", 2000),
	GIFT2014036000("FRA201402240003", 6000);
	
	private final String activityId;	// 活動Id
	private final float condition;		// 花費條件
	
	private EnumActivityType(String activityId, int condition){
		this.activityId = activityId;
		this.condition = condition;
	}

	public String getActivityId() {
		return activityId;
	}

	public float getCondition() {
		return condition;
	}

}
