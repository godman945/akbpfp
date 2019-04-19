package com.pchome.akbpfp.db.service.recognize;

import java.util.Date;
import java.util.List;

import com.pchome.akbpfp.db.pojo.AdmRecognizeRecord;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.enumerate.recognize.EnumOrderType;

public interface IAdmRecognizeRecordService extends IBaseService <AdmRecognizeRecord,Integer>{

	public void createRecognizeRecord(String customerInfoId, String orderId, EnumOrderType enumOrderType, float price) throws Exception;

	public List<AdmRecognizeRecord> findRecognizeRecords(String customerInfoId, String recognizeOrderId, EnumOrderType enumOrderType, int orderPrice);

	public void insertAdmRecognizeRecord(AdmRecognizeRecord admRecognizeRecord) throws Exception;

	public void updateAdmRecognizeRecord(AdmRecognizeRecord admRecognizeRecord) throws Exception;

	public void saveAdmRecognizeRecord(AdmRecognizeRecord admRecognizeRecord) throws Exception;
}
