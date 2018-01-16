package com.pchome.akbpfp.data.threadprocess;

import com.pchome.akbpfp.db.service.ad.IPfpAdService;

public class ThreadServiceBean {

	private IPfpAdService pfpAdService;

	public IPfpAdService getPfpAdService() {
		return pfpAdService;
	}

	public void setPfpAdService(IPfpAdService pfpAdService) {
		this.pfpAdService = pfpAdService;
	}

}
