package net.proselyte.springbootdemo.Controller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

@Component
public class PageableHelper {
    public void fillPageable(Model model, Integer pageNumber, int totalPages) {
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("totalPages", totalPages);
        int prevPage = Math.max(pageNumber - 1, 0);
        model.addAttribute("prevPage", prevPage);
        int nextPage = Math.min(pageNumber + 1, totalPages);
        model.addAttribute("nextPage", nextPage);
        List<Integer> showedNumbers = List.of(pageNumber + 1, nextPage + 1, nextPage + 2);
        model.addAttribute("showedNumbers", showedNumbers);
    }
}
