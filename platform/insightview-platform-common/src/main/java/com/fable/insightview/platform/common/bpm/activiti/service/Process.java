package com.fable.insightview.platform.common.bpm.activiti.service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Base64;

import com.fable.insightview.platform.common.bpm.activiti.entity.BpmnModel;
import com.fable.insightview.platform.common.bpm.activiti.mgr.BpmRestManagementClient;
import com.fable.insightview.platform.common.bpm.activiti.mgr.ConstantsBpm;
import com.fable.insightview.platform.common.bpm.activiti.mgr.EnumActivitiRestType;
import com.fable.insightview.platform.common.bpm.activiti.model.DefinitionsRS;
import com.fable.insightview.platform.common.bpm.activiti.model.IdentityLinkRS;
import com.fable.insightview.platform.common.bpm.activiti.model.InstancesRS;
import com.fable.insightview.platform.common.bpm.activiti.model.ProcessDefinitionInstancesRS;
import com.fable.insightview.platform.common.bpm.activiti.model.ProcessDefinitionsRS;
import com.fable.insightview.platform.common.bpm.activiti.model.TaskNodeRS;
import com.fable.insightview.platform.common.bpm.activiti.model.TaskUserRS;
import com.fable.insightview.platform.common.bpm.activiti.model.VariableRS;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * 流程业务接口
 * @author xue.antai
 *
 */
public class Process extends BpmRestManagementClient{
	
	private Logger logger = LoggerFactory.getLogger(Process.class);
	
	public static final String IDENTITY_LINK_TYPE_ASSIGNEE = "assignee";
	public static final String IDENTITY_LINK_TYPE_CANDIDATE = "candidate";
	public static final String IDENTITY_LINK_TYPE_OWNER = "owner";
	public static final String IDENTITY_LINK_TYPE_STARTER = "starter";
	public static final String IDENTITY_LINK_TYPE_PARTICIPANT = "participant";
	
	/********************用户组类型begin*****************************************/
	public static final String ID_GROUP_TYPE_ASSIGNMENT = "assignment";
	public static final String ID_GROUP_TYPE_SECURITY_ROLE = "security-role";
	/********************用户组类型end*******************************************/
	
	/********************列表分页查询begin***********************************************/
	public static final Integer PAGE_DEFAULT_START = 0;
	public static final Integer PAGE_SIZE_MAX_VALUE = 20000;
	/********************列表分页查询end************************************************/
	
	private static Process self;	
	private Integer localUserId;
	
    public static Process instance(){
        if(null == self){
        	try {
				self = new Process();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return self;        
    }
    
    public Process() throws IOException {
    	super(1);
	}
    
    public Process(Integer localUserId) throws IOException {
    	super(localUserId);
	}
    
	/**
	 * 获取流程定义列表
	 * @return
	 * @throws Exception
	 */
    public ProcessDefinitionsRS getDefinitions()throws Exception{
    	ProcessDefinitionsRS proc = null;
    	String url = super.urlForm("repository.management.definitions");
    	logger.info("url:" + url);
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.GET);
    	
        Gson gson = new Gson();
        proc = gson.fromJson(resp, ProcessDefinitionsRS.class);
    	return proc;
    }
    
    /**
     * 根据ID获取流程定义
     * @param pdid 流程定义ID
     * @return
     * @throws Exception
     */
    public DefinitionsRS getProcessDefinition(String pdid) throws Exception {
    	DefinitionsRS defRs = null;
    	String url = super.urlForm("repository.management.definition", pdid);
    	logger.info("url:" + url);
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.GET);
    	Gson gson = new Gson();
    	defRs = gson.fromJson(resp, DefinitionsRS.class);
    	return defRs;
    }
    
    /**
     * 根据流程Key返回相应的流程定义
     * @param pdKey
     * @param isLatest 是否返回最新的流程定义
     * @return
     * @throws Exception
     */
    public ProcessDefinitionsRS getProcessDefinitionByKey(String pdKey, boolean isLatest) throws Exception {
    	ProcessDefinitionsRS proc = null;
    	String url = super.urlForm("repository.management.definitions.pdkey", pdKey, String.valueOf(isLatest));
    	logger.info("url:" + url);
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.GET);
    	
