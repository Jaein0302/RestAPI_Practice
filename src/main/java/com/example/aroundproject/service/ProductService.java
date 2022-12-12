package com.example.aroundproject.service;

import com.example.aroundproject.data.dto.ProductDto;

public interface ProductService {
    //db에 저장
    ProductDto saveProduct(String productId, String productName, int productPrice, int productStock);

    //id로 조회
    ProductDto getProduct(String productId);
}
