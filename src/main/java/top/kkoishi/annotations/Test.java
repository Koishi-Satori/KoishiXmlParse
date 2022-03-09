package top.kkoishi.annotations;

import java.lang.annotation.*;

/**
 * @author Kkoishi_
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Target(ElementType.METHOD)
public @interface Test {

}
