package org.j2os.jauth;

import org.j2os.jauth.client.controller.JAuthController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainSpring {
    public static void main(String[] args) {
        JAuthController.setServer("http://localhost:8081");
        JAuthController.setToken("myjava123");
        SpringApplication.run(MainSpring.class);

    }
}
