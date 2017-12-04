package com.fable.insightview.platform.common.util;

public interface SystemFinalValue {

	// 监测对象类型站点
	int MOINTOR_DEFINE_WEBSITE = 90;
	
	// 业务类型
	int BIZ_TYPE = 1;

	// 业务类型:事件管理-故障
	int BIZ_TYPE_EVENT_INCIDENT = 1;

	// 业务类型:事件管理-服务请求
	int BIZ_TYPE_EVENT_SERVICE = 2;

	// 业务类型:问题管理
	int BIZ_TYPE_PROBLEM = 3;

	// 业务类型:变更管理
	int BIZ_TYPE_CHANGE = 4;

	// 业务类型:发布管理
	int BIZ_TYPE_RELEASE = 5;

	// 业务类型:知识管理
	int BIZ_TYPE_KNOWLEDGE = 6;

	//业务类型:告警派单
	int BIZ_TYPE_ALARMSENDORDER = 8;
	
	//业务类型:外包资源服务
	int BIZ_TYPE_OUTSOURCINGSERVICE = 9;
		
	//业务类型:耗材领用
	int BIZ_TYPE_CONSUMABLEAPPLICATION = 10;
	
	// 业务类型:其他
	int BIZ_TYPE_OTHER = 7;

	// 新建故障事件的初始化字段：kind
	byte EVENT_INCIDENT_KIND = 0;

	// 新建故障事件的初始化字段:status
	int EVENT_INCIDENT_STATUS = 0;

	// 新建服务请求事件的初始化字段：kind
	byte EVENT_SERVICE_KIND = 1;

	// 新建服务请求事件的初始化字段：result
	int EVENT_SERVICE_RESULT = 1;

	// 新建服务请求事件的初始化字段:status
	byte EVENT_SERVICE_STATUS = 1;

	// 新建变更的初始化字段:status
	int BIZ_CHANGE_STATUS = 1;

	// 新建变更的初始化字段:status
	int BIZ_RELEASE_STATUS = 1;

	// 资产采用折旧方案采用模式(不采用折旧方案)初始化字段:depreciateMode
	int RESASSET_DEPRECIATEMODE_NO = 0;

	// 资产采用折旧方案采用模式(采用产品目录的折旧方案)初始化字段:depreciateMode
	int RESASSET_DEPRECIATEMODE_RESPRODUCTCATALOG = 1;

	// 资产采用折旧方案采用模式(采用资产本身的折旧方案)初始化字段:depreciateMode
	int RESASSET_DEPRECIATEMODE_SELF = 2;

	// 资产的状态初始化字段：status(在用)
	int RESASSET_STATUS_USE = 1;

	// 资产折旧方法(年限平均法/直线法)
	int RESASSET_DEPRECIATEMODE_WAY_LINE = 0;

	// 资产折旧方法(工作量法)
	int RESASSET_DEPRECIATEMODE_WAY_WORK = 1;

	// 资产折旧方法(双倍余额递减法(加速折旧法))
	int RESASSET_DEPRECIATEMODE_WAY_DOUBLE = 2;

	// 资产折旧方法(年数总和法)
	int RESASSET_DEPRECIATEMODE_WAY_SUM = 3;

	// 资产折旧方法 计算模式(按寿命)
	int RESASSET_DEPRECIATEMODE_MODE_YEARS = 0;

	// 资产折旧方法 计算模式(按百分比)
	int RESASSET_DEPRECIATEMODE_MODE_PERCENT = 1;

	// SESSION_DATA
	String SESSION_DATA = "sysUserInfoBeanOfSession";

	// 配置项类型对应表的前缀
	String CMDB_TABLENAME_PREFIX = "ResPrefix";

	// 配置项类型对应属性初始模式
	int CMDB_ATTRIBUTEINIT_MODE = 1;

	// 配置项类型按资产管理
	int CMDB_AS_ASSET = 1;

	// 配置项不锁定
	int CMDB_RESCI_NOLOCK = 1;

	// 配置项锁定
	int CMDB_RESCI_LOCK = 2;

	// 配置项类型为组件
	int CMDB_RESTYPEDEFINE_COMPONENT = 1;

