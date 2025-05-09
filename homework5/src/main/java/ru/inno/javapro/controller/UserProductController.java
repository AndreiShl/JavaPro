package ru.inno.javapro.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
import java.util.stream.Collectors;

@ResponseBody
@RestController
@RequiredArgsConstructor
@RequestMapping("/UserProduct")
public class UserProductController {

    private final ProductService productService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("findAllProductsByUserId")
    List<ProductDto> findAllProductsByUserId(@RequestParam Integer userId) {
        return productService
                .findAllProductsByUserId(userId)
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping("saveUser")
    URI saveUser(@RequestBody UserDto userDto) {
        Users user = userService.saveUser(modelMapper.map(userDto, Users.class));

        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/UserProduct")
                .path("/getUserById")
                .queryParam("userId", user.getId())
                .build()
                .toUri();
    }

    @GetMapping("getUserById")
    UserDto getUserById(@RequestParam Integer userId) {
        return modelMapper
                .map(
                        userService
                                .getUser(userId)
                                .orElseThrow(() -> new RuntimeException("No user whith id " + userId)),
                        UserDto.class);
    }

    @PostMapping("saveUserProduct")
    URI saveUserProduct(@RequestParam Integer userId, @RequestBody ProductDto productDto) {
        Users user = userService.getUserById(userId).orElseThrow();
        Product product = modelMapper.map(productDto, Product.class);
        product.setUser(user);
        product = productService.saveProduct(product);

        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/UserProduct")
                .path("/getProductById")
                .queryParam("productId", product.getId())
                .build()
                .toUri();
    }

    @GetMapping("getProductById")
    ProductDto getProductById(Integer productId) {
        return modelMapper.map(productService.findProductById(productId).orElseThrow(), ProductDto.class);
    }
}
