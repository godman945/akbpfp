package com.pchome.enumerate.catalog.prodGroup;

public enum EnumProdGroupCondition {

	eq("eq", "=","相等"),
	neq("neq", "<>","不相等"),
	gt("gt", ">","大於"),
	lt("lt", "<","小於"),
	gte("gte", ">=","大於等於"),
	lte("lte", "<=","小於等於");
	
	private final String condition;
	private final String symbol;
	private final String conditionDesc;

	private EnumProdGroupCondition(String condition, String symbol, String conditionDesc) {
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
