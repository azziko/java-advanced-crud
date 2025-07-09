package cz.sharipov.azul.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cz.sharipov.azul.crud.model.Poem;
import cz.sharipov.azul.crud.repository.PoemRepository;

@Service
public class PoemService {
    
    @Autowired
    private PoemRepository poemRepo;

    public Poem savePoem(Poem poem) {
        return poemRepo.save(poem);
    }

    public Poem getPoem(long id) {
        return poemRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    public Poem updatePoem(long id, Poem poem) {
        Poem fetchedPoem = poemRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        fetchedPoem.setTitle(poem.getTitle());
        fetchedPoem.setAuthor(poem.getAuthor());
        fetchedPoem.setGenre(poem.getGenre());
        fetchedPoem.setContent(poem.getContent());

        poemRepo.save(fetchedPoem);

        return fetchedPoem;
    }

    public Poem deletePoem(long id) {
        Poem fetchedPoem = poemRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        poemRepo.deleteById(id);

        return fetchedPoem;
    }

    public Page<Poem> searchPoemsByTitle(String title, int page, int size, Pageable pageable) {
        Page<Poem> poems = poemRepo.findByTitle(title, pageable);

        return poems;
    }

    public Page<Poem> searchPoemsByAuthor(String author, int page, int size, Pageable pageable) {
        Page<Poem> poems = poemRepo.findByAuthor(author, pageable);

        return poems;
    }

    public Page<Poem> searchPoemsByGenre(String genre, int page, int size, Pageable pageable) {
        Page<Poem> poems = poemRepo.findByGenre(genre, pageable);

        return poems;
    }

    public Page<Poem> searchPoemsByContent(String content, int page, int size, Pageable pageable) {
        Page<Poem> poems = poemRepo.findByContent(content, pageable);

        return poems;
    }
}