	// 配置项已定制特殊处理，并添加到应用中
	int CMDB_RESTYPEDEFINE_MODELAPPLYSTATUS = 2;
	
	int CMDB_RESTYPEDEFINE_MODELAPPLYSTATUS_DESK = 3;
	
	// 查询所有监控类型信息的url配置key值
	String CMDB_MONITOR_TYPES_KEY = "rest.monitor.types";

	// 审计项状态待审计
	int RESAUDITLIST_STATUS = 1;

	// 审计任务状态为已完成
	int RESAUDITTASK_STATUS = 3;

	// 审计任务状态为审计中
	int RESAUDITTASK_STATUS_AUDIT = 2;

	// 配置项锁定
	int RESCI_ISLOCK_YES = 2;

	// 配置项不锁定
	int RESCI_ISLOCK_NO = 1;

	// 控件类型 为 富文本编辑框
	String WIDGET_TYPE = "RichTxt";
	
	//控件类型为 附件上传
	String FILE_TYPE = "File";

	// 处理类型 查看处理
	String HANDLE_TYPE = "check";

	// combobox中的数据类型
	String COMBOBOX_SYSTEM = "System";// 系统值
	String COMBOBOX_CUSTOMSELECT = "CustomSelect";// 自定义值
	String COMBOBOX_DICT = "Dict";// 数据字典值
	String COMBOBOX_TREE ="Tree";//下拉树

	// 特征属性的默认排列格式
	int ATTRIBUTE_FORMCOLUMN = 2;

	// 配置项关系变更 表示删除变更
	int RELATION_OPERATE = 2;

	int RELATION_OPERATE_SAVE = 1;

	/************** 业务流水号 ****************/
	// 事件管理
	String EVENT_NO = "EV";

	// 问题管理
	String PROBLEM_NO = "PR";

	// 变更管理
	String CHANGE_NO = "CH";

	// 发布管理
	String RELEASE_NO = "RE";
	
	//配置管理
	String CONFIG_NO = "CO";
	/************** 业务流水号 ****************/

	// 数据库字段常量
	String COL_PREFIX = "COL_";

	// 配置项对应表名
	String RESCI_TABLE_NAME = "ResCi";
	String RESCI_TABLE_ID_NAME = "CiId";
	String RESCI_TABLE_LABLE_NAME = "Name";
	int RESTYPEDEFINE_SPECIALATTRIBUTES_COUNT = 1;

	String DEPARTMENT_TABLENAME = "SysDepartment";
	String USERINFO_TABLENAME = "SysUserInfo";
	String ORGANITION_TABLENAME = "SysOrganization";

	Integer OWN_RELATIONID = 5;
	Integer USEING_RELATIONID = 6;
	Integer MAINTAIN_RELATIONID = 7;

	Integer RELATION_ISDEFAULT = 1;

	// 资产维修 登记费用
	byte MAINTANENCE_ISRESEXPENSE_YSE = 1;

	// 资产维修 不登记费用
	byte MAINTANENCE_ISRESEXPENSE_NO = 0;

	// 上线审核通过
	byte UPLINEAPPRAISAL_RESULT_PASS = 1;

	// 上线审核拒绝
	byte UPLINEAPPRAISAL_RESULT_REFUCE = 2;

	// 资源申请要求新增或修改成功
	Integer ADD_UPDATE_RESREQUIRE_SUCCESS = 0;

	// 资源申请要求新增失败
	Integer ADD_RESQUIRE_FAILURE = 1;

	// 资源申请要求修改失败
	Integer UPDATE_RESQUIRE_FAILURE = 2;

	// 资源申请分配资源changeId
	Integer RES_CHANGEID = 0;

	// 资源申请列表批量删除若审核通过则不能删除提示
	Integer RESAPP_BATCHDEL_FAILURE = 0;
	
	//应用系统下线
	Integer APPLICATIONSYSTEM_OFFLINE = 3;
	
	//关系是否展示
	Integer RESCIRELATION_ISVIEW = 1;
	
	String USED_PEOPLE = "被使用-人员";
	
	String MAINTAIN_PEOPLE = "被维护-人员";
	
