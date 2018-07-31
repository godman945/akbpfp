package com.pchome.akbpfp.db.service.catalog.prodGroup;


import java.util.List;

import com.pchome.akbpfp.db.dao.catalog.prodGroup.IPfpCatalogGroupDAO;
import com.pchome.akbpfp.db.dao.catalog.prodGroup.PfpCatalogGroupDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroup;
import com.pchome.akbpfp.db.service.BaseService;


public class PfpCatalogGroupService extends BaseService<PfpCatalogGroup,String> implements IPfpCatalogGroupService{
//	private PfpCatalogGroupDAO pfpCatalogGroupDAO;
	
	
//	private IAccesslogProvider accesslogProvider;
//	private PfpUserMemberRefDAO pfpUserMemberRefDAO;
	
	public String getCatalogType(String groupId) throws Exception{
		
		List<PfpCatalogGroup> pfpCatalogGroups = ((IPfpCatalogGroupDAO)dao).getCatalogType(groupId);
		
		
		String catalogType = "";
		
		if( pfpCatalogGroups.size() > 0 || pfpCatalogGroups.size() > 0){
			
			catalogType = pfpCatalogGroups.get(0).getPfpCatalog().getCatalogType();
		}

		return catalogType;
	}


//	public void setPfpCatalogGroupDAO(PfpCatalogGroupDAO pfpCatalogGroupDAO) {
//		this.pfpCatalogGroupDAO = pfpCatalogGroupDAO;
//	}

//	public AccountVO existentAccount(String memberId) throws Exception{
//		
//		List<PfpCustomerInfo> pfpCustomerInfos = ((PfpCatalogGroupDAO)dao).validMemberId(memberId);
//		List<PfpUserMemberRef> pfpUserMemberRefs = pfpUserMemberRefDAO.activateUserMemberRef(memberId);
//		
//		AccountVO accountVO = null;
//		
//		if( pfpCustomerInfos.size() > 0 || pfpUserMemberRefs.size() > 0){
//			
//			accountVO = this.customerInfoTransformAccountVO(pfpCustomerInfos.get(0));
//		}
//
//		return accountVO;
//	}
//	
//	public AccountVO getAccountVO(String customerInfoId) throws Exception{
//		
//		List<PfpCustomerInfo> pfpCustomerInfos = ((PfpCatalogGroupDAO)dao).findCustomerInfo(customerInfoId);
//		
//		AccountVO accountVO = null;
//		
//		if(pfpCustomerInfos.size() > 0){
//			
//			accountVO = this.customerInfoTransformAccountVO(pfpCustomerInfos.get(0));
//			
//		}
//		
//		return accountVO;
//	}
//	
//	public PfpCustomerInfo findCustomerInfo(String customerInfoId){
//		PfpCustomerInfo customerInfo = null;
//		List<PfpCustomerInfo> customerInfos = ((PfpCatalogGroupDAO)dao).findCustomerInfo(customerInfoId);
//		
//		if(customerInfos.size() > 0){
//			customerInfo = customerInfos.get(0);
//		}
//		
//		return customerInfo;
//	}	
//	
//	public List<PfpCustomerInfo> findAllPfpCustomerInfo() {
//		return ((PfpCatalogGroupDAO)dao).findAllPfpCustomerInfo();
//	}
//	
//	
//	private AccountVO customerInfoTransformAccountVO(PfpCustomerInfo pfpCustomerInfo) throws Exception{
//		
//		AccountVO accountVO = new AccountVO();
//			
//		accountVO = new AccountVO();
//		
//		accountVO.setCustomerInfoId(pfpCustomerInfo.getCustomerInfoId());
//		accountVO.setCustomerInfoTitle(pfpCustomerInfo.getCustomerInfoTitle());
//		accountVO.setMemberId(pfpCustomerInfo.getMemberId());
//		accountVO.setCategory(pfpCustomerInfo.getCategory());				
//		accountVO.setCompanyTitle(pfpCustomerInfo.getCompanyTitle());
//		accountVO.setRegistration(pfpCustomerInfo.getRegistration());			
//		accountVO.setIndustry(pfpCustomerInfo.getIndustry());
//		accountVO.setUrlYN(pfpCustomerInfo.getUrl());			
//		accountVO.setUrlAddress(pfpCustomerInfo.getUrlAddress());	
//		accountVO.setCustomerInfoStatus(pfpCustomerInfo.getStatus());
//		accountVO.setRemain(pfpCustomerInfo.getRemain());		
//		accountVO.setTelephone(pfpCustomerInfo.getTelephone());
//		accountVO.setMobile(pfpCustomerInfo.getMobile());
//		accountVO.setZip(pfpCustomerInfo.getZip());
//		accountVO.setAddress(pfpCustomerInfo.getAddress());
//		accountVO.setAuthorizedPage(pfpCustomerInfo.getAuthorizedPage());
//			
//		return accountVO;
//	}
//	
//	public List<PfpCustomerInfo> findValidCustomerInfos(){
//		return ((PfpCatalogGroupDAO)dao).findValidCustomerInfos();
//	}
//	
//	public void saveOrUpdateWithAccesslog(PfpCustomerInfo customerInfo, String pcId, String userId, String clientIp) throws Exception{
//		
//		// 帳戶異動記錄 AdmAccesslog
//		StringBuffer msg = new StringBuffer();
//		
//		msg.append(EnumAccesslogAction.ACCOUNT_MODIFY.getMessage()).append("：");		
//		msg.append(customerInfo.getCategory()).append(",");
//		msg.append(customerInfo.getCustomerInfoTitle()).append(",");
//		msg.append(customerInfo.getIndustry()).append(",");
//		msg.append(customerInfo.getCompanyTitle()).append(",");
//		msg.append(customerInfo.getRegistration()).append(",");
//		msg.append(customerInfo.getUrl()).append(",");
//		msg.append(customerInfo.getUrlAddress()).append(",");
//		msg.append(customerInfo.getMobile()).append(",");
//		msg.append(customerInfo.getTelephone()).append(",");
//		msg.append(customerInfo.getZip()).append(",");
//		msg.append(customerInfo.getAddress()).append(",");
//		msg.append(customerInfo.getPayType()).append(",");
//		msg.append(customerInfo.getStatus()).append(",");
//		
//		Integer id = accesslogProvider.addAccesslog(EnumAccesslogChannel.PFP, 
//													EnumAccesslogAction.ACCOUNT_MODIFY, 
//													msg.toString(), 
//													pcId, 
//													null, 
//													customerInfo.getCustomerInfoId(), 
//													userId, 
//													clientIp, 
//													EnumAccesslogEmailStatus.NO);
//		
//		((PfpCatalogGroupDAO)dao).saveOrUpdate(customerInfo);
//	}
//	
//	public PfpCustomerInfo findCustomerInfoByMmeberId(String memberId){
//	
//		List<PfpCustomerInfo> list = ((IPfpCatalogGroupDAO)dao).findCustomerInfoByMmeberId(memberId);
//		
//		if(list.isEmpty()){
//			return null;
//		}else{
//			return list.get(0);
//		}
//	}
//	
//	public void setAccesslogProvider(IAccesslogProvider accesslogProvider) {
//		this.accesslogProvider = accesslogProvider;
//	}
//
//	public void setPfpUserMemberRefDAO(PfpUserMemberRefDAO pfpUserMemberRefDAO) {
//		this.pfpUserMemberRefDAO = pfpUserMemberRefDAO;
//	}




}
