package net.proselyte.springbootdemo.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Поле не может быть пустым")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Поле не может быть пустым")
    @Column(name = "last_name")
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
    public List<Book> books;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
