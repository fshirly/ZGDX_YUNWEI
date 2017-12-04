package com.fable.insightview.platform.common.dynamicdb.api;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.common.dynamicdb.Column;

/**
 * 动态表数据操作接口
 * 提供数据的查询、修改、新增、删除功能
 * 数据模型采用Map结构，key为字段名称，value为字段值
 * @author 郑自辉
 *
 */
public interface IDynamicDBQuery {

	/**
	 * 新增数据
	 * @param tableName 表名，为数组结构，包含所有父表
	 * @param data 行数据
	 * @return 主键ID
	 */
	String save(Map<String, Object> data,String... tableName);
	
	/**
	 * 删除数据
	 * @param tableName 表名，为数组结构，包含所有父表
	 * @param id 主键ID
	 */
	void delete(String id,String... tableName);
	
	/**
	 * 更新数据
	 * @param tableName 表名，为数组结构，包含所有父表
	 * @param data 新数据
	 */
	void update(Map<String, Object> data,String... tableName);
	
	/**
	 * 通过sequence生成主键
	 * @return
	 */
	Integer queryPrimaryIdWork(String tableName);
	
	/**
	 * 查询某条数据
	 * @param tableName 表名，为数组结构，包含所有父表
	 * @param id ID
	 * @return 该行数据
	 */
	Map<String, String> query(String id,String... tableName);
	
	/**
	 * 批量获取对应的组件信息
	 * @param compIds 组件ID，List结构
	 * @param tableName 该组件信息保存的数据库表
	 * @return List<Map<String, String>> 组件数据，List<Map>结构
	 */
	List<Map<String, String>> listComp(List<String> compIds,String tableName);
	
	/**
	 * 绑定数据库初始化属性值
	 * 用于属性初始化值的获取--绑定数据库方式
	 * @param tableName 绑定数据库表名
	 * @param labelColumn 文本显示字段
	 * @param valueColumn 值字段
	 * @return List<AttrInitData> 属性初始化值
	 */
	List<AttrInitData> initByDB(String tableName,String labelColumn,String valueColumn);
	
	/**
	 * 查询所有数据
	 * @param tableName 表名，为数组结构，包含所有父表
	 * @param start 开始记录
	 * @param pageSize 每页显示记录
	 * @param typeId 配置项类型ID
	 * @return Map结构，total:总数，data:列表数据，为List<Map>结构，每行对应一个Map,key为字段，value为其值
	 */
	Map<String, Object> list(String typeId,int start,int pageSize,String... tableName);
	
	/**
	 * 根据条件查询数据
	 * @param tableName 表名
	 * @param condition 查询条件，key为查询字段，value为查询关键字
	 * @param start 开始记录
	 * @param pageSize 每页显示记录
	 * @param typeId 配置项类型ID
	 * @return Map结构，total:总数，data:列表数据，为List<Map>结构，每行对应一个Map,key为字段，value为其值
	 */
	Map<String, Object> list(String typeId,Map<String, String> condition,int start,int pageSize,String... tableName);
	
	/**
	 * 查询表的所有字段
	 * @param tableName 表名
	 * @return List<Column> 字段集合
	 */
	List<Column> listColumn(String... tableName);
}
