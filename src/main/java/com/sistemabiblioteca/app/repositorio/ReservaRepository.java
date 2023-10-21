package com.sistemabiblioteca.app.repositorio;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sistemabiblioteca.app.entidades.Reserva;

public interface ReservaRepository extends MongoRepository<Reserva, String> {
	
	List<Reserva> findByDocEstudiante(String docEstudiante);
	List<Reserva> findByEstado(String estado);
	Reserva findByCodigo(String codigo);
    void deleteByCodigo(String codigo);
	

}
