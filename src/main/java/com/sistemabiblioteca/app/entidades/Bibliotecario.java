package com.sistemabiblioteca.app.entidades;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "bibliotecario")
public class Bibliotecario {
	@Id
	private String documento;
	
	private String nombre;
	
	private String apellido;
	
	private String correo;

}
