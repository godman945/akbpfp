package com.pchome.akbpfp.struts2;

import java.util.EnumMap;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pchome.enumerate.cookie.EnumCookieConstants;
import com.pchome.enumerate.cookie.EnumCookiePfpKey;
import com.pchome.soft.depot.utils.CookieStringToMap;
import com.pchome.soft.depot.utils.CookieUtil;
import com.pchome.soft.depot.utils.EncodeUtil;
import com.pchome.soft.util.DateValueUtil;


public class BaseCookieAction extends BaseAction {

	private static final long serialVersionUID = 3713211934251786551L;

	protected Logger log = LogManager.getRootLogger();

	// 個人資訊
    private String id_pchome;
    private String user_id;
    private String bu_id;
    private String user_status;
    private String user_privilege;
    private String customer_info_id;
    private String customer_info_title;
    private String pfd_customer_info_id;
    private String pfd_user_id;
    private String pay_type;			// 付款方式
    private String root_user;			// 管理者登入
    private EnumMap<EnumCookiePfpKey, String> cookieMap;
    
    // 共用查詢時間
    private String choose_start_date;	// 查詢開始時間
	private String choose_end_date;		// 查詢結束時間
	private String choose_date;

	public String getId_pchome() {
		return id_pchome;
	}

	public void setId_pchome(String id_pchome) {
		this.id_pchome = id_pchome;
	}

	public String getUser_id() {
		if (cookieMap != null) {
			user_id = cookieMap.get(EnumCookiePfpKey.PFP_USER_ID);
		}
		return user_id;
	}

	public String getUser_status() {
		if (cookieMap != null) {
			user_status = cookieMap.get(EnumCookiePfpKey.PFP_USER_STATUS);
		}
		return user_status;
	}

	public String getUser_privilege() {
		if (cookieMap != null) {
			user_privilege = cookieMap.get(EnumCookiePfpKey.PFP_USER_PRIVILEGE_ID);
		}
		return user_privilege;
	}

	public String getCustomer_info_id() {
		if (cookieMap != null) {
			customer_info_id = cookieMap.get(EnumCookiePfpKey.PFP_CUSTOMER_INFO_ID);
		}
		return customer_info_id;
	}
	
	public String getCustomer_info_title() {
		if (cookieMap != null) {
			customer_info_title = cookieMap.get(EnumCookiePfpKey.PFP_CUSTOMER_TITLE);
		}
		return customer_info_title;
	}
	
	public String getPfd_customer_info_id() {
		if (cookieMap != null) {
			pfd_customer_info_id = cookieMap.get(EnumCookiePfpKey.PFD_CUSTOMER_INFO_ID);
		}
		return pfd_customer_info_id;
	}

	public String getPfd_user_id() {
		if (cookieMap != null) {
			pfd_user_id = cookieMap.get(EnumCookiePfpKey.PFD_USER_ID);
		}
		return pfd_user_id;
	}

	public String getPay_type() {
		if (cookieMap != null) {
			pay_type = cookieMap.get(EnumCookiePfpKey.PFP_PAY_TYPE);
		}
		return pay_type;
	}

	public String getRoot_user() {
		if (cookieMap != null) {
			root_user = cookieMap.get(EnumCookiePfpKey.MANAGER);
		}
		return root_user;
	}

	public void setAkb_pfp_user(String akb_pfp_user) {
		cookieMap = CookieStringToMap.getInstance().transformEnumMap(akb_pfp_user);
	}
	
	// 共用查詢時間
	public String getChoose_start_date() {
		if (StringUtils.isBlank(this.choose_date)) {
			this.defalutDate();
		} else {
			if (this.choose_date.length() < 4) {
				this.defalutDate();
			} else {
				choose_start_date = this.choose_date.split(",")[0];
			}
		}

		return choose_start_date;
	}
	
	public String getChoose_end_date() {
		if (StringUtils.isBlank(this.choose_date)) {
			this.defalutDate();
		} else {
			if (this.choose_date.length() < 4) {
				this.defalutDate();
			} else {
				choose_end_date = this.choose_date.split(",")[1];
			}
		}

		return choose_end_date;
	}
	
	public void setChooseDate(String chooseStartDate, String chooseEndDate){
		String content = chooseStartDate+","+chooseEndDate;
		String encodeCookie = EncodeUtil.getInstance().encryptAES(content, EnumCookieConstants.COOKIE_PFP_SECRET_KEY.getValue());
		
		CookieUtil.writeCookie(super.response, EnumCookieConstants.COOKIE_CHOOSE_DATE.getValue(), encodeCookie,	
				EnumCookieConstants.COOKIE_PCHOME_DOMAIN.getValue(), EnumCookieConstants.COOKIE_TWELVE_HOUR_AGE, null);
	}

	public void setChoose_date(String choose_date) {
		this.choose_date = EncodeUtil.getInstance().decryptAES(choose_date,EnumCookieConstants.COOKIE_PFP_SECRET_KEY.getValue());
	}
	
	private void defalutDate(){		
		choose_end_date = DateValueUtil.getInstance().getDateValue(DateValueUtil.TODAY, DateValueUtil.DBPATH);
		choose_start_date = DateValueUtil.getInstance().getDateValue(-30, DateValueUtil.DBPATH);
		this.setChooseDate(choose_start_date, choose_end_date);
	}

	public String getBu_id() {
		bu_id = cookieMap.get(EnumCookiePfpKey.PFB_BU_ACCOUND);
		return bu_id;
	}

	public void setBu_id(String bu_id) {
		this.bu_id = bu_id;
	}
	
}