//package cn.fablesoft.test;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.sql.Timestamp;
//import java.text.DateFormat;
//import java.util.Date;
//import java.util.Properties; 
//
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
//
//import com.fable.insightview.monitor.entity.AlarmNode;
//import com.fable.insightview.monitor.messTool.MessageTool;  
//
//public class putJafkaMessageA  {
//
////	public static Logger logger = LoggerFactory.getLogger(putJafkaMessage.class);
//
//	private static MessageTool mt = null;
//	
//	static int num = 0;
//	
//	private static void jafka() {
//		mt = MessageTool.getInstance();
//		try {
//			mt.setProperty(getProperties());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	 
//	public void init() {
//		try {
//			num++;
//			
//			AlarmNode alarm = new AlarmNode();//;alarmdispatcher.queue.poll();
//			alarm.setAlarmID(10);
//			alarm.setAlarmOID("1.3.6.1.4.1.8828.2.4");
//			alarm.setAlarmTitle("ffffff");
//			alarm.setSourceMOID(1);
//			alarm.setSourceMOName("ffffff");
//			alarm.setSourceMOIPAddress("192.168.1.35");
//			alarm.setMoclassID(10);
//			alarm.setMoid(10);
//			alarm.setMoName("192.168.1.35");
//			alarm.setAlarmLevel(10);
//			alarm.setAlarmLevelName("123");
//			alarm.setAlarmType(2);
//			alarm.setAlarmTypeName("123");
//			
//			Date now=new Date();
//			DateFormat d2 = DateFormat.getDateTimeInstance(); 
//		    String str2 = d2.format(now);  
//			
//			alarm.setStartTime(Timestamp.valueOf(str2));
//			alarm.setLastTime(Timestamp.valueOf(str2));
//			alarm.setRepeatCount(2);
//			alarm.setAlarmStatus(num);
//			
//			mt.alarmProduce(alarm);
//		} catch (Exception e) {
//			e.printStackTrace();
////			logger.error("����zookeeperʧ�ܣ�", e);
//		}
//	}
//
//	public static Properties getProperties() throws IOException {
//		String fileName = "../zookeeper.properties";
//		
//		FileInputStream fis = null;
//		Properties ZKPrpperty = new Properties();
//		try {  
//			fis = new FileInputStream(fileName);
//			ZKPrpperty.load(fis);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		 
//        try {
//			fis.close();
//		} catch (Exception e) {
//			e.printStackTrace();
////			log.error("获取zookeeper配置文件异常:" +e);
//		}
//		return ZKPrpperty;
//	}
// 
//	public static void main(String[] args){
//		jafka();
//		putJafkaMessageA putTest = new putJafkaMessageA();
//		putTest.init();
//	}
//}