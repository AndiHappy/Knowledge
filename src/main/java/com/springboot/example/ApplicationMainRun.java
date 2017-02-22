package com.springboot.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//
//We generally recommend that you locate your main application class 
//in a root package above other classes. The @EnableAutoConfiguration annotation is 
//often placed on your main class, and it implicitly defines a base “search package” 
//for certain items. For example, if you are writing a JPA application, the package of
//the @EnableAutoConfiguration annotated class will be used to search for @Entity items.
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ApplicationMainRun {
  public static void main(String[] args) throws Exception {
    SpringApplication.run(ApplicationMainRun.class, args);
  }
}
