<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix='security'
	uri='http://www.springframework.org/security/tags'%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
	<meta http-equiv="Cache-Control" content="no-store" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<meta http-equiv="Content-Language" content="zh-cn" />
	<title>KSCC运维</title> <!-- mainframe -->
	<link rel="shortcut icon" href="${sessionScope.filePath}${ sessionScope.LogoIcon}" />
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/base64.js"></script>
	<!-- easyui -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/default/easyui.css"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery.easyui.extend.js"></script>
	<!-- my ui -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/layout.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/notify-popwin.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/icon.css"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>
	<!-- dTree -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/fui/plugin/fui-tree.min.js"></script>
	<script type="text/javascript"   src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
	
	<!-- 消息推送 -->
	<script type="text/javascript"   src="${pageContext.request.contextPath}/js/common/jquery.atmosphere.js"></script>
	<script type="text/javascript"   src="${pageContext.request.contextPath}/js/common/notify-popwin.js"></script>
	<script type="text/javascript"   src="${pageContext.request.contextPath}/js/common/atmosphere.js"></script>
	
	<!-- 字体样式  -->
<%-- 	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/platform/font/iconfont.css" /> --%>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/font/iconfont.css" />
	
	<!-- 换肤 -->
    <%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/platform/system/theme/css/theme.css" /> --%>
     <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/theme/css/theme.css" />
	<style>
		.logo{
			background: none !important;
			margin: 5px 100px 0 10px!important;
		}
	</style>
</head>
<body class="index-body">
	<div class="container">

		<!-- begin .header -->
		<div class="header">
			<div id ="headLogo" class="logo">
				<%--<input type="hidden" id="img_path" value="${sessionScope.ProductSmallIcon}" />--%>
				<%--<input type="hidden" id="file_path" value="${sessionScope.filePath}" />--%>
				<img src="${pageContext.request.contextPath}/style/images/kssc-img/karlLogo.png">
			</div>
				
			<!-- begin .headerMenu -->
			<div class="headerMenu">
				<input type="hidden" id="firstMenuId" value="${sessionScope.sysMenuModuleLst[sessionScope.sysMenuModuleLst.size()-1].menuId}" />
				<input type="hidden" id="firstMenuName" value="${sessionScope.sysMenuModuleLst[sessionScope.sysMenuModuleLst.size()-1].menuName}" />
				<%--<input type="hidden" id="hId" value="${sessionScope.sysMenuModuleLst[sessionScope.sysMenuModuleLst.size()-1].menuName}" />--%>
				<ul>
				   <%--  <li class="personalCenter">
					  <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true" flag="0"><i class="icon iconfont icon-wode"></i>个人中心<i class="icon iconfont">&#xe65a;</i></a>
					  <ul class="dropdown-menu">
					    	<li class=""><a href="javascript:Main.tools.resumePop('${ sessionScope.sysUserInfoBeanOfSession.id }');" class="resume">我的</a></li>
					    	<li class=""><a href="javascript:Main.tools.changePwd('${ sessionScope.sysUserInfoBeanOfSession.id }');" class="looked">修改密码</a></li>
					    	<li class=""><a href="${pageContext.request.contextPath}/j_spring_security_logout;" class="exit">退出</a></li>
					  </ul>
					</li> --%>
					<c:forEach var="sysMenuModuleBean"
						items="${ sessionScope.sysMenuModuleLst}">
						<li><a href="${sysMenuModuleBean.linkURL}"><i class="icon iconfont ${sysMenuModuleBean.icon}"></i>${sysMenuModuleBean.menuName} </a></li>
					</c:forEach>
				</ul>
			</div>
			<!-- end .headerMenu -->
			
			<!-- begin .userMsg -->
			<%-- <div class="userMsg">
				<div class="msg">您好！<!-- <input type="hidden" value="${ sessionScope.sysUserInfoBeanOfSession.userType }" id="userTypeId" />
					<span id="userType"></span>， -->${ sessionScope.sysUserInfoBeanOfSession.userName }
					[<c:forEach var="sysUserGroupBean" items="${ sessionScope.sysUserGroupLstOfSession}">${sysUserGroupBean}</c:forEach>]
					<span id="dateArea"></span>
				</div>
				<div class="tools">
					<!-- <a href="#" class="set">设置</a> -->
					<a href="javascript:Main.tools.resumePop('${ sessionScope.sysUserInfoBeanOfSession.id }');" class="resume">个人中心</a>
					<a href="javascript:Main.tools.changePwd('${ sessionScope.sysUserInfoBeanOfSession.id }');" class="looked">修改密码</a>
					<a href="${pageContext.request.contextPath}/j_spring_security_logout;" class="exit">退出</a>
				</div>
			</div> --%>
			<!-- end .userMsg -->

		</div>
		<!-- end .header -->

		<!-- begin .main -->
		<div class="main">
		
			<!-- begin .sidebar 左侧iframe-->
			<div class="sidebar" id="frmTitle" style="display:none">
				<iframe id="component_1" name="component_1" src="" frameborder="0"> </iframe>
			</div>
			<!-- end .sidebar -->

			<!-- begin .picBox -->
			<div class="picBox" id="switchPoint" style="display:none" onclick="javascript:doHs();"></div>
			<!-- end .picBox -->

			<!-- begin .content 右侧iframe-->
			<div class="content">
				<c:choose>
					<c:when test="${sessionScope.sysMenuModuleLst[sessionScope.sysMenuModuleLst.size()-1].menuId == 301}">
						<iframe id="component_2" name="component_2" src="${pageContext.request.contextPath}/dashboard/toDashboardList" frameborder="0"></iframe>
					</c:when>
					<c:otherwise>
						<iframe id="component_2" name="component_2" frameborder="0"></iframe>
					</c:otherwise>
				</c:choose>
			</div>
			<!-- end .content -->

		</div>
		<!-- end .main -->

		<%--<div class="footer">版权所有&nbsp;&copy;&nbsp;&nbsp;<c:if test="${ sessionScope.CopyRight eq '' }">2013-2014&nbsp;&nbsp;&nbsp;江苏飞搏软件技术有限公司</c:if>${ sessionScope.CopyRight}&nbsp;&nbsp;${ sessionScope.Version}</div>--%>

	</div>
	
	<div id="content" class="notify-content"></div>
	
	<div id="popWin" class="popWin"></div>
	<div id="popWin2" class="popWin2"></div>
	<div id="popWin3" class="popWin3"></div>
	<div id="popWin4" class="popWin4"></div>
