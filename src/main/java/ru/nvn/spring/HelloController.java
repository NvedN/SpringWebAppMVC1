package ru.nvn.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author NVN
 */
@Controller
public class HelloController {

    @GetMapping("/hello-world")
    public String sayHello() {
        return "hello_world";
    }
}
