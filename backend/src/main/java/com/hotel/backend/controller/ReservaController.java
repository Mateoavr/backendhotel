package com.hotel.backend.controller;

import com.hotel.backend.model.Reserva;
import com.hotel.backend.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*") // Permite que tu web HTML se conecte sin bloqueos de seguridad CORS
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    @PostMapping
    public ResponseEntity<?> crearReserva(@RequestBody Reserva nuevaReserva) {
        try {
            // Guarda la reserva y el huésped automáticamente en PostgreSQL
            Reserva reservaGuardada = reservaRepository.save(nuevaReserva);

            return ResponseEntity.ok("{\"mensaje\": \"¡Reserva exitosa! Código: " + reservaGuardada.getId() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"Hubo un problema al procesar la reserva\"}");
        }
    }
}