package com.umasuo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by umasuo on 17/2/9.
 */
@SpringBootApplication
@RestController
public class Application {

  @GetMapping("/test")
  public String testApi() {
    return "test";
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
