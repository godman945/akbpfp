package com.pchome.akbpfp.db.dao.recognize;

import java.util.Date;
import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.AdmRecognizeRecord;

public interface IAdmRecognizeRecordDAO extends IBaseDAO<AdmRecognizeRecord,Integer>{

	public List<AdmRecognizeRecord> findRecognizeRecords(String customerInfoId,	String recognizeOrderId, String orderType, int orderPrice);

	public void saveOrUpdateAdmRecognizeRecord(AdmRecognizeRecord admRecognizeRecord) throws Exception;

	public void insertAdmRecognizeRecord(AdmRecognizeRecord admRecognizeRecord) throws Exception;

	public void updateAdmRecognizeRecord(AdmRecognizeRecord admRecognizeRecord) throws Exception;

}
