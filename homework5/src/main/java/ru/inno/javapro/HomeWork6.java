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

/**
 * Доработайте сервис, пусть теперь он хранит продукты клиентов.
 * Хранение продуктов необходимо организовать аналогично ранее добавленным пользователям.
 * У каждого пользователя может быть несколько продуктов, но у каждого продукта один пользователь.
 * Продукт клиента: id, номер счета, баланс, тип продукта (счет, карта), userId.
 * Сервис должен хранить продукты.
 * Сервис должен давать возможность: запросить все продукты по userId, запросить продукт по productId.
 * Все изменения в БД выполняются через инструмент миграции, добавленный ранее*
 */
@SpringBootApplication
public class HomeWork6
{
    public static void main( String[] args ) {
        SpringApplication.run(HomeWork6.class, args);
    }
}
