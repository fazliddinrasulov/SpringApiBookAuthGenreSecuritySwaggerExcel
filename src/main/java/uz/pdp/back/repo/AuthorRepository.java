package uz.pdp.back.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.back.entity.Author;

import java.util.List;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
    List<Author> findAllByOrderByCreatedAt();

}