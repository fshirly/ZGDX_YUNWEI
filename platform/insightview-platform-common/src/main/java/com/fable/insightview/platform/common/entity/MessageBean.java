package com.fable.insightview.platform.common.entity;

//import com.wordnik.swagger.annotations.ApiModel;
//import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author fangang
 *         通用消息封装BO对象
 */
//@ApiModel(value = "通用消息封装BO对象")
public class MessageBean {

    /**
     * 操作是否成功
     */
    //@ApiModelProperty(value = "操作是否成功")
    private boolean success = true;

    /**
     * 消息内容
     */
    //@ApiModelProperty(value = "消息内容")
    private String message;

    /**
     * 操作失败时的错误代码
     */
    //@ApiModelProperty(value = "操作失败时的错误代码")
    private String errorCode;


    /*************
     * 构造函数
     ***********/
    public MessageBean() {
    }

    public MessageBean(boolean success) {
        this.success = success;
    }

    public MessageBean(boolean success, String message) {
        this(success);
        this.message = message;
    }

    public MessageBean(boolean success, String message, String errorCode) {
        this(success, message);
        this.errorCode = errorCode;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }


    /*************
     * 通用静态方法
     ***********/
    public static MessageBean error(String message) {
        return new MessageBean(false, message);
    }

    public static MessageBean success(String message) {
        return new MessageBean(true, message);
    }


}
