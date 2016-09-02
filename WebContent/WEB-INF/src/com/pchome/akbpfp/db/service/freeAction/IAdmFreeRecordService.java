package com.pchome.akbpfp.db.service.freeAction;

import java.util.List;

import com.pchome.akbpfp.db.pojo.AdmFreeRecord;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.akbpfp.db.vo.bill.AdmFreeVO;

public interface IAdmFreeRecordService extends IBaseService<AdmFreeRecord, Integer> {

	public List<AdmFreeVO> findAccountFree(String customerInfoId, String startDate, String endDate);
	
	public AdmFreeRecord findUserRecord(String actionId, String customerInfoId);
}
