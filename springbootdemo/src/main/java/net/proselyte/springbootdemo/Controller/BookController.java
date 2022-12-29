package net.proselyte.springbootdemo.Controller;

import net.proselyte.springbootdemo.Model.Book;
import net.proselyte.springbootdemo.Service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class BookController {
    private final BookService bookService;
    private final PageableHelper pageableHelper;

    public BookController(BookService bookService, PageableHelper pageableHelper) {
        this.bookService = bookService;
        this.pageableHelper = pageableHelper;
    }

    @GetMapping("/books")
    public String findAllBooks(Model model, @ModelAttribute("newBook") CreateBookRequest newBook,
                               @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                               @RequestParam(value = "sortField", required = false, defaultValue = "title") String sortField,
                               @RequestParam(value = "sortType", required = false, defaultValue = "ASC") String sortType) {

        if (model.getAttribute("newBookFormErrors") != null) {
            model.addAttribute("org.springframework.validation.BindingResult.newBook",
                    model.getAttribute("newBookFormErrors"));
        }

        Page<Book> page = bookService.findAll(pageNumber, sortField, Sort.Direction.fromString(sortType));
        model.addAttribute("books", page.toList());
        pageableHelper.fillPageable(model, pageNumber, page.getTotalPages(), sortField, sortType);
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

    @GetMapping("/book-update/{id}")
    public String updateBookForm(@PathVariable("id") Long id, Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        return "/book-update";
    }

    @PostMapping("/book-update")
    public String updateBook(@Valid Book book, Errors errors) {
        if (errors.hasErrors())
            return "/book-update";
        bookService.saveBook(book);
        return "redirect:/books";
    }
}
