package ru.inno.javapro;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.inno.javapro.model.User;
import ru.inno.javapro.service.UserService;

/**
 * - Разверните локально postgresql БД, создайте таблицу users (id bigserial primary key, username varchar(255) unique);
 * - Создайте Maven проект и подключите к нему: драйвер postgresql, hickaricp, spring context.
 * - Создайте пул соединений в виде Spring бина
 * - Создайте класс User (Long id, String username)
 * - Реализуйте в виде бина класс UserDao который позволяет выполнять CRUD операции над пользователями
 * - Реализуйте в виде бина UserService, который позволяет: создавать, удалять, получать одного, получать всех пользователей из базы данных
 * - Создайте Spring Context, получите из него бин UserService и выполните все возможные операции *
 */
@RequiredArgsConstructor
@Slf4j
public class HomeWork4 {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("ru.inno.javapro");
        UserService userService = context.getBean(UserService.class);
        //Сначала очистим БД
        for (User user : userService.getAllUsers()) {
            userService.deleteUser(user.id());
        }

        Long firstUserId = userService.addUser(new User(null, "Ivan"));
        System.out.println("firstUserId is " + firstUserId);
        Long secondUserId = userService.addUser(new User(null, "Petr"));
        System.out.println("secondUserId is " + secondUserId);
        User firstUser = userService.getUser(firstUserId);
        System.out.println(firstUser);
        System.out.println(userService.getAllUsers());
        userService.deleteUser(firstUserId);
        System.out.println(userService.getAllUsers());
    }
}
