package com.fable.insightview.platform.common.dynamicdb.jdbc.work.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fable.insightview.platform.common.dynamicdb.jdbc.sql.SqlMapperUtil;
import com.fable.insightview.platform.common.dynamicdb.jdbc.work.BaseWork;
/**
 * 列表查询总数
 * @author 郑自辉
 *
 */
public class ListTotalWork extends BaseWork {
	
	private static final Logger logger = LogManager.getLogger();
	
	private int total;
	
	private String curTypeId;
	
	private Map<String, String> condition;
	
	public ListTotalWork(WorkType workType,String curTypeId,Map<String, String> condition)
	{
		super(workType);
		this.curTypeId = curTypeId;
		this.condition = condition;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	protected String sql() {
		String totalSql = SqlMapperUtil.getSqlMapper().getListtotal();
		if (StringUtils.isEmpty(curTypeId) || "null".equals(curTypeId))
		{
			totalSql = totalSql.replace("#{curTypeId}","");
		}
		else
		{
			totalSql = totalSql.replace("#{curTypeId}"," and rc.CiTypeid in (" + curTypeId + ") ");
		}
		//拼装Condition
		StringBuilder conditions = new StringBuilder();
		if (null != condition && !condition.isEmpty())
		{
			Set<Entry<String, String>> entrySet = condition.entrySet();
			for (Iterator<Entry<String, String>> it = entrySet.iterator();it.hasNext();)
			{
				Entry<String, String> param = it.next();
				String key = param.getKey();
				/**
				 * 当前登录用户ID只用作查询资源的关注，并不属于资源数据本身的查询条件
				 * 所以此处要过滤掉
				 */
				if("bizSqlCondition".equals(key)) {
					conditions.append(" ").append(param.getValue()).append(" ");
				}
				else if("excludeSqlCondition".equals(key)) {
					conditions.append(" ").append(param.getValue()).append(" ");
				}
				else if ("userId".equals(key)) {
					continue;
				} 
				else if ("name".equals(key)) {
					conditions.append(" and rc.name like '%").append(param.getValue()).append("%' ");
				} 
				else if ("applicationSystemUserId".equals(key))
				{
					conditions.append(" and rc.userId = ").append(param.getValue());
				}else if ("applicationSystemMaintainerId".equals(key))
				{
					conditions.append(" and rc.maintainerId = ").append(param.getValue());
				} else
				{
					conditions.append("and rc.")
					            .append(param.getKey())
					            .append("=")
					            .append(param.getValue());
				}
			}
		}
		totalSql = totalSql.replace("#{condition}", conditions.toString());
		return totalSql;
	}

	@Override
	protected void processData(ResultSet rs) {
		try {
			while (rs.next())
			{
				total = rs.getInt("total");
			}
		} catch (SQLException e) {
			logger.error("分页查询列表总条数失败，配置项类型：{}",curTypeId);
		}
	}
}
