package org.j2os.jauth.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @GetMapping("/modir")
    public String modir() {
        return "MODIR IS OK";
    }

    @GetMapping("/karbar")
    public String karbar() {
        return "KARBAR IS OK";
    }
}
