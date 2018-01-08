package com.pchome.akbpfp.db.service.bill;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pchome.akbpfp.db.dao.bill.IPfpTransDetailDAO;
import com.pchome.akbpfp.db.pojo.PfpTransDetail;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.bill.BillVO;
import com.pchome.akbpfp.db.vo.bill.BillVOList;
import com.pchome.enumerate.trans.EnumTransType;
import com.pchome.soft.util.DateValueUtil;

public class PfpTransDetailService extends BaseService <PfpTransDetail, String> implements IPfpTransDetailService{
	
	public List<PfpTransDetail> findAccountTransDetails(String customerInfoId, Date startDate, Date endDate) throws Exception{
		return ((IPfpTransDetailDAO) dao).findAccountTransDetails(customerInfoId, startDate, endDate);
	}
	
	
	public BillVOList findPfpTransDetail(String customerInfoId, String startDate, String endDate) {
		
		Date sDate = DateValueUtil.getInstance().stringToDate(startDate);
		Date eDate = DateValueUtil.getInstance().stringToDate(endDate);
		
		BillVOList billVOs = null;
		
		List<PfpTransDetail> details = ((IPfpTransDetailDAO) dao).findAccountTransDetails(customerInfoId, sDate, eDate);
		
		if(!details.isEmpty()){
			
			billVOs = new BillVOList();
			List<BillVO> vos = new ArrayList<BillVO>();
			float totalSaveMoney = 0;
			float totalTaxMoney = 0;
			float totalReturnMoney = 0;				
			float totalAdSpentMoney = 0;
			float remain = 0;
			boolean tag = true;
			
			for(PfpTransDetail detail:details){
				
				if(tag){
					remain = detail.getRemain();
					tag = false;
				}
				
				BillVO vo = new BillVO();
				
				vo.setTransDate(DateValueUtil.getInstance().dateToString(detail.getTransDate()));
				vo.setTransContents(detail.getTransContent());
				
				if(detail.getTransType().equals(EnumTransType.ADD_MONEY.getTypeId()) ||
						detail.getTransType().equals(EnumTransType.GIFT.getTypeId()) ||
						detail.getTransType().equals(EnumTransType.FEEDBACK_MONEY.getTypeId()) ||
						detail.getTransType().equals(EnumTransType.LATER_SAVE.getTypeId())){
					vo.setSaveMoney(detail.getTransPrice()+detail.getTax());
					vo.setTaxMoney(detail.getTax());
					totalSaveMoney += detail.getTransPrice()+detail.getTax();
					totalTaxMoney += detail.getTax();
				}
				
				if(detail.getTransType().equals(EnumTransType.INVALID_COST.getTypeId())){
					vo.setReturnMoney(detail.getTransPrice());
					totalReturnMoney += detail.getTransPrice();
				}
				
				if(detail.getTransType().equals(EnumTransType.SPEND_COST.getTypeId()) ||detail.getTransType().equals(EnumTransType.REFUND.getTypeId()) ||	detail.getTransType().equals(EnumTransType.LATER_REFUND.getTypeId())){
					vo.setAdSpentMoney(detail.getTransPrice());
					totalAdSpentMoney += detail.getTransPrice();
				}
				
				vo.setRemain(detail.getRemain());				
				
				vos.add(vo);
			}
			
			billVOs.setBillVOs(vos);
			billVOs.setTotalSaveMoney(totalSaveMoney);
			billVOs.setTotalTaxMoney(totalTaxMoney);
			billVOs.setTotalReturnMoney(totalReturnMoney);
			billVOs.setTotalAdSpentMoney(totalAdSpentMoney);
			billVOs.setRemain(remain);
		}
		
		return billVOs;
	}
	
}
