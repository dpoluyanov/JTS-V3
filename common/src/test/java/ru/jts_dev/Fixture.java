package ru.jts_dev;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Helps {@link FixtureParameterResolver} determine correct fixture for given type
 *
 * @author Camelion
 * @since 31.07.16
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Fixture {
    /**
     * The name of template, that should be created for this type
     *
     * @return name of needed template
     */
    String value();
}
