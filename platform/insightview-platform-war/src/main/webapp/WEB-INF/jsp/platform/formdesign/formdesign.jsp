<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta content="text/html; charset=UTF-8" http-equiv="content-type">
  <title>表单设计器</title>
<link href="${pageContext.request.contextPath}/style/formdesign/style.css" type="text/css" rel="stylesheet" title="blue"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/fui/themes/default/fui-tree.min.css"/>
<link href="${pageContext.request.contextPath}/style/formdesign/designer.css" type="text/css" rel="stylesheet"/>
<link href="${pageContext.request.contextPath}/style/formdesign/form_designer.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/fui/fui-all.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/formdesign/base/FormDesigner.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/formdesign/base/FormWidgetFactory.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/formdesign/base/FormWidget.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/formdesign/base/FormDataModel.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/fui/plugin/fui-fileupload.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
<link href="${pageContext.request.contextPath}/plugin/select2/select2.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/select2/select2_locale_zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/formdesign/widget/Input.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/formdesign/widget/Radio.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/formdesign/widget/DefinedRadio.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/formdesign/widget/RichText.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/formdesign/widget/DateTimeBox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/formdesign/widget/TextArea.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/formdesign/widget/FileUpload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/formdesign/widget/DictionaryAttitude.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/formdesign/widget/System_Combotree.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/formdesign/widget/System_Select2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/formdesign/widget/CustomComboBox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/formdesign/widget/CustomCheckBox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/formdesign/widget/CheckBox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/formdesign/widget/InputLinked.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/formdesign/widget/SelectLink.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/formdesign/widget/SelectBothLink.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/formdesign/widget/InputPopUp.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/formdesign/widget/PersonSelect.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bpm/form/prop/Process_Variable.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bpm/form/prop/Process_Title.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>

<script>
$(function(){
	 ISV.pf.fd.base.FormDesigner.design();
});
</script>
</head>

