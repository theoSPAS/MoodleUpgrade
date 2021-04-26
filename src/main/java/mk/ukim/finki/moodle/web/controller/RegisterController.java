package mk.ukim.finki.moodle.web.controller;

import mk.ukim.finki.moodle.model.exceptions.*;
import mk.ukim.finki.moodle.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("bodyContent", "register");
        return "master-template";
    }

    @PostMapping
    public String register(@RequestParam String username, @RequestParam String password,
                           @RequestParam String repeatPassword) {
        try {
            this.userService.register(username, password, repeatPassword);
            return "redirect:/login";
        } catch (PasswordsDoNotMatchException | InvalidArgumentsException | EmailExistsException exception) {
            return "redirect:/register?error=" + exception.getMessage();
        }
    }
}
