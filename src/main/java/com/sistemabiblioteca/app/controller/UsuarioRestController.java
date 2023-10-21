package com.sistemabiblioteca.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistemabiblioteca.app.entidades.Usuario;
import com.sistemabiblioteca.app.exception.NotFoundException;
import com.sistemabiblioteca.app.repositorio.UsuarioRepository;

@RestController
@RequestMapping(value = "/api/usuarios")
public class UsuarioRestController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/")
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public Usuario getUsuarioById(@PathVariable String id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
    }

    @PostMapping("/")
    public Usuario saveUsuario(@RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Usuario usuario = mapper.convertValue(body, Usuario.class);
        return usuarioRepository.save(usuario);
    }

    @PutMapping("/{id}")
    public Usuario updateUsuario(@PathVariable String id, @RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Usuario usuario = mapper.convertValue(body, Usuario.class);
        usuario.setDocumento(id);
        return usuarioRepository.save(usuario);
    }

    @DeleteMapping("/{id}")
    public Usuario deleteUsuario(@PathVariable String id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        usuarioRepository.deleteById(id);
        return usuario;
    }

}
