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

@RestController
@RequestMapping("/api/poems")
public class PoemRouter {

    @Autowired
    private PoemService poemService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Poem> add(@RequestBody Poem poem) {
        Poem addedPoem = poemService.savePoem(poem);
        
        return new ResponseEntity<>(addedPoem, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Poem> getById(@PathVariable Long id) {
        Poem fetchedPoem = poemService.getPoem(id);
        
        return new ResponseEntity<>(fetchedPoem, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Poem> update(@PathVariable long id, @RequestBody Poem inputedPoem) {
        Poem updatedPoem = poemService.updatePoem(id, inputedPoem);

        return new ResponseEntity<>(updatedPoem, HttpStatus.OK);
    } 

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Poem> deletePoem(@PathVariable Long id) {
        Poem deletedPoem = poemService.deletePoem(id);

        return new ResponseEntity<>(deletedPoem, HttpStatus.OK);
    }

    @GetMapping("/search/title")
    public ResponseEntity<Page<Poem>> searchByTitle(
        @RequestParam String title,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Poem> poemPage = poemService.searchPoemsByTitle(title, page, size, pageable);
        return new ResponseEntity<>(poemPage, HttpStatus.OK);
    }

    @GetMapping("/search/author")
    public ResponseEntity<Page<Poem>> searchByAuhtor(
        @RequestParam String author,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Poem> poemPage = poemService.searchPoemsByAuthor(author, page, size, pageable);
        return new ResponseEntity<>(poemPage, HttpStatus.OK);
    }

    @GetMapping("/search/genre")
    public ResponseEntity<Page<Poem>> searchByGenre(
        @RequestParam String genre,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Poem> poemPage = poemService.searchPoemsByGenre(genre, page, size, pageable);
        return new ResponseEntity<>(poemPage, HttpStatus.OK);
    }

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
