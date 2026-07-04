package bg.softuni.booknest.web.controller;

import bg.softuni.booknest.model.dto.ContactRequest;
import bg.softuni.booknest.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PageController {

    private final EmailService emailService;

    public PageController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/about")
    public ModelAndView about() {
        ModelAndView mv = new ModelAndView("about");
        mv.addObject("activePage", "pages");
        return mv;
    }

    @GetMapping("/contact")
    public ModelAndView contact() {
        ModelAndView mv = new ModelAndView("contact");
        mv.addObject("activePage", "pages");
        mv.addObject("contactRequest", new ContactRequest());
        return mv;
    }

    @PostMapping("/contact")
    public ModelAndView sendContactMessage(
            @Valid ContactRequest contactRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("contact");
            mv.addObject("activePage", "pages");
            mv.addObject("contactRequest", contactRequest);
            return mv;
        }

        emailService.sendContactMessage(contactRequest);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Your message has been sent successfully.");

        return new ModelAndView("redirect:/contact");
    }
}