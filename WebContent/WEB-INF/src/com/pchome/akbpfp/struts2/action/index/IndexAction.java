package com.pchome.akbpfp.struts2.action.index;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.pchome.akbpfd.db.service.user.PfdUserMemberRefService;
import com.pchome.akbpfp.db.pojo.PfpUserMemberRef;
import com.pchome.akbpfp.db.service.user.PfpUserMemberRefService;
import com.pchome.akbpfp.db.vo.faq.FaqListVO;
import com.pchome.akbpfp.db.vo.faq.FaqQuestionVO;
import com.pchome.akbpfp.db.vo.faq.FaqSolutionVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.account.EnumPfpRootUser;
import com.pchome.enumerate.privilege.EnumPrivilegeModel;
import com.pchome.utils.HttpUtil;



public class IndexAction extends BaseCookieAction {
	
    	private static final long serialVersionUID = 1L;
	private PfpUserMemberRefService pfpUserMemberRefService;
	private PfdUserMemberRefService pfdUserMemberRefService;
	private String result="success";

	private List<FaqListVO> faqListVOs = new ArrayList<FaqListVO>();	// 總攬頁項目及內容的列表物件
	private ArrayList<FaqQuestionVO> faqQuestionVOs = new ArrayList<FaqQuestionVO>();	// 總攬頁項目及內容的列表物件
	private FaqSolutionVO faqSolutionVO = new FaqSolutionVO();			// 問題解答的物件
	private String faqServerMain;		// faq 問題總攬頁的 api 網址
	private String faqServerGroup;		// faq 問題分類頁的 api 網址
	private String faqServerSolution;	// faq 查詢問題答案的 api 網址

	private String fid;	// faq 項目列表ID
	private String qid;	// faq 問題列表ID
	private int lid = 1;	// 紀錄目前選到哪個項目用的 ID
	private String sid;	// 原本是faq換頁使用，現在採 ajax 處理，不用換頁，所以目前無用
	private String qimg = "q01";
	private InputStream msg;	// 回傳問題的答案使用
	private String accountType;
	public String execute() throws Exception{
	    //判斷登入者使否取有小天使權限或PFD切換權限
	    boolean pfpAngelFlag= false;
	    List<PfpUserMemberRef> pfpUserMemberRefList = pfpUserMemberRefService.loadAll();
	    for (PfpUserMemberRef pfpUserMemberRef : pfpUserMemberRefList) {
		if(pfpUserMemberRef.getId().getMemberId().equals(super.getId_pchome()) && pfpUserMemberRef.getPfpUser().getPrivilegeId() == EnumPrivilegeModel.ADM_USER.getPrivilegeId()){
		    pfpAngelFlag = true;
		    break;
		}
	    }
	    if(pfpAngelFlag){
		accountType = EnumPfpRootUser.PCHOME_MANAGER.getPrivilege();
	    }else if(!pfpAngelFlag){
		accountType = EnumPfpRootUser.NO.getPrivilege();
	    }
	    
		/*
		this.checkRedirectSSLUrl();
		if(StringUtils.isNotBlank(this.resultType)){
			return this.resultType;
		}
		
		String ref = request.getHeader("Referer");
		log.info("refer: "+ref);
		if(StringUtils.isNotBlank(ref)){
			log.info("Enum: "+EnumRedirectAction.CLASSIFIEDS_DEVURL.getAction().toString());
			log.info("boolean : "+ref.contains(EnumRedirectAction.CLASSIFIEDS_DEVURL.getAction().toString()));
			if(ref.contains(EnumRedirectAction.CLASSIFIEDS_DEVURL.getAction().toString())){
				//TODO redirect to directions pages
				log.info("return INPUT to open.html");
				return "open";
			}
		}
		
		if(StringUtils.isNotBlank(super.getId_pchome())){
			// 取使用者資料
			List<PfpUserMemberRef> userMemberRefs = PfpUserMemberRefService.activateUserMemberRef(super.getId_pchome());

			String ref = request.getHeader("Referer");
			// 帳號不存在
			if(userMemberRefs.size() <= 0){
				
				//從免費刊登進入(無帳號)導open頁
				log.info("refer: "+ref);
				if(StringUtils.isNotBlank(ref)){
					if(ref.contains(EnumRedirectAction.CLASSIFIEDS_DEVURL.getAction().toString())){
						log.info("return INPUT to open.html");
						return "open";
					}
				}
			}else{
				//從免費刊登進入(有pfp帳號)導入原網頁
				String url = request.getRequestURL().toString();
				log.info("url == "+url);
				redirectUrl = url;
				return "refer";
				
			}
		}
		*/
		
		HttpUtil.doGet("https://billing.pchome.com.tw/rmiservice/pv.html", "t=pfp:ck_pfp_index");
		return result;
	}
	
