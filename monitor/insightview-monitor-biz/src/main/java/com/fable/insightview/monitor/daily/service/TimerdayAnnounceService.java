package com.fable.insightview.monitor.daily.service;

public interface TimerdayAnnounceService {
   /*
    * 开始进行上传
    */
   public String beginToUploadDayAnnounceExcel(String filePath,String prefixFileName);

   /*
    * 查询昨日值班人
    */
   public String querybeforeDutePeople();
   

   /*
    * 查询前一天值班人电话
    */
   public String queryDutePeoplePhoneNumber();
   
   /*
    * 查询当天值班人电话
    */
   public String queryCurrentDutePeoplePhoneNumber();
   
}
