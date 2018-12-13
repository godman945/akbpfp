package com.pchome.akbpfp.db.service.codeManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.pojo.PfpCodeConvertRule;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.codeManage.ConvertTrackingRuleVO;
import com.pchome.akbpfp.db.vo.codeManage.ConvertTrackingVO;

public class PfpCodeConvertRuleService extends BaseService<PfpCodeConvertRule,String> implements IPfpCodeConvertRuleService{
	
	public void saveOrUpdateWithCommit(PfpCodeConvertRule pfpCodeConvertRule) throws Exception{
    	((IPfpCodeConvertRuleDAO) dao).saveOrUpdateWithCommit(pfpCodeConvertRule);
	}
	
	public void deletePfpCodeConvertRule(String convertSeq) throws Exception{
		((IPfpCodeConvertRuleDAO)dao).deletePfpCodeConvertRule(convertSeq);
	}
	
	public List<ConvertTrackingRuleVO> getPfpCodeConvertRuleByCondition(ConvertTrackingVO convertTrackingVO) throws Exception{
		List<Map<String,Object>> convertTrackingRuleList = ((IPfpCodeConvertRuleDAO)dao).getPfpCodeConvertRuleByCondition(convertTrackingVO);
		List<ConvertTrackingRuleVO> convertTrackingRuleVOList = new ArrayList<ConvertTrackingRuleVO>();
		
		if( (!convertTrackingRuleList.isEmpty()) && (convertTrackingRuleList.size()>0) ){
			for (Object object : convertTrackingRuleList) {
				ConvertTrackingRuleVO convertTrackingRuleBean = new ConvertTrackingRuleVO();
				Map obj = (Map) object;
				
				convertTrackingRuleBean.setConvertRuleId(obj.get("convert_rule_id").toString());	
				convertTrackingRuleBean.setConvertSeq(obj.get("convert_seq").toString());
				convertTrackingRuleBean.setConvertRuleWay(obj.get("convert_rule_way").toString());
				convertTrackingRuleBean.setConvertRuleValue(obj.get("convert_rule_value").toString());
				
				convertTrackingRuleVOList.add(convertTrackingRuleBean);
			}
		}
		
		return convertTrackingRuleVOList;
	}
	
	
}
