package com.pchome.akbpfp.catalog.prodList.factory;

public class ProdListFactory {

	private EcProdList ecProdList;

	public AProdList getAProdListObj(String catalogType) throws Exception {
		switch (catalogType) {
			case "EC_PROD_LIST":
				return ecProdList;
			default:
				return null;
		}
	}

	
	public EcProdList getEcProdList() {
		return ecProdList;
	}

	public void setEcProdList(EcProdList ecProdList) {
		this.ecProdList = ecProdList;
	}

}
