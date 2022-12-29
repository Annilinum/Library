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

import java.util.ArrayList;
import java.util.List;

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
        return bookRepository.findAll(pageRequest);
    }

    public Book findById(long id) {
        return bookRepository.getOne(id);
    }

    public List<Book> findFreeBooks() {
        return new ArrayList<>();//;/bookRepository.findAll().stream().filter(book -> book.getUser() == null).collect(Collectors.toList());
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public void deleteUserFromBook(Long bookId) {
        Book book = bookRepository.getOne(bookId);
        //   book.количество_оставшихся_книг = book.количество_оставшихся_книг + 1
        /*  book.setUser(null);*/
        //book.getUsers().remove()??/??
        saveBook(book);
    }

    public void saveBook(Long bookId, Long userId) {
        User user = userRepository.getReferenceById(userId);
        Book book = bookRepository.getOne(bookId);
        book.getUsers().add(user);
        //book.setUser(user);
        saveBook(book);
    }

    public void saveBook(String title, String author) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        saveBook(book);
    }
}
