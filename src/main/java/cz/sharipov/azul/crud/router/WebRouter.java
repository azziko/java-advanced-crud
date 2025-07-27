package cz.sharipov.azul.crud.router;

import cz.sharipov.azul.crud.model.Poem;
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
        model.addAttribute("poem", new Poem());
        return "admin";
    }

    @GetMapping("/admin/poem/{id}")
    public String adminEditing(@PathVariable("id") long id, Model model) {
        model.addAttribute("poem", poemService.getPoem(id));
        return "edit";
    }

    @PostMapping("/admin/poem/{id}")
    public String adminEdit(@PathVariable("id") long id, @ModelAttribute Poem poem, Model model) {
        model.addAttribute("poem", poemService.updatePoem(id, poemService.updatePoem(id, poem)));
        return "redirect:/admin";
    }

    @PostMapping("/admin/poems")
    public String adminAdd(@ModelAttribute Poem poem) {
        poemService.savePoem(poem);
        return "redirect:/admin";
    }

    @PostMapping("/admin/poems/{id}")
    public String adminDelete(@PathVariable Long id, @RequestParam(value = "method", required = false) String method) {
        if ("delete".equalsIgnoreCase(method)) {
            poemService.deletePoem(id);
        }
        
        return "redirect:/admin";
    }
}