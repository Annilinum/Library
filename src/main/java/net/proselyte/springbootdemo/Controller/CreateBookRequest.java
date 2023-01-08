package net.proselyte.springbootdemo.Controller;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookRequest {
  @NotEmpty(message = "Поле не может быть пустым")
  private String author;
  @NotEmpty(message = "Поле не может быть пустым")
  private String title;

  private int countBook;

  @Override public String toString() {
    return "CreateBookRequest{" +
        "author='" + author + '\'' +
        ", title='" + title + '\'' +
        '}';
  }
}
