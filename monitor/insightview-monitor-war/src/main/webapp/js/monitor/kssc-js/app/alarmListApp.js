define(["base","commonApp"],function(base,common){
	var bodyHeight=$("body").height();
	var containerHeight = $(".container").height(bodyHeight-30);
	var gridOption = {
		processing:true,
		serverSide:true,
		searching:false,
		ordering:false,
		lengthChange:false,
		paging:true,
		info:true,
		pagingType: "full_numbers",
		ajax:{
			url:$.base+"/monitor/alarmMgr/alarmPanel/loadActiveAlarmListNew",
			type:"post",
			contentType:"application/json",
			dataType:"json",
			data:function (d) {
				return JSON.stringify({pageNo:d.start/d.length+1,pageSize:d.length,
					param:{"userId":userID,"viewCfgID":"2"}})
			}
		},
		columns:[
			{"title":"","data": "id","sWidth":"5%"},
			{"title":"序号","data": "id","sWidth":"8%"},
			{"title":"告警时间","data":"lastTime","sWidth":"18%"},
			{"title":"告警级别","data":"alarmLevelName","sWidth":"10%"},
			{"title":"告警标题","data":"alarmTitle","sWidth":"25%"},
			{"title":"告警类型","data":"alarmTypeName","sWidth":"14%"},
			{"title":"告警源名称","data":"moName","sWidth":"20%"}
		],
		columnDefs:[
			{
				"render":function(data,type,row,meta){
					return "<input type='radio' alarmID='"+row.alarmID+"' name='clearItem' class='clearItem'>"
				},
				targets:0
			},
			{
				"render":function(data,type,row,meta){
					return meta.row
				},
				targets:1
			},
			{
				"render":function(data,type,row,meta){
					var time =  new Date(row.lastTime);
					var year =  time.getFullYear();
					var month = time.getMonth()+1;
					var date = time.getDate();
					var hours = time.getHours();
					var minute = time.getMinutes();
					var second = time.getSeconds();
					if(month<10){
						month = "0"+month
					}
					if(date<10){
						date = "0"+date
					}
					if(hours<10){
						hours = "0"+hours
					}

					if(minute<10){
						minute = "0"+minute
					}
					if(second<10){
						second = "0"+second
					}
					return year+"-"+month+"-"+date+" "+hours+":"+minute+":"+second;
				},
				targets:2
			},
			{
				"render":function(data,type,row,meta){
					var color = "";
					switch (row.alarmLevel){
						case 1:
							color = "#ce0000";
						break;
						case 2:
							color = "#e16602";
						break;
						case 3:
							color = "#ec9b00";
						break;
						case 4:
							color = "#81b601";
						break;
						case 5:
							color = "#ccc";
						break;
					}
					return  "<div style='width:100%;height:100%;background:"+color+" '>"+row.alarmLevelName+"</div>";
				},
				targets:3
			}
		],
		drawCallback:function(setting){
			clearItem();
			base.scroll({
				container:$(".container")
			})
		}
	};
	function clearItem() {
		$("#searchBtn").off().on("click",function () {
			if( $(".clearItem:checked").length==0){
				base.requestTip({position:"center"}).error("请先选择一条数据！");
				return;
			}
			var id = $(".clearItem:checked").attr("alarmID");
			var modal = base.modal({
				modalId:"clearItem",
				label:"告警清除",
				width:350,
				height:50,
				context:"<div>是否确定清除告警？(被清除的告警将存至历史告警)</div>",
				callback:function () {

				},
				buttons:[
					{
						label:"确定",
						cls:"btn btn-info",
						clickEvent:function () {
							$.ajax({
								url:$.base+"/monitor/alarmActive/doBathClearAlarmActive",
								type:"post",
								data:{"id":id,"cleanInfo":""},
								success:function (result) {
									base.requestTip({position:"center"}).success("告警清除成功！");
									modal.hide();
									grid.reload()
								}
							})
						}
					},
					{
						label:"取消",
						cls:"btn btn-warning",
						clickEvent:function(){
							modal.hide();
						}
					}
				]
			})

		})
	}
	var setGrid = function(){
		grid = base.datatables({
			container:$("#monitor"),
			option:gridOption
		})
	} 
	return {
		main:function(){
			setGrid();
		}
	}
})