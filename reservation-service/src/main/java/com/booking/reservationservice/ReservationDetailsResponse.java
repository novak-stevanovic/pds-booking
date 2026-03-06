package com.booking.reservationservice;

import com.booking.reservationservice.Reservation;

public class ReservationDetailsResponse {

    private Reservation reservation;
    private UserResponse user;

    public ReservationDetailsResponse() {}

    public ReservationDetailsResponse(Reservation reservation, UserResponse user) {
        this.reservation = reservation;
        this.user = user;
    }

    public Reservation getReservation() { return reservation; }
    public void setReservation(Reservation reservation) { this.reservation = reservation; }

    public UserResponse getUser() { return user; }
    public void setUser(UserResponse user) { this.user = user; }
}