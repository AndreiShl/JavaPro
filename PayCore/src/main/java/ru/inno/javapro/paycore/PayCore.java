package ru.inno.javapro.paycore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Добавте сервис платежного ядра в один проект с сервисом продуктов в виде отдельного модуля
 * Добавить интеграцию платежного сервиса с сервисом продуктов через RestTemplate (или RestClient)
 * Добавить возможность запрашивать продукты у платежного сервиса (клиент кидает запрос в платежный сервис, платежный сервис запрашивает продукты клиента у сервиса продуктов и возвращает клиенту результат)
 * Добавить процесс исполнения платежа, так же в нем выбор продукта, проверку его существования и достаточности средств на нем
 * Добавить возврат ошибок клиенту о проблемах как на стороне платежного сервиса, так и на стороне сервиса продуктов
 */
@SpringBootApplication
public class PayCore {
    public static void main(String[] args) {
        SpringApplication.run(PayCore.class, args);
    }
}
