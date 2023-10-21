package com.sistemabiblioteca.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.sistemabiblioteca.app.entidades.Libro;
import com.sistemabiblioteca.app.entidades.Prestamo;
import com.sistemabiblioteca.app.entidades.Reserva;
import com.sistemabiblioteca.app.repositorio.LibroRepository;
import com.sistemabiblioteca.app.repositorio.PrestamoRepository;
import com.sistemabiblioteca.app.repositorio.ReservaRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Estudent")
@SessionAttributes("documentoEstudiante")
public class ModuloEstudianteWebController {

	@Autowired
	private LibroRepository libroRepository;

	@Autowired
	private ReservaRepository reservaRepository;
	
	@Autowired
	private PrestamoRepository prestamoRepository;

	@GetMapping("/librosDisponibles")
	public String mostrarLibrosDisponibles(Model model) {
		List<Libro> librosDisponibles = libroRepository.findByEstado("disponible");
		model.addAttribute("libros", librosDisponibles);
		return "ModEstudent/lista-libros-dispo";
	}

	@GetMapping("/buscarLibros")
	public String buscarLibrosDisponibles(@RequestParam(name = "query", required = false) String query, Model model) {
		List<Libro> librosDisponibles;

		if (query != null && !query.isEmpty()) {
			librosDisponibles = libroRepository.findByEstadoAndTituloContainsOrAutorContains("disponible", query,
					query);
		} else {
			librosDisponibles = libroRepository.findByEstado("disponible");
		}

		model.addAttribute("libros", librosDisponibles);
		return "ModEstudent/lista-libros-dispo :: #libros-container";
	}

	@GetMapping("/reservar/{codLibro}")
	public String reservasNewTemplate(@PathVariable("codLibro") String codLibro, Model model, HttpSession session) {
		Libro libro = libroRepository.findById(codLibro).orElse(null);

		if (libro == null) {
			return "redirect:/Estudent";
		}

		String documentoEstudiante = (String) session.getAttribute("documentoEstudiante");
		if (documentoEstudiante == null) {
			return "redirect:/loginEstudent";
		}

		model.addAttribute("libro", libro);
		model.addAttribute("documentoEstudiante", documentoEstudiante);
		Reserva reserva = new Reserva();
		reserva.setCodLibro(libro.getCodigo());
		model.addAttribute("reserva", reserva);

		return "ModEstudent/reservas-form-estudent";
	}

	@PostMapping("/save")
	public String reservaSaveProcess(@ModelAttribute("reserva") Reserva reserva, HttpSession session) {
		if (reserva.getCodigo() == null || reserva.getCodigo().isEmpty()) {
			reserva.setCodigo(null);
		}
		String documentoEstudiante = (String) session.getAttribute("documentoEstudiante");
		if (documentoEstudiante == null) {
			return "redirect:/loginEstudent";
		}
		reserva.setDocEstudiante(documentoEstudiante);
		reserva.setEstado("espera");

		Libro libro = libroRepository.findById(reserva.getCodLibro()).orElse(null);

		if (libro == null) {
			return "redirect:/Estudent";
		}
		if ("disponible".equals(libro.getEstado())) {
			libro.setEstado("reservado");
			libroRepository.save(libro);
			reservaRepository.save(reserva);
		} else {

			return "redirect:/Estudent/librosDisponibles";
		}
		reservaRepository.save(reserva);
		return "redirect:/Estudent/librosDisponibles";
	}

	@GetMapping("/misReservas")
	public String mostrarMisReservas(Model model, HttpSession session) {
		String documentoEstudiante = (String) session.getAttribute("documentoEstudiante");

		if (documentoEstudiante == null) {
			return "redirect:/loginEstudent";
		}
		List<Reserva> misReservas = reservaRepository.findByDocEstudiante(documentoEstudiante);
		for (Reserva reserva : misReservas) {
			reserva.setLibro(libroRepository.findByCodigo(reserva.getCodLibro()));
		}

		model.addAttribute("misReservas", misReservas);

		return "ModEstudent/lista-mis-reservas";
	}

	@GetMapping("/cancelarReserva/{codigo}")
	public String cancelarReserva(@PathVariable("codigo") String codigo) {
		Reserva reserva = reservaRepository.findById(codigo).orElse(null);

		if (reserva == null) {
			return "redirect:/Estudent/misReservas";
		}

		if ("espera".equals(reserva.getEstado())) {
			reserva.setEstado("cancelada");
			reservaRepository.save(reserva);

			Libro libro = libroRepository.findById(reserva.getCodLibro()).orElse(null);
			if (libro != null) {
				libro.setEstado("disponible");
				libroRepository.save(libro);
			}
		}

		return "redirect:/Estudent/misReservas";
	}
	
	@GetMapping("/prestamosActive")
	public String mostrarPrestamosActive(Model model, HttpSession session) {
		String documentoEstudiante = (String) session.getAttribute("documentoEstudiante");

		if (documentoEstudiante == null) {
			return "redirect:/loginEstudent";
		}
		List<Prestamo> misPrestamosAct = prestamoRepository.findByDocEstudianteAndEstado(documentoEstudiante, "prestado");
		for (Prestamo prestamo : misPrestamosAct) {
			prestamo.setLibro(libroRepository.findByCodigo(prestamo.getCodLibro()));
		}

		model.addAttribute("misPrestamosAct", misPrestamosAct);

		return "ModEstudent/lista-mis-prestamosAct";
	}
	
	@GetMapping("/prestamosHist")
	public String mostrarHistoPrestamos(Model model, HttpSession session) {
		String documentoEstudiante = (String) session.getAttribute("documentoEstudiante");

		if (documentoEstudiante == null) {
			return "redirect:/loginEstudent";
		}
		List<Prestamo> histprestamos = prestamoRepository.findByDocEstudiante(documentoEstudiante);
		for (Prestamo prestamo : histprestamos) {
			prestamo.setLibro(libroRepository.findByCodigo(prestamo.getCodLibro()));
		}

		model.addAttribute("histprestamos", histprestamos);

		return "ModEstudent/lista-mi-prestamosHist";
	}

}
