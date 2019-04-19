package com.pchome.akbpfp.db.service.advideo;

import com.pchome.akbpfp.db.dao.advideo.PfpAdVideoSourceDAO;
import com.pchome.akbpfp.db.pojo.PfpAdVideoSource;
import com.pchome.akbpfp.db.service.BaseService;

public class PfpAdVideoSourceService extends BaseService<PfpAdVideoSource,String> implements IPfpAdVideoSourceService{

    public PfpAdVideoSource getVideoUrl(String videoUrl) throws Exception {
		return ((PfpAdVideoSourceDAO)dao).getVideoUrl(videoUrl);
	}
}
