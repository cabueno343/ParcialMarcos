package com.sistemabiblioteca.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sistemabiblioteca.app.entidades.Prestamo;
import com.sistemabiblioteca.app.exception.NotFoundException;
import com.sistemabiblioteca.app.repositorio.PrestamoRepository;

@Controller
@RequestMapping("/prestamos")
public class PrestamoWebController {
	@Autowired
    private PrestamoRepository prestamoRepository;

    @GetMapping("/")
    public String prestamosListTemplate(Model model) {
        model.addAttribute("prestamos", prestamoRepository.findAll());
        return "ModAdmin/prestamos-list";
    }

    @GetMapping("/new")
    public String prestamosNewTemplate(Model model) {
        model.addAttribute("prestamo", new Prestamo());
        return "ModAdmin/prestamos-form";
    }

    @GetMapping("/edit/{id}")
    public String prestamosEditTemplate(@PathVariable("id") String id, Model model) {
        model.addAttribute("prestamo", prestamoRepository.findById(id).orElseThrow(() -> new NotFoundException("Prestamo no encontrado")));
        return "ModAdmin/prestamos-form";
    }

    @PostMapping("/save")
    public String prestamoSaveProcess(@ModelAttribute("prestamo") Prestamo prestamo) {
        if (prestamo.getCodigo().isEmpty()) {
            prestamo.setCodigo(null);
        }
        prestamoRepository.save(prestamo);
        return "redirect:/prestamos/";
    }

    @GetMapping("/delete/{id}")
    public String prestamoDeleteProcess(@PathVariable("id") String id) {
        prestamoRepository.deleteById(id);
        return "redirect:/prestamos/";
    }

}
