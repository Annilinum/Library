package net.proselyte.springbootdemo.Controller;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import net.proselyte.springbootdemo.Model.Book;
import net.proselyte.springbootdemo.Service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class BookController {
  private final BookService bookService;
  private final PageableHelper<Book> pageableHelper;

  @GetMapping("/books")
  public String getBooksTable(Model model,
      @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
      @RequestParam(value = "sortField", required = false, defaultValue = "title") String sortField,
      @RequestParam(value = "sortType", required = false) String sortType) {

    Direction sortDirection = sortType == null ? Direction.ASC : Direction.fromString(sortType);
    PageRequest pageRequest = PageRequest.of(pageNumber, 10, Sort.by(sortDirection, sortField));
    Page<Book> page = bookService.getBooksPage(pageRequest);
    pageableHelper.fillModel(model, page, sortField, sortDirection);
    return "books.html";
  }

  @GetMapping("/book/{bookId}")
  public String deleteBook(@PathVariable("bookId") long bookId) {
    bookService.deleteBook(bookId);
    return "redirect:/books";
  }

  @GetMapping("/book/create")
  public String createBookForm(@ModelAttribute(name = "newBook") CreateBookRequest newBook) {
    return "book-create.html";
  }

  @PostMapping("/book/create")
  public String createBook(@Valid @ModelAttribute(name = "newBook") CreateBookRequest newBook, Errors errors) {
    if (errors.hasErrors()) {
      return "book-create.html";
    }
    bookService.createNewBook(newBook.getTitle(), newBook.getAuthor(), newBook.getCountBook());
    return "redirect:/books";
  }

  @GetMapping("/book/{bookId}/update")
  public String getUpdateBookForm(@PathVariable("bookId") long bookId, Model model) {
    Book book = bookService.findById(bookId);
    model.addAttribute("book", book);
    return "book-update.html";
  }

  @PostMapping("/book/update")
  public String updateBook(@Valid Book book, Errors errors) {
    if (errors.hasErrors()) return "/book/update";
    bookService.saveBook(book);
    return "redirect:/books";
  }

  @GetMapping("/book/{bookId}/readers")
  public String getReadersTable(@PathVariable("bookId") long bookId, Model model) {
    model.addAttribute("readers", bookService.getReaderByBookId(bookId));
    model.addAttribute("bookId", bookId);
    return "readers.html";
  }
}
