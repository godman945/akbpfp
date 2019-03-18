package com.pchome.akbpfp.db.service.template;

import java.util.List;

import com.pchome.akbpfp.db.pojo.AdmTemplateProduct;
import com.pchome.akbpfp.db.service.IBaseService;

public interface ITemplateProductService extends IBaseService<AdmTemplateProduct, String>{
    	
	public List<AdmTemplateProduct> getTemplateProductByXType(List<String> condition) throws Exception;
}
