package com.pchome.akbpfp.struts2.action.apply;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.api.MemberAPI;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.vo.member.MemberVO;
import com.pchome.akbpfp.struts2.BaseSSLAction;
import com.pchome.enumerate.account.EnumPfpAuthorizAtion;

public class AuthorizationAction extends BaseSSLAction{

	private MemberAPI memberAPI;
	private IPfpCustomerInfoService pfpCustomerInfoService;
	
	private MemberVO memberVO;
	
	private String memberName;
	private String memberSex;	
	private String memberBirthday;	
	private String memberTelephone;
	private String memberMobile;
	private String zipcode;
	private String address;
	
	public String execute() throws Exception{
		log.info("***START***");
		this.checkRedirectSSLUrl();
		
		if(StringUtils.isNotBlank(this.resultType)){
			return this.resultType;
		}
		
		// 從會員中心取會員資料
		this.memberVO = memberAPI.getMemberVOData(super.getId_pchome());
	

		return SUCCESS;
	}

	/**
	 * 同意約定條款和刊登規範
	 * 1. 補填帳戶資料
	 * 2. 回填會員中心資料
	 */
	public String agreeAuthorizationAction() throws Exception{
		
		if(StringUtils.isNotBlank(super.getId_pchome()) && StringUtils.isNotBlank(memberName) && 
				StringUtils.isNotBlank(memberSex) && StringUtils.isNotBlank(memberBirthday) && 
				StringUtils.isNotBlank(memberTelephone) && StringUtils.isNotBlank(memberMobile) && 
				StringUtils.isNotBlank(zipcode) && StringUtils.isNotBlank(address)){	
						
			// 補填帳戶資料
			PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfoByMmeberId(super.getId_pchome());
			
			pfpCustomerInfo.setAuthorizedPage(EnumPfpAuthorizAtion.NO.getStatus());
			pfpCustomerInfo.setTelephone(memberTelephone);
			pfpCustomerInfo.setMobile(memberMobile);
			pfpCustomerInfo.setZip(zipcode);
			pfpCustomerInfo.setAddress(address);
			
			pfpCustomerInfoService.saveOrUpdate(pfpCustomerInfo);	
			
			// 回填會員中心資料
			this.memberVO = memberAPI.getMemberVOData(super.getId_pchome());
			this.memberVO.setMemberName(memberName);
			this.memberVO.setMemberSex(memberSex);
			this.memberVO.setMemberBirthday(memberBirthday);
			this.memberVO.setMemberTelephone(memberTelephone);
			this.memberVO.setMemberMobile(memberMobile);
			this.memberVO.setMemberZip(zipcode);
			this.memberVO.setMemberAddress(address);
			
			this.memberAPI.updateMemberData(this.memberVO);
		}
		else{
//			log.info(" memberName: "+memberName);
//			log.info(" memberSex: "+memberSex);
//			log.info(" memberBirthday: "+memberBirthday);
//			log.info(" memberTelephone: "+memberTelephone);
//			log.info(" memberMobile: "+memberMobile);
//			log.info(" zipcode: "+zipcode);
//			log.info(" address: "+address);
		}
		
		return SUCCESS;
	}

	public void setMemberAPI(MemberAPI memberAPI) {
		this.memberAPI = memberAPI;
	}

	public void setPfpCustomerInfoService(
			IPfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}
	
	public MemberVO getMemberVO() {
		return memberVO;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName.trim();
	}

	public void setMemberSex(String memberSex) {
		this.memberSex = memberSex.trim();
	}

	public void setMemberBirthday(String memberBirthday) {
		this.memberBirthday = memberBirthday.trim();
	}

	public void setMemberTelephone(String memberTelephone) {
		this.memberTelephone = memberTelephone.trim();
	}

	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile.trim();
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode.trim();
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
