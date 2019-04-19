package com.pchome.akbpfp.db.dao.ad;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.AdmDefineAd;

public interface IAdmDefineAdDAO extends IBaseDAO<AdmDefineAd, String> {

	public List<AdmDefineAd> getDefineAdByCondition(String defineAdSeq, String defineAdId, String defineAdName, String adPoolSeq) throws Exception;

	public AdmDefineAd getDefineAdById(String defineAdId) throws Exception;
	
	public AdmDefineAd getDefineAdBySeq(String defineAdSeq) throws Exception;

	public AdmDefineAd getDefineAdByPoolSeq(String adPoolSeq) throws Exception;
}
