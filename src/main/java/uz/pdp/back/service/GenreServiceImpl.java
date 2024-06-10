package uz.pdp.back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.back.entity.Genre;
import uz.pdp.back.repo.GenreRepository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAllByOrderByCreatedAt();
    }

    @Override
    public Genre findById(UUID id) {
        return genreRepository.findById(id).get();
    }

    @Override
    public Genre saveFromString(String name) {
        Genre genre = Genre.builder().name(name).build();
        return genreRepository.save(genre);
    }
}
