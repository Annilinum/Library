package net.proselyte.springbootdemo.Model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "author")
    //@NotEmpty(message = "Поле не может быть пустым")
    private String author;

   // @NotEmpty(message = "Поле не может быть пустым")
    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="person_id")
    private User user;
}