	/**
	 * 首頁 - 廣告優勢
	 * @return
	 * @throws Exception
	 */
	public String advantage() throws Exception{
		
		HttpUtil.doGet("https://billing.pchome.com.tw/rmiservice/pv.html", "t=pfp:ck_pfp_advantage");
		return result;
	}

	/**
	 * 首頁 - 活動一(主要)
	 * @return
	 * @throws Exception
	 */
	public String click() throws Exception{
		
		HttpUtil.doGet("https://billing.pchome.com.tw/rmiservice/pv.html", "t=pfp:ck_pfp_click");
		return result;
	}

	/**
	 * 首頁 - 活動二(兩個活動要同時出現才會用到)
	 * @return
	 * @throws Exception
	 */
	public String click2() throws Exception{
		
		HttpUtil.doGet("https://billing.pchome.com.tw/rmiservice/pv.html", "t=pfp:ck_pfp_click2");
		return result;
	}
	
	/**
	 * 首頁 - 收費及付款
	 * @return
	 * @throws Exception
	 */
	public String pay() throws Exception{
		
		HttpUtil.doGet("https://billing.pchome.com.tw/rmiservice/pv.html", "t=pfp:ck_pfp_pay");
		return result;
	}
	
	/**
	 * 首頁 - 熱門問答
	 * @return
	 * @throws Exception
	 */
	public String qa() throws Exception{
		
		HttpUtil.doGet("https://billing.pchome.com.tw/rmiservice/pv.html", "t=pfp:ck_pfp_qa");
		return result;
	}
	
	/**
	 * 首頁 - 服務中心
	 * @return
	 * @throws Exception
	 */
	public String faq() throws Exception{
		if(StringUtils.isBlank(fid)) {
			fid = "1";
		}

		// 設定 faq api 讀取服務中心的網址物件
		URL mURL = new URL(faqServerMain + "?channel=show");
		// 設定 dom4j 讀取 XML 的物件
		SAXReader reader = new SAXReader();
		// 呼叫 faq api，並設定為 document 物件 
		Document document = reader.read(mURL);
		// 將 root tag 的內容，設定到 Element
		Element root = document.getRootElement();
		// 項目列表序號用
		int tmp1 = 0;

		// 開始讀取 faq 的內容
        for ( Iterator<?> i = root.elementIterator(); i.hasNext(); ) {
        	// 序號 +1
        	tmp1++;
        	Element element = (Element) i.next();
        	// 如果項目是文字資料的話
			if(element.isTextOnly()) {
			} else {	// 項目不為文字資料，則繼續讀取下一層物件資料
				// 項目列表物件
				FaqListVO sFaqListVO = new FaqListVO();
				// 設定項目序號 
				sFaqListVO.setNo(tmp1);

				// 開始讀取問題資料
				for ( Iterator<?> j = element.elementIterator(  ); j.hasNext(); ) {
					Element foo = (Element) j.next();
					// 如果為文字，則此項為項目的名字(例如：新手上路、常見問題....)
					if(foo.isTextOnly()) {
						if(foo.getName().equals("name")) {
							sFaqListVO.setName(foo.getTextTrim());
						} else if(foo.getName().equals("f_id")) {
							String fid = foo.getTextTrim();
							sFaqListVO.setFid(fid);
						}
					}
				}
				// 將項目的物件加進項目列表物件裡
		        faqListVOs.add(sFaqListVO);
				
		        // 僅顯示選中項目的問題列表
		        if(sFaqListVO.getFid().equals(fid)) {
					// 讀取問題列表
					getFaqQuestionListVO(fid, sFaqListVO);
		        }
			}
		}

		// 設定 "紀錄目前選到哪個項目用的 ID"(左側列表的黃棒棒) 的預設值為 1 
		if(StringUtils.isNotBlank(fid)) {
			try {
				lid = Integer.parseInt(fid);
				if(StringUtils.isNotBlank(qid)) {
					faqSolutionVO = getSolution();
				}
			} catch(Exception e) {
				System.out.println(e.toString()); //print Exception message
			}
		}
		
		HttpUtil.doGet("https://billing.pchome.com.tw/rmiservice/pv.html", "t=pfp:ck_pfp_faq");
		return result;
	}

