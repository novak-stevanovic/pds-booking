package com.booking.reservationservice;

import com.booking.reservationservice.ReservationDetailsResponse;
import com.booking.reservationservice.Reservation;
import com.booking.reservationservice.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> getAll() {
        return reservationService.getAll();
    }

    @GetMapping("/{id}")
    public Reservation getById(@PathVariable Long id) {
        return reservationService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation create(@Valid @RequestBody Reservation reservation) {
        return reservationService.create(reservation);
    }

    @PutMapping("/{id}")
    public Reservation update(@PathVariable Long id, @Valid @RequestBody Reservation reservation) {
        return reservationService.update(id, reservation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        reservationService.delete(id);
    }

    @GetMapping("/{id}/details")
    public ReservationDetailsResponse getDetails(@PathVariable Long id) {
        return reservationService.getDetails(id);
    }
}