package com.pchome.akbpfp.db.service.template;

import java.util.List;

import com.pchome.akbpfp.db.dao.template.AdmTemplateProductDAO;
import com.pchome.akbpfp.db.pojo.AdmTemplateProduct;
import com.pchome.akbpfp.db.service.BaseService;

public class TemplateProductService extends BaseService<AdmTemplateProduct, String> implements ITemplateProductService {


	public List<AdmTemplateProduct> getTemplateProductByXType(List<String> condition) throws Exception {
		return ((AdmTemplateProductDAO)dao).getTemplateProductByXType(condition);
	}


}