        Gson gson = new Gson();
        proc = gson.fromJson(resp, ProcessDefinitionsRS.class);
    	return proc;
    }
    
    
    /**
     * 根据流程定义ID所有当前活动的流程实例
     * 
     * @param definitionsRS
     * @return
     * @throws Exception
     */
    public ProcessDefinitionInstancesRS getProcessIntancesByDefinition(DefinitionsRS definitionsRS)throws Exception{
    	ProcessDefinitionInstancesRS pi = null;
    	String url = super.urlForm("repository.management.instances",definitionsRS.getId());  
    	logger.info("url:" + url);
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.GET);
    	Gson gson = new Gson();
    	pi = gson.fromJson(resp, ProcessDefinitionInstancesRS.class);
    	return pi;
    }
    
    /**
     * 根据流程实例ID获取流程实例
     * @param piid
     * @return
     * @throws Exception
     */
    public InstancesRS getProcessInstance(String piid) throws Exception {
    	InstancesRS  instRs = null;
    	String url = super.urlForm("process.rumtime.instance", piid);
    	logger.info("url:" + url);
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.GET);
    	Gson gson = new Gson();
    	instRs = gson.fromJson(resp, InstancesRS.class);
    	return instRs;
    }
    
    /**
     * 根据实例ID结束流程实例
     * @param instanceId
     * @return 
     * @throws Exception
     */
    public String endInstance(String instanceId)throws Exception{
    	String url = super.urlForm("process.management.end",instanceId); 
    	logger.info("url:" + url);
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.DELETE);
    	return resp;
    }
    
    /**
     * 根据实例ID删除活动的流程实例或流程历史
     * @param instanceId
     * @return
     * @throws Exception
     */
    public String deleteInstance(String instanceId) throws Exception {
    	String url = super.urlForm("process.management.delete", instanceId);
    	logger.info("url:" + url);
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.DELETE);
    	return resp;
    }
    
    /**
     * 更新任务
     * @param taskId
     * @param params 更新内容
     * @return 
     * @throws Exception
     */
    public String updateTask(String taskId, Map<String, Object> params) throws Exception {
    	if(null==params) {
    		params = new HashMap<String, Object>();
    	}
    	String url = super.urlForm("runtime.tasks.update", taskId);
    	logger.info("url:" + url);
    	logger.info("requestBody:" + new Gson().toJson(params));
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.PUT, params);
    	return resp;
    }
    
    /**
     * 事件触发(signalEvent)
     *
     */
    @Deprecated
    public void signalEvent(String type, Map<String, Object> event)throws Exception {
    	// TODO unsported
    	String url = super.urlForm("fable.process.signalEvent", type, JsonUtil.map2Json(event)); 
    	logger.info("url:" + url);
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.MULTIPART, event);
    }
    
    /**
     * 通知信号事件发生
     * @param signalName 信号名称 
     * @param tenantId 信号事件应该执行在的tenantId
     * @param async 如果为 true，处理信号应该是异步的。<br>
     * 返回码为 202 - Accepted 表示请求已接受，但尚未执行。<br>
     * 如果为 false，会立即处理信号，结果为 (200 - OK) 会在成功完成后返回。如果忽略，默认为 false 。<br>	
     * @param variables 额外变量，只有async为true时生效
     * @return 
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
	public String signalEvent(String signalName, String tenantId, Boolean async, Map<String, Object> variables) throws Exception {
    	String url = super.urlForm("process.runtime.signals");
    	async = async==null?false:async;
    	if(!StringUtils.isNotEmpty(signalName)) {
    		throw new IllegalArgumentException("singal name cannot be null");
    	}
    	Map<String, Object> params = new HashMap<String, Object>();
    	if(!async) {
			List vars = super.getDataParam(variables);
    		params.put("variables", vars);
    	}
    	params.put("async", async);
    	params.put("signalName", signalName);
    	if(StringUtils.isNotEmpty(tenantId)) {
    		params.put("tenantId", tenantId);
    	}
    	logger.info("url:" + url);
    	logger.info("requestBody:" + new Gson().toJson(params));
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.POST, params);
    	return resp;
    }
        
    /**
     * 发起流程
     * @param definitionKey 流程定义Key
     * @return
     * @throws Exception
     */
    public InstancesRS startInstance(String definitionKey) throws Exception {
    	InstancesRS inst = this.startInstance(definitionKey, null, null);
    	return inst;
    }
    
    /**
     * 发起流程
     * @param definitionKey 流程定义Key
     * @param businessId 业务ID
     * @return
     * @throws Exception
     */
    public InstancesRS startInstance(String definitionKey, Integer businessId) throws Exception {
    	InstancesRS inst = this.startInstance(definitionKey, businessId, null);
    	return inst;
    }
    
    /**
     * 发起流程
     * @param definitionKey 流程定义Key
     * @param params 额外参数
     * @return
     * @throws Exception
     */
    public InstancesRS startInstance(String definitionKey, Map<String, Object> params) throws Exception {
    	InstancesRS inst = this.startInstance(definitionKey, null, params);
    	return inst;
    }
    
    /**
     * 发起流程
     * @param definitionKey 流程定义Key
     * @param businessId 业务ID
     * @param params 额外参数
     * @return
     * @throws Exception
     */
	@SuppressWarnings("rawtypes")
	public InstancesRS startInstance(String definitionKey, Integer businessId, Map<String, Object> params)throws Exception{
    	InstancesRS inst = null;
    	String url = super.urlForm("runtime.process-instances.startInstance");
    	HashMap<String, Object> hm = new HashMap<String, Object>();
//    	hm.put("processDefinitionId", definitionId);
    	hm.put("processDefinitionKey", definitionKey);
    	if(null != businessId) {
    		hm.put("businessKey", businessId);
    	}
    	
    	if(params != null) {
    		List variables = super.getDataParam(params);
    		hm.put("variables", variables);
    	}
    	logger.info("url:" + url);
    	Gson gson = new Gson();
    	logger.info("requestBody:" + gson.toJson(hm) );
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.POST, hm);
    	inst = gson.fromJson(resp, InstancesRS.class);
    	return inst;
    }
        
	/**
     * 根据流程实例获取当前人工任务列表
     * @param instance
     * @return
     * @throws Exception
     */
    public TaskUserRS getActiveNodeInfo(InstancesRS instance)throws Exception{
    	String url = super.urlForm("process.runtime.activeNodeInfo",instance.getId());
    	logger.info("url:" + url);
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.GET);
    	Gson gson = new GsonBuilder().setDateFormat(ConstantsBpm.DATE_FORMAT_ISO_DATETIME_SHORT).create();
    	TaskUserRS nodes = gson.fromJson(resp, TaskUserRS.class);    	
    	return nodes;
    }
    
    /**
     * 根据流程状态获取流程实例
     * @param procInstId 
     * @param isFinished 流程是否结束
     * @return 实例列表
     * @throws Exception
     */
    public ProcessDefinitionInstancesRS findInstByStatus(Integer procInstId, Boolean isFinished) throws Exception {
    	String url = super.urlForm("process.runtime.checkInstanceStatus", procInstId.toString(), isFinished.toString());
    	logger.info("url:" + url);
    	ProcessDefinitionInstancesRS instRs = null;
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.GET);
    	instRs = new Gson().fromJson(resp, ProcessDefinitionInstancesRS.class);
    	return instRs;
    }
    
    /**
     * 根据流程实例ID获取流程变量
     * @param piid 流程实例ID
     * @return
     * @throws Exception
     */
	public List<VariableRS> getVariableInfo(String piid) throws Exception {
    	List<VariableRS> list = null;
    	String url = super.urlForm("runtime.process-instances.variables", piid);
    	logger.info("url:" + url);
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.GET);
    	Gson gson = new GsonBuilder().setDateFormat(ConstantsBpm.DATE_FORMAT_ISO_DATETIME_LONG).create(); 	
    	Type collectionType = new TypeToken<Collection<VariableRS>>(){}.getType();
    	list = gson.fromJson(resp, collectionType);    	
    	return list;
    }
    
	/**
	 * 获取指定流程定义ID的流程定义,推荐使用getProcessDefinition代替
	 * @param definition
	 * @return
	 * @throws Exception
	 */
	@Deprecated
    public DefinitionsRS getDefinition(DefinitionsRS definition)throws Exception{  	
    	ProcessDefinitionsRS proc = this.getDefinitions();
    	Collection<DefinitionsRS> list = proc.getDefinitions();
    	for (DefinitionsRS definitionsRS : list) {
			if(definition.getId().equals(definitionsRS.getId())){
				definition = definitionsRS;
			}
		}
    	
    	return definition;
    }
    
	/**
	 * 获取流程定义图
	 */
	@SuppressWarnings("unchecked")
	public InputStream getImage(String proceDefKey) throws Exception {
		ProcessDefinitionsRS pdrs = this.getProcessDefinitionByKey(proceDefKey, true);
		String url = pdrs.getData().iterator().next().getDiagramResource();
		logger.info("imageUrl:" + url);
		String resp = super.getDataFromRestService(url, EnumActivitiRestType.GET);
		Map<String, String> map2 = new Gson().fromJson(resp, Map.class);

		String contentUrl = (String) map2.get("contentUrl");
		return commontGetImage(contentUrl);
	}
	
	/**
	 * 获取流程定义图URL
	 * @param proceDefKey
	 * @return
	 * @throws Exception
	 */
	public String getImageUrl(String proceDefKey) throws Exception {
		ProcessDefinitionsRS pdrs = this.getProcessDefinitionByKey(proceDefKey, true);
		String url = pdrs.getData().iterator().next().getDiagramResource();
		logger.info("imageUrl:" + url);
		String resp = super.getDataFromRestService(url, EnumActivitiRestType.GET);
		Map<String, String> map2 = new Gson().fromJson(resp, Map.class);

		String contentUrl = (String) map2.get("contentUrl");
		return contentUrl;
	}
	
	/**
	 * 获取流程实例图
	 * @param procInstId 
	 */
	public InputStream getInstanceImage(Integer procInstId) throws Exception {
		String contentUrl = super.urlForm("runtime.process-instances.diagram", String.valueOf(procInstId));
		return commontGetImage(contentUrl);
	}
	
	/**
	 * 获取流程实例图URL
	 * @param procInstId
	 * @return
	 * @throws Exception
	 */
	public String getInstanceImageUrl(Integer procInstId) throws Exception {
		String contentUrl = super.urlForm("runtime.process-instances.diagram", String.valueOf(procInstId));
		return contentUrl;
	}
	
	
	/**
	 * 通用的获取图片方法
	 */
	public InputStream commontGetImage(String contentUrl) throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getMethod = new HttpGet(contentUrl);
		InputStream in = null;

		String auth = getUserName() + ":" + getPassword();
		byte[] encodedAuth = Base64.encode(auth.getBytes());
		String authHeader = "Basic " + new String(encodedAuth);
		getMethod.addHeader("Authorization", authHeader);

		try {
			HttpResponse response = httpClient.execute(getMethod);
			HttpEntity httpEntity = response.getEntity();
			in = httpEntity.getContent();
			
		} catch (Exception e) {
			System.out.println(e.getMessage() + "," + e.getStackTrace());
		} finally {
//			getMethod.releaseConnection();
//			httpClient.getConnectionManager().shutdown();
		}
		return in;
	}
    
    /**
     * 获取指定用户的待办任务列表
     * @param userId
     * @param procDefKey 流程定义Key
     * @param start 分页查询开始值
     * @param size 分页查询每页记录数
     * @return
     * @throws Exception
     */
    public TaskUserRS findPendingTaskListWithinUser(String userId, String procDefKey, Integer start, Integer size)throws Exception{
    	TaskUserRS tu = null;
      	String url = super.urlForm("task.list.tasks.assignee",userId, start.toString(), size.toString());
      	if(StringUtils.isNotEmpty(procDefKey)) {
      		url += "&processDefinitionKey=" + procDefKey;
      	}
      	logger.info("url:" + url);
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.GET);
    	Gson gson = new GsonBuilder().setDateFormat(ConstantsBpm.DATE_FORMAT_ISO_DATETIME_LONG).create();    	
    	tu = gson.fromJson(resp, TaskUserRS.class); 
    	return tu;
    } 
    
    /**
     * 获取所有待办人工任务列表
     * @param isActive 人工任务是否挂起，true表示激活，false表示挂起
     * @return 
     * @throws Exception
     */
    public TaskUserRS findAllPendingTaskList(Boolean isActive) throws Exception {
    	TaskUserRS turs = null;
    	String url = super.urlForm("task.list.tasks") + "?start=" + PAGE_DEFAULT_START + "&size=" + PAGE_SIZE_MAX_VALUE;
    	if(null != isActive) {
    		url += "&active=" + isActive;
    	}
    	logger.info("url:" + url);
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.GET);
    	Gson gson = new GsonBuilder().setDateFormat(ConstantsBpm.DATE_FORMAT_ISO_DATETIME_LONG).create();
    	turs = gson.fromJson(resp, TaskUserRS.class);
    	return turs;
    }
    
    /**
     * 获取当前待办任务的处理权限
     * @param taskId
     * @return
     * @throws Exception
     */
    public List<IdentityLinkRS> findIdentityLinks(String taskId) throws Exception {
    	List<IdentityLinkRS> ils = null;
    	String url = super.urlForm("identification.runtime.identitylinks", taskId);
    	logger.info("url:" + url);
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.GET);
    	Gson gson = new GsonBuilder().setDateFormat(ConstantsBpm.DATE_FORMAT_ISO_DATETIME_LONG).create();
    	ils = gson.fromJson(resp, new TypeToken<List<IdentityLinkRS>>(){}.getType());
    	return ils;
    }
    
    /**
	 * 已办任务列表
	 * @param userId
     * @param procDefKey 流程定义Key
     * @param start 分页查询开始值 
     * @param size 分页查询页面每页显示记录数
     * @return
	 * @throws Exception
	 */
    public TaskUserRS findCompletedTaskListWithinUser(String userId, String procDefKey, Integer start, Integer size) throws Exception{
    	String url = super.urlForm("tasks.list.completed", userId, Boolean.TRUE.toString(), start.toString(), size.toString());
    	if(StringUtils.isNotEmpty(procDefKey)) {
    		url += "&processDefinitionKey=" + procDefKey;
    	}
    	System.out.println(url);
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.GET);
    	Gson gson = new GsonBuilder().setDateFormat(ConstantsBpm.DATE_FORMAT_ISO_DATETIME_LONG).create();
    	TaskUserRS nodes = gson.fromJson(resp, TaskUserRS.class);    	
    	return nodes;
    }
    
    /**
     * 获取待认领任务列表
     * @param procDefKey 
     * @param start 分页查询开始下标
     * @param size 分页查询每页记录数
     * @param userTaskVO
     * @return
     * @throws Exception
     */
    public TaskUserRS findClaimedTaskListWithinUser(String userId, String procDefKey, Integer start, Integer size)throws Exception{
      	String url = super.urlForm("task.list.tasks.claimed", userId, start.toString(), size.toString());
      	if(StringUtils.isNotEmpty(procDefKey)) {
      		url += "&processDefinitionKey=" + procDefKey;
      	}
      	logger.info("url:" + url);
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.GET);
    	Gson gson = new GsonBuilder().setDateFormat(ConstantsBpm.DATE_FORMAT_ISO_DATETIME_LONG).create();    	
    	TaskUserRS tu = gson.fromJson(resp, TaskUserRS.class); 
    	return tu;
    }
    
    /**
     * 获取指定ID的任务
     * @param taskId
     * @return
     * @throws Exception
     */
    public TaskNodeRS getTaskNode(String taskId)throws Exception{
    	TaskNodeRS tn = null;
      	String url = super.urlForm("runtime.tasks.tasknode",taskId);
      	logger.info("url:" + url);
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.GET);
    	Gson gson = new GsonBuilder().setDateFormat(ConstantsBpm.DATE_FORMAT_ISO_DATETIME_LONG).create();
    	// 转日期有问题,Exception in thread "main" com.google.gson.JsonSyntaxException: 2015-05-11T13:48:31.312+08:00
    	tn = gson.fromJson(resp, TaskNodeRS.class); 
    	return tn;
    }
    
    public TaskNodeRS getHistoryTask(String taskId) throws Exception {
    	TaskNodeRS tn = null;
    	String url = super.urlForm("task.history.inst", taskId);
    	logger.info("url:" + url);
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.GET);
    	Gson gson = new GsonBuilder().setDateFormat(ConstantsBpm.DATE_FORMAT_ISO_DATETIME_LONG).create();
    	// 转日期有问题,Exception in thread "main" com.google.gson.JsonSyntaxException: 2015-05-11T13:48:31.312+08:00
    	tn = gson.fromJson(resp, TaskUserRS.class).getData().get(0);
    	return tn;
    }
    
    
    /**
     * 根据流程定义ID获取流程定义所包含的任务列表
     * @param procDefKey 流程定义ID
     * @return
     * @throws Exception
     */
    public List<TaskNodeRS> findTaskNodesByProcDefKey(String procDefKey)throws Exception{
    	List<TaskNodeRS> tns = null;
      	String url = super.urlForm("repository.management.tasks", procDefKey);
      	logger.info("url:" + url);
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.GET);
    	Gson gson = new GsonBuilder().setDateFormat(ConstantsBpm.DATE_FORMAT_ISO_DATETIME_LONG).create();    
//    	Type collectionType = new TypeToken<Collection<TaskNodeRS>>(){}.getType();
    	TaskUserRS turs = gson.fromJson(resp, TaskUserRS.class);
    	tns = turs.getData();
    	return tns;
    }
    
    /**
     * 查询流程实例已经办理的节点和当前要办理的节点
     * @param instanceId 实例ID
     * @return
     * @throws Exception
     */
    public List<TaskNodeRS> getIntanceNodes(String instanceId)throws Exception {
    	// TODO test
      	String url = super.urlForm("task.list.allNodes", instanceId, Process.PAGE_DEFAULT_START.toString(), Process.PAGE_SIZE_MAX_VALUE.toString());
      	logger.info("url:" + url);
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.GET);
    	Gson gson = new GsonBuilder().setDateFormat(ConstantsBpm.DATE_FORMAT_ISO_DATETIME_LONG).create();    
    	List<TaskNodeRS> tns = null;
    	TaskUserRS turs = gson.fromJson(resp, TaskUserRS.class);
    	tns = turs.getData();
    	return tns;
    }
    
    /**
     * 完成人工任务
     * @param taskId
     * @param params
     * @return
     * @throws Exception
     */
	@SuppressWarnings("rawtypes")
	public String taskComplete(String taskId, Map<String, Object> params)throws Exception{
    	String resp = null;
    	String url = super.urlForm("runtime.tasks.complete",taskId);
    	logger.info("url:" + url);
    	HashMap<String, Object> hm = new HashMap<String, Object>();
    	hm.put("action", "complete");
    	if(params != null && !params.isEmpty()) {
    		List variables = super.getDataParam(params);
    		hm.put("variables", variables);
    	}
    	logger.info("requestBody:" + new Gson().toJson(hm));
    	resp = super.getDataFromRestService(url, EnumActivitiRestType.POST, hm);
    	return resp;
    }
	
	/**
     * 认领（分配）任务
     * @param taskId
     * @param userId
	 * @return 
     * @throws Exception
     */
    public String taskAssign(String taskId, String userId)throws Exception {
    	String url = super.urlForm("runtime.tasks.assign", taskId); 
    	logger.info("url:" + url);
    	HashMap<String, Object> hm = new HashMap<String, Object>();
    	hm.put("action", "claim");
    	hm.put("assignee", userId);
    	logger.info("requestBody:" + new Gson().toJson(hm));
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.POST, hm);
    	return resp;
    }
    /**
     * 代理(委托)人工任务
     * @param taskId
     * @param userId
     * @return 
     * @throws Exception
     */
    public String taskDelegate(String taskId, String userId)throws Exception {
    	String url = super.urlForm("runtime.tasks.delegate", taskId); 
    	logger.info("url:" + url);
    	HashMap<String, Object> hm = new HashMap<String, Object>();
    	hm.put("action", "delegate");
    	hm.put("assignee", userId);
    	logger.info("requestBody:" + new Gson().toJson(hm));
    	String resp = super.getDataFromRestService(url, EnumActivitiRestType.POST, hm);
    	return resp;
    }
    
    /**
	 * 回退任务 仅支持未认领的任务
	 * @param taskId
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public String taskResolve(String taskId, Map<String, Object> params)throws Exception{
    	String resp = null;
    	String url = super.urlForm("runtime.tasks.resolve",taskId);
    	logger.info("url:" + url);
    	HashMap<String, Object> hm = new HashMap<String, Object>();
    	hm.put("action", "resolve");
    	if(params != null) {
    		List vars = super.getDataParam(params);
    		hm.put("variables", vars);
    	}
    	logger.info("requestBody:" + new Gson().toJson(hm));
    	resp = super.getDataFromRestService(url, EnumActivitiRestType.POST, hm);
    	return resp;
    }
	
	/**
	 * 添加用户组
	 * @param id
	 * @param name
	 * @param type
	 * @return 201	表示创建了群组。400表示丢失了群组的id。
	 * @throws Exception
	 */
	public String identityAddGroup(String id, String name, String type) throws Exception {
		String resp = null;
		String url = super.urlForm("identification.group.add");
		logger.info("url:" + url);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("name", name);
		params.put("type", type);
		logger.info("url:"+ url + ",requestBody:" + new Gson().toJson(params));
		resp = super.getDataFromRestService(url, EnumActivitiRestType.POST, params);
		logger.info("resulst:" + resp);
		return resp;
	}

	/**
	 * 删除群组
	 * @param groupId
	 * @return 204	表示找到了群组，并成功删除了。响应体为空。<br>
	 * 404	表示找不到请求的群组。<br>
	 * @throws Exception
	 */
	public String identityDelGroup(String groupId) throws Exception {
		String resp = null;
		String url = super.urlForm("identification.group.delete", groupId);
		logger.info("url:" + url );
		resp = super.getDataFromRestService(url, EnumActivitiRestType.DELETE);
		return resp;
	}
	
	/**
	 * 根据流程实例ID获取当前任务
	 * @param procInstId
	 * @return 400	表示传递的参数格式错误，或'delegationState'使用了不合法的数据('pending' 和 'resolved'以外的数据)。状态信息包含了详细信息。<br>
	 * @throws Exception
	 */
	public List<TaskNodeRS> getActiveTaskNode(String procInstId) throws Exception {
		List<TaskNodeRS> tasks = null;
		String url = super.urlForm("runtime.tasks.activeNodeWithPiid", procInstId);
		logger.info("url:" + url);
		String resp = super.getDataFromRestService(url, EnumActivitiRestType.GET);
		Gson gson = new GsonBuilder().setDateFormat(ConstantsBpm.DATE_FORMAT_ISO_DATETIME_LONG).create();
    	TaskUserRS taskUser = gson.fromJson(resp, TaskUserRS.class); 	
    	tasks = taskUser.getData();
		return tasks;		
	}
	
	/**
	 * 根据processDefinitionId获取Bpmn模型
	 * @param processDefinitionId
	 * @return 
	 * @throws Exception
	 */
	public BpmnModel getBpmnModel(String processDefinitionId) throws Exception {
		BpmnModel bpmnModel = null;
		String url = super.urlForm("repository.management.bpmnmodel", processDefinitionId);
		logger.info("url:" + url);
		String resp = super.getDataFromRestService(url, EnumActivitiRestType.GET);
		Gson gson = new GsonBuilder().setDateFormat(ConstantsBpm.DATE_FORMAT_ISO_DATETIME_LONG).create();
		System.out.println("resp:" + resp);
//		bpmnModel = gson.fromJson(resp, BpmnModel.class);
		bpmnModel = gson.fromJson(resp, new TypeToken<BpmnModel>(){}.getType());
		return bpmnModel;
	}
}