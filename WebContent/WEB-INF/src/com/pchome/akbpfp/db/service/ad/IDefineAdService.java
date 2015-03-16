package com.pchome.akbpfp.db.service.ad;

import java.util.List;

import com.pchome.akbpfp.db.dao.ad.AdmDefineAdDAO;
import com.pchome.akbpfp.db.pojo.AdmDefineAd;
import com.pchome.akbpfp.db.service.IBaseService;

public interface IDefineAdService extends IBaseService<AdmDefineAd, String>{

	public List<AdmDefineAd> getAllDefineAd() throws Exception;

	public List<AdmDefineAd> getDefineAdByCondition(String defineAdSeq, String defineAdId, String defineAdName, String adPoolSeq) throws Exception;

	public AdmDefineAd getDefineAdById(String defineAdSeq) throws Exception;

	public AdmDefineAd getDefineAdBySeq(String defineAdSeq) throws Exception;

	public AdmDefineAd getDefineAdByPoolSeq(String adPoolSeq) throws Exception;
	
	public void setAdmDefineAdDAO(AdmDefineAdDAO admDefineadDAO) throws Exception;

}
