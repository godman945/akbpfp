package com.pchome.akbpfp.db.service.freeAction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pchome.akbpfp.db.dao.freeAction.IAdmFreeRecordDAO;
import com.pchome.akbpfp.db.pojo.AdmFreeRecord;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.bill.AdmFreeVO;
import com.pchome.soft.util.DateValueUtil;

public class AdmFreeRecordService extends BaseService<AdmFreeRecord, Integer> implements IAdmFreeRecordService{

	public List<AdmFreeVO> findAccountFree(String customerInfoId, String startDate, String endDate){
		
		Date sDate = DateValueUtil.getInstance().stringToDate(startDate);
		Date eDate = DateValueUtil.getInstance().stringToDate(endDate);
		
		SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
		List<AdmFreeVO> list = new ArrayList<AdmFreeVO>(); 
		
		List<AdmFreeRecord> details = ((IAdmFreeRecordDAO)dao).findAccountFree(customerInfoId, sDate, eDate);
		
		if(!details.isEmpty()){
			for(AdmFreeRecord detail:details){
				AdmFreeVO vo = new AdmFreeVO();
				
				if(detail.getRecordDate() != null){
					vo.setRecordDate(dateFormate.format(detail.getRecordDate()));
				}
				
				if(detail.getAdmFreeAction().getActionStartDate() != null){
					vo.setActionStartDate(dateFormate.format(detail.getAdmFreeAction().getActionStartDate()));
				}
				
				if(detail.getAdmFreeAction().getActionEndDate() != null){
					vo.setActionEndDate(dateFormate.format(detail.getAdmFreeAction().getActionEndDate()));
				}
				
				vo.setActionName(detail.getAdmFreeAction().getActionName());
				vo.setNote(detail.getAdmFreeAction().getNote());
				vo.setGiftMoney(detail.getAdmFreeAction().getGiftMoney());
				
				if(detail.getAdmFreeAction().getInviledDate() != null){
					vo.setInviledDate(dateFormate.format(detail.getAdmFreeAction().getInviledDate()));
				}
				
				list.add(vo);
			}
		}
		
		return list;
	}
	
	public AdmFreeRecord findUserRecord(String actionId, String customerInfoId){
		List<AdmFreeRecord> list = ((IAdmFreeRecordDAO)dao).findUserRecord(actionId, customerInfoId);
		
		if(list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}
}
