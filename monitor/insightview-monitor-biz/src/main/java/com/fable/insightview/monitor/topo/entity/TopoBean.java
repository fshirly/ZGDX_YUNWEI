package com.fable.insightview.monitor.topo.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 拓扑
 * 
 */
@Alias("topo")
public class TopoBean {
	@NumberGenerator(name = "monitorTopoPK")
	private Integer id;
	private String topoName;
	private Integer topoLevel;
	private Integer topoType;
	private String jsonFilePath;
	private String description;
	private Integer alarmLevel;
	private Integer totalNodeNum;

	private String topoLevelName;
	private String levelIcon;
	private String alarmLevelName;
	private String ids;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTopoName() {
		return topoName;
	}

	public void setTopoName(String topoName) {
		this.topoName = topoName;
	}

	public Integer getTopoLevel() {
		return topoLevel;
	}

	public void setTopoLevel(Integer topoLevel) {
		this.topoLevel = topoLevel;
	}
	
	public Integer getTopoType() {
		return topoType;
	}

	public void setTopoType(Integer topoType) {
		this.topoType = topoType;
	}

	public String getJsonFilePath() {
		return jsonFilePath;
	}

	public void setJsonFilePath(String jsonFilePath) {
		this.jsonFilePath = jsonFilePath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTopoLevelName() {
		return topoLevelName;
	}

	public void setTopoLevelName(String topoLevelName) {
		this.topoLevelName = topoLevelName;
	}

	public Integer getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(Integer alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public Integer getTotalNodeNum() {
		return totalNodeNum;
	}

	public void setTotalNodeNum(Integer totalNodeNum) {
		this.totalNodeNum = totalNodeNum;
	}

	public String getLevelIcon() {
		return levelIcon;
	}

	public void setLevelIcon(String levelIcon) {
		this.levelIcon = levelIcon;
	}

	public String getAlarmLevelName() {
		return alarmLevelName;
	}

	public void setAlarmLevelName(String alarmLevelName) {
		this.alarmLevelName = alarmLevelName;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}
