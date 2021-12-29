package runtime.processor.annotation;

import org.springframework.core.Ordered;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface SortOrder {
    int value() default Integer.MIN_VALUE;
}
