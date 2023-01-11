package net.proselyte.springbootdemo.Service;

import java.util.List;
import java.util.stream.Collectors;
import net.proselyte.springbootdemo.Model.Book;
import net.proselyte.springbootdemo.Model.User;
import net.proselyte.springbootdemo.Repository.BookRepository;
import net.proselyte.springbootdemo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BookService {
  private final BookRepository bookRepository;
  private final UserRepository userRepository;

  @Autowired
  public BookService(BookRepository bookRepository, UserRepository userRepository) {
    this.bookRepository = bookRepository;
    this.userRepository = userRepository;
  }

  public Page<Book> getBokksPage(Integer pageNumber, String sortField, Sort.Direction sortDirection) {
    PageRequest pageRequest = PageRequest.of(pageNumber, 10, Sort.by(sortDirection, sortField));
    return bookRepository.findAll(pageRequest);
  }

  public Book findById(long id) {
    return bookRepository.getOne(id);
  }

  public List<Book> findFreeBooks(long excludeUserId) {
    return bookRepository.findAll().stream().filter(book -> {
      List<User> readers = book.getUsers();
      boolean bookIssuedExcludedUser = readers.stream().anyMatch(user -> user.getId() == excludeUserId);
      return book.getCountLeft() > 0 && !bookIssuedExcludedUser;
    }).collect(Collectors.toList());
  }

  public void saveBook(Book book) {
    bookRepository.save(book);
  }

  public void deleteBook(Long id) {
    bookRepository.deleteById(id);
  }

  public void getBookBack(long bookId, long userId) {
    Book book = bookRepository.getOne(bookId);
    book.getUsers().remove(userRepository.getById(userId));
    book.setCountLeft(book.getCountLeft() + 1);
    saveBook(book);
  }

  public void issueBook(long bookId, long userId) {
    User user = userRepository.getReferenceById(userId);
    Book book = bookRepository.getOne(bookId);
    book.getUsers().add(user);
    book.setCountLeft(book.getCountLeft() - 1);
    saveBook(book);
  }

  public void createNewBook(String title, String author, int countBook) {
    Book book = new Book();
    book.setTitle(title);
    book.setAuthor(author);
    book.setCountTotal(countBook);
    book.setCountLeft(countBook);
    saveBook(book);
  }

  public List<User> getReaderByBookId(Long bookId) {
    return findById(bookId).getUsers();
  }
}
