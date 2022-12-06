package net.proselyte.springbootdemo.Model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table (name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name = "author")
    private String author;

    @Column (name = "title")
    private String title;
}
