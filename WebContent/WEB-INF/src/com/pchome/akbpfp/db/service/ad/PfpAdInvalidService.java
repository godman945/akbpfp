package com.pchome.akbpfp.db.service.ad;

import java.util.Date;
import java.util.List;

import com.pchome.akbpfp.db.dao.ad.IPfpAdInvalidDAO;
import com.pchome.akbpfp.db.pojo.PfpAd;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdInvalid;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.utils.EnumStatus;

public class PfpAdInvalidService extends BaseService<PfpAdInvalid, Integer> implements IPfpAdInvalidService {
    public List<Object[]> selectPfpAdInvalid(String customerInfoId, Date startDate, Date endDate, PfpAdAction pfpAdAction, PfpAdGroup pfpAdGroup, PfpAd pfpAd, EnumStatus enumStatus, EnumAdType enumAdType) {
        return ((IPfpAdInvalidDAO) dao).selectPfpAdInvalid(customerInfoId, startDate, endDate, pfpAdAction, pfpAdGroup, pfpAd, enumStatus, enumAdType);
    }
}