package top.kkoishi.annotations;

import java.lang.annotation.*;

/**
 * Require the target where the annotation is used as a not-null one.
 * Usage:
 * <div>&nbsp;&nbsp;&nbsp;&nbsp;final void link (XmlNode @NotNull parent, XmlNode @NotNull item) {...</div>
 *
 * @author KKoishi_
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE})
public @interface NotNull {
    String value () default "";

    Class<? extends Exception> exception () default Exception.class;
}
