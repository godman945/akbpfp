package com.pchome.akbpfp.struts2.action.account;


import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.api.MemberAPI;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.db.vo.account.AccountVO;
import com.pchome.akbpfp.db.vo.member.MemberVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.akbpfp.struts2.BaseSSLAction;

public class AccountContentAction extends BaseSSLAction{

	private MemberAPI memberAPI;
	private PfpCustomerInfoService pfpCustomerInfoService;
	private MemberVO memberVO;
	
	private String memberName;
	private String memberSex;	
	private String memberBirthday;	
	private String memberTelephone;
	private String memberMobile;
	private String zipcode;
	private String address;
	
	public String execute() throws Exception{
		
		this.checkRedirectSSLUrl();
		if(StringUtils.isNotBlank(this.resultType)){
			return this.resultType;
		}
		
		memberVO = memberAPI.getMemberVOData(super.getId_pchome());
		AccountVO accountVO = pfpCustomerInfoService.getAccountVO(super.getCustomer_info_id());
		
		if(StringUtils.isBlank(memberVO.getMemberMobile())){
			memberVO.setMemberMobile(accountVO.getMobile());
		}
		
		if(StringUtils.isBlank(memberVO.getMemberTelephone())){
			memberVO.setMemberTelephone(accountVO.getTelephone());
		}
		
		if(StringUtils.isBlank(memberVO.getMemberZip())){
			memberVO.setMemberZip(accountVO.getZip());
		}
		
		if(StringUtils.isBlank(memberVO.getMemberAddress())){
			memberVO.setMemberAddress(accountVO.getAddress());
		}
		
		return SUCCESS;
	}

	public String accountContentUpdateAction() throws Exception{
		
		this.checkRedirectSSLUrl();
		if(StringUtils.isNotBlank(this.resultType)){
			return this.resultType;
		}
		
		if(StringUtils.isNotEmpty(memberName.trim()) && StringUtils.isNotEmpty(memberSex.trim()) && 
				StringUtils.isNotEmpty(memberBirthday.trim()) && StringUtils.isNotEmpty(memberTelephone.trim()) && 
				StringUtils.isNotEmpty(memberMobile.trim()) && StringUtils.isNotEmpty(zipcode.trim()) && 
				StringUtils.isNotEmpty(address.trim())){

			//解地址長度過長 bug，限 50 字元(2013/11/20)
			if (address.length() > 50) {
				address = address.substring(0, 50);
			}

			// 回存會員中心
			memberVO = new MemberVO();
			memberVO.setMemberId(super.getId_pchome());
			memberVO.setMemberName(memberName.trim());
			memberVO.setMemberSex(memberSex.trim());
			memberVO.setMemberBirthday(memberBirthday.trim());
			memberVO.setMemberTelephone(memberTelephone.trim());
			memberVO.setMemberMobile(memberMobile.trim());
			memberVO.setMemberZip(zipcode.trim());
			memberVO.setMemberAddress(address.trim());
			
			memberAPI.updateMemberData(memberVO);
			
			// 更新帳戶資料
			PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
			
			pfpCustomerInfo.setTelephone(memberTelephone.trim());
			pfpCustomerInfo.setMobile(memberMobile.trim());
			pfpCustomerInfo.setZip(zipcode.trim());
			pfpCustomerInfo.setAddress(address.trim());
			// have problem
			pfpCustomerInfoService.saveOrUpdateWithAccesslog(pfpCustomerInfo, super.getId_pchome(), super.getUser_id(), request.getRemoteAddr());
			
		}
		
		
		return SUCCESS;
	}
	

	public void setMemberAPI(MemberAPI memberAPI) {
		this.memberAPI = memberAPI;
	}

	public void setPfpCustomerInfoService(
			PfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}

	public MemberVO getMemberVO() {
		return memberVO;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public void setMemberSex(String memberSex) {
		this.memberSex = memberSex;
	}

	public void setMemberBirthday(String memberBirthday) {
		this.memberBirthday = memberBirthday;
	}

	public void setMemberTelephone(String memberTelephone) {
		this.memberTelephone = memberTelephone;
	}

	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public void setAddress(String address) {
		this.address = address;
	}	
	
}