	String BELONG_DEPARTMENT = "属于-部门";
	
	Integer USE_PEOPLE = 9;
	
	//上传图片路径key
	String FILE_SERVER_URLKEY = "fileServerURL";
	
	//标签对应表名
	String TAG_TABLENAME = "ResPrefixReader";
	
	Integer DELSUCCESSUSER = 0;
	
	//用户删除状态失败（已被分配任务的用户）
	Integer DELHASWORKUSER = 1;
	
	Integer DELFAILUREUSER = 2;
	
	//应用系统的资源类型id（用于应用系统管理）
	Integer APPLICATIONSYSTEM_RESTYPEID = 20;
	
	//系统初始化信息属性isDeletbale=0不能删除
	int DELETE_UP_SCOPE = 0;
	String DODEL = "DoDel";
	
	/*路由器初始化ID值*/
	Integer ROUTER_TYPE_ID = 11;
	/*交换机初始化ID值*/
	Integer SWITCH_TYPE_ID = 12;
	/*机柜初始化ID值*/
	Integer CABINET_TYPE_ID = 15;	
	
	/**耗材领用状态-待审批*/
	Integer MATERIALAPPLY_STATUS_TOBEAPPROVE = 1;
	
	/**耗材领用状态-待领用*/
	Integer MATERIALAPPLY_STATUS_TOBEAPPLY = 2;
	
	/**耗材领用状态-已领用*/
	Integer MATERIALAPPLY_STATUS_BEPALLYED = 3;
	
	/**耗材领用状态-已拒绝*/
	Integer MATERIALAPPLY_STATUS_BEREFUSED = 4;
	
	/************** 流程ID ****************/
	// 耗材领用
	String ITSM_PROCESSID_CONSUMABLEAPP = "consumableApplication.consumableApplication";
	// 外包服务请求
	String ITSM_PROCESSID_OUTSOURCING = "outsourcingService.outsourcingRequest";
	
	/************** 待办、已办、待认领列表的排序标识 ****************/
	String ITSM_BACKLOG = "Reserved";
	String ITSM_ALREADYHANDLE = "Completed";
	String ITSM_PARTICIPATE = "BeClaimed";
	
	/************** 海安移动运维的两个流程taskName的映射 ****************/
	String ITSM_CONSUMABLEAPP_APPLICATION = "Application";
	String ITSM_CONSUMABLEAPP_APPROVE = "Approve";
	String ITSM_CONSUMABLEAPP_OBTAIN = "Obtain";
	String ITSM_OUTSOURCING_REQUEST = "ServiceRequest";
	String ITSM_OUTSOURCING_DISPOSE = "ClaimAndDispose";
	
	/***********************应用系统上线流程相关begin************************************/
	Integer BIZ_TYPE_DEVICE_TURNOVER_APPLY = 11;
	Integer BIZ_TYPE_DIGITAL_CERTIFICATE_JOINT = 12;
	Integer BIZ_TYPE_DOMAIN_RECORD = 13;
	Integer BIZ_TYPE_ORGANIZER_JOINT = 14;
	Integer BIZ_TYPE_NETWORK_APPLICATION = 101;
	Integer APP_ID_DEVICE_TURNOVER_APPLY = 900;
	Integer APP_ID_DIGITAL_CERTIFICATE_JOINT = 1000;
	Integer APP_ID_DOMAIN_RECORD = 1100;
	Integer APP_ID_ORGANIZER_JOINT = 1200;
	Integer APP_ID_CONFIG_MANAGE = 1300;
	Integer APP_ID_INCIDENT = 200;
	/************************* 发布管理 APPID start *****************************************/
	Integer APP_ID_RELEASE = 500;
	/************************* 发布管理 APPID end ******************************************/
	
