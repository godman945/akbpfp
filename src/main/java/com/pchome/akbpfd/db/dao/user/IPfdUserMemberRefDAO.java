package com.pchome.akbpfd.db.dao.user;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfdUserMemberRef;

public interface IPfdUserMemberRefDAO extends IBaseDAO<PfdUserMemberRef,String>{
	
    public PfdUserMemberRef getUserMemberRef(String memberId);
}
