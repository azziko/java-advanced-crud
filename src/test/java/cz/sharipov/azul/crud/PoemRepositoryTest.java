package cz.sharipov.azul.crud;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

import cz.sharipov.azul.crud.model.Poem;
import cz.sharipov.azul.crud.repository.PoemRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PoemRepositoryTest {
    Poem poem;

    @Autowired
    private PoemRepository poemRepo;

    @BeforeEach
    void setUp() {
        poem = new Poem();
        poem.setTitle("title");
        poem.setAuthor("author");
        poem.setGenre("genre");
        poem.setContent("content");
    }

    @Test
    void testSavePoem() {
        poem.setTitle("some different title");

        Poem savedPoem = poemRepo.save(poem);
        assertEquals("some different title", savedPoem.getTitle());
    }

    @Test
    void testFindByAuthor() {
        poem.setAuthor("John Smith");

        poemRepo.save(poem);
        Page<Poem> res = poemRepo.findByAuthor("Smith", PageRequest.of(0, 5));
        assertEquals(1, res.getTotalElements());
    }

    @Test
    void testFindByTitle() {
        poem.setTitle("Swan Song");

        poemRepo.save(poem);
        Page<Poem> res = poemRepo.findByTitle("Song", PageRequest.of(0, 5));
        assertEquals(1, res.getTotalElements());
    }

    @Test
    void testFindByGenre() {
        poem.setTitle("Horror");

        poemRepo.save(poem);
        Page<Poem> res = poemRepo.findByGenre("Horror", PageRequest.of(0, 5));
        assertEquals(1, res.getTotalElements());
    }

    @Test
    void testFindByContent() {
        poem.setContent("lorem ipsun");

        poemRepo.save(poem);
        Page<Poem> res = poemRepo.findByContent("ipsun", PageRequest.of(0, 5));
        assertEquals(1, res.getTotalElements());
    }
}
