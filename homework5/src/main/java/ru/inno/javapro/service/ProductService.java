package ru.inno.javapro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.inno.javapro.model.Product;
import ru.inno.javapro.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAllProductsByUserId(Integer userId) {
        return productRepository.findProductByUserId(userId);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> findProductById(Integer productId) {
        return productRepository.findById(productId);
    }
}
