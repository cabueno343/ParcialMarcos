package com.sistemabiblioteca.app.controller;

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
import com.sistemabiblioteca.app.entidades.Bibliotecario;
import com.sistemabiblioteca.app.exception.NotFoundException;
import com.sistemabiblioteca.app.repositorio.BibliotecarioRepository;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/bibliotecarios")
public class BibliotecarioRestController {
	
	@Autowired
	private BibliotecarioRepository bibliotecarioRepository;
	
	@GetMapping("/")
    public List<Bibliotecario> getAllBibliotecarios() {
        return bibliotecarioRepository.findAll();
    }
	
	@GetMapping("/{id}")
    public Bibliotecario getBibliotecarioById(@PathVariable String id) {
        return bibliotecarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Bibliotecario no encontrado"));
    }
	
	@PostMapping("/")
    public Bibliotecario saveBibliotecario(@RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Bibliotecario bibliotecario = mapper.convertValue(body, Bibliotecario.class);
        return bibliotecarioRepository.save(bibliotecario);
    }
	
	@PutMapping("/{id}")
    public Bibliotecario updateBibliotecario(@PathVariable String id, @RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Bibliotecario bibliotecario = mapper.convertValue(body, Bibliotecario.class);
        bibliotecario.setDocumento(id);
        return bibliotecarioRepository.save(bibliotecario);
    }
	
	@DeleteMapping("/{id}")
    public Bibliotecario deleteBibliotecario(@PathVariable String id) {
		Bibliotecario bibliotecario = bibliotecarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Bibliotecario no encontrado"));
		bibliotecarioRepository.deleteById(id);
        return bibliotecario;
    }

}
