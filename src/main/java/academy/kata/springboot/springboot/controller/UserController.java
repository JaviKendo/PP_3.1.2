package academy.kata.springboot.springboot.controller;

import academy.kata.springboot.springboot.model.User;
import academy.kata.springboot.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String showUsers(@RequestParam(value = "count", required = false, defaultValue = "5") int count,
                            Model model) {
        model.addAttribute("users", userService.getUsersCount(count));
        return "users";
    }

    @GetMapping(value = "/{id}")
    public String showUserById(@PathVariable("id") long id, Model model) {
        model.addAttribute("showUser", userService.getUserById(id));
        return "show-user-info";
    }

    @GetMapping(value = "/addNewUser")
    public String addNewUser(@ModelAttribute("user") User user) {
        return "add-user";
    }

    @PostMapping()
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/add-user";
        }

        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping(value = "/{id}/editUser")
    public String editUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit-user";
    }

    @PatchMapping(value = "/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/edit-user";
        }

        userService.updateUser(user);
        return "redirect:/users";
    }

    @DeleteMapping(value = "/{id}")
    public String removeUser(@PathVariable("id") long id) {
        userService.removeUserById(id);
        return "redirect:/users";
    }

}