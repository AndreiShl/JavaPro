package ru.inno.javapro.classes;

import ru.inno.javapro.homework1.annotations.AfterSuite;
import ru.inno.javapro.homework1.annotations.BeforeSuite;

public class TestClassNonStaticAfterAnnotations {
    @BeforeSuite
    static void firstBeforeMethod() {
    }

    @AfterSuite
    void secondNonStaticAfterMethod() {
    }
}
