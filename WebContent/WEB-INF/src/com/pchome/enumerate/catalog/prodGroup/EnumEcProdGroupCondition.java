package com.pchome.enumerate.catalog.prodGroup;

public enum EnumEcProdGroupCondition {

	like("like", "like","包含"),
	notlike("notlike", "not like","不包含"),
	gt("gt", ">","大於"),
	lt("lt", "<","小於"),
	eq("eq", "=","等於或屬於"),
	neq("neq", "<>","不等於或不屬於"),
	gte("gte", ">=","大於等於"),
	lte("lte", "<=","小於等於");
	
	private final String condition;
	private final String symbol;
	private final String conditionDesc;

	private EnumEcProdGroupCondition(String condition, String symbol, String conditionDesc) {
		this.condition = condition;
		this.symbol = symbol;
		this.conditionDesc = conditionDesc;
	}

	public String getCondition() {
		return condition;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getConditionDesc() {
		return conditionDesc;
	}

}
