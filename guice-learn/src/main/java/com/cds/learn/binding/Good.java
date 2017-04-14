package com.cds.learn.binding;

import com.google.inject.BindingAnnotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p></p>
 * author chendongsheng5 2017/4/14 17:45
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/4/14 17:45
 * modify by reason:{方法名}:{原因}
 */
@Retention(RetentionPolicy.RUNTIME)
@BindingAnnotation
@Target(ElementType. LOCAL_VARIABLE)
public @interface Good {}
