package com.manager.demo.Controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerInfoController {

    @GetMapping("/home")
    public String homepage(HttpSession session) {
        if(session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "Customer/homepage";
    }
}
