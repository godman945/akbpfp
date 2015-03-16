package com.pchome.soft.depot.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.vo.account.AccountVO;
import com.pchome.akbpfp.db.vo.member.MemberVO;
import com.pchome.enumerate.cookie.EnumCookieConstants;

public class ApplyDataCookieUtile {

	public AccountVO getAccountVO(HttpServletRequest request) throws Exception{
		
//		// 讀取cookie
//		String cookieData = CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_AKBPFP_APPLY.getValue(), 
//								EnumCookieConstants.COOKIE_USING_CODE.getValue());
		AccountVO accountVO = null;
//		
//		if(StringUtils.isBlank(cookieData)){
//			return null;
//		}else{
//			String decodeData = EncodeUtil.getInstance().decryptAES(cookieData, EnumCookieConstants.COOKIE_SECRET_KEY.getValue());
//			String[] applyData = decodeData.split(",");
//			
//			accountVO = new AccountVO();
//			
//			/**
//			 *  data:
//			 *  data[0] ---> CustomerInfoId
//			 *  data[1] ---> MemberId
//			 *  data[2] ---> Category
//			 *  data[3] ---> CustomerInfoTitle
//			 *  data[4] ---> CompanyTitle
//			 *  data[5] ---> Registration
//			 *  data[6] ---> Industry
//			 *  data[7] ---> UrlYN
//			 *  data[8] ---> UrlAddress
//			 *  data[9] ---> CustomerInfoStatus
//			 *  data[10] ---> Name
//			 *  data[11] ---> Sex
//			 *  data[12] ---> Birthday
//			 *  data[13] ---> Telephone
//			 *  data[14] ---> Mobile
//			 *  data[15] ---> Zip
//			 *  data[16] ---> Address
//			 *  data[17] ---> addMoney
//			 *  data[18] ---> tax
//			 */
//			
//			accountVO.setCustomerInfoId(applyData[0]);
//			accountVO.setMemberId(applyData[1]);
//			accountVO.setCategory(applyData[2]);
//			accountVO.setCustomerInfoTitle(applyData[3]);		
//			accountVO.setCompanyTitle(applyData[4]);
//			accountVO.setRegistration(applyData[5]);		
//			accountVO.setIndustry(applyData[6]);
//			accountVO.setUrlYN(applyData[7]);
//			accountVO.setUrlAddress(applyData[8]);
//			accountVO.setAddMoney(Integer.valueOf(applyData[17]));
//			accountVO.setAddTax(Integer.valueOf(applyData[18]));
//			
//			MemberVO memberVO = new MemberVO();
//			memberVO.setMemberId(applyData[1]);
//			memberVO.setMemberName(applyData[10]);
//			memberVO.setMemberSex(applyData[11]);
//			memberVO.setMemberBirthday(applyData[12]);
//			memberVO.setMemberTelephone(applyData[13]);
//			memberVO.setMemberMobile(applyData[14]);
//			memberVO.setMemberZip(applyData[15]);
//			memberVO.setMemberAddress(applyData[16]);
//
//			accountVO.setMemberVO(memberVO);
//		}		
//		
		return accountVO;
	}
	
	public String[] applyCookieDate(HttpServletRequest request) throws Exception{
		
		String[] applyData = null;
//		// 讀取cookie
//		String cookieData = CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_AKBPFP_APPLY.getValue(), 
//								EnumCookieConstants.COOKIE_USING_CODE.getValue());
//		
//		if(StringUtils.isNotBlank(cookieData)){
//			String decodeData = EncodeUtil.getInstance().decryptAES(cookieData, EnumCookieConstants.COOKIE_SECRET_KEY.getValue());
//			applyData = decodeData.split(",");
//		}
		
		return applyData;
	}
	
	public void writeCookie(HttpServletResponse response, String data) throws Exception{
		
//		this.clearCookie(response);
//		
//		// 寫入cookie 
//		String encodeCookie = EncodeUtil.getInstance().encryptAES(data, EnumCookieConstants.COOKIE_SECRET_KEY.getValue());
//		
//		CookieUtil.writeCookie(response, EnumCookieConstants.COOKIE_AKBPFP_APPLY.getValue(), encodeCookie,	
//								EnumCookieConstants.COOKIE_DOMAIN.getValue(), EnumCookieConstants.COOKIE_APPLY_AGE, 
//								EnumCookieConstants.COOKIE_USING_CODE.getValue());
		
	}
	
	public void clearCookie(HttpServletResponse response){
		
//		// 清掉cookie
//		Cookie deleteCookie = new Cookie(EnumCookieConstants.COOKIE_AKBPFP_APPLY.getValue(), EnumCookieConstants.COOKIE_USING_CODE.getValue());
//		deleteCookie.setDomain(".pchome.com.tw");
//		deleteCookie.setMaxAge(0);
//		deleteCookie.setPath("/");
//		response.addCookie(deleteCookie);
	}
}
