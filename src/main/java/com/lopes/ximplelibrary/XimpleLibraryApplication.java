package com.lopes.ximplelibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class XimpleLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(XimpleLibraryApplication.class, args);
    }

}
