package com.pchome.akbpfp.db.service.catalog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pchome.akbpfp.db.dao.catalog.IPfpCatalogDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogVO;
import com.pchome.enumerate.ad.EnumPfpCatalog;
import com.pchome.enumerate.sequence.EnumSequenceTableName;

public class PfpCatalogService extends BaseService<PfpCatalog,String> implements IPfpCatalogService{
	
	private ISequenceService sequenceService;
	
	public String getCatalogType(String catalogSeq) throws Exception{
		List<PfpCatalog> pfpCatalog = ((IPfpCatalogDAO)dao).getCatalogType(catalogSeq);
		
		String catalogType = "";
		if( (!pfpCatalog.isEmpty()) && (pfpCatalog.size()>0) ){
			catalogType = pfpCatalog.get(0).getCatalogType();
		}
		return catalogType;
	}
	
	public PfpCatalog getPfpCatalog(String catalogSeq) throws Exception{
		return ((IPfpCatalogDAO)dao).getPfpCatalog(catalogSeq).get(0);
	}
	
	public List<PfpCatalog> getPfpCatalogList(String pfpCustomerInfoId) throws Exception{
		return ((IPfpCatalogDAO)dao).getPfpCatalogList(pfpCustomerInfoId);
	}

	public List<PfpCatalog> getPfpCatalogByCustomerInfoId(String customerInfoId) throws Exception {
		List<PfpCatalog> data = ((IPfpCatalogDAO)dao).getPfpCatalogByCustomerInfoId(customerInfoId);
		return data;
	}
	
	public List<PfpCatalog> checkPfpCatalogPrivilege(String customerInfoId,String catalogSeq) throws Exception{
		return ((IPfpCatalogDAO)dao).checkPfpCatalogPrivilege(customerInfoId,catalogSeq);
	}
	
	
	/**
	 * 查詢商品目錄清單
	 * @param PfpCatalogVO
	 * @return
	 */
	@Override
	public List<PfpCatalogVO> getPfpCatalogList(PfpCatalogVO vo) {
		List<Map<String,Object>> pfpCatalogList = ((IPfpCatalogDAO) dao).getPfpCatalogList(vo);
		
		List<PfpCatalogVO> pfpCatalogVOList = new ArrayList<>();
		for (Map<String, Object> dataMap : pfpCatalogList) {
			PfpCatalogVO pfpCatalogVO = new PfpCatalogVO();
			pfpCatalogVO.setCatalogSeq((String) dataMap.get("catalog_seq"));
			pfpCatalogVO.setCatalogName((String) dataMap.get("catalog_name"));
			
			String catalog_type = (String) dataMap.get("catalog_type");
			pfpCatalogVO.setCatalogType(catalog_type);
			if (EnumPfpCatalog.CATALOG_SHOPPING.getType().equals(catalog_type)) {
				pfpCatalogVO.setCatalogTypeName(EnumPfpCatalog.CATALOG_SHOPPING.getTypeName());
			}
			
			if (dataMap.get("catalog_upload_type") != null) { // 上傳方式
				String catalog_upload_type = (String) dataMap.get("catalog_upload_type");
				pfpCatalogVO.setCatalogUploadType(catalog_upload_type.trim());
				if (EnumPfpCatalog.CATALOG_UPLOAD_FILE_UPLOAD.getType().equals(catalog_upload_type)) {
					pfpCatalogVO.setCatalogUploadTypeName(EnumPfpCatalog.CATALOG_UPLOAD_FILE_UPLOAD.getTypeName());
				} else if (EnumPfpCatalog.CATALOG_UPLOAD_AUTOMATIC_SCHEDULING.getType().equals(catalog_upload_type)) {
					pfpCatalogVO.setCatalogUploadTypeName(EnumPfpCatalog.CATALOG_UPLOAD_AUTOMATIC_SCHEDULING.getTypeName());
				} else if (EnumPfpCatalog.CATALOG_UPLOAD_STORE_URL.getType().equals(catalog_upload_type)) {
					pfpCatalogVO.setCatalogUploadTypeName(EnumPfpCatalog.CATALOG_UPLOAD_STORE_URL.getTypeName());
				} else if (EnumPfpCatalog.CATALOG_UPLOAD_MANUAL_UPLOAD.getType().equals(catalog_upload_type)) {
					pfpCatalogVO.setCatalogUploadTypeName(EnumPfpCatalog.CATALOG_UPLOAD_MANUAL_UPLOAD.getTypeName());
				}
				
				pfpCatalogVO.setNextUpdateDatetime(calculateTheNextUpdateTime(catalog_upload_type));
			}
			
			if (dataMap.get("update_content") != null) { // 資料來源
				pfpCatalogVO.setUploadContent((String) dataMap.get("update_content"));
			}
			
			if (dataMap.get("update_datetime") != null) { // 最近更新
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mma", Locale.ENGLISH);
				pfpCatalogVO.setUpdateDatetime(formatter.format(dataMap.get("update_datetime")));
			}
			
			if (dataMap.get("catalog_prod_num") != null) { // 所有商品數量
				pfpCatalogVO.setCatalogProdNum((int)dataMap.get("catalog_prod_num"));
			}
			
			if (dataMap.get("error_num") != null) { // 失敗筆數
				pfpCatalogVO.setErrorNum(String.valueOf(dataMap.get("error_num")));
			}
			
			if (dataMap.get("catalog_upload_log_seq") != null) { // 更新紀錄編號
				pfpCatalogVO.setCatalogUploadLogSeq(String.valueOf(dataMap.get("catalog_upload_log_seq")));
			}
			
			if (dataMap.get("upload_status") != null) { // 資料上傳狀態(0:未上傳 (預設值), 1:上傳中, 2:上傳完成)
				pfpCatalogVO.setUploadStatus((String)dataMap.get("upload_status"));
			}
			
			pfpCatalogVOList.add(pfpCatalogVO);
		}
		return pfpCatalogVOList;
	}

