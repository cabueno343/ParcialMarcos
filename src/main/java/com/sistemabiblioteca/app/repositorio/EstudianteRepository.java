package com.sistemabiblioteca.app.repositorio;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.sistemabiblioteca.app.entidades.Estudiante;


public interface EstudianteRepository extends MongoRepository<Estudiante, String>{
	Estudiante findByDocumento(String documento);
}
