package com.project.search.config;

import jakarta.annotation.PostConstruct;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumConfig {

    @PostConstruct
    void setDriverProperty() {
        String osName = System.getProperty("os.name");

        if (osName.equals("Mac OS X")) {
            String osArch = System.getProperty("os.arch");
            if (osArch.equals("aarch64")) {
                System.setProperty("webdriver.chrome.driver", "drivers/chromedriver_mac_arm");
            } else {
                System.setProperty("webdriver.chrome.driver", "drivers/chromedriver_mac");
            }
        } if (osName.equals("Linux")) {
            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver_linux");
        } else {
            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver_win");
        }
    }

    @Bean
    public ChromeDriver chromeDriver() {

        ChromeDriver driver = new ChromeDriver();

        driver.manage().window().maximize();

        return driver;
    }

}
