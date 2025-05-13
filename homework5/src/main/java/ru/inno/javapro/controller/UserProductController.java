package ru.inno.javapro.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.inno.javapro.model.Product;
import ru.inno.javapro.model.Users;
import ru.inno.javapro.model.dto.ProductDto;
import ru.inno.javapro.model.dto.UserDto;
import ru.inno.javapro.service.ProductService;
import ru.inno.javapro.service.UserService;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@ResponseBody
@RestController
@RequiredArgsConstructor
@RequestMapping("/UserProduct")
public class UserProductController {

    private final ProductService productService;
    private final UserService userService;

    @GetMapping("findAllProductsByUserId")
    List<ProductDto> findAllProductsByUserId(@RequestParam Integer userId) {
        return productService.findAllProductsByUserId(userId);
    }

    @PostMapping("saveUser")
    ResponseEntity<Objects> saveUser(@RequestBody UserDto userDto) {
        Users user = userService.saveUser(userDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/UserProduct")
                .path("/getUserById")
                .queryParam("userId", user.getId())
                .build()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("getUserById")
    UserDto getUserById(@RequestParam Integer userId) {
        return userService.getUser(userId);
    }

    @PostMapping("saveUserProduct")
    ResponseEntity<Objects> saveUserProduct(@RequestParam Integer userId, @RequestBody ProductDto productDto) {
        Product product = productService.saveUserProduct(userId, productDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/UserProduct")
                .path("/getProductById")
                .queryParam("productId", product.getId())
                .build()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("getProductById")
    ProductDto getProductById(Integer productId) {
        return productService.findProductById(productId);
    }
}
