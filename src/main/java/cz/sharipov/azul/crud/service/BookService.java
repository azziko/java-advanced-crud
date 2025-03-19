package cz.sharipov.azul.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cz.sharipov.azul.crud.model.Book;
import cz.sharipov.azul.crud.repository.BookRepository;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepo;

    public Book saveBook(Book book) {
        return bookRepo.save(book);
    }

    public Book getBook(long id) {
        return bookRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    public Book updateBook(long id, Book book) {
        Book fetchedBook = bookRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        fetchedBook.setTitle(book.getTitle());
        fetchedBook.setAuthor(book.getAuthor());
        fetchedBook.setPrice(book.getPrice());
        fetchedBook.setGenre(book.getGenre());

        bookRepo.save(fetchedBook);

        return fetchedBook;
    }

    public Book deleteBook(long id) {
        Book fetchedBook = bookRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        bookRepo.deleteById(id);

        return fetchedBook;
    }

    public Page<Book> searchBooksByTitle(String title, int page, int size, Pageable pageable) {
        Page<Book> books = bookRepo.findByTitle(title, pageable);

        return books;
    }

    public Page<Book> searchBooksByAuthor(String author, int page, int size, Pageable pageable) {
        Page<Book> books = bookRepo.findByAuthor(author, pageable);

        return books;
    }

    public Page<Book> searchBooksByGenre(String genre, int page, int size, Pageable pageable) {
        Page<Book> books = bookRepo.findByGenre(genre, pageable);

        return books;
    }
}
