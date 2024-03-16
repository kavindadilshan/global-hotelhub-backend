package com.bolton.globalhotelhub.config;

import javax.annotation.PostConstruct;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumConfiguration {

    @PostConstruct
    void postConstruct(){
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
    }

    @Bean
    public ChromeDriver driver() {
        ChromeOptions options = new ChromeOptions();


        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("pageLoadStrategy", "eager");


        return new ChromeDriver(options.merge(caps));
    }
}
