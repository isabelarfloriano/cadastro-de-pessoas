package com.java.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.java.backend")
public class CadastroDePessoasApplication {

    public static void main(String[] args) {
        SpringApplication.run(CadastroDePessoasApplication.class, args);
    }

}