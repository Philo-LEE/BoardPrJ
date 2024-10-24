package annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//걍 내부의 어노테이션이 작용될 시점
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapping {
    String value() default "";



}
