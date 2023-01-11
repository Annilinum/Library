package net.proselyte.springbootdemo.Controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class PageableHelper<T> {
  public void fillPageable(Model model, Page<T> page, Integer pageNumber, String sortField,
      String sortType) {
    int totalPages = page.getTotalPages();
    model.addAttribute("rowsData", page.toList());
    int prevPage = Math.max(pageNumber - 1, 0);
    int nextPage = Math.min(pageNumber + 1, totalPages);
    model.addAttribute("prevPage", prevPage);
    model.addAttribute("pageNumber", pageNumber);
    model.addAttribute("totalPages", totalPages);
    model.addAttribute("nextPage", nextPage);
    List<Integer> showedNumbers = List.of(pageNumber + 1, nextPage + 1, nextPage + 2);
    model.addAttribute("showedNumbers", showedNumbers);
    model.addAttribute("sortField", sortField);
    model.addAttribute("sortType", sortType);
  }
}
