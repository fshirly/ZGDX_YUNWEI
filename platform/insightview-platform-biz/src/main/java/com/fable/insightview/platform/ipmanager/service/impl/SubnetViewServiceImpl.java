package com.fable.insightview.platform.ipmanager.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.core.dao.idgenerator.impl.IDGeneratorFactory;
import com.fable.insightview.platform.dao.IDepartmentDao;
import com.fable.insightview.platform.dict.util.DictionaryLoader;
import com.fable.insightview.platform.ipmanager.entity.IPManAddressListBean;
import com.fable.insightview.platform.ipmanager.entity.IPManSubNetInfoBean;
import com.fable.insightview.platform.ipmanager.entity.IPManSubNetRDeptBean;
import com.fable.insightview.platform.ipmanager.entity.SubnetDeptTreeBean;
import com.fable.insightview.platform.ipmanager.entity.SubnetExportBean;
import com.fable.insightview.platform.ipmanager.mapper.IPManAddressListMapper;
import com.fable.insightview.platform.ipmanager.mapper.IPManSubNetInfoMapper;
import com.fable.insightview.platform.ipmanager.mapper.IPManSubNetRDeptMapper;
import com.fable.insightview.platform.ipmanager.service.ISubnetViewService;
import com.fable.insightview.platform.ipmanager.util.IPPoolUtil;
import com.fable.insightview.platform.ipmanager.util.Nets;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.provider.entity.ProviderInfoBean;

@Service
public class SubnetViewServiceImpl implements ISubnetViewService{
	private final Logger logger = LoggerFactory.getLogger(SubnetViewServiceImpl.class);
	//预占状态
	private static final int PREEMPT_STATUS = 2;
	//占用状态
	private static final int USED_STATUS = 3;
	//保留状态
	private static final int RESERVED_SATUS = 4;
	@Autowired
	IPManSubNetInfoMapper ipManSubNetInfoMapper;
	@Autowired
	IPManSubNetRDeptMapper ipManSubNetRDeptMapper;
	@Autowired
	IPManAddressListMapper ipManAddressListMapper;
	@Autowired
	IDepartmentDao departmentDao;
	
	/**
	 * 获得子网部门树
	 */
	@Override
	public Map<String, Object> getSubnetAndDeptVal() {
		List<SubnetDeptTreeBean> treeMenuList = new ArrayList<SubnetDeptTreeBean>();
		Map<Integer, String> networkTypeMap = DictionaryLoader.getConstantItems("networkType");
		logger.info(" 获得子网信息");
		List<IPManSubNetInfoBean> subnetLst = ipManSubNetInfoMapper.getAllSubNetInfo();
		for (int i = 0; i < subnetLst.size(); i++) {
			SubnetDeptTreeBean sd = new SubnetDeptTreeBean();
			sd.setId("s" + subnetLst.get(i).getSubNetId());
			sd.setParentId("0");
			sd.setName(subnetLst.get(i).getIpAddress() + "（" + networkTypeMap.get(subnetLst.get(i).getSubNetType()) + "）");
			treeMenuList.add(sd);
			//每个子网的空闲
			SubnetDeptTreeBean sd2 = new SubnetDeptTreeBean();
			sd2.setId("f" + subnetLst.get(i).getSubNetId());
			sd2.setParentId("s" + subnetLst.get(i).getSubNetId());
			sd2.setName("未分配");
			treeMenuList.add(sd2);
		}
		
		logger.info("获得部门信息");
		List<IPManSubNetRDeptBean> subnetRDeptLst = ipManSubNetRDeptMapper.getAllSubNetRDeptInfo();
		for (int i = 0; i < subnetRDeptLst.size(); i++) {
			SubnetDeptTreeBean sd = new SubnetDeptTreeBean();
			sd.setId("d" + subnetRDeptLst.get(i).getDeptId());
			sd.setParentId("s" + subnetRDeptLst.get(i).getSubNetId());
			sd.setName(subnetRDeptLst.get(i).getDeptName());
			treeMenuList.add(sd);
		}
		
		String menuLstJson = JsonUtil.toString(treeMenuList);
		logger.info("menuLstJson======="+menuLstJson);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		return result;
	}

	@Override
	public List<IPManSubNetInfoBean> selectSubNetInfo(
			Page<IPManSubNetInfoBean> page) {
		List<IPManSubNetInfoBean> subnetLst = ipManSubNetInfoMapper.selectSubNetInfo(page);
		Map<Integer, String> networkTypeMap = DictionaryLoader.getConstantItems("networkType");
		for (int i = 0; i < subnetLst.size(); i++) {
			int subNetType = subnetLst.get(i).getSubNetType();
			String subNetTypeName = networkTypeMap.get(subNetType);
			subnetLst.get(i).setSubNetTypeName(subNetTypeName);
		}
		return subnetLst;
	}

	@Override
	public IPManSubNetInfoBean getSubnetInfoByID(int subNetId) {
		IPManSubNetInfoBean bean = ipManSubNetInfoMapper.getSubnetInfoByID(subNetId);
		Map<Integer, String> networkTypeMap = DictionaryLoader.getConstantItems("networkType");
		String subNetTypeName = networkTypeMap.get(bean.getSubNetType());
		bean.setSubNetTypeName(subNetTypeName);
		bean.setFreeNum(bean.getFreeNum() + bean.getPreemptNum());
		return bean;
	}

	@Override
	public List<IPManSubNetInfoBean> getAllSubNetInfo() {
		return ipManSubNetInfoMapper.getAllSubNetInfo();
	}


