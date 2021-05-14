package com.zhangwei.anon;

import java.lang.annotation.*;


@Target(value = ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface SignIgnore {

}