	// 呼叫問題列表的 api，讀取問題列表
	private void getFaqQuestionListVO(String f_id, FaqListVO sFaqListVO) throws Exception{
		// 呼叫分類 api，才會有完整的分類內容
		// 設定 faq api 讀取問題分類的網址物件
		URL gURL = new URL(faqServerGroup + "?channel=show&f_id=" + f_id);
		// 設定 dom4j 讀取 XML 的物件
		SAXReader readerG = new SAXReader();
		// 呼叫 faq api，並設定為 document 物件 
		Document documentG = readerG.read(gURL);
		// 將 root tag 的內容，設定到 Element
		Element rootG = documentG.getRootElement();

		// 開始讀取 faq 的內容
        for ( Iterator<?> g = rootG.elementIterator(); g.hasNext(); ) {
        	Element elementG = (Element) g.next();
        	// 如果項目是文字資料的話
			if(elementG.isTextOnly()) {
			} else {	// 項目不為文字資料，則繼續讀取下一層物件資料
				// 問題列表物件
				//ArrayList<FaqQuestionVO> faqQuestionVOs = new ArrayList<FaqQuestionVO>();
				// 問題列表序號
				int tmpG = 0;
	        	// 開始讀取問題內容
				for ( Iterator<?> k = elementG.elementIterator(  ); k.hasNext(); ) {
					Element hoo = (Element) k.next();
					// 目前 faq 僅到這一層，日後有新增一個層級的話，就要再進行讀取
					// 讀取問題的標題(content)、項目ID(fid)、問題ID(qid)
					if(hoo.isTextOnly()) {
					} else {
						// 問題列表物件
			        	FaqQuestionVO faqQuestionVO = new FaqQuestionVO();
						// 開始讀取問題資料
						for ( Iterator<?> i1 = hoo.elementIterator(  ); i1.hasNext(); ) {
							Element qoo = (Element) i1.next();
							if(qoo.isTextOnly()) {
								// 讀取問題的標題(content)
					        	if(qoo.getName().equals("content")) {
					        		faqQuestionVO.setFaqContent(qoo.getTextTrim());
					        	} else if(qoo.getName().equals("f_id")) {	// 項目ID(fid)
					        		faqQuestionVO.setFaqFId(qoo.getTextTrim());
					        	} else if(qoo.getName().equals("q_id")) {	// 問題ID(qid)
					        		faqQuestionVO.setFaqQId(qoo.getTextTrim());
					        	}
							}
						}
						// 問題列表序號
			        	tmpG ++;
						// 這是為了可以順利抓到 CSS 設定的問題項目圖檔所設定的編號
			        	String imgNo = "01";
						if(tmpG < 10 ) {
							imgNo = "0" + Integer.toString(tmpG);
						} else {
							imgNo = Integer.toString(tmpG);
						}
						faqQuestionVO.setNo(imgNo);
						
		        		if(faqQuestionVO.getFaqFId().equals(fid) && faqQuestionVO.getFaqQId().equals(qid)) {
		        			qimg = "q" + imgNo;
		        		}

		        		// 將問題內容設定到問題列表物件
						faqQuestionVOs.add(faqQuestionVO);
					}
				}
				// 將問題列表的物件設定進項目對應的物件中
				sFaqListVO.setFaqQuestionVOs(faqQuestionVOs);
			}
        }
	}
	
	/**
	 * 首頁 - 讀取答案
	 * @return
	 * @throws Exception
	 */
	private FaqSolutionVO getSolution() throws Exception{
		FaqSolutionVO faqSolutionVO = new FaqSolutionVO();			// 問題解答的物件
		
		// 設定 faq api 讀取服務中心的網址物件
		URL sURL = new URL(faqServerSolution + "?channel=show&f_id=" + fid + "&q_id=" + qid);
		// 設定 dom4j 讀取 XML 的物件
		SAXReader reader = new SAXReader();
		// 呼叫 faq api，並設定為 document 物件 
        Document document = reader.read(sURL);
		// 將 root tag 的內容，設定到 Element
        Element root = document.getRootElement();

        // 讀取答案內容
        for ( Iterator<?> i = root.elementIterator(); i.hasNext(); ) {
        	Element element = (Element) i.next();
			// 讀取問題的標題(content)、問題答案(content)
			if(element.isTextOnly()) {
				if(element.getName().equals("question")) {
					faqSolutionVO.setFaqQuestion(element.getTextTrim());
				} else if(element.getName().equals("content")) {
					String content = element.getTextTrim().replaceAll("(?i)(&lt;)","<")
							.replaceAll("(?i)(&gt;)",">")
							.replaceAll("(?i)(&quot;)","\"")
							.replaceAll("(?i)(&amp;)","&");

					faqSolutionVO.setFaqSolutionContent(content);
				}
			}
        }

        return faqSolutionVO;
	}
	
