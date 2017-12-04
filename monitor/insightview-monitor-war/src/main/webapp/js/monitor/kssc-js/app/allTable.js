define(["base","commonApp"],function(base,common){
	//获取menu
	function getMenu(){
		$.ajax({
			url:$.base+"/json/menu.json",
			type:"get",
			success:function(result){
				//将一维数组转换成二维数组
				if(result && result.data && result.data.length>0){
					var arr = transform(result.data);
					base.template({
						container:$(".ui-menu"),
						templateId:"menu-tpl",
						data:arr,
						//helper:[{"name":"show","event":showHelper}],
						callback:function(){
							//渲染页面
							var url = $(".panel-title:eq(0)").attr("url")
							getPage(url)
							clickMenu();
							clickPanelHead();
							base.scroll({
								//container:$(".ui-menubar")
							});
						}
					})
				}
				
			}
		})
	}
	//将一维数组转换成二维数组
	function transform(arr){
		var temp=[];
		$.each(arr,function(index,item){
			if(item.pid=="0"){//说明是父级
				item.items=[];
				$.each(arr,function(subIndex,subItem){
					if(subItem.pid==item.id){//子集的pid与父级的id相同
						item.items.push(subItem)
					}
				})
				temp.push(item)
			}
		})
		return temp;
	}
	function clickMenu(){
		//点击menu
		$(".panel-body ul>li").off().on("click",function(){
			$(".panel-body ul>li").removeClass("active");
			$(this).addClass("active");
			var url = $(this).attr("url");
			getPage(url)
		})
	}
	function clickPanelHead(){
		$(".panel-title").off().on("click",function(){
			var url = $(this).attr("url");
			getPage(url)
		})
	}
	//获取页面
	function getPage(url){
		if(url){
			base.loadPage({
				container:$(".ui-article"),
				url:url,
				callback:function(){
					
				}
			})
		}
	}
	return {
		main:function(){
			getMenu(); 
		}
	}
})
