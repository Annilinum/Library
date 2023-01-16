package net.proselyte.springbootdemo.Controller;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import net.proselyte.springbootdemo.Model.User;
import net.proselyte.springbootdemo.Service.BookService;
import net.proselyte.springbootdemo.Service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class UserController {
  private final UserService userService;
  private final BookService bookService;
  private final PageableHelper<User> pageableHelper;

  @GetMapping("/")
  public String getUsers(Model model,
      @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
      @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
      @RequestParam(value = "sortType", required = false) String sortType) {

    Direction sortDirection = sortType == null ? Direction.ASC : Direction.fromString(sortType);
    PageRequest pageRequest = PageRequest.of(pageNumber, 10, Sort.by(sortDirection, sortField));
    Page<User> page = userService.getUsersPage(pageRequest);
    pageableHelper.fillPageable(model, page, sortField, sortDirection);
    return "user-list.html";
  }

  @GetMapping("/user/create")
  public String createUserForm(User user) {
    return "user-create.html";
  }

  @PostMapping("/user/create")
  public String createUser(@Valid User user, Errors errors) {
    if (errors.hasErrors()) return "user-create.html";
    userService.saveUser(user);
    return "redirect:/";
  }

  @GetMapping("/user/{userId}/delete")
  public String deleteUser(@PathVariable("userId") long userId) {
    userService.deleteById(userId);
    return "redirect:/";
  }

  @GetMapping("/user/{userId}/update")
  public String updateUserForm(@PathVariable("userId") long userId, Model model) {
    User user = userService.findById(userId);
    model.addAttribute("user", user);
    return "user-update.html";
  }

  @PostMapping("/user/update")
  public String updateUser(@Valid User user, Errors errors) {
    if (errors.hasErrors()) return "user-update.html";
    userService.saveUser(user);
    return "redirect:/";
  }

  @GetMapping("/user/{userId}/books")
  public String getUserBooks(@PathVariable("userId") long userId, Model model) {
    model.addAttribute("books", userService.getBookByUserId(userId));
    model.addAttribute("userId", userId);
    model.addAttribute("freeBooks", bookService.findFreeBooks(userId));
    return "user-books.html";
  }

  @GetMapping("/user/{userId}/return-book/{bookId}")
  public String returnBook(@PathVariable("bookId") long bookId, @PathVariable("userId") long userId) {
    bookService.returnBook(bookId, userId);
    return "redirect:/user/" + userId + "/books";
  }
}
