package bg.softuni.booknest.model.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ReservationEditRequest {

    @NotNull(message = "Reservation date is required")
    private LocalDate reservationDate;

    @NotNull(message = "Return date is required")
    @FutureOrPresent(message = "Return date cannot be in the past")
    private LocalDate returnDate;

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public ReservationEditRequest setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
        return this;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public ReservationEditRequest setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        return this;
    }
}