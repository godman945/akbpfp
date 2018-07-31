package com.pchome.akbpfp.catalog.prodGroup.factory;

public class ProdGroupFactory {

//	private static Map<String, Object> objectMap = new HashMap<String, Object>();

	public static AProdGroup getAProdGroupObj(String catalogType) throws Exception {
		
		switch (catalogType) {
			case "1":
				EcProdGroup ecProdGroup = new EcProdGroup();
				return ecProdGroup;
			case "2":
				BookingProdGroup bookingProdGroup = new BookingProdGroup();
				return bookingProdGroup;
//			case "3":
//				EcProdGroup ecProdGroup = new EcProdGroup();
//				return ecProdGroup;
//			case "4":
//				EcProdGroup ecProdGroup = new EcProdGroup();
//				return ecProdGroup;
			default:
				break;
		}
		return null;
		
	}
}