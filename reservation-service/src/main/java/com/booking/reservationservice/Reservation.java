package com.booking.reservationservice;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    @FutureOrPresent
    private LocalDate reservationDate;

    @NotBlank
    @Column(nullable = false)
    private String destination;

    public Reservation() {}

    public Reservation(Long id, Long userId, LocalDate reservationDate, String destination) {
        this.id = id;
        this.userId = userId;
        this.reservationDate = reservationDate;
        this.destination = destination;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public LocalDate getReservationDate() { return reservationDate; }
    public void setReservationDate(LocalDate reservationDate) { this.reservationDate = reservationDate; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
}