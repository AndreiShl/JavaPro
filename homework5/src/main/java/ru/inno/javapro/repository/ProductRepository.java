package ru.inno.javapro.repository;

import org.springframework.data.repository.CrudRepository;
import ru.inno.javapro.model.Product;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findProductByUserId(Integer userId);
}
