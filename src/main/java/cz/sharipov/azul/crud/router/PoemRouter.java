package cz.sharipov.azul.crud.router;

import cz.sharipov.azul.crud.model.Poem;
import cz.sharipov.azul.crud.service.PoemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

/**
 * Rest API controller.
 */
@RestController
@RequestMapping("/api/poems")
public class PoemRouter {

    /** 
     * Service injection.
     */
    @Autowired
    private PoemService poemService;

    /**
     * Add poem handler, restricted to admin role only.
     * 
     * @param poem
     * @return Added poem.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Poem> add(@RequestBody Poem poem) {
        Poem addedPoem = poemService.savePoem(poem);
        
        return new ResponseEntity<>(addedPoem, HttpStatus.CREATED);
    }

    /**
     * Handler that get poem by its id.
     * 
     * @param id
     * @return fetched poem.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Poem> getById(@PathVariable Long id) {
        Poem fetchedPoem = poemService.getPoem(id);
        
        return new ResponseEntity<>(fetchedPoem, HttpStatus.OK);
    }

    /**
     * Handler for updating a poem. Restricted to the admin role.
     * 
     * 
     * @param id
     * @param inputedPoem
     * @return updated poem.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Poem> update(@PathVariable long id, @RequestBody Poem inputedPoem) {
        Poem updatedPoem = poemService.updatePoem(id, inputedPoem);

        return new ResponseEntity<>(updatedPoem, HttpStatus.OK);
    } 

    /**
     * Delete poem handler. Restricted to the admin role.
     * 
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Poem> deletePoem(@PathVariable Long id) {
        Poem deletedPoem = poemService.deletePoem(id);

        return new ResponseEntity<>(deletedPoem, HttpStatus.OK);
    }

    /**
     * Search poem by title handler.
     * 
     * @param title
     * @param page
     * @param size
     * @return page of the requested size or a page of size 5.
     */
    @GetMapping("/search/title")
    public ResponseEntity<Page<Poem>> searchByTitle(
        @RequestParam String title,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Poem> poemPage = poemService.searchPoemsByTitle(title, page, size, pageable);
        return new ResponseEntity<>(poemPage, HttpStatus.OK);
    }

    /**
     * Search poem by authoe handler. 
     * 
     * @param author
     * @param page
     * @param size
     * @return page of the requested size or a page of size 5.
     */
    @GetMapping("/search/author")
    public ResponseEntity<Page<Poem>> searchByAuhtor(
        @RequestParam String author,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Poem> poemPage = poemService.searchPoemsByAuthor(author, page, size, pageable);
        return new ResponseEntity<>(poemPage, HttpStatus.OK);
    }

    /**
     * Search poem by its genre handler.
     * 
     * @param genre
     * @param page
     * @param size
     * @return page of the requested size or a page of size 5.
     */
    @GetMapping("/search/genre")
    public ResponseEntity<Page<Poem>> searchByGenre(
        @RequestParam String genre,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Poem> poemPage = poemService.searchPoemsByGenre(genre, page, size, pageable);
        return new ResponseEntity<>(poemPage, HttpStatus.OK);
    }

    /**
     * Search poem by its content handler.
     * Case insensetive.
     * 
     * @param content
     * @param page
     * @param size
     * @return page of the requested size or a page of size 5.
     */
    @GetMapping("/search/content")
    public ResponseEntity<Page<Poem>> searchByContent(
        @RequestParam String content,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Poem> poemPage = poemService.searchPoemsByContent(content, page, size, pageable);
        return new ResponseEntity<>(poemPage, HttpStatus.OK);
    }
}
