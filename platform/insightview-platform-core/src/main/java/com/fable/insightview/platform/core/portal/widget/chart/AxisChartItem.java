package com.fable.insightview.platform.core.portal.widget.chart;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

import com.fable.insightview.platform.core.portal.TemplateFactory;

public  class AxisChartItem extends ChartItem {

	private ChartMark[] chartMarkPoint;

	private ChartMark[] chartMarkLine;

	@Override
	public StringBuilder draw() {
		StringBuilder sb = new StringBuilder();
		
		if (chartMarkPoint != null && chartMarkPoint.length > 0
				&& chartMarkLine != null && chartMarkLine.length > 0) {
			
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("ChartMarks-markPoint", getMarkPointDesc());
			valueMap.put("ChartMarks-markLine", getMarkLineDesc());

			StrSubstitutor sub = new StrSubstitutor(valueMap, "$[", "]");
			sb.append(sub.replace(TemplateFactory.getTemplate("AxisChartItem")));
		}
		return sb;
	}

	private String getMarkPointDesc() {
		StringBuilder sb = new StringBuilder("[");
		if (chartMarkPoint != null) {
			for (ChartMark m : chartMarkPoint) {
				sb.append(m.draw());
				sb.append(",");
			}
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		return sb.toString();
	}

	private String getMarkLineDesc() {
		StringBuilder sb = new StringBuilder("[");
		if (chartMarkLine != null) {
			for (ChartMark m : chartMarkLine) {
				sb.append(m.draw());
				sb.append(",");
			}
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		return sb.toString();
	}
	
	public ChartMark[] getChartMarkPoint() {
		return chartMarkPoint;
	}

	public void setChartMarkPoint(ChartMark[] chartMarkPoint) {
		this.chartMarkPoint = chartMarkPoint;
	}

	public ChartMark[] getChartMarkLine() {
		return chartMarkLine;
	}

	public void setChartMarkLine(ChartMark[] chartMarkLine) {
		this.chartMarkLine = chartMarkLine;
	}
	
}
