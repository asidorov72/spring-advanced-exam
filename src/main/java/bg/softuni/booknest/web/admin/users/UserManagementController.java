package bg.softuni.booknest.web.admin.users;

import bg.softuni.booknest.model.dto.UserEditRequest;
import bg.softuni.booknest.model.enums.UserRole;
import bg.softuni.booknest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import bg.softuni.booknest.model.dto.UserDto;

import java.util.UUID;

@Controller
@RequestMapping("/admin/users")
public class UserManagementController {

    private final UserService userService;

    public UserManagementController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView users() {
        ModelAndView modelAndView = new ModelAndView("admin/users");

        modelAndView.addObject("activePage", "users");
        modelAndView.addObject("users", userService.getAllUsers());

        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editUser(@PathVariable UUID id) {

        UserDto user = userService.getUserById(id);

        UserEditRequest request = new UserEditRequest()
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setEmail(user.getEmail())
                .setRole(user.getRole());

        ModelAndView modelAndView = new ModelAndView("admin/user-edit");

        modelAndView.addObject("roles", UserRole.values());
        modelAndView.addObject("activePage", "users");
        modelAndView.addObject("userEditRequest", request);
        modelAndView.addObject("userId", id);

        return modelAndView;
    }

    @PostMapping("/{id}/edit")
    public ModelAndView updateUser(
            @PathVariable UUID id,
            @Valid @ModelAttribute("userEditRequest") UserEditRequest userEditRequest,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("admin/user-edit");

            mv.addObject("roles", UserRole.values());
            mv.addObject("activePage", "users");
            mv.addObject("userId", id);

            return mv;
        }

        userService.updateUser(id, userEditRequest);

        return new ModelAndView("redirect:/admin/users");
    }

}