package com.fable.insightview.platform.dutymanager.duty.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.dutymanager.DutyDataValidate;
import com.fable.insightview.platform.dutymanager.duty.entity.DutyStatistics;
import com.fable.insightview.platform.dutymanager.duty.entity.PfDuty;
import com.fable.insightview.platform.dutymanager.duty.entity.PfDutyReserve;
import com.fable.insightview.platform.dutymanager.duty.entity.PfOrdersDuty;
import com.fable.insightview.platform.dutymanager.duty.entity.PfUsersOrder;
import com.fable.insightview.platform.dutymanager.duty.mapper.DutyChangeMapper;
import com.fable.insightview.platform.dutymanager.duty.mapper.DutyMapper;
import com.fable.insightview.platform.dutymanager.duty.mapper.DutyReserveMapper;
import com.fable.insightview.platform.dutymanager.duty.mapper.OrdersDutyMapper;
import com.fable.insightview.platform.dutymanager.duty.mapper.UsersOrderMapper;
import com.fable.insightview.platform.dutymanager.duty.service.IDutyService;
import com.fable.insightview.platform.dutymanager.dutyorder.entity.DutyOrder;
import com.fable.insightview.platform.dutymanager.dutyorder.mapper.DutyOrderMapper;
import com.fable.insightview.platform.dutymanager.dutyorder.service.DutyOrderService;
import com.fable.insightview.platform.message.entity.PfMessage;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.sysinit.SystemParamInit;
import com.google.gson.Gson;

@Service
public class DutyServiceImpl implements IDutyService {

	@Autowired
	private DutyMapper dutyMapper;
	@Autowired
	private OrdersDutyMapper ordersDutyMpper;
	@Autowired
	private UsersOrderMapper userOrderMapper;
	@Autowired
	private DutyReserveMapper dutyReserveMapper;
	@Autowired
	private DutyOrderMapper orderMapper;
	@Autowired
	private DutyChangeMapper dutyChangeMapper;
	@Autowired
	private DutyOrderService dutyOrderService;

	private static final Logger LOGGER = LoggerFactory.getLogger(DutyServiceImpl.class);

	@Override
	public List<Map<String, String>> queryDutyers(Map<String, Object> params) {
		return dutyMapper.queryDutyers(params);
	}

	@Override
	public List<Map<String, Object>> list(@SuppressWarnings("rawtypes") Page page) {
		List<Map<String, Object>> dutys = dutyMapper.list(page);
		if (dutys.isEmpty()) {
			return new ArrayList<Map<String, Object>>();
		}
		return getOrders(dutys, false);
	}

	@Override
	public List<Map<String, Object>> queryOrders(String ids) {
		return dutyMapper.queryOrders(ids);
	}

	@Override
	public boolean validateDutyDate(String dutyDate, Integer dutyId) {
		Map<String, Object> duty = dutyMapper.queryByDutyDate(dutyDate, dutyId);
		return duty == null || duty.isEmpty() ? true : false;
	}

	@Override
	public void insert(PfDuty duty) {
		dutyMapper.insert(duty);
		this.insertReserve(duty);
		if (null != duty.getOrders() && !duty.getOrders().isEmpty()) {
			this.insertOrders(duty.getOrders(), duty.getId());
		}
	}

	@Override
	public Map<String, Object> query(int dutyId) {
		Map<String, Object> duty = dutyMapper.query(dutyId);
		duty.putAll(queryReserves(dutyId));
		return duty;
	}

	/* 查询值班记录下的备勤人员信息 */
	private Map<String, Object> queryReserves(int dutyId) {
		Map<String, Object> duty = new HashMap<String, Object>();
		List<Map<String, Object>> reserves = dutyReserveMapper.query(dutyId);
		duty.put("reserves", reserves);
		duty.put("reservesize", reserves.size());
		String reserveIds = null, reserveNames = null;
		Map<String, Object> reserve = null;
		for (int i = 0, size = reserves.size(); i < size; i++) {
			reserve = reserves.get(i);
			if (i == 0) {
				reserveIds = reserve.get("UserId").toString();
				reserveNames = reserve.get("UserName").toString();
			} else {
				reserveIds += "," + reserve.get("UserId");
				reserveNames += "," + reserve.get("UserName");
			}
		}
		duty.put("reserveIds", reserveIds);
		duty.put("reserveNames", reserveNames);
		return duty;
	}

