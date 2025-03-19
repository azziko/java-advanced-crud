package cz.sharipov.azul.crud.router;

import cz.sharipov.azul.crud.model.Book;
import cz.sharipov.azul.crud.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

@RestController
@RequestMapping("/api/books")
public class BookRouter {

    @Autowired
    private BookService bookService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> add(@RequestBody Book book) {
        Book addedBook = bookService.saveBook(book);
        
        return new ResponseEntity<>(addedBook, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        Book fetchedBook = bookService.getBook(id);
        
        return new ResponseEntity<>(fetchedBook, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> update(@PathVariable long id, @RequestBody Book inputedBook) {
        Book updatedBook = bookService.updateBook(id, inputedBook);

        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    } 

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id) {
        Book deletedBook = bookService.deleteBook(id);

        return new ResponseEntity<>(deletedBook, HttpStatus.OK);
    }

    @GetMapping("/search/title")
    public ResponseEntity<Page<Book>> searchByTitle(
        @RequestParam String title,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Book> bookPage = bookService.searchBooksByTitle(title, page, size, pageable);
        return new ResponseEntity<>(bookPage, HttpStatus.OK);
    }

    @GetMapping("/search/author")
    public ResponseEntity<Page<Book>> searchByAuhtor(
        @RequestParam String author,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Book> bookPage = bookService.searchBooksByAuthor(author, page, size, pageable);
        return new ResponseEntity<>(bookPage, HttpStatus.OK);
    }

    @GetMapping("/search/genre")
    public ResponseEntity<Page<Book>> searchByGenre(
        @RequestParam String genre,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Book> bookPage = bookService.searchBooksByGenre(genre, page, size, pageable);
        return new ResponseEntity<>(bookPage, HttpStatus.OK);
    }
}
