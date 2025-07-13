package cz.sharipov.azul.crud.router;

import cz.sharipov.azul.crud.service.PoemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebRouter {

    @Autowired
    private PoemService poemService;

    @GetMapping("/")
    public String publicIndex(Model model) {
        model.addAttribute("poems", poemService.searchPoemsByContent("", 0, 100, null).getContent());
        return "index";
    }

    @GetMapping("/poem/{id}/")
    public String publicPoem(@PathVariable("id") long id, Model model) {
        model.addAttribute("poem", poemService.getPoem(id));
        return "poem";
    }

    @GetMapping("/admin")
    public String adminHome(Model model) {
        model.addAttribute("poems", poemService.searchPoemsByContent("", 0, 100, null).getContent());
        return "admin";
    }
}