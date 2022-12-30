package com.project.search.controller;

import com.project.search.common.ProductDto;
import com.project.search.service.SearchProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("find-products")
public class SearchProductController {

    @Autowired
    private SearchProductService searchProductService;

    @GetMapping("/{productName}")
    ResponseEntity<List<ProductDto>> search(@PathVariable("productName") String productName) {
        List<ProductDto> products = searchProductService.search(productName);

        return ResponseEntity.status(HttpStatus.OK).body(products);
    }
}
