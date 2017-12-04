$.fn.createSelect2 = function(options) {
	  var defaults = {};    
	  var path = getRootPatch();
	  var opts = $.extend(defaults, options);  
	  var that = this;
	  var ajax_param = {
		url : path + options.uri,
		type : "get",
		dataType : "json",
		async : false,
		data : {
			"id" : options.param,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (data != "") {
				$.each(that,function(index,e){
					 var selId = $(e).attr('id');
					
					 for(var i=0;i<data.length;i++)  {
                        buildselect(selId,data[i][options.name],data[i][options.id]);
                     }
                
					 $("#"+selId).select2({'formatNoMatches' : function () {return '没有匹配';}});
                     if(undefined != options.initVal){
                         $("#"+selId).select2("val", options.initVal[selId]);
                     }
				});
			} else {
				$.each(that,function(index,e){
					 $("#"+$(e).attr('id')).select2({'formatNoMatches' : function () {return '没有匹配';}});
				});
			}
		}
	};
	ajax_(ajax_param);
}; 

//设置输入框默认提示消息
$.fn.setDefauleValue = function (options) {
    var defauleValue = options.prompt||'';
    $(this).val(defauleValue).css('color', '#999');

    return this.each(function () {
        $(this).focus(function () {
            if ($(this).val() == defauleValue) {
                $(this).val('').css('color', '#000'); //输入值的颜色 
            }
        }).blur(function () {
            if ($(this).val() == '') {
                $(this).val(defauleValue).css('color', '#999'); //默认值的颜色 
            }
        });
    });
}

$.fn.deptAsynTree = function (options) {
	var that = this;
	var width = $(that).width()+4;
	var $tree = $('<ul style="width: '+ width +'px;display: none;border: 1px solid #B8CED9;height: 130px;overflow-y: auto;background-color:white;z-index:1000;position:absolute;" id="tree_dept" ></ul>');
	var $hidden = $('<input type="hidden" name="'+$(that).attr('name')+'" value="'+$(that).attr('alt')+'"/>');
	$(that).removeAttr('name').after($tree).after($hidden);
	$tree.tree({
	    url : getRootName() + "/permissionDepartment/initTree",
	    animate:true,
	    lines:true,
	    onBeforeExpand:function(node,param){  //节点展开前触发
	    	var queryParam = "pid";
	    	if (node.isOrg) { //判断是否是按组织机构查询
	    		queryParam = "orgId";
	    	}
	    	$tree.tree('options').url = getRootName() + "/permissionDepartment/initDepartment?"+queryParam+"=" + node.id;
        },
    	onDblClick : function(node){
    		if (!node.isChecked) {
    			$tree.hide();
        		$(that).val(node.text).attr('title', node.text);
        		$hidden.val(node.id);
        		options && options.onDblClick && options.onDblClick(node);
    		}
    	},
    	formatter : function(node) {
    		return "<div style='width: 220px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;' title='"+node.text+"'>"+node.text+"</div>";
    	}
	});
	$(this).click(function() {
		if ($tree.css("display") == 'none') {
			$tree.show();
			
			if ($('#clearInfo').length == 0) {
				$tree.append("<li id='clearInfo' style='float: right;margin-right: 5px; cursor: pointer;'><span id='clear_dept'>清除</span></li>");
				$('#clear_dept').click(function(){
					$(that).val('');
					$hidden.val('');
					options && options.clear && options.clear();
				});
			}
		} else {
			$tree.hide();
		}
	});
	document.onclick =function(e){
	    if ($tree.css("display") != 'none' && e.target.id != $(that).attr("id")) {
	    	$tree.hide();
	    }
	};
};