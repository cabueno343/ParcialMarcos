package com.sistemabiblioteca.app.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.sistemabiblioteca.app.entidades.Estudiante;
import com.sistemabiblioteca.app.entidades.Libro;
import com.sistemabiblioteca.app.entidades.Prestamo;
import com.sistemabiblioteca.app.repositorio.EstudianteRepository;
import com.sistemabiblioteca.app.repositorio.LibroRepository;
import com.sistemabiblioteca.app.repositorio.PrestamoRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Bibliot/prestamos")
@SessionAttributes("documentoBibliotecario")
public class PrestamoBibliotecarioController {
	@Autowired
    private PrestamoRepository prestamoRepository;
	
	@Autowired
    private LibroRepository libroRepository;
	
	@Autowired
    private EstudianteRepository estudianteRepository;
	

    @GetMapping("/")
    public String prestamosListTemplate(Model model, HttpSession session) {
    	String documentoBibliotecario = (String) session.getAttribute("documentoBibliotecario");
		if (documentoBibliotecario == null) {
			return "redirect:/loginApp";
		}
		List<String> estados = Arrays.asList("prestado", "vencido");
		List<Prestamo> prestamos = prestamoRepository.findByEstadoIn(estados);
		for (Prestamo prestamo : prestamos) {
			prestamo.setLibro(libroRepository.findByCodigo(prestamo.getCodLibro()));
			prestamo.setEstudiante(estudianteRepository.findByDocumento(prestamo.getDocEstudiante()));
		}
        model.addAttribute("prestamos", prestamos);
        
        return "ModBibliotecario/prestamos-list-bibliotecario";
    }

    @GetMapping("/new")
    public String prestamosNewTemplate(Model model, HttpSession session) {
    	String documentoBibliotecario = (String) session.getAttribute("documentoBibliotecario");
		if (documentoBibliotecario == null) {
			return "redirect:/loginApp";
		}
		List<Libro> librosDisponibles = libroRepository.findByEstado("disponible");
		List<Estudiante> estudiantes = estudianteRepository.findAll();
		model.addAttribute("libros", librosDisponibles);
		model.addAttribute("estudiantes", estudiantes);
		model.addAttribute("documentoBibliotecario", documentoBibliotecario);
        model.addAttribute("prestamo", new Prestamo());
        return "ModBibliotecario/prestamos-form-bibliotecario";
    }

    @GetMapping("/Resivir/{id}")
	public String cancelarReserva(@PathVariable("id") String id) {
    	Prestamo prestamo = prestamoRepository.findById(id).orElse(null);
		

		if (prestamo == null) {
			return "redirect:/Bibliot/prestamos/";
		}

		if ("prestado".equals(prestamo.getEstado())) {
			prestamo.setEstado("devuelto");
			prestamoRepository.save(prestamo);

			Libro libro = libroRepository.findById(prestamo.getCodLibro()).orElse(null);
			if (libro != null) {
				libro.setEstado("disponible");
				libroRepository.save(libro);
			}
		}

		return "redirect:/Bibliot/prestamos/";
	}

    @PostMapping("/save")
    public String prestamoSaveProcess(@ModelAttribute("prestamo") Prestamo prestamo) {
        if (prestamo.getCodigo().isEmpty()) {
            prestamo.setCodigo(null);
        }
        Libro libro = libroRepository.findById(prestamo.getCodLibro()).orElse(null);
        if ("disponible".equals(libro.getEstado())) {
			libro.setEstado("prestado");
			libroRepository.save(libro);
			
		}
        prestamoRepository.save(prestamo);
        return "redirect:/Bibliot/prestamos/";
    }
}