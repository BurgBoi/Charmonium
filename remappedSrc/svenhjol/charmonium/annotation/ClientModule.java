package svenhjol.charmonium.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ClientModule {
    String mod() default "";

    String description() default "";

    int priority() default 0;

    boolean alwaysEnabled() default false;

    boolean enabledByDefault() default true;
}
