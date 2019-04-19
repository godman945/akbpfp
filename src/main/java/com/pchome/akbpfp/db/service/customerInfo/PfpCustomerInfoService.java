package com.pchome.akbpfp.db.service.customerInfo;


import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.pchome.akbpfp.db.dao.customerInfo.IPfpCustomerInfoDAO;
import com.pchome.akbpfp.db.dao.customerInfo.PfpCustomerInfoDAO;
import com.pchome.akbpfp.db.dao.user.PfpUserMemberRefDAO;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.pojo.PfpUserMemberRef;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.account.AccountVO;
import com.pchome.config.TestConfig;
import com.pchome.rmi.accesslog.EnumAccesslogAction;
import com.pchome.rmi.accesslog.EnumAccesslogChannel;
import com.pchome.rmi.accesslog.EnumAccesslogEmailStatus;
import com.pchome.rmi.accesslog.IAccesslogProvider;


public class PfpCustomerInfoService extends BaseService<PfpCustomerInfo,String> implements IPfpCustomerInfoService{
	
	private IAccesslogProvider accesslogProvider;
	private PfpUserMemberRefDAO pfpUserMemberRefDAO;

	public AccountVO existentAccount(String memberId) throws Exception{
		
		List<PfpCustomerInfo> pfpCustomerInfos = ((PfpCustomerInfoDAO)dao).validMemberId(memberId);
		List<PfpUserMemberRef> pfpUserMemberRefs = pfpUserMemberRefDAO.activateUserMemberRef(memberId);
		
		AccountVO accountVO = null;
		
		if( pfpCustomerInfos.size() > 0 || pfpUserMemberRefs.size() > 0){
			
			accountVO = this.customerInfoTransformAccountVO(pfpCustomerInfos.get(0));
		}

		return accountVO;
	}
	
	public AccountVO getAccountVO(String customerInfoId) throws Exception{
		
		List<PfpCustomerInfo> pfpCustomerInfos = ((PfpCustomerInfoDAO)dao).findCustomerInfo(customerInfoId);
		
		AccountVO accountVO = null;
		
		if(pfpCustomerInfos.size() > 0){
			
			accountVO = this.customerInfoTransformAccountVO(pfpCustomerInfos.get(0));
			
		}
		
		return accountVO;
	}
	
	public PfpCustomerInfo findCustomerInfo(String customerInfoId){
		PfpCustomerInfo customerInfo = null;
		List<PfpCustomerInfo> customerInfos = ((PfpCustomerInfoDAO)dao).findCustomerInfo(customerInfoId);
		
		if(customerInfos.size() > 0){
			customerInfo = customerInfos.get(0);
		}
		
		return customerInfo;
	}	
	
	public List<PfpCustomerInfo> findAllPfpCustomerInfo() {
		return ((PfpCustomerInfoDAO)dao).findAllPfpCustomerInfo();
	}
	
	
	private AccountVO customerInfoTransformAccountVO(PfpCustomerInfo pfpCustomerInfo) throws Exception{
		
		AccountVO accountVO = new AccountVO();
			
		accountVO = new AccountVO();
		
		accountVO.setCustomerInfoId(pfpCustomerInfo.getCustomerInfoId());
		accountVO.setCustomerInfoTitle(pfpCustomerInfo.getCustomerInfoTitle());
		accountVO.setMemberId(pfpCustomerInfo.getMemberId());
		accountVO.setCategory(pfpCustomerInfo.getCategory());				
		accountVO.setCompanyTitle(pfpCustomerInfo.getCompanyTitle());
		accountVO.setRegistration(pfpCustomerInfo.getRegistration());			
		accountVO.setIndustry(pfpCustomerInfo.getIndustry());
		accountVO.setUrlYN(pfpCustomerInfo.getUrl());			
		accountVO.setUrlAddress(pfpCustomerInfo.getUrlAddress());	
		accountVO.setCustomerInfoStatus(pfpCustomerInfo.getStatus());
		accountVO.setRemain(pfpCustomerInfo.getRemain());		
		accountVO.setTelephone(pfpCustomerInfo.getTelephone());
		accountVO.setMobile(pfpCustomerInfo.getMobile());
		accountVO.setZip(pfpCustomerInfo.getZip());
		accountVO.setAddress(pfpCustomerInfo.getAddress());
		accountVO.setAuthorizedPage(pfpCustomerInfo.getAuthorizedPage());
			
		return accountVO;
	}
	
	public List<PfpCustomerInfo> findValidCustomerInfos(){
		return ((PfpCustomerInfoDAO)dao).findValidCustomerInfos();
	}
	
	public void saveOrUpdateWithAccesslog(PfpCustomerInfo customerInfo, String pcId, String userId, String clientIp) throws Exception{
		
		// 帳戶異動記錄 AdmAccesslog
		StringBuffer msg = new StringBuffer();
		
		msg.append(EnumAccesslogAction.ACCOUNT_MODIFY.getMessage()).append("：");		
		msg.append(customerInfo.getCategory()).append(",");
		msg.append(customerInfo.getCustomerInfoTitle()).append(",");
		msg.append(customerInfo.getIndustry()).append(",");
		msg.append(customerInfo.getCompanyTitle()).append(",");
		msg.append(customerInfo.getRegistration()).append(",");
		msg.append(customerInfo.getUrl()).append(",");
		msg.append(customerInfo.getUrlAddress()).append(",");
		msg.append(customerInfo.getMobile()).append(",");
		msg.append(customerInfo.getTelephone()).append(",");
		msg.append(customerInfo.getZip()).append(",");
		msg.append(customerInfo.getAddress()).append(",");
		msg.append(customerInfo.getPayType()).append(",");
		msg.append(customerInfo.getStatus()).append(",");
		
		Integer id = accesslogProvider.addAccesslog(EnumAccesslogChannel.PFP, 
													EnumAccesslogAction.ACCOUNT_MODIFY, 
													msg.toString(), 
													pcId, 
													null, 
													customerInfo.getCustomerInfoId(), 
													userId, 
													clientIp, 
													EnumAccesslogEmailStatus.NO);
		
		((PfpCustomerInfoDAO)dao).saveOrUpdate(customerInfo);
	}
	
	public PfpCustomerInfo findCustomerInfoByMmeberId(String memberId){
	
		List<PfpCustomerInfo> list = ((IPfpCustomerInfoDAO)dao).findCustomerInfoByMmeberId(memberId);
		
		if(list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	public void setAccesslogProvider(IAccesslogProvider accesslogProvider) {
		this.accesslogProvider = accesslogProvider;
	}

	public void setPfpUserMemberRefDAO(PfpUserMemberRefDAO pfpUserMemberRefDAO) {
		this.pfpUserMemberRefDAO = pfpUserMemberRefDAO;
	}


	public static void main(String arg[]) throws Exception{

		ApplicationContext context = new FileSystemXmlApplicationContext(TestConfig.path);
		Logger log = LogManager.getRootLogger();

		PfpCustomerInfoService service = (PfpCustomerInfoService) context.getBean("PfpCustomerInfoService");
		
		//AccountVO vo = service .findRegisterDataById("reantoilpc");
		log.info("\n   size "+service.existentAccount("ray998"));
	}


}
