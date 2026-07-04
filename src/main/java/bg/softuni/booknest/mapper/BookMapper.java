package bg.softuni.booknest.mapper;

import bg.softuni.booknest.model.dto.BookDto;
import bg.softuni.booknest.model.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDto toDto(Book book) {
        return new BookDto()
                .setId(book.getId())
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .setDescription(book.getDescription())
                .setGenre(book.getGenre())
                .setStatus(book.getStatus())
                .setBookImage(book.getBookImage())
                .setReleaseYear(book.getReleaseYear())
                .setRentalPrice(book.getRentalPrice())
                .setFeatured(book.isFeatured())
                .setCreatedOn(book.getCreatedOn())
                .setUpdatedOn(book.getUpdatedOn());
    }
}