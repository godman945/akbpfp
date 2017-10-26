package com.pchome.akbpfp.data.threadprocess;

import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.pchome.akbpfp.db.service.ad.IPfpAdService;
import com.pchome.akbpfp.db.vo.ad.PfpAdAdVideoViewSumVO;
import com.pchome.akbpfp.db.vo.ad.PfpAdAdVideoViewVO;
import com.pchome.akbpfp.db.vo.ad.PfpAdAdViewConditionVO;
import com.pchome.enumerate.thread.EnumAdThreadType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PfpThreadProcess implements Callable<String> {

	Log log = LogFactory.getLog(PfpThreadProcess.class);
	
	private ThreadServiceBean threadServiceBean;
	private JSONObject conditionJson;
	private EnumAdThreadType enumAdThreadType;
	
	public PfpThreadProcess(JSONObject conditionJson,EnumAdThreadType enumAdThreadType,ThreadServiceBean threadServiceBean) {
		this.threadServiceBean = threadServiceBean;
		this.conditionJson = conditionJson;
		this.enumAdThreadType = enumAdThreadType;
		
	}

	public String call() throws Exception {
		//影音廣告明細總數
		if(enumAdThreadType.equals(EnumAdThreadType.AD_VIEW_VIDEO_COUNT)){
			PfpAdAdViewConditionVO pfpAdAdViewConditionVO = (PfpAdAdViewConditionVO) JSONObject.toBean(this.conditionJson, PfpAdAdViewConditionVO.class);
			IPfpAdService pfpAdService = this.threadServiceBean.getPfpAdService();
			PfpAdAdVideoViewSumVO pfpAdAdVideoViewSumVO = pfpAdService.getAdAdVideoDetailViewCount(pfpAdAdViewConditionVO);
			JSONObject result = JSONObject.fromObject(pfpAdAdVideoViewSumVO);
			return result.toString();
			
		}
		
		//影音廣告明細
		if(enumAdThreadType.equals(EnumAdThreadType.AD_VIEW_VIDEO_DETAIL)){
			PfpAdAdViewConditionVO pfpAdAdViewConditionVO = (PfpAdAdViewConditionVO) JSONObject.toBean(this.conditionJson, PfpAdAdViewConditionVO.class);
			IPfpAdService pfpAdService = this.threadServiceBean.getPfpAdService();
			List<PfpAdAdVideoViewVO> pfpAdAdVideoViewVOList = pfpAdService.getAdAdVideoDetailView(pfpAdAdViewConditionVO);	
			
			JSONArray resultList = new JSONArray();
			for (PfpAdAdVideoViewVO pfpAdAdVideoViewVO : pfpAdAdVideoViewVOList) {
				JSONObject json = JSONObject.fromObject(pfpAdAdVideoViewVO);
				resultList.add(json);
			}
			return resultList.toString();
		}
		
		//取得youtube影片網址
		if(enumAdThreadType.equals(EnumAdThreadType.AD_VIDEO_PLAY_URL)){
			
		}
		return null;
	}
}