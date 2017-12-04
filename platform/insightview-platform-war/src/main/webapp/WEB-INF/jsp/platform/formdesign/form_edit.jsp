<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta content="text/html; charset=UTF-8" http-equiv="content-type">
</head>

<body>
	<input type="hidden" id="form_table" value="${form.formTable }"/>
	<input type="hidden" id="id" value="${formData.id }"/>
	<input type="hidden" id="formId" value="${form.id }"/>
	 <table class="table_style">
	   <!-- 根据属性循环绘制控件 -->                           
        <c:forEach items="${attributes}" varStatus="current" var="attribute">
	        <!-- tr 开始-->
	        <c:if test="${current.index % form.formLayout eq 0 } ">
			  <tr>
			</c:if>
		    <td  align="center" style="height:35px;">
				<span style="display:inline" class="imgSpan">
	             <div class="div_left"> 
		             <c:set var="columnName" value="${attribute.columnName }"/>
		             <c:set var="lowercaseColumnName" value="${fn:toLowerCase(columnName)}"/>
		             <!-- 访问render返回控件模版来绘制单个控件 -->
			         <jsp:include  page="/platform/form/prop/edit/render" flush="true">
			        	<jsp:param value="${attribute.id }" name="id"/>
			        	<jsp:param value="${attribute.editUrl }" name="editView"/>
			        	<jsp:param value="${form.id }" name="formId"/>
			        	<jsp:param value="${formData[lowercaseColumnName] }" name="value"/>
			        </jsp:include>
				</div>
				</span>
			</td>
								
			<!-- tr 关闭-->
			<c:if test="${form.formLayout eq 1 }">
			</tr>
			</c:if>
	        <c:if test="${current.index % form.formLayout eq 1 }">
	         <input type="hidden" id="tdCount" value="${form.formLayout }"/>
	            </tr>      
	        </c:if>
       </c:forEach>
     </table>
     <script>
      $(document).ready(function() {
	      $(".panel.window").css("z-index","9000");
	      $(".window-shadow").css("z-index","8998");
	      $(".window-mask").css("z-index","8999"); 
    }); 
     </script>
</body>

</html>