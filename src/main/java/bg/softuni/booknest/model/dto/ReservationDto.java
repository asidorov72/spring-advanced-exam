package bg.softuni.booknest.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ReservationDto {

    private UUID id;

    private UUID bookId;

    private String bookTitle;

    private String bookAuthor;

    private String bookImage;

    private LocalDate reservationDate;

    private LocalDate returnDate;

    private BigDecimal rentalPrice;

    public UUID getId() {
        return id;
    }

    public ReservationDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getBookId() {
        return bookId;
    }

    public ReservationDto setBookId(UUID bookId) {
        this.bookId = bookId;
        return this;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public ReservationDto setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
        return this;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public ReservationDto setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
        return this;
    }

    public String getBookImage() {
        return bookImage;
    }

    public ReservationDto setBookImage(String bookImage) {
        this.bookImage = bookImage;
        return this;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public ReservationDto setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
        return this;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public ReservationDto setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    public BigDecimal getRentalPrice() {
        return rentalPrice;
    }

    public ReservationDto setRentalPrice(BigDecimal rentalPrice) {
        this.rentalPrice = rentalPrice;
        return this;
    }
}