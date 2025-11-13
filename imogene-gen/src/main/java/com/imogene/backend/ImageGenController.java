package com.imogene.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageGenController {
    @GetMapping("/api/gen/ping")
    public String ping(){
        return "Imogene-gen is working :)";
    }
}
