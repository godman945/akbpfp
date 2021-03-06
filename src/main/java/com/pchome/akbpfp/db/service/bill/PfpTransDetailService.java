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
			float totalRefundMoney = 0;
			float remain = 0;
			boolean tag = true;
			BigDecimal totalAdSpentMoneyBigDecimal = new BigDecimal(0);
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
//					totalTaxMoney += detail.getTax();
				}
				
				if(detail.getTransType().equals(EnumTransType.INVALID_COST.getTypeId())){
					vo.setReturnMoney(detail.getTransPrice());
					totalReturnMoney += detail.getTransPrice();
				}
				
				if(detail.getTransType().equals(EnumTransType.SPEND_COST.getTypeId())){
					totalAdSpentMoneyBigDecimal = totalAdSpentMoneyBigDecimal.add(new BigDecimal(String.valueOf(detail.getTransPrice())));
//					vo.setAdSpentMoney(detail.getTransPrice());
					vo.setAdSpentMoney((new BigDecimal(String.valueOf(detail.getTransPrice()))).floatValue());
					totalAdSpentMoney += detail.getTransPrice();
				}
				
				//預付、後付退款
				if(detail.getTransType().equals(EnumTransType.REFUND.getTypeId()) ||	detail.getTransType().equals(EnumTransType.LATER_REFUND.getTypeId())){
					vo.setRefundMoney(detail.getTransPrice() + Math.round(detail.getTax()));
					totalRefundMoney += (detail.getTransPrice() + Math.round(detail.getTax()));
				}
				
				vo.setRemain(detail.getRemain());				
				vos.add(vo);
			}
			billVOs.setBillVOs(vos);
			billVOs.setTotalSaveMoney(totalSaveMoney);
			billVOs.setTotalTaxMoney(totalTaxMoney);
			billVOs.setTotalReturnMoney(totalReturnMoney);
			billVOs.setTotalAdSpentMoney(totalAdSpentMoneyBigDecimal.floatValue());
			billVOs.setRemain(remain);
			billVOs.setTotalRefundMoney(totalRefundMoney);
		}
		
		return billVOs;
	}
	
}
