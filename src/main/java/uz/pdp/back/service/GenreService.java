package uz.pdp.back.service;

import org.springframework.stereotype.Service;
import uz.pdp.back.entity.Genre;

import java.util.List;
import java.util.UUID;

@Service
public interface GenreService {
    Genre save(Genre genre);

    List<Genre> findAll();

    Genre findById(UUID uuid);

    Genre saveFromString(String name);
}
