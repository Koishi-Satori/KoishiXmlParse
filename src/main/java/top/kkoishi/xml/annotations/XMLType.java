package top.kkoishi.xml.annotations;

import jdk.jfr.Experimental;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author KKoishi_
 */
@Experimental
@Retention(RetentionPolicy.CLASS)
@Inherited
@Documented
@Target(ElementType.FIELD)
public @interface XMLType {

}
