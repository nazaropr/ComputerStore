package com.example.ComputerStore;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Hellocontroller {
    @GetMapping("test")
    public String index(Model model) {
        // Додайте дані моделі, якщо вони необхідні
        model.addAttribute("message", "Привіт, світ!");

        return "index";
    }
    @GetMapping("/api")
    public String Api(){
        return "this is api";
    }

}