	String PROCESS_ID_DEVICE_TURNOVER_APPLY = "deviceTurnoverApply.deviceTurnoverApply";
	String PROCESS_ID_DIGITAL_CERTIFICATE_JOINT = "digitalCertificateJoint.digitalCertificateJoint";
	String PROCESS_ID_DOMAIN_RECORD = "domainRecord.domainRecord";
	String PROCESS_ID_ORGANIZER_JOINT = "organizerJoint.organizerJoint";
//	1-进出机房申请，2-部门领导待审批,3-科信领导待审批,4-设备进出机房办理,5-设备进出机房办理确认,6-已完成,7-部门审批拒绝，8-科信审批拒绝
	Integer AS_DT_APPLY = 1;
	Integer AS_DT_TO_FIRST_AUDIT = 2;
	Integer AS_DT_TO_SECOND_AUDIT = 3;
	Integer AS_DT_TO_ASSIGN = 4;
	Integer AS_DT_TO_CONFIRM = 5;
	Integer AS_DT_END = 6;
	Integer AS_DT_FIRST_REJECT = 7;
	Integer AS_DT_SECOND_REJECT = 8;
	// 下一步处理方式
	String PROC_HANDLE_TYPE_CHOOSE = "choose";
	String PROC_HANDLE_TYPE_SEQUENCE = "sequence";
	// 选择处理人方式
	String PROC_SELECT_OP_TYPE_DEPT = "1";
	String PROC_SELECT_OP_TYPE_USER_GROUP = "2";
	String PROC_SELECT_OP_TYPE_BOTH = "3";
	// 办理人用户群组
	Integer PROC_HANDLER_UG_AUDIT = 4;
	Integer PROC_HANDLER_UG_ASSIGN = 3;
	
	/***********************应用系统上线流程相关end*************************************************/

	/***********************门户系统相关begin***********************************************************/
	String ROOT_NGOMS_ENABLE_KEY = "ngoms.enabled";
	String NGOMS_INSIGTVIEW_SERVER = "ngoms.insigtview.server";
	String NGOMS_INSIGHTIVEW_URI = "ngmos.insightivew.uri";
	String NGOMS_INSIGHTIVEW_URI_BOOTSTRAP = "ngmos.insightivew.uri.bootstrap";
	// MQ消息流程相关队列名称
	String NGOMS_MQ_TASK_QUEUE = "ngoms.mq.task.queue";
	/***********************门户系统相关end*************************************************************/

	/**********************流程定义KEY begin******************/
	// 故障管理
	String PD_KEY_INCIDENT_MANAGEMENT = "WorkFlow.JYIncident.bpmn2";
	// 服务请求
	String PD_KEY_SERVICE_REQUEST = "com.fable.itsm.serviceRequest";
	// 问题管理
	String PD_KEY_PROBLEM_MANAGEMENT = "com.fable.itsm.problemManage";
	// 变更管理
	String PD_KEY_CHANGE_MANAGEMENT = "com.fable.itsm.changeManage";
	// 发布管理
	String PD_KEY_PUBLISHMENT = "com.fable.itsm.releaseManage";
	// 告警派单
	String PD_KEY_ALARM_SEND_ORDER = "alarmSendOrder.alarmSendOrder";
	// 耗材领用
	String PD_KEY_CONSUMABLE_APPLICATION = "consumableApplication.consumableApplication";
	// 外包服务请求
	String PD_KEY_OUTSOURCING_REQUEST = "outsourcingService.outsourcingRequest";
	// 设备进出机房申请
	String PD_KEY_DEVICE_TURNOVER_APPLY = "deviceTurnoverApply.deviceTurnoverApply";
	// 数字证书申请
	String PPD_KEY_DIGITAL_CERTIFICATE_JOINT = "digitalCertificateJoint.digitalCertificateJoint";
	// 域名申请
	String PD_KEY_DOMAIN_RECORD = "domainRecord.domainRecord";
	// 人员组织接入申请
	String PD_KEY_ORGANIZER_JOINT = "organizerJoint.organizerJoint";
	// 入网接入
	String PD_KEY_NETWORK_APPLICATION = "processesForShengTing.NetworkDeviceDredgeApp";
	//配置管理
	String PD_KEY_CONFIG_MANAGE = "configManage.configManage";
	//耗材领用
	String PD_KEY_CONSUMABLE_APPFORRD = "consumableAppForRD.consumableAppForRD";
	// 硬件设备使用申请
	String PD_KEY_HARDWARE_USAGE_APPLICATION = "hardwareUsageApplication.hardwareUsageApplication";
	// 设备进入机房申请
	String PD_KEY_DEVICE_ENTER_ROOM_APPLICATION = "deviceEnterRoomApplication.deviceEnterRoomApplication"; 
	// 地市告警派单申请
	String PD_KEY_WORK_ORDER_APPLICATION = "workOrderApplication.workOrderApplication";
	//告警派单三期申请
	String PD_KEY_ALARMSENDORDER3 = "alarmSendOrder3.alarmSendOrder3";
	/*********************流程定义KEY end*****************************/

