package cz.sharipov.azul.crud.repository;

import cz.sharipov.azul.crud.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
   @Query(value = "SELECT * FROM books WHERE LOWER(title) LIKE LOWER(CONCAT('%', ?1, '%'))", nativeQuery = true)
   Page<Book> findByTitle(String title, Pageable pageable);

   @Query(value = "SELECT * FROM books WHERE LOWER(author) LIKE LOWER(CONCAT('%', ?1, '%'))", nativeQuery = true)
   Page<Book> findByAuthor(String author, Pageable pageable);

   @Query(value = "SELECT * FROM books WHERE LOWER(genre) LIKE LOWER(CONCAT('%', ?1, '%'))", nativeQuery = true)
   Page<Book> findByGenre(String genre, Pageable pageable);
}