package cz.sharipov.azul.crud.repository;

import cz.sharipov.azul.crud.model.Poem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PoemRepository extends JpaRepository<Poem, Long> {
   @Query(value = "SELECT * FROM poems WHERE LOWER(title) LIKE LOWER(CONCAT('%', ?1, '%'))", nativeQuery = true)
   Page<Poem> findByTitle(String title, Pageable pageable);

   @Query(value = "SELECT * FROM poems WHERE LOWER(author) LIKE LOWER(CONCAT('%', ?1, '%'))", nativeQuery = true)
   Page<Poem> findByAuthor(String author, Pageable pageable);

   @Query(value = "SELECT * FROM poems WHERE LOWER(genre) LIKE LOWER(CONCAT('%', ?1, '%'))", nativeQuery = true)
   Page<Poem> findByGenre(String genre, Pageable pageable);

   @Query(value = "SELECT * FROM poems WHERE LOWER(content) LIKE LOWER(CONCAT('%', ?1, '%'))", nativeQuery = true)
   Page<Poem> findByContent(String content, Pageable pageable);
}