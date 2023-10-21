package com.sistemabiblioteca.app.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sistemabiblioteca.app.entidades.Bibliotecario;

public interface BibliotecarioRepository extends MongoRepository<Bibliotecario, String> {

}
