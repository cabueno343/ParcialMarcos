package com.sistemabiblioteca.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistemabiblioteca.app.entidades.Usuario;
import com.sistemabiblioteca.app.entidades.Estudiante;
import com.sistemabiblioteca.app.exception.NotFoundException;
import com.sistemabiblioteca.app.repositorio.EstudianteRepository;
import com.sistemabiblioteca.app.repositorio.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	private UsuarioRestController usuarioRestController;

	@Autowired
	private UsuarioRepository usuarioRepositoryr;

	@Autowired
	private EstudianteRepository estudianteRepositoryr;

	@PostMapping("/loginEst")
	public String login(@RequestParam String documento, @RequestParam String contraseña, @RequestParam String rol,
			RedirectAttributes attributes, HttpSession session) {
		Usuario usuario = null;
		try {
			usuario = usuarioRestController.getUsuarioById(documento);
		} catch (NotFoundException e) {
			attributes.addFlashAttribute("error", "Usuario no encontrado");
			return "redirect:/loginEstudent";
		}

		if (usuario != null && usuario.equals(documento, contraseña, rol)) {
			session.setAttribute("documentoEstudiante", documento);
			return "redirect:/Estudent";
		} else {
			attributes.addFlashAttribute("error", "Usuario o contraseña incorrectos");
			return "redirect:/loginEstudent";
		}
	}

	@PostMapping("/loginUsu")
	public String loginApp(@RequestParam String documento, @RequestParam String contraseña, @RequestParam String rol,
			RedirectAttributes attributes, HttpSession session) {
		Usuario usuario = null;
		try {
			usuario = usuarioRestController.getUsuarioById(documento);
		} catch (NotFoundException e) {
			attributes.addFlashAttribute("error", "Usuario no encontrado");
			return "redirect:/loginApp";
		}

		if (usuario != null && usuario.equals(documento, contraseña, rol)) {
			if ("administrador".equals(usuario.getRol())) {
				session.setAttribute("documentoAdmin", documento);
				return "redirect:/Admin";
			} else {
				session.setAttribute("documentoBibliotecario", documento);
				return "redirect:/Bibliot";
			}
		} else {
			attributes.addFlashAttribute("error", "Usuario o contraseña incorrectos");
			return "redirect:/loginApp";
		}
	}

	@PostMapping("/saveEstudent")
	public String registerEstudent(@RequestParam String documento, @RequestParam String contraseña,
			@RequestParam String nombre, @RequestParam String apellido, @RequestParam String correo, @RequestParam String confcontra,
			RedirectAttributes attributes) {
		Usuario usuario = usuarioRepositoryr.findById(documento).orElse(null);

		if (usuario == null) {
			if (contraseña.equals(confcontra)) {
				Usuario addusuario = new Usuario();
				addusuario.setDocumento(documento);
				addusuario.setContraseña(contraseña);
				addusuario.setRol("estudiante");
				
				Estudiante est = new Estudiante();
				est.setDocumento(documento);
				est.setNombre(nombre);
				est.setApellido(apellido);
				est.setCorreo(correo);
				
				
				attributes.addFlashAttribute("success", "Las constraseñas deben coincidir");
				
				usuarioRepositoryr.save(addusuario);
				estudianteRepositoryr.save(est);

				return "redirect:/loginEstudent";

			} else {
				attributes.addFlashAttribute("error", "Las constraseñas deben coincidir");
				return "redirect:/registerEstudent";
			}

		} else {
			attributes.addFlashAttribute("error", "Documento ya existe");
			return "redirect:/registerEstudent";
		}

	}

}
