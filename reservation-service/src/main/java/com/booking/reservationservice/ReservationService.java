package com.booking.reservationservice;

import com.booking.reservationservice.UserClient;
import com.booking.reservationservice.ReservationDetailsResponse;
import com.booking.reservationservice.UserResponse;
import com.booking.reservationservice.Reservation;
import com.booking.reservationservice.ReservationRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserClient userClient;

    public ReservationService(ReservationRepository reservationRepository, UserClient userClient) {
        this.reservationRepository = reservationRepository;
        this.userClient = userClient;
    }

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public Reservation getById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found"));
    }

    public Reservation create(Reservation reservation) {
        reservation.setId(null);
        validateUserExists(reservation.getUserId());
        return reservationRepository.save(reservation);
    }

    public Reservation update(Long id, Reservation reservation) {
        Reservation existing = getById(id);
        validateUserExists(reservation.getUserId());

        existing.setUserId(reservation.getUserId());
        existing.setReservationDate(reservation.getReservationDate());

        return reservationRepository.save(existing);
    }

    public void delete(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found");
        }
        reservationRepository.deleteById(id);
    }

    public ReservationDetailsResponse getDetails(Long id) {
        Reservation reservation = getById(id);
        UserResponse user = validateUserExists(reservation.getUserId());
        return new ReservationDetailsResponse(reservation, user);
    }

    @CircuitBreaker(name = "userService", fallbackMethod = "userFallback")
    @Retry(name = "userService")
    public UserResponse validateUserExists(Long userId) {
        return userClient.getUserById(userId);
    }

    public UserResponse userFallback(Long userId, Throwable t) {
        if (t instanceof feign.FeignException.NotFound) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist");
        }

        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "User service unavailable");
    }
}