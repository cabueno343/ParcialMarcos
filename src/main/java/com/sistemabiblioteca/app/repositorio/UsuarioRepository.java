package com.sistemabiblioteca.app.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sistemabiblioteca.app.entidades.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
	Usuario findByDocumento(String documento);
}
