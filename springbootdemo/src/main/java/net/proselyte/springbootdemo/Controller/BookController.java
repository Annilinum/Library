package net.proselyte.springbootdemo.Controller;

import net.proselyte.springbootdemo.Model.Book;
import net.proselyte.springbootdemo.Model.User;
import net.proselyte.springbootdemo.Service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public String findAll(Model model){
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "books-list";
    }

    @GetMapping("/book-create")
    public String createUserForm(User user) {
        return "book-create";
    }

    @PostMapping("/book-create")
    public String createUser(Book book) {
        bookService.saveBook(book);
        return "redirect:/users";
    }
    @GetMapping("/book-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }
}
