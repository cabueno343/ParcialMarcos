package com.sistemabiblioteca.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sistemabiblioteca.app.entidades.Bibliotecario;
import com.sistemabiblioteca.app.exception.NotFoundException;
import com.sistemabiblioteca.app.repositorio.BibliotecarioRepository;

@Controller
@RequestMapping("/bibliotecarios")
public class BibliotecarioWebController {
	
	@Autowired
	private BibliotecarioRepository bibliotecarioRepository;
	
	@GetMapping("/")
	public String bibliotecariosListTemplate(Model model) {
		model.addAttribute("bibliotecarios", bibliotecarioRepository.findAll());
		return "ModAdmin/bibliotecarios-list";
	}
	
	@GetMapping("/new")
    public String bibliotecariosNewTemplate(Model model) {
        model.addAttribute("bibliotecario", new Bibliotecario());
        return "ModAdmin/bibliotecarios-form";
    }
	
	@GetMapping("/edit/{id}")
    public String bibliotecariosEditTemplate(@PathVariable("id") String id, Model model) {
        model.addAttribute("bibliotecario", bibliotecarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Bibliotecario no encontrado")));
        return "ModAdmin/bibliotecarios-form";
    }
	
	@PostMapping("/save")
    public String bibliotecarioSaveProcess(@ModelAttribute("bibliotecario") Bibliotecario bibliotecario) {
        if (bibliotecario.getDocumento().isEmpty()) {
        	bibliotecario.setDocumento(null);
        }
        bibliotecarioRepository.save(bibliotecario);
        return "redirect:/bibliotecarios/";
    }
	
	@GetMapping("/delete/{id}")
    public String bibliotecarioDeleteProcess(@PathVariable("id") String id) {
		bibliotecarioRepository.deleteById(id);
        return "redirect:/bibliotecarios/";
    }

}
