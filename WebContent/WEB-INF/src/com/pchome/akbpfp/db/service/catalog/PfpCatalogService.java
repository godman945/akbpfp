package com.pchome.akbpfp.db.service.catalog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.pchome.akbpfp.db.dao.catalog.IPfpCatalogDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogVO;
import com.pchome.config.TestConfig;
import com.pchome.enumerate.ad.EnumPfpCatalog;

public class PfpCatalogService extends BaseService<PfpCatalog, String> implements IPfpCatalogService {

	/**
	 * 查詢商品目錄清單
	 * @param PfpCatalogVO
	 * @return
	 */
	@Override
	public List<PfpCatalogVO> getPfpCatalogList(PfpCatalogVO vo) {
		List<Map<String,Object>> pfpCatalogList = ((IPfpCatalogDAO) dao).getPfpCatalogList(vo);
		System.out.println("AAA " + pfpCatalogList);
		
		List<PfpCatalogVO> pfpCatalogVOList = new ArrayList<>();
		for (Map<String, Object> dataMap : pfpCatalogList) {
			PfpCatalogVO pfpCatalogVO = new PfpCatalogVO();
			pfpCatalogVO.setCatalogSeq((String) dataMap.get("catalog_seq"));
			pfpCatalogVO.setCatalogName((String) dataMap.get("catalog_name"));
			
			String catalog_type = (String) dataMap.get("catalog_type");
			if (EnumPfpCatalog.CATALOG_SHOPPING.getType().equals(catalog_type)) {
				pfpCatalogVO.setCatalogType(EnumPfpCatalog.CATALOG_SHOPPING.getTypeName());
			}
			
			String catalog_upload_type = (String) dataMap.get("catalog_upload_type");
			if (EnumPfpCatalog.CATALOG_UPLOAD_FILE_UPLOAD.getType().equals(catalog_upload_type)) {
				pfpCatalogVO.setCatalogUploadType(EnumPfpCatalog.CATALOG_UPLOAD_FILE_UPLOAD.getTypeName());
			} else if (EnumPfpCatalog.CATALOG_UPLOAD_AUTOMATIC_SCHEDULING.getType().equals(catalog_upload_type)) {
				pfpCatalogVO.setCatalogUploadType(EnumPfpCatalog.CATALOG_UPLOAD_AUTOMATIC_SCHEDULING.getTypeName());
			} else if (EnumPfpCatalog.CATALOG_UPLOAD_STORE_URL.getType().equals(catalog_upload_type)) {
				pfpCatalogVO.setCatalogUploadType(EnumPfpCatalog.CATALOG_UPLOAD_STORE_URL.getTypeName());
			} else if (EnumPfpCatalog.CATALOG_UPLOAD_MANUAL_UPLOAD.getType().equals(catalog_upload_type)) {
				pfpCatalogVO.setCatalogUploadType(EnumPfpCatalog.CATALOG_UPLOAD_MANUAL_UPLOAD.getTypeName());
			}
			
			pfpCatalogVO.setUpdateContent((String) dataMap.get("update_content"));
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			pfpCatalogVO.setUpdateDatetime(formatter.format(dataMap.get("update_datetime")));
			pfpCatalogVO.setSuccessNum(String.valueOf(dataMap.get("success_num")));
			pfpCatalogVO.setErrorNum(String.valueOf(dataMap.get("error_num")));
			
			pfpCatalogVOList.add(pfpCatalogVO);
		}
		return pfpCatalogVOList;
	}

	/**
	 * 刪除商品目錄
	 * @param PfpCatalogVO
	 * @return
	 */
	@Override
	public List<PfpCatalogVO> deletePfpCatalog(PfpCatalogVO vo) {
		((IPfpCatalogDAO) dao).deletePfpCatalog(vo);
		return null;
	}
	
	
	/**
	 * 測試用
	 * @param arg
	 * @throws Exception
	 */
	public static void main(String arg[]) throws Exception {

		ApplicationContext context = new FileSystemXmlApplicationContext(TestConfig.path);

		// Logger log = Logger.getLogger(PfpCatalogService.class);

		PfpCatalogService service = (PfpCatalogService) context.getBean("PfpCatalogService");

		PfpCatalogVO vo = new PfpCatalogVO();
//		vo.setQueryString("");
//		vo.setPageNo(1);
//		vo.setPageSize(10);
		vo.setPfpCustomerInfoId("AC2013071700005");
//		service.getPfpCatalogList(vo);
//		int pageCount = vo.getPageCount();
//		int totalCount = vo.getTotalCount();
//		System.out.println(pageCount);
//		System.out.println(totalCount);
		
		vo.setCatalogSeq("PC201808240000000001");
		service.deletePfpCatalog(vo);
	}
}
