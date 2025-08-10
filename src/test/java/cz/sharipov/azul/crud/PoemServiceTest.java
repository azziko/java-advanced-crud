package cz.sharipov.azul.crud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

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
    void testGetPoemThrowsError() {
        Mockito.when(poemRepo.findById((long) 123)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> poemService.getPoem((long) 123));
        assertEquals("Not found", exception.getMessage());
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
    void testUpdatePoemThrowsError() {
        Mockito.when(poemRepo.findById((long) 123)).thenReturn(Optional.empty());
        Poem updatedPoem = new Poem();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> poemService.updatePoem((long) 123, updatedPoem));
        assertEquals("Not found", exception.getMessage());
    }

    @Test
    void testDeletePoem() {
        Mockito.when(poemRepo.findById((long) 1)).thenReturn(Optional.of(poem));
        doNothing().when(poemRepo).deleteById((long) 1);

        Poem res = poemService.deletePoem((long) 1);
        assertEquals(poem, res);
        verify(poemRepo).deleteById((long) 1);
    }

    @Test
    void testDeletePoemThrowsError() {
        Mockito.when(poemRepo.findById((long) 123)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> poemService.deletePoem((long) 123));
        assertEquals("Not found", exception.getMessage());
    }
    
    @Test
    void testSearchPoemsByTitle() {
        List<Poem> poems = List.of(poem);
        Pageable pageable = Mockito.mock(Pageable.class);

        Mockito.when(poemRepo.findByTitle("title", pageable))
            .thenReturn(new PageImpl<>(poems));

        Page<Poem> res = poemService.searchPoemsByTitle("title", 0, 5, pageable);
        assertEquals(poem, res.getContent().get(0));
    }

    @Test
    void testSearchPoemsByAuthor() {
        List<Poem> poems = List.of(poem);
        Pageable pageable = Mockito.mock(Pageable.class);

        Mockito.when(poemRepo.findByAuthor("author", pageable))
            .thenReturn(new PageImpl<>(poems));

        Page<Poem> res = poemService.searchPoemsByAuthor("author", 0, 5, pageable);
        assertEquals(poem, res.getContent().get(0));
    }
    
    @Test
    void testSearchPoemsByGenre() {
        List<Poem> poems = List.of(poem);
        Pageable pageable = Mockito.mock(Pageable.class);

        Mockito.when(poemRepo.findByGenre("genre", pageable))
            .thenReturn(new PageImpl<>(poems));

        Page<Poem> res = poemService.searchPoemsByGenre("genre", 0, 5, pageable);
        assertEquals(poem, res.getContent().get(0));
    }

    @Test
    void testSearchPoemsByContent() {
        List<Poem> poems = List.of(poem);
        Pageable pageable = Mockito.mock(Pageable.class);

        Mockito.when(poemRepo.findByContent("content", pageable))
            .thenReturn(new PageImpl<>(poems));

        Page<Poem> res = poemService.searchPoemsByContent("content", 0, 5, pageable);
        assertEquals(poem, res.getContent().get(0));
    }
}