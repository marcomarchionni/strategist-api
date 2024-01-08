package com.marcomarchionni.strategistapi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DocumentationController {

    @GetMapping(value = {"/", "/docs", "/docs/"})
    public String getDocumentation() {
        return "redirect:/docs/index.html";
    }
}
