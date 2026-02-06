package ru.gelman.user_crud_service.proxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class LoggedInvocationHandler implements InvocationHandler {
    private final Object bean;
    private final List<String> proxyMethods;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!proxyMethods.contains(method.getName())) {
            return method.invoke(bean, args);
        }

        String className = bean.getClass().getName();
        String methodName = method.getName();
        String argsValues = Arrays.toString(args);
        String argTypes = Arrays.stream(args).map(a -> a.getClass().getSimpleName()).collect(Collectors.joining(", "));
        try {
            log.info("Calling method {}.{}({}) with args={}", className, methodName, argTypes, argsValues);
            Object retValue = method.invoke(bean, args);
            log.info("Method execution result: {}", retValue);
            return retValue;
        } catch (Throwable ex) {
            log.error("Method execution failed with exception: {}", ex.toString());
            throw ex;
        }
    }
}
