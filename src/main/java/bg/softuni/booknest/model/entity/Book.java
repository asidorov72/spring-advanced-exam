package bg.softuni.booknest.model.entity;

import bg.softuni.booknest.model.dto.BookEditRequest;
import bg.softuni.booknest.model.enums.BookStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import bg.softuni.booknest.model.enums.Genre;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

@Entity
@Table(name = "books")
public class Book extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookStatus status;

    @Column(name = "book_image")
    private String bookImage;

    @Column(nullable = false, name = "release_year")
    private int releaseYear;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal rentalPrice;

    @Column(nullable = false)
    private boolean featured;

    public BigDecimal getRentalPrice() {
        return rentalPrice;
    }

    public Book setRentalPrice(BigDecimal rentalPrice) {
        this.rentalPrice = rentalPrice;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Book setDescription(String description) {
        this.description = description;
        return this;
    }

    public Genre getGenre() {
        return genre;
    }

    public Book setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public BookStatus getStatus() {
        return status;
    }

    public Book setStatus(BookStatus status) {
        this.status = status;
        return this;
    }

    public String getBookImage() {
        return bookImage;
    }

    public Book setBookImage(String bookImage) {
        this.bookImage = bookImage;
        return this;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public Book setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public boolean isFeatured() {
        return featured;
    }

    public Book setFeatured(boolean featured) {
        this.featured = featured;
        return this;
    }
}