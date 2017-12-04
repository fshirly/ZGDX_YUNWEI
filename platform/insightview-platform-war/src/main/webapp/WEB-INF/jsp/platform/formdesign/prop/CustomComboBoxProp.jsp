<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
    <div id="divPopPage">
        <table id="tblAddInputAttr" class="formtable1">
            <tr>
                <td class="title">属性名称：</td>
                <td><input id="attributesLabel" class="element"
                    elementType="normal" name="attributesLabel"
                    validator="{'default':'*'}"
                    value="${attribute.attributesLabel}" /><b>*</b></td>
            </tr>
            <tr>
                <td class="title">显示在列表页：</td>
                <td><input id="isTableDisplay"
                    value="${attribute.isTableDisplay }"
                    class="easyui-combobox element" elementType="combobox"
                    style="width: 182px" editable="false" name="isTableDisplay" /></td>
            </tr>
            <tr>
			  <td class="title">所跨列数：</td>
			  <td ><input id="columnNum" name="columnNum" value="${attribute.columnNum}" class="easyui-combobox element" elementType="combobox" style="width:182px" editable="false"></td>
			</tr>
            <tr>
                <td class="title">是否必填：</td>
                <td><input id="required" value="${attribute.required }"
                    class="easyui-combobox element" elementType="combobox"
                    style="width: 182px" editable="false" name="required" /></td>
            </tr>
            <tr id="trHandInput">
                <td class="title">自定义单选框名称：</td>
                <td colspan="2"><input id="iptPzTpVal" /> <a
                    href="javascript:void(0);" id="addOption"
                    style="display: inline-block; text-align: center; color: #ffffff; border: 1px solid; border-color: #5cb8e6 #297ca6 #297ca6 #5cb8e6; background: #36a3d9; min-width: 80px; height: 24px; line-height: 24px; margin: 7px 9px 0 0;">添加到名称列表</a>
                    <br /> <select id="customInitValue" size="6"
                    validator="{'default':'*'}" style="height: 75px;">
                </select> <a href="javascript:void(0);" id="removeOption"
                    style="display: inline-block; text-align: center; color: #ffffff; border: 1px solid; border-color: #5cb8e6 #297ca6 #297ca6 #5cb8e6; background: #36a3d9; min-width: 80px; height: 24px; line-height: 24px; margin: 7px 9px 0 0;">&nbsp;&nbsp;移&nbsp;&nbsp;除&nbsp;&nbsp;</a>
                </td>
            </tr>
        </table>
    </div>
    <script type="text/javascript">
        $(function() {
            /* var customInitValues = JSON.parse('${attribute.customInitValues}');
            $.each(customInitValues, function() {
                var id = this.id;
                var label = this.text;
                $("#customInitValue").append(
                        "<option value='" + id + "'>" + label + "</option>");
            }); */

            $('#addOption').click(function() {
                var inputValue = $('#iptPzTpVal').val();
                if (inputValue.length > 30) {
                    var ErrorMsg = "精选列表选项值不能超过30个字符！";
                    $.messager.alert("提示", ErrorMsg, "error");
                } else {
                    doAddTp();
                }
            });

            $('#removeOption').click(function() {
                                var checkIndex = $("#customInitValue").get(0).value; // 获取Select选择的索引值
                                $("#customInitValue option[value='"+ checkIndex + "']").remove(); // 删除Select中索引值为0的Option(第一个)
                            });

            function doAddTp() {
                var lastOption = $('#customInitValue option:last');
                var lastValue = lastOption.attr('value');
                if (!lastValue) {
                    lastValue = 1;
                } else {
                    lastValue++;
                }
                var pzTpVal = $("#iptPzTpVal").val();
                $("#customInitValue").append(
                        "<option value='" + lastValue + "'>" + pzTpVal
                                + "</option>"); // 为Select追加一个Option(下拉项)
                $("#iptPzTpVal").val('');
            }

            function checkOptionLength() {
                var flag = true;
                var OptionLength = document.getElementById('customInitValue').options.length;
                if (OptionLength <= 0) {
                    $.messager.alert("提示", "精选列表至少要添加一项!", "error");
                    flag = false;
                }
                return flag;
            }
        });
    </script>
</body>
</html>