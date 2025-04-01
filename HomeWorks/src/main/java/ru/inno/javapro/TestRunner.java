package ru.inno.javapro;

import org.apache.commons.beanutils.ConvertUtils;
import ru.inno.javapro.homework1.annotations.AfterSuite;
import ru.inno.javapro.homework1.annotations.BeforeSuite;
import ru.inno.javapro.homework1.annotations.CsvSource;
import ru.inno.javapro.homework1.annotations.Test;
import ru.inno.javapro.homework1.exceptions.TestRunnerException;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestRunner {
    private static final String ERROR_MESSAGE_WRONG_PARAMETERS_NUMBER = "Метод %s имеет %s параметров, хотя аннотирован @CsvSource с %s параметрами";

    /**
     * Выполнение методов тестового класса
     *
     * @param c тестовый класс
     * @throws ReflectiveOperationException возможное исключение
     */
    static void runTests(Class<?> c) throws ReflectiveOperationException {
        Method beforeSuiteMethod = null;
        Method afterSuiteMethod = null;
        List<Method> testMethods = new ArrayList<>();
        Object testInstance = c.getConstructor().newInstance();

        for (Method method : c.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                if (beforeSuiteMethod != null) {
                    throw new TestRunnerException("Более одного метода аннотированы BeforeSuite");
                } else if (!Modifier.isStatic(method.getModifiers())) {
                    throw new TestRunnerException("метод аннотированный BeforeSuite не static");
                }
                beforeSuiteMethod = method;
            } else if (method.isAnnotationPresent(AfterSuite.class)) {
                if (afterSuiteMethod != null) {
                    throw new TestRunnerException("Более одного метода аннотированы AfterSuite");
                } else if (!Modifier.isStatic(method.getModifiers())) {
                    throw new TestRunnerException("метод аннотированный AfterSuite не static");
                }
                afterSuiteMethod = method;
            } else if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }
        }

        if (beforeSuiteMethod != null) {
            beforeSuiteMethod.setAccessible(true);
            beforeSuiteMethod.invoke(c);
        }

        testMethods
                .stream()
                .sorted(Comparator.comparingInt(o -> o.getAnnotation(Test.class).priority()))
                .forEachOrdered(method -> {
                    method.setAccessible(true);
                    try {
                        method.invoke(testInstance, methodArgs(method));
                    } catch (Exception e) {
                        throw new TestRunnerException(e);
                    }
                });

        if (afterSuiteMethod != null) {
            afterSuiteMethod.setAccessible(true);
            afterSuiteMethod.invoke(c);
        }
    }

    /**
     * Получение массива аргументов метода из значения аннотации CsvSource
     * Аргументы приводятся к типу аргумента метода, если метод не аннотирован CsvSource,
     * то возвращается пустой массив
     *
     * @param method метод для которого получаются аргументы
     * @return массив объектов содержащий значения аргументов метода
     */
    private static Object[] methodArgs(Method method) {
        if (method.isAnnotationPresent(CsvSource.class)) {
            String[] csvValues = method.getAnnotation(CsvSource.class).value().split(",");
            Object[] args = new Object[csvValues.length];
            if (method.getParameterCount() != csvValues.length) {
                throw new TestRunnerException(String.format(ERROR_MESSAGE_WRONG_PARAMETERS_NUMBER, method.getName(),
                        method.getParameterCount(), csvValues.length));
            }
            int i = 0;
            for (var parameter : method.getParameters()) {
                args[i] = ConvertUtils.convert(csvValues[i++], parameter.getType());
            }
            return args;
        }
        return new Object[]{};
    }
}
