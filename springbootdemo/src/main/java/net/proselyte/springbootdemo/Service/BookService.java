package net.proselyte.springbootdemo.Service;

import net.proselyte.springbootdemo.Model.Book;
import net.proselyte.springbootdemo.Model.User;
import net.proselyte.springbootdemo.Repository.BookRepository;
import net.proselyte.springbootdemo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Page<Book> findAll(Integer pageNumber, String sortField, Sort.Direction sortDirection) {
        PageRequest pageRequest = PageRequest.of(pageNumber, 10, Sort.by(sortDirection, sortField));
        Page<Book> page = bookRepository.findAll(pageRequest);
        return page;
    }

    public List<Book> findFreeBooks() {
        return bookRepository.findAll().stream().filter(book -> book.getUser() == null).collect(Collectors.toList());
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public void deleteUserFromBook(Long bookId) {
        Book book = bookRepository.getOne(bookId);
        book.setUser(null);
        saveBook(book);
    }

    public void saveBook(Long bookId, Long userId) {
        User user = userRepository.getReferenceById(userId);
        Book book = bookRepository.getOne(bookId);
        book.setUser(user);
        saveBook(book);
    }

    public void saveBook(String title, String author) {

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        saveBook(book);
    }
}
