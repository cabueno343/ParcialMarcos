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
import com.sistemabiblioteca.app.entidades.Prestamo;
import com.sistemabiblioteca.app.exception.NotFoundException;
import com.sistemabiblioteca.app.repositorio.PrestamoRepository;

@RestController
@RequestMapping(value = "/api/prestamos")
public class PrestamoRestController {
	
	@Autowired
    private PrestamoRepository prestamoRepository;

    @GetMapping("/")
    public List<Prestamo> getAllPrestamos() {
        return prestamoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Prestamo getPrestamoById(@PathVariable String id) {
        return prestamoRepository.findById(id).orElseThrow(() -> new NotFoundException("Prestamo no encontrado"));
    }

    @PostMapping("/")
    public Prestamo savePrestamo(@RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Prestamo prestamo = mapper.convertValue(body, Prestamo.class);
        return prestamoRepository.save(prestamo);
    }

    @PutMapping("/{id}")
    public Prestamo updatePrestamo(@PathVariable String id, @RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Prestamo prestamo = mapper.convertValue(body, Prestamo.class);
        prestamo.setCodigo(id);
        return prestamoRepository.save(prestamo);
    }

    @DeleteMapping("/{id}")
    public Prestamo deletePrestamo(@PathVariable String id) {
        Prestamo prestamo = prestamoRepository.findById(id).orElseThrow(() -> new NotFoundException("Prestamo no encontrado"));
        prestamoRepository.deleteById(id);
        return prestamo;
    }

}
