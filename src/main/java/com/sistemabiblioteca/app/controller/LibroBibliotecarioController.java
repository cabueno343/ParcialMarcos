package com.sistemabiblioteca.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sistemabiblioteca.app.entidades.Libro;
import com.sistemabiblioteca.app.exception.NotFoundException;
import com.sistemabiblioteca.app.repositorio.LibroRepository;

@Controller
@RequestMapping("/Bibliot/libros")
public class LibroBibliotecarioController {
	
	@Autowired
    private LibroRepository libroRepository;

    @GetMapping("/")
    public String librosListTemplate(Model model) {
        model.addAttribute("libros", libroRepository.findAll());
        return "ModBibliotecario/libros-list-bibliotecario";
    }

    @GetMapping("/new")
    public String librosNewTemplate(Model model) {
        model.addAttribute("libro", new Libro());
        return "ModBibliotecario/libros-form-bibliotecario";
    }
    
    @GetMapping("/edit/{id}")
    public String librosEditTemplate(@PathVariable("id") String id, Model model) {
        model.addAttribute("libro", libroRepository.findById(id).orElseThrow(() -> new NotFoundException("Libro no encontrado")));
        return "ModBibliotecario/libros-form-bibliotecario";
    }

    @PostMapping("/save")
    public String libroSaveProcess(@ModelAttribute("libro") Libro libro) {
        if (libro.getCodigo().isEmpty() || libro.getCodigo() == null) {
            libro.setCodigo(null);
        }
        libroRepository.save(libro);
        return "redirect:/Bibliot/libros/";
    }
	

}
