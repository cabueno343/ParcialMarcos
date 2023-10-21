package com.sistemabiblioteca.app.repositorio;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sistemabiblioteca.app.entidades.Prestamo;

public interface PrestamoRepository extends MongoRepository<Prestamo, String>{
	Prestamo findByCodigo(String codigo);
	List<Prestamo> findByDocEstudianteAndEstado(String docEstudiante, String estado);
	List<Prestamo> findByDocEstudiante(String docEstudiante);
	
	List<Prestamo> findByEstadoIn(List<String> estados);

}
