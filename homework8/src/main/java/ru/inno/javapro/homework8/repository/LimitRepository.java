package ru.inno.javapro.homework8.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.inno.javapro.homework8.model.Limit;

import java.util.Optional;

@Repository
public interface LimitRepository extends CrudRepository<Limit, Integer> {

    @Modifying(clearAutomatically = true)
    @Query("update Limit l set l.limit = :limit")
    @Transactional
    void setAllLimit(@Param("limit") Double limit);

    Optional<Limit> findFirstByUserId(Integer userId);
}
