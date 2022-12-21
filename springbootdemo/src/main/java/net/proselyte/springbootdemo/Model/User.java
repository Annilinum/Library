package net.proselyte.springbootdemo.Model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    public List<Book> books;

}
