package com.sistemabiblioteca.app.entidades;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "usuario")
public class Usuario {
	@Id
	private String documento;
	
	private String contraseña;
	
	private String rol;
	

	public boolean equals(String documento, String contraseña, String rol) {
        return this.documento.equals(documento) && this.contraseña.equals(contraseña) && this.rol.equals(rol);
    }

}
