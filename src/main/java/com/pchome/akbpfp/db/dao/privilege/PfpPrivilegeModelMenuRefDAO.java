package com.pchome.akbpfp.db.dao.privilege;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpPrivilegeModelMenuRef;
import com.pchome.config.TestConfig;

public class PfpPrivilegeModelMenuRefDAO extends BaseDAO <PfpPrivilegeModelMenuRef,String> implements IPfpPrivilegeModelMenuRefDAO{
	
	@SuppressWarnings("unchecked")
	public List<String> findPfpPrivilegeMenuIdByModelId(String modelId) throws Exception{
		
		List<String> menuIdList = new ArrayList<String>();

		List<PfpPrivilegeModelMenuRef> list = (List<PfpPrivilegeModelMenuRef>) super.getHibernateTemplate().find("from PfpPrivilegeModelMenuRef where id.modelId = ?", modelId);

		if (list!=null) {
			for (int i=0; i<list.size(); i++) {
				menuIdList.add(Integer.toString(list.get(i).getId().getMenuId()));
			}
		}

		return menuIdList;
	}
	
	
	
	
	public static void main(String arg[]) throws Exception{
		ApplicationContext context = new FileSystemXmlApplicationContext(TestConfig.path);
		Logger log = LogManager.getRootLogger();
		PfpPrivilegeModelMenuRefDAO dao = (PfpPrivilegeModelMenuRefDAO) context.getBean("PfpPrivilegeModelMenuRefDAO");
		List<String> list = dao.findPfpPrivilegeMenuIdByModelId("1");
		log.info(" list   "+list);
	}
}
