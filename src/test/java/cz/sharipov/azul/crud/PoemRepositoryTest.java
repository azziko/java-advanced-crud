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
    Poem poem = new Poem();

    @Autowired
    private PoemRepository poemRepository;

    @BeforeEach
    void setUp() {
        poem.setTitle("title");
        poem.setAuthor("author");
        poem.setGenre("genre");
        poem.setContent("content");
    }

    @Test
    public void testSavePoem() {
        poem.setTitle("some different title");

        Poem savedPoem = poemRepository.save(poem);
        assertEquals("some different title", savedPoem.getTitle());
    }

    @Test
    public void testFindByAuthor() {
        poem.setAuthor("John Smith");

        poemRepository.save(poem);
        Page<Poem> res = poemRepository.findByAuthor("Smith", PageRequest.of(0, 5));
        assertEquals(1, res.getTotalElements());
    }

    @Test
    public void testFindByTitle() {
        poem.setTitle("Swan Song");

        poemRepository.save(poem);
        Page<Poem> res = poemRepository.findByTitle("Song", PageRequest.of(0, 5));
        assertEquals(1, res.getTotalElements());
    }

    @Test
    public void testFindByGenre() {
        poem.setTitle("Horror");

        poemRepository.save(poem);
        Page<Poem> res = poemRepository.findByGenre("Horror", PageRequest.of(0, 5));
        assertEquals(1, res.getTotalElements());
    }

    @Test
    public void testFindByContent() {
        poem.setContent("lorem ipsun");

        poemRepository.save(poem);
        Page<Poem> res = poemRepository.findByContent("ipsun", PageRequest.of(0, 5));
        assertEquals(1, res.getTotalElements());
    }
}
