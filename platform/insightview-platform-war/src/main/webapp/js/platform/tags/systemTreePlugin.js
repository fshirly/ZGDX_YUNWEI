/**
 * Created by Wolf.J on 2015-12-22.
 */
;
(function ($) {
    $.fn.systemTree = function (options) {
        var defaultVal = {dialogOpts: {}, treeOpts: {type: "org"}};

        var idVal = this.attr('id');
        if (!idVal) {
            idVal = new Date() - 0;
            this.attr('id', idVal);
        }

        var dialogOpts = $.extend({}, defaultVal.dialogOpts);
        var treeOpts = $.extend({}, defaultVal.treeOpts);
        if (options && options.dialogOpts) {
            dialogOpts = $.extend(dialogOpts, options.dialogOpts);
        }
        if (options && options.treeOpts) {
            treeOpts = $.extend(treeOpts, options.treeOpts);
        }

        treeOpts.divId = idVal;

        //var html =
        //        '<input type="text" id="##id##Name" name="##id##Name" readonly="readonly" value=""/>' +
        //        '<input type="button" id="##id##Btn" value="选择" />'
        //        + '<input   id="##id##Id" name="##id##Id" readonly="readonly" value="" hidden="hidden" >'
        //        + '<input   id="##id##UserId" name="##id##UserId" readonly="readonly" value="" hidden="hidden" >' ;

        var html = '<span class="textbox easyui-fluid combo" value="" multiple="false" cascadeCheck="false" style="width: 30%">'
            + '<span style="right: 0px;" class="textbox-addon textbox-addon-right">'
            + '<img class="sysimg" id="##id##Btn" src="'+rootPath+'images/platform/mores.png" href="javascript:void(0)"></img>'
            + '</span>'
            + '<input type="text" id="##id##Name" name="##id##Name" readonly="readonly" style="width: 90%" value="" />'
            + '<input id="##id##Id" name="##id##Id" readonly="readonly" value="" hidden="hidden" >'
            + '<input id="##id##UserId" name="##id##UserId" readonly="readonly" value="" hidden="hidden" >'
            + '</span>';

        this.html(html.replace(new RegExp(/(##id##)/g), idVal));
        $('#' + idVal + 'Btn').bind('click', treeOpts, showSelectTree);

        //加载默认值
        doLoadDefaultNames(idVal, treeOpts);
    };

    function showSelectTree(e) {
        e.data.selectedIds = $('#' + e.data.divId + 'Id').val();
        setJsonObj('treeOption', e.data); //在弹出窗口之前设置Json对象,提供给弹出窗口使用
        var title = '选择机构';
        var type = e.data.type;
        if (type == "org") {
            title = "机构树选择";
        } else if (type == "department") {
            title = "部门树选择";
        } else if (type == "post") {
            title = "岗位树选择";
        } else if (type == "user") {
            title = "人员树选择（带岗位）";
        } else if (type == "userNoPost") {
            title = "人员树选择（不带岗位）";
        } else if (type == "role") {
            title = "角色树选择";
        } else if (type == "roleUser") {
            title = "角色人员树选择";
        }
        OpenWin(rootName + "tag/orgmanager/sysOrgTreeEasyUI/html", title, 400, 600);
    }

})(jQuery);


function setTreeSelectedValue(divId, ids, values, userIds) {
    if (divId) {
        $('#' + divId + 'Id').val(ids);
        $('#' + divId + 'Name').val(values);
        $('#' + divId + 'UserId').val(userIds);
    } else {
        console.error('divId:' + divId);
    }
}
function doSetSelectedValue(nodes, divId) {
    if (nodes && divId && nodes.length > 0) {
        var ids = '';
        var values = '';
        var userIds = '';
        for (var i = 0; i < nodes.length; i++) {
            var node = nodes[i];
            ids += "," + node.id;
            values += "," + node.name;
            userIds += "," + node.userId;
        }
        ids = ids.substr(1);
        values = values.substr(1);
        userIds = userIds.substr(1);
        setTreeSelectedValue(divId, ids, values, userIds);
    }
}
function doLoadDefaultNames(divId, treeOpts) {
    var ids = treeOpts.selectedIds;
    if (ids && ids.length > 0) {
        var selectedUrl = '';
        if (treeOpts.type == "org") {
            selectedUrl = rootPath + "/tag/orgmanager/treeOrgsSelectedNodes";
        } else if (treeOpts.type == "department") {
            selectedUrl = rootPath + "/tag/orgmanager/treeDepartmentsSelectedNodes";
        } else if (treeOpts.type == "post") {
            selectedUrl = rootPath + "/tag/orgmanager/treeOrgPostsSelectedNodes";
        } else if (treeOpts.type == "user") {
            selectedUrl = rootPath + "/tag/orgmanager/treeUserPostSelectedNodes";
        } else if (treeOpts.type == "userNoPost") {
            selectedUrl = rootPath + "/tag/orgmanager/treeUserNoPostSelectedNodes";
        } else if (treeOpts.type == "role") {
            selectedUrl = rootPath + "/tag/orgmanager/treeRolesSelectedNodes";
        } else if (treeOpts.type == "roleUser") {
            selectedUrl = rootPath + "/tag/orgmanager/treeRoleUsersSelectedNodes";
        }
        var ajaxParam = {
            url: selectedUrl,
            type: "post",
            contentType: "application/json",
            data: JSON.stringify({pid: "", selectedIds: ids.split(',')}),
            success: function (res) {
                return doSetSelectedValue(res, divId);
            },
            error: function () {
                console.error('doLoadDefaultNames error, url:', selectedUrl);
            }
        };
        $.ajax(ajaxParam);
    }
}
