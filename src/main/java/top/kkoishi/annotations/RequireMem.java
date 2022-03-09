package top.kkoishi.annotations;

import java.lang.annotation.*;

/**
 * @author KKoishi_
 */
@Retention(RetentionPolicy.CLASS)
@Documented
@Inherited
@Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE})
public @interface RequireMem {
    boolean value () default true;

    Class<? extends Exception> exception () default Exception.class;
}
