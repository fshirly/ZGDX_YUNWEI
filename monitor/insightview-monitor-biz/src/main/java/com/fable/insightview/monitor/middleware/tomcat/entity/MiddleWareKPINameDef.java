package com.fable.insightview.monitor.middleware.tomcat.entity;

public class MiddleWareKPINameDef {
	public static final String CLASSUNLOADED = "ClassunLoaded";
	public static final String CLASSTOTALLOADED = "ClassTotalLoaded";
	public static final String CLASSLOADED = "classLoaded";
	public static final String SERVICEAVAILABILITY = "ServiceAvailability";
	// data source
	public static final String INITSIZE = "InitSize";
	public static final String MAXACTIVE = "MaxActive";
	public static final String MAXIDLE = "MaxIdle";
	public static final String MINIDLE = "MinIdle";
	public static final String MAXWAIT = "MaxWait";
	// jvm

	public static final String ATTR = "HeapMemoryUsage";
	public static final String HEAPMAX = "HeapMax";
	public static final String HEAPSIZE = "HeapSize";
	public static final String HEAPFREE = "HeapFree";
	public static final String HEAPUSAGE = "HeapUsage";
	public static final String HEAPUSED = "HeapUsed";
	
	// memory pool
	public static final String MEMORYMAX = "MemoryMax";
	public static final String MEMORYSIZE = "MemorySize";
	public static final String MEMORYFREE = "MemoryFree";
	public static final String MEMORYUSAGE = "MemoryUsage";
	public static final String MEMORYUSED = "MemoryUsed";
	// thread pool
	public static final String MAXTHREADS = "MaxThreads";
	public static final String CURRTHREADS = "CurrThreads";
	public static final String BUSYTHREADS = "BusyThreads";
	// WAS --jdbc pool
	public static final String ALLOCATECONNECTIONS = "AllocateConnections";
	public static final String CREATECONNECTIONS = "CreateConnections";
	public static final String CLOSECONNECTIONS = "CloseConnections";
	public static final String CONNECTIONHANDLE = "ConnectionHandle";
	public static final String FAULTCONNECTIONS = "FaultConnections";
	public static final String MANAGEDCONNECTIONS = "ManagedConnections";
	public static final String PREPSTMTCACHEDISCARD = "PrepStmtCacheDiscard";
	public static final String JDBCPOOLSIZE = "JDBCPoolSize";
	public static final String JDBCFREEPOOLSIZE = "JDBCFreePoolSize";
	public static final String JDBCPOOLUSAGE = "JDBCPoolUsage";
	public static final String JDBCTIME = "JDBCTime";
	public static final String USETIME = "UseTime";
	public static final String WAITTIME = "WaitTime";
	
	// jta
	public static final String JTAGLOBALBEGUN = "JtaGlobalBegun";
	public static final String JTAGLOBALINVOLVED = "JtaGlobalInvolved";
	public static final String JTALOCALBEGUN = "JtaLocalBegun";
	public static final String JTAACTIVE = "JtaActive";
	public static final String JTALOCALACTIVE = "JtaLocalActive";
	public static final String JTAGLOBALTRANTIME = "JtaGlobalTranTime";
	public static final String JTALOCALTRANTIME = "JtaLocalTranTime";
	public static final String JTAGLOBALCOMPLETIONTIME = "JtaGlobalCompletionTime";
	public static final String JTAGLOBALPREPARETIME = "JtaGlobalPrepareTime";
	public static final String JTAGLOBALCOMMITTIME = "JtaGlobalCommitTime";
	public static final String JTALOCALCOMPLETIONTIME = "JtaLocalCompletionTime";
	public static final String JTALOCALCOMMITTIME = "JtaLocalCommitTime";
	public static final String JTAOPTIMIZATION = "JtaOptimization";
	public static final String JTACOMMITTED = "JtaCommitted";
	public static final String JTALOCALCOMMITTED = "JtaLocalCommitted";
	public static final String JTAROLLEDBACK = "JtaRolledback";
	public static final String JTALOCALROLLEDBACK = "JtaLocalRolledback";
	public static final String JTAGLOBALTIMEOUT = "JtaGlobalTimeout";
	public static final String JTALOCALTIMEOUT = "JtaLocalTimeout";
	
