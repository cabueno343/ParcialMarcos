package com.sistemabiblioteca.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistemabiblioteca.app.entidades.Libro;
import com.sistemabiblioteca.app.repositorio.LibroRepository;

@Service
public class LibroService {

	@Autowired
    private LibroRepository libroRepository;

    public void actualizarLibro(Libro libro) {
        libroRepository.save(libro);
    }
}

