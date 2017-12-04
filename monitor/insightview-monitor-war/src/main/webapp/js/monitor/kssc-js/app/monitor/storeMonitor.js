define(["base","commonApp","enclosure"],function(base,common){
	var bodyHeight=$("body").height();
	$(".container").height(bodyHeight-30);
	var cloud = {
		processing:true,
		serverSide:true,
		searching:false,
		ordering:false,
		lengthChange:false,
		paging:false,
		info:false,
		pagingType: "full_numbers",
		ajax:{
			url:$.base+"/store/cloudUsage",
			type:"get",
			dataSrc:function (d) {
				var obj ={};
				var data=[];
				obj.Capacity=6;
				obj.rate =d.data;
				if(d.data.indexOf("%")!=-1){
					d.data = d.data.split("%")[0]
				}
				obj.used =  d.data*6;
				obj.unUsed = 6-d.data*6;
				data.push(obj);
				return data;
			}
		},
		columns:[
			{"title":"总空间(T)","data":"Capacity","sWidth":"25%"},
			{"title":"已用空间(T)","data":"used","sWidth":"25%"},
			{"title":"剩余空间(T)","data":"unUsed","sWidth":"25%"},
			{"title":"使用率","data":"rate","sWidth":"25%"}
		],
		columnDefs:[
			{
				"render":function (data,type,row,meta) {
					if(row.Capacity==null){
						return 0
					}else{
						return row.Capacity
					}
				},
				"targets":0
			},
			{
				"render":function(data,type,row,meta){
					var rate;
					if(row.rate==null){
						rate = 0;
					}else{
						rate = row.rate;
					}
                    var width = rate;
                    var background="";
                    if(rate>0 && rate<=60){
                        background="#81b601";
                    }else if(rate>60 && rate<=70){
                        background="#ec9b00";
                    }else if(rate>70 && rate<=80){
                        background="#e16602"
                    }else{
                        background="#ce0000"
                    }
                    var html = "<div class='progressWrapperDiv'><div class='progressWrapper'>"+
                        "<div class='progressBar' style='width:"+width+";background:"+background+"'></div>"
                        +"</div><span>"+width+"</span></div>";
                    return html;
				},
				"targets":3
			}
		],
		drawCallback:function(setting){
			setGrid($("#code"),code);
		}
	};
	var code = {
		processing:true,
		serverSide:true,
		searching:false,
		ordering:false,
		lengthChange:false,
		paging:true,
		info:true,
		pagingType: "full_numbers",
		ajax:{
			"type":"post",
			"url":$.base+"/store/diskStatus",
			"contentType":"application/json",
			dataType:"json",
			"data": function ( d ) {
				return JSON.stringify({pageNo:d.start/d.length+1,pageSize:d.length,param:{}});
			},
			dataSrc:function (d) {
				if(d.data==null){
					return []
				}else{
					return d.data
				}
			}
		},
		columns:[
			{"title":"名称","data":"codecOwnership","sWidth":"15%"},
			{"title":"新视通账号","data":"newvideoNum","sWidth":"10%"},
			{"title":"IP","data":"ip","sWidth":"10%"},
			{"title":"所属医院","data":"hospitalName","sWidth":"15%"},
			{"title":"总空间","data":"Capacity","sWidth":"10%"},
			{"title":"已用空间","data":"Free","sWidth":"10%"},
			{"title":"剩余空间","data":"Free","sWidth":"10%"},
			{"title":"使用率","data":"Free","sWidth":"20%"}
		],
		columnDefs:[
			{
				"render":function (data,type,row,meta) {
					if(row.Capacity==null){
						return 0
					}else{
						return row.Capacity
					}
				},
				"targets":4
			},
			{
				"render":function (data,type,row,meta) {
					if(row.remainStorm==null){
						return 0
					}else{
						return Number(row.remainStorm);
					}

				},
				"targets":5
			},
			{
				"render":function (data,type,row,meta) {
					if(row.Free==null){
						return 0
					}else{
						return Number(row.Free);
					}

				},
				"targets":6
			},
			{
				"render":function(data,type,row,meta){
					var rate;
					if(row.Capacity==null){
						rate = 0;
					}
					if(Number(row.Capacity)>0){
						rate = (Number(row.remainStorm)/Number(row.Capacity)*100).toFixed(2);
					}else{
						rate=0
					}
					var width = rate+"%";
					var background="";
					if(rate>0 && rate<=60){
						background="#81b601";
					}else if(rate>60 && rate<=70){
						background="#ec9b00";
					}else if(rate>70 && rate<=80){
						background="#e16602"
					}else{
						background="#ce0000"
					}
					var html = "<div class='progressWrapperDiv'><div class='progressWrapper'>"+
						"<div class='progressBar' style='width:"+width+";background:"+background+"'></div>"
						+"</div><span>"+width+"</span></div>";
					return html;
				},
				"targets":7
			}
		],
		drawCallback:function(setting){
			base.scroll({
				container:$(".container")
			})
		}
	}
	var setGrid = function(table,gridOption){
		grid = base.datatables({
			container:table,
			option:gridOption
		})
	}
	return {
		main:function(){
			setGrid($("#cloud"),cloud);
		}
	}
})