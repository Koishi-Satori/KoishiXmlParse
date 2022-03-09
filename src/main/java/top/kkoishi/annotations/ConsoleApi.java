package top.kkoishi.annotations;

import jdk.jfr.Period;

import java.lang.annotation.*;

/**
 * @author KKoishi_
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Period
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE})
public @interface ConsoleApi {
}
