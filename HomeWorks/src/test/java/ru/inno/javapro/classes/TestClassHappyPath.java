package ru.inno.javapro.classes;

import ru.inno.javapro.homework1.annotations.AfterSuite;
import ru.inno.javapro.homework1.annotations.BeforeSuite;
import ru.inno.javapro.homework1.annotations.CsvSource;
import ru.inno.javapro.homework1.annotations.Test;

public class TestClassHappyPath {

    @BeforeSuite
    static void beforeMethod() {
        System.out.print("Before method;");
    }

    @Test
    void firstTestMethod() {
        System.out.print("First test method;");
    }

    @Test(priority = 1)
    @CsvSource("10, Java, 20, true")
    void secondTestMethod(int a, String b, int c, boolean d) {
        System.out.printf("Second test method run with args %d, %s, %d, %b;", a, b, c, d);
    }

    @Test(priority = 20)
    static void thirdTestMethod() {
        System.out.print("Third test method;");
    }

    @AfterSuite
    static void afterMethod() {
        System.out.print("After method;");
    }
}
