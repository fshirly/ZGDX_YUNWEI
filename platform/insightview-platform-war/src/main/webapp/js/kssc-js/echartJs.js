define(["base","commonApp","echarts2.0","china-main-city-map"],function(base,common){
	var bodyHeight=$("body").height();//设置页面的高度
	$(".container-box").height(bodyHeight-31);//首先container-box
    $(".left-box").height(bodyHeight-81);//去掉header的30px，一级content-box的上下padding，20px
    $(".right-box").height(bodyHeight-81);
	$(".appChart").css({width:"95%",height:"95%","margin":"auto"});
	$("#app").css({width:"100%",height:"100%","margin":"auto"});//设置中国地图的宽高
    var liHeight = $(".right-box").height()/3;
    $(".right-box ul>li").height(liHeight);//将right-box中的高度分成3份
	//获取城市name与code对应的对象
	var cityMap = getCode();
	var myChart = echarts.init(document.getElementById('app'));
	//初始化地图
	loadMap('100000', 'china',false);
	function loadMap(mapCode, mapName,flag){
		$.getJSON($.base+'/js/kssc-js/china-main-city/' + mapCode + '.json',function(data){
			if(data){
				var option = {
					tooltip:{
						trigger:'item',
						formatter:'{b}'
					},
					legend: {
						orient: 'vertical',
						x:'left',
						y:'bottom',
						itemWidth: 77,
						itemHeight: 7,
						selected:{
                            "编解码正常":true,
                            "编解码异常":true
                        },
						selectedMode: 'multiple',
						itemGap: 35,
						data:[
							{
								name:"编解码正常",
								textStyle:{
									color:"#333"
								},
								icon:"image://../style/images/kssc-img/normal.png"
							},
							{
								name:"编解码异常",
								textStyle:{
									color:"#333"
								},
								icon:"image://../style/images/kssc-img/abnormal.png"
							}
						]
					},
					dataRange:{
						show:false,
						min:0,
						max:3,
                        calculable : false,
                        splitNumber:2,
                        selectedMode:"multiple",
						color: ['#ff0000','#6cff00']
					},
					series:[
						{
							name:'编解码状态',
							type: 'map',
							mapType: mapName,
							selectedMode : 'multiple',//single
							label: {
								normal:{
									show: true
								},
								emphasis: {
									show: true
								}
							},
							itemStyle:{
								normal:{
									borderColor:'#cad3e1',
									label:{
										show:true,
										textStyle:{
											color:'#ccc'
										}
									},
									areaStyle:{
										color:'#00489d'
									}
								},
								emphasis:{
									color:"transparent",
									label:{
										show:true,
										textStyle:{
											color:'#fff'
										}
									}
								},
								borderColor:"#fff"
							},
							markLine:{
								smooth:true,
								effect:{
									show:true,
									scaleSize:1,
									period:30,
									color:'#fff',
									shadowBlur:10
								},
								itemStyle:{
									normal:{
                                        label:{show:false},
										borderWidth:1,
										lineStyle:{
											type:'solid',
											shadowBlur:10
										}
									},
                                    emphasis:{
                                        label:{show:false}
                                    }
								},
								data:[]
							},
							markPoint:{
								symbol:'emptyCircle',
				                symbolSize : function (v){
				                    return 10 + v/10
				                },
				                effect : {
				                    show: true,
				                    shadowBlur : 0
				                },
				                itemStyle:{
				                    normal:{
				                        label:{show:false}
				                    },
				                    emphasis: {
                                        label:{show:false}
				                    }
				                },
				                data:[]
							},
							data:data,
                            geoCoord: {
                                '上海': [121.4648,31.2891],
                                '东莞市': [113.8953,22.901],
                                '东营市': [118.7073,37.5513],
                                '中山市': [113.4229,22.478],
                                '临汾市': [111.4783,36.1615],
                                '临沂市': [118.3118,35.2936],
                                '丹东市': [124.541,40.4242],
                                '丽水市': [119.5642,28.1854],
                                '乌鲁木齐市': [87.9236,43.5883],
                                '佛山市': [112.8955,23.1097],
                                '保定市': [115.0488,39.0948],
                                '兰州市': [103.5901,36.3043],
                                '包头市': [110.3467,41.4899],
                                '北京': [116.4551,40.2539],
                                '北海市': [109.314,21.6211],
                                '南京市': [118.8062,31.9208],
                                '南宁市': [108.479,23.1152],
                                '南昌市': [116.0046,28.6633],
                                '南通市': [121.1023,32.1625],
                                '厦门市': [118.1689,24.6478],
                                '台州市': [121.1353,28.6688],
                                '合肥市': [117.29,32.0581],
                                '呼和浩特市': [111.4124,40.4901],
                                '咸阳市': [108.4131,34.8706],
                                '哈尔滨市': [127.9688,45.368],
                                '唐山市': [118.4766,39.6826],
                                '嘉兴市': [120.9155,30.6354],
                                '大同市': [113.7854,39.8035],
                                '大连市': [122.2229,39.4409],
                                '天津': [117.4219,39.4189],
                                '太原市': [112.3352,37.9413],
                                '威海市': [121.9482,37.1393],
                                '宁波市': [121.5967,29.6466],
                                '宝鸡市': [107.1826,34.3433],
                                '宿迁市': [118.5535,33.7775],
                                '常州市': [119.4543,31.5582],
                                '广州市': [113.5107,23.2196],
                                '廊坊市': [116.521,39.0509],
                                '延安市': [109.1052,36.4252],
                                '张家口市': [115.1477,40.8527],
                                '徐州市': [117.5208,34.3268],
                                '德州市': [116.6858,37.2107],
                                '惠州市': [114.6204,23.1647],
                                '成都市': [103.9526,30.7617],
                                '扬州市': [119.4653,32.8162],
                                '承德市': [117.5757,41.4075],
                                '拉萨市': [91.1865,30.1465],
                                '无锡市': [120.3442,31.5527],
                                '日照市': [119.2786,35.5023],
                                '昆明市': [102.9199,25.4663],
                                '杭州市': [119.5313,29.8773],
                                '枣庄市': [117.323,34.8926],
                                '柳州市': [109.3799,24.9774],
                                '株洲市': [113.5327,27.0319],
                                '武汉市': [114.3896,30.6628],
                                '汕头市': [117.1692,23.3405],
                                '江门市': [112.6318,22.1484],
                                '沈阳市': [123.1238,42.1216],
                                '沧州市': [116.8286,38.2104],
                                '河源市': [114.917,23.9722],
                                '泉州市': [118.3228,25.1147],
                                '泰安市': [117.0264,36.0516],
                                '泰州市': [120.0586,32.5525],
                                '济南市': [117.1582,36.8701],
                                '济宁市': [116.8286,35.3375],
                                '海口市': [110.3893,19.8516],
                                '淄博市': [118.0371,36.6064],
                                '淮安市': [118.927,33.4039],
                                '深圳市': [114.5435,22.5439],
                                '清远市': [112.9175,24.3292],
                                '温州市': [120.498,27.8119],
                                '渭南市': [109.7864,35.0299],
                                '湖州市': [119.8608,30.7782],
                                '湘潭市': [112.5439,27.7075],
                                '滨州市': [117.8174,37.4963],
                                '潍坊市': [119.0918,36.524],
                                '烟台市': [120.7397,37.5128],
                                '玉溪市': [101.9312,23.8898],
                                '珠海市': [113.7305,22.1155],
                                '盐城市': [120.2234,33.5577],
                                '盘锦市': [121.9482,41.0449],
                                '石家庄市': [114.4995,38.1006],
                                '福州市': [119.4543,25.9222],
                                '秦皇岛市': [119.2126,40.0232],
                                '绍兴市': [120.564,29.7565],
                                '聊城市': [115.9167,36.4032],
                                '肇庆市': [112.1265,23.5822],
                                '舟山市': [122.2559,30.2234],
                                '苏州市': [120.6519,31.3989],
                                '莱芜市': [117.6526,36.2714],
                                '菏泽市': [115.6201,35.2057],
                                '营口市': [122.4316,40.4297],
                                '葫芦岛市': [120.1575,40.578],
                                '衡水市': [115.8838,37.7161],
                                '衢州市': [118.6853,28.8666],
                                '西宁市': [101.4038,36.8207],
                                '西安市': [109.1162,34.2004],
                                '贵阳市': [106.6992,26.7682],
                                '连云港市': [119.1248,34.552],
                                '邢台市': [114.8071,37.2821],
                                '邯郸市': [114.4775,36.535],
                                '郑州市': [113.4668,34.6234],
                                '鄂尔多斯市': [108.9734,39.2487],
                                '重庆': [107.7539,30.1904],
                                '金华市': [120.0037,29.1028],
                                '铜川市': [109.0393,35.1947],
                                '银川市': [106.3586,38.1775],
                                '镇江市': [119.4763,31.9702],
                                '长春市': [125.8154,44.2584],
                                '长沙市': [113.0823,28.2568],
                                '长治市': [112.8625,36.4746],
                                '阳泉市': [113.4778,38.0951],
                                '青岛市': [120.4651,36.3373],
                                '韶关市': [113.7964,24.7028]
                            }
						}
					]
				};
				var markLineData=[];
				var markPointData = [];
				var renderData = getCodeStatus();
				var tempArr=[];
				$.each(renderData.codeList,function (index,item) {
					var obj = {};
					var temp = {name:"上海"};
					var arr=[];
                    obj.name = item.location;
					obj.value = item.status=="ok"? 1:2; //将状态是1的
					arr=[temp,obj];
					markLineData.push(arr);
					markPointData.push(obj);
					tempArr.push(item.location)
				});
				option.series[0].markLine.data = markLineData;
				option.series[0].markPoint.data = markPointData;
				//下钻
                // if(!flag){
                 //
                // }else{
				// 	delete option.series[0].markLine;
				// 	if($.inArray(mapName,tempArr)!=-1){//包含
				// 		//判断下钻的点是否包含在返回的数组中，如果包含有markpoint，否则没有
				// 		option.series[0].markPoint.data = markPointData;
				// 	}else{
				// 		delete option.series[0].markPoint;
				// 		//option.series[0].markPoint.data = markPointData;
				// 	}
				// }
				myChart.setOption(option);
				window.myChart=myChart;
				if(flag){ //true表示下钻，下钻之后要把markline去掉
					$.each(tempArr,function (index,item) {
						window.myChart.delMarkLine(0,"上海 > "+item);
						if($.inArray(mapName,tempArr)==-1){
							window.myChart.delMarkPoint(0,item);
						}
					});

				}
				console.log(myChart)
			}
		})
	}
	myChart.on("click",function(params){
        //下钻
		var name = params.name;
		var mapCode = cityMap[name];
		loadMap(mapCode,name,true);
	});
	//初始化新世通云平台表格
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
			"url":$.base+"/store/getMedInterfaceInfo",
			"type":"post",
			"contentType":"application/json",
			"dataType":"json",
			"data":function (d) {
				return JSON.stringify({pageNo:d.start/d.length+1,pageSize:d.length,param:{}})
			}
		},
		columns:[
			{"title":"接口状态","data": "status","sWidth":"31%"},
			{"title":"接口名称","data":"name","sWidth":"31%"},
			{"title":"接口URI","data":"url","sWidth":"25%"},
			{"title":"方法","data":"method","sWidth":"13%"}
		],
		columnDefs:[
			{
				"render":function(data,type,row,meta){
					if(row.status==0){
						return "异常"
					}else{
						return "正常"
					}
				},
				targets:0
			},
            {
                "render":function(data,type,row,meta){
                    var url ="";
                    if(row.url!=null){
                        if(row.url.length>10){
                            url=row.url.substr(0,10)+"....."
                        }else{
                            url = row.url;
                        }
                    }else{
                        url="";
                    }
                    var html = "<div title='"+row.url+"'>"+url+"</div>";
                    return html;
                },
                targets:2
            }
		],
		drawCallback:function(setting){
			$(".warpper").height($(".right-box").height()-40);
			base.scroll({
				container:$(".warpper")
			});
		}
	};
	var setGrid = function(){
		grid = base.datatables({
			container:$("#tableCloud"),
			option:gridOption
		})
	};
	//点击调整宽度
	var flag = false;
	function ajustWidth(){

		$(".ajustBtn").off().on("click",function(){
			if(!flag){
				$(".left-box").animate({width:'100%'},function(){
					$(".ajustBtn i").addClass("transformIcon");
					myChart.resize();
					flag=true;
			    });
			}else{
				$(".left-box").animate({width:'55%'},function(){
					$(".ajustBtn i").removeClass("transformIcon");
					myChart.resize();
					flag=false;
				});
			}
			
			$(".warpper").hide();
			
		})
	}
	//获取编解码状态
	function getCodeStatus(){
        var data;
		$.ajax({
			url:$.base+"/store/medtOkOrNot"	,
			type:"get",
			contentType:"application/json",
			async:false,
			success:function(result){
				data =  result.data;
				$(".code .normal").html(data.normal);
				$(".code .abnormal").html(data.abnormal);
			}
		});
        return data;
	}
	//获取天翼云存储状态
	function getCloud(){
		var cloud={};
		$.ajax({
			url:$.base+"/store/cloudUsage",
			type:"get",
			contentType:"application/json",
			async:false,
			success:function(result){
				if(result.status=="1"){
					$(".cloud .connectivity").html("正常");
					$(".cloud .rate").html(result.data);
				}else{
					$(".cloud .connectivity").html("异常")
				}

				cloud = result;
			}
		});
		return cloud;
	}
	//获取新视通正常个数
	function getNormalNum() {
		$.ajax({
			url:$.base+"/store/getMedInterfaceInfo",
			type:"post",
			contentType:"application/json",
			data:JSON.stringify({pageNo:1,pageSize:10,param:{}}),
			success:function(result) {
				$(".normalPort").html(result.recordsTotal);
			}
		})
	}
	return {
		main:function(){
            getCodeStatus();
			ajustWidth();
			getCloud();
			getNormalNum();
			//返回上一级
			$("#contextMenu").off().on("click",function(){
				loadMap('100000', 'china',false);
				$(".initContent").show();
				$(".warpper").html("");
			});
			//点击天翼云存储平台
			$(".storageClouds").off().on("click",function(){
				$(".initContent").hide();
				var width = ($(".right-box").width()-80)+"px";
				var height = ($(".right-box").height()-150)+"px";
				var html = "<div id='pie' style='width:"+width+";height:"+height+"'></div>";
				$(".warpper").html(html).show();

				$(".left-box").animate({width:'55%'},"fast","linear",function(){
					myChart.resize();
					//初始化天翼云平台
					require(["echarts","app/pieChart"],function(echarts,pie){
						var obj = getCloud();
						if(obj.data.indexOf("%")!=-1){
							obj.data = obj.data.split("%")[0]
						}
						var used =  obj.data*6;
						var unUsed = 6-obj.data*6;
						var data =[{value:used,name:"已使用空间("+used+"T)"},{value:unUsed,name:"未使用空间("+unUsed+"T)"}];
						pie.series[0].data=data;
						pie.legend.data[0].name = "已使用空间("+used+"T)" ;
						pie.legend.data[1].name = "未使用空间("+unUsed+"T)" ;
						var pieChart = echarts.init(document.getElementById('pie'));
						pieChart.setOption(pie)
					})
				});
			});
			//点击新世通云平台
			$(".cloudPlatform").off().on("click",function(){
				$(".initContent").hide();
				var html = "<table class='table table-bordered' id='tableCloud'><caption>新视通接口状态详情</caption></table>";
				$(".warpper").html(html).show();
				$(".left-box").animate({width:'55%'},"fast","linear",function(){
					myChart.resize();
					setGrid();
				});
				
			})
		}
	}
});