</body>
<script>
	function loginOut() {
		var path = getRootName();
		var uri = path + "/commonLogin/loginOut";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"t" : Math.random()
			},
			error : function() {
				alert("ajax_error");
			},
			success : function(data) {
				window.location.href = path+"/login.jsp"
			}
		};
		ajax_(ajax_param);
	}

	var _loginCount = 0;
	//刚登录进主页面隐藏左侧导航和收缩区域
	function justLoginIn() {
		if (0 == _loginCount) {
			++_loginCount;
			$('#frmTitle').css("display", "block");
			$('#switchPoint').css("display", "block");
			$('.content').css("left", "200px");
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
	//页面大小改变时设置数据展示区
	/*$(window).resize(function() {
	 $(".datas").css("top",$(".conditions").height()+31+5);
	 });
	 */
	var status = 1;
	function doHs() {
		if (1 == window.status) {
			window.status = 0;
			$("#switchPoint")
					.css("background-image",
							"url(${pageContext.request.contextPath}/style/images/left.png)");
			$("#frmTitle").animate({
				width : 'hide'
			}, "slow");
			$("#switchPoint").animate({
				left : '1px'
			}, "slow");
			$(".content").animate({
				left : '10px'
			}, "slow");

		} else {
			window.status = 1;
			$("#switchPoint")
					.css("background-image",
							"url(${pageContext.request.contextPath}/style/images/right.png)");
			$("#frmTitle").animate({
				width : 'show'
			}, "slow");
			$("#switchPoint").animate({
				left : '191px'
			}, "slow");
			$(".content").animate({
				left : '200px'
			}, "slow");
		}
	}
	function dorefreshMenu(menuId, menuName) {
		var path = getRootName();
		var uri = path + "/commonLogin/refreshMenu?menuid=" + menuId + "&menuName="+encodeURI(menuName);
		//window.location.location = uri;
		if(menuId == "301"){
			var	url = getRootName() + '/commonLogin/toMain';
		    window.location.href = url;
		}
		else if(menuId=="10030"){
            loginOut();
		}
		else{
		    $("#component_1").attr("src", uri);
		    justLoginIn();
		}
	}

	$(document).ready(
			function() {
				switch ($('#userTypeId').val()) {
				case '0':
					$('#userType').html("系统管理员用户 ");
					break;
				case '1':
					$('#userType').html("企业内IT部门用户 ");
					break;
				case '2':
					$('#userType').html("企业业务部门用户");
					break;
				case '3':
					$('#userType').html("外部供应商用户");
					break;
				}
				//头部导航菜单选中效果
				$(".headerMenu li ").click(
						function() {
							//移除其他选中状态
							$(".headerMenu li").removeClass("visited_top").css("color",
									"#d1f0ff");
							//改变选中后状态
							$(this).addClass("visited_top").css("color",
									"#d1f0ff");
						});
						
			var imgPath =$("#img_path").val();
			var path = getRootName();
			var filepath = $("#file_path").val();
			if(imgPath && imgPath!=""){
				var newPath = filepath+imgPath;
				var img = $("<img src='"+ newPath +"'  onerror='this.src=\""+path+"/style/images/kssc-img/karlLogo.png\"'/>");
//				$("#headLogo").css("background","none");
				$("#headLogo").append(img);
				
			} 
			});
			function selectFirstMenu() {
				var menuId = $("#firstMenuId").val();
				var menuName = $("#firstMenuName").val();
				if(menuId != 301){
					dorefreshMenu(menuId, menuName);
				}
			}
			
			function modalShow(){
				$("a.dropdown-toggle").unbind().on("click",function(){
					if($(this).attr("flag") == "0"){
						$("ul.dropdown-menu").show();
						$(this).attr("flag","1");
					}else{
						$("ul.dropdown-menu").hide();
						$(this).attr("flag","0");
					}
				});
			}  
			
	        $("*").click(function (event) {
	            if (!$(this).hasClass("personalCenter")&&!$(this).hasClass("dropdown-toggle") ){
	            	$("ul.dropdown-menu").hide();
					$(this).attr("flag","0");
	            }else{
	            	event.stopPropagation(); //阻止事件冒泡  
	            }
	              
	        });
	        
			function modalHide(){
				$("ul.dropdown-menu").hide();
				$(this).attr("flag","0");
			}
			window.onload=selectFirstMenu;//不要括号
			window.onload=modalShow;
</script>
</html>