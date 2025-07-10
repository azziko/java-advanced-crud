package cz.sharipov.azul.crud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import cz.sharipov.azul.crud.model.Poem;
import cz.sharipov.azul.crud.repository.PoemRepository;
import cz.sharipov.azul.crud.service.PoemService;

@SpringBootTest
public class PoemServiceTest {

    Poem poem;

    @Mock
    private PoemRepository poemRepo;

    @InjectMocks
    private PoemService poemService;

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
        Mockito.when(poemRepo.save(Mockito.any(Poem.class))).thenReturn(poem);

        Poem res = poemService.savePoem(poem);
        assertEquals(poem, res);
        verify(poemRepo).save(poem);
    }

    @Test
    void testGetPoem() {
        poem.setId((long) 1);

        Mockito.when(poemRepo.findById((long) 1)).thenReturn(Optional.of(poem));
        Poem res = poemService.getPoem((long) 1);
        assertEquals(poem, res);
    }

    @Test
    void testUpdatePoem() {
        poem.setId((long) 1);

        Poem updatedPoem = new Poem();
        updatedPoem.setTitle("other title");

        Mockito.when(poemRepo.findById((long) 1)).thenReturn(Optional.of(poem));
        Mockito.when(poemRepo.save(Mockito.any(Poem.class))).thenReturn(poem);

        Poem res = poemService.updatePoem((long) 1, updatedPoem);
        assertEquals("other title", res.getTitle());
        verify(poemRepo).save(poem);
    }

    @Test
    void testDeletePoem() {
        Mockito.when(poemRepo.findById((long) 1)).thenReturn(Optional.of(poem));
        doNothing().when(poemRepo).deleteById((long) 1);

        Poem res = poemService.deletePoem((long) 1);
        assertEquals(poem, res);
        verify(poemRepo).deleteById((long) 1);
    }
}