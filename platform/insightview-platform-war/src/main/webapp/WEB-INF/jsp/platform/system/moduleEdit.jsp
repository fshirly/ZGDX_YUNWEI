<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta charset="UTF-8">
    <title>模块编辑页面</title>
    <script type="text/javascript" charset="utf-8" src="../../../js/platform/system/head.js"></script>
  </head>
  <body>
    <div style="padding:10px 10px 10px 10px;">
      <form id="fm">
        <table class="formtable1">
          <input id="id" name="id" type="hidden">
          <tr>
            <td class="title">模块名称：</td>
            <td>
              <input id="name" name="name" type="text" class="easyui-validatebox" data-options="required:true"><dfn>*</dfn>
            </td>
          </tr>
          <tr>
            <td class="title">模块类型：</td>
            <td>
              <select id="type" name="type" class="easyui-combobox easyui-validatebox" data-options="required:true">
                <option value="1" selected="selected">模块</option>
                <option value="2">功能按钮</option>
              </select><dfn>*</dfn>
            </td>
          </tr>
          <tr>
            <td class="title">父模块：</td>
            <td>
              <select id="pid" name="pid" class="easyui-combotree easyui-validatebox" data-options="required:true" readonly="true"></select><dfn>*</dfn>
            </td>
          </tr>
          <tr>
            <td valign="top" class="title">描述：</td>
            <td>
              <textarea id="note" name="note" rows="4" class="easyui-validatebox"></textarea>
            </td>
          </tr>
        </table>
      </form>
    </div>
    <div class="dialog-button">
      <a href="javascript:void(0)" onclick="save()">确定</a>
      <a href="javascript:void(0)" onclick="closeModalWindow()">取消</a>
    </div>
  </body>
</html>
<script type="text/javascript">
  var rootName = getRootName();
  
  //表单数据
  var formData = null;
  
  /**
   * 页面初始化
   */
  $(document).ready(function() {
	formData = getJsonObj("row");
	getModule();
  });
  
  /**
   * 获取模块信息
   */
  function getModule() {
    $.ajax({
      type: "GET",
      url: rootName + "/sys/module",
      dataType: 'json',
      success: function(result) {
    	//父模块pid为-1时显示'无'的处理
      	result.data.push({
          "id": "-1",
          "name": "无",
          "pid": "-2"
        });
  	    var fileds = "id,name";
        var nodes = ConvertToTreeJson(result.data, "id", "pid", "name", fileds);
        $("#pid").combotree({ data: nodes, onSelect: function(node){}});
        $("#fm").form('load', formData);
      },
      error: function(result) {
        show_error(result);
      }
    });
  }
  
  /**
   * 保存模块
   */
  function save() {
    var jsonObj = $('#fm').serializeObject();
    $.ajax({
      type: jsonObj.id ? "PUT": "POST",
      url: rootName + "/sys/module",
      data: JSON.stringify(jsonObj),
      dataType: 'json',
      contentType: "application/json",
      beforeSend: function() {
        return $("#fm").form('validate');
      },
      success: function(result) {
    	if(result.success) {
    	  window.top.$.messager.alert("提示信息", jsonObj.id ? "修改成功！" : "添加成功！", "info", function() {
    		closeModalWindow();
            if(!jsonObj.id) {
              $('#id').val(result.message);
            }
            var pWin = getMainWindow();
            pWin.renderTreeGrid($('#fm').serializeObject());
          });
          $(".panel-tool-close").css("display","none"); //去掉关闭按钮
        }
      },
      error: function(result) {
        show_error(result);
      }
    });
  }
</script>