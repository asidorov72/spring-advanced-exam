package bg.softuni.booknest.web.admin.books;

import bg.softuni.booknest.model.dto.BookDto;
import bg.softuni.booknest.model.dto.BookEditRequest;
import bg.softuni.booknest.model.enums.BookStatus;
import bg.softuni.booknest.model.enums.Genre;
import bg.softuni.booknest.service.BookService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/admin/books")
public class BookManagementController {

    private final BookService bookService;

    public BookManagementController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ModelAndView books() {
        ModelAndView mv = new ModelAndView("admin/books");

        mv.addObject("activePage", "books");
        mv.addObject("books", bookService.getAllBooks());

        return mv;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editBook(@PathVariable UUID id) {
        BookDto book = bookService.getBookById(id);

        BookEditRequest request = new BookEditRequest()
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .setDescription(book.getDescription())
                .setGenre(book.getGenre())
                .setStatus(book.getStatus())
                .setBookImage(book.getBookImage())
                .setReleaseYear(book.getReleaseYear())
                .setRentalPrice(book.getRentalPrice())
                .setFeatured(book.isFeatured());

        ModelAndView mv = new ModelAndView("admin/book-edit");

        mv.addObject("activePage", "books");
        mv.addObject("bookEditRequest", request);
        mv.addObject("bookId", id);
        mv.addObject("genres", Genre.values());
        mv.addObject("statuses", BookStatus.values());

        return mv;
    }

    @PostMapping("/{id}/edit")
    public ModelAndView updateBook(
            @PathVariable UUID id,
            @Valid @ModelAttribute("bookEditRequest") BookEditRequest bookEditRequest,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("admin/book-edit");

            mv.addObject("activePage", "books");
            mv.addObject("bookId", id);
            mv.addObject("genres", Genre.values());
            mv.addObject("statuses", BookStatus.values());

            return mv;
        }

        bookService.updateBook(id, bookEditRequest);

        return new ModelAndView("redirect:/admin/books");
    }

    @GetMapping("/add")
    public ModelAndView addBook() {
        ModelAndView mv = new ModelAndView("admin/book-add");

        mv.addObject("activePage", "books");
        mv.addObject("bookEditRequest", new BookEditRequest());
        mv.addObject("genres", Genre.values());
        mv.addObject("statuses", BookStatus.values());

        return mv;
    }

    @PostMapping("/add")
    public ModelAndView createBook(
            @Valid @ModelAttribute("bookEditRequest") BookEditRequest bookEditRequest,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("admin/book-add");

            mv.addObject("activePage", "books");
            mv.addObject("genres", Genre.values());
            mv.addObject("statuses", BookStatus.values());

            return mv;
        }

        bookService.createBook(bookEditRequest);

        return new ModelAndView("redirect:/admin/books");
    }

    @GetMapping("/{id}/delete")
    public String deleteBook(@PathVariable UUID id) {

        bookService.deleteBook(id);

        return "redirect:/admin/books";
    }
}