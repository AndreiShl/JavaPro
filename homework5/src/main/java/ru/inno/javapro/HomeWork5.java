package ru.inno.javapro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Скорректируйте ваш сервис:
 * - вместо имеющуейся сущности user сделайте Entity
 * - Подключите стартер Spring data jpa
 * - вынесите все настройки в файл application.yml/properties
 * - опишите repository для работы с сущностью, можете использовать Query если посчитаете нужным
 * - исправте методы UserService на работу с repository
 * - опишите класс CommandLineRunner и выполните возможные операции
 * - добавте в проект миграцию для создания таблиц базы, инициализируйте тестовый набор данных в бд
 */
@SpringBootApplication
public class HomeWork5
{
    public static void main( String[] args ) {
        SpringApplication.run(HomeWork5.class, args);
    }
}
