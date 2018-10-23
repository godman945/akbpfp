package com.pchome.akbpfp.db.service.catalog.uploadErrLogList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.catalog.uploadErrLogList.IPfpCatalogUploadErrLogDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogUploadLog;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogUploadLogVO;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogVO;

public class PfpCatalogUploadErrLogService extends BaseService<PfpCatalogUploadLog, String> implements IPfpCatalogUploadErrLogService {

	@Override
	public List<PfpCatalogVO> getCatalogProdUploadErrList(PfpCatalogUploadLogVO vo) {
		List<Map<String, Object>> catalogProdUploadErrList = ((IPfpCatalogUploadErrLogDAO) dao).getPfpCatalogList(vo);
		
		List<PfpCatalogVO> pfpCatalogVOList = new ArrayList<>();
		for (Map<String, Object> dataMap : catalogProdUploadErrList) {
			
		}
		return pfpCatalogVOList;
		
//		List<Map<String,Object>> pfpCatalogList = ((IPfpCatalogDAO) dao).getPfpCatalogList(vo);
//		
//		List<PfpCatalogVO> pfpCatalogVOList = new ArrayList<>();
//		for (Map<String, Object> dataMap : pfpCatalogList) {
//			PfpCatalogVO pfpCatalogVO = new PfpCatalogVO();
//			pfpCatalogVO.setCatalogSeq((String) dataMap.get("catalog_seq"));
//			pfpCatalogVO.setCatalogName((String) dataMap.get("catalog_name"));
//			
//			String catalog_type = (String) dataMap.get("catalog_type");
//			pfpCatalogVO.setCatalogType(catalog_type);
//			if (EnumPfpCatalog.CATALOG_SHOPPING.getType().equals(catalog_type)) {
//				pfpCatalogVO.setCatalogTypeName(EnumPfpCatalog.CATALOG_SHOPPING.getTypeName());
//			}
//			
//			if (dataMap.get("catalog_upload_type") != null) { // 上傳方式
//				String catalog_upload_type = (String) dataMap.get("catalog_upload_type");
//				pfpCatalogVO.setCatalogUploadType(catalog_upload_type.trim());
//				if (EnumPfpCatalog.CATALOG_UPLOAD_FILE_UPLOAD.getType().equals(catalog_upload_type)) {
//					pfpCatalogVO.setCatalogUploadTypeName(EnumPfpCatalog.CATALOG_UPLOAD_FILE_UPLOAD.getTypeName());
//				} else if (EnumPfpCatalog.CATALOG_UPLOAD_AUTOMATIC_SCHEDULING.getType().equals(catalog_upload_type)) {
//					pfpCatalogVO.setCatalogUploadTypeName(EnumPfpCatalog.CATALOG_UPLOAD_AUTOMATIC_SCHEDULING.getTypeName());
//				} else if (EnumPfpCatalog.CATALOG_UPLOAD_STORE_URL.getType().equals(catalog_upload_type)) {
//					pfpCatalogVO.setCatalogUploadTypeName(EnumPfpCatalog.CATALOG_UPLOAD_STORE_URL.getTypeName());
//				} else if (EnumPfpCatalog.CATALOG_UPLOAD_MANUAL_UPLOAD.getType().equals(catalog_upload_type)) {
//					pfpCatalogVO.setCatalogUploadTypeName(EnumPfpCatalog.CATALOG_UPLOAD_MANUAL_UPLOAD.getTypeName());
//				}
//				
//				pfpCatalogVO.setNextUpdateDatetime(calculateTheNextUpdateTime(catalog_upload_type));
//			}
//			
//			if (dataMap.get("update_content") != null) { // 資料來源
//				pfpCatalogVO.setUploadContent((String) dataMap.get("update_content"));
//			}
//			
//			if (dataMap.get("update_datetime") != null) { // 最近更新
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mma", Locale.ENGLISH);
//				pfpCatalogVO.setUpdateDatetime(formatter.format(dataMap.get("update_datetime")));
//			}
//			
//			if (dataMap.get("success_num") != null) { // 成功筆數
//				pfpCatalogVO.setSuccessNum(String.valueOf(dataMap.get("success_num")));
//			}
//			
//			if (dataMap.get("error_num") != null) { // 失敗筆數
//				pfpCatalogVO.setErrorNum(String.valueOf(dataMap.get("error_num")));
//			}
//			
//			if (dataMap.get("catalog_upload_log_seq") != null) { // 更新紀錄編號
//				pfpCatalogVO.setCatalogUploadLogSeq(String.valueOf(dataMap.get("catalog_upload_log_seq")));
//			}
//			
//			pfpCatalogVOList.add(pfpCatalogVO);
//		}
//		return pfpCatalogVOList;
	
		
		// TODO Auto-generated method stub
//		return null;
	}

}