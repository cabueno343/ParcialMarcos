package com.sistemabiblioteca.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sistemabiblioteca.app.entidades.Prestamo;
import com.sistemabiblioteca.app.repositorio.PrestamoRepository;

@Service
public class PrestamoService {

	@Autowired
    private PrestamoRepository prestamoRepository;

    public PrestamoService(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    public Prestamo buscarPrestamoPorCodigo(String codigo) {
        return prestamoRepository.findByCodigo(codigo);
    }

    public void aplazarPrestamo(String codigo, String nuevaFechaVencimiento) {
        Prestamo prestamo = prestamoRepository.findByCodigo(codigo);
        prestamo.setFechaVencimiento(nuevaFechaVencimiento);
        prestamo.setEstado("prestado");
        prestamoRepository.save(prestamo);
    }

    public void marcarPrestamoComoDevuelto(String codigo) {
        Prestamo prestamo = prestamoRepository.findByCodigo(codigo);
        if (prestamo != null && prestamo.getEstado().equals("devuelto")) {
            prestamoRepository.delete(prestamo); // Elimina el préstamo
            
            // También puedes agregar código para cambiar el estado del libro aquí.
        }
    }
}


