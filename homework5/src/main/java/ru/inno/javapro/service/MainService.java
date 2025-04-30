package ru.inno.javapro.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import ru.inno.javapro.model.Users;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService implements CommandLineRunner {

    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        userService.deleteAllUsers();

        Users firstUser = userService.saveUser(new Users(null, "Ivan"));
        log.info("firstUserId is {}", firstUser.getId());
        Users secondUserId = userService.saveUser(new Users(null, "Petr"));
        log.info("secondUserId is {}", secondUserId);
        Users userWithIdOfFirstUser = userService.getUser(firstUser.getId()).get();
        log.info("userWithIdOfFirstUser is {}", userWithIdOfFirstUser);
        log.info("All users {}", userService.getAllUsers());
        userService.deleteUserById(firstUser.getId());
        log.info("All users {}", userService.getAllUsers());
    }
}
