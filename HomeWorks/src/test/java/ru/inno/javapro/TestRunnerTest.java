package ru.inno.javapro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.inno.javapro.classes.TestClassDoubleAnnotations;
import ru.inno.javapro.classes.TestClassHappyPath;
import ru.inno.javapro.homework1.exceptions.TestRunnerException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TestRunnerTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("При неверно аннотированных методах возникает ошибка")
    void shouldGetErrorOnWrongAnnotation() {
        assertThatThrownBy(() -> TestRunner.runTests(TestClassDoubleAnnotations.class))
                .isInstanceOf(TestRunnerException.class)
                .hasMessageContaining("[Класс TestClassDoubleAnnotations содержит 2 методов аннотированных BeforeSuite, " +
                        "допустимое количество не более 1, Класс TestClassDoubleAnnotations содержит 2 методов аннотированных AfterSuite, " +
                        "допустимое количество не более 1, Класс TestClassDoubleAnnotations содержит не статические методы, аннотированные AfterSuite]");
    }

    @Test
    @DisplayName("Успешное выполнение")
    void TestRunnerHappyPath() throws ReflectiveOperationException {
        TestRunner.runTests(TestClassHappyPath.class);
        assertThat(outputStreamCaptor.toString().trim()).isEqualTo("Before method;Second test method run with args 10, " +
                " Java, 0, false;First test method;Third test method;After method;");
    }
}