	public static final String JTAGLOBALACTIVE = "JtaGlobalActive";
	public static final String JTAGLOBALCOMMITTED = "JtaGlobalCommitted";
	public static final String JTAGLOBALROLLEDBACK = "JtaGlobalRolledback";
	public static final String JTAGLOBALOPTIMIZATION = "JtaGlobalOptimization";
	// jvm
	// public static final String HEAPSIZE = "HeapSize";
	// public static final String HEAPFREE = "HeapFree";
	// public static final String HEAPUSAGE = "HeapUsage";
	// thread pool
	public static final String CREATETHREADS = "CreateThreads";
	public static final String DESTROYTHREADS = "DestroyThreads";
	public static final String ACTIVETHREADS = "ActiveThreads";
	public static final String THREADPOOLSIZE = "ThreadPoolSize";
	public static final String PERCENTMAXED = "PercentMaxed";
		
	// web module
	public static final String TOTALREQUESTS = "TotalRequests";
	public static final String CONCURRENTREQUESTS = "ConcurrentRequests";
	public static final String RESPONSETIME = "ResponseTime";
	public static final String NUMERRORS = "NumErrors";
	public static final String TOMCAT = "tomcat";
	public static final String WEBSPHERE = "websphere";
	public static final String WEBLOGIC = "weblogic";
	
	//Weblogic
	//jta
	public static final String JTAABANDONED = "JtaAbandoned";
	public static final String JTATOTAL = "JtaTotal";
	//	private static final String JTAROLLEDBACK = "JtaRolledBack";
	//	private static final String JTAACTIVE = "JtaActive";
	public static final String JTAHEURISTICS = "JtaHeuristics";
	public static final String JTALLRCOMMITTED = "JtaLLRCommitted";
	public static final String JTATWOPHASECOMMITTED = "JtaTwoPhaseCommitted";
	public static final String JTAROLLEDBACKSYSTEM = "JtaRolledBackSystem";
		public static final String JTANORESOURCESCOMMITTED = "JtaNoResourcesCommitted";
	//	private static final String JTACOMMITTED = "JtaCommitted";
		public static final String JTAROLLEDBACKAPP = "JtaRolledBackApp";
		public static final String JTAROLLEDBACKTIMEOUT = "JtaRolledBackTimeout";
		public static final String JTAROLLEDBACKRESOURCE = "JtaRolledBackResource";
		public static final String JTAREADONLYONEPHASECOMMITTED = "JtaReadOnlyOnePhaseCommitted";
		public static final String JTAONERESOURCEONEPHASECOMMITTED = "JtaOneResourceOnePhaseCommitted";
		//jms
		public static final String JMSCONNECTIONS = "JmsConnections";
		public static final String JMSSERVERS = "JmsServers";
		public static final String JMSCONNECTIONSCURRENT = "JmsConnectionsCurrent";
		public static final String JMSSERVERSHIGH = "JmsServersHigh";
		public static final String JMSSERVERSCURRENT = "JmsServersCurrent";
		public static final String JMSCONNECTIONSHIGH = "JmsConnectionsHigh";
		public static final String JMSNAME = "JmsName";
		// thread pool
		public static final String TOTALTHREADS = "TotalThreads";
		public static final String IDLETHREADS = "IdleThreads";
		public static final String PENDINGUSERREQUEST = "PendingUserRequest";
		public static final String THREADPOOLUSAGE = "ThreadPoolUsage";
		//public static final String PERCENTMAXED = "PercentMaxed";
		
		//jdbc pool 
		public static final String CURRCONNECTIONS = "CurrConnections";
		public static final String AVAILABLESESSIONS = "AvailableSessions";
		public static final String CURRWAITINGCONNECTIONS = "CurrWaitingConnections";
		public static final String LEAKEDCONNECTIONS = "LeakedConnections";
		public static final String CURRACTIVECONNECTIONS = "CurrActiveConnections";
		public static final String FAILURESRECONNECT = "FailuresReconnect";
		public static final String TOTALCONNECTIONS = "TotalConnections";
	
		

}
