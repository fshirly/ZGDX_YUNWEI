<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>模块关联Rest接口</title>
    <script type="text/javascript" charset="utf-8" src="../../../js/platform/system/head.js"></script>
  </head>
  <body class="easyui-layout" data-options="fit:true">
    <div region="center" style="overflow:auto">
      <table id="treegrid" toolbar="#toolbar"></table>
    </div>
    <div region="south">
      <div class="dialog-button2">
        <a href="javascript:void(0)" onclick="apply()">确定</a>
        <a href="javascript:void(0)" onclick="closeModalWindow()">取消</a>
      </div>
    </div>
    <div id="toolbar">
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"
        plain="true" onclick="$('#treegrid').treegrid('expandAll')">全部展开</a>
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove"
        plain="true" onclick="$('#treegrid').treegrid('collapseAll');">全部折叠</a>
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"
        plain="true" onclick="selectAll(1);">全选</a>
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove"
        plain="true" onclick="selectAll(0);">清除</a>
    </div>
    <div id="mm" class="easyui-menu" style="width:120px;">
      <div onclick="selectSub(1)">全选</div>
      <div onclick="selectSub(0)">取消</div>
    </div>
  </body>
</html>
<script type="text/javascript">
  var rootName = getRootName();
  var moduleId = getParam("id");
  
  /**
   * 页面初始化
   */
  $(document).ready(function() {
	initTreeGridData();
	query();
  });
  
  /**
   * 查询rest
   */
  function query() {
    $.ajax({
      type: "GET",
      url: rootName + "/sys/restSecurity/getRestData",
      dataType: 'json',
      success: function(result) {
        var fileds = "id,pid,name,aliasName,url,type,note";
        var nodes = ConvertToTreeGridJson(result.data, "id", "pid", fileds);
        $('#treegrid').treegrid('loadData', nodes);
        
        $.ajax({
          type: "GET",
          url: rootName + "/sys/restSecurity/queryRestByModuleId/" + moduleId,
          dataType: 'json',
          success: function(data) {
            $(data).each(function(i, obj) {
              //$("#treegrid").treegrid('select', obj);
              if($("#" + hex_md5(obj)).length == 1) {
            	$("#" + hex_md5(obj)).get(0).checked = true;
              }
            });
          },
          error: function(data) {
        	show_error(data);
          }
        });
      },
      error: function(result) {
        show_error(result);
      }
    });
  }
  
  /**
   * 初始化rest列表数据
   */
  function initTreeGridData() {
	$("#treegrid").treegrid({
      //title: "Rest接口",
      width: '100%',
      height: '100%',
      animate: true,
      //singleSelect: false,
      //checkbox: true,
      rownumbers: true,
      autoRowHeight: true,
      nowrap: true,
      idField: 'id',
      treeField: 'aliasName',
      columns:[[
        {field:'id',title:'id',width:100,hidden:true },
        {field:'pid',title:'pid',width:100,hidden:true },
        {field:'aliasName',title:'名称',width:280,formatter:function(value, rowData, rowIndex){
          if(rowData.type == 3) {
        	return "<input type='checkbox' id='" + hex_md5(rowData.id) + "' value='" + rowData.id + "'>&nbsp;" + value;
          } else {
        	return value;
          }
        }},
        {field:'type',title:'接口类型',align:'center',width:85,formatter:function(value,row,index){ return value == "1" ? "包" : value == "2" ? "类" : "方法" }},
        {field:'url',title:'请求URL',width:295},
        {field:'note',title:'说明',width:175,formatter:function(value, rowData, rowIndex){
          if(rowData.type == 2) {
          	return value.fontcolor("blue");
          } else {
        	return value;
          }
        }}
      ]],
      onContextMenu: function(e, node) {
        e.preventDefault();
        $('#treegrid').treegrid('select', node.id);
        if(node.type == 2) {
          $('#mm').menu('show', {
            left: e.pageX,
            top: e.pageY
          });
        }
      },
      onClickRow: function(row) {
        if(row.type == 3) {
          $("#" + hex_md5(row.id)).get(0).checked = !$("#" + hex_md5(row.id)).get(0).checked ? true : false;
        }
      },
      onDblClickRow: function(row) {
        //
      },
    });
  }
  
  /**
   * 部分全选/清除
   */
  function selectSub(checked) {
	var nodes = $('#treegrid').treegrid('getSelections');
	if(nodes.length != 1) return;
	
	var lst = nodes[0].children;
	for(var i = 0; i < lst.length; i++) {
	  $("#" + hex_md5(lst[i].id)).get(0).checked = checked || checked == "1" ? true : false;
	}
  }
  
  /**
   * 全选/清除
   */
  function selectAll(checked) {
	debugger;
	var nodes = $('#treegrid').treegrid('getChildren');
	for(var i = 0; i < nodes.length; i++) {
	  if(nodes[i].type == 3) {
		$("#" + hex_md5(nodes[i].id)).get(0).checked = checked || checked == "1" ? true : false;
	  }
	}
  }
  
  /**
   * 模块关联Rest接口确认
   */
  function apply() {
    var jsonObj = [];
    //var nodes = $('#treegrid').treegrid('getSelections');
    var nodes = $('#treegrid').treegrid('getChildren');
    //window.top.$.messager.alert("提示信息", JSON.stringify(nodes), "info");
    for(var i = 0; i < nodes.length; i++) {
      var el = $("#" + hex_md5(nodes[i].id));
      if(el.length == 0 || !el.get(0).checked) {
    	continue;
      }
      
      var obj = {};
      obj.moduleId = moduleId;
      obj.url = nodes[i].url;
      obj.methodName = nodes[i].name;
      jsonObj.push(obj);
    }
    
  	$.ajax({
      type: "POST",
      url: rootName + "/sys/restSecurity/saveRest/" + moduleId,
      data: JSON.stringify(jsonObj),
      dataType: 'json',
      contentType: "application/json",
      success: function(result) {
        if(result.success) {
          window.top.$.messager.alert("提示信息", result.message, "info", function() {
        	closeModalWindow();
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