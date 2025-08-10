package cz.sharipov.azul.crud.router;

import cz.sharipov.azul.crud.model.Poem;
import cz.sharipov.azul.crud.service.PoemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Web Controller that handles routing to UI pages.
 * 
 * Provides enpdoints different from REST API, to display the REST API fcuntuanality.
 */
@Controller
public class WebRouter {

    /** 
     * Service injection.
     */
    @Autowired
    private PoemService poemService;

    /**
     * Display all poems handler.
     * 
     * @param model
     * @return index page
     */
    @GetMapping("/")
    public String publicIndex(Model model) {
        model.addAttribute("poems", poemService.searchPoemsByContent("", 0, 100, null).getContent());
        return "index";
    }

    /**
     * Render poem page by its id.
     * 
     * @param id
     * @param model
     * @return poem page
     */
    @GetMapping("/poem/{id}/")
    public String publicPoem(@PathVariable("id") long id, Model model) {
        model.addAttribute("poem", poemService.getPoem(id));
        return "poem";
    }

    /**
     * Render admin page. Restricted to aithenticated users.
     * 
     * @param model
     * @return admin page
     */
    @GetMapping("/admin")
    public String adminHome(Model model) {
        model.addAttribute("poems", poemService.searchPoemsByContent("", 0, 100, null).getContent());
        model.addAttribute("poem", new Poem());
        return "admin";
    }

    /**
     * Render admin's porm edit page by id. Restricted to aithenticated users.
     * 
     * @param id
     * @param model
     * @return edit page
     */
    @GetMapping("/admin/poem/{id}")
    public String adminEditing(@PathVariable("id") long id, Model model) {
        model.addAttribute("poem", poemService.getPoem(id));
        return "edit";
    }

    /**
     * Submit changes by admin via edit page. Restricted to aithenticated users.
     * 
     * @param id
     * @param poem
     * @param model
     * @return redirect to admin
     */
    @PostMapping("/admin/poem/{id}")
    public String adminEdit(@PathVariable("id") long id, @ModelAttribute Poem poem, Model model) {
        model.addAttribute("poem", poemService.updatePoem(id, poemService.updatePoem(id, poem)));
        return "redirect:/admin";
    }

    /**
     * Submit a poem added by admin. Restricted to aithenticated users.
     * 
     * @param poem
     * @return redirect to admin
     */
    @PostMapping("/admin/poems")
    public String adminAdd(@ModelAttribute Poem poem) {
        poemService.savePoem(poem);
        return "redirect:/admin";
    }

    /**
     * Delete page submitted by admin. Restricted to aithenticated users.
     * 
     * @param id
     * @param method
     * @return redirect to admin
     */
    @PostMapping("/admin/poems/{id}")
    public String adminDelete(@PathVariable Long id, @RequestParam(value = "method", required = false) String method) {
        if ("delete".equalsIgnoreCase(method)) {
            poemService.deletePoem(id);
        }
        
        return "redirect:/admin";
    }
}