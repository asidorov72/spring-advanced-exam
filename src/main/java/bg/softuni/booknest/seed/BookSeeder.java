package bg.softuni.booknest.seed;

import bg.softuni.booknest.model.entity.Book;
import bg.softuni.booknest.model.enums.BookStatus;
import bg.softuni.booknest.model.enums.Genre;
import bg.softuni.booknest.repository.BookRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Order(2)
public class BookSeeder implements Seeder {

    private final BookRepository bookRepository;

    public BookSeeder(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void seed() {
        if (bookRepository.count() > 0) {
            return;
        }

        List<Book> books = List.of(
                new Book()
                        .setTitle("The Hobbit")
                        .setAuthor("J.R.R. Tolkien")
                        .setDescription("Bilbo Baggins embarks on an unexpected journey through Middle-earth.")
                        .setGenre(Genre.FANTASY)
                        .setReleaseYear(1937)
                        .setRentalPrice(BigDecimal.valueOf(4.99))
                        .setStatus(BookStatus.ACTIVE)
                        .setBookImage("https://www.smsa.org.au/wp-content/uploads/2022/07/Dune-by-Frank-Herbert-582x895.png"),

                new Book()
                        .setTitle("Dune")
                        .setAuthor("Frank Herbert")
                        .setDescription("Epic science fiction saga on the desert planet Arrakis.")
                        .setGenre(Genre.SCIENCE_FICTION)
                        .setReleaseYear(1965)
                        .setRentalPrice(BigDecimal.valueOf(5.99))
                        .setStatus(BookStatus.ACTIVE)
                        .setBookImage("https://pictures.abebooks.com/isbn/9780425027066-us.jpg"),

                new Book()
                        .setTitle("Romeo and Juliet")
                        .setAuthor("William Shakespeare")
                        .setDescription("Classic romantic tragedy about two young lovers.")
                        .setGenre(Genre.ROMANCE)
                        .setReleaseYear(1597)
                        .setRentalPrice(BigDecimal.valueOf(3.99))
                        .setStatus(BookStatus.ACTIVE)
                        .setBookImage("https://pictures.abebooks.com/isbn/9780743477116-us.jpg"),

                new Book()
                        .setTitle("Hamlet")
                        .setAuthor("William Shakespeare")
                        .setDescription("One of the greatest dramas ever written.")
                        .setGenre(Genre.DRAMA)
                        .setReleaseYear(1603)
                        .setRentalPrice(BigDecimal.valueOf(3.99))
                        .setStatus(BookStatus.ACTIVE)
                        .setBookImage("https://pictures.abebooks.com/isbn/9780743477123-us.jpg"),

                new Book()
                        .setTitle("The Da Vinci Code")
                        .setAuthor("Dan Brown")
                        .setDescription("Mystery thriller novel about secrets hidden in art and history.")
                        .setGenre(Genre.THRILLER)
                        .setReleaseYear(2003)
                        .setRentalPrice(BigDecimal.valueOf(5.49))
                        .setStatus(BookStatus.ACTIVE)
                        .setBookImage("https://pictures.abebooks.com/isbn/9780307474278-us.jpg"),

                new Book()
                        .setTitle("Sapiens")
                        .setAuthor("Yuval Noah Harari")
                        .setDescription("A brief history of humankind.")
                        .setGenre(Genre.HISTORY)
                        .setReleaseYear(2011)
                        .setRentalPrice(BigDecimal.valueOf(6.49))
                        .setStatus(BookStatus.ACTIVE)
                        .setBookImage("https://pictures.abebooks.com/isbn/9780062316097-us.jpg")
        );

        bookRepository.saveAll(books);
    }
}