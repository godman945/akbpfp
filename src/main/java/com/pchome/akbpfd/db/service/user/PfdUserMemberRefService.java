package com.pchome.akbpfd.db.service.user;

import com.pchome.akbpfd.db.dao.user.IPfdUserMemberRefDAO;
import com.pchome.akbpfp.db.pojo.PfdUserMemberRef;
import com.pchome.akbpfp.db.service.BaseService;

public class PfdUserMemberRefService extends BaseService<PfdUserMemberRef,String> implements IPfdUserMemberRefService{
	public PfdUserMemberRef getUserMemberRef(String memberId) {
	    return ((IPfdUserMemberRefDAO)dao).getUserMemberRef(memberId);
	}
}
