package ru.inno.javapro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.inno.javapro.classes.TestClassDoubleAnnotations;
import ru.inno.javapro.classes.TestClassHappyPath;
import ru.inno.javapro.classes.TestClassNonStaticAfterAnnotations;
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
    @DisplayName("Возникает ошибка когда Класс содержит две аннотации BeforeSuite")
    void shouldGetErrorOnTwoAfterSuite() {
        assertThatThrownBy(() -> TestRunner.runTests(TestClassDoubleAnnotations.class))
                .isInstanceOf(TestRunnerException.class)
                .hasMessageContaining("Более одного метода аннотированы BeforeSuite");
    }

    @Test
    @DisplayName("Возникает ошибка когда Класс содержит не статическую AfterSuite")
    void shouldGetErrorOnNonStaticAfterSuite() {
        assertThatThrownBy(() -> TestRunner.runTests(TestClassNonStaticAfterAnnotations.class))
                .isInstanceOf(TestRunnerException.class)
                .hasMessageContaining("метод аннотированный AfterSuite не static");
    }

    @Test
    @DisplayName("Успешное выполнение")
    void TestRunnerHappyPath() throws ReflectiveOperationException {
        TestRunner.runTests(TestClassHappyPath.class);
        assertThat(outputStreamCaptor.toString().trim()).isEqualTo("Before method;Second test method run with args 10, " +
                " Java, 0, false;First test method;Third test method;After method;");
    }
}
