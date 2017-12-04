<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file = "../../common/pageincluded.jsp" %>
 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
    </head>
    <body>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/datadumpconfig/dataDump_modify.js">
        </script>
        <%        String id =(String)request.getAttribute("id"); %>
        <div id ="divEditDump">
            <input id="id" value="<%= id%>" type="hidden" />
            <table id="tblEditDump" class="formtable1">
                <tr>
                    <td class="title">
                        表名：
                    </td>
                    <td>
                        <input id="ipt_tableName" class="input" validator="{'default':'*','length':'1-200'}" /><b>*</b>
                    </td>
                </tr>
                <tr>
                    <td class="title">
                        时间列名：
                    </td>
                    <td>
                        <input id="ipt_timeColumnName" class="input" validator="{'default':'*','length':'1-200'}" /><b>*</b>
                    </td>
                </tr>
            </table>
            <div class="conditionsBtn">
                <input class="buttonB" type="button" value="确定" onclick="javascript:toUpdate();"/><input class="buttonB" type="button" value="取消" onclick="javascript:$('#popWin').window('close');"/>
            </div>
        </div>
    </body>
</html>