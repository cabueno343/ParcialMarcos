package com.sistemabiblioteca.app.entidades;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "prestamo")
public class Prestamo {
	@Id
	private String codigo;
	
	private String fecha;
	
	private String fechaVencimiento;
	
	private String estado;
	
	private String docEstudiante;
	
	private String docBiblio;
	
	private String codLibro;
	
	@DBRef
    private Libro libro;
	
	@DBRef
    private Estudiante estudiante;

	
}
