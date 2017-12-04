package com.fable.insightview.monitor.database.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class MSSQLServerKPINameDef {
	public static final String BUFFCACHEHITS = "BuffCacheHits";
	public static final String TOTALPAGES = "TotalPages";
	public static final String FREEPAGES = "FreePages";
	public static final String DATABASEPAGES = "DatabasePages";
	public static final String STOLENPAGES="StolenPages";
	public static final String CHECKPOINTPAGES = "CheckpointPages";
	public static final String FREELISTSTALLS = "FreeListStalls";
	public static final String LAZYWRITES = "LazyWrites";
	public static final String PAGEREADS = "PageReads";
	public static final String PAGEWRITES = "PageWrites";
	public static final String PAGELOOKUPS = "PageLookups";	
	public static final String TOTALSERVMEMORY = "TotalServMemory";
	public static final String CONNECTIONMEMORY = "ConnectionMemory";	
	public static final String LOCKMEMORY = "LockMemory";
	public static final String OPTIMIZERMEMORY = "OptimizerMemory";
	public static final String SQLCACHEMEMORY = "SQLCacheMemory";
	public static final String GRANTEDMEMORY = "GrantedMemory";	
	public static final String LOGINS = "Logins";
	public static final String LOGOUTS = "Logouts";
	public static final String USERCONNECTIONS = "UserConnections";
	public static final String CACHEOBJECTS = "CacheObjects";
	public static final String CACHEPAGES = "CachePages";
	public static final String LOCKREQUESTS = "LockRequests";
	public static final String LOCKTIMEOUTS = "LockTimeouts";
	public static final String LOCKWAITS = "LockWaits";
	public static final String DEADLOCKS = "DeadLocks";
	public static final String AVGWAITTIME = "AvgWaitTime";
	public static final String BATCHREQUESTS = "BatchRequests";
	public static final String SQLCOMPILATIONS = "SQLCompilations";	
	public static final String SQLRECOMPILATIONS = "SQLReCompilations";
	public static final String AUTOPARAMATTEMPTS = "AutoParamAttempts";
	public static final String FAILEDAUTOPARAMS = "FailedAutoParams";
	public static final String LATCHWAITS = "LatchWaits";
	public static final String AVGLATCHWAITTIME = "AvgLatchWaitTime";
	public static final String FULLSCANS = "FullScans";
	public static final String RANGESCANS = "RangeScans";	
	public static final String TABLELOCKESCALATIONS = "TableLockEscalations";
	public static final String WORKTABLESCREATED = "WorktablesCreated";
	
	public static final String DATAFILESIZE = "DataFileSize";
	public static final String LOGFILESIZE = "LogFileSize";
	public static final String LOGFILEUSEDSIZE = "LogFileUsedSize";
	public static final String LOGUSAGE = "LogUsage";
	public static final String TRANSACTIONS = "Transactions";
	public static final String ACTIVETRANSACTIONS = "ActiveTransactions";
	public static final String LOGFLUSHES = "LogFlushes";
	public static final String LOGFLUSHWAITS = "LogFlushWaits";
	public static final String LOGFLUSHWAITTIME = "LogFlushWaitTime";
	public static final String LOGGROWTHS = "LogGrowths";	
	public static final String LOGSHRINKS = "LogShrinks";
	public static final String LOGTRUNCATIONS = "LogTruncations";
	/**
	 * 设置查询时间(公共调用方法)
	 * @param request
	 * @param params
	 * @return
	 */
	public static Map<String, Object> queryBetweenTime(HttpServletRequest request, Map<String, Object> params) {
		String time = request.getParameter("time");
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar d = Calendar.getInstance();
		String timeEnd = f.format(d.getTime());
		String timeBegin = "";
		
		if(time != null){
			if ("24H".equals(time)) {// 最近一天
				d.add(Calendar.DATE, -1);
				timeBegin = f.format(d.getTime());
			} else if ("7D".equals(time)) {// 最近一周
				d.add(Calendar.DATE, -7);
				timeBegin = f.format(d.getTime());
			} else if ("Today".equals(time)) {// 今天
				d.add(Calendar.DATE, 0);
				d.set(Calendar.HOUR_OF_DAY, 0);
				d.set(Calendar.MINUTE, 0);
				d.set(Calendar.SECOND, 0);
				timeBegin = f.format(d.getTime());
			} else if ("Yes".equals(time)) {// 昨天
				d.add(Calendar.DAY_OF_MONTH, -1);
				d.set(Calendar.HOUR_OF_DAY, 0);
				d.set(Calendar.MINUTE, 0);
				d.set(Calendar.SECOND, 0);
				timeBegin = f.format(d.getTime());
				d.set(Calendar.HOUR_OF_DAY, 23);
				d.set(Calendar.MINUTE, 59);
				d.set(Calendar.SECOND, 59);
				timeEnd = f.format(d.getTime());
			}else if ("Week".equals(time)) {// 本周
				if(d.get(Calendar.DAY_OF_WEEK)==1){
					d.add(Calendar.DAY_OF_WEEK, -(d.get(Calendar.DAY_OF_WEEK)+5));
				}else{
					d.add(Calendar.DAY_OF_WEEK, -(d.get(Calendar.DAY_OF_WEEK)-2));
				}
				
				d.set(Calendar.HOUR_OF_DAY, 0);
				d.set(Calendar.MINUTE, 0);
				d.set(Calendar.SECOND, 0);
				timeBegin = f.format(d.getTime());
			}else if ("Month".equals(time)) {// 本月
				d.add(Calendar.MONTH, 0);
				//设置为1号,当前日期既为本月第一天 
				d.set(Calendar.DAY_OF_MONTH,1);
				d.set(Calendar.HOUR_OF_DAY, 0);
				d.set(Calendar.MINUTE, 0);
				d.set(Calendar.SECOND, 0);
				timeBegin = f.format(d.getTime());
			}else if ("LastMonth".equals(time)) {// 上月
				 d.add(Calendar.MONTH, -1);
			     d.set(Calendar.DAY_OF_MONTH,1);//上月第一天
			     d.set(Calendar.HOUR_OF_DAY, 0);
				 d.set(Calendar.MINUTE, 0);
				 d.set(Calendar.SECOND, 0);
			     timeBegin = f.format(d.getTime());
			     Calendar cale = Calendar.getInstance();
			     //设置为1号,当前日期既为本月第一天 
			     cale.set(Calendar.DAY_OF_MONTH,0);
			     cale.set(Calendar.HOUR_OF_DAY, 23);
			     cale.set(Calendar.MINUTE, 59);
			     cale.set(Calendar.SECOND, 59);
			     timeEnd = f.format(cale.getTime());

			}
		}
		System.out.println("timeBegin=" + timeBegin + "\ntimeEnd=" + timeEnd);
		params.put("timeBegin", timeBegin);
		params.put("timeEnd", timeEnd);
		return params;
	}
}
