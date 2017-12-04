package nl.justobjects.pushlet.filter;

import com.fable.insightview.monitor.entity.AlarmNode;
 

public abstract class FilterHandler {
    /**
     * 持有下一个处理请求的对象
     */
    protected FilterHandler successor = null;
    /**
     * 取值方法
     */
    public FilterHandler getSuccessor() {
        return successor;
    }
    /**
     * 设置下一个处理请求的对象
     */
    public void setSuccessor(FilterHandler successor) {
        this.successor = successor;
    }
    /**
     * 处理告警过滤
     * @param filter 过滤条件
     * @param alarm  过滤对象
     * @return       成功或失败的具体通知
     */
    public abstract boolean handleAlarmFilter(FilterParamObject filter , AlarmNode alarm);
}