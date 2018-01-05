package shiro.hcw.anno;

import java.lang.annotation.*;

/**
 * Copyright (C), 2017，jumore Tec.
 * Author: hechengwen
 * Version:
 * Date: 2017/12/18 17:12
 * Description:
 * Others:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface LoginRequired {

    String value() default "hello";

}

