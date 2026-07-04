package bg.softuni.booknest.web.admin.reservations;

import bg.softuni.booknest.model.dto.ReservationEditRequest;
import bg.softuni.booknest.model.entity.Reservation;
import bg.softuni.booknest.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin/reservations")
public class ReservationManagementController {

    private final ReservationService reservationService;

    public ReservationManagementController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ModelAndView reservations() {

        List<Reservation> reservations =
                reservationService.getAllReservations();

        ModelAndView mv = new ModelAndView("admin/reservations");

        mv.addObject("reservations", reservations);
        mv.addObject("activePage", "reservations");

        return mv;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editReservation(@PathVariable UUID id) {
        Reservation reservation = reservationService.getReservationById(id);

        ReservationEditRequest reservationEditRequest = new ReservationEditRequest()
                .setReservationDate(reservation.getReservationDate())
                .setReturnDate(reservation.getReturnDate());

        return new ModelAndView("admin/reservation-edit")
                .addObject("reservation", reservation)
                .addObject("reservationEditRequest", reservationEditRequest)
                .addObject("activePage", "reservations");
    }

    @PostMapping("/{id}/edit")
    public ModelAndView updateReservation(@PathVariable UUID id,
                                          @Valid @ModelAttribute("reservationEditRequest") ReservationEditRequest reservationEditRequest,
                                          BindingResult bindingResult) {

        Reservation reservation = reservationService.getReservationById(id);

        if (bindingResult.hasErrors()) {
            return new ModelAndView("admin/reservation-edit")
                    .addObject("reservation", reservation)
                    .addObject("activePage", "reservations");
        }

        reservationService.updateReservation(id, reservationEditRequest);

        return new ModelAndView("redirect:/admin/reservations");
    }

    @GetMapping("/{id}/delete")
    public ModelAndView deleteReservation(@PathVariable UUID id) {
        reservationService.deleteReservation(id);

        return new ModelAndView("redirect:/admin/reservations");
    }
}