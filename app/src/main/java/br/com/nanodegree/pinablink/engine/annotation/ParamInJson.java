package br.com.nanodegree.pinablink.engine.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Pinablink on 13/04/2018.
 */
@Target(ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ParamInJson {

    String name();
    String concatLeft() default "";
    String concatRight() default "";
    int  valueType() default Param.VALUE_TYPE_STRING;
    boolean isArrayData() default false;
}
