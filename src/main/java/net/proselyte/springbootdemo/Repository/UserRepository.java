package net.proselyte.springbootdemo.Repository;

import net.proselyte.springbootdemo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
