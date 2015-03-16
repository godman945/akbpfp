package com.pchome.akbpfp.db.dao.ad;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdDetail;

public interface IPfpAdDetailDAO extends IBaseDAO<PfpAdDetail,String>{

	public List<PfpAdDetail> getPfpAdDetails(String adDetailSeq, String adSeq, String adPoolSeq, String defineAdSeq) throws Exception;

	public List<PfpAdDetail> getPfpAdDetailByAdSeq(String adSeq) throws Exception;
	
	public PfpAdDetail getPfpAdDetailBySeq(String adDetailSeq) throws Exception;
	
	public void saveOrUpdatePfpAdDetail(PfpAdDetail pfpAdDetail) throws Exception;
	
	public void insertPfpAdDetail(PfpAdDetail pfpAdDetail) throws Exception;
	
	public void updatePfpAdDetail(PfpAdDetail pfpAdDetail) throws Exception;
	
	public void deletePfpAdDetail(String adDetailSeq) throws Exception;
}