	/**
	 * 新增商品目錄
	 * @throws Exception 
	 */
	@Override
	public void savePfpCatalog(PfpCatalogVO vo) throws Exception {
		// 新增 pfp_catalog "商品目錄"
		vo.setCatalogSeq(sequenceService.getId(EnumSequenceTableName.PFP_CATALOG, "", 20));
		((IPfpCatalogDAO) dao).savePfpCatalog(vo);
	}
	
	/**
	 * 查詢目前目錄資料上傳狀態
	 * @param uploadingCatalogSeqList
	 * @return
	 */
	@Override
	public List<Map<String, String>> getCatalogUploadingStatus(String uploadingCatalogSeqList) {
		List<Map<String, String>> resultData = new ArrayList<Map<String, String>>();
		try {
			JSONArray jsonArray = new JSONArray(uploadingCatalogSeqList);

			// 將jsonArray轉ArrayList，hibernate查詢用
			List<String> catalogSeqList = new ArrayList<String>();
			for (int i = 0; i < jsonArray.length(); i++) {
				catalogSeqList.add(jsonArray.getString(i));
			}
			
			List<Map<String, Object>> list = ((IPfpCatalogDAO) dao).getCatalogUploadingStatus(catalogSeqList);
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> objArray = list.get(i);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("catalogSeq", objArray.get("catalog_seq").toString());
				map.put("uploadStatus", objArray.get("upload_status").toString());
				resultData.add(map);
			}
			
			return resultData;
		} catch (Exception e) {
			return resultData;
		}
	}
	
	/**
	 * 刪除商品目錄
	 */
	@Override
	public void deletePfpCatalog(PfpCatalogVO vo) {
		((IPfpCatalogDAO) dao).deletePfpCatalog(vo);
	}
	
	/**
	 * 更新商品目錄
	 */
	@Override
	public void updatePfpCatalog(PfpCatalogVO vo) {
		((IPfpCatalogDAO) dao).updatePfpCatalog(vo);
	}
	
	/**
	 * 更新目錄資料，一般購物類使用
	 * @param pfpCatalogVO
	 */
	@Override
	public void updatePfpCatalogForShoppingProd(PfpCatalogVO vo) {
		((IPfpCatalogDAO) dao).updatePfpCatalogForShoppingProd(vo);
	}
	
	/**
	 * 計算下次更新時間
	 * 當天凌晨1、2點查看，則顯示當日，超過凌晨1、2點則顯示明天
	 * @param catalog_upload_type
	 * @return e.x "2018/09/14 01:00AM"
	 */
	private String calculateTheNextUpdateTime(String catalog_upload_type) {
		
		// 是自動排程、pchome賣場網址顯示下次更新時間
		if (EnumPfpCatalog.CATALOG_UPLOAD_AUTOMATIC_SCHEDULING.getType().equals(catalog_upload_type) 
				|| EnumPfpCatalog.CATALOG_UPLOAD_STORE_URL.getType().equals(catalog_upload_type)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			Calendar now = Calendar.getInstance();
			int hour = now.get(Calendar.HOUR_OF_DAY);
			String nextUpdateHour = "";
			
			if (EnumPfpCatalog.CATALOG_UPLOAD_AUTOMATIC_SCHEDULING.getType().equals(catalog_upload_type)) {
				nextUpdateHour = " 01:00AM";
			} else if (EnumPfpCatalog.CATALOG_UPLOAD_STORE_URL.getType().equals(catalog_upload_type)) {
				nextUpdateHour = " 02:00AM";
			}
			
			if ((hour >= 1 && EnumPfpCatalog.CATALOG_UPLOAD_AUTOMATIC_SCHEDULING.getType().equals(catalog_upload_type))
					|| (hour >= 2 && EnumPfpCatalog.CATALOG_UPLOAD_STORE_URL.getType().equals(catalog_upload_type))) {
				now.add(Calendar.DAY_OF_MONTH, +1);
			}
			
			Date date = now.getTime();
			return formatter.format(date) + nextUpdateHour;
		} else {
			return "";
		}
	}
	
	/**
	 * 測試用
	 * @param arg
	 * @throws Exception
	 */
	public static void main(String arg[]) throws Exception {
		
//		ApplicationContext context = new FileSystemXmlApplicationContext(TestConfig.path);
//
//		// Logger log = Logger.getLogger(PfpCatalogService.class);
//
//		PfpCatalogService service = (PfpCatalogService) context.getBean("PfpCatalogService");
//
//		PfpCatalogVO vo = new PfpCatalogVO();
////		vo.setQueryString("");
////		vo.setPageNo(1);
////		vo.setPageSize(10);
//		vo.setPfpCustomerInfoId("AC2013071700005");
////		service.getPfpCatalogList(vo);
////		int pageCount = vo.getPageCount();
////		int totalCount = vo.getTotalCount();
////		System.out.println(pageCount);
////		System.out.println(totalCount);
//		
////		vo.setCatalogSeq("PC201808240000000001");
//		
//		vo.setCatalogName("0827商品目錄");
//		vo.setCatalogType("1");
//		service.savePfpCatalog(vo);
	}

	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

}