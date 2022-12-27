package net.proselyte.springbootdemo.Service;

import lombok.AllArgsConstructor;
import net.proselyte.springbootdemo.Model.Book;
import net.proselyte.springbootdemo.Model.User;
import net.proselyte.springbootdemo.Repository.BookRepository;
import net.proselyte.springbootdemo.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public User findById(Long id) {
        return userRepository.getOne(id);
    }

    public List<Book> getBookByUserId(Long userId) {
        return findById(userId).getBooks();
    }

    public Page<User> findAll(Integer pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, 10);
        Page<User> page = userRepository.findAll(pageRequest);
        return page;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
