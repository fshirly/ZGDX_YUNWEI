package com.fable.insightview.platform.itsm.core.page;

import java.util.List;


/**
 * 分页列表
 * 
 * @author 汪朝  2013-9-30
 */
public class PageList<T> extends Result {

    private static final long serialVersionUID = -3729460386670004909L;

    /** 分页 */
    protected Page            page;

    /** 数据列表 */
    protected List<T>         list;

    /**
     * 构造函数
     */
    public PageList() {
        super();
    }

    /**
     * 构造函数
     * @param page 分页
     * @param list 数据列表
     */
    public PageList(Page page, List<T> list) {
        super();
        this.page = page;
        this.list = list;
    }

    /**
     * 获取分页
     * @return 分页
     */
    public Page getPage() {
        return page;
    }

    /**
     * 设置分页
     * @param page 分页
     */
    public void setPage(Page page) {
        this.page = page;
    }

    /**
     * 获取数据列表
     * @return 数据列表
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 设置数据列表
     * @param list 数据列表
     */
    public void setList(List<T> list) {
        this.list = list;
    }

}
