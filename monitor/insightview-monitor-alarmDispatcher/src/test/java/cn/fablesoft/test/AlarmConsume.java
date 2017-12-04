//package cn.fablesoft.test;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.Properties;
//
//import com.fable.insightview.monitor.entity.AlarmNode;
//import com.fable.insightview.monitor.interfaces.MessageHandler;
//import com.fable.insightview.monitor.messTool.MessageTool;
//
//public class AlarmConsume implements MessageHandler {
//	private static MessageTool mt;
//	
//	private static Properties getProperties() throws IOException {
//		String fileName = "../zookeeper.properties";
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
//			System.out.println(e);
//		}
//		return ZKPrpperty;
//	}
//	
//	private AlarmConsume() {
//		mt = MessageTool.getInstance();
//		try {
//			mt.setProperty(getProperties());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	private void AlarmConsumeProcess() {
//		mt.alarmConsume(this);
//	}
//	
//	public void handleMessage(Object mess) {
//		if(mess instanceof AlarmNode) {
//			AlarmNode node = (AlarmNode)mess;
//			System.out.println("alarmID:" +node.getAlarmID());
//			System.out.println("alarmDefineID:" +node.getAlarmDefineID());
//			System.out.println("start:" +node.getStartTime());
//			System.out.println("Last:" +node.getLastTime());
//		}
//	}
//	
//	public static void main(String[] args) {
//		AlarmConsume ac = new AlarmConsume();
//		
//		while (true) {
//			try {
//				ac.AlarmConsumeProcess();
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				
//			}
//			
//		}
//	}
//}
