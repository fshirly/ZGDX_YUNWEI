/**
 * 
 */
package com.fable.insightview.platform.core.formaction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记表单重复提交令牌
 * @author xue.antai
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AvoidDuplicateSubmission {
	/**
	 * 表单加载令牌标记
	 * @return
	 */
    boolean formLoadToken() default false;

    /**
     * 表单提交完成令牌移除
     * @return
     */
    boolean formSubmitCompleteToken() default false; 
    
    /**
     * 表单加载支持多个令牌标记
     * @return
     */
    boolean multipartFormLoadToken() default false;
    
    /**
     * 表单提交支持指定令牌移除
     * @return
     */
    boolean multipartSubmitCompleteToken() default false;
    
    /**
     * 多个令牌索引值，配合mutipartSubmitCompleteToken使用，指定索引的令牌被移除
     * @return
     */
    int multipartTokenIndex() default 0;
    
    /**
     * 多个令牌的生成的个数
     * @return
     */
    int multipartTokenCount() default 3;
}
