package net.proselyte.springbootdemo.Controller;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import net.proselyte.springbootdemo.Model.Book;
import net.proselyte.springbootdemo.Service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class BookController {
  private final BookService bookService;
  private final PageableHelper pageableHelper;

  @GetMapping("/books")
  public String getBooks(Model model,
      @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
      @RequestParam(value = "sortField", required = false, defaultValue = "title") String sortField,
      @RequestParam(value = "sortType", required = false) String sortType) {

    sortType = sortType == null ? String.valueOf(Sort.Direction.ASC) : sortType;
    Page<Book> page = bookService.getBokksPage(pageNumber, sortField, Sort.Direction.fromString(sortType));
    model.addAttribute("books", page.toList());
    pageableHelper.fillPageable(model, pageNumber, page.getTotalPages(), sortField, sortType);
    return "books";
  }

  @DeleteMapping("/book/{bookId}")
  public String deleteBook(@PathVariable("bookId") long bookId) {
    bookService.deleteBook(bookId);
    return "redirect:/books";
  }

  @GetMapping("/book-delete/{userId}/{id}")
  public String deleteUserFromBook(@PathVariable("id") long bookId, @PathVariable("userId") Long userId) {
    bookService.deleteUserFromBook(bookId, userId);
    return "redirect:/user-books/" + userId;
  }

  @PostMapping("/issue-book")
  public String issueBook(long bookId, long userId) {
    bookService.issueBook(bookId, userId);
    return "redirect:/user-books/" + userId;
  }

  @GetMapping("/create-new-book")
  public String createBookForm(@ModelAttribute(name = "newBook") CreateBookRequest newBook) {
    return "book-create";
  }

  @PostMapping("/create-new-book")
  public String createBook(@Valid @ModelAttribute(name = "newBook") CreateBookRequest newBook, Errors errors) {
    if (errors.hasErrors()) {
      return "book-create";
    }
    bookService.createNewBook(newBook.getTitle(), newBook.getAuthor(), newBook.getCountBook());
    return "redirect:/books";
  }

  @GetMapping("/book-update/{id}")
  public String updateBookForm(@PathVariable("id") long id, Model model) {
    Book book = bookService.findById(id);
    model.addAttribute("book", book);
    return "book-update";
  }

  @PostMapping("/book-update")
  public String updateBook(@Valid Book book, Errors errors) {
    if (errors.hasErrors()) return "/book-update";
    bookService.saveBook(book);
    return "redirect:/books";
  }

  @GetMapping("/readers/{id}")
  public String getReader(@PathVariable("id") long id, Model model) {
    model.addAttribute("readers", bookService.getReaderByBookId(id));
    model.addAttribute("bookId", id);
    return "readers";
  }
}
