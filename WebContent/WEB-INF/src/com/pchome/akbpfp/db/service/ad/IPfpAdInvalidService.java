package com.pchome.akbpfp.db.service.ad;

import java.util.Date;
import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpAd;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdInvalid;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.utils.EnumStatus;

public interface IPfpAdInvalidService extends IBaseService<PfpAdInvalid, Integer> {
    public abstract List<Object[]> selectPfpAdInvalid(String customerInfoId, Date startDate, Date endDate, PfpAdAction pfpAdAction, PfpAdGroup pfpAdGroup, PfpAd pfpAd, EnumStatus enumStatus, EnumAdType enumAdType);
}
