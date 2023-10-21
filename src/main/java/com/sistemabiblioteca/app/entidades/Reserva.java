package com.sistemabiblioteca.app.entidades;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "reserva")
public class Reserva {
	@Id
	private String codigo;
	
	private String fecha;
	
	private String estado;
	
	private String docEstudiante;
	
	private String codLibro;
	
	@DBRef
    private Libro libro;

	
}
