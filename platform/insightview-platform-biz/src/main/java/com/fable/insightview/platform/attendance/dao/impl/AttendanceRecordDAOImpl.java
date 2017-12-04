package com.fable.insightview.platform.attendance.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.TimestampType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.attendance.dao.AttendanceRecordDAO;
import com.fable.insightview.platform.attendance.entity.AttRecInfoStatisVO;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.page.Page;

@Repository("attendanceRecordDAO")
public class AttendanceRecordDAOImpl extends GenericDaoHibernate implements
		AttendanceRecordDAO {
	private final Logger logger = LoggerFactory.getLogger(AttendanceRecordDAOImpl.class);
	
	@SuppressWarnings("unchecked")
	public List<AttRecInfoStatisVO> qryAttRecInfoStatisVOs(
			Page<AttRecInfoStatisVO> page, Boolean isPage) {
		Integer attPlanId = (Integer) page.getParams().get("attPlanId");
		Date chkInStartTime = (Date) page.getParams().get("checkInStartTime");
		Date chkInEndTime = (Date) page.getParams().get("checkInEndTime");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strStartTime = sdf.format(chkInStartTime);
		String strEndTime = sdf.format(chkInEndTime);
				
		Long userId = page.getParams().get("userId")==null ? null : Long.valueOf(page.getParams().get("userId").toString());
		String hasCheckedIn = String.valueOf(page.getParams().get("hasUncheckedIn"));
		StringBuffer sql = new StringBuffer();
		List<AttRecInfoStatisVO> list = null;
		sql.append("select a.AttDate,a.AttTime,perConf.StartTime,perConf.EndTime,a.UserId,");
		sql.append("group_concat(ifnull(a.AttTime,'未签到') order by a.AttPeriodId separator ';') attTimes,group_concat(concat(perConf.StartTime,'~',perConf.EndTime) order by a.AttPeriodId separator ';') signTimes from ");
		sql.append("( ").append("select pepConf.UserId,planConf.AttendanceId attPlanId,planConf.AttendanceId, planConf.AttStartTime,planConf.AttEndTime,");
		sql.append("recInfo.AttTime,recInfo.AttPeopleId,recInfo.AttRecordId,recInfo.AttPeriodId,recInfo.AttSignDate AttDate ");
		sql.append("from AttendanceRecordInfo recInfo ");
		sql.append("inner join (AttendancePeopleConf pepConf inner join AttendancePlanConf planConf on (pepConf.AttendanceId = planConf.AttendanceId) ");
		sql.append(" ) on (recInfo.AttPeopleId= pepConf.Id) where 1=1 ");
		sql.append("and pepConf.AttendanceId = ").append(attPlanId);
		sql.append(" ) a inner join AttendancePeriodConf perConf on (a.AttendanceId = perConf.AttendanceId and a.AttPeriodId = perConf.AttPeriodId ) ");
		sql.append(" where 1=1 and perConf.AttendanceId = ").append(attPlanId);
		sql.append(" and date_format(a.AttDate,'%Y-%m-%d')>=date_format('").append(strStartTime).append("','%Y-%m-%d') ");
		sql.append(" and date_format(a.AttDate,'%Y-%m-%d')<=date_format('").append(strEndTime).append("','%Y-%m-%d') ");
		sql.append(" and date_format(a.AttDate,'%Y-%m-%d')<=date_format(sysdate(),'%Y-%m-%d') ");
		if(null != userId && -1 != userId) {
			sql.append(" and a.UserId = ").append(userId);
		}
		if(StringUtils.isNotEmpty(hasCheckedIn) && "1".equals(hasCheckedIn)) {
			sql.append(" and a.AttTime is null ");
		}
		sql.append(" group by a.UserId asc,a.AttDate asc order by a.UserId asc,a.AttDate asc,a.AttPeriodId asc ");
		
		SQLQuery sqlQuery = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		sqlQuery.addScalar("AttTime", TimestampType.INSTANCE).addScalar("StartTime").addScalar("EndTime")
		.addScalar("AttDate", DateType.INSTANCE).addScalar("UserId", IntegerType.INSTANCE)
		.addScalar("attTimes").addScalar("signTimes");
//		sqlQuery.setParameter("chkInStartTime", chkInStartTime);
//		sqlQuery.setParameter("chkInEndTime", chkInEndTime);
		isPage = isPage == null ? true : isPage;
		if(isPage) {
			sqlQuery.setFirstResult((int) ((page.getPageNo() - 1) * page.getPageSize()));
			sqlQuery.setMaxResults(page.getPageSize());
		}
		list = sqlQuery.setResultTransformer(Transformers.aliasToBean(AttRecInfoStatisVO.class)).list();
		if(isPage) {
			this.setTotalCount(sql.toString(), page);
		}
		
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	private int setTotalCount(String sql, Page page) {
		StringBuffer countSql = new StringBuffer();
		countSql.append("select count(1) from (").append(sql).append(") a ");
		SQLQuery sqlQuery = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(countSql.toString());
		int count = ((Number)sqlQuery.uniqueResult()).intValue();
		page.setTotalRecord(count);
		return count;
	}
	

}
