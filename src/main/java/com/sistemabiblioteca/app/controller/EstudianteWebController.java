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
@RequestMapping("/estudiantes")
public class EstudianteWebController {
	
	@Autowired
	private EstudianteRepository estudianteRepository;
	
	@GetMapping("/")
	public String estudiantesListTemplate(Model model) {
		model.addAttribute("estudiantes", estudianteRepository.findAll());
		return "ModAdmin/estudiantes-list";
	}
	
	@GetMapping("/new")
    public String estudiantesNewTemplate(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        return "ModAdmin/estudiantes-form";
    }
	
	@GetMapping("/edit/{id}")
    public String estudianteEditTemplate(@PathVariable("id") String id, Model model) {
        model.addAttribute("estudiante", estudianteRepository.findById(id).orElseThrow(() -> new NotFoundException("estudiante no encontrado")));
        return "ModAdmin/estudiantes-form";
    }
	
	@PostMapping("/save")
    public String estudianteSaveProcess(@ModelAttribute("estudiante") Estudiante estudiante) {
        if (estudiante.getDocumento().isEmpty()) {
            estudiante.setDocumento(null);
        }
        estudianteRepository.save(estudiante);
        return "redirect:/estudiantes/";
    }
	
	@GetMapping("/delete/{id}")
    public String estudianteDeleteProcess(@PathVariable("id") String id) {
		estudianteRepository.deleteById(id);
        return "redirect:/estudiantes/";
    }

}
