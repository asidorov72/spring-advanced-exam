package bg.softuni.booknest.mapper;

import bg.softuni.booknest.model.dto.ReservationDto;
import bg.softuni.booknest.model.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public ReservationDto toDto(Reservation reservation) {
        return new ReservationDto()
                .setId(reservation.getId())
                .setBookId(reservation.getBook().getId())
                .setBookTitle(reservation.getBook().getTitle())
                .setBookAuthor(reservation.getBook().getAuthor())
                .setBookImage(reservation.getBook().getBookImage())
                .setReservationDate(reservation.getReservationDate())
                .setReturnDate(reservation.getReturnDate())
                .setRentalPrice(reservation.getBook().getRentalPrice());
    }
}