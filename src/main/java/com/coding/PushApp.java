package com.coding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;


/**
 * @author guan
 */
@EnableSwagger2WebMvc
@SpringBootApplication
public class PushApp{

    public static void main(String[] args) {
        SpringApplication.run(PushApp.class, args);
    }


}
