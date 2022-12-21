package net.proselyte.springbootdemo.Controller;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
@Data
public class CreateBookRequest {
    @NotEmpty(message = "Поле не может быть пустым")
    private String author;
    @NotEmpty(message = "Поле не может быть пустым")
    private String title;
}
