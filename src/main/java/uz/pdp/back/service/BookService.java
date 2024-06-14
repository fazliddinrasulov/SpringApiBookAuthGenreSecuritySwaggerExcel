package uz.pdp.back.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.back.entity.Book;
import uz.pdp.back.model.dto.AddBookDto;

import java.util.List;
import java.util.UUID;

@Service
public interface BookService {
    Book save(Book book);

    List<Book> findAll();

    List<Book> findByAuthorId(UUID authorId);

    List<Book> findByGenreIdAndAuthorId(UUID genreId, UUID authorId);

    Book saveFromAddBookDto(byte[] bytes, AddBookDto addBookDto);

    Book findById(UUID id);

    void deleteBook(UUID id);

    byte[] exportBookToExcel();

    void importExcelToDb(MultipartFile file);
}
