package bg.softuni.booknest.model.dto;

import bg.softuni.booknest.model.enums.BookStatus;
import bg.softuni.booknest.model.enums.Genre;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class BookEditRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(min = 2, max = 100, message = "Author must be between 2 and 100 characters")
    private String author;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @NotNull(message = "Genre is required")
    private Genre genre;

    @NotNull(message = "Status is required")
    private BookStatus status;

    private String bookImage;

    @NotNull(message = "Release year is required")
    @Min(value = 1000, message = "Release year must be after 1000")
    @Max(value = 2100, message = "Release year cannot be after 2100")
    private Integer releaseYear;

    @NotNull(message = "Rental price is required")
    @DecimalMin(value = "0.01", message = "Rental price must be greater than zero")
    private BigDecimal rentalPrice;

    private boolean featured;

    public String getTitle() {
        return title;
    }

    public BookEditRequest setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public BookEditRequest setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BookEditRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public Genre getGenre() {
        return genre;
    }

    public BookEditRequest setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public BookStatus getStatus() {
        return status;
    }

    public BookEditRequest setStatus(BookStatus status) {
        this.status = status;
        return this;
    }

    public String getBookImage() {
        return bookImage;
    }

    public BookEditRequest setBookImage(String bookImage) {
        this.bookImage = bookImage;
        return this;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public BookEditRequest setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public BigDecimal getRentalPrice() {
        return rentalPrice;
    }

    public BookEditRequest setRentalPrice(BigDecimal rentalPrice) {
        this.rentalPrice = rentalPrice;
        return this;
    }

    public boolean isFeatured() {
        return featured;
    }

    public BookEditRequest setFeatured(boolean featured) {
        this.featured = featured;
        return this;
    }
}