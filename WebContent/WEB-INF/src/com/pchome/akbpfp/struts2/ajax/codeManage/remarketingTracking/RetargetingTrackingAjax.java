package com.pchome.akbpfp.struts2.ajax.codeManage.remarketingTracking;

import java.util.HashMap;
import java.util.Map;

import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.service.portalcms.bean.Mail;
import com.pchome.soft.util.SpringEmailUtil;

public class RetargetingTrackingAjax extends BaseCookieAction {

	private static final long serialVersionUID = 1L;
	
	private String mailFrom;  

	// private String[] filterContentMap;
	private String mailReceivers;
	private String mailContent;
	private String result;
	private Map<String, Object> resultMap;

	private SpringEmailUtil springEmailUtil;
	// private int currentPage = 1; // 第幾頁(初始預設第1頁)
	// private int pageSizeSelected = 10; // 每頁筆數(初始預設每頁10筆)
	// private int totalCount = 0; // 資料總筆數
	// private int pageCount = 0; // 總頁數
	// private List<Object> prodList;

	// private IPfpAdService pfpAdService;
	// private IPfpAdDetailService pfpAdDetailService;

	/**
	 * 再行銷sendmail
	 */
	public String sendRetargetingTrackingMailAjax() {
		try {
			log.info(">>> mailReceivers: " + mailReceivers);
			log.info(">>> mailContent: " + mailContent);

			resultMap = new HashMap<String, Object>();
			
			String[] mailReceiversAry= mailReceivers.split(";");

			// 出錯時寄信
//			Mail mail = null;
//			mail = PortalcmsUtil.getInstance().getMail("P098");
//			if (mail == null) {
//				throw new Exception("Mail Object is null.");
//			}
			Mail mail = new Mail();
			String subject = "［PChome聯播網］再行銷代碼";
//			mail.setMailFrom("adcl@msx.pchome.com.tw");
			mail.setMailTo(mailReceiversAry);
			mail.setMsg(mailContent);
			springEmailUtil.sendHtmlEmail(subject, mailFrom, mail.getMailTo(), mail.getMailBcc(), mail.getMsg());

			resultMap.put("msg", "mail發送成功");
			resultMap.put("status", "SUCCESS");

		} catch (Exception e) {
			log.error("error:" + e);
			resultMap.put("msg", "mail發送失敗，系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			resultMap.put("status", "ERROR");
			return SUCCESS;
		}

		return SUCCESS;
	}

	
//	/**
//	 * 回傳錯誤訊息
//	 */
//	public Map<String, Object> returnErrorMsgMap(String errorMsg) {
//
//		Map<String, Object> errorMsgMap = new HashMap<String, Object>();
//		errorMsgMap.put("currentPage", 1);
//		errorMsgMap.put("pageCount", 1);
//		errorMsgMap.put("totalCount", 0);
//		errorMsgMap.put("status", "ERROR");
//		errorMsgMap.put("msg", errorMsg);
//
//		return errorMsgMap;
//	}

	
	
	
	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getMailReceivers() {
		return mailReceivers;
	}

	public void setMailReceivers(String mailReceivers) {
		this.mailReceivers = mailReceivers;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	public SpringEmailUtil getSpringEmailUtil() {
		return springEmailUtil;
	}

	public void setSpringEmailUtil(SpringEmailUtil springEmailUtil) {
		this.springEmailUtil = springEmailUtil;
	}
	
	
}