package ru.jts_dev;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.util.logging.Logger;

/**
 * Resolves fixture parameters, declared in package {@link ru.jts_dev.fixture.templates}
 * or try to load fixture with `default` label.
 * <p>
 * When type marked with {@link ru.jts_dev.Fixture} annotation,
 * {@link ru.jts_dev.Fixture#value()} will be used, to choose template
 * <p>
 * If no {@link ru.jts_dev.Fixture} annotation present, that default value will be loaded
 * <p>
 * TODO may be moved to separate testing library
 *
 * @author Camelion
 * @since 31.07.16
 */
public class FixtureParameterResolver implements ParameterResolver {
    private static final Logger LOG = Logger.getLogger(FixtureParameterResolver.class.getName());

    /**
     * Load all fixtures in package {@link ru.jts_dev.fixture.templates}
     */
    public FixtureParameterResolver() {
        FixtureFactoryLoader.loadTemplates("ru.jts_dev.fixture.templates");
    }

    /**
     * Determine, if the requested parameterType and name(or template with "default" label)
     * presents in fixture factory
     */
    @Override
    public boolean supports(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Class<?> parameterType = parameterContext.getParameter().getType();
        ru.jts_dev.Fixture fixture = parameterContext.getParameter().getAnnotation(ru.jts_dev.Fixture.class);

        boolean supports;
        if (fixture != null) {
            try {
                supports = Fixture.from(parameterType).gimme(fixture.value()) != null;
            } catch (Exception e) {
                LOG.throwing(FixtureParameterResolver.class.getCanonicalName(), "supports", e);
                supports = false;
            }
        } else {
            try {
                supports = Fixture.from(parameterType).gimme("default") != null;
            } catch (Exception e) {
                LOG.throwing(FixtureParameterResolver.class.getCanonicalName(), "supports", e);
                supports = false;
            }
        }


        return supports;
    }

    /**
     * Resolve a value for the {@link Parameter} in the supplied
     * {@link ParameterContext} by retrieving the corresponding fixture template
     */
    @Override
    public Object resolve(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Class<?> parameterType = parameterContext.getParameter().getType();
        ru.jts_dev.Fixture fixture = parameterContext.getParameter().getAnnotation(ru.jts_dev.Fixture.class);

        Object target;

        if (fixture != null) {
            target = Fixture.from(parameterType).gimme(fixture.value());
        } else {
            target = Fixture.from(parameterType).gimme("default");
        }

        return target;
    }
}
