package com.sistemabiblioteca.app.repositorio;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sistemabiblioteca.app.entidades.Libro;

@Repository
public interface LibroRepository extends MongoRepository<Libro, String>{
	List<Libro> findByEstadoAndTituloContainsOrAutorContains(String estado, String titulo, String autor);
    List<Libro> findByEstado(String estado);
    Libro findByCodigo(String id);
}
