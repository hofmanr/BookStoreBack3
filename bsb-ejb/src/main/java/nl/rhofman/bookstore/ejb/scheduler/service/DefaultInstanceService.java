package nl.rhofman.bookstore.ejb.scheduler.service;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.spi.CreationalContext;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.inject.Inject;

import java.util.Set;
import java.util.function.Consumer;

/**
 * this clas is used as a helper class for executing CDI beans.
 * There is a memory issue when using {@code Instance<MyClass> instances}. This is a workaround.
 * See method dtoToMessage in the MessageBuilder class (this is another workaround).
 */
@Dependent
@Default
public class DefaultInstanceService implements InstanceService {

    private final BeanManager beanManager;

    @Inject
    public DefaultInstanceService(BeanManager beanManager) {
        this.beanManager = beanManager;
    }

    @Override
    public <T> void executeBean(Class<T> type, Consumer<T> action) {
        Set<Bean<?>> beans = beanManager.getBeans(type);

        for (Bean<?> bean : beans) {
            CreationalContext<?> context = beanManager.createCreationalContext(bean);
            T serviceBean = (T) beanManager.getReference(bean, type, context);
            action.accept(serviceBean);
        }
    }
}
