package bg.softuni.booknest.model.dto;

import bg.softuni.booknest.model.enums.BookStatus;
import bg.softuni.booknest.model.enums.Genre;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class BookDto {

    private UUID id;

    private String title;

    private String author;

    private String description;

    private Genre genre;

    private BookStatus status;

    private String bookImage;

    private int releaseYear;

    private BigDecimal rentalPrice;

    private boolean featured;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    public UUID getId() {
        return id;
    }

    public BookDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public BookDto setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BookDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public Genre getGenre() {
        return genre;
    }

    public BookDto setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public BookStatus getStatus() {
        return status;
    }

    public BookDto setStatus(BookStatus status) {
        this.status = status;
        return this;
    }

    public String getBookImage() {
        return bookImage;
    }

    public BookDto setBookImage(String bookImage) {
        this.bookImage = bookImage;
        return this;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public BookDto setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public BigDecimal getRentalPrice() {
        return rentalPrice;
    }

    public BookDto setRentalPrice(BigDecimal rentalPrice) {
        this.rentalPrice = rentalPrice;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public BookDto setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public BookDto setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public boolean isFeatured() {
        return featured;
    }

    public BookDto setFeatured(boolean featured) {
        this.featured = featured;
        return this;
    }
}