package net.proselyte.springbootdemo.Controller;

import net.proselyte.springbootdemo.Model.User;
import net.proselyte.springbootdemo.Service.BookService;
import net.proselyte.springbootdemo.Service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class UserController {
    private final UserService userService;
    private final BookService bookService;
    private final PageableHelper pageableHelper;

    public UserController(UserService userService, BookService bookService, PageableHelper pageableHelper) {
        this.userService = userService;
        this.bookService = bookService;
        this.pageableHelper = pageableHelper;
    }

    @GetMapping("/users")
    public String findAll(Model model,
                          @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                          @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
                          @RequestParam(value = "sortType", required = false, defaultValue = "ASC") String sortType) {
        Page<User> users = userService.findAll(pageNumber, sortField, Sort.Direction.fromString(sortType));
        model.addAttribute("users", users.toList());

        pageableHelper.fillPageable(model, pageNumber, users.getTotalPages(), sortField, sortType);
        return "user-list";
    }

    @GetMapping("/user-create")
    public String createUserForm(User user) {
        return "user-create";
    }

    @PostMapping("/user-create")
    public String createUser(@Valid User user, Errors errors) {
        if (errors.hasErrors())
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
    public String updateUser(@Valid User user, Errors errors) {
        if (errors.hasErrors())
            return "/user-update";
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/user-books/{id}")
    public String getUserBooks(@PathVariable("id") Long id, Model model) {
        model.addAttribute("books", userService.getBookByUserId(id));
        model.addAttribute("userId", id);
        model.addAttribute("freeBooks", bookService.findFreeBooks());
        return "/html-user-books";
    }

}
