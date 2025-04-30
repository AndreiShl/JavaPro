package ru.inno.javapro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.inno.javapro.model.Users;
import ru.inno.javapro.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<Users> getUserById(int id) {
        return userRepository.findById(id);
    }

    public Optional<Users> getUser(Integer id) {
        return userRepository.findById(id);
    }

    public Users saveUser(Users user) {
        return userRepository.save(user);
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public Iterable<Users> getAllUsers() {
        return userRepository.findAll();
    }
}
