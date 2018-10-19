package com.pchome.akbpfp.api;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.pchome.akbpfp.db.vo.member.MemberVO;
import com.pchome.enumerate.redirect.EnumRedirectAction;
import com.pchome.soft.depot.utils.HttpUtil;
import com.pchome.soft.depot.utils.RSAUtils;

public class MemberAPI {
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	private String memberServer;

	public MemberVO getMemberVOData(String memberId) throws Exception{
		
		MemberVO vo = null;
		Map<String,String> map = this.getMemberMapData(memberId);
		
		if(map.size() > 0){
			vo = new MemberVO();
			vo.setMemberId(map.get("id"));
			vo.setMemberName(map.get("user_name"));
			vo.setMemberSex(map.get("sexuality"));
			vo.setMemberCheckMail(map.get("email"));
			vo.setMemberBirthday(map.get("birthday"));
			vo.setMemberTelephone(map.get("phone")); // 格式:0222454667
			vo.setMemberMobile(map.get("cellphone"));
			vo.setMemberZip(map.get("zipcode"));
			vo.setMemberAddress(map.get("address"));
			vo.setAuth(map.get("auth"));
		}
		
		return vo;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,String> getMemberMapData(String memberId) throws Exception{
		
		Map<String,String> map = new LinkedHashMap<String,String>();
		
		String url = memberServer+EnumRedirectAction.MEMBER_API_FIND_MEMBER.getAction()+"?ad_user_id="+memberId;
		log.info("url = "+url);
		
		String json = HttpUtil.getInstance().getResult(url, "UTF-8");		
		log.info(" member json = "+json);
		
		JSONObject jsonObject = new JSONObject(json);
		
		Iterator<String> keys = jsonObject.keys();
		
		while(keys.hasNext()){
			String key = keys.next();
			String value = jsonObject.get(key).toString();
			map.put(key, value);
		}
		log.info("map:"+map);
		return map;
	}
	
	public void updateMemberData(MemberVO memberVO) throws Exception {

		StringBuffer sb = new StringBuffer();

		sb.append("<member>");
		sb.append("<id>").append(memberVO.getMemberId()).append("</id>");
		sb.append("<user_name>").append(memberVO.getMemberName()).append("</user_name>");
		sb.append("<sexuality>").append(memberVO.getMemberSex()).append("</sexuality>");
		sb.append("<birthday>").append(memberVO.getMemberBirthday()).append("</birthday>");
		sb.append("<phone>").append(memberVO.getMemberTelephone()).append("</phone>");
		sb.append("<cellphone>").append(memberVO.getMemberMobile()).append("</cellphone>");
		sb.append("<zipcode>").append(memberVO.getMemberZip()).append("</zipcode>");
		sb.append("<address>").append(memberVO.getMemberAddress()).append("</address>");
		sb.append("</member>");

		String data = sb.toString();
		log.info(">>> data = " + data);

		String encodeData = URLEncoder.encode(data, "UTF-8");
		log.info(">>> encodeData = " + encodeData);

		encodeData = URLEncoder.encode(encodeData, "UTF-8");
		log.info(">>> encodeData2 = " + encodeData);

		String url = memberServer+EnumRedirectAction.MEMBER_API_UPDATE_MEMBER.getAction()+"?ad_user_data="+encodeData;
		log.info(">>> url = " + url);
		
		String json = HttpUtil.getInstance().getResult(url, "UTF-8");
		log.info(">>> json = " + json);
	}
	
	public String checkAvailableEmail(String email) throws Exception{
		
		String pcId = null;
		String url = memberServer+EnumRedirectAction.MEMBER_API_CHECK_AVAILABLE_EMAIL.getAction()+
					"?ad_email="+email;
		
		String json = HttpUtil.getInstance().getResult(url, "UTF-8");

		JSONObject jsonObject = new JSONObject(json);
		String stat = jsonObject.get("stat").toString();

		if(stat.equals("yes")){
			pcId = jsonObject.get("msg").toString();
		}
		
		return pcId;
	}	

	public void setMemberServer(String memberServer) {
		this.memberServer = memberServer;
	}

	public static void main(String[] args) {

		MemberVO memberVO = new MemberVO();
		memberVO.setMemberId("richard0120");
		memberVO.setMemberName("大白大白大白大白大白");
		memberVO.setMemberSex("M");
		memberVO.setMemberBirthday("1971-06-12");
		memberVO.setMemberTelephone("02-22660066");
		memberVO.setMemberMobile("0922555666");
		memberVO.setMemberZip("106");
		memberVO.setMemberAddress("今天清晨各地氣溫已經比昨天回升了大約2度左右，台北大約17度，嘉義也有16度，不過對人體來說還是相當冷");

		MemberAPI api = new MemberAPI();
		try {
			api.updateMemberData(memberVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
