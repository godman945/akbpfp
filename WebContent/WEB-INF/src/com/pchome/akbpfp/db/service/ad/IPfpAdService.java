package com.pchome.akbpfp.db.service.ad;

import java.util.Date;
import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpAd;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.akbpfp.db.vo.ad.PfpAdAdVideoViewSumVO;
import com.pchome.akbpfp.db.vo.ad.PfpAdAdVideoViewVO;
import com.pchome.akbpfp.db.vo.ad.PfpAdAdViewConditionVO;
import com.pchome.akbpfp.db.vo.ad.PfpAdAdViewVO;
import com.pchome.enumerate.ad.EnumAdType;


public interface IPfpAdService extends IBaseService<PfpAd,String>{
	
	public PfpAd getPfpAdBySeq(String adSeq) throws Exception;

	public void saveOrUpdateWithCommit(PfpAd adAd) throws Exception;
	
	public List<PfpAdAdViewVO> getAdAdView(String customerInfoId, String adGroupSeq, String keyword, EnumAdType enumAdType, Date startDate, Date endDate, int page, int pageSize) throws Exception;
	
	public List<PfpAdAdViewVO> getAllAdAdView(String customerInfoId, String adGroupSeq, String keyword, EnumAdType enumAdType, Date startDate, Date endDate) throws Exception;

	public long getPfpAdCount(String customerInfoId, String adGroupSeq, String keyword) throws Exception;

	public long getPfpAdCount(String customerInfoId, String adGroupSeq, String keyword, int page, int pageSize) throws Exception;

	public List<PfpAd> validAdAd(String adGroupSeq) throws Exception;
	
	//取得影音廣告明細
	public List<PfpAdAdVideoViewVO> getAdAdVideoDetailView(PfpAdAdViewConditionVO pfpAdAdViewConditionVO) throws Exception;
	//取得影音廣告總計資訊
	public PfpAdAdVideoViewSumVO getAdAdVideoDetailViewCount(PfpAdAdViewConditionVO pfpAdAdViewConditionVO) throws Exception;
}
