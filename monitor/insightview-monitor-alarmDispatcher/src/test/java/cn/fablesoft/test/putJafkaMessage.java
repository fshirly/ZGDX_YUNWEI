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
//public class putJafkaMessage  {
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
//			AlarmNode alarm = new AlarmNode();//;alarmdispatcher.queue.poll();
//			alarm.setAlarmID(num);
//			alarm.setAlarmOID("0.0.0.0.0.1.1.1");
//			alarm.setAlarmTitle("alarmTest");
//			alarm.setSourceMOID(1);
//			alarm.setSourceMOName("Alarm Test");
//			alarm.setSourceMOIPAddress("192.168.1.35");
//			alarm.setMoclassID(1);
//			alarm.setMoid(1);
//			alarm.setMoName("192.168.1.35");
//			alarm.setAlarmLevel(10);
//			alarm.setAlarmLevelName("sss");
//			alarm.setAlarmType(2);
//			alarm.setAlarmTypeName("ss");
//			
//			Date now=new Date();
//			DateFormat d2 = DateFormat.getDateTimeInstance(); 
//		    String str2 = d2.format(now);  
//			
//			alarm.setStartTime(Timestamp.valueOf(str2));
//			alarm.setLastTime(Timestamp.valueOf(str2));
//			alarm.setRepeatCount(2);
//			alarm.setAlarmStatus(num);
//			System.out.println(mt);
//			mt.alarmProduce(alarm);
//			System.out.println("制造数据成功");
//		} catch (Exception e) { 
//			e.printStackTrace();
//		}
//	}
//
//	public static Properties getProperties() throws IOException {
//		String fileName = "zookeeper.properties";
//		
//		FileInputStream fis = null;
//		Properties ZKPrpperty = new Properties();
//		try {
//			fis = new FileInputStream(fileName);
//			ZKPrpperty.load(fis);
//		} catch (Exception e) {
//		}
//		
//        try {
//			fis.close();
//		} catch (Exception e) { 
//		}
//		return ZKPrpperty;
//	}
// 
//	public static void main(String[] args){
//		jafka();
//		putJafkaMessage putTest = new putJafkaMessage();
//		putTest.init();
//	}
//}