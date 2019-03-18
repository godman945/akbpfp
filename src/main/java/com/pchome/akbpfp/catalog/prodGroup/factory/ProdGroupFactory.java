package com.pchome.akbpfp.catalog.prodGroup.factory;

public class ProdGroupFactory {

	private EcProdGroup ecProdGroup;

	public AProdGroup getAProdGroupObj(String catalogType) throws Exception {
		
		switch (catalogType) {
			case "EC_PROD_GROUP":
				return ecProdGroup;
			default:
				return null;
		}
	}

	public void setEcProdGroup(EcProdGroup ecProdGroup) {
		this.ecProdGroup = ecProdGroup;
	}
	public EcProdGroup getEcProdGroup() {
		return ecProdGroup;
	}
	
}