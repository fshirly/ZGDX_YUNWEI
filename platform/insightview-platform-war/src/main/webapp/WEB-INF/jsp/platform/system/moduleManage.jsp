<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>模块管理页面</title>
    <script type="text/javascript" charset="utf-8" src="../../../js/platform/system/head.js"></script>
    
  </head>
  <body>
    <!--<a href="../../../" class="easyui-linkbutton" style="width:85px;margin:10px 0px;">返回首页</a>-->
    <div id="toolbar">
      <a id="addFirst" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" style="display:none"
        plain="true" onclick="addFirstNode()">新增</a>
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" style="display:none"
        plain="true" onclick="savaData()">保存</a>
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-setting"
        plain="true" onclick="relatedRest()">关联Rest接口</a>
      <dfn style="float:right;padding:5px; display:none">支持节点拖拽功能</dfn>
    </div>
    <table id="treegrid" toolbar="#toolbar"></table>
    <div id="grid_mm" class="easyui-menu" style="width:120px;">
      <div onclick="addNode()">添加节点</div>
      <div onclick="addChildNode()">添加子节点</div>
      <div class="menu-sep"></div>
      <div onclick="move(1)">向上移动</div>
      <div onclick="move(-1)">向下移动</div>
      <div class="menu-sep"></div>
      <div onclick="editNode()">编辑节点</div>
      <div onclick="removeNode()">删除节点</div>
    </div>
  </body>
