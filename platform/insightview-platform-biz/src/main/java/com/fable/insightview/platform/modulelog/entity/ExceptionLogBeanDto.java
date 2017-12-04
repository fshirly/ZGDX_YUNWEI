package com.fable.insightview.platform.modulelog.entity;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionLogBeanDto extends ExceptionLogBean {
    private static final Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Logger logger = LoggerFactory.getLogger(ExceptionLogBeanDto.class);

    /**
     * 开始时间
     */
    //@ApiModelProperty(value = "开始时间")
    private Date startTime;

    /**
     * 结束时间
     */
    //@ApiModelProperty(value = "结束时间")
    private Date endTime;

    /**
     * 页码
     */
    //@ApiModelProperty(value = "页码")
    private int pageNum = 1;

    /**
     * 每页大小
     */
    //@ApiModelProperty(value = "每页大小")
    private int pageSize = 10;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(String strStartTime) {
        if (StringUtils.isEmpty(strStartTime)) {
            return;
        }
        try {
            this.startTime = (Date) format.parseObject(strStartTime);
        } catch (ParseException e) {
            logger.warn("开始时间转换失败", e);
        }
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(String strEndTime) {
        if (StringUtils.isEmpty(strEndTime)) {
            return;
        }
        try {
            this.endTime = (Date) format.parseObject(strEndTime);
        } catch (ParseException e) {
            logger.warn("结束时间转换失败", e);
        }
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
