package com.sistemabiblioteca.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sistemabiblioteca.app.entidades.Reserva;
import com.sistemabiblioteca.app.repositorio.ReservaRepository;

@Service
public class ReservaServiceImpl implements ReservaService {
	private final ReservaRepository reservaRepository;

    public ReservaServiceImpl(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }
   

    @Override
    public List<Reserva> obtenerTodasReservas() {
        
    	return new ArrayList<Reserva>();
    }
    
    public List<Reserva> obtenerReservasPorEstado(String estado) {
        return reservaRepository.findByEstado(estado);
    }


    @Override
    public Reserva obtenerReservaPorCodigo(String codigo) {
        // Implementa la lógica para obtener una reserva por su código desde el repositorio.
        // En este ejemplo, se devuelve una reserva ficticia para evitar errores de compilación.
        Reserva reservaFicticia = new Reserva();
        return reservaFicticia;
    }
    

    @Override
    public void guardarReserva(Reserva reserva) {
        // Implementa la lógica para guardar una reserva en el repositorio.
    }

    @Override
    public void eliminarReservaPorCodigo(String codigo) {
        // Implementa la lógica para eliminar una reserva por su código desde el repositorio.
    }
}