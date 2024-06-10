package uz.pdp.back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.back.entity.Author;
import uz.pdp.back.repo.AuthorRepository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAllByOrderByCreatedAt();
    }

    @Override
    public Author findById(UUID id) {
        return authorRepository.findById(id).get();
    }

    @Override
    public Author saveFromString(String name) {
        return authorRepository.save(Author.builder().name(name).build());
    }
}
