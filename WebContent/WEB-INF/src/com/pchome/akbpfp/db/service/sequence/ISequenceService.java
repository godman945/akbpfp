package com.pchome.akbpfp.db.service.sequence;

import com.pchome.akbpfp.db.pojo.Sequence;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.enumerate.sequence.EnumSequenceTableName;

public interface ISequenceService extends IBaseService<Sequence,String>{
	
	// 取流水編號
	public String getSerialNumber(EnumSequenceTableName enumSequenceTableName) throws Exception;
	
	// 取流水編號20碼
	public String getSerialNumberByLength20(EnumSequenceTableName enumSequenceTableName) throws Exception;

	//取得流水編號 part 2
	public String getId(EnumSequenceTableName enumSequenceTableName,String mid) throws Exception;

}
