package ru.inno.javapro.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.inno.javapro.model.Product;
import ru.inno.javapro.model.Users;
import ru.inno.javapro.model.dto.ProductDto;
import ru.inno.javapro.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public List<ProductDto> findAllProductsByUserId(Integer userId) {
        return productRepository.findProductByUserId(userId).stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product saveUserProduct(Integer userId, ProductDto productDto) {
        Users user = userService.getUserById(userId).orElseThrow();
        Product product = modelMapper.map(productDto, Product.class);
        product.setUser(user);
        return productRepository.save(product);
    }

    public ProductDto findProductById(Integer productId) {
        return modelMapper.map(productRepository.findById(productId).orElseThrow(), ProductDto.class);
    }
}
