package com.pchome.akbpfp.db.service.ad;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.pchome.akbpfp.db.dao.ad.AdmDefineAdDAO;
import com.pchome.akbpfp.db.dao.ad.IAdmDefineAdDAO;
import com.pchome.akbpfp.db.pojo.AdmDefineAd;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.config.TestConfig;

public class DefineAdService extends BaseService<AdmDefineAd, String> implements IDefineAdService {

	private IAdmDefineAdDAO admDefineAdDAO;

	public List<AdmDefineAd> getAllDefineAd() throws Exception {
		return admDefineAdDAO.loadAll();
	}

	public List<AdmDefineAd> getDefineAdByCondition(String defineAdSeq, String defineAdId, String defineAdName, String adPoolSeq) throws Exception {
		return admDefineAdDAO.getDefineAdByCondition(defineAdSeq, defineAdId, defineAdName, adPoolSeq);
	}
	
	public AdmDefineAd getDefineAdById(String defineAdId) throws Exception {
		return admDefineAdDAO.getDefineAdById(defineAdId);
	}
	
	public AdmDefineAd getDefineAdBySeq(String defineAdSeq) throws Exception {
		return admDefineAdDAO.getDefineAdBySeq(defineAdSeq);
	}
	
	public AdmDefineAd getDefineAdByPoolSeq(String adPoolSeq) throws Exception {
		return admDefineAdDAO.getDefineAdByPoolSeq(adPoolSeq);
	}

	public void setAdmDefineAdDAO(AdmDefineAdDAO admDefineadDAO) throws Exception {
		this.admDefineAdDAO = admDefineadDAO;
	}

	public static void main(String[] args) throws Exception {

		System.out.println("===== start test =====");

	    ApplicationContext context = new FileSystemXmlApplicationContext(TestConfig.path);

	    System.out.println("===== end test =====");
	}
}
