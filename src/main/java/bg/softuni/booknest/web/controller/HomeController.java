package bg.softuni.booknest.web.controller;

import bg.softuni.booknest.model.enums.Genre;
import bg.softuni.booknest.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final BookService bookService;

    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/404")
    public String notFound() {
        return "404";
    }

    @GetMapping("/")
    public ModelAndView viewHomePage() {
        ModelAndView mv = new ModelAndView("home");

        mv.addObject("activePage", "home");

        mv.addObject("recentlyAddedBooks", bookService.getRecentlyAddedBooks());
        mv.addObject("featuredBooks", bookService.getFeaturedBooks());
        mv.addObject("genres", Genre.values());
        mv.addObject("popularBooks", bookService.getPopularBooks());
        mv.addObject("readersChoiceBook", bookService.getReadersChoiceBook());
        mv.addObject("books", bookService.getAllBooks());

        return mv;
    }
}