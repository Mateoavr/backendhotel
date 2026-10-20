package com.hotel.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String habitacion;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String totalPagado;

    // Relación: Muchos reservas pueden pertenecer a un mismo huésped
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "huesped_id")
    private Huesped huesped;

    // Guardaremos los tours como un texto simple (JSON) por ahora para facilitar la inserción
    @Column(columnDefinition = "TEXT")
    private String toursDetalle;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getHabitacion() { return habitacion; }
    public void setHabitacion(String habitacion) { this.habitacion = habitacion; }
    public LocalDate getCheckIn() { return checkIn; }
    public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }
    public String getTotalPagado() { return totalPagado; }
    public void setTotalPagado(String totalPagado) { this.totalPagado = totalPagado; }
    public Huesped getHuesped() { return huesped; }
    public void setHuesped(Huesped huesped) { this.huesped = huesped; }
    public String getToursDetalle() { return toursDetalle; }
    public void setToursDetalle(String toursDetalle) { this.toursDetalle = toursDetalle; }
}