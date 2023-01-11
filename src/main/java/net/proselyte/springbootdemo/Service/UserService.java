package net.proselyte.springbootdemo.Service;

import java.util.List;
import lombok.AllArgsConstructor;
import net.proselyte.springbootdemo.Model.Book;
import net.proselyte.springbootdemo.Model.User;
import net.proselyte.springbootdemo.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final BookService bookService;

  public User findById(Long id) {
    return userRepository.getOne(id);
  }

  public List<Book> getBookByUserId(Long userId) {
    return findById(userId).getBooks();
  }

  public Page<User> findAll(Integer pageNumber, String sortField, Sort.Direction sortDirection) {
    PageRequest pageRequest = PageRequest.of(pageNumber, 10, Sort.by(sortDirection, sortField));
    return userRepository.findAll(pageRequest);
  }

  public void saveUser(User user) {
    userRepository.save(user);
  }

  public void deleteById(long userId) {
    getBookByUserId(userId).forEach(book -> bookService.getBookBack(book.getId(), userId));
    userRepository.deleteById(userId);
  }
}