</html>
<script type="text/javascript">
  var rootName = getRootName();
  var op = 1; //操作类型
  
  var id = 1000;
  function getId() {
    return id++;
  }
  
  /**
   * 页面初始化
   */
  $(document).ready(function() {
	InitTreeGridData();
    query();
  });
  
  /**
   * 查询模块数据
   */
  function query() {
    $.ajax({
      type: "GET",
      url: rootName + "/sys/module",
      dataType: 'json',
      success: function(result) {
    	if(isEmpty(result.data)) {
    	  $("#addFirst").show();
    	}
        var fileds = "id,pid,name,type,sortOrder,url,note";
        var nodes = ConvertToTreeGridJson(result.data, "id", "pid", fileds);
        //window.top.$.messager.alert("提示信息", JSON.stringify(nodes), "info");
        $("#treegrid").treegrid('loadData', nodes);
        $("#treegrid").treegrid('collapseAll');
      },
      error: function(result) {
        show_error(result);
      }
    });
  }
  
  /**
   * 初始化树形表格数据
   */
  function InitTreeGridData() {
    $("#treegrid").treegrid({
      //title: "模块管理",
      width: '100%',
      height: '100%',
      singleSelect: true,
      rownumbers: true,
      autoRowHeight: true,
      nowrap: true,
      idField: 'id',
      treeField: 'name',
      columns:[[
        {field:'id',title:'id',width:100,hidden:true },
        {field:'pid',title:'父模块id',width:100,hidden:true },
        {field:'name',title:'模块名称',width:'35%'},
        {field:'type',title:'模块类型',width:'10%',formatter:function(value,row,index){ return value == "1" ? "模块" : "功能按钮" }},
        {field:'url', title:'URL',width:'35%' },
       // {field:'methodName',title:'方法',width: 450},
        {field:'note',title:'说明',width:'18%'}
       
      ]],
      onContextMenu: function(e,row) {
        if(row){
          e.preventDefault();
          $(this).treegrid('select', row.id);
          $('#grid_mm').menu('show',{
            left: e.pageX,
            top: e.pageY
          });
        }
      },
      onLoadSuccess: function(row) {
		$(this).treegrid('enableDnd', row ? row.id : null);
	  },
    });
  }
  
  /**
   * 添加第一个节点
   */
  function addFirstNode() {
    var _data = $('#treegrid').treegrid('getData');
    if(_data.length == 0) {
      addNode();
    }
  }
  
  /**
   * 添加节点
   */
  function addNode() {
    var pNode;
	var row = $('#treegrid').treegrid('getSelected');
	if(!isEmpty(row)) {
	  pNode = $('#treegrid').treegrid('getParent', row.id);
	}
    var pid = pNode ? pNode.id : "-1";
    setJsonObj('row', {"pid": pid}); //在弹出窗口之前设置Json对象,提供给弹出窗口使用
    OpenWin(rootName + "/sys/module/create/html", "新增模块", 608, 352);
    
    op = 1;
  }
  
  /**
   * 添加子节点
   */
  function addChildNode() {
	var row = $('#treegrid').treegrid('getSelected');
	setJsonObj('row', {"pid": row.id}); //在弹出窗口之前设置Json对象,提供给弹出窗口使用
	OpenWin(rootName + "/sys/module/create/html", "新增子模块", 608, 352);
	
    op = 2;
  }
  
  /**
   * 编辑节点
   */
  function editNode() {
	var row = $('#treegrid').treegrid('getSelected');
	setJsonObj('row', row); //在弹出窗口之前设置Json对象,提供给弹出窗口使用
	OpenWin(rootName + "/sys/module/create/html", "修改模块", 608, 352);
	
    op = 3;
  }
  
  /**
   * 移除节点
   */
  function removeNode() {
	window.top.$.messager.confirm('确认', '您确定要删除选择的记录吗？',
    function(r) {
      if(r) {
        var jsonObj = [];
        var row = $('#treegrid').treegrid('getSelected');
        $.ajax({
          type: "DELETE",
          url: rootName + '/sys/module/' + row.id,
          //data: JSON.stringify(jsonObj),
          dataType: 'json',
          contentType: "application/json",
          success: function(result) {
            if(result.success) {
              //window.top.$.messager.alert("提示信息", result.message);
              $('#treegrid').treegrid('remove', row.id);
              
              //重新获取数据
              var _data = $('#treegrid').treegrid('getData');
              if(_data.length == 0) {
                $("#addFirst").show();
              }
            }
          },
          error: function(result) {
            show_error(result);
          }
        });
      }
    });
  }
  
  /**
   * 移动节点
   */
  function move(direction) {
	var row = $('#treegrid').treegrid('getSelected'); //当前行
    var nodeData = $('#treegrid').treegrid('find', row.id);
    if(row.pid && row.pid != '-1') { //非根节点
      var pNodeData = $('#treegrid').treegrid('find', row.pid);
      var pos = $.inArray(nodeData, pNodeData.children);
      if((direction == 1 && pos == 0) || (direction == -1 && pos == pNodeData.children.length - 1)) {
      	return;
      }
      var toPos = pos - direction;
      pNodeData.children = changeArrayItemPos(pNodeData.children, pos, toPos);
      $('#treegrid').treegrid('update',{
        id: row.pid,
        row: pNodeData
      });
      //调用update方法时,子节点数据没有刷新,这里还需要调用loadData方法刷新子节点
      var data = $('#treegrid').treegrid('getData'); //treegrid数据
      $('#treegrid').treegrid('loadData', data);
    } else { //根节点
      var data = $('#treegrid').treegrid('getData'); //treegrid数据
      var pos = $.inArray(nodeData, data);
      if((direction == 1 && pos == 0) || (direction == -1 && pos == data.length - 1)) {
    	return;
      }
      var toPos = pos - direction;
      var newData = changeArrayItemPos(data, pos, toPos);
      $('#treegrid').treegrid('loadData', newData);
    }
  }
  
  /**
   * 更新TreeGrid
   */
  function renderTreeGrid(formData) {
    var row = $('#treegrid').treegrid('getSelected');
    var _data = [];
    _data.push(formData);
    
    if(op == 1) { //添加节点
  	  var rows = $('#treegrid').treegrid('getData');
      if(rows.length == 0) {
	    $('#treegrid').treegrid('append', {
          data: _data
        });
	    $("#addFirst").hide();
      } else {
        $('#treegrid').treegrid('insert', {
          after: row.id,
          data: formData
        });
      }
    } else if(op == 2) { //添加子节点
      $('#treegrid').treegrid('append', {
        parent: row.id,
        data: _data
      });
    } else if(op == 3) { //编辑节点
      $('#treegrid').treegrid('update', {
        id: row.id,
        row: formData
      });
    }
  }
  
  /**
   * 保存模块设置(拖动或调整顺序时需要保存)
   */
  function savaData() {
    var isModified = false; //数据修改标识
	
	var data = [];
    var items = $('#treegrid').treegrid('getChildren');
    
    //根据树形数据设置sortOrder
    var roots = $('#treegrid').treegrid('getRoots');
    for(var i = 0; i < roots.length; i++) {
      if(i + 1 != roots[i].sortOrder) {
    	roots[i].sortOrder = i + 1;
    	isModified = true;
      }
    }
    
    for(var i = 0; i < items.length; i++) {
      var level0 = $('#treegrid').treegrid('getLevel', items[i].id); //节点层级
      var nodes11 = $('#treegrid').treegrid('getChildren', items[i].id);
      var num = 0; //一级子节点序号
      for(var j = 0; j < nodes11.length; j++) {
    	var level = $('#treegrid').treegrid('getLevel', nodes11[j].id); //子节点层级
    	if(level != level0 + 1) continue; //用来控制只获取一级子节点
    	
    	if(num + 1 != nodes11[j].sortOrder) {
    	  nodes11[j].sortOrder = num + 1;
    	  isModified = true;
    	}
    	num ++;
	  }
    }
    
    if(!isModified && $('#treegrid').datagrid('getChanges').length == 0) {
      window.top.$.messager.alert("提示信息", "模块数据没有任何修改！", "info");
      return false;
    }
    
    for(var i = 0; i < items.length; i++) {
      var obj = {};
      obj.id = items[i].id;
      obj.pid = items[i].pid;
      if(items[i]._parentId && typeof(items[i]._parentId) != "undefined") {
        obj.pid = items[i]._parentId; //节点拖动时_parentId表示父节点id
      }
      obj.name = items[i].name;
      obj.type = items[i].type;
      obj.sortOrder = items[i].sortOrder;
      obj.note = items[i].note;
      
      data.push(obj);
    }
    //show_json(data);
    
    $.ajax({
      type: "PUT",
      url: rootName + "/sys/module/saveData",
      dataType: 'json',
      contentType: "application/json",
      data: JSON.stringify(data),
      success: function(result) {
        if(result.success) {
          window.top.$.messager.alert("提示信息", result.message, "info");
        }
      },
      error: function(result) {
        show_error(result);
      }
    });
  }
  
  /**
   * 关联Rest接口
   */
  function relatedRest() {
	if($("#treegrid").treegrid("getSelections").length != 1) {
	  window.top.$.messager.alert("提示信息", "请选择一个模块关联Rest接口！", "info");
      return;
    }
	
    var row = $('#treegrid').treegrid('getSelected');
    OpenWin(rootName + "/sys/restSecurity/moduleRest/html?id=" + row.id, "关联Rest接口", 885, 550);
  }
</script>