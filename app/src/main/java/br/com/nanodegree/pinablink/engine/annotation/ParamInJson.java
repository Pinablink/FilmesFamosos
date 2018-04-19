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
    /**
     * @return
     */
    String name();

    /**
     *
     * @return
     */
    String concatLeft() default "";

    /**
     *
     * @return
     */
    String concatRight() default "";

    /**
     *
     * @return
     */
    int  valueType() default Param.VALUE_TYPE_STRING;

    /**
     * @return
     */
    boolean isArrayData() default false;
}
