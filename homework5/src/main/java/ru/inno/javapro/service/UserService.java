package ru.inno.javapro.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.inno.javapro.model.Users;
import ru.inno.javapro.model.dto.UserDto;
import ru.inno.javapro.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public Optional<Users> getUserById(int id) {
        return userRepository.findById(id);
    }

    public UserDto getUser(Integer id) {
        return modelMapper.map(
                userRepository.findById(id).orElseThrow(() -> new RuntimeException("No user whith id " + id)),
                UserDto.class);
    }

    public Users saveUser(UserDto user) {
        return userRepository.save(modelMapper.map(user, Users.class));
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
