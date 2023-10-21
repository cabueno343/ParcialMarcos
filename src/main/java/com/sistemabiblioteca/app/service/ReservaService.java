package com.sistemabiblioteca.app.service;

import java.util.List;

import com.sistemabiblioteca.app.entidades.Reserva;

public interface ReservaService {
    List<Reserva> obtenerTodasReservas();
    
    List<Reserva> obtenerReservasPorEstado(String estado);

    Reserva obtenerReservaPorCodigo(String codigo);

    void guardarReserva(Reserva reserva);

    void eliminarReservaPorCodigo(String codigo);
    
}






