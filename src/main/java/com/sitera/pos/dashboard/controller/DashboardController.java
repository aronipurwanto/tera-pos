package com.sitera.pos.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class DashboardController {
    @GetMapping
    public ModelAndView dashboard() {
        return new ModelAndView("redirect:/dashboard");
    }

    @GetMapping("dashboard")
    public ModelAndView index() {
        return new ModelAndView("pages/dashboard/index");
    }
}