	@Override
	public List<IPManAddressListBean> selectFreeSubNetInfo(
			Page<IPManAddressListBean> page) {
		List<IPManAddressListBean> freeAddressLst = ipManAddressListMapper.selectFreeSubNetInfo(page);
		for (int i = 0; i < freeAddressLst.size(); i++) {
			Map<Integer, String> networkTypeMap = DictionaryLoader.getConstantItems("networkType");
			IPManAddressListBean bean = freeAddressLst.get(i);
			String subNetTypeName = networkTypeMap.get(bean.getSubNetType());
			bean.setSubNetTypeName(subNetTypeName);
		}
		return freeAddressLst;
	}

	@Override
	public List<IPManAddressListBean> getAllFreeSubNetInfo(int subNetId) {
		return ipManAddressListMapper.getAllFreeSubNetInfo(subNetId);
	}

	@Override
	public void exportExcel(HttpServletResponse response,
			SubnetExportBean exportEntity) {
		logger.info("导出前处理。。。");
		// 导出的文件名称
		String fileName = exportEntity.getExlName();

		// 设置response头信息
		response.setContentType("octets/stream");
		response.setCharacterEncoding("gbk");
		try {
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("gb2312"), "iso8859-1"));

			OutputStream out = response.getOutputStream();
			// ExportExcelByPOI exportExcelByPOI = new ExportExcelByPOI();
			this.exportExcel(exportEntity, out);

