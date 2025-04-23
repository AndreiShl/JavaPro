package ru.inno.javapro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.inno.javapro.model.User;
import ru.inno.javapro.repository.UserDao;

import java.util.List;

@Service
public class UserService {
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    UserDao userDao;
    public User getUser(Long id) {
        return userDao.getUserById(id);
    }

    public Long addUser(User user) {
        return userDao.addUser(user);
    }

    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
    }
}
