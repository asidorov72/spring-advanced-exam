package bg.softuni.booknest.web.controller;

import bg.softuni.booknest.model.dto.ReservationDto;
import bg.softuni.booknest.model.dto.UserDto;
import bg.softuni.booknest.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/my")
    public ModelAndView getMyReservations(HttpSession session) {

        UserDto user = (UserDto) session.getAttribute("user");

        List<ReservationDto> reservations =
                reservationService.getUserReservations(user.getId());

        return new ModelAndView("my-reservations")
                .addObject("reservations", reservations);
    }
}