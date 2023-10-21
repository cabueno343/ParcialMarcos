package com.sistemabiblioteca.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistemabiblioteca.app.entidades.Libro;
import com.sistemabiblioteca.app.exception.NotFoundException;
import com.sistemabiblioteca.app.repositorio.LibroRepository;

@RestController
@RequestMapping(value = "/api/libros")
public class LibroRestController {
	
	@Autowired
    private LibroRepository libroRepository;
	
	@GetMapping("/")
    public List<Libro> getAllLibros() { 
        return libroRepository.findAll();
    }
	
	@GetMapping("/{id}")
    public Libro getLibroById(@PathVariable String id) {
        return libroRepository.findById(id).orElseThrow(() -> new NotFoundException("Libro no encontrado"));
    }
    
    @PostMapping("/")
    public Libro saveLibro(@RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Libro libro = mapper.convertValue(body, Libro.class);
        return libroRepository.save(libro);
    }

    @PutMapping("/{id}")
    public Libro updateLibro(@PathVariable String id, @RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Libro libro = mapper.convertValue(body, Libro.class);
        libro.setCodigo(id);
        return libroRepository.save(libro);
    }

    @DeleteMapping("/{id}")
    public Libro deleteLibro(@PathVariable String id) {
        Libro libro = libroRepository.findById(id).orElseThrow(() -> new NotFoundException("Libro no encontrado"));
        libroRepository.deleteById(id);
        return libro;
    }

}
