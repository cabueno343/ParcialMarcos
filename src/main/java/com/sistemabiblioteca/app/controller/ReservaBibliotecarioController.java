package com.sistemabiblioteca.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sistemabiblioteca.app.entidades.Reserva;
import com.sistemabiblioteca.app.exception.NotFoundException;
import com.sistemabiblioteca.app.repositorio.ReservaRepository;

@Controller
@RequestMapping("/Bibliot/reservas")
public class ReservaBibliotecarioController {
	
	@Autowired
    private ReservaRepository reservaRepository;

    @GetMapping("/")
    public String reservasListTemplate(Model model) {
        model.addAttribute("reservas", reservaRepository.findAll());
        return "ModBibliotecario/reservas-list-bibliotecario";
    }

    @GetMapping("/edit/{id}")
    public String reservasEditTemplate(@PathVariable("id") String id, Model model) {
        model.addAttribute("reserva", reservaRepository.findById(id).orElseThrow(() -> new NotFoundException("Reserva no encontrada")));
        return "ModBibliotecario/reservas-form-bibliotecario";
    }

    @PostMapping("/save")
    public String reservaSaveProcess(@ModelAttribute("reserva") Reserva reserva) {
        if (reserva.getCodigo().isEmpty()) {
            reserva.setCodigo(null);
        }
        reservaRepository.save(reserva);
        return "redirect:/Bibliot/reservas/en-espera";
    }
}