var dutymanager = dutymanager || {};
dutymanager.configerUser = {
		search : function() {/*查询值班人员信息*/
			var dutys = this.getSelect();
			var userIds = f.map(dutys, function(duty){return duty.value;});
			f.ajax({
				url : getRootName() + '/platform/dutymanager/duty/queryDutyers',
				data : {userName : f('[name="userName"]').val(),deptId : f('#user_department').f_combotree('getValue'), userIds : userIds.join(',')},
				success : function (options) {
					var $sel = f('#selLeft');
					$sel.children('option').remove();
					f.each (options, function (index, option) {
						$sel[0].options.add(new Option(option.name, option.id));
					});
				}
			});
		},
		reset : function() {/*重置查询条件*/
			f('[name="userName"]').val('');
			f('#user_department').val('').f_combotree('setValue','');
		},
		confirm : function () {/*添加值班信息人员*/
			var dutys = this.getSelect();
			var dutyIds = f.map(dutys, function (duty) {return duty.value;}).join(',');
			var dutyNames = f.map(dutys, function (duty) {return duty.label;}).join(',');
			var $selRight = f('#selRight'); 
			if ($selRight.length > 0) {
				var options = $selRight.children('option');
				if (options.length > 1 || options.length === 0) {
					f.messager.alert('提示', '值班班次必须指定值班负责人(且为1人),请检查!', 'info');
					return;
				}
				var dutyers = f('#dutyers').val(dutyNames);
				dutyers.prev().val(dutyIds);
				if (dutys.length >= 3) {
					dutyers.attr('title', dutyNames);
				}
			} else {
				var readyer = f('#readys').val(dutyNames);
				readyer.prev().val(dutyIds);
				if (dutys.length >= 8) {
					readyer.attr('title', dutyNames);
				}
			}
			f('#configer').dialog('close');
		},
		getSelect : function () {/*获取已选择的值班人员信息*/
			var dutys = f('#selmid').children('option'), principal = f('#selRight').children('option');
			f.merge(dutys, principal);
			return dutys;
		}
};
f(function() {
	/*初始化部门树*/
	f('#user_department').f_combotree({
         rootName : '部门',
         height : 200,
         url : getRootName() + '/permissionDepartment/findDeptByOrgId?organizationId=' + f('#current_orgId').val(),
         titleField : 'deptName',
         idField : 'deptId',
         parentIdField : 'parentDeptID'
     });	
	f.fn.LRSelect('selLeft', 'selmid', 'img_L_AllTo_M', 'img_L_To_M', 'img_M_To_L', 'img_M_AllTo_L');
	if (f('#userType').val() != '1') {
		f.fn.LRSelect('selmid', 'selRight', 'ALL', 'img_M_To_R', 'img_R_To_M', 'ALL');
	}
});