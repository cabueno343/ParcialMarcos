package com.sistemabiblioteca.app.entidades;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "libro")
public class Libro {
	
	@Id
	private String codigo;
	
	private String titulo;
	
	private String autor;
	
	private String categoria;
	
	private String estado;
	
	@DBRef
    private Prestamo prestamo;

}
