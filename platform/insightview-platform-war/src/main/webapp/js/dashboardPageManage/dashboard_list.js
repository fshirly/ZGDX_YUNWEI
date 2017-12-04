define(["base","commonApp","echarts2.0","china-main-city-map"],function(base,common){
	//设置页面的高度
	var bodyHeight=$("body").height();//首先container-box
	$(".container-box").height(bodyHeight-31);
	$("#app").css({width:"80%",height:"80%","margin":"auto"})//设置中国地图的宽高
	//获取城市name与code对应的对象
	var cityMap = getCode();
	var myChart = echarts.init(document.getElementById('app'));
	//初始化地图
	loadMap('100000', 'china');
	function loadMap(mapCode, mapName){
		$.getJSON('js/kssc-js/china-main-city/' + mapCode + '.json',function(data){
			if(data){
				var option = {
					tooltip:{
						trigger:'item',
						formatter:'{b}'
					},
					dataRange:{
						show:false,
						min:0,
						max:1,
						precision:1,
						splitNumber:2,
						color: ['#6cff00', '#ff0000'],
					},
					series:[
						{
							name:'',
							type: 'map',
							mapType: mapName,
							selectedMode : 'single',//single
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
											color:'#ff0000'
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
										borderWidth:1,
										lineStyle:{
											type:'solid',
											color:"red",
											shadowBlur:10
										}
									}
								},
								data:[
									[{name:"上海"},{name:"北京",value:0}],
									[{name:"上海"},{name:"南京",value:1}],
									[{name:"上海"},{name:"南宁",value:1}],
									[{name:"上海"},{name:"无锡",value:0}],
									[{name:"上海"},{name:"苏州",value:0}],
									[{name:"上海"},{name:"重庆",value:1}]
								]
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
										label:{position:'top'}
									}
								},
								data:[
									{name:"北京",value:0},
									{name:"南京",value:1},
									{name:"南宁",value:1},
									{name:"无锡",value:0},
									{name:"苏州",value:0},
									{name:"重庆",value:1}
								]
							},
							data:data,
							geoCoord: {
								'上海': [121.4648,31.2891],
								'东莞': [113.8953,22.901],
								'东营': [118.7073,37.5513],
								'中山': [113.4229,22.478],
								'临汾': [111.4783,36.1615],
								'临沂': [118.3118,35.2936],
								'丹东': [124.541,40.4242],
								'丽水': [119.5642,28.1854],
								'乌鲁木齐': [87.9236,43.5883],
								'佛山': [112.8955,23.1097],
								'保定': [115.0488,39.0948],
								'兰州': [103.5901,36.3043],
								'包头': [110.3467,41.4899],
								'北京': [116.4551,40.2539],
								'北海': [109.314,21.6211],
								'南京': [118.8062,31.9208],
								'南宁': [108.479,23.1152],
								'南昌': [116.0046,28.6633],
								'南通': [121.1023,32.1625],
								'厦门': [118.1689,24.6478],
								'台州': [121.1353,28.6688],
								'合肥': [117.29,32.0581],
								'呼和浩特': [111.4124,40.4901],
								'咸阳': [108.4131,34.8706],
								'哈尔滨': [127.9688,45.368],
								'唐山': [118.4766,39.6826],
								'嘉兴': [120.9155,30.6354],
								'大同': [113.7854,39.8035],
								'大连': [122.2229,39.4409],
								'天津': [117.4219,39.4189],
								'太原': [112.3352,37.9413],
								'威海': [121.9482,37.1393],
								'宁波': [121.5967,29.6466],
								'宝鸡': [107.1826,34.3433],
								'宿迁': [118.5535,33.7775],
								'常州': [119.4543,31.5582],
								'广州': [113.5107,23.2196],
								'廊坊': [116.521,39.0509],
								'延安': [109.1052,36.4252],
								'张家口': [115.1477,40.8527],
								'徐州': [117.5208,34.3268],
								'德州': [116.6858,37.2107],
								'惠州': [114.6204,23.1647],
								'成都': [103.9526,30.7617],
								'扬州': [119.4653,32.8162],
								'承德': [117.5757,41.4075],
								'拉萨': [91.1865,30.1465],
								'无锡': [120.3442,31.5527],
								'日照': [119.2786,35.5023],
								'昆明': [102.9199,25.4663],
								'杭州': [119.5313,29.8773],
								'枣庄': [117.323,34.8926],
								'柳州': [109.3799,24.9774],
								'株洲': [113.5327,27.0319],
								'武汉': [114.3896,30.6628],
								'汕头': [117.1692,23.3405],
								'江门': [112.6318,22.1484],
								'沈阳': [123.1238,42.1216],
								'沧州': [116.8286,38.2104],
								'河源': [114.917,23.9722],
								'泉州': [118.3228,25.1147],
								'泰安': [117.0264,36.0516],
								'泰州': [120.0586,32.5525],
								'济南': [117.1582,36.8701],
								'济宁': [116.8286,35.3375],
								'海口': [110.3893,19.8516],
								'淄博': [118.0371,36.6064],
								'淮安': [118.927,33.4039],
								'深圳': [114.5435,22.5439],
								'清远': [112.9175,24.3292],
								'温州': [120.498,27.8119],
								'渭南': [109.7864,35.0299],
								'湖州': [119.8608,30.7782],
								'湘潭': [112.5439,27.7075],
								'滨州': [117.8174,37.4963],
								'潍坊': [119.0918,36.524],
								'烟台': [120.7397,37.5128],
								'玉溪': [101.9312,23.8898],
								'珠海': [113.7305,22.1155],
								'盐城': [120.2234,33.5577],
								'盘锦': [121.9482,41.0449],
								'石家庄': [114.4995,38.1006],
								'福州': [119.4543,25.9222],
								'秦皇岛': [119.2126,40.0232],
								'绍兴': [120.564,29.7565],
								'聊城': [115.9167,36.4032],
								'肇庆': [112.1265,23.5822],
								'舟山': [122.2559,30.2234],
								'苏州': [120.6519,31.3989],
								'莱芜': [117.6526,36.2714],
								'菏泽': [115.6201,35.2057],
								'营口': [122.4316,40.4297],
								'葫芦岛': [120.1575,40.578],
								'衡水': [115.8838,37.7161],
								'衢州': [118.6853,28.8666],
								'西宁': [101.4038,36.8207],
								'西安': [109.1162,34.2004],
								'贵阳': [106.6992,26.7682],
								'连云港': [119.1248,34.552],
								'邢台': [114.8071,37.2821],
								'邯郸': [114.4775,36.535],
								'郑州': [113.4668,34.6234],
								'鄂尔多斯': [108.9734,39.2487],
								'重庆': [107.7539,30.1904],
								'金华': [120.0037,29.1028],
								'铜川': [109.0393,35.1947],
								'银川': [106.3586,38.1775],
								'镇江': [119.4763,31.9702],
								'长春': [125.8154,44.2584],
								'长沙': [113.0823,28.2568],
								'长治': [112.8625,36.4746],
								'阳泉': [113.4778,38.0951],
								'青岛': [120.4651,36.3373],
								'韶关': [113.7964,24.7028]
							}
						}
					]
				}
				myChart.setOption(option);
			}
		})
	}
	myChart.on("click",function(params){
		var name = params.name;
		var mapCode = cityMap[name];
		loadMap(mapCode,name);
	})
	//初始化新世通云平台表格
	var gridOption = {
		processing:true,
		serverSide:false,
		searching:false,
		ordering:false,
		lengthChange:false,
		paging:false,
		info:false,
		ajax:{
			url:"json/cloud.json"
		},
		columns:[
			{"title":"接口状态","data": "status","sWidth":"25%"},
			{"title":"接口名称","data":"name","sWidth":"25%"},
			{"title":"接口URI","data":"uri","sWidth":"35%"},
			{"title":"方法","data":"method","sWidth":"15%"},
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
			}
		],
		drawCallback:function(setting){
			$(".warpper").height($(".right-box").height()-40)
			base.scroll({
				container:$(".warpper")
			});
		}
	}
	var setGrid = function(){
		grid = base.datatables({
			container:$("#tableCloud"),
			option:gridOption
		})
	}
	//点击调整宽度
	var flag = false;
	function ajustWidth(){
		$(".ajustBtn").off().on("click",function(){
			if(!flag){
				$(".left-box").animate({width:'100%'},function(){
					myChart.resize();
				});
			}else{
				$(".left-box").animate({width:'50%'},function(){
					myChart.resize();
				});
			}

			$(".warpper").hide();

		})
	}
	return {
		main:function(){
			ajustWidth();
			//返回上一级
			$("#contextMenu").off().on("click",function(){
				loadMap('100000', 'china');
			});
			//点击天翼云存储平台
			$(".storageClouds").off().on("click",function(){
				var width = ($(".right-box").width()-80)+"px";
				var height = ($(".right-box").height()-80)+"px";
				var html = "<div id='pie' style='width:"+width+";height:"+height+"'></div>";
				$(".warpper").html(html);
				$(".left-box").animate({width:'50%'},"fast","linear",function(){
					myChart.resize();
					//初始化天翼云平台
					require(["app/pieChart"],function(pie){
						var pieChart = echarts.init(document.getElementById('pie'));
						pieChart.setOption(pie)
					})
				});
			})
			//点击新世通云平台
			$(".cloudPlatform").off().on("click",function(){
				var html = "<table class='table table-bordered' id='tableCloud'><caption>新世通接口状态详情</caption></table>";
				$(".warpper").html(html);
				$(".left-box").animate({width:'50%'},"fast","linear",function(){
					myChart.resize();
					setGrid();
				});

			})
		}
	}
})
