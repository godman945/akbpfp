package com.pchome.akbpfp.db.service.ad;

import java.util.List;

import com.pchome.akbpfp.db.dao.ad.PfpAdDetailDAO;
import com.pchome.akbpfp.db.dao.catalog.prod.IPfpCatalogProdEcDAO;
import com.pchome.akbpfp.db.pojo.PfpAdDetail;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.ad.PfpAdDetailVO;
import com.pchome.akbpfp.db.vo.catalog.prodList.ProdListConditionVO;
import com.pchome.utils.CommonUtils;

public class PfpAdDetailService extends BaseService<PfpAdDetail,String> implements IPfpAdDetailService{
	
	private PfpAdDetailDAO pfpAdDetailDAO;
	
	public List<PfpAdDetail> getAllPfpAdDetails() throws Exception{
		return pfpAdDetailDAO.loadAll();
	}
	
	public List<PfpAdDetail> getPfpAdDetails(String adDetailSeq, String adSeq, String adPoolSeq, String defineAdSeq) throws Exception{
		return pfpAdDetailDAO.getPfpAdDetails(adDetailSeq, adSeq, adPoolSeq, defineAdSeq);
	}
	
	public List<PfpAdDetail> getPfpAdDetailByAdSeq(String adSeq) throws Exception {
		return pfpAdDetailDAO.getPfpAdDetailByAdSeq(adSeq);
	}
	
	public PfpAdDetail getPfpAdDetailBySeq(String adDetailSeq) throws Exception {
		return pfpAdDetailDAO.getPfpAdDetailBySeq(adDetailSeq);
	}

	public void insertPfpAdDetail(PfpAdDetail pfpAdDetail) throws Exception {
		pfpAdDetailDAO.insertPfpAdDetail(pfpAdDetail);
	}

	public void updatePfpAdDetail(PfpAdDetail pfpAdDetail) throws Exception {
		pfpAdDetailDAO.updatePfpAdDetail(pfpAdDetail);
	}

	public void deletePfpAdDetail(String adDetailSeq) throws Exception {
		pfpAdDetailDAO.deletePfpAdDetail(adDetailSeq);
	}

	public void savePfpAdDetail(PfpAdDetail pfpAdDetail) throws Exception {
		pfpAdDetailDAO.saveOrUpdatePfpAdDetail(pfpAdDetail);
	}

	public void savePfpAdDetail(PfpAdDetailVO pfpAdDetailVO) throws Exception {
		pfpAdDetailDAO.saveOrUpdatePfpAdDetail(pfpAdDetailVO);
	}

	public void setPfpAdDetailDAO(PfpAdDetailDAO pfpAdDetailDAO) throws Exception {
		this.pfpAdDetailDAO = pfpAdDetailDAO;
	}

	public List<PfpAdDetail> getPfpAdDetailsForAdGroup(String customerInfoId, String adGroupSeq, String adDetailId, String adDetailContent)throws Exception {
		
		// 先攔掉 SQL Injection 攻擊語法
		customerInfoId = CommonUtils.filterSqlInjection(customerInfoId);
		adGroupSeq = CommonUtils.filterSqlInjection(adGroupSeq);
		
		return pfpAdDetailDAO.getPfpAdDetailsForAdGroup(customerInfoId, adGroupSeq, adDetailId, adDetailContent);
	}
	
	public String checkCatalogGroupAdStatusCount(String catalogGroupSeq, String prodGroup ) throws Exception{
		return pfpAdDetailDAO.checkCatalogGroupAdStatusCount(catalogGroupSeq,prodGroup);
	}
	
	public static void main(String arg[]) throws Exception{
	}


}
