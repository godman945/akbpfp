package com.pchome.akbpfp.db.dao.privilege;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpPrivilegeModelMenuRef;

public interface IPfpPrivilegeModelMenuRefDAO extends IBaseDAO <PfpPrivilegeModelMenuRef,String> {
	
	public List<String> findPfpPrivilegeMenuIdByModelId(String modelId) throws Exception;
}
