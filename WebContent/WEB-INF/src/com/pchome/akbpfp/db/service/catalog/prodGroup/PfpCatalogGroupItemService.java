package com.pchome.akbpfp.db.service.catalog.prodGroup;


import java.util.List;

import com.pchome.akbpfp.db.dao.catalog.prodGroup.IPfpCatalogGroupItemDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroup;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;
import com.pchome.akbpfp.db.service.BaseService;


public class PfpCatalogGroupItemService extends BaseService<PfpCatalogGroupItem,Integer> implements IPfpCatalogGroupItemService{
//	private PfpCatalogGroupDAO pfpCatalogGroupDAO;
	
	
//	private IAccesslogProvider accesslogProvider;
//	private PfpUserMemberRefDAO pfpUserMemberRefDAO;
	
	
	public String getCatalogGroupFilterSQL(String groupId) throws Exception{
		List<PfpCatalogGroupItem> pfpCatalogGroupItems = ((IPfpCatalogGroupItemDAO)dao).getCatalogGroupFilterSQL(groupId);
		
		StringBuffer filterSQL = new StringBuffer();
		
		if( (!pfpCatalogGroupItems.isEmpty()) && (pfpCatalogGroupItems.size()>0) ){
			for (PfpCatalogGroupItem pfpCatalogGroupItem : pfpCatalogGroupItems) {
				filterSQL.append(" and ");
				filterSQL.append(pfpCatalogGroupItem.getCatalogGroupItemField());
				filterSQL.append(" ");
				filterSQL.append(pfpCatalogGroupItem.getCatalogGroupItemCondition());
				filterSQL.append(" ");
				filterSQL.append("'"+pfpCatalogGroupItem.getCatalogGroupItemValue()+"'");
			}
		}
		
		return filterSQL.toString();
	}
	
	





}
