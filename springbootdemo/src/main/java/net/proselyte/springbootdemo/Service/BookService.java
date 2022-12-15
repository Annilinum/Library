package net.proselyte.springbootdemo.Service;

import net.proselyte.springbootdemo.Controller.BadRequestException;
import net.proselyte.springbootdemo.Model.Book;
import net.proselyte.springbootdemo.Model.User;
import net.proselyte.springbootdemo.Repository.BookRepository;
import net.proselyte.springbootdemo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Book findById(Long id) {
        return bookRepository.getOne(id);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public void saveBook(String title, String author, long userId) {
        if (title.isBlank()) throw new BadRequestException();
        if (author.isBlank()) throw new BadRequestException();

        User user = userRepository.getReferenceById(userId);
        Book book = new Book();
        book.setUser(user);
        book.setTitle(title);
        book.setAuthor(author);
        saveBook(book);
    }

    public void saveBook(String title, String author) {
        if (title.isBlank()) throw new BadRequestException();
        if (author.isBlank()) throw new BadRequestException();

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        saveBook(book);
    }
}
