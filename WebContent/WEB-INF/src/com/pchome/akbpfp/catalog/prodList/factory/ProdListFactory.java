package com.pchome.akbpfp.catalog.prodList.factory;

public class ProdListFactory {
	


	private EcProdList ecProdList;
//	private BookingProdList bookingProdList;

	public AProdList getAProdListObj(String catalogType) throws Exception {
		
		switch (catalogType) {
			case "EC_PROD_LIST":
				return ecProdList;
//			case "BOOKING_PROD_LIST":
//				return bookingProdList;
//			case "TRAFFIC_PROD_GROUP":
//				return ecProdGroup;
//			case "RENT_PROD_GROUP":
//				return ecProdGroup;
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
