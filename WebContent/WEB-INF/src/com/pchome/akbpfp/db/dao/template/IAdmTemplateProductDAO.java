package com.pchome.akbpfp.db.dao.template;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.AdmTemplateProduct;

public interface IAdmTemplateProductDAO extends IBaseDAO<AdmTemplateProduct, String> {

	
	public List<AdmTemplateProduct> getTemplateProductByXType(List<String> condition) throws Exception;
}
