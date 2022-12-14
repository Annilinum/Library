package net.proselyte.springbootdemo.Controller;

import net.proselyte.springbootdemo.Model.Book;
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
    public String findBooks(Model model){
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "/books";
    }

    @GetMapping("/book-delete/{userId}/{id}")
    public String deleteBook(@PathVariable("id") Long bookId, @PathVariable("userId") Long userId) {
        bookService.deleteById(bookId);
        return "redirect:/user-books/" + userId;
    }

    @PostMapping("/create-book")
    public String createBook(String title, String author, Long userId) {
        bookService.saveBook(title, author, userId);
        return "redirect:/user-books/" + userId;
    }

}
