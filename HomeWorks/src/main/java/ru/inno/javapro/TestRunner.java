package ru.inno.javapro;

import org.apache.commons.beanutils.ConvertUtils;
import ru.inno.javapro.homework1.annotations.AfterSuite;
import ru.inno.javapro.homework1.annotations.BeforeSuite;
import ru.inno.javapro.homework1.annotations.CsvSource;
import ru.inno.javapro.homework1.annotations.Test;
import ru.inno.javapro.homework1.exceptions.TestRunnerException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TestRunner {
    private static final String ERROR_MESSAGE_TO_MANY_ANNOTATED_CLASSES = "Класс %s содержит %s методов аннотированных %s, " +
            "допустимое количество не более %s";
    private static final String ERROR_MESSAGE_EXIST_NOT_STATIC_METHOD = "Класс %s содержит не статические методы, аннотированные %s";
    private static final String ERROR_MESSAGE_WRONG_PARAMETERS_NUMBER = "Метод %s имеет %s параметров, хотя аннотирован @CsvSource с %s параметрами";

    /**
     * Выполнение методов тестового класса
     *
     * @param c тестовый класс
     * @throws ReflectiveOperationException возможное исключение
     */
    static void runTests(Class<?> c) throws ReflectiveOperationException {
        checkAnnotations(c);
        runOneAnnotatedMethod(c, BeforeSuite.class);
        runAllTestMethods(c);
        runOneAnnotatedMethod(c, AfterSuite.class);
    }

    /**
     * Выполнение единственного аннотированного метода, если методов несколько, то выполняется первый попавшийся
     * Проверка на то что метод единственный выполняется методом {@link #checkAnnotatedMethod}}
     *
     * @param c               Класс содержащий метод
     * @param annotationClass Аннотация, которой аннотированы методы
     * @throws ReflectiveOperationException возможное исключение
     */
    private static void runOneAnnotatedMethod(Class<?> c, Class<? extends Annotation> annotationClass) throws ReflectiveOperationException {
        for (Method method : c.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotationClass)) {
                method.setAccessible(true);
                method.invoke(c);
                return;
            }
        }
    }

    /**
     * Последовательно выполняются все аннотированные @Test методы в соответствии с приоритетом
     * Если метод не статический, то перед выполнением создается новый объект
     * Аннотация содержит значение приоритета
     *
     * @param c Класс содержащий методы
     */
    private static void runAllTestMethods(Class<?> c) {
        Arrays.stream(c.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Test.class))
                .sorted(Comparator.comparingInt(o -> o.getAnnotation(Test.class).priority()))
                .forEachOrdered(method -> {
                    try {
                        method.setAccessible(true);
                        method.invoke(
                                Modifier.isStatic(method.getModifiers()) ? c : c.getConstructor().newInstance(),
                                methodArgs(method)
                        );
                    } catch (Exception e) {
                        throw new TestRunnerException(e);
                    }
                });
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

    /**
     * Проверяет допустимость использования аннотаций
     *
     * @param c Проверяемый класс
     * @throws TestRunnerException исключение с описанием всех ошибок
     */
    private static void checkAnnotations(Class<?> c) throws TestRunnerException {
        List<String> errors = new ArrayList<>();
        errors = checkAnnotatedMethod(c, BeforeSuite.class, errors, 1);
        errors = checkAnnotatedMethod(c, AfterSuite.class, errors, 1);
        if (!errors.isEmpty()) {
            throw new TestRunnerException(errors.toString());
        }
    }

    /**
     * Проверка аннотаций методов класса
     *
     * @param c               Проверяемый класс
     * @param annotationClass Проверяемая аннотация
     * @param errors          список ошибок
     * @param maxAllowedCount максимальное допустимое количество аннотированных методов
     * @return список ошибок
     */
    private static List<String> checkAnnotatedMethod(Class<?> c, Class<? extends Annotation> annotationClass, List<String> errors, long maxAllowedCount) {
        List<String> resultErrors = new ArrayList<>(errors);
        long annotatedMethodCount = getAnnotatedMethodCount(c, annotationClass);
        if (annotatedMethodCount > maxAllowedCount) {
            resultErrors.add(String.format(ERROR_MESSAGE_TO_MANY_ANNOTATED_CLASSES,
                    c.getSimpleName(), annotatedMethodCount, annotationClass.getSimpleName(), maxAllowedCount));
        }
        if (existNonStaticAnnotatedMethods(c, annotationClass)) {
            resultErrors.add(String.format(ERROR_MESSAGE_EXIST_NOT_STATIC_METHOD, c.getSimpleName(), annotationClass.getSimpleName()));
        }
        return resultErrors;
    }

    /**
     * Подсчет аннотированных методов классе
     *
     * @param c               Класс в котором идет подсчет методов
     * @param annotationClass Аннотация, которой аннотированы методы
     * @return количество методов
     */
    private static long getAnnotatedMethodCount(Class<?> c, Class<? extends Annotation> annotationClass) {
        return Arrays.stream(c.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(annotationClass))
                .count();
    }

    /**
     * Проверка наличия аннотированных статических методов класса
     *
     * @param c               Класс в котором идет проверка
     * @param annotationClass Аннотация, которой аннотированы методы
     * @return признак наличия статических методов
     */
    private static boolean existNonStaticAnnotatedMethods(Class<?> c, Class<? extends Annotation> annotationClass) {
        return Arrays.stream(c.getDeclaredMethods())
                .anyMatch(method -> method.isAnnotationPresent(annotationClass) && !Modifier.isStatic(method.getModifiers()));
    }
}