	/**
	 * 首頁 - 服務中心 - 答案
	 * @return
	 * @throws Exception
	 */
	public String faqSolution() throws Exception{

//		// 設定 faq api 讀取服務中心的網址物件
//		URL sURL = new URL(faqServerSolution + "?channel=show&f_id=" + fid + "&q_id=" + qid);
//		// 設定 dom4j 讀取 XML 的物件
//		SAXReader reader = new SAXReader();
//		// 呼叫 faq api，並設定為 document 物件 
//		Document document = reader.read(sURL);
//		// 將 root tag 的內容，設定到 Element
//		Element root = document.getRootElement();
//
//		// 讀取答案內容
//		for ( Iterator i = root.elementIterator(); i.hasNext(); ) {
//			Element element = (Element) i.next();
//			// 讀取問題的標題(content)、問題答案(content)
//			if(element.isTextOnly()) {
//				if(element.getName().equals("question")) {
//					faqSolutionVO.setFaqQuestion(element.getTextTrim());
//				} else if(element.getName().equals("content")) {
//					faqSolutionVO.setFaqSolutionContent(element.getTextTrim());
//				}
//			}
//		}
		// 回傳答案的內容
		msg = new ByteArrayInputStream(getSolution().getFaqSolutionContent().getBytes("UTF-8"));

        return SUCCESS;
	}

	/**
	 * 首頁 - 全站曝光
	 * @return
	 * @throws Exception
	 */
	public String show() throws Exception{
		
		HttpUtil.doGet("https://billing.pchome.com.tw/rmiservice/pv.html", "t=pfp:ck_pfp_show");
		return result;
	}
	
	public String open() throws Exception{
		
		return result;
	}
	
//	public String rule() throws Exception{
//			
//		return result;
//	}
	

	public List<FaqListVO> getFaqListVOs() {
		return faqListVOs;
	}

	public PfpUserMemberRefService getPfpUserMemberRefService() {
	    return pfpUserMemberRefService;
	}

	public void setPfpUserMemberRefService(
		PfpUserMemberRefService pfpUserMemberRefService) {
	    this.pfpUserMemberRefService = pfpUserMemberRefService;
	}

	public PfdUserMemberRefService getPfdUserMemberRefService() {
	    return pfdUserMemberRefService;
	}

	public void setPfdUserMemberRefService(
		PfdUserMemberRefService pfdUserMemberRefService) {
	    this.pfdUserMemberRefService = pfdUserMemberRefService;
	}

	public void setFaqListVOs(List<FaqListVO> faqListVOs) {
		this.faqListVOs = faqListVOs;
	}

	public ArrayList<FaqQuestionVO> getFaqQuestionVOs() {
		return faqQuestionVOs;
	}

	public void setFaqQuestionVOs(ArrayList<FaqQuestionVO> faqQuestionVOs) {
		this.faqQuestionVOs = faqQuestionVOs;
	}

	public FaqSolutionVO getFaqSolutionVO() {
		return faqSolutionVO;
	}

	public void setFaqSolutionVO(FaqSolutionVO faqSolutionVO) {
		this.faqSolutionVO = faqSolutionVO;
	}

	public String getFaqServerMain() {
		return faqServerMain;
	}

	public void setFaqServerMain(String faqServerMain) {
		this.faqServerMain = faqServerMain;
	}

	public String getFaqServerGroup() {
		return faqServerGroup;
	}

	public void setFaqServerGroup(String faqServerGroup) {
		this.faqServerGroup = faqServerGroup;
	}

	public String getFaqServerSolution() {
		return faqServerSolution;
	}

	public void setFaqServerSolution(String faqServerSolution) {
		this.faqServerSolution = faqServerSolution;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public int getLid() {
		return lid;
	}

	public void setLid(int lid) {
		this.lid = lid;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getQimg() {
		return qimg;
	}

	public void setQimg(String qimg) {
		this.qimg = qimg;
	}

	public InputStream getMsg() {
		return msg;
	}

	public String getAccountType() {
	    return accountType;
	}

	public void setAccountType(String accountType) {
	    this.accountType = accountType;
	}

	

}