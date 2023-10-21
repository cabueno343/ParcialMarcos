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
import com.sistemabiblioteca.app.entidades.Reserva;
import com.sistemabiblioteca.app.exception.NotFoundException;
import com.sistemabiblioteca.app.repositorio.ReservaRepository;

@RestController
@RequestMapping(value = "/api/reservas")
public class ReservaRestController {
	@Autowired
    private ReservaRepository reservaRepository;
    
    @GetMapping("/")
    public List<Reserva> getAllReservas() {
        return reservaRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Reserva getReservaById(@PathVariable String id) {
        return reservaRepository.findById(id).orElseThrow(() -> new NotFoundException("Reserva no encontrada"));
    }
    
    @PostMapping("/")
    public Reserva saveReserva(@RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Reserva reserva = mapper.convertValue(body, Reserva.class);
        return reservaRepository.save(reserva);
    }

    @PutMapping("/{id}")
    public Reserva updateReserva(@PathVariable String id, @RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Reserva reserva = mapper.convertValue(body, Reserva.class);
        reserva.setCodigo(id);
        return reservaRepository.save(reserva);
    }

    @DeleteMapping("/{id}")
    public Reserva deleteReserva(@PathVariable String id) {
        Reserva reserva = reservaRepository.findById(id).orElseThrow(() -> new NotFoundException("Reserva no encontrada"));
        reservaRepository.deleteById(id);
        return reserva;
    }

}
