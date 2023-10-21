package com.sistemabiblioteca.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sistemabiblioteca.app.entidades.Estudiante;
import com.sistemabiblioteca.app.exception.NotFoundException;
import com.sistemabiblioteca.app.repositorio.EstudianteRepository;

@Controller
@RequestMapping("/Bibliot/estudiantes")
public class EstudianteBibliotecarioController {
	
	@Autowired
	private EstudianteRepository estudianteRepository;
	
	@GetMapping("/")
	public String estudiantesListTemplate(Model model) {
		model.addAttribute("estudiantes", estudianteRepository.findAll());
		return "ModBibliotecario/estudiantes-list-bibliotecario";
	}
	
	@GetMapping("/new")
    public String estudiantesNewTemplate(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        return "ModBibliotecario/estudiantes-form-bibliotecario";
    }
	
	@GetMapping("/edit/{id}")
    public String estudianteEditTemplate(@PathVariable("id") String id, Model model) {
        model.addAttribute("estudiante", estudianteRepository.findById(id).orElseThrow(() -> new NotFoundException("estudiante no encontrado")));
        return "ModBibliotecario/estudiantes-form-bibliotecario";
    }
	
	@PostMapping("/save")
    public String estudianteSaveProcess(@ModelAttribute("estudiante") Estudiante estudiante) {
        if (estudiante.getDocumento().isEmpty()) {
            estudiante.setDocumento(null);
        }
        estudianteRepository.save(estudiante);
        return "redirect:/Bibliot/estudiantes/";
    }
	

}

