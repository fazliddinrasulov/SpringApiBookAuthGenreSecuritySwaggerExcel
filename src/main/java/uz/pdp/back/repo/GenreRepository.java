package uz.pdp.back.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.back.entity.Genre;

import java.util.List;
import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {
    List<Genre> findAllByOrderByCreatedAt();
}