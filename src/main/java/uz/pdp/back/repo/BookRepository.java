package uz.pdp.back.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.back.entity.Book;
import uz.pdp.back.model.projection.BookForExcelProjection;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByAuthorId(UUID authorId);

    List<Book> findByAuthorIdAndGenreId(UUID authorId, UUID genreId);

    List<Book> findByGenreId(UUID genreId);

    @Query(value = "select b.id, a.id as authorId, g.id as genreId, b.title, a.name as author, g.name as genre from book b join author a on b.author_id = a.id join genre g on b.genre_id = g.id", nativeQuery=true)
    List<BookForExcelProjection> findAllBookAsExcel();

    List<Book> findAllByOrderByCreatedAt();

}