package com.fable.insightview.platform.common.dynamicdb.jdbc.work.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fable.insightview.platform.common.dynamicdb.Column;
import com.fable.insightview.platform.common.dynamicdb.jdbc.sql.SqlMapperUtil;
/**
 * 列表查询工作，包含搜索
 * @author 郑自辉
 *
 */
public class ListWork extends BaseQueryWork {
	
	private static final Logger logger = LogManager.getLogger();
	
	/**
	 * 查询出的列表数据
	 */
	private List<Map<String, String>> listData = new ArrayList<Map<String, String>>();
	
	/**
	 * 要查询的配置项类型ID
	 */
	private String curTypeId;
	
	/**
	 * 查询条件
	 */
	private Map<String, String> condition;
	
	/**
	 * 开始条数
	 */
	private int start;
	
	/**
	 * 每页显示条数
	 */
	private int pageSize;
	
	public ListWork(WorkType workType,String[] tableName,String curTypeId,
			List<Column> columns,Map<String, String> condition,int start,int pageSize)
	{
		super(workType,tableName,columns);
		this.curTypeId = curTypeId;
		this.condition = condition;
		this.start = start;
		this.pageSize = pageSize;
	}

	public List<Map<String, String>> getListData() {
		return listData;
	}

	@Override
	protected String sql() {
		String listSql = selectSql();
		//拼装Select
		StringBuilder selectColumns = new StringBuilder();
		if (null != columns && !columns.isEmpty())
		{
			for (int i = 0,size = columns.size();i < size;i++)
			{
				Column column = columns.get(i);
				if (null == column)
				{
					continue;
				}
				selectColumns.append(column.getTableName()).append(".").append(column.getColumnName());
				if (i != size -1)
				{
					selectColumns.append(",");
				}
			}
		}
		if (StringUtils.isEmpty(selectColumns.toString()))
		{
			selectColumns.append("ResCi.CiId,ResCi.Name,ResCi.status,ResCi.AssetId,ResCi.CiTypeid,ResTypeDefine.ResTypeName ");
		}
		else
		{
			selectColumns.append(",")
						 .append("ResCi.CiId,ResCi.Name,ResCi.status,ResCi.AssetId,ResCi.CiTypeid,ResTypeDefine.ResTypeName ");
		}
		listSql = listSql.replace("#{select}", selectColumns.toString());
		
		//拼装left join
		StringBuilder join = new StringBuilder();
		if (null != tableName && tableName.length > 0)
		{
			for (String t : tableName)
			{
				if (StringUtils.isEmpty(t) || "ResCi".equals(t))
				{
					continue;
				}
				join.append("LEFT JOIN")
					.append(" ")
					.append(t)
					.append(" ")
					.append(t)
					.append(" on ")
					.append("ResCi.CiId=")
					.append(t)
					.append(".CiId ");
			}
		}
		listSql = listSql.replace("#{left-join}", join.toString());
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
				} else if ("name".equals(key))
				{
					conditions.append(" and rc.name like '%").append(param.getValue()).append("%' ");
				} else if ("applicationSystemUserId".equals(key))
				{
					conditions.append(" and rc.userId = ").append(param.getValue());
				}else if ("applicationSystemMaintainerId".equals(key))
				{
					conditions.append(" and rc.maintainerId = ").append(param.getValue());
				}
				else {
					conditions.append("and rc.")
					            .append(param.getKey())
					            .append("=")
					            .append(param.getValue());
				}
			}
		}
		listSql = listSql.replace("#{condition}", conditions.toString());
		listSql = listSql.replace("#{start}", String.valueOf(start))
							.replace("#{pageSize}", String.valueOf(pageSize));
		if (StringUtils.isNotEmpty(curTypeId) && !"null".equals(curTypeId))
		{
			listSql = listSql.replace("#{curTypeId}"," and rc.CiTypeid in (" + curTypeId + ") ");
		}
		else
		{
			listSql = listSql.replace("#{curTypeId}","");
		}
		//拼接用户ID
		listSql = listSql.replace("#{userId}", condition.get("userId"));
		logger.info("分页查询配置项类型：{}，最终组装的SQL语句：{}",curTypeId,listSql);
		return listSql;
	}

	@Override
	protected String selectSql() {
		return SqlMapperUtil.getSqlMapper().getList();
	}

	@Override
	protected String genWhere(String tableName) {
		return tableName + ".CiId ";
	}

	@Override
	protected void processData(ResultSet rs) {
		try {
			while(rs.next())
			{
				Map<String, String> data = new HashMap<String, String>();
				for (Column c : columns)
				{
					data.put(c.getColumnName(), StringUtils.isEmpty(rs.getString(c.getColumnName())) ? "" : rs.getString(c.getColumnName()));
				}
				//配置项列表那边已经使用了ciID作为主键字段的名称，所以这边也设置下
				data.put("ciID", StringUtils.isEmpty(rs.getString("CiId")) ? "" : rs.getString("CiId"));
				data.put("Name", StringUtils.isEmpty(rs.getString("Name")) ? "" : rs.getString("Name"));
				data.put("status", StringUtils.isEmpty(rs.getString("status")) ? "" : rs.getString("status"));
				data.put("assetId", rs.getString("AssetId"));
				data.put("ciTypeId", rs.getString("CiTypeid"));
				data.put("resTypeName", rs.getString("ResTypeName"));
				data.put("attention_count", rs.getString("attention_count"));
				data.put("user_name", rs.getString("user_name"));
				data.put("maintainer_name", rs.getString("maintainer_name"));
				data.put("department_name", rs.getString("department_name"));
				data.put("projectName", rs.getString("projectName"));
				listData.add(data);
			}
		} catch (SQLException e) {
			logger.error("查询结果转换失败,表名：{}",(Object[])tableName);
		}
	}
}
