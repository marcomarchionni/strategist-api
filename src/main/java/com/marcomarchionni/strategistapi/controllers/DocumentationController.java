package com.marcomarchionni.strategistapi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class DocumentationController {

    @GetMapping(value = {"/", "/docs", "/docs/"})
    public RedirectView getDocumentation() {
        return new RedirectView("swagger-ui/index.html");
    }
}
