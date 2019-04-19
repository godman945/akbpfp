package com.pchome.akbpfd.db.service.user;

import com.pchome.akbpfp.db.pojo.PfdUserMemberRef;
import com.pchome.akbpfp.db.pojo.PfpUserMemberRef;
import com.pchome.akbpfp.db.service.IBaseService;

public interface IPfdUserMemberRefService extends IBaseService<PfdUserMemberRef,String>{
	
    public PfdUserMemberRef getUserMemberRef(String memberId);
	
}
