package bg.softuni.booknest.web.controller;

import bg.softuni.booknest.model.dto.BookDto;
import bg.softuni.booknest.model.dto.UserDto;
import bg.softuni.booknest.service.BookService;
import bg.softuni.booknest.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final ReservationService reservationService;

    public BookController(BookService bookService, ReservationService reservationService) {
        this.bookService = bookService;
        this.reservationService = reservationService;
    }

    @GetMapping
    public ModelAndView viewBooksPage(@RequestParam(required = false) String query) {
        ModelAndView mv = new ModelAndView("books");

        mv.addObject("activePage", "books");
        mv.addObject("books", bookService.searchActiveBooks(query));
        mv.addObject("query", query);

        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView viewBookDetails(@PathVariable UUID id, HttpSession session) {
        BookDto book = bookService.getBookById(id);

        ModelAndView mv = new ModelAndView("book-details");

        UserDto user = (UserDto) session.getAttribute("user");

        boolean reservedByCurrentUser = user != null
                && reservationService.isReservedByCurrentUser(id, user.getId());

        mv.addObject("book", book);
        mv.addObject("activePage", "books");
        mv.addObject("reservedByCurrentUser", reservedByCurrentUser);

        return mv;
    }

    @PostMapping("/{id}/reserve")
    public String reserveBook(@PathVariable UUID id,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {

        UserDto user = (UserDto) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        try {
            reservationService.reserveBook(id, user.getId());
            redirectAttributes.addFlashAttribute("successMessage", "Book reserved successfully!");
        } catch (IllegalStateException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/books/" + id;
    }
}