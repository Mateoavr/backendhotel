package com.hotel.backend.controller;

import com.hotel.backend.model.Huesped;
import com.hotel.backend.model.Reserva;
import com.hotel.backend.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    @GetMapping
    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }
    // POST /api/reservas -> Crea una reserva
    @PostMapping
    public ResponseEntity<?> crearReserva(@RequestBody Reserva reserva) {
        // 1. Asignar Estado por defecto
        if (reserva.getEstado() == null || reserva.getEstado().isEmpty()) {
            reserva.setEstado("Confirmada");
        }

        // 2. Prevenir el error NOT NULL de la base de datos (total_pagado)
        if (reserva.getTotalPagado() == null || reserva.getTotalPagado().isEmpty()) {
            reserva.setTotalPagado("Pendiente");
        }

        // 3. Prevenir los errores NOT NULL del huésped (RUT y Correo)
        if (reserva.getHuesped() != null) {
            if (reserva.getHuesped().getRut() == null) {
                reserva.getHuesped().setRut("Pendiente");
            }
            if (reserva.getHuesped().getCorreo() == null) {
                reserva.getHuesped().setCorreo("Pendiente");
            }
        }

        // Guardamos
        Reserva nuevaReserva = reservaRepository.save(reserva);
        return ResponseEntity.ok(Map.of(
                "mensaje", "¡Reserva registrada con éxito!",
                "id", nuevaReserva.getId()
        ));
    }

    // PUT /api/reservas/{id} -> Actualiza una reserva desde la Intranet
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarReserva(@PathVariable Long id, @RequestBody Reserva datosActualizados) {
        return reservaRepository.findById(id).map(reserva -> {

            if (datosActualizados.getTipoHabitacion() != null) reserva.setTipoHabitacion(datosActualizados.getTipoHabitacion());
            if (datosActualizados.getNumeroHabitacion() != null) reserva.setNumeroHabitacion(datosActualizados.getNumeroHabitacion());
            if (datosActualizados.getCheckIn() != null) reserva.setCheckIn(datosActualizados.getCheckIn());
            if (datosActualizados.getCheckOut() != null) reserva.setCheckOut(datosActualizados.getCheckOut());
            if (datosActualizados.getEstado() != null) reserva.setEstado(datosActualizados.getEstado());

            if (datosActualizados.getHuesped() != null && datosActualizados.getHuesped().getNombre() != null) {
                if (reserva.getHuesped() != null) {
                    reserva.getHuesped().setNombre(datosActualizados.getHuesped().getNombre());
                } else {
                    // Si el huésped no existía, creamos uno con valores por defecto
                    Huesped nuevoHuesped = datosActualizados.getHuesped();
                    nuevoHuesped.setRut("Pendiente");
                    nuevoHuesped.setCorreo("Pendiente");
                    reserva.setHuesped(nuevoHuesped);
                }
            }
            reservaRepository.save(reserva);
            return ResponseEntity.ok(Map.of("mensaje", "Reserva actualizada con éxito"));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/reservas/{id} -> Elimina una reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReserva(@PathVariable Long id) {
        if (reservaRepository.existsById(id)) {
            reservaRepository.deleteById(id);
            return ResponseEntity.ok(Map.of("mensaje", "Reserva eliminada con éxito"));
        }
        return ResponseEntity.notFound().build();
    }
}