			out.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}

	@Override
	public void exportExcel(SubnetExportBean exportEntity, OutputStream out) {
		logger.info("开始导出。。。");
		// 声明一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 生成一个表格
		XSSFSheet sheet = workbook.createSheet();
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		XSSFCellStyle style = workbook.createCellStyle();
		
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		
		// 生成一个字体
		XSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		
		// font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		
		// 生成并设置另一个样式
		XSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(CellStyle.BORDER_THIN);
		style2.setBorderLeft(CellStyle.BORDER_THIN);
		style2.setBorderRight(CellStyle.BORDER_THIN);
		style2.setBorderTop(CellStyle.BORDER_THIN);
		style2.setAlignment(CellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		// 生成另一个字体
		XSSFFont font2 = workbook.createFont();
		
		// font2.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 声明一个画图的顶级管理器
		XSSFDrawing patriarch = sheet.createDrawingPatriarch();
		
		// 定义注释的大小和位置,详见文档
		XSSFComment comment = patriarch
				.createCellComment((new XSSFClientAnchor(0, 0, 0, 0, (short) 4,2, (short) 6, 5)));
		
		// 设置注释内容
		comment.setString(new XSSFRichTextString("可以在POI中添加注释！"));
		
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("leno");
		String[] headers = exportEntity.getTitleArr();
		
		// 产生表格标题行
		XSSFRow row = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			XSSFRichTextString text = new XSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		// 遍历数据集产生shuj
		try {
			String jsonResource = JsonUtil.toString(exportEntity.getExpResource());
			ObjectMapper mapper = new ObjectMapper();
			JsonNode info = mapper.readTree(jsonResource);
			int index = 0;
			for (Iterator iter = info.iterator(); iter.hasNext();) {
				++index;
				
				// 创建一行
				row = sheet.createRow(index);
				XSSFCell cell = null;
				Object value = null;
				JsonNode item = (JsonNode) iter.next();
				for (int i = 0; i < exportEntity.getColnameArr().length; i++) {
					String columnName = exportEntity.getColnameArr()[i];
					String columnVal = "";
					if (columnName.indexOf(".") < 0) {
						columnVal = item.get(columnName).toString();
					} else {
						columnVal = item.get(columnName.split("\\.")[0]).get(
								columnName.split("\\.")[1]).toString();
					}
					cell = row.createCell(i);
					if (null != columnVal && !"null".equals(columnVal)) {
						value = columnVal;
					} else {
						value = "";
					}
					String textValue = value.toString();
					textValue = textValue.replace("\"", "");
					if (null == value) {
						value = "-";
					}
					
					XSSFRichTextString richString = new XSSFRichTextString(textValue);
					XSSFFont font3 = workbook.createFont();
					font3.setColor(HSSFColor.BLUE.index);
					richString.applyFont(font3);
					cell.setCellValue(richString);

					value = textValue;
					cell.setCellValue(value.toString());
					cell.setCellStyle(style2);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("信息导出异常！",e.getMessage());
		}

		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	@Override
	public SubnetExportBean refExportBean(SubnetExportBean exportBean) {
		exportBean.setColnameArr(exportBean.getColNameArrStr().split(","));
		exportBean.setTitleArr(exportBean.getColTitleArrStr().split(","));
		return exportBean;
	}

	@Override
	public List<IPManSubNetRDeptBean> listSubnetConDept(
			Page<IPManSubNetRDeptBean> page) {
		return ipManSubNetRDeptMapper.listSubnetConDept(page);
	}

	@Override
	public List<IPManSubNetRDeptBean> getAllSubNetRDeptInfo2(int subNetId) {
		return ipManSubNetRDeptMapper.getAllSubNetRDeptInfo2(subNetId);
	}

	@Override
	public List<IPManAddressListBean> selectAllSubnetDeptInfo(
			Page<IPManAddressListBean> page) {
		List<IPManAddressListBean> addressLst = ipManAddressListMapper.selectAllSubnetDeptInfo(page);
		for (int i = 0; i < addressLst.size(); i++) {
			Map<Integer, String> networkTypeMap = DictionaryLoader.getConstantItems("networkType");
			IPManAddressListBean bean = addressLst.get(i);
			String subNetTypeName = networkTypeMap.get(bean.getSubNetType());
			bean.setSubNetTypeName(subNetTypeName);
		}
		return addressLst;
	}

	@Override
	public List<IPManAddressListBean> getAllSubNetDeptInfo(int subNetId,
			int deptId) {
		return ipManAddressListMapper.getAllSubNetDeptInfo(subNetId, deptId);
	}

	@Override
	public Map<String, Object> checkAddSubnet(IPManSubNetInfoBean bean) {
		Map<String, Object> result = new HashMap<String, Object>();
		//输入的网络地址是否正确
		boolean isStartIP = false;
		//是否存在子网段覆盖
		boolean isExist = false;
		//子网容量是否大于4
		boolean isRightNum = false;
		String ip = bean.getIpAddress();
		int[] ipscope = IPPoolUtil.getIPIntScope(ip, bean.getSubNetMark());
		String ipAddress = IPPoolUtil.intToIp(ipscope[0]);
		if(ip.equals(ipAddress)){
			isStartIP = true;
			bean.setIpAddress(ipAddress);
			bean.setBroadCast(IPPoolUtil.intToIp(ipscope[1]));
			List<IPManSubNetInfoBean> subnetLst = ipManSubNetInfoMapper.getByIPAndSubNetMark(bean);
			if(subnetLst.size() > 0){
				isExist = false;
			}else{
				isExist = true;
				int totalNum = ipscope[1]-ipscope[0] + 1;
				if(totalNum >= 4){
					isRightNum = true;
				}else{
					isRightNum = false;
				}
			}
		}else{
			isStartIP = false;
		}
		result.put("isStartIP", isStartIP);
		result.put("ipAddress", ipAddress);
		result.put("isExist", isExist);
		result.put("isRightNum", isRightNum);
		return result;
	}

	@Override
	public boolean doAddSubnet(IPManSubNetInfoBean bean) {
		boolean addSubnetInfoFlag = false;
		boolean addAddressFlag = false;
		int[] ipscope = IPPoolUtil.getIPIntScope(bean.getIpAddress(), bean.getSubNetMark());
		String netAddress = IPPoolUtil.intToIp(ipscope[0]);
		String broadCast = IPPoolUtil.intToIp(ipscope[1]);
		int startNum = IPPoolUtil.ipToInt(netAddress);
		int endNum = IPPoolUtil.ipToInt(broadCast);
		int totalNum = endNum - startNum + 1;
		int freeNum = totalNum - 2;
		bean.setNetAddress(netAddress);
		bean.setBroadCast(broadCast);
		bean.setTotalNum(totalNum);
		bean.setFreeNum(freeNum);
		bean.setUsedNum(0);
		bean.setPreemptNum(0);
		bean.setIsSplit(0);
		
		//新增子网信息，录入进IPManSubNetInfo表
		try {
			ipManSubNetInfoMapper.insertSubnetInfo(bean);
			addSubnetInfoFlag = true;
		} catch (Exception e) {
			logger.error("新增子网信息异常："+e);
		}
		
		//根据其实ip，循环新增IP地址，录入进IPManAddressList表
		int subnetId = bean.getSubNetId();
		int j = startNum;
		List<IPManAddressListBean> insertLst = new ArrayList<IPManAddressListBean>();
		for (int i = 0; i < totalNum; i++) {
			int id = Integer.parseInt(IDGeneratorFactory.getInstance().getGenerator(IPManAddressListBean.class)
					.generate().toString());
			String ip = IPPoolUtil.intToIp(j);
			
			IPManAddressListBean addressListBean = new IPManAddressListBean();
			addressListBean.setId(id);
			addressListBean.setSubNetId(subnetId);
			addressListBean.setIpAddress(ip);
			if(i == 0 || i== totalNum - 1){
				addressListBean.setStatus(4); //网络地址、广播地址状态为：保留
			}else{
				addressListBean.setStatus(1); //新增的ip地址为：空闲
			}
			insertLst.add(addressListBean);
//			try {
//				ipManAddressListMapper.insertAddress(addressListBean);
//				addAddressFlag = true;
//			} catch (Exception e) {
//				logger.error("新增ip地址异常："+e);
//			}
			j++;
		}
		
		
		try {
			ipManAddressListMapper.batchInsertAddress(insertLst);
			addAddressFlag = true;
		} catch (Exception e) {
			logger.error("新增ip地址异常："+e);
		}
//		Date endTime = new Date();
//		System.out.println("批量插入开始时间："+startTime);
//		System.out.println("批量结束开始时间："+endTime);
//		long l=endTime.getTime() -startTime.getTime() ;
//		System.out.println("时间差："+l/1000);
		if(addSubnetInfoFlag == true && addAddressFlag == true){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean doFreeAssignDept(IPManAddressListBean addressListBean) {
		boolean updateAddressFlag = false;
		boolean updateSubnetInfoFlag = false;
		boolean doSubnetRDeptFlag = false;
		int updatenum = -1;
//		String[] idLst = addressListBean.getIds().split(",");
		int subNetId = addressListBean.getSubNetId();
		int deptId = addressListBean.getDeptId();
		
		//更新ip地址状态
		IPManAddressListBean bean = new IPManAddressListBean();
		String ids = addressListBean.getIds();
		if(ids.contains(",")){
			ids = ids.substring(0, ids.lastIndexOf(","));
		}
		bean.setIds(ids);
		bean.setDeptId(deptId);
		bean.setStatus(2);
		try {
			updatenum = ipManAddressListMapper.updateAddressByIDs(bean);
			updateAddressFlag = true;
		} catch (Exception e) {
			logger.error("更新ip地址异常："+e);
		}
		
		//更新子网信息表
		int diviceNum = updatenum;
		IPManSubNetInfoBean subnetInfo =ipManSubNetInfoMapper.getSubnetInfoByID(subNetId);
		//子网预占地址数 = 原来的+分配的
		subnetInfo.setPreemptNum(subnetInfo.getPreemptNum() + diviceNum);
		subnetInfo.setFreeNum(subnetInfo.getFreeNum() - diviceNum);
		try {
			ipManSubNetInfoMapper.updateSubnetInfoByID(subnetInfo);
			updateSubnetInfoFlag = true;
		} catch (Exception e) {
			logger.error("更新子网信息异常："+e);
		}
		
		//是否已经存在子网部门关系
		IPManSubNetRDeptBean subNetRDeptBean = new IPManSubNetRDeptBean();
		subNetRDeptBean.setSubNetId(subNetId);
		subNetRDeptBean.setDeptId(deptId);
		List<IPManSubNetRDeptBean> subnetRdeptLst = ipManSubNetRDeptMapper.getBySubNetAndDept(subNetRDeptBean);
		//已经存在关系，则更新
		try {
			if(subnetRdeptLst.size() > 0){
				IPManSubNetRDeptBean subnetRDept = subnetRdeptLst.get(0);
				//子网部门关系中 预占地址数=原来的 + 分配的
				subnetRDept.setPreemptNum(subnetRDept.getPreemptNum() + diviceNum);
				ipManSubNetRDeptMapper.updateSubNetRDept(subnetRDept);
			}
			//不存在关系，则新增一条关系
			else{
				subNetRDeptBean.setUsedNum(0);
				subNetRDeptBean.setPreemptNum(diviceNum);
				ipManSubNetRDeptMapper.insertSubnetRDept(subNetRDeptBean);
			}
			doSubnetRDeptFlag = true;
		} catch (Exception e) {
			logger.error("新增更新子网部门关系异常："+e);
		}
		
		if(updateAddressFlag == true && updateSubnetInfoFlag == true && doSubnetRDeptFlag == true){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean checkBeforeDel(int subNetId) {
		IPManAddressListBean addressListBean = new IPManAddressListBean();
		addressListBean.setSubNetId(subNetId);
		addressListBean.setStatus(USED_STATUS);
		List<IPManAddressListBean> addressLst = ipManAddressListMapper.getBySubNetIdAndDeptAndStatus(addressListBean);
		List<IPManSubNetRDeptBean> subnetRDeptLst = ipManSubNetRDeptMapper.getUsedBySubNet(subNetId);
		if(addressLst.size() <= 0 && subnetRDeptLst.size() <= 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean delSubNetInfoById(int subNetId) {
		return ipManSubNetInfoMapper.delSubNetInfoById(subNetId);
	}

	@Override
	public boolean delSubNetInfos(List<Integer> subNetIds) {
		return ipManSubNetInfoMapper.delSubNetInfos(subNetIds);
	}

	@Override
	public Map<String, Object> doWithdraw(IPManAddressListBean addressListBean, String ids) {
		boolean withdrwaFlag = false;
		boolean isReserve = false;
		String ipAddress = "";
		
		boolean updateAddressFlag = false;
		boolean updateSubnetInfoFlag = false;
		boolean doSubnetRDeptFlag = false;
		String[] splitLst = ids.split(",");
		int subNetId = addressListBean.getSubNetId();
		int deptId = addressListBean.getDeptId();
		
		//能够被收回的id
		List<Integer> withdrawIdLst = new ArrayList<Integer>();
		String withdrawIds = "";
		//被设备占用不能收回的id
		List<Integer> reserveIdLst = new ArrayList<Integer>();
		for (int i = 0; i < splitLst.length; i++) {
			int id = Integer.parseInt(splitLst[i]);
			IPManAddressListBean bean = ipManAddressListMapper.getById(id);
			if(bean != null){
				if(bean.getStatus() == 3){
					reserveIdLst.add(id);
				}else{
					withdrawIdLst.add(id);
					withdrawIds = withdrawIds + id + ",";
				}
			}
			
		}
		
		//收回能收回的
		if(withdrawIdLst.size() > 0 ){
			withdrawIds = withdrawIds.substring(0,withdrawIds.lastIndexOf(","));
			
			//获得收回的地址中预占地址数、占用地址数
			IPManAddressListBean manAddressListBean = new IPManAddressListBean();
//			ids = ids.substring(0, ids.lastIndexOf(","));
			manAddressListBean.setIds(withdrawIds);
			manAddressListBean.setStatus(2);
			int preemptNumTotal = ipManAddressListMapper.getCountByIdsAndStatus(manAddressListBean);
//			manAddressListBean.setStatus(3);
//			int usedNumTotal = ipManAddressListMapper.getCountByIdsAndStatus(manAddressListBean);
			
			
			//更新ip地址状态
			for (int i = 0; i < withdrawIdLst.size(); i++) {
				int id = withdrawIdLst.get(i);
				IPManAddressListBean bean = new IPManAddressListBean();
				bean.setId(id);
				bean.setStatus(1);
				try {
					ipManAddressListMapper.updateAddressByID2(bean);
					updateAddressFlag = true;
				} catch (Exception e) {
					logger.error("更新ip地址异常："+e);
				}
			}
			
			//更新子网subNetInfo信息
			int diviceNum = withdrawIdLst.size();
			IPManSubNetInfoBean subnetInfo =ipManSubNetInfoMapper.getSubnetInfoByID(subNetId);
			//子网预占地址数 = 原来的-收回的中预占数
			subnetInfo.setPreemptNum(subnetInfo.getPreemptNum() - preemptNumTotal);
			//子网占用地址数 = 原有的 - 收回的中占用数
//			subnetInfo.setUsedNum(subnetInfo.getUsedNum() - usedNumTotal);
			subnetInfo.setFreeNum(subnetInfo.getFreeNum() + diviceNum);
			try {
				ipManSubNetInfoMapper.updateSubnetInfoByID(subnetInfo);
				updateSubnetInfoFlag = true;
			} catch (Exception e) {
				logger.error("更新子网信息异常："+e);
			}
			
			//操作子网部门关系表
			IPManSubNetRDeptBean subNetRDeptBean = new IPManSubNetRDeptBean();
			subNetRDeptBean.setSubNetId(subNetId);
			subNetRDeptBean.setDeptId(deptId);
			List<IPManSubNetRDeptBean> subnetRdeptLst = ipManSubNetRDeptMapper.getBySubNetAndDept(subNetRDeptBean);
			try {
				if(subnetRdeptLst.size() > 0){
					IPManSubNetRDeptBean subnetRDept = subnetRdeptLst.get(0);
					int defaultUsedNum = subnetRDept.getUsedNum();
					int defaultPreemptNum = subnetRDept.getPreemptNum();
//					int currentUsedNum = defaultUsedNum - usedNumTotal;
					int currentPreemptNum = defaultPreemptNum - preemptNumTotal;
					
					//如果占用地址数=0，预占地址数=0，则删除该子网部门关系;否则更新
					if(defaultUsedNum == 0 && currentPreemptNum == 0){
						ipManSubNetRDeptMapper.delSubNetDDeptById(subnetRDept.getId());
					}else{
						//子网部门关系中 现在预占地址数=原来的 - 收回的,现在占用地址数 = 原有的 -收回的
						subnetRDept.setPreemptNum(currentPreemptNum);
//						subnetRDept.setUsedNum(currentUsedNum);
						ipManSubNetRDeptMapper.updateSubNetRDept(subnetRDept);
					}
				}
				
				doSubnetRDeptFlag = true;
			} catch (Exception e) {
				logger.error("操作子网部门关系异常："+e);
			}
			if(updateAddressFlag == true && updateSubnetInfoFlag == true && doSubnetRDeptFlag == true){
				withdrwaFlag = true;
			}else{
				withdrwaFlag = false;
			}
		}
		
		//获得不能收回的ip地址
		for (int i = 0; i < reserveIdLst.size(); i++) {
			String ip = ipManAddressListMapper.getById(reserveIdLst.get(i)).getIpAddress();
			if((i + 1) % 2 == 0){
				ipAddress = ipAddress + ip + "," + "<br/>";
			}else{
				ipAddress = ipAddress + ip + ",";
			}
		}
		if(ipAddress.length() > 0){
			withdrwaFlag = false;
			isReserve = true;
			ipAddress = ipAddress.substring(0,ipAddress.lastIndexOf(","));
		}
		ipAddress = "<div style='float:left'>【"+ipAddress+"】</div>";
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("withdrwaFlag", withdrwaFlag);
		result.put("isReserve", isReserve);
		result.put("ipAddress", ipAddress);
		return result;
	}

	@Override
	public boolean delBySubNetId(int subNetId) {
		return ipManAddressListMapper.delBySubNetId2(subNetId);
	}

	@Override
	public Map<String, Object> doWithdrawDept(String ids, int subNetId) {
		boolean withdrwaFlag = false;
		boolean isReserve = false;
		String deptName ="";
		boolean updateAddressFlag = false;
		boolean delSubnetRDept = false;
		boolean updateSubNatFlag = false;
		logger.info("从子网中收回部门，子网的id="+subNetId);
		String[] splitLst = ids.split(",");
		//能够被收回的id
		List<Integer> withdrawIdLst = new ArrayList<Integer>();
		//被设备占用不能收回的id
		List<Integer> reserveIdLst = new ArrayList<Integer>();
		
		for (int i = 0; i < splitLst.length; i++) {
			int id = Integer.parseInt(splitLst[i]);
			IPManSubNetRDeptBean subNetRDeptBean = ipManSubNetRDeptMapper.getByID(id);
			if(subNetRDeptBean != null){
				if(subNetRDeptBean.getUsedNum() > 0 ){
					reserveIdLst.add(id);
				}else{
					withdrawIdLst.add(id);
				}
			}
		}
		
		//收回能收回的
		if(withdrawIdLst.size() > 0 ){
			//收回的占用IP地址总数
			int useNumTotal = 0;
			//收回的预占地址总数
			int preemptNumTotal = 0;
			for (int i = 0; i < withdrawIdLst.size(); i++) {
				int id = withdrawIdLst.get(i);
				IPManSubNetRDeptBean subNetRDeptBean = ipManSubNetRDeptMapper.getByID(id);
				int deptId = subNetRDeptBean.getDeptId();
				logger.info("收回的部门的id="+deptId);
				//获得该部门在改子网段下占用地址数、预占地址数
				int usedNum = subNetRDeptBean.getUsedNum();
				int preemptNum = subNetRDeptBean.getPreemptNum();
				
				//修改IP地址状态，将该部门的所有ip地址置为空闲
				IPManAddressListBean addressListBean = new IPManAddressListBean();
				addressListBean.setStatus(1);
				addressListBean.setDeptId(deptId);
				addressListBean.setSubNetId(subNetId);
				try {
					ipManAddressListMapper.updateBySubNetAndDept(addressListBean);
					updateAddressFlag = true;
				} catch (Exception e) {
					logger.error("更新ip地址异常："+e);
				}
				
				//删除子网部门关系
				try {
					ipManSubNetRDeptMapper.delSubNetDDeptById(id);
					delSubnetRDept = true;
				} catch (Exception e) {
					logger.error("删除子网部门关系异常："+e);
				}
				useNumTotal = useNumTotal + usedNum ;
				preemptNumTotal = preemptNumTotal + preemptNum;
			}
			
			//获得该子网网的信息
			IPManSubNetInfoBean subNetInfoBean = ipManSubNetInfoMapper.getSubnetInfoByID(subNetId);
			subNetInfoBean.setUsedNum(subNetInfoBean.getUsedNum() - useNumTotal);
			subNetInfoBean.setPreemptNum(subNetInfoBean.getPreemptNum() - preemptNumTotal);
			subNetInfoBean.setFreeNum(subNetInfoBean.getFreeNum() + useNumTotal +preemptNumTotal);
			try {
				ipManSubNetInfoMapper.updateSubnetInfoByID(subNetInfoBean);
				updateSubNatFlag = true;
			} catch (Exception e) {
				logger.error("更新子网信息异常："+e);
			}
			
			if(updateAddressFlag == true && delSubnetRDept == true && updateSubNatFlag == true){
				withdrwaFlag = true;
			}else{
				withdrwaFlag = false;
			}
		}
		
		//获得不能收回的部门
		for (int i = 0; i < reserveIdLst.size(); i++) {
			String dName = ipManSubNetRDeptMapper.getByID(reserveIdLst.get(i)).getDeptName();
			if((i + 1) % 3 == 0){
				deptName = deptName + dName + "," + "<br/>";
			}else{
				deptName = deptName + dName + ",";
			}
		}
		if(deptName.length() > 0){
			withdrwaFlag = false;
			isReserve = true;
			deptName = deptName.substring(0,deptName.lastIndexOf(","));
		}
		deptName = "<br/><div style='float:left'>【"+deptName+"】</div>";
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("withdrwaFlag", withdrwaFlag);
		result.put("isReserve", isReserve);
		result.put("deptName", deptName);
		return result;
	}

	@Override
	public boolean isInSubNet(int subNetId, String checkIp) {
		IPManSubNetInfoBean subNetInfoBean = ipManSubNetInfoMapper.getSubnetInfoByID(subNetId);
		int[] ipscope = IPPoolUtil.getIPIntScope(checkIp, subNetInfoBean.getSubNetMark());
		String ipAddress = IPPoolUtil.intToIp(ipscope[0]);
		if(ipAddress.equals(subNetInfoBean.getIpAddress())){
			return true;
		}
		return false;
	}

	@Override
	public Map<String, Object> checkIsAllFree(IPManAddressListBean addressListBean) {
		//是否全都是空闲的ip
		boolean isAllFree = false;
		//空闲id
		String freeIds = "";
		//空闲地址数
		int freeNum = 0;
		//空闲地址数
		int totalNum = 0;
		//使用地址数
		int useNum = 0;
		//保留地址数
		int reserveNum = 0;
		List<IPManAddressListBean> addressList = ipManAddressListMapper
				.getByIPScopeAndSubNetId(addressListBean);
		totalNum = addressList.size();
		IPManAddressListBean bean = new IPManAddressListBean();
		for (int i = 0; i < addressList.size(); i++) {
			bean = addressList.get(i);
			if (bean.getStatus() == 1 && bean.getDeptId() == null) {
				freeIds = freeIds + bean.getId() + ",";
				freeNum++;
			} else if (bean.getStatus() == 2 || bean.getStatus() == 3) {
				useNum++;
			} else {
				reserveNum++;
			}
		}
		if(freeNum == addressList.size()){
			isAllFree = true;
		}else{
			isAllFree = false;
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isAllFree", isAllFree);
		result.put("freeIds", freeIds);
		result.put("totalNum", totalNum);
		result.put("freeNum", freeNum);
		result.put("useNum", useNum);
		result.put("reserveNum", reserveNum);
		return result;
	}

	/**
	 * 子网拆分预览
	 */
	@Override
	public Map<String, Object> doPreviewSplitSubnet(String ipAddress, String subNetMark ,int splitNum) {
		Nets net =  IPPoolUtil.getSubnetting(ipAddress,subNetMark ,splitNum);
		List<IPManSubNetInfoBean> splitSubnetLst  = new ArrayList<IPManSubNetInfoBean>();
		for (int i = 0; i < net.getSubNetColl().length; i++) {
			IPManSubNetInfoBean subNetInfoBean = new IPManSubNetInfoBean();
			String[] netAndBroadCast = net.getSubNetColl()[i].split("---");
			subNetInfoBean.setSubNetMark(net.getSubNetMask());
			
			//网络地址是否空闲
			IPManAddressListBean addressListBean = ipManAddressListMapper.getByIPAddress(netAndBroadCast[0]);
			if(addressListBean != null){
				subNetInfoBean.setIsNetAddressFree(addressListBean.getStatus());
				String statusVal = "";
				if(addressListBean.getStatus() == 1){
					statusVal = "（空闲）";
				}else if(addressListBean.getStatus() == 2){
					statusVal = "（空闲）";
				}else if(addressListBean.getStatus() == 3){
					statusVal = "（占用）";
				}else if(addressListBean.getStatus() == 4){
					statusVal = "（保留）";
				}
				subNetInfoBean.setNetAddress(netAndBroadCast[0] + statusVal);
			}
			//广播地址是否空闲
			IPManAddressListBean addressListBean2 = ipManAddressListMapper.getByIPAddress(netAndBroadCast[1]);
			if(addressListBean2 != null){
				subNetInfoBean.setIsBroadCastFree(addressListBean2.getStatus());
				String statusVal = "";
				if(addressListBean2.getStatus() == 1){
					statusVal = "（空闲）";
				}else if(addressListBean2.getStatus() == 2){
					statusVal = "（空闲）";
				}else if(addressListBean2.getStatus() == 3){
					statusVal = "（占用）";
				}else if(addressListBean2.getStatus() == 4){
					statusVal = "（保留）";
				}
				subNetInfoBean.setBroadCast(netAndBroadCast[1] + statusVal);
			}
			splitSubnetLst.add(subNetInfoBean);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", splitSubnetLst);
		return result;
	}

	@Override
	public boolean doSplit(int oldSubNetId, String ipAddress, String subNetMark, int splitNum) {
		logger.info("执行子网拆分，拆分的子网id："+oldSubNetId+",子网地址为："+ipAddress+",拆分的数量："+splitNum);
		try {
			Nets net =  IPPoolUtil.getSubnetting(ipAddress,subNetMark,splitNum);
			IPManSubNetInfoBean oldSubnet = ipManSubNetInfoMapper.getSubnetInfoByID(oldSubNetId); 
			for (int i = 0; i < net.getSubNetColl().length; i++) {
				String[] netAndBroadCast = net.getSubNetColl()[i].split("---");
				String netAddress = netAndBroadCast[0];
				String broadCast = netAndBroadCast[1];
				//获得新子网段的IP地址
				IPManAddressListBean addressListBean = new IPManAddressListBean();
				addressListBean.setStartIp(netAddress);
				addressListBean.setEndIp(broadCast);
				List<IPManAddressListBean> addressList = ipManAddressListMapper.getAddressByIPScope(addressListBean);
				addressListBean.setStatus(PREEMPT_STATUS);
				int preemptNum = ipManAddressListMapper.getCountByScopeAndStatus(addressListBean);
				addressListBean.setStatus(USED_STATUS);
				int usedNum = ipManAddressListMapper.getCountByScopeAndStatus(addressListBean);
				
				//新增子网
				IPManSubNetInfoBean subNetInfoBean = new IPManSubNetInfoBean();
				subNetInfoBean.setIpAddress(netAddress);
				subNetInfoBean.setNetAddress(netAddress);
				subNetInfoBean.setBroadCast(broadCast);
				subNetInfoBean.setSubNetMark(net.getSubNetMask());
				subNetInfoBean.setSubNetType(1);
				int startNum = IPPoolUtil.ipToInt(netAddress);
				int endNum = IPPoolUtil.ipToInt(broadCast);
				int totalNum = endNum - startNum + 1;
				int freeNum = totalNum - 2 - usedNum;
				subNetInfoBean.setTotalNum(totalNum);
				subNetInfoBean.setFreeNum(freeNum);
				subNetInfoBean.setUsedNum(usedNum);
				subNetInfoBean.setPreemptNum(preemptNum);
				subNetInfoBean.setIsSplit(1);
				subNetInfoBean.setGateway(oldSubnet.getGateway());
				subNetInfoBean.setDnsAddress(oldSubnet.getDnsAddress());
				subNetInfoBean.setSubNetType(oldSubnet.getSubNetType());
				ipManSubNetInfoMapper.insertSubnetInfo(subNetInfoBean);
				int subNetId = subNetInfoBean.getSubNetId();
				
				
				//子网段中占用或预占的部门
				Map<Integer, Integer> deptMap = new HashMap<Integer, Integer>();
				//子网段中所有IP地址的id
				String ipAddressIds = "";
				for (int j = 1; j < addressList.size(); j++) {
					ipAddressIds = ipAddressIds + addressList.get(j).getId()+",";
					if(addressList.get(j).getDeptId() != null){
						deptMap.put(addressList.get(j).getDeptId(), addressList.get(j).getDeptId());
					}
				}
				ipAddressIds = ipAddressIds.substring(0, ipAddressIds.lastIndexOf(","));
				//批量更新ip地址所属子网
				ipManAddressListMapper.batchUpdate(ipAddressIds, subNetId);
				
				//更新新的网络地址和广播地址
				IPManAddressListBean address = new IPManAddressListBean();
				address.setSubNetId(subNetId);
				address.setStatus(RESERVED_SATUS);
				address.setId(addressList.get(0).getId());
				ipManAddressListMapper.updateReservedAddressByID(address);
				address.setId(addressList.get(addressList.size() - 1).getId());
				ipManAddressListMapper.updateReservedAddressByID(address);
				
				
				//新增子网部门关系
				List<Integer> deptIdLst = new ArrayList<Integer>();
				for(Iterator iterator = deptMap.keySet().iterator();iterator.hasNext();){
					int id = deptMap.get(iterator.next());
					deptIdLst.add(id);
				}
				for (int j = 0; j < deptIdLst.size(); j++) {
					IPManAddressListBean address2 = new IPManAddressListBean();
					address2.setDeptId(deptIdLst.get(j));
					address2.setStartIp(netAddress);
					address2.setEndIp(broadCast);
					address2.setStatus(USED_STATUS);
					int deptUseNum = ipManAddressListMapper.getCountByScopeAndStatusAndDept(address2);
					address2.setStatus(PREEMPT_STATUS);
					int deptPreemptNum = ipManAddressListMapper.getCountByScopeAndStatusAndDept(address2);
					
					IPManSubNetRDeptBean subNetRDeptBean = new IPManSubNetRDeptBean();
					subNetRDeptBean.setSubNetId(subNetId);
					subNetRDeptBean.setDeptId(deptIdLst.get(j));
					subNetRDeptBean.setUsedNum(deptUseNum);
					subNetRDeptBean.setPreemptNum(deptPreemptNum);
					ipManSubNetRDeptMapper.insertSubnetRDept(subNetRDeptBean);
				}
			}
			
			//删除原来的子网与部门关系
			ipManSubNetRDeptMapper.delSubNetDeptBySubNetID(oldSubNetId);
			//删除原来的子网信息
			ipManSubNetInfoMapper.delSubNetInfoById(oldSubNetId);
			return true;
		} catch (NumberFormatException e) {
			logger.error("子网拆分异常："+e);
		}
		return false;
	}

	@Override
	public SubnetDeptTreeBean searchTreeNodes(String treeName) {
		SubnetDeptTreeBean bean = new SubnetDeptTreeBean();
		List<IPManSubNetInfoBean> subnetInfoLst = ipManSubNetInfoMapper.getByIpAddress(treeName);
		if(subnetInfoLst.size() > 0){
			bean.setId("s" + subnetInfoLst.get(0).getSubNetId());
		}else{
			List<IPManSubNetInfoBean> allSubnetLst = ipManSubNetInfoMapper.getAllSubNetInfo();
			for (int j = 0; j < allSubnetLst.size(); j++) {
				List<IPManSubNetInfoBean> subnaetAndDeptLst = ipManSubNetInfoMapper.getBySubnetAndDept(allSubnetLst.get(j).getSubNetId(), treeName);
				if(subnaetAndDeptLst.size() > 0){
					bean.setId("d" + subnaetAndDeptLst.get(0).getDeptId());
					bean.setParentId("s" + subnaetAndDeptLst.get(0).getSubNetId());
					bean.setName(subnaetAndDeptLst.get(0).getDeptName());
				}
			}
		}
		return bean;
	}

	@Override
	public boolean isSplit(int subNetId, String ipAddress, String subNetMark,
			int splitNum) {
		Nets net =  IPPoolUtil.getSubnetting(ipAddress,subNetMark ,splitNum);
		int ipCount = net.getIpCount();
		if(ipCount <= 1){
			return false;
		}
		return true;
	}

	@Override
	public int getSubnetCount(IPManSubNetInfoBean bean) {
		List<IPManSubNetInfoBean> subnetLst = ipManSubNetInfoMapper.getAllSubnetByCondition(bean);
		return subnetLst.size();
	}

	@Override
	public String getFreeIds(IPManAddressListBean bean) {
		String ids = "";
		List<IPManAddressListBean> addressLst = ipManAddressListMapper.getFreeAddress(bean);
		for (int i = 0; i < addressLst.size(); i++) {
			ids += addressLst.get(i).getId() + ",";
		}
		return ids;
	}

	@Override
	public boolean doUpdateSubnet(IPManSubNetInfoBean bean) {
		try {
			logger.info("更新的子网ID为：" + bean.getSubNetId());
			ipManSubNetInfoMapper.updateSubnetInfoByID(bean);
			return true;
		} catch (Exception e) {
			logger.error("更新子网异常：",e);
		}
		return false;
	}

	@Override
	public boolean delSubNetDeptBySubNetID(int subNetId) {
		try {
			ipManSubNetRDeptMapper.delSubNetDeptBySubNetID(subNetId);
			return true;
		} catch (Exception e) {
			logger.error("删除子网部门关系异常：", e);
		}
		return false;
	}

	@Override
	public List<IPManAddressListBean> getByDeptIdAndStatus(Page<IPManAddressListBean> page) {
		List<IPManAddressListBean> list = ipManAddressListMapper.getByDeptIdAndStatus(page);
		for (IPManAddressListBean b : list) {
			if (null != b.getOfficeID()) {
				IPManAddressListBean addrInfo = ipManAddressListMapper.getAddressInfoByOfficeID(b.getOfficeID());
				if(null != addrInfo) {
					b.setBuilding(addrInfo.getBuilding());
					b.setFloorName(addrInfo.getFloorName());
				}
			}
		}
		
		return list;
	}

	@Override
	public IPManAddressListBean getByDeptIdAndUserId(Integer deptId,
			Integer userId) {
		return ipManAddressListMapper.getByDeptIdAndUserId(deptId, userId);
	}

	@Override
	public IPManAddressListBean getByIPAddress(String ipAddress) {
		return ipManAddressListMapper.getByIPAddress(ipAddress);
	}

	@Override
	public boolean updateAddressByID(IPManAddressListBean bean) {
		try {
			ipManAddressListMapper.updateAddressByID(bean);
			return true;
		} catch (Exception e) {
			logger.error("更新IP信息异常：", e);
		}
		return false;
	}

	@Override
	public List<IPManAddressListBean> getByUserId(
			Page<IPManAddressListBean> page) {
		return ipManAddressListMapper.getByUserId(page);
	}

	@Override
	public List<IPManAddressListBean> getByUserId(Integer userId, Integer netType) {
		return ipManAddressListMapper.getByUserId2(userId, netType);
	}

	@Override
	public ProviderInfoBean getOutDeptByUserId(Integer userId) {
		ProviderInfoBean p = this.ipManAddressListMapper.getOutDeptByUserId(userId);
		return p;
	}
	
	
}
