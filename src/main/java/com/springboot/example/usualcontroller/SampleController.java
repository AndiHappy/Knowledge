package com.springboot.example.usualcontroller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration

//@RestController. This is known as a stereotype annotation.
public class SampleController {

    @RequestMapping("/com.usualcontroller")
    @ResponseBody
    String home() {
        return "com.usualcontroller: Hello World!";
    }
}