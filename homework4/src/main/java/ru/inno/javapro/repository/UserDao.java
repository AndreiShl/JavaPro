package ru.inno.javapro.repository;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.inno.javapro.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Позволяет выполнять CRUD операции над пользователями.
 */
@Repository
@RequiredArgsConstructor
public class UserDao {
    private final HikariDataSource dataSource;

    /**
     * Получение пользователя по идентификатору.
     * Если пользователь не найден, возникает RuntimeException
     * @param id идентификатор пользователя
     * @return объект User
     */
    public User getUserById(Long id) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pst = connection.prepareStatement("select * from users where id = ?");
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new User(rs.getLong(1), rs.getString(2));
            } else {
                throw new RuntimeException("User not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pst = connection.prepareStatement("select * from users");
            List<User> users = new ArrayList<>();
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getLong(1), rs.getString(2)));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Добавление пользователя
     * @param user добавляемый пользователь
     * @return полученный идентификатор
     */
    public Long addUser(User user) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pst = connection.prepareStatement("insert into users(username) values(?)",
                    Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, user.username());
            int numRows = pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("no id");
    }

    /**
     * Удаление пользователя.
     * @param id идентификатор пользователя
     */
    public void deleteUser(Long id) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pst = connection.prepareStatement("delete from users where id = ?");
            pst.setLong(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Обновляет информацию пользователя
     * @param user пользователь
     */
    public void updateUser(User user) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pst = connection.prepareStatement("update users set username = ? where id = ?");
            pst.setString(1, user.username());
            pst.setLong(2, user.id());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
