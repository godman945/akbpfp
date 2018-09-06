package com.pchome.akbpfp.catalog.prodGroup.factory;

public class ProdGroupFactory {

	private EcProdGroup ecProdGroup;
	private BookingProdGroup bookingProdGroup;

	public AProdGroup getAProdGroupObj(String catalogType) throws Exception {
		
		switch (catalogType) {
			case "EC_PROD_GROUP":
				return ecProdGroup;
			case "BOOKING_PROD_GROUP":
				return bookingProdGroup;
//			case "TRAFFIC_PROD_GROUP":
//				return ecProdGroup;
//			case "RENT_PROD_GROUP":
//				return ecProdGroup;
			default:
				return null;
		}
	}

	public void setEcProdGroup(EcProdGroup ecProdGroup) {
		this.ecProdGroup = ecProdGroup;
	}

	public void setBookingProdGroup(BookingProdGroup bookingProdGroup) {
		this.bookingProdGroup = bookingProdGroup;
	}

	public EcProdGroup getEcProdGroup() {
		return ecProdGroup;
	}

	public BookingProdGroup getBookingProdGroup() {
		return bookingProdGroup;
	}

	
	
	
}