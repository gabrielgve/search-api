package com.project.search.service;

import com.project.search.common.ProductDto;

import java.util.List;

public interface SearchProductService {

    List<ProductDto> search(String productName);
}
