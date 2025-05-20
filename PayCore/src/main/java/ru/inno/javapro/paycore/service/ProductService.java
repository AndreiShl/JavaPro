package ru.inno.javapro.paycore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.inno.javapro.paycore.model.dto.ProductDto;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final RestClient restClient;

    public ProductDto getProduct(Integer productId) {
        return
                restClient
                        .get()
                        .uri("/product",
                                uriBuilder -> uriBuilder
                                        .queryParam("productId", productId)
                                        .build())
                        .retrieve()
                        .onStatus(httpStatusCode -> httpStatusCode.value() == 404, (req, res) -> {
                            throw new NoSuchElementException(new String(res.getBody().readAllBytes()));
                        })
                        .body(ProductDto.class);
    }

    public List<ProductDto> getUserProducts(Integer userId) {
        return restClient
                .get()
                .uri("/product/user",
                        uriBuilder -> uriBuilder.queryParam("userId", userId).build())
                .retrieve()
                .body(List.class);
    }
}
