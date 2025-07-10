package cz.sharipov.azul.crud;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;

import cz.sharipov.azul.crud.model.Poem;
import cz.sharipov.azul.crud.service.PoemService;

@SpringBootTest
@AutoConfigureMockMvc
public class PoemRouterTest {
    
    Poem poem;
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PoemService poemService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        poem = new Poem();
        poem.setTitle("title");
        poem.setAuthor("author");
        poem.setGenre("genre");
        poem.setContent("content");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAdd() throws Exception {
        poem.setId((long) 1);
        Mockito.when(poemService.savePoem(Mockito.any(Poem.class))).thenReturn(poem);

        mockMvc.perform(post("/api/poems")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(poem)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("title")));
    }

    @Test
    void testGetById() throws Exception {
        poem.setId((long) 2);

        Mockito.when(poemService.getPoem(2)).thenReturn(poem);

        mockMvc.perform(get("/api/poems/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("title")));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testUpdate() throws Exception {
        poem.setId((long) 1);

        Poem updatedPoem = new Poem();
        updatedPoem.setId((long) 2);
        updatedPoem.setTitle("other title");

        Mockito.when(poemService.updatePoem(Mockito.eq((long) 2), Mockito.any(Poem.class))).thenReturn(updatedPoem);

        mockMvc.perform(patch("/api/poems/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(poem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("other title")));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeletePoem() throws Exception {
        poem.setId((long) 1);
        Mockito.when(poemService.deletePoem((long) 1)).thenReturn(poem);

        mockMvc.perform(delete("/api/poems/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("title")));
    }

    @Test
    void testSearchByTitle() throws Exception {
        poem.setId((long) 1);
        poem.setTitle("other title");

        Page<Poem> page = new PageImpl<>(List.of(poem));
        Mockito.when(poemService.searchPoemsByTitle("other title", 0, 5, PageRequest.of(0, 5))).thenReturn(page);

        mockMvc.perform(get("/api/poems/search/title")
                .param("title", "other title")
                .param("page", "0")
                .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title", is("other title")));
    }

    @Test
    void testSearchByAuthor() throws Exception {
        poem.setId((long) 1);
        poem.setAuthor("other author");

        Page<Poem> page = new PageImpl<>(List.of(poem));
        Mockito.when(poemService.searchPoemsByAuthor("other author", 0, 5, PageRequest.of(0, 5))).thenReturn(page);

        mockMvc.perform(get("/api/poems/search/author")
                .param("author", "other author")
                .param("page", "0")
                .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].author", is("other author")));
    }

    @Test
    void testSearchByGenre() throws Exception {
        poem.setId((long) 1);
        poem.setGenre("other genre");

        Page<Poem> page = new PageImpl<>(List.of(poem));
        Mockito.when(poemService.searchPoemsByGenre("other genre", 0, 5, PageRequest.of(0, 5))).thenReturn(page);

        mockMvc.perform(get("/api/poems/search/genre")
                .param("genre", "other genre")
                .param("page", "0")
                .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].genre", is("other genre")));
    }

    @Test
    void testSearchByContent() throws Exception {
        poem.setId((long) 1);
        poem.setContent("lorem ipsun");

        Page<Poem> page = new PageImpl<>(List.of(poem));
        Mockito.when(poemService.searchPoemsByContent("lorem", 0, 5, PageRequest.of(0, 5))).thenReturn(page);

        mockMvc.perform(get("/api/poems/search/content")
                .param("content", "lorem")
                .param("page", "0")
                .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].content", containsString("lorem")));
    }
}