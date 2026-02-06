package ru.gelman.user_crud_service.proxy;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import ru.gelman.user_crud_service.annotation.Logged;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class LoggedBeanPostProcessor implements BeanPostProcessor {

    @Override
    public @Nullable Object postProcessBeforeInitialization(Object bean, @NonNull  String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        Class<?>[] beenInterfaces = beanClass.getInterfaces();
        List<String> proxyMethods = getProxyMethodNamesOfBean(beanClass);
        if (beenInterfaces.length == 0 || proxyMethods.isEmpty()) {
            return bean;
        }

        log.debug("Creating LoggedProxy for bean {}", beanName);
        return Proxy.newProxyInstance(
                beanClass.getClassLoader(),
                beenInterfaces,
                new LoggedInvocationHandler(bean, proxyMethods)
        );
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        return bean;
    }

    private List<String> getProxyMethodNamesOfBean(Class<?> beanClass) {
        List<Method> proxyMethods = List.of(beanClass.getDeclaredMethods());
        return proxyMethods.stream()
                .filter(method -> beanClass.isAnnotationPresent(Logged.class) || method.isAnnotationPresent(Logged.class))
                .map(Method::getName)
                .collect(Collectors.toList());
    }
}
