package com.sistemabiblioteca.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sistemabiblioteca.app.exception.NotFoundException;
import com.sistemabiblioteca.app.entidades.Usuario;
import com.sistemabiblioteca.app.repositorio.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class UsuarioWebController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping("/")
	public String usuariosListTemplate(Model model) {
		model.addAttribute("usuarios", usuarioRepository.findAll());
		return "ModAdmin/usuarios-list";
	}
	
	@GetMapping("/new")
    public String usuariosNewTemplate(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "ModAdmin/usuarios-form";
    }
	
    @GetMapping("/edit/{id}")
    public String usuarioEditTemplate(@PathVariable("id") String id, Model model) {
        model.addAttribute("usuario", usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("usuario no encontrado")));
        return "ModAdmin/usuarios-form";
    }

    @PostMapping("/save")
    public String usuarioSaveProcess(@ModelAttribute("usuario") Usuario usuario) {
        if (usuario.getDocumento().isEmpty()) {
            usuario.setDocumento(null);
        }
        usuarioRepository.save(usuario);
        return "redirect:/usuarios/";
    }

    @GetMapping("/delete/{id}")
    public String usuarioDeleteProcess(@PathVariable("id") String id) {
    	usuarioRepository.deleteById(id);
        return "redirect:/usuarios/";
    }

}
