package com.pchome.akbpfp.db.dao.ad;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAd;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdInvalid;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.utils.EnumStatus;

public class PfpAdInvalidDAO extends BaseDAO<PfpAdInvalid, Integer> implements IPfpAdInvalidDAO {
    @SuppressWarnings("unchecked")
    public List<Object[]> selectPfpAdInvalid(String customerInfoId, Date startDate, Date endDate, PfpAdAction pfpAdAction, PfpAdGroup pfpAdGroup, PfpAd pfpAd, EnumStatus enumStatus, EnumAdType enumAdType) {
        StringBuffer hql = new StringBuffer();
        hql.append("select adSeq, ");
        hql.append("    sum(adInvalidClk), ");
        hql.append("    sum(adInvalidClkPrice) ");
        hql.append("from pfpAdInvalid ");
        hql.append("where customerInfoId = :customerInfoId ");
        hql.append("    and adInvalidDate >= :startDate ");
        hql.append("    and adInvalidDate <= :endDate ");
        if (pfpAdAction != null) {
            hql.append("    and adActionSeq = :adActionSeq ");
        }
        if (pfpAdGroup != null) {
            hql.append("    and adGroupSeq = :adGroupSeq ");
        }
        if (pfpAd != null) {
            hql.append("    and adSeq = :adSeq ");
        }
        if (enumStatus != null) {
            hql.append("    and pfpAd.pfpAdGroup.pfpAdAction.adActionStatus = :adActionStatus ");
            hql.append("    and pfpAd.pfpAdGroup.adGroupStatus = :adGroupStatus ");
            hql.append("    and pfpAd.adStatus = :adStatus ");
        }
        if (enumAdType != null) {
            hql.append("    and adType = :adType ");
        }
        hql.append("group by pfpAd.adSeq ");
        hql.append("order by adInvalidDate ");

        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql.toString());
        query.setString("customerInfoId", customerInfoId);
        query.setDate("startDate", startDate);
        query.setDate("endDate", endDate);
        if (pfpAdAction != null) {
            query.setString("adActionSeq", pfpAdAction.getAdActionSeq());
        }
        if (pfpAdGroup != null) {
            query.setString("adGroupSeq", pfpAdGroup.getAdGroupSeq());
        }
        if (pfpAd != null) {
            query.setString("adSeq", pfpAd.getAdSeq());
        }
        if (enumStatus != null) {
            query.setInteger("adActionStatus", enumStatus.getStatusId());
            query.setInteger("adGroupStatus", enumStatus.getStatusId());
            query.setInteger("adStatus", enumStatus.getStatusId());
        }
        if (enumAdType != null) {
            query.setInteger("adType", enumAdType.getType());
        }

        return query.list();
    }
}