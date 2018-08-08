package com.pchome.akbpfp.db.service.sequence;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.pchome.akbpfp.db.dao.sequence.ISequenceDAO;
import com.pchome.akbpfp.db.pojo.Sequence;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.config.TestConfig;
import com.pchome.enumerate.sequence.EnumSequenceTableName;

public class SequenceService extends BaseService<Sequence,String> implements ISequenceService{

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private int orderDayNum;


	public void setOrderDayNum(int orderDayNum) {
		this.orderDayNum = orderDayNum;
	}

	/**
     * 流水號長度共有15碼
     */
	@Override
    public String getSerialNumber(EnumSequenceTableName enumSequenceTableName) throws Exception{

		int limit = 15;
		Sequence sequence = this.getSequence(enumSequenceTableName);

		StringBuffer id = new StringBuffer();
		id.append(sequence.getTableChar())
			.append(sequence.getTableDate());
		String num = this.getZeroSeq(String.valueOf(sequence.getTableNo()), (limit - id.length()));
		id.append(num);

		return id.toString();
	}

	private Sequence getSequence(EnumSequenceTableName enumSequenceTableName) throws Exception{

		Sequence sequence = ((ISequenceDAO) dao).get(enumSequenceTableName.getSnoName());
		Date date = new Date();
		String today = sdf.format(date);

		if(sequence == null){
			// 無資料
			sequence = new Sequence();
			sequence.setTableName(enumSequenceTableName.getSnoName());
			sequence.setTableChar(enumSequenceTableName.getCharName().toUpperCase());
			sequence.setTableDate(today);
			sequence.setTableNo(1);
			sequence.setCreateDate(date);

		}else{
			if(today.equals(sequence.getTableDate())){
				// 數字累計上去
				int num = sequence.getTableNo();
				sequence.setTableNo(++num);
			}else{
				// 數字重新計算
				sequence.setTableDate(today);
				sequence.setTableNo(1);
			}

		}

		sequence.setUpdateDate(date);
		((ISequenceDAO) dao).saveOrUpdate(sequence);

		return sequence;
	}

	private String getZeroSeq(String str, int limit) throws Exception{

        StringBuffer sb = new StringBuffer();

        for (int i = 0, length = limit - str.length(); i < length; i++) {
            sb.append("0");
        }
        sb.append(str);

        return sb.toString();
	}

	private String getIDForTable(EnumSequenceTableName enumSequenceTableName, String mid) throws Exception{
		Sequence sequence = getSequence(enumSequenceTableName);

		int limit=0;
		String tableDate="";
		String checkCode="";
		StringBuffer tableNo = new StringBuffer();
		int no = sequence.getTableNo();

		limit = orderDayNum;
		tableDate=sequence.getTableDate();
		checkCode="";

		for (int i = 0, length = limit - String.valueOf(no).length(); i < length; i++) {
			tableNo.append("0");
		}

		tableNo.append(no);

		StringBuffer id = new StringBuffer();


		id.append(sequence.getTableChar())
		.append(mid)
		.append(tableDate)
		.append(tableNo)
		.append(checkCode);

		return id.toString();
	}

	@Override
    @Transactional(isolation=Isolation.SERIALIZABLE)
	public synchronized String getId(EnumSequenceTableName enumSequenceTableName,String mid) throws Exception {
//		String id = null;
//		while(StringUtils.isBlank(id)) {
//		    try {
//		        id = this.getIDForTable(enumSequenceTableName, mid);
//		    }
//		    catch (Exception e) {
//		        log.error(enumSequenceTableName + " " + mid);
//		        Thread.sleep(100);
//		    }
//		}
//
//		return id;

	    return this.getIDForTable(enumSequenceTableName, mid);
	}

	/**
	 * 可輸入欄位總長度，補滿需要的位數
	 * ex:totalLength=20 結果:PCUL2018080800000001
	 * @param enumSequenceTableName
	 * @param mid 中間要輸入甚麼參數
	 * @param totalLength 總長度
	 * @return 
	 * @throws Exception
	 */
	@Override
	public String getId(EnumSequenceTableName enumSequenceTableName, String mid, int totalLength) throws Exception {
		Sequence sequence = getSequence(enumSequenceTableName);
		String tableDate = sequence.getTableDate();
		int no = sequence.getTableNo();
		
		// 輸入總欄位數 - 序號前面的代碼長度 - 底線等等的長度 - 日期長度 - DB內目前序號長度
		totalLength = totalLength - sequence.getTableChar().length() - mid.length() - tableDate.length() - String.valueOf(no).length();

		StringBuffer tableNo = new StringBuffer();
		for (int i = 0; i < totalLength; i++) {
			tableNo.append("0");
		}
		tableNo.append(no);

		StringBuffer id = new StringBuffer();
		id.append(sequence.getTableChar())
		  .append(mid)
		  .append(tableDate)
		  .append(tableNo);
		
		return id.toString();
	}
	
	public static void main(String arg[]) throws Exception{
		ApplicationContext context = new FileSystemXmlApplicationContext(TestConfig.path);

		Logger log = Logger.getLogger(SequenceService.class);

		SequenceService service = (SequenceService) context.getBean("SequenceService");

		log.info(" id -->  "+service.getSerialNumber(EnumSequenceTableName.ORDER));
	}
}