	/**********************流程服务接口相关begin**************************/
	String PROCESS_GROUP_TYPE_ASSIGNMENT = "assignment";
	String PROCESS_GROUP_TYPE_SECURITY_ROLE = "security-role";
	/**********************流程服务接口相关end***********************************/

	/**********************用户组相关begin*************************************************/
	String USER_GROUP_CONSUMABLE_APPLICATION = "2";
	/**********************用户组相关end**************************************************/

	/***********************配置管理流程相关begin***********************************************************/
	Integer BIZ_TYPE_CONFIG_MANAGE = 15;
	String PROCESS_CONFIG_TASKNAME_APPLICATION = "ConfigApplication";
	String PROCESS_CONFIG_TASKNAME_DISPOSE = "ConfigDispose";
	String PROCESS_CONFIG_TASKNAME_AUDIT = "ConfigAudit";
	/***********************配置管理流程相关end*************************************************************/
	/***********************耗材领用流程相关--只针对如东 begin***********************************************************/
	int BIZ_TYPE_CONSUMABLEAPPFORRD = 16;
	//流程id
	String ITSM_PROCESSID_CONSUMABLEAPPFORRD = "consumableAppForRD.consumableAppForRD";
	//申请
	String ITSM_CONSUMABLEAPPFORRD_APPLICATION = "Application";
	//办理出库
	String ITSM_CONSUMABLEAPPFORRD_TRANSACTION= "Transaction";
	//耗材领用状态-待提交
	Integer MATERIALAPPLYFORRD_STATUS_APPLICATION = 1;
	//耗材领用状态-待领用
	Integer MATERIALAPPLYFORRD_STATUS_TRANSACTION = 2;
	//耗材领用状态-已领用
	Integer MATERIALAPPLYFORRD_STATUS_BEPALLYED = 3;
	/***********************耗材领用流程相关--只针对如东 end*************************************************************/

	String URI_ENCODING_UTF8 = "UTF-8";
	String URI_ENCODING_ASCII = "ISO-8859-1";
	
	/***********************江苏省厅流程需求相关begin**************************************/
	Integer BIZ_TYPE_ST_NETWORKAPP = 101; // 公安网、互联网接入申请，业务类型
	Integer APP_ID_ST_NETWORKAPP = 1001;
	//科信部门
	Integer DEPTID_NETWORKAPP_TECH_DISPOSE = 11001;
	// 业务流水号规则
	Integer ST_NETWORKAPP_NO_GA = 1;
	Integer ST_NETWORKAPP_NO_HL = 2;
	// 申请当前状态：1-待提交；2-待分配；3-待办理（上门安装）；4-待确认（安装确认）；5-已完成；6-已拒绝
	Integer STATUS_NET_APPLY = 1;
	Integer STATUS_NET_ALLOTIP = 2;
	Integer STATUS_NET_INSTALL = 3;
	Integer STATUS_NET_CONFIRM = 4;
	Integer STATUS_NET_END = 5;
	Integer STATUS_NET_FAILED = 6;
	// 组ID定义，需跟初始化脚本的组ID保持一致
	Integer GROUPID_NETWORKAPP_ALLOTIP = 401; // 科信部门分配IP组ID
	Integer GROUPID_NETWORKAPP_INSTALL = 402; // 上门安装组ID
	//科信办理组
	String GRP_ID_TECH_INFO_DISPOSE = "3";
	// 安全确认组
	String GRP_ID_SECURITY_CONFIRM = "403";
	// 流转节点名称
	String TASK_DEF_KEY_NET_APPLY = "NetworkDredgeApp";
	String TASK_DEF_KEY_NET_ALLOTIP = "AllocationIP";
	String TASK_DEF_KEY_NET_INSTALL = "ImplementInstall";
	String TASK_DEF_KEY_NET_CONFIRM = "ProposerConfirm";
	String TASK_DEF_NET_END = "end1";
	
