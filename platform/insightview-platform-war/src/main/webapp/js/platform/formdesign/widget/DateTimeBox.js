/**
* 单行文本控件实现类
* 继承控件基类ISV.formd.base.Widget
* 郑自辉
*/
f.namespace('ISV.pf.fd.widget');
(function(w){
	w.DateTimeBox = function(){
        //控件类型
        this.widgetType = 'DateTimeBox';
        
        ISV.pf.fd.base.FormWidget.apply(this,arguments);
        
        /**
         * 属性界面渲染处理
         * 主要包含对特殊控件的渲染
         * 如easyui的控件
         */
        this.customOnReady = function(){
            $('#required').combobox({
                data: [
                        {
                            id: 1,
                            text: '是'
                        },
                        {
                            id: 0,
                            text: '否'
                        }
                ],
                valueField: 'id',
                value: 1
            });
            $('#isTableDisplay').combobox({
                data: [
                        {
                            id: 1,
                            text: '是'
                        },
                        {
                            id: 0,
                            text: '否'
                        }
                ],
                valueField: 'id',
                value: 1
            });
            $('#columnNum').combobox({
				data : [
				        {
				        	id : 1,
				        	text : '一列显示'
				        },
				        {
				        	id : 2,
				        	text : '两列显示'
				        }],
				        valueField: 'id',
						value: 1
			});
            $('#columnNum').combobox({value : $('#columnNum').val()});
			$('#showTime').combobox({
				data : [{
					       id : 1,
					       text : '否'
				       },{
				    	   id : 2,
				    	   text : '是'
				       }],
			   valueField : 'id',
			   value : 1
			});
			$('#showTime').combobox({value : $('#showTime').val()});
           
            if ($('#required').val() != '') {
                $('#required').combobox('setValue',$('#required').val());
            }
            if ($('#isTableDisplay').val() != '') {
                $('#isTableDisplay').combobox('setValue',$('#isTableDisplay').val());
            }
        };
    	
    	/**
    	 * 创建DateTimeBox元素
    	 */
    	this.createWidget = function(data){
    		var $datetimebox = $('<input/>');
    		$datetimebox.attr('id',data.id).attr('widgetType',data.widgetType).attr('style','width:150px');
    		return $datetimebox;
    	};
    	
    	this.customRender = function($dom){
    		$dom.datetimebox({
    //           formatter : formatDateText, 
               editable : false
            });
        };
	};
	//注册此控件类型
	ISV.pf.fd.base.FormWidgetFactory.register('DateTimeBox',w.DateTimeBox);
})(ISV.pf.fd.widget);