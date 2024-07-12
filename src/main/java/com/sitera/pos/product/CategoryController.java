package com.sitera.pos.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("pages/category/index");
    }
}