	// 硬件设备使用申请
	Integer APP_ID_HARDWARE_USAGE_APPLICATION = 1002;
	String PROCESS_ID_HARDWARE_USAGE_APPLICATION = "hardwareUsageApplication.hardwareUsageApplication";
	Integer BIZ_TYPE_HARDWARE_USAGE_APPLICATION = 102;
	// 申请当前状态，1-待提交，2-代科信办理，3-待申请人确认，4-准备中，5-待安装，6-待安全确认，7-已完成
	Integer STATUS_HUA_APPLY = 1;
	Integer STATUS_HUA_DISPOSE = 2;
	Integer STATUS_HUA_PROPOSER_CONFIRM = 3;
	Integer STATUS_HUA_PREPARING = 4;
	Integer STATUS_HUA_INSTALL = 5;
	Integer STATUS_HUA_SECURITY_CONFIRM = 6;
	Integer STATUS_HUA_END = 7;
	// 硬件设备使用业务流水号规则
	Integer ST_HARDWARE_DEVICE_APP = 3;
	// 流转节点名称
	String TASK_DEF_KEY_HUA_APPLY = "OwnDepartmentLeaderApplicate";
	String TASK_DEF_KEY_HUA_DISPOSE = "TechInfoDipose";
	String TASK_DEF_KEY_HUA_PROPOSER_CONFIRM = "ProposerConfirm";
	String TASK_DEF_KEY_HUA_PREPARING = "InstallationPreparing";
	String TASK_DEF_KEY_HUA_INSTALL = "DoorInstall";
	String TASK_DEF_KEY_HUA_SECURITY_CONFIRM = "SecurityConfirm";
	String TASK_DEF_KEY_HUA_END = "end1";
	
	// 设备进入机房start
	Integer APP_ID__DEVICE_ENTER_ROOM_APPLICATION = 1003;
	String PROCESS_ID_DEVICE_ENTER_ROOM_APPLICATION = "deviceEnterRoomApplication.deviceEnterRoomApplication";
	Integer BIZ_TYPE_DEVICE_ENTER_ROOM = 103;
	Integer GROUPID_TYPE_DEV_ENTER_ROOM_INSTALL = 405; // 实施部署组ID
	// 申请状态 1-待提交，2-待办理，3-待确认，4-待上架，5-已完成
	Integer STATUS_DER_APPLY = 1;
	Integer STATUS_DER_DISPOSE = 2;
	Integer STATUS_DER_PROPOSER_CONFIRM = 3;
	Integer STATUS_DER_PUT_AWAY = 4;
	Integer STATUS_DER_END = 5;
	// 流水号规则
	Integer APP_NO_DER = 4;
	// 流转节点名称
	String TASK_DEF_KEY_DER_APPLY = "DeviceEnterRoomRegister";
	String TASK_DEF_KEY_DER_DISPOSE = "TechInfoDispose";
	String TASK_DEF_KEY_DER_PROPOSER_CONFIRM = "ProposerConfirm";
	String TASK_DEF_KEY_DER_PUT_AWAY = "JobImplementation";
	String TASK_DEF_KEY_DER_END = "end1";
	// 设备进入机房end
	
	// 工单流程start
	Integer APP_ID_ALARM_SEND_ORDER = 600;
	Integer APP_ID_WORKORDER_APPLICALITON = 1700;
	String PROCESS_ID_WORKORDER_APPLICATION = "workOrderApplication.workOrderApplication";
	Integer BIZ_TYPE_WORKORDER_APPLICATION = 17;
	// 申请状态 1-待接收，2-待处理，3-已完成
	Integer STATUS_WOA_RECEIVE = 1;
	Integer STATUS_WOA_DISPOSE = 2;
	Integer STATUS_WOA_END = 3;
	
