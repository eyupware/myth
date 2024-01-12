package gov.babalar.notes;

/**
 * ----------
 * 9/5/2023
 * 6:02 PM
 * ----------
 **/

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MapThisClass {
    String className();
    String superClass();
}
