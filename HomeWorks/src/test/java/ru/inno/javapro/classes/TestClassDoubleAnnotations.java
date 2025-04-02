package ru.inno.javapro.classes;

import ru.inno.javapro.homework1.annotations.AfterSuite;
import ru.inno.javapro.homework1.annotations.BeforeSuite;

public class TestClassDoubleAnnotations {
    @BeforeSuite
    static void firstBeforeMethod() {
    }

    @BeforeSuite
    static void secondBeforeMethod() {
    }

    @AfterSuite
    static void firstAfterMethod() {
    }
}