	//告警派单三期start
	Integer APP_ID_ALARMSENDORDER3 = 1800;
	Integer BIZ_TYPE_ALARMSENDORDER3 = 18;
	String  PROCESS_ID_ALARMSENDORDER3 = "alarmSendOrder3.alarmSendOrder3";
	//告警派单三期的状态（与告警派单状态不是一个状态注意！！！且此状态只适用于3期的告警派单，与二期的状态不一致）
	Integer STATUS_ALARMSENDORDER3_FRIST = 1;//待一线处理
	Integer STATUS_ALARMSENDORDER3_SECOND = 2;//待二线处理
	Integer STATUS_ALARMSENDORDER3_EXPERT = 3;//待专家处理
	Integer STATUS_ALARMSENDORDER3_FILE = 4;//待归档
	Integer STATUS_ALARMSENDORDER3_FINISH = 5;//已经办结
	//工单处理级别
	Integer SOLUTERESULTTYPELEVEL1 = 1;//一线处理
	Integer SOLUTERESULTTYPELEVEL2 = 2;//二线处理
	Integer SOLUTERESULTTYPELEVEL3 = 3;//专家处理
	//告警派单三期end
	
	// 告警派单状态
	// 1-已登记，2-处理中，3-已恢复，4-已关闭
	Integer STATUS_AOS_REGISTERED = 1;
	Integer STATUS_AOS_HANDLING = 2;
	Integer STATUS_AOS_RECOVERED = 3;
	Integer STATUS_AOS_CLOSED = 4;
	// 流转节点名称
	String TASK_DEF_KEY_WOA_RECEIVE = "WorkOrderReceive";
	String TASK_DEF_KEY_WOA_DISPOSE = "WorkOrderDispose";
	String TASK_DEF_KEY_WOA_END = "end1";
	// 地市告警派单类型
	Integer WORK_ORDER_TYPE_CITY = 1;
	// 工单流程end
	// 科信流程服务状态
	String SERV_STATUS_PENDING = "pending";
	String SERV_STATUS_HANDLING = "handling";
	String SERV_STATUS_COMPLETED = "completed";
	String SERV_STATUS_ALL = "all";
	// 前端流程定义Key
	String FE_PDK_NETWORK_APPLICATION = "PoliceNetworkDeviceDredgeApp";
	String FE_PDK_DEVICE_ENTER_ROOM = "DeviceEnterRoomReg";
	String FE_PDK_HUA_APPLICATION = "HardwareDeviceApp";
	
