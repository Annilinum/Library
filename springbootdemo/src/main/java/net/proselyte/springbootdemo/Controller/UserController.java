package net.proselyte.springbootdemo.Controller;

import net.proselyte.springbootdemo.Model.User;
import net.proselyte.springbootdemo.Service.BookService;
import net.proselyte.springbootdemo.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final BookService bookService;

    public UserController(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping("/users")
    public String findAll(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/user-create")
    public String createUserForm(User user) {
        return "user-create";
    }

    @PostMapping("/user-create")
    public String createUser(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "user-create";
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/users";
    }

    @GetMapping("/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "/user-update";
    }

    @PostMapping("/user-update")
    public String updateUser(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "/user-update";
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/user-books/{id}")
    public String getUserBooks(@PathVariable ("id") Long id, Model model){
        model.addAttribute("books", userService.getBookByUserId(id));
        model.addAttribute("userId", id);
        model.addAttribute("freeBooks", bookService.findFreeBooks());
        return "/html-user-books";
    }

}
