<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>Cron表达式简介</title>
    <style type="text/css">
    	body{
    		font-size:10pt
    	}
    </style>
  </head>
  <body>
	<p>Cron表达式有两种语法格式：</p> 
	<p>(1)Seconds Minutes Hours DayofMonth Month DayofWeek Year</p>
	<p>(2)Seconds Minutes Hours DayofMonth Month DayofWeek</p>
	<p>每一个域可出现的值/字符如下： </p>
	<p>Seconds:值为0-59的整数，也可出现", - * /"字符</p>
	<p>Minutes:值为0-59的整数，也可出现", - * /"字符</p>
	<p>Hours:值为0-23的整数，也可出现", - * /"字符</p>
	<p>DayofMonth:值为0-31的整数，也可出现", - * / ? L W C"字符</p>
	<p>Month:值为1-12的整数或JAN-DEC，也可出现", - * /"字符</p>
	<p>DayofWeek:值为1-7的整数或SUN-SAT，（1表示星期天，2表示星期一，依次类推）也可出现", - * / ? L C #"字符</p>
	<p>Year:值为1970-2099的整数，也可出现", - * /"字符</p>
	<p>以上各字符的含义为： </p>
	<p>(1)*：表示任意值，比如若在Minutes域使用*则表示每分钟都触发事件</p>
	<p>(2)?:只能用在DayofMonth或DayofWeek域，它也表示任意值，因DayofMonth和DayofWeek会相互影响，所以有时候只能使用?而不能使用*（比如想在每月的20日触发调度，不管20日是星期几，则只能使用如下写法： 0 0 0 20 * ?, 最后一位只能用？而不能使用*，否则表示周一到周日每天都会触发）</p>
	<p>(3)-:表示范围，比如Minutes域值为5-20则表示从5到20分钟每分钟触发一次</p>
	<p>(4)/：表示从起始时间开始，然后每隔固定时间触发一次，例如Minutes域值为5/20,则表示5、25、45分钟各触发一次</p>
	<p>(5),:表示在列出的每个值时间点都触发一次。例如：Minutes域值为0,5,20，则表示0、5、20分钟各触发一次</p>
	<p>(6)L:只能出现在DayofWeek和DayofMonth域，表示最后（Last），例如DayofWeek域值为5L,则表示在最后一个星期四触发</p>
	<p>(7)W:只能出现在DayofMonth域，表示有效工作日(周一到周五),系统将在离指定日期的最近的有效工作日触发事件。例如若DayofMonth域值为5W，并且5号是星期六，则将在最近的工作日即星期五4日触发；若5号是星期天，则在6日(周一)触发；如果5日是星期一到星期五中的一天，则就在5日触发；另外，W的最近寻找不会跨月</p>
	<p>(8)LW:表示某个月最后一个工作日，即最后一个星期五</p>
	<p>(9)#:用于确定每个月第几个星期几，只能出现在DayofMonth域。例如在4#2表示某月的第二个星期三</p>
	<p>实例：</p>
	<p>* * 9-18 ? * MON-FRI 周一至周五每天9点至18点不间断运行</p>
	<p>0 0 9,18 ? * MON-FRI 周一至周五每天9点和18点整点触发 </p>
	<p>0 0 10 20 * ? 每月20号10点整点触发</p> 
  </body>
</html>