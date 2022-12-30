package com.project.search.service;

import com.project.search.common.ProductDto;

import java.util.List;

public interface ScraperService {

    List<ProductDto> findProducts(String productName);

}
