define(["base","commonApp"],function(base,common){
	var bodyHeight=$("body").height();
	$(".container").height(bodyHeight-30)
	var cloud = {
		processing:true,
		serverSide:true,
		searching:false,
		ordering:false,
		lengthChange:false,
		paging:false,
		info:true,
		pagingType: "full_numbers",
		ajax:{
			url:$.base+"/store/cloudUsage",
			type:"get",
			dataSrc:function (d) {
			}
		},
		columns:[
			{"title":"总空间","data":"total","sWidth":"25%"},
			{"title":"已用空间","data":"used","sWidth":"25%"},
			{"title":"剩余空间","data":"unUsed","sWidth":"25%"},
			{"title":"使用率","data":"rate","sWidth":"25%"}
		],
		columnDefs:[
			{
				"render":function(data,type,row,meta){
					var width = row.rate+"%";
					var background="";
					if(row.rate>0 && row.rate<60){
						background="#68e879";
					}else if(row.rate>=60 && row.rate<80){
						background="#f9ef6f";
					}else{
						background="#ff0000"
					}
					var html = "<div class='progressWrapper'>"+
						"<div class='progressBar' style='width:"+width+";background:"+background+"'>"+width+"</div>"
						+"</div>"
					return html
				},
				"targets":3
			}
		],
		drawCallback:function(setting){
			setGrid($("#code"),code);
		}
	}
	var code = {
		processing:true,
		serverSide:false,
		searching:false,
		ordering:false,
		lengthChange:false,
		paging:true,
		info:true,
		pagingType: "full_numbers",
		ajax:{
			url:"json/monitorCode.json"
		},
		columns:[
			{"title":"名称","data":"name","sWidth":"15%"},
			{"title":"新视通账号","data":"account","sWidth":"10%"},
			{"title":"IP","data":"ip","sWidth":"10%"},
			{"title":"所属医院","data":"hospital","sWidth":"15%"},
			{"title":"总空间","data":"total","sWidth":"10%"},
			{"title":"已用空间","data":"used","sWidth":"10%"},
			{"title":"剩余空间","data":"unUsed","sWidth":"10%"},
			{"title":"使用率","data":"rate","sWidth":"20%"},
		],
		columnDefs:[
			{
				"render":function(data,type,row,meta){
					var width = row.rate+"%";
					var background="";
					if(row.rate>0 && row.rate<60){
						background="#68e879";
					}else if(row.rate>=60 && row.rate<80){
						background="#f9ef6f";
					}else{
						background="#ff0000"
					}
					var html = "<div class='progressWrapper'>"+
						"<div class='progressBar' style='width:"+width+";background:"+background+"'>"+width+"</div>"
						+"</div>"
					return html
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