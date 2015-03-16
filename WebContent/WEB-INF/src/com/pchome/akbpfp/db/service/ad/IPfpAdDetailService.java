package com.pchome.akbpfp.db.service.ad;

import java.util.List;

import com.pchome.akbpfp.db.dao.ad.PfpAdDetailDAO;
import com.pchome.akbpfp.db.pojo.PfpAdDetail;
import com.pchome.akbpfp.db.service.IBaseService;


public interface IPfpAdDetailService extends IBaseService<PfpAdDetail,String>{

	public List<PfpAdDetail> getAllPfpAdDetails() throws Exception;
	
	public List<PfpAdDetail> getPfpAdDetails(String adDetailSeq, String adSeq, String adPoolSeq, String defineAdSeq) throws Exception;

	public List<PfpAdDetail> getPfpAdDetailByAdSeq(String adSeq) throws Exception;
	
	public PfpAdDetail getPfpAdDetailBySeq(String adDetailSeq) throws Exception;

	public void insertPfpAdDetail(PfpAdDetail pfpAdDetail) throws Exception;
	
	public void updatePfpAdDetail(PfpAdDetail pfpAdDetail) throws Exception;
	
	public void deletePfpAdDetail(String adDetailSeq) throws Exception;
	
	public void savePfpAdDetail(PfpAdDetail pfpAdDetail) throws Exception;
	
	public void setPfpAdDetailDAO(PfpAdDetailDAO pfpAdDetailDAO) throws Exception;
	
}
