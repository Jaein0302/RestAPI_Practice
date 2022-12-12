package com.example.aroundproject.service.impl;

import com.example.aroundproject.data.dto.ProductDto;
import com.example.aroundproject.data.entity.ProductEntity;
import com.example.aroundproject.data.handler.ProductDataHandler;
import com.example.aroundproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    //데이터 핸들링
    ProductDataHandler productDataHandler;

    @Autowired
    public ProductServiceImpl(ProductDataHandler productDataHandler) {
        this.productDataHandler = productDataHandler;
    }

    @Override
    public ProductDto saveProduct(String productId, String productName, int productPrice, int productStock) {
        ProductEntity productEntity = productDataHandler.saveProductEntity(productId, productName, productPrice, productStock);

        //entity를 다시 dto로 바꾸는 작업 (client, controller, service에서는 dto를 쓰고, service, DAO, DB에서는 entity를 쓰니깐)
        //
        ProductDto productDto = new ProductDto(productEntity.getProductId(), productEntity.getProductName(),
                                                productEntity.getProductPrice(), productEntity.getProductStock());

        return productDto;
    }

    @Override
    public ProductDto getProduct(String productId) {
        ProductEntity productEntity = productDataHandler.getProductEntity(productId);

        ProductDto productDto = new ProductDto(productEntity.getProductId(), productEntity.getProductName(),
                                                productEntity.getProductPrice(), productEntity.getProductStock());

        return productDto;
    }
}
