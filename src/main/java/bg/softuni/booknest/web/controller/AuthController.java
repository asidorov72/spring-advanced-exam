package bg.softuni.booknest.web.controller;

import bg.softuni.booknest.model.dto.UserDto;
import bg.softuni.booknest.model.dto.UserLoginRequest;
import bg.softuni.booknest.model.dto.UserRegisterRequest;
import bg.softuni.booknest.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView getRegisterPage() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("userRegisterRequest", new UserRegisterRequest());

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(
            @Valid UserRegisterRequest userRegisterRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("register");
            modelAndView.addObject("userRegisterRequest", userRegisterRequest);
            return modelAndView;
        }

        boolean registered = userService.register(userRegisterRequest);

        if (!registered) {
            ModelAndView modelAndView = new ModelAndView("register");
            modelAndView.addObject("userRegisterRequest", userRegisterRequest);
            modelAndView.addObject("registerError", true);

            return modelAndView;
        }

        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("userLoginRequest", new UserLoginRequest());

        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(
            @Valid UserLoginRequest userLoginRequest,
            BindingResult bindingResult,
            HttpSession session
    ) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("userLoginRequest", userLoginRequest);
            return modelAndView;
        }

        Optional<UserDto> userDto = userService.login(userLoginRequest);

        if (userDto.isEmpty()) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("userLoginRequest", userLoginRequest);
            modelAndView.addObject("loginError", true);

            return modelAndView;
        }

        session.setAttribute("user_id", userDto.get().getId());
        session.setAttribute("user", userDto.get());

        return new ModelAndView("redirect:/");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();

        return new ModelAndView("redirect:/login");
    }
}