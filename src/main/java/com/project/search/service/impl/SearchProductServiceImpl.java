package com.project.search.service.impl;

import com.project.search.common.ProductDto;
import com.project.search.service.ScraperService;
import com.project.search.service.SearchProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;

@Service
public class SearchProductServiceImpl implements SearchProductService {

    @Autowired
    private ScraperService scraperService;

    @Override
    public List<ProductDto> search(String productName) {
        String name = removeAccent(productName);
        return scraperService.findProducts(name);
    }

    private String removeAccent(String productName) {
        return Normalizer
                .normalize(productName, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
}