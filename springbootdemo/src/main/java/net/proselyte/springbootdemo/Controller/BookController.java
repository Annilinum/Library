package net.proselyte.springbootdemo.Controller;

import net.proselyte.springbootdemo.Model.Book;
import net.proselyte.springbootdemo.Service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public String findAllBooks(Model model, @ModelAttribute("newBook") CreateBookRequest newBook, @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber) {
        if (model.getAttribute("newBookFormErrors") != null) {
            model.addAttribute("org.springframework.validation.BindingResult.newBook", model.getAttribute("newBookFormErrors"));
        }

        List<Book> books = bookService.findAll(pageNumber);
        model.addAttribute("books", books);

        int prevPage = Math.max(pageNumber - 1, 0);
        model.addAttribute("prevPage", prevPage);
        int nextPage = pageNumber + 1;
        model.addAttribute("nextPage", nextPage);

        List<Integer> showedNumbers = List.of(pageNumber, nextPage, nextPage + 1);
        model.addAttribute("showedNumbers", showedNumbers);
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
    public String createBook(Long bookId, Long userId) {
        bookService.saveBook(bookId, userId);
        return "redirect:/user-books/" + userId;
    }

    @PostMapping("/create-newBook")
    public String createBook(@Valid CreateBookRequest newBook, Errors errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("newBookFormErrors", errors);
            redirectAttributes.addFlashAttribute("newBook", newBook);
            return "redirect:/books";
        }
        bookService.saveBook(newBook.getTitle(), newBook.getAuthor());
        return "redirect:/books";
    }


}
