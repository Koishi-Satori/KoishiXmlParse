package top.kkoishi.annotations;

import java.lang.annotation.*;

/**
 * @author KKoishi_
 */
@Documented
@Inherited
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE})
public @interface Range {
    long min ();

    long max ();
}
