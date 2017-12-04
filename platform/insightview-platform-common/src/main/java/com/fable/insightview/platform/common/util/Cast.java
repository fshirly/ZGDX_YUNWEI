package com.fable.insightview.platform.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class Cast {
	
	public static String toString(Object obj ){
		if (obj == null )
			return Nothing.nullString;
		else
			return obj.toString();
	}
	 public static int isNumber(String s){   
         String regex = "^[1-9][0-9]*\\.[0-9]+$|^[1-9][0-9]*$|^0+\\.[0-9]+$";  
         char c = s.charAt(0);  
         boolean bool;  
         if(c=='+'|c=='-'){  
             bool = s.substring(1).matches(regex);  
         }else{  
             bool = s.matches(regex);  
         }  
         if(bool){  
             return 1;  
         }else{  
             return 0;  
         }  
     }  
	 public static int isInt(String s){ 
		 String regex = "[0-9]+" ; 
         char c = s.charAt(0);  
         boolean bool;  
         if(c=='+'|c=='-'){  
             bool = s.substring(1).matches(regex);  
         }else{  
             bool = s.matches(regex);  
         }  
         if(bool){  
             return 1;  
         }else{  
             return 0;  
         }  
	 }
	public static int toInt(String strValue){
		if(Nothing.IsNull(strValue))
				return Nothing.nullInt;  
		try{
			if(isInt(strValue)>0 )
				return Integer.parseInt(strValue);
			if(isNumber(strValue)>0 )
				return (int) toDouble(strValue);
			
			return Nothing.nullInt;
		}
		catch (Exception ex)
		{
				return Nothing.nullInt;
		} 
	}
	public static long toLong(String strValue){
		if(Nothing.IsNull(strValue))
			return Nothing.nullInt;  
		try{
			return  Long.parseLong(strValue); 
		}
		catch (Exception ex)
		{
				return Nothing.nullInt ;
		} 
	}
	public static float toFloat(String strValue){
		if(Nothing.IsNull(strValue))
			return Nothing.nullInt;  
		try{
			return Float.parseFloat(strValue); 
		}
		catch (Exception ex)
		{
				return Nothing.nullInt ;
		}  
	}
	public static double toDouble(String strValue){
		if(Nothing.IsNull(strValue))
			return Nothing.nullInt;  
		try{
			return Double.parseDouble(strValue); 
		}
		catch (Exception ex)
		{
				return Nothing.nullInt ;
		} 
	}
	
	public static int toInt(Boolean bl){
		if(bl)
			return 1;
		else
			return 0;
	}
	public static Integer toInteger(Object obj ){
		if(obj == null)
			return null;
		return toInt(obj);
	}
	public static Long toLLong(Object obj ){
		if(obj == null)
			return null;
		return toLong(obj);
	}
	
	public static int toInt(Object obj){
		if(obj == null )
				return Nothing.nullInt;
		
		return toInt(obj.toString());
	}
	public static long toLong(Object obj){
		if(obj == null )
				return Nothing.nullInt;
		return toLong(obj.toString());
	}
	public static float toFloat(Object obj){
		if(obj == null )
				return Nothing.nullInt;
		return toFloat(obj.toString());
	}
	public static double toDouble(Object obj){
		if(obj == null )
				return Nothing.nullInt;
		return toDouble(obj.toString());
	}
	
	public static boolean toBoolean(String strValue){ 
		if(Nothing.IsNull(strValue))
			return Nothing.nullBoolean;  
	 
		ArrayList<String> strNo= Tool.SplitArrayList("0,false,no,n") ;
		ArrayList<String>  strYes=Tool.SplitArrayList("1,true,yes,y,on");
		
		if(strNo.indexOf(strValue.toLowerCase())>=0 )
			return false;
		if(strYes.indexOf(strValue.toLowerCase())>=0 )
			return true; 
		
		if(toInt(strValue)>0)
			return true;		
		
		return false;
	}
	public static boolean toBoolean(int iValue){
		if(iValue > 0)
			return true;
		else
			return false;
	}
	public static boolean toBoolean(Object obj){
		if(obj == null)
			return false;
		return toBoolean(obj.toString());
	}

	public static Date toDate( java.sql.Date dt){
		try{
			if(dt == null)
				return Nothing.nullDate; 
			
			 return new Date(dt.getTime());
		}
		catch (Exception ex){
			return Nothing.nullDate;			
		}
	}
	public static Date toDate(String strValue){
		try{
			if(Nothing.IsNull(strValue))
				return Nothing.nullDate; 
			
			//SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			
			return sdf.parse(strValue);   
		}
		catch (Exception ex){
			return Nothing.nullDate;			
		}
	}
	public static Date toDate(Object obj){
		try{
			if(Nothing.IsNull(obj))
				return Nothing.nullDate;  
			if(obj.getClass().getName().toLowerCase().equals("java.sql.date"))
				return toDate((java.sql.Date)obj ); 
			
			return toDate(obj.toString()); 
		}
		catch (Exception ex){
			return Nothing.nullDate;			
		}
	}

	public static UUID toGuid(String strValue){
		if(Nothing.IsNull(strValue))
			return Nothing.nullGuid;
		
		try{
			//支持49C9AE70C6634F748A8290AD37549C57格式的转换
			if(strValue.indexOf("-") == -1 && strValue.length() == 32) {
				StringBuffer sb = new StringBuffer(strValue);
				sb.insert(8, "-").insert(13, "-").insert(18, "-").insert(23, "-");
				strValue = sb.toString();
			}
			
			return UUID.fromString(strValue);
		}
		catch (Exception ex){
			return Nothing.nullGuid;
		}
	}
	public static UUID toGuid(Object obj){
		if(Nothing.IsNull(obj))
			return Nothing.nullGuid;
		try{
			return toGuid(obj.toString());
		}
		catch (Exception ex){
			return Nothing.nullGuid;
		}
	}
	
	/**
	 * UUID转化为String,并且去掉'-'
	 * @param uuid
	 * @return
	 */
	public static String guid2Str(UUID uuid){
		if(Nothing.IsNull(uuid))
			return Nothing.nullGuidStr;
		
		return uuid.toString().toUpperCase().replaceAll("-", "");
	}


	/**
	 * 生成uuid串，大写，去-
	 * @return str
	 */
	public static String genUuidStr(){
		return Tool.newGuid().toString().toUpperCase().replaceAll("-", "");
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//String str = "49C9AE70C6634F748A8290AD37549C57";
		String str = "49C9AE70-C663-4F74-8A82-90AD37549C57";
		UUID uuid = Cast.toGuid(str);
		System.out.println(uuid);
		System.out.println(guid2Str(uuid));
	}
}
