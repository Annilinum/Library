package net.proselyte.springbootdemo.Controller;

import net.proselyte.springbootdemo.Model.Book;
import net.proselyte.springbootdemo.Service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public String findAllBooks(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "/books";
    }
    @GetMapping("/test")
    public String findFreeBooks(Model model) {
        List<Book> freeBooks = bookService.findFreeBooks();
        model.addAttribute("freeBooks", freeBooks);
        return "/books";
    }

    @GetMapping("/books/{bookId}")
    public String deleteBook(@PathVariable("bookId") Long bookId) {
        bookService.deleteBook(bookId);
        return "redirect:/books";
    }

    @GetMapping("/book-delete/{userId}/{id}")
    public String deleteUserFromBook(@PathVariable("id") Long bookId, @PathVariable("userId") Long userId) {
        bookService.deleteUserFromBook(bookId);
        return "redirect:/user-books/" + userId;
    }

    @PostMapping("/create-book")
    public String createBook(String title, String author, Long userId) {
        bookService.saveBook(title, author, userId);
        return "redirect:/user-books/" + userId;
    }

    @PostMapping("/create-newBook")
    public String createBook(String title, String author) {
        bookService.saveBook(title, author);
        return "redirect:/books";
    }


}
