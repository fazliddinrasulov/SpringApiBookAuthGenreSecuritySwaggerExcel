package uz.pdp.back.service;

import org.springframework.stereotype.Service;
import uz.pdp.back.entity.Author;

import java.util.List;
import java.util.UUID;

@Service
public interface AuthorService {
    Author save(Author author);

    List<Author> findAll();

    Author findById(UUID id);

    Author saveFromString(String name);
}
