package bg.softuni.booknest.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity {

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Book book;

    @Column(nullable = false, name = "reservation_date")
    private LocalDate reservationDate;

    @Column(nullable = false, name = "return_date")
    private LocalDate returnDate;

    public User getUser() {
        return user;
    }

    public Reservation setUser(User user) {
        this.user = user;
        return this;
    }

    public Book getBook() {
        return book;
    }

    public Reservation setBook(Book book) {
        this.book = book;
        return this;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public Reservation setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
        return this;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public Reservation setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        return this;
    }
}