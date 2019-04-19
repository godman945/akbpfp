package com.pchome.akbpfp.db.service.codeManage;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCodeConvertRule;
import com.pchome.akbpfp.db.vo.codeManage.ConvertTrackingVO;


public interface IPfpCodeConvertRuleDAO extends IBaseDAO<PfpCodeConvertRule,String>{
	
	public void saveOrUpdateWithCommit(PfpCodeConvertRule pfpCodeConvertRule) throws Exception;
	
	public void deletePfpCodeConvertRule(String convertSeq) throws Exception;
	
	public List<Map<String,Object>> getPfpCodeConvertRuleByCondition(ConvertTrackingVO convertTrackingVO) throws Exception;
}
