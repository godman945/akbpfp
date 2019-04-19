package com.pchome.akbpfp.db.dao.ad;

import java.util.Date;
import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAd;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdInvalid;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.utils.EnumStatus;

public interface IPfpAdInvalidDAO extends IBaseDAO<PfpAdInvalid, Integer> {
    public abstract List<Object[]> selectPfpAdInvalid(String customerInfoId, Date startDate, Date endDate, PfpAdAction pfpAdAction, PfpAdGroup pfpAdGroup, PfpAd pfpAd, EnumStatus enumStatus, EnumAdType enumAdType);
}