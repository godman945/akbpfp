package com.pchome.akbpfp.db.service.codeManage;

import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpCodeConvertRule;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.akbpfp.db.vo.codeManage.ConvertTrackingRuleVO;
import com.pchome.akbpfp.db.vo.codeManage.ConvertTrackingVO;

public interface IPfpCodeConvertRuleService extends IBaseService<PfpCodeConvertRule,String>{
	
	public void saveOrUpdateWithCommit(PfpCodeConvertRule pfpCodeConvertRule)throws Exception;
	
	public void deletePfpCodeConvertRule(String convertSeq) throws Exception;
	
	public List<ConvertTrackingRuleVO>  getPfpCodeConvertRuleByCondition(ConvertTrackingVO convertTrackingVO) throws Exception;
}