package com.sistemabiblioteca.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginaController {
	@GetMapping("/")
    public String index() {
        return "index";
    }
	
	@GetMapping("/loginEstudent")
    public String loginEstudent() {
        return "loginEstudent";
    }
	
	@GetMapping("/loginApp")
    public String loginApp() {
        return "loginApp";
    }
	
	@GetMapping("/registerEstudent")
    public String registerEstudent() {
        return "registerEstudent";
    }
	
	@GetMapping("/Estudent")
    public String moduloEstudent() {
        return "/ModEstudent/moduloEstudent";
    }
	
	@GetMapping("/Admin")
    public String moduloAdmin() {
        return "/ModAdmin/moduloAdmin";
    }
	
	@GetMapping("/Bibliot")
    public String moduloBibliot() {
        return "/ModBibliotecario/moduloBibliotecario";
    }

}
