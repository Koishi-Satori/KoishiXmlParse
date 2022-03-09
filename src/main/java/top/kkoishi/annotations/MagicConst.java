package top.kkoishi.annotations;

import java.lang.annotation.*;

/**
 * @author KKoishi_
 */
@Retention(RetentionPolicy.CLASS)
@Documented
@Inherited
@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface MagicConst {
    Class<?> type () default Object.class;

    boolean required () default true;

    boolean preferred () default true;

    String info () default "";

    String[] args () default {};
}
