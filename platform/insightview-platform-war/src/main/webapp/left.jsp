<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/common/include_css.css" />


<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/style/style_one/js/main.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
</head>
<body>
	<ul class="nav">
		<c:forEach var="sysMenuModuleBean"
			items="${ sessionScope.leftSysMenuModuleLst}">
			<c:if test="${sysMenuModuleBean[2]==2}">
				<li class="firLi" onclick="doShow(${sysMenuModuleBean[0] });"><a
					href="javascript:void(0)">${sysMenuModuleBean[1] }</a></li>
			</c:if>

			<c:if test="${sysMenuModuleBean[2]==3}">
				<ul id="${sysMenuModuleBean[3] }" name="${sysMenuModuleBean[3] }"
					class="hdUl">
					<li class="hdLi"><a
						href="${pageContext.request.contextPath}${sysMenuModuleBean[6] }"
						target="component_2">${sysMenuModuleBean[1] }</a></li>
				</ul>
			</c:if>
		</c:forEach>
	</ul>
</body>

<script>
	//页面大小改变时设置数据展示区
	/*$(window).resize(function() {
	 $(".datas").css("top",$(".conditions").height()+31+5);
	 });
	 */

	$(document).ready(
			function() {

				//页面刚加载时设置数据展示区域
				//$(".datas").css("top",$(".conditions").height()+31+5);

				/*
				 *左侧导航两种手风琴效果
				 */

				
				//滑动效果
				/*
				$(".nav .firLi").mouseover(function(){
					$(this).next(".hdUl").slideDown(500).siblings(".hdUl").slideUp("slow");
				});
				 */

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
	
	 function dorefreshMenu(menuId,jspName) {
			var path = getRootName();

			var uri = path + "/commonLogin/refreshMenu?menuid=" + menuId + "";
			//window.location.href = uri;
			$("#component_1",parent.document.body).attr("src",uri);
			if(jspName != undefined){
				
				$("#component_2",parent.document.body).attr("src",path+jspName);
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