<body id="designer" class="easyui-layout">
	<div region="west" split="true"  iconCls="palette-icon" title="表单设计器" style="width:170px;">
		<div class="easyui-accordion" id="accordion_root" fit="true" border="false">
				<div id="basicControl"  title="基本控件" iconCls="palette-menu-icon" class="palette-menu">
				</div>
				<div id="advancedControl" title="高级控件" iconCls="palette-menu-icon"  class="palette-menu">
				</div>
				<div id="workflowRelevan" title="流程相关" iconCls="palette-menu-icon" class="palette-menu">
				</div>
				<div id="workflowRelevan" title="表单控件" iconCls="palette-menu-icon" class="palette-menu">
				</div>
		</div>
	</div>
	<div id="process-panel" class="easyui-tabs" region="center" split="true"  iconCls="process-icon" title="表单">
				
				<div id="designer-area" title="布局预览" style="POSITION: absolute;width:100%;height:100%;padding: 0;border: none;">
                    <div style="border-bottom:1px solid #8DB2E3; width:100%;height:30px;line-height:30px;">
                      <div align="center">
                      		<input type="hidden" id="businessNodeId" value="${businessNodeId }"/>
                      		<input type="hidden" id="formId" value="${form.id }"/>
                      		<input type="hidden" id="businessId" value="${businessId }"/>
                      		<input type="hidden" id="businessType" value="${businessType }"/>
                      		 <input type="hidden" id="tdCount" value="${form.formLayout}">
                                                                           表单名称：<input id="formName" style="height:18px;width:150px;" value="${form.formName }"/>
                                                                           布局方式：<select style="height:26px;width:150px;" id="formLayout">
                                      <option value="1">一列</option>
                                      <option value="2">两列</option>
                                  </select>
                        </div>      
                    </div>
                    
                    <div class="right" style="position: absolute; width: 100%; height: 100%; top: 30px;" >
                        <table id="layout_tab" style="width:100%;border:1px solid #8DB2E3;-webkit-user-select:none;-ms-user-select:none;-moz-user-select:none;">                           
                            <c:forEach items="${attributes}" varStatus="current" var="attribute">
                            	
                             	<!-- tr 开始-->
                             	<c:if test="${current.index % form.formLayout eq 0 && attributes[current.index-1].columnNum eq 2}">
                                     	<tr>
                               </c:if>
                               <c:if test="${current.index % form.formLayout eq 0 && attributes[current.index-2].columnNum eq 1 && attributes[current.index-1].columnNum eq 1}">
                                     	<tr>
                               </c:if>
                               <c:if test="${attribute.columnNum eq 2}">
                                      <tr>
                               </c:if>
								<!-- 给td加上序号seq -->
								<c:if test="${attribute.columnNum eq 1 }">
								    <td class="drop" align="center" style="height:35px;" seq="${current.index}" columnNum="${attribute.columnNum}">
								</c:if>
								<c:if test="${attribute.columnNum eq 2 }">
								 <td class="drop" align="center" style="height:35px;" seq="${current.index}" colspan="3" columnNum="${attribute.columnNum}">
								</c:if>
								  <span style="display:inline" class="imgSpan">
                                   <div class="div_left"> 
							        	 <input type="hidden" id ="${attribute.id }_hidden" mark="currentId" value="${attribute.id }" />
							        <jsp:include  page="/platform/form/prop/edit/render" flush="true">
							        	<jsp:param value="${attribute.id }" name="id"/>
							        	<jsp:param value="${attribute.editUrl }" name="editView"/>
							        	<jsp:param value="${form.id }" name="formId"/>
							        </jsp:include><img id='${attribute.id }_img_edit_show' class="opImg" style="cursor:pointer;visibility:hidden;" 
							        	 src='${pageContext.request.contextPath}/style/images/icon/icon_modify.png' 
							        	 onclick="ISV.pf.fd.base.FormDesigner.edit_WidgetPage($(this).closest('td'),{
							        		 id: '${attribute.id}',
							        		 widgetType: '${attribute.widgetType}',
							        		 dataType: '${attribute.dataType}',
							        		 datalength: '${attribute.datalength}',
							        		 editUrl: '${attribute.editUrl}',
							        		 viewUrl: '${attribute.viewUrl}'
							        	 });" /><img id='${attribute.id }_img_del_show' class="opImg" style="cursor:pointer;visibility:hidden;" 
							             onclick="ISV.pf.fd.base.FormDesigner.del_WidgetPage(this,{
							            	 id:'${attribute.id}'
							             });"
							             src='${pageContext.request.contextPath}/style/images/icon/icon_delete.png' />
							    </div>
							    </span> 
							    </td>
								<!-- tr 关闭--> 
								<c:if test="${form.formLayout eq 1 }">
								  </tr>
								</c:if>
								<c:if test="${current.index % form.formLayout eq 1 && current.index > 0 && attributes[current.index-1].columnNum eq 1}">
								    </tr>
								</c:if> 
                                <c:if test="${current.index % form.formLayout eq 0}">
                                  <c:choose>
                                    <c:when test="${attribute.columnNum eq 2}">
                                      </tr>
                                    </c:when>
                                    <c:when test="${attributes[current.index-1].columnNum eq 1 && attributes[current.index-2].columnNum eq 2}">
                                      </tr>
                                    </c:when>
                                  </c:choose>
                                </c:if>
                                <c:if test="${attribute.columnNum eq 2}">
                                     </tr>
                               </c:if> 
                             </c:forEach>
                             <tr>
                              <td class="drop" align="center" style="height:35px;">
                              </td>
                             </tr>
                             <tr>
                              <td class="drop" align="center" style="height:35px;"></td>
                             </tr>

                           </table>     
                      </div> 
				</div>	
	</div>
	
	<!-- toolbar -->
	<div id="toolbar-panel" region="north" border="false" style="height:36px;background:#E1F0F2;">
		<div style="background:#E1F0F2;padding:5px;">
			<a href="javascript:void(0)" id="sb1" class="easyui-splitbutton" menu="#edit-menu" iconCls="icon-edit">编辑</a>
			<a href="javascript:void(0)" id="mb3" class="easyui-menubutton" menu="#mm3" iconCls="icon-help">帮助</a>
		</div>
		<div id="edit-menu" style="width:150px;">
			<div iconCls="icon-undo">回退</div>
			<div iconCls="icon-redo">前进</div>
			<div class="menu-sep"></div>
			<div iconCls="icon-save" id="save_button">保存</div>
			<div><a href="#">导出</a></div>
		</div>
		<div id="mm3" style="width:150px;">
			<div>帮助</div>
			<div class="menu-sep"></div>
			<div>关于</div>
		</div>
	</div>
	<div id="propWin"/>
</body>

</html>