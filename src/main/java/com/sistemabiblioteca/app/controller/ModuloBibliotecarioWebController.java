package com.sistemabiblioteca.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sistemabiblioteca.app.entidades.Libro;
import com.sistemabiblioteca.app.entidades.Prestamo;
import com.sistemabiblioteca.app.entidades.Reserva;
import com.sistemabiblioteca.app.repositorio.LibroRepository;
import com.sistemabiblioteca.app.repositorio.PrestamoRepository;
import com.sistemabiblioteca.app.repositorio.ReservaRepository;
import com.sistemabiblioteca.app.service.PrestamoService;
import com.sistemabiblioteca.app.service.ReservaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Bibliot")
@SessionAttributes("documentoBibliotecario")
public class ModuloBibliotecarioWebController {
	
	private final PrestamoService prestamoService;
    private final ReservaService reservaService; // Agrega el servicio de ReservaService

    public ModuloBibliotecarioWebController(PrestamoService prestamoService, ReservaService reservaService) {
        this.prestamoService = prestamoService;
        this.reservaService = reservaService; // Inyecta el servicio de ReservaService
    }

    @Autowired
    private PrestamoRepository prestamoRepository;
    
    @Autowired
    private ReservaRepository reservaRepository;
    
    @Autowired
    private LibroRepository libroRepository;
   

    @PostMapping("/prestamos/actualizarEstado/{codigo}/{nuevoEstado}")
    public String actualizarEstadoPrestamo(@PathVariable String codigo, @PathVariable String nuevoEstado) {
        Prestamo prestamo = prestamoRepository.findByCodigo(codigo);
        if (prestamo != null) {
            prestamo.setEstado(nuevoEstado);
            prestamoRepository.save(prestamo);
        }
        return "redirect:/Bibliot/prestamos/";
    }
    
    @GetMapping("/prestamos/buscar/{codigo}")
    public String buscarPrestamoPorCodigo(@PathVariable String codigo, Model model) {
        Prestamo prestamo = prestamoRepository.findByCodigo(codigo);
        if (prestamo != null) {
            model.addAttribute("prestamo", prestamo);
        }
        return "ModBibliotecario/prestamos-form-bibliotecario"; // Puedes crear una vista específica para la búsqueda si lo prefieres.
    }

    @GetMapping("/prestamos/aplazar/{codigo}")
    public String aplazarPrestamoForm(@PathVariable String codigo, Model model) {
        Prestamo prestamo = prestamoService.buscarPrestamoPorCodigo(codigo);
        model.addAttribute("prestamo", prestamo);
        return "ModBibliotecario/aplazar-prestamo";
    }

    @PostMapping("/prestamos/aplazar/{codigo}")
    public String aplazarPrestamo(@PathVariable String codigo, @ModelAttribute("prestamo") Prestamo prestamo) {
        prestamoService.aplazarPrestamo(codigo, prestamo.getFechaVencimiento());
        return "redirect:/Bibliot/prestamos/";
    }
   
    @GetMapping("/prestamos/marcarDevuelto/{codigo}")
    public String marcarDevuelto(@PathVariable String codigo) {
        prestamoService.marcarPrestamoComoDevuelto(codigo);
        return "redirect:/Bibliot/prestamos/";
    }
    
    @GetMapping("/reservas/en-espera")
    public String obtenerReservasEnEspera(Model model) {
        List<Reserva> reservasEnEspera = reservaService.obtenerReservasPorEstado("espera");
        model.addAttribute("reservas", reservasEnEspera);
        return "ModBibliotecario/reservas-list-bibliotecario";
    }

    @GetMapping("/reservas/aprobar-reserva/{docEstudiante}/{codLibro}/{codigo}")
    public String aprobarReserva(@PathVariable("docEstudiante") String docEstudiante, @PathVariable("codLibro") String codLibro, HttpSession session,Model model, @PathVariable("codigo") String codReserva) {
    	String documentoBibliotecario = (String) session.getAttribute("documentoBibliotecario");
		if (documentoBibliotecario == null) {
			return "redirect:/loginApp";
		}
		Libro libro = libroRepository.findById(codLibro).orElse(null);
		Reserva reserva = reservaRepository.findById(codReserva).orElse(null);
		model.addAttribute("libro", libro);
		Prestamo prestamo = new Prestamo();
		prestamo.setCodLibro(libro.getCodigo());
		prestamo.setDocEstudiante(docEstudiante);
		prestamo.setDocBiblio(documentoBibliotecario);
		model.addAttribute("prestamo", prestamo);
		model.addAttribute("reserva", reserva);
        return "ModBibliotecario/prestamos-reservas-form";
    }
    
    @PostMapping("/savePrest")
	public String prestamoSaveProcess(@ModelAttribute("prestamo") Prestamo prestamo, HttpSession session, @RequestParam String codReserva ) {
		String documentoBibliotecario = (String) session.getAttribute("documentoBibliotecario");
		if (documentoBibliotecario == null) {
			return "redirect:/loginApp";
		}
		if (prestamo.getCodigo() == null || prestamo.getCodigo().isEmpty()) {
			prestamo.setCodigo(null);
		}
		prestamo.setDocBiblio(documentoBibliotecario);
		prestamo.setEstado("prestado");
		
		if(codReserva == null) {
			System.out.println("Reserva null");
		}
		System.out.println("codigo  reserva: "+ codReserva);
		
		Reserva reserva = reservaRepository.findById(codReserva).orElse(null);

		Libro libro = libroRepository.findById(reserva.getCodLibro()).orElse(null);
		
		prestamo.setDocEstudiante(reserva.getDocEstudiante());
		prestamo.setCodLibro(reserva.getCodLibro());

		if (libro == null) {
			return "redirect:/Bibliot/reservas/en-espera";
		}
		if ("reservado".equals(libro.getEstado())) {
			libro.setEstado("prestado");
			libroRepository.save(libro);
			reserva.setEstado("aceptada");
			reservaRepository.save(reserva);
			prestamoRepository.save(prestamo);
		} else {

			return "redirect:/Bibliot/reservas/en-espera";
		}
		prestamoRepository.save(prestamo);
		return "redirect:/Bibliot/reservas/en-espera";
	}


}