	/** 无锡科信业务start **/
	// 计算机、网络维护申请start
	Integer APP_ID_COMP_NET_MAINTENANCE = 1004;
	String PROCESS_ID_COMP_NET_MAINTENANCE = "CompNetMaintenance.CompNetMaintenance";
	Integer BIZ_TYPE_COMP_NET_MAINTENANCE = 104;
	Integer GRPID_TYPE_CNM_TECH_INFO_DISPOSE = 406;
	Integer GRPID_TYPE_CNM_SERVICE_DESK_CONFIRM = 407; // 服务台确认组ID
	// 申请进度 1-待申请登记；2-待服务台确认；3-待科信办理；4-已完成；5-已拒绝
	Integer STATUS_CNM_APPLY = 1;
	Integer STATUS_CNM_SERVICE_DESK_CONFIRM = 2;
	Integer STATUS_CNM_DISPOSE = 3;
	Integer STATUS_CNM_END = 4;
	Integer STATUS_CNM_REJECT = 5;
	// 流水号规则
	Integer APP_NO_CNM = 5;
	// 流转节点名称
	String TASK_DEF_KEY_CNM_APPLY = "IncidentApplication";
	String TASK_DEF_KEY_CNM_SERVICE_DESK_CONFIRM = "ServiceDeskConfirm";
	String TASK_DEF_KEY_CNM_DISPOSE = "TechInfoDispose";
	String TASK_DEF_KEY_CNM_END = "end1";
	// 前端流程定义Key
	String FE_PDK_DEF_CNM_APPLICATION = "CompNetMaintenance";
	// 流程标题
	String PROC_NAME_CNM = "计算机、网络维护申请登记";
	// 表单标题
	String FORM_TITLE_CNM = "计算机、网络维护";
	// 计算机、网络维护申请end
	//入网申请start
	Integer APP_ID_NETWORK_JOINT_APP = 1005;
	String PROCESS_ID_NETWORK_JOINT_APP = "NetworkJointApplication.NetworkJointApplication";
	Integer BIZ_TYPE_NETWORK_JOINT_APP = 105;
	Integer GRPID_TYPE_NJA_TECH_INFO_DISPOSE = 406;
	Integer GRPID_TYPE_NJA_SERVICE_DESK_CONFIRM = 407; // 服务台确认组ID
	// 申请进度 1-待申请登记；2-待服务台确认；3-待科信办理；4-已完成；5-已拒绝
	Integer STATUS_NJA_APPLY = 1;
	Integer STATUS_NJA_SERVICE_DESK_CONFIRM = 2;
	Integer STATUS_NJA_DISPOSE = 3;
	Integer STATUS_NJA_END = 4;
	Integer STATUS_NJA_REJECT = 5;
	// 流水号规则
	Integer APP_NO_NJA = 6;
	// 流转节点名称
	String TASK_DEF_KEY_NJA_APPLY = "NetworkJointApp";
	String TASK_DEF_KEY_NJA_SERVICE_DESK_CONFIRM = "ServiceDeskConfirm";
	String TASK_DEF_KEY_NJA_DISPOSE = "TechInfoDispose";
	String TASK_DEF_KEY_NJA_END = "end1";
	// 前端流程定义Key
	String FE_PDK_NJA_APPLICATION = "NetworkJointApplication";
	// 流程标题
	String PROC_NAME_NJA = "入网申请登记";
	// 表单标题
	String FORM_TITLE_NJA = "入网";
	//服务台确认是否办理
	Integer SERVICE_CONFIRM_IS_HANDLE = 1;
	Integer SERVICE_CONFIRM_NOT_HANDLE = 0;
	
	// 入网申请end
	/** 无锡科信业务end **/
	
	
	/***********************江苏省厅流程需求相关end****************************************/

	/***********************省市联动相关start************************************/
	// 新老告警派单流程切换开关的配置项类型
	Integer SYS_CONFIG_TYPE_PROCESS_WOA = 2002;
	// 是否地市告警派单
	String SCT_PROC_WOA_CITY_WORKORDER_FLAG = "AlarmWorkOrderVersion";
	//省市互动工单状态，1-开始，2-终结
	Integer SCT_PROC_WOA_CITY_WORKORDER_STATUS_START = 1;
	Integer SCT_PROC_WOA_CITY_WORKORDER_STATUS_END = 2;
	//安徽省厅查询url
	String REST_ALARMORDERNSEND_SHENGTING_URL = "rest.alarmOrdernSend.shengting.url";
	//告警派单版本
	Integer FIRST_VERSION_ALARM_SEND_ORDER = 1;
	Integer SECOND_VERSION_ALARM_SEND_ORDER =2;
	Integer THIRD_VERSION_ALARM_SEND_ORDER =3;
	//告警状态
	Integer ALREADY_SIGNED_ALARM = 23;
	/***********************省市联动相关end************************************/
	
	//入网申请人员为外来人员
	Integer FOREIGN_USER_TYPE = 3;
	//外来单位人员入网申请默认密码
	String DEFAULT_FOREIGN_PERSON_PASSWD = "123456";
	//外来人员是否自动锁定
	Integer DEFAULT_FOREIGN_PERSON_ISOUTLOCK = 0;
	//外来人员默认状态
	Integer DEFAULT_FOREIGN_PERSON_STATUS = 1;
	//外来人员默认state
	Integer DEFAULT_FOREIGN_PERSON_STATE = 0;
	//查询用户表现在ID的主键
	String USER_INFO_ID_PK = "SysUserInfoPK";
	//查询用户供应商表主键ID
	String SYS_PROVIDER_INFO_ID_PK = "SysProviderUserPK";
	//查询供应商表主键ID
	String PROVIDER_INFO_ID_PK = "ProviderInfoPK";
	
}
