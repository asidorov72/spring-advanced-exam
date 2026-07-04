package bg.softuni.booknest.web.admin.dashboard;

import bg.softuni.booknest.service.BookService;
import bg.softuni.booknest.service.ReservationService;
import bg.softuni.booknest.service.TransactionService;
import bg.softuni.booknest.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final BookService bookService;
    private final ReservationService reservationService;
    private final TransactionService transactionService;

    public AdminController(UserService userService, BookService bookService, ReservationService reservationService, TransactionService transactionService) {
        this.userService = userService;
        this.bookService = bookService;
        this.reservationService = reservationService;
        this.transactionService = transactionService;
    }

    @GetMapping
    public ModelAndView dashboard() {
        ModelAndView mv = new ModelAndView("admin/dashboard");

        mv.addObject("activePage", "dashboard");

        mv.addObject("usersCount", userService.getUsersCount());
        mv.addObject("booksCount", bookService.getBooksCount());
        mv.addObject("reservationsCount", reservationService.getReservationsCount());
        mv.addObject("transactionsCount", transactionService.getTransactionsCount());

        return mv;
    }
}