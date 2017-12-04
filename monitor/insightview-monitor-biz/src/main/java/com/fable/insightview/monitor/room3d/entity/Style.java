package com.fable.insightview.monitor.room3d.entity;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author Administrator
 * 3D ROOM 样式模型
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class Style {
	
	/**
	 * string类型，决定shape的形状
	 */
	private String shape3d;
	
	/**
	 * 3d图形整体贴图
	 */
	@JsonProperty("shape3d.image")
	private String shape3dImage;
	
	/**
	 * 决定3d图形整体贴图的uv偏移
	 */
	@JsonProperty("shape3d.uv.offset")
	private List<Float> shape3dUvOffset;
	
	@JsonProperty("front.uv")
	private List<Integer> frontUv;
	
	/**
	 * 决定3d图形的反面是否显示，隐藏背面可提高性能
	 */
	@JsonProperty("shape3d.reverse.cull")
	private String shape3dReverseCull;
	
	/**
	 * 3d图形整体颜色
	 */
	@JsonProperty("shape3d.color")
	private String shape3dColor;
	
	/**
	 * 3d图形顶面颜色
	 */
	@JsonProperty("shape3d.top.color")
	private String shape3dTopColor;
	
	/**
	 * 决定3d图形顶面贴图的uv缩放，格式为[3, 2]
	 */
	@JsonProperty("shape3d.top.uv.scale")
	private List<Float> shape3dTopUvScale;
	
	/**
	 * 六面贴图的uv缩放
	 */
	@JsonProperty("all.uv.scale")
	private List<Float> allUvScale;
	
	/**
	 * 顶面贴图的uv缩放
	 */
	@JsonProperty("top.uv.scale")
	private List<Float> topUvScale;
	
	/**
	 * 六面体颜色
	 */
	@JsonProperty("all.color")
	private String allColor;
	
	/**
	 * 六面贴图
	 */
	@JsonProperty("all.image")
	private String allImage;
	
	/**
	 * 前面贴图
	 */
	@JsonProperty("front.image")
	private String frontImage;
	
	/**
	 * 左面贴图
	 */
	@JsonProperty("left.image")
	private String leftImage;
	
	/**
	 * 右面贴图
	 */
	@JsonProperty("right.image")
	private String rightImage;
	
	/**
	 * 后面贴图
	 */
	@JsonProperty("back.image")
	private String backImage;
	
	/**
	 * 六面是否全部显示
	 */
	@JsonProperty("all.visible")
	private Boolean allVisible;
	
	
	@JsonProperty("back.visible")
	private Boolean backVisible;
	
	/**
	 * 前面是否显示
	 */
	@JsonProperty("front.visible")
	private Boolean frontVisible;
	
	/**
	 * 六面的反面是否显示正面的内容
	 */
	@JsonProperty("all.reverse.flip")
	private Boolean allReverseFlip;
	
	/**
	 * 六面是否透明
	 */
	@JsonProperty("all.transparent")
	private Boolean allTransparent;
	
	/**
	 * 六面的背面是否可见
	 */
	@JsonProperty("all.reverse.cull")
	private Boolean allReverseCull;
	
	/**
	 * 图元染色颜色，HT会自动在原有图元图片颜色上叠加染色效果
	 */
	@JsonProperty("body.color")
	private String bodyColor;
	
	/**
	 * 告警图标
	 */
	private Icons icons;
	
	@JsonProperty("shape.border.width")
	private Integer shapeBorderWidth;
	
	@JsonProperty("shape.border.cap")
	private String shapeBorderCap;
	
	@JsonProperty("shape.border.color")
	private String shapeBorderColor;
	
	@JsonProperty("border.color")
	private String borderColor;
	
	@JsonProperty("border.width")
	private Integer borderWidth;
	
	private Boolean flow;
	
	@JsonProperty("attach.operation")
	private String attachOperation;
	
	
	@JsonProperty("all.reverse.color")
	private String allReverseColor;
	

	public List<Integer> getFrontUv() {
		return frontUv;
	}

	public void setFrontUv(List<Integer> frontUv) {
		this.frontUv = frontUv;
	}

	public Boolean getBackVisible() {
		return backVisible;
	}

	public void setBackVisible(Boolean backVisible) {
		this.backVisible = backVisible;
	}

	public String getAttachOperation() {
		return attachOperation;
	}

	public void setAttachOperation(String attachOperation) {
		this.attachOperation = attachOperation;
	}

	public String getAllReverseColor() {
		return allReverseColor;
	}

	public void setAllReverseColor(String allReverseColor) {
		this.allReverseColor = allReverseColor;
	}

	public Integer getShapeBorderWidth() {
		return shapeBorderWidth;
	}

	public void setShapeBorderWidth(Integer shapeBorderWidth) {
		this.shapeBorderWidth = shapeBorderWidth;
	}

	public String getShapeBorderCap() {
		return shapeBorderCap;
	}

	public void setShapeBorderCap(String shapeBorderCap) {
		this.shapeBorderCap = shapeBorderCap;
	}

	public String getShapeBorderColor() {
		return shapeBorderColor;
	}

	public void setShapeBorderColor(String shapeBorderColor) {
		this.shapeBorderColor = shapeBorderColor;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	public Integer getBorderWidth() {
		return borderWidth;
	}

	public void setBorderWidth(Integer borderWidth) {
		this.borderWidth = borderWidth;
	}

	public Boolean getFlow() {
		return flow;
	}

	public void setFlow(Boolean flow) {
		this.flow = flow;
	}

	public String getShape3d() {
		return shape3d;
	}

	public void setShape3d(String shape3d) {
		this.shape3d = shape3d;
	}

	public String getShape3dImage() {
		return shape3dImage;
	}

	public void setShape3dImage(String shape3dImage) {
		this.shape3dImage = shape3dImage;
	}

	public List<Float> getShape3dUvOffset() {
		return shape3dUvOffset;
	}

	public void setShape3dUvOffset(List<Float> shape3dUvOffset) {
		this.shape3dUvOffset = shape3dUvOffset;
	}

	public String getShape3dReverseCull() {
		return shape3dReverseCull;
	}

	public void setShape3dReverseCull(String shape3dReverseCull) {
		this.shape3dReverseCull = shape3dReverseCull;
	}

	public String getShape3dColor() {
		return shape3dColor;
	}

	public void setShape3dColor(String shape3dColor) {
		this.shape3dColor = shape3dColor;
	}

	public String getShape3dTopColor() {
		return shape3dTopColor;
	}

	public void setShape3dTopColor(String shape3dTopColor) {
		this.shape3dTopColor = shape3dTopColor;
	}

	public List<Float> getShape3dTopUvScale() {
		return shape3dTopUvScale;
	}

	public void setShape3dTopUvScale(List<Float> shape3dTopUvScale) {
		this.shape3dTopUvScale = shape3dTopUvScale;
	}

	public List<Float> getAllUvScale() {
		return allUvScale;
	}

	public void setAllUvScale(List<Float> allUvScale) {
		this.allUvScale = allUvScale;
	}

	public List<Float> getTopUvScale() {
		return topUvScale;
	}

	public void setTopUvScale(List<Float> topUvScale) {
		this.topUvScale = topUvScale;
	}

	public String getAllColor() {
		return allColor;
	}

	public void setAllColor(String allColor) {
		this.allColor = allColor;
	}

	public String getAllImage() {
		return allImage;
	}

	public void setAllImage(String allImage) {
		this.allImage = allImage;
	}

	public String getFrontImage() {
		return frontImage;
	}

	public void setFrontImage(String frontImage) {
		this.frontImage = frontImage;
	}

	public String getLeftImage() {
		return leftImage;
	}

	public void setLeftImage(String leftImage) {
		this.leftImage = leftImage;
	}

	public String getRightImage() {
		return rightImage;
	}

	public void setRightImage(String rightImage) {
		this.rightImage = rightImage;
	}

	public String getBackImage() {
		return backImage;
	}

	public void setBackImage(String backImage) {
		this.backImage = backImage;
	}

	public Boolean getAllVisible() {
		return allVisible;
	}

	public void setAllVisible(Boolean allVisible) {
		this.allVisible = allVisible;
	}

	public Boolean getFrontVisible() {
		return frontVisible;
	}

	public void setFrontVisible(Boolean frontVisible) {
		this.frontVisible = frontVisible;
	}

	public Boolean getAllReverseFlip() {
		return allReverseFlip;
	}

	public void setAllReverseFlip(Boolean allReverseFlip) {
		this.allReverseFlip = allReverseFlip;
	}

	public Boolean getAllTransparent() {
		return allTransparent;
	}

	public void setAllTransparent(Boolean allTransparent) {
		this.allTransparent = allTransparent;
	}

	public Boolean getAllReverseCull() {
		return allReverseCull;
	}

	public void setAllReverseCull(Boolean allReverseCull) {
		this.allReverseCull = allReverseCull;
	}

	public String getBodyColor() {
		return bodyColor;
	}

	public void setBodyColor(String bodyColor) {
		this.bodyColor = bodyColor;
	}

	public Icons getIcons() {
		return icons;
	}

	public void setIcons(Icons icons) {
		this.icons = icons;
	}

	
	
	

}
