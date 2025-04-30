package ru.inno.javapro.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.inno.javapro.model.Users;

@Repository
public interface UserRepository extends CrudRepository<Users, Integer> {
}
