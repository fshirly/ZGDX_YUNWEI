<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/icon.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>

<!-- 字体样式  -->
<%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/platform/font/iconfont.css" /> --%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/font/iconfont.css" />
	
<!-- 换肤 -->
<%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/platform/system/theme/css/theme.css" /> --%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/theme/css/theme.css" />

<!-- IE8兼容 -->
<!--[if lt IE 9]>  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/theme/css/theme8.css"/> <![endif]-->
<!--[if lte IE 8]>  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/theme/css/theme8.css"/> <![endif]-->

    
</head>
<body>
	<div id="menu">
		<c:forEach var="menuItem" items="${ sessionScope.leftSysMenuModuleLst}">
			<div id="${menuItem.menuId}" title="${menuItem.menuName }" navigationBar="${menuItem.navigationBar }" data-options="iconCls:'${menuItem.icon}'" >
        		<ul id="subMenu_${menuItem.menuId}"></ul>
        	</div>
		</c:forEach>
	</div>
</body>

<script>
	$(document).ready(
			function() {
				$('#menu').accordion({
					fit:true,
					onSelect:function(t,i)
					{
						var $menu = $(this).accordion('getPanel',i);
						var subMenuId = $menu.attr('id');
						var navigationBar = $menu.attr('navigationBar');
						var $treeMenu = $('#subMenu_' + subMenuId);
						//通过检查树是否已经生成来控制菜单树只被加载一次，如果已经
						//生成就不会重新加载
						if (!$treeMenu.hasClass('tree'))
						{
							$treeMenu.tree({
								method:'get',
								url:getRootName() + '/commonLogin/subMenu?parentMenuId=' + subMenuId + "&navigationBar=" + encodeURI(navigationBar),
								onClick:function(node)
								{
									var url = getRootName() + node.attributes.url;
									if("/room3dController/create3dRoom" == node.linkURL){
										window.open(url, '机房3D视图编辑器');
									}else{
										url = url + (url.indexOf('?') == -1 ? '?' : '&') + 'navigationBar=' + encodeURI(node.navigationBar);

										window.parent.$('#component_2').attr('src',url);
										window.parent.$('#component_2').attr("descr", node.descr);
									}
								},
								onLoadSuccess:function(node,data)
								{
									//debugger
//									$(".accordion-body").each(function (index,item) {
//										var height = $(item).children().height();
//										$(item).css("height",height);
//									})
									//第一个主菜单项的子菜单树加载的时候默认选中第一个子菜单
									if (data.length > 0)
									{
										var treeNode = $treeMenu.tree('find',data[0].id);
										$(treeNode.target).click();
										load = false;
									}
								}
							});
						} else {
							var firstMenu = $treeMenu.tree('getChildren', $treeMenu)[0];
							if(firstMenu) {
								$(firstMenu.target).click();
							}
						}
					}
				});
				customMainMenuIcon();
				//左侧导航菜单选中效果
				$(".hdUl a ").click(
					function() {
						//移除其他选中状态
						$(".hdUl a").removeClass("visited_left").css(
								"text-decoration", "none");
						//改变选中后状态
						$(this).addClass("visited_left");
					});

			});
	
		//使用自定义图片
		function customMainMenuIcon()
		{
			$.each($('#menu').accordion('panels'),function(){
				/*
				if ($(this).attr('icon'))
				{
					var icon = $('<div>').addClass('panel-icon');
					icon.css('background','url("' +getRootName() + $(this).attr('icon') + '")');
					icon.insertAfter($(this).parent().find('.panel-title'));
					$(this).parent().find('.panel-icon.icon-ok').remove();
				}
				*/
				//改变字体样式
				$(this).parent().find('.panel-title').css('font-weight','normal');
			});
		}
	
		//自定义节点图标
		function treeIcon(treeId,data)
		{
			for (var i = 0;i<data.length;i++)
			{
				var tnode = data[i];
				if (tnode.attributes.icon)
				{
					var treeNode = $('#' + treeId).tree('find',tnode.id);
					var $tIcon = $(treeNode.target).find('.tree-icon');
					$tIcon.css('background','url("' + getRootName() + tnode.attributes.icon + '")');
				}
				if (tnode.children)
				{
					treeIcon(treeId,tnode.children);
				}
			}
		}
		function doShow(parentMenuId) {
			
			var temps = $("[name=" + parentMenuId + "]");

			for (var i = 0; i < temps.length; i++) {
				var temp = temps[i];
				var now = temp.style.display;
				if (now == "block") {
					temp.style.display = "none";
				} else if (now == "none" || now == "") {
					temp.style.display = "block";
				}
			}
		}
</script>
</html>