	@Override
	public List<Map<String, Object>> queryOrders(int dutyId) {
		List<Map<String, Object>> orders = ordersDutyMpper.querySingle(dutyId);
		List<Map<String, Object>> users = userOrderMapper.querySingle(dutyId);
		List<Map<String, Object>> userOfOrder = null;
		String userIds = null, dutyers = null;
		for (Map<String, Object> order : orders) {
			userOfOrder = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> user : users) {
				if (order.get("ID").toString().equals(user.get("OrderIdOfDuty").toString())) {
					userOfOrder.add(user);
					userIds = order.get("UserIds") + "";
					dutyers = order.get("Dutyers") + "";
					if ("1".equals(user.get("UserType").toString())) {/* 值班负责人 */
						order.put("Principal", user.get("UserName"));
						order.put("PrincipalId", user.get("UserId"));
					} else {
						order.put("Dutyers", "null".equals(dutyers) ? user.get("UserName") : dutyers + "," + user.get("UserName"));
						order.put("UserIds", "null".equals(userIds) ? user.get("UserId") : userIds + "," + user.get("UserId"));
					}
				}
			}
			order.put("users", userOfOrder);
			order.put("orderIdOfDuty", order.get("ID"));
			order.put("ID", order.get("OrderId"));
		}
		return orders;
	}

	@Override
	public void update(PfDuty duty) {
		dutyMapper.update(duty);
		dutyReserveMapper.delete(duty.getId());/* 删除备勤人员 */
		this.insertReserve(duty);/* 新增备勤人员 */
		if (StringUtils.isNotEmpty(duty.getOrderIds())) {/* 删除值班班次 */
			this.deleteMultiOrder(duty.getOrderIds());
		}
		if (null != duty.getOrders() && !duty.getOrders().isEmpty()) {/* 添加值班班次信息 */
			this.insertOrders(duty.getOrders(), duty.getId());
		}
	}

	/* 获取值班记录下的班次信息 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getOrders(List<Map<String, Object>> dutys, boolean moreInfo) {
		List<Integer> dutyIds = new ArrayList<Integer>();
		for (Map<String, Object> duty : dutys) {
			dutyIds.add(Integer.parseInt(duty.get("ID") + ""));
			duty.put("DutyDate", duty.get("DutyDate") + "(" + getWeekOfDate(duty.get("DutyDate").toString()) + ")");
		}
		if (moreInfo) {
			String file = SystemParamInit.getValueByKey("fileServerURL");/* 查询用户头图标路径 */
			List<Map<String, Object>> reserves = queryReserves(dutyIds);
			for (Map<String, Object> duty : dutys) {
				duty.put("fileUrl", file);
				for (Map<String, Object> reserve : reserves) {/* 查询值班备勤人员信息 */
					if (Integer.parseInt(duty.get("ID") + "") == Integer.parseInt(reserve.get("DutyId") + "")) {
						if (null == duty.get("reserves")) {
							duty.put("reserves", reserve.get("UserName"));
						} else {
							duty.put("reserves", duty.get("reserves") + "," + reserve.get("UserName"));
						}
					}
				}
			}
		}
		List<Map<String, Object>> orders = ordersDutyMpper.query(dutyIds);// 值班中的所有值班班次
		List<Map<String, Object>> users = userOrderMapper.query(dutyIds);// 值班中的所有值班人员
		List<Map<String, Object>> userNames = null;// 一个班次中的值班人员
		List<Map<String, Object>> orderInfo = null;// 一个值班中的班次信息
		String orderId = null;
		for (Map<String, Object> order : orders) {/* 封装值班记录的值班班次信息 */
			userNames = new ArrayList<Map<String, Object>>();
			orderId = order.get("ID") + "";
			for (Map<String, Object> user : users) {/* 封装班次值班人员信息 */
				if (orderId.equals(user.get("OrderIdOfDuty").toString())) {
					userNames.add(user);
				}
			}
			order.put("userNames", userNames);
			for (Map<String, Object> duty : dutys) {
				if (order.get("DutyId").toString().equals(duty.get("ID").toString())) {
					orderInfo = (List<Map<String, Object>>) duty.get("order");
					if (null == orderInfo) {
						orderInfo = new ArrayList<Map<String, Object>>();
					}
					orderInfo.add(order);
					duty.put("order", orderInfo);
				}
			}
		}
		return dutys;
	}

	/* 查询多个值班记录下的备勤人员 */
	private List<Map<String, Object>> queryReserves(List<Integer> dutyIds) {
		return dutyReserveMapper.queryMulti(dutyIds);
	}

	/* 添加值班备勤人员信息 */
	private void insertReserve(PfDuty duty) {
		String[] readys = this.strToArr(duty.getReadys());
		PfDutyReserve reserve = null;
		for (int i = 0, length = readys.length; i < length; i++) {
			reserve = new PfDutyReserve(duty.getId(), Integer.parseInt(readys[i]));
			dutyReserveMapper.insert(reserve);
		}
	}

	/* 添加值班班次信息 */
	private void insertOrders(List<Map<String, Object>> orders, int dutyId) {
		PfOrdersDuty orderDuty = null;
		for (Map<String, Object> order : orders) {
			orderDuty = new PfOrdersDuty(dutyId, order.get("Title").toString(), order.get("BeginPoint").toString(), order.get("EndPoint").toString(), Integer.parseInt(order.get("ID").toString()));
			orderDuty.setIntervalDays(Integer.parseInt(order.get("IntervalDays").toString()));
			orderDuty.setExchangeStart((Date) order.get("exchangeStart"));
			orderDuty.setExchangeEnd((Date) order.get("exchangeEnd"));
			orderDuty.setForceTime((Date) order.get("forceTime"));
			ordersDutyMpper.insert(orderDuty);/* 导入值班班次最多5个 */
			if (!StringUtils.isEmpty(order.get("PrincipalId").toString())) {/* 添加班次负责人员信息 */
				this.insertOrderUser(orderDuty, Integer.parseInt(order.get("PrincipalId").toString()), 1);
			}
			if (order.get("UserIds") != null) {
				String[] userIds = this.strToArr(order.get("UserIds").toString());
				for (int i = 0, length = userIds.length; i < length; i++) {
					this.insertOrderUser(orderDuty, Integer.parseInt(userIds[i]), 2);
				}
			}
		}
	}

	/* 添加班次值班人员信息 */
	private void insertOrderUser(PfOrdersDuty orderDuty, int userId, int userType) {
		PfUsersOrder usersOrder = new PfUsersOrder(orderDuty.getId(), orderDuty.getDutyId(), userId, userType);
		userOrderMapper.insert(usersOrder);
	}

	/* 字符转换数组 */
	private String[] strToArr(String str) {
		return StringUtils.isEmpty(str) ? new String[0] : str.split(",");
	}

	/* 当前日期是星期几 */
	private String getWeekOfDate(String date) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(dateFm.parse(date));
		} catch (ParseException e) {
			LOGGER.error("值班管理：日期获取星期几异常：{}", e);
		}
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDays[w < 0 ? 0 : w];
	}

	@Override
	public void deleteSingleOrder(int orderIdOfDuty) {
		userOrderMapper.deleteByOrderId(orderIdOfDuty);/* 删除班次中的值班人员 */
		ordersDutyMpper.deleteSingle(orderIdOfDuty);/* 删除值班班次信息 */
	}

	@Override
	public void delete(int dutyId) {
		dutyReserveMapper.delete(dutyId);/* 删除值班备勤人员 */
		userOrderMapper.deleteByDutyId(dutyId);/* 删除值班记录中的值班人员 */
		ordersDutyMpper.delete(dutyId);/* 删除值班班次信息 */
		dutyMapper.deleteLogs(dutyId);/* 删除值班记录日志 */
		dutyMapper.deleteChanges(dutyId);/* 删除值班调班记录信息 */
		dutyMapper.delete(dutyId);/* 删除值班记录 */
	}

	@Override
	public List<Map<String, Object>> queryMulti(Map<String, Object> params) {
		List<Map<String, Object>> dutys = dutyMapper.queryMulti(params);
		if (dutys.isEmpty()) {
			return new ArrayList<Map<String, Object>>();
		}
		return getOrders(dutys, true);
	}

	/**
	 * 准备导出数据
	 */
	@Override
	public void exportData(OutputStream out, List<Map<String, Object>> data, String order) {
		Map<String, List<Map<String, String>>> map = parseData(data);
		// 声明工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 声明工作表
		HSSFSheet sheet = workbook.createSheet();
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// 设置表头
		setExcelHeader(workbook, sheet);

		Set<String> keys = map.keySet();

		List<String> keyList = Arrays.asList(keys.toArray(new String[] {}));
		Collections.sort(keyList);
		if ("desc".equals(order)) {
			Collections.reverse(keyList);
		}

		int rownum = 1;
		for (String key : keyList) {
			List<Map<String, String>> list = map.get(key);
			int size = list.size();
			int firstRow = rownum;
			int lastRow = rownum + size - 1;
			for (Map<String, String> rowValue : list) {
				Row row = sheet.createRow(rownum);
				Cell cell0 = row.createCell(0);
				cell0.setCellValue(rowValue.get("dutyDate"));
				cell0.setCellStyle(style);
				Cell cell1 = row.createCell(1);
				cell1.setCellValue(rowValue.get("dutyTime"));
				cell1.setCellStyle(style);
				Cell cell2 = row.createCell(2);
				cell2.setCellValue(rowValue.get("dutyers"));
				cell2.setCellStyle(style);
				Cell cell3 = row.createCell(3);
				cell3.setCellValue(rowValue.get("title"));
				cell3.setCellStyle(style);
				Cell cell4 = row.createCell(4);
				cell4.setCellValue(rowValue.get("dutyHeader"));
				cell4.setCellStyle(style);
				Cell cell5 = row.createCell(5);
				cell5.setCellValue(rowValue.get("begin"));
				cell5.setCellStyle(style);
				Cell cell6 = row.createCell(6);
				cell6.setCellValue(rowValue.get("end"));
				cell6.setCellStyle(style);
				Cell cell7 = row.createCell(7);
				cell7.setCellValue(rowValue.get("leader"));
				cell7.setCellStyle(style);
				Cell cell8 = row.createCell(8);
				cell8.setCellValue(rowValue.get("dutyReserves"));
				cell8.setCellStyle(style);
				Cell cell9 = row.createCell(9);
				cell9.setCellValue(rowValue.get("level"));
				cell9.setCellStyle(style);
				rownum++;
			}
			// 判断是否需要合并单元格
			if (size > 1) {
				// 合并值班日期
				sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, 0, 0));
				// 合并值班时间
				sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, 1, 1));
				// 合并带班领导
				sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, 7, 7));
				// 合并备勤人员
				sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, 8, 8));
				// 合并备勤等级
				sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, 9, 9));
			}
		}

		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public Map<String, Object> importData(String[] titles, List<Map<Integer, Object>> data, int userId, String minD, String maxD, int recordCount) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		List<Map<String, String>> dutyers = dutyMapper.queryDutyers(params);/* 查询所有值班人 */
		List<DutyOrder> orders = orderMapper.selectAllDutyOrder();/* 查询所有值班班次 */
		params.put("begin", minD);
		params.put("end", maxD);
		List<Map<String, Object>> dutys = dutyMapper.queryMulti(params);/* 所有值班日期 */
		List<String> dates = new ArrayList<String>();/* 所有日期信息 */
		List<String> users = new ArrayList<String>();/* 值班人名称 */
		Map<String, String> userIds = new HashMap<String, String>();/* 保存用户ID */
		List<String> orderNames = new ArrayList<String>();/* 班次名称 */
		Map<String, DutyOrder> orderIds = new HashMap<String, DutyOrder>();/* 班次IDs */
		for (Map<String, Object> duty : dutys) {
			dates.add(String.valueOf(duty.get("DutyDate")));
		}
		for (DutyOrder order : orders) {/* 获取系统中的所有班次信息[班次名称] */
			orderNames.add(order.getTitle());
			orderIds.put(order.getTitle(), order);
		}
		for (Map<String, String> duty : dutyers) {/* 获取所有的值班人员的人名信息 */
			users.add(duty.get("name"));
			userIds.put(duty.get("name").toString(), String.valueOf(duty.get("id")));
		}
		String validT = DutyDataValidate.validateTitle(orderNames, titles);/* 验证值班班次信息是否存在 */
		if (!StringUtils.isEmpty(validT)) {
			result.put("title", validT);
			return result;
		}
		Map<String, Object> dataCon = DutyDataValidate.validateData(data, users, dates, titles, orderNames);/* 验证导入值班班次数据信息 */
		List<Map<Integer, Object>> effect = (List<Map<Integer, Object>>) dataCon.get("effect");/* 导入的有效数据 */
		List<PfDuty> finalDutys = packageData(effect, orderIds, userIds, titles);
		for (PfDuty pd : finalDutys) {
			List<Map<String, Object>> os = pd.getOrders();
			for(Map<String, Object> order : os) {
				String id = order.get("ID").toString();
				DutyOrder dutyOrder = dutyOrderService.findDutyOrderById(Integer.valueOf(id));
				String exchangeStart = dutyOrder.getExchangeStart();
				String exchangeEnd = dutyOrder.getExchangeEnd();
				String forceTime = dutyOrder.getForceTime();
				Integer intervalDays = dutyOrder.getIntervalDays();
				Date dutyDate = pd.getDutyDate();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dutyDate);
				calendar.add(Calendar.DATE, intervalDays);
				String newDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
				exchangeStart = newDate + " " + exchangeStart + ":00";
				exchangeEnd = newDate + " " + exchangeEnd + ":00";
				forceTime = newDate + " " + forceTime + ":00";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					order.put("exchangeStart", sdf.parse(exchangeStart));
					order.put("exchangeEnd", sdf.parse(exchangeEnd));
					order.put("forceTime", sdf.parse(forceTime));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			this.insert(pd);
		}
		result.put("error", "文件共有值班记录数为：" + recordCount + ";导入成功记录数为：" + effect.size() + (null == dataCon.get("error") ? "" : "<br/>失败原因：<br/>" + dataCon.get("error")));
		return result;
	}

	/**
	 * （导入数据【解析excel中的数据】）封装值班记录信息
	 * 
	 * @param srcData
	 * @param orderIds
	 * @param users
	 * @param titles
	 * @return
	 */
	private List<PfDuty> packageData(List<Map<Integer, Object>> srcData, Map<String, DutyOrder> orderIds, Map<String, String> users, String[] titles) {
		if (null == srcData) {
			return new ArrayList<PfDuty>();
		}
		List<PfDuty> dutys = new ArrayList<PfDuty>();
		int columns = titles.length;
		List<Map<String, Object>> ordersD = null;
		Map<String, Object> order = null;
		Map<Integer, Object> excDuty = null;
		String[] dutyerUser = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		PfDuty duty = null;
		DutyOrder dutyOrder = null;
		try {
			for (int i = 0; i < srcData.size(); i++) {
				excDuty = srcData.get(i);
				duty = new PfDuty();
				ordersD = new ArrayList<Map<String, Object>>();
				duty.setDutyDate(sdf.parse(String.valueOf(excDuty.get(columns - 3))));
				duty.setLevel(Integer.valueOf(excDuty.get(columns - 1).toString()));
				if (excDuty.containsKey(0)) {/* 导入第一列为带班领导 */
					dutyerUser = (String[]) excDuty.get(0);
					duty.setLeaderId(Integer.parseInt(users.get(dutyerUser[0])));
				}
				for (int j = 1; j < columns - 3; j++) {/* 第3列..最大5列为值班班次信息 */
					order = new HashMap<String, Object>();
					String title = DutyDataValidate.getRealTitle(titles[j], new ArrayList<String>(orderIds.keySet()));
					;
					dutyOrder = orderIds.get(title);
					order.put("ID", dutyOrder.getId() + "");
					order.put("Title", dutyOrder.getTitle());
					order.put("BeginPoint", dutyOrder.getBeginPoint());
					order.put("EndPoint", dutyOrder.getEndPoint());
					order.put("IntervalDays", String.valueOf(dutyOrder.getIntervalDays()));
					dutyerUser = (String[]) excDuty.get(j);
					for (int m = 0, size = dutyerUser.length; m < size; m++) {
						if (m == 0) {
							order.put("PrincipalId", users.get(dutyerUser[0]));/* 设置值班负责人 */
						} else if (order.containsKey("UserIds")) {
							order.put("UserIds", order.get("UserIds") + "," + users.get(dutyerUser[m]));
						} else {
							order.put("UserIds", users.get(dutyerUser[m]));
						}
					}
					ordersD.add(order);
				}
				duty.setOrders(ordersD);
				dutyerUser = (String[]) excDuty.get(columns - 2);/* 导入模板最后一列为备勤人员信息 */
				if (null != dutyerUser) {
					for (int m = 0, size = dutyerUser.length; m < size; m++) {
						if (m == 0) {
							duty.setReadys(users.get(dutyerUser[0]));
						} else {
							duty.setReadys(duty.getReadys() + "," + users.get(dutyerUser[m]));
						}
					}
				}
				dutys.add(duty);
			}
		} catch (ParseException e) {
			LOGGER.error("解析日期格式异常：{}", e);
		}
		return dutys;
	}

	@SuppressWarnings("unchecked")
	public Map<String, List<Map<String, String>>> parseData(List<Map<String, Object>> data) {
		Map<String, List<Map<String, String>>> result = new HashMap<String, List<Map<String, String>>>();
		for (Map<String, Object> temp : data) {
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			// 值班日期（yyyy-MM-dd(星期x)格式）
			String date = temp.get("DutyDate").toString();
			String dutyDate = date.substring(0, 10);
			String dutyTime = date.substring(11, 14);
			String leaderName = null;
			if (temp.get("Leader") == null || "".equals(temp.get("Leader"))) {
				leaderName = "";
			} else {
				leaderName = temp.get("Leader").toString();
			}
			String level = temp.get("Level").toString();
			if(level != null && level.length()>0) {
				switch (level) {
				case "1":
					level = "一级";
					break;
				case "2":
					level = "二级";
					break;
				case "3":
					level = "三级";
					break;
				case "4":
					level = "四级";
					break;
				}
			}
			StringBuilder dutyReserves = new StringBuilder();
			// 根据值班id获取值班备勤人员
			Integer dutyId = (Integer) temp.get("ID");
			List<Map<String, Object>> reserves = dutyReserveMapper.query(dutyId);
			for (Map<String, Object> reserve : reserves) {
				Integer userId = (Integer) reserve.get("UserId");
				String username = dutyChangeMapper.selectUsernameByUserId(userId);
				if (dutyReserves.toString().length() == 0) {
					dutyReserves.append(username);
				} else {
					dutyReserves.append(", " + username);
				}
			}
			List<Map<String, Object>> orders = (List<Map<String, Object>>) temp.get("order");
			for (Map<String, Object> temp1 : orders) {
				Map<String, String> map = new HashMap<String, String>();
				String beginPoint = temp1.get("BeginPoint").toString();
				String endPoint = temp1.get("EndPoint").toString();
				String title = temp1.get("Title").toString();
				List<Map<String, Object>> userNames = (List<Map<String, Object>>) temp1.get("userNames");
				String dytyHeader = null;
				StringBuilder dutyers = new StringBuilder();
				for (Map<String, Object> temp2 : userNames) {
					String userType = temp2.get("UserType").toString();
					String userName = temp2.get("UserName").toString();
					if (userType.equals("1")) {
						dytyHeader = userName;
					}
					if (dutyers.toString().length() == 0) {
						dutyers.append(userName);
					} else {
						dutyers.append(", " + userName);
					}
				}
				map.put("dutyDate", dutyDate); // 值班日期
				map.put("dutyTime", dutyTime); // 值班时间
				map.put("dutyers", dutyers.toString()); // 值班人
				map.put("title", title); // 值班班次
				map.put("dutyHeader", dytyHeader); // 值班负责人
				map.put("begin", beginPoint); // 开始时间
				map.put("end", endPoint); // 结束时间
				map.put("leader", leaderName); // 带班领导
				map.put("dutyReserves", dutyReserves.toString()); //备勤人员
				map.put("level", level);
				list.add(map);
			}
			result.put(dutyDate, list);
		}
		return result;
	}

	// 设置表头
	public void setExcelHeader(HSSFWorkbook workbook, HSSFSheet sheet) {
		Row row = sheet.createRow(0);
		row.setHeight((short) 500);
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFont(font);
		Cell cell0 = row.createCell(0);
		cell0.setCellValue("值班日期");
		cell0.setCellStyle(style);
		Cell cell1 = row.createCell(1);
		cell1.setCellValue("值班时间");
		cell1.setCellStyle(style);
		Cell cell2 = row.createCell(2);
		cell2.setCellValue("值班人");
		cell2.setCellStyle(style);
		Cell cell3 = row.createCell(3);
		cell3.setCellValue("值班班次");
		cell3.setCellStyle(style);
		Cell cell4 = row.createCell(4);
		cell4.setCellValue("值班负责人");
		cell4.setCellStyle(style);
		Cell cell5 = row.createCell(5);
		cell5.setCellValue("开始时间");
		cell5.setCellStyle(style);
		Cell cell6 = row.createCell(6);
		cell6.setCellValue("结束时间");
		cell6.setCellStyle(style);
		Cell cell7 = row.createCell(7);
		cell7.setCellValue("带班领导");
		cell7.setCellStyle(style);
		Cell cell8 = row.createCell(8);
		cell8.setCellValue("备勤人员");
		cell8.setCellStyle(style);
		Cell cell9 = row.createCell(9);
		cell9.setCellValue("备勤等级");
		cell9.setCellStyle(style);
	}

	@Override
	public void deleteMultiOrder(String orderIds) {
		userOrderMapper.deleteMulti(orderIds);/* 批量删除班次中的值班人员 */
		ordersDutyMpper.deleteMulti(orderIds);/* 批量删除值班班次信息 */
	}

	@Override
	public int findOrgIdByUserId(int userId) {
		return dutyMapper.selectOrgidByUserid(userId);
	}

	@Override
	public List<PfMessage> queryMsg() {
		List<PfMessage> msg = new ArrayList<PfMessage>();
		String dutyDate = getTomorrow();/* 明天 */
		Map<String, Object> duty = dutyMapper.queryByDutyDate(dutyDate, null);/* 获取明天是否有值班排班 */
		LOGGER.info("短信通知值班日期[" + dutyDate + "]；值班班次信息：" + new Gson().toJson(duty));
		if (null == duty || duty.isEmpty()) {
			return msg;
		}
		int dutyId = Integer.parseInt(duty.get("ID") + "");
		Map<String, Object> leaderInfo = dutyMapper.query(dutyId);/* 值班信息 */
		LOGGER.info("短信通知值班日期[" + dutyDate + "]；值班信息：" + new Gson().toJson(leaderInfo));
		StringBuilder msgB = new StringBuilder();
		if (null != leaderInfo.get("Leader")) {
			msgB.append("明天(").append(dutyDate).append(")带班领导[").append(leaderInfo.get("Leader")).append("]值班民警");
		} else {
			msgB.append("明天(").append(dutyDate).append(")").append("值班民警");
		}
		List<Map<String, Object>> dutyers = userOrderMapper.queryNoRepeatDuty(dutyId);/* 查询值班人信息 */
		LOGGER.info("短信通知值班日期[" + dutyDate + "]；值班人信息：" + new Gson().toJson(dutyers));
		for (Map<String, Object> dutyer : dutyers) {/* 获取短信提示信息 */
			msgB.append("[").append(dutyer.get("UserName")).append("]");
		}
		String msgInfo = msgB.toString();/* 短信信息 */
		List<String> existNo = new ArrayList<String>();/* 保存一份电话号码 */
		Object mobilePhone = null;
		dutyers.add(leaderInfo);
		for (Map<String, Object> dutyer : dutyers) {
			mobilePhone = dutyer.get("MobilePhone");
			LOGGER.info("短信通知值班日期[" + dutyDate + "]；值班人联系方式：" + mobilePhone);
			if (null != mobilePhone && StringUtils.isNotEmpty(String.valueOf(mobilePhone)) && !existNo.contains(mobilePhone)) {/* 值班人短信信息 */
				msg.add(new PfMessage(String.valueOf(mobilePhone), msgInfo));
				existNo.add(String.valueOf(mobilePhone));
			}
		}
		return msg;
	}

	/* 获取明天日期 */
	private String getTomorrow() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}

	/* 获取特定时间相隔几天日期 */
	private Date getBeforeOrAfter(Date pointTime, int intervalDays) {
		if (null == pointTime) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(pointTime);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + intervalDays);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/*解析时间字符*/
	private Date getRealDate(String date, String hours, String intervals) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String realHour = hours.substring(0, 5);
			Calendar cal = Calendar.getInstance();
			cal.setTime(df.parse(date + " " + realHour));
			if (StringUtils.isNotEmpty(intervals)) {
				cal.set(Calendar.DATE, cal.get(Calendar.DATE) + Integer.valueOf(intervals));
			}
			return cal.getTime();
		} catch (ParseException e) {
			LOGGER.error("值班管理：查询特定时间点值班信息, 解析时间异常{}", e);
		}
		return null;
	}

	@Override
	public Map<String, Object> queryPointInfo(Date pointTime) {
		Date yesterday = getBeforeOrAfter(pointTime, -1);
		Date tomorrow = getBeforeOrAfter(pointTime, 1);
		List<Map<String, Object>> dutyers = dutyMapper.queryRange(yesterday, tomorrow, 1);/*获取昨天、今天、明天的值班信息*/
		Map<String, Object> dutyInfo = new HashMap<String, Object>();
		if (null == dutyers || dutyers.isEmpty()) {
			return dutyInfo;
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for (Map<String, Object> dutyer : dutyers) {
			String beginPoint = String.valueOf(dutyer.get("BeginPoint"));
			String endPoint = String.valueOf(dutyer.get("EndPoint"));
			String intervalDays = String.valueOf(dutyer.get("IntervalDays"));
			String date = df.format(dutyer.get("DutyDate"));
			Date start = getRealDate(date, beginPoint, 0 + "");
			Date end = getRealDate(date, endPoint, intervalDays);
			if (null != start && null != end && start.getTime() < pointTime.getTime() && pointTime.getTime() < end.getTime()) {
				return dutyer;
			}
		}
		return dutyInfo;
	}
	
	@Override
	public List<Map<String, Object>> queryPointInfo(Date pointTime, int userType) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Date yesterday = getBeforeOrAfter(pointTime, -1);
		Date tomorrow = getBeforeOrAfter(pointTime, 1);
		List<Map<String, Object>> dutyers = dutyMapper.queryRange(yesterday, tomorrow, userType);/*获取昨天、今天、明天的值班信息*/
		Map<String, Object> dutyInfo = new HashMap<String, Object>();
		if (null == dutyers || dutyers.isEmpty()) {
			return result;
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for (Map<String, Object> dutyer : dutyers) {
			String beginPoint = String.valueOf(dutyer.get("BeginPoint"));
			String endPoint = String.valueOf(dutyer.get("EndPoint"));
			String intervalDays = String.valueOf(dutyer.get("IntervalDays"));
			String date = df.format(dutyer.get("DutyDate"));
			Date start = getRealDate(date, beginPoint, 0 + "");
			Date end = getRealDate(date, endPoint, intervalDays);
			if (null != start && null != end && start.getTime() < pointTime.getTime() && pointTime.getTime() < end.getTime()) {
				result.add(dutyer);
			}
		}
		return result;
	}

	@Override
	public String getPointDuyter(Date pointTime) {
		return String.valueOf(this.queryPointInfo(pointTime).get("UserId"));
	}

	@Override
	public List<Map<String, Object>> queryMultiOriginal(Map<String, Object> params) {
		return dutyMapper.queryMulti(params);
	}

	@Override
	public List<Map<String, Object>> betweenDuty(Date start, Date end, int userType) {
		return dutyMapper.queryRange(start, end, userType);
	}

	@Override
	public List<Map<String, Object>> findAllDutiesAfterDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(date);
		Date d = null;
		try {
			d = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Map<String, Object>> duties = dutyMapper.selectAllDutiesAfterDate(d);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if(duties == null || duties.isEmpty()) {
			return result;
		}
		for(Map<String, Object> dutyer : duties) {
			String beginPoint = String.valueOf(dutyer.get("BeginPoint"));
			String ss = sdf.format(dutyer.get("DutyDate"));
			Date start = getRealDate(ss, beginPoint, 0 + "");
			if (start != null && start.getTime() > date.getTime()) {
				result.add(dutyer);
			}
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> findOrdersOfDutyByDate(Date date) {
		return dutyMapper.selectOrdersOfDutyByDate(date);
	}

	@Override
	public Map<String, Object> findAlarmInfoByDate(Map<String, Object> dateParam) {
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("urgencyAlarmCount", dutyMapper.findUrgencyAlarmCount(dateParam));
		returnMap.put("workOrderDoneCount", dutyMapper.findAllAlarmDoneCount(dateParam));
		returnMap.put("urgencyAlarmAlreadyDoneCount", dutyMapper.findUrgencyAlarmAlreadyDoneCount(dateParam));
		returnMap.put("eventDoneCount", dutyMapper.findEventAlreadyDoneCount(dateParam));
		returnMap.put("workOrderCount", dutyMapper.findAllAlarmDoneCount(dateParam)+dutyMapper.findEventAlreadyDoneCount(dateParam));
		return returnMap;
	}

	@Override
	public List<String> findCurrentDutyers() {
		List<String> result = new ArrayList<String>();
		List<Map<String, Object>> dutyList = this.queryPointInfo(new Date(), 2);
		if(dutyList != null && !dutyList.isEmpty()) {
			String LeaderId = dutyList.get(0).get("LeaderId").toString();
			result.add(LeaderId);
			for(Map<String, Object> duty : dutyList) {
				String UserId = duty.get("UserId").toString();
				result.add(UserId);
			}
		}
		return result;
	}

	@Override
	public List<String> findAllDutyersByOrderId(Integer orderId) {
		return this.dutyMapper.selectAllDutyersByOrderId(orderId);
	}

	@Override
	public List<Map<String, Object>> findUsersOfOrderByUserType(Integer orderId, Integer userType) {
		return dutyMapper.selectUsersOfOrderByUserType(orderId, userType);
	}

	@Override
	public List<Map<String, Object>> findCurrentDutyersForZJW() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> dutyList = this.queryPointInfo(new Date(), 0);
		if(dutyList != null && !dutyList.isEmpty()) {
			for(Map<String, Object> duty : dutyList) {
				String UserId = duty.get("UserId").toString();
				String dutyName = duty.get("dutyName").toString();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("UserId", UserId);
				map.put("UserName", dutyName);
				result.add(map);
			}
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> findOrdersOfDutyBeforeCurrent() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowStr = sdf.format(new Date());
		Date now = null;
		try {
			now = sdf.parse(nowStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return this.dutyMapper.selectOrdersOfDutyBeforeCurrent(now);
	}

	@Override
	public String findLeaderNoticesByDutyId(Integer dutyId) {
		return dutyMapper.selectLeaderNoticesByDutyId(dutyId);
	}
	
	@Override
	public void editLeaderNoticesByDutyId(String notice, Integer dutyId) {
		dutyMapper.updateLeaderNoticesByDutyId(notice, dutyId);
	}

	@Override
	public List<Map<String, Object>> findLatestFinishedOrdersOfDuty() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowStr = sdf.format(new Date());
		Date now = null;
		try {
			now = sdf.parse(nowStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return this.dutyMapper.selectLatestOrdersOfDuty(now);
	}

	@Override
	public List<DutyStatistics> findDutyStatisticsByOrderIdAndType(Integer orderId, Integer type) {
		return this.dutyMapper.selectDutyStatisticsByOrderIdAndType(orderId, type);
	}

	@Override
	public List<Map<String, Object>> queryDutyersOfOder(String orderId) {
		return dutyMapper.queryDutyersOfOder(orderId);
	}

	@Override
	public String isOperator(String userAccount) {
		String oper = "false";
		if(StringUtils.isEmpty(userAccount)) {
			return oper;
		}
		List<Map<String, Object>> orders = this.findOrdersOfDutyBeforeCurrent();/*查询值班班次信息*/
		if (null == orders || orders.isEmpty()) {
			return oper;
		}
		List<Map<String, Object>> users = this.queryDutyersOfOder(orders.get(0).get("OrderOfDutyId") + "");
		for (Map<String, Object> user : users){
			if (userAccount.equals(user.get("leadAccount")) && userAccount.equals(user.get("UserAccount"))) {/*值班人和带班领导*/
				oper = "bath";
				break;
			}
			if (userAccount.equals(user.get("leadAccount"))) {/*验证是否为值班带班领导*/
				oper = "lead";
			}
			if (userAccount.equals(user.get("UserAccount"))) {/*是否为值班人*/
				oper = "true";
			}
		}
		return oper;
	}

	@Override
	public boolean isExchange() {
		Date curr = new Date();
		List<Map<String, Object>> orders = this.findOrdersOfDutyBeforeCurrent();/*查询值班班次信息*/
		if (null == orders || orders.isEmpty()) {
			return false;
		}
		return curr.compareTo((Date)orders.get(0).get("ExchangeStart")) >= 0;
	}

	@Override
	public Map<String, Object> findCurrentOrderTimeForHL() {
		Map<String, Object> result = new HashMap<String, Object>();
		Date pointTime = new Date();
		Date yesterday = getBeforeOrAfter(pointTime, -1);
		Date tomorrow = getBeforeOrAfter(pointTime, 1);
		List<Map<String, Object>> dutyers = dutyMapper.queryRange(yesterday, tomorrow, 1);/*获取昨天、今天、明天的值班信息*/
		if (null == dutyers || dutyers.isEmpty()) {
			return result;
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for (Map<String, Object> dutyer : dutyers) {
			String beginPoint = String.valueOf(dutyer.get("BeginPoint"));
			String endPoint = String.valueOf(dutyer.get("EndPoint"));
			String intervalDays = String.valueOf(dutyer.get("IntervalDays"));
			String date = df.format(dutyer.get("DutyDate"));
			Date start = getRealDate(date, beginPoint, 0 + "");
			Date end = getRealDate(date, endPoint, intervalDays);
			if (null != start && null != end && start.getTime() < pointTime.getTime() && pointTime.getTime() < end.getTime()) {
				result.put("start", start);
				result.put("end", end);
				return result;
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> queryOrder(String orderId) {
		Map<String, Object> order = ordersDutyMpper.queryOrder(orderId);
		if (!(null == order || order.isEmpty())) {
			order.put("StartDate", getRealDate(order.get("DutyDate")+"", order.get("BeginPoint")+"", null));
			order.put("EndDate", getRealDate(order.get("DutyDate")+"", order.get("EndPoint")+"", order.get("IntervalDays")+""));
		}
		return order;
	}

	@Override
	public int queryUsers(String orderId) {
		return userOrderMapper.queryUsers(orderId);
	}

	@Override
	public List<String> queryDutyersInfo(String orderId) {
		return dutyMapper.queryDutyersInfo(orderId);
	}

	@Override
	public Map<String, Object> queryExchanges() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> orders = this.findOrdersOfDutyBeforeCurrent();
		if (null != orders && !orders.isEmpty()) {
			Map<String, Object> current = orders.get(0);//当前值班班次
			String orderId = current.get("OrderOfDutyId")+"";
			result.put("orderId", orderId);
			result.put("forceTime", current.get("ForceTime"));
			result.put("userIds", this.queryDutyersInfo(orderId));
		}
		return result;
	}

}
