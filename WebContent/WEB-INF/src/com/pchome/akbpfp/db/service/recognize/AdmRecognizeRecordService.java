package com.pchome.akbpfp.db.service.recognize;

import java.util.Date;
import java.util.List;

import com.pchome.akbpfp.db.dao.recognize.IAdmRecognizeRecordDAO;
import com.pchome.akbpfp.db.dao.recognize.AdmRecognizeRecordDAO;
import com.pchome.akbpfp.db.pojo.AdmRecognizeRecord;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.service.sequence.SequenceService;
import com.pchome.enumerate.recognize.EnumOrderType;
import com.pchome.enumerate.sequence.EnumSequenceTableName;

public class AdmRecognizeRecordService extends BaseService<AdmRecognizeRecord,Integer> implements IAdmRecognizeRecordService{

	private SequenceService sequenceService;
	private IPfpCustomerInfoService customerInfoService;
	
	public void createRecognizeRecord(String customerInfoId, String orderId, EnumOrderType enumOrderType, float price) throws Exception{
		
		String recordId = sequenceService.getSerialNumber(EnumSequenceTableName.ADM_RECOGNIZE_RECORD);
		PfpCustomerInfo customerInfo = customerInfoService.findCustomerInfo(customerInfoId);
		
		AdmRecognizeRecord recognizeRecord = new AdmRecognizeRecord();
		Date today = new Date();
		recognizeRecord.setRecognizeRecordId(recordId);		
		recognizeRecord.setPfpCustomerInfo(customerInfo);
		recognizeRecord.setRecognizeOrderId(orderId);
		recognizeRecord.setOrderType(enumOrderType.getTypeTag());
		recognizeRecord.setOrderPrice(price);
		recognizeRecord.setOrderRemain(price);
		recognizeRecord.setOrderRemainZero("N");
		recognizeRecord.setSaveDate(today);
		recognizeRecord.setCreateDate(today);
		recognizeRecord.setUpdateDate(today);
		
		((AdmRecognizeRecordDAO)dao).saveOrUpdate(recognizeRecord);
	}

	public List<AdmRecognizeRecord> findRecognizeRecords(String customerInfoId, String recognizeOrderId, EnumOrderType enumOrderType, int orderPrice){
		return ((IAdmRecognizeRecordDAO)dao).findRecognizeRecords(customerInfoId, recognizeOrderId, enumOrderType.getTypeTag(), orderPrice);
	}
	
	public void setSequenceService(SequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public void setCustomerInfoService(IPfpCustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public void insertAdmRecognizeRecord(AdmRecognizeRecord admRecognizeRecord) throws Exception {
		((AdmRecognizeRecordDAO)dao).insertAdmRecognizeRecord(admRecognizeRecord);
	}

	public void updateAdmRecognizeRecord(AdmRecognizeRecord admRecognizeRecord) throws Exception {
		((AdmRecognizeRecordDAO)dao).updateAdmRecognizeRecord(admRecognizeRecord);
	}

	public void saveAdmRecognizeRecord(AdmRecognizeRecord admRecognizeRecord) throws Exception {
		((AdmRecognizeRecordDAO)dao).saveOrUpdateAdmRecognizeRecord(admRecognizeRecord);
	}
}
