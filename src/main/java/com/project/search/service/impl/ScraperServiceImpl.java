package com.project.search.service.impl;

import com.project.search.common.ProductDto;
import com.project.search.exception.ValidationException;
import com.project.search.service.ScraperService;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ScraperServiceImpl implements ScraperService {

    public static final Logger LOG = LoggerFactory.getLogger(ScraperServiceImpl.class);

    @Value("${scraping.url}")
    private String url;

    @Autowired
    private ChromeDriver chromeDriver;

    @Override
    public List<ProductDto> findProducts(String productName) {
        List<ProductDto> products = new ArrayList<>();

        try {
            chromeDriver.get(url);

            WebElement searchInput = chromeDriver.findElement(By.id("input-busca"));
            searchInput.clear();
            searchInput.sendKeys(productName);
            WebElement buttonSearch = chromeDriver.findElement(By.xpath("//*[@id=\"barraBuscaKabum\"]/div/form/button"));
            buttonSearch.click();
            WebElement orderByLowestValue = chromeDriver.findElement(By.xpath("//*[@id=\"Filter\"]/div[1]/select/option[2]"));
            orderByLowestValue.click();

            List<WebElement> items = chromeDriver.findElements(By.className("productCard"));
            products = collectItemsInfo(items);
        } catch (Exception e) {
            LOG.error("Não foi possível executar o driver", e.getMessage());
            throw new ValidationException("Não foi possível executar o driver");
        }
        finally {
            WebDriver exit = new ChromeDriver();
            exit.quit();
        }
        return products;
    }

    private List<ProductDto> collectItemsInfo(List<WebElement> items) {
        int attempts = 0;
        while(attempts < 2) {
            try {
                return items.stream()
                        .map(item -> {
                            String productDesc = item.findElement(By.className("nameCard")).getText();
                            String price = item.findElement(By.className("priceCard")).getText();
                            String link = item.findElement(By.className("sc-ff8a9791-10")).getAttribute("href");

                            ProductDto productDto = new ProductDto();
                            productDto.setProductName(productDesc);
                            productDto.setValue(price);
                            productDto.setUrl(link);
                            return productDto;
                        }).toList();
            } catch (StaleElementReferenceException e) {
                items = chromeDriver.findElements(By.className("productCard"));
            }
            attempts++;
        }

        return Collections.emptyList();
    }


}
