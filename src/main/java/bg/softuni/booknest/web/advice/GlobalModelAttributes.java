package bg.softuni.booknest.web.advice;

import bg.softuni.booknest.model.dto.UserDto;
import bg.softuni.booknest.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/*
@ControllerAdvice — это способ добавить что-то во все контроллеры сразу, чтобы не копировать код.

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute("reservationCount")
    public long reservationCount(...) {
        ...
    }
}

Spring автоматически вызывает этот метод перед рендерингом любой страницы. И во всех .html появляется:
${reservationCount}
 */

@ControllerAdvice
public class GlobalModelAttributes {

    private final ReservationService reservationService;

    public GlobalModelAttributes(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @ModelAttribute("reservationCount")
    public long reservationCount(HttpSession session) {

        UserDto user = (UserDto) session.getAttribute("user");

        if (user == null) {
            return 0;
        }

        return reservationService.getReservationCount(user.getId());
    }
}