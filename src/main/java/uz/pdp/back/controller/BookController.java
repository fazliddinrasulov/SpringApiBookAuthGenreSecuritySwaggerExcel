package uz.pdp.back.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.back.entity.Attachment;
import uz.pdp.back.entity.Book;
import uz.pdp.back.model.dto.AddBookDto;
import uz.pdp.back.service.AttachmentService;
import uz.pdp.back.service.BookService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/book")
public class BookController {
    private final BookService bookService;
    private final AttachmentService attachmentService;

    @GetMapping
    public HttpEntity<?> getAllBooks() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("{id}")
    public HttpEntity<?> getBookById(@PathVariable UUID id) {
        Book book = bookService.findById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/author/{authorId}")
    public HttpEntity<?> getAllBooksByAuthorId(@PathVariable UUID authorId) {
        return ResponseEntity.ok(bookService.findByAuthorId(authorId));
    }

    @GetMapping("/genre/{result}")
    public HttpEntity<?> getAllBooksByGenreIdAndAuthorId(@PathVariable String[] result) {
        UUID genreId = UUID.fromString(result[0]);
        UUID authorId = null;
        if (!result[1].equals("null")) {
            authorId = UUID.fromString(result[1]);
        }
        return ResponseEntity.ok(bookService.findByGenreIdAndAuthorId(genreId, authorId));
    }

    @PostMapping
    public HttpEntity<?> saveBook(@RequestParam("file") MultipartFile file, AddBookDto addBookDto) throws IOException {
        byte[] bytes = file.getBytes();
        Book book = bookService.saveFromAddBookDto(bytes, addBookDto);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("{id}")
    public void deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
    }


    @GetMapping("/attachment/{bookId}")
    public void getBookFileByAttachmentId(@PathVariable UUID bookId, HttpServletResponse resp) throws IOException {
        Attachment attachment = attachmentService.findByBookId(bookId);
        byte[] photo = attachment.getData();
        resp.setContentType("image/png");
        resp.getOutputStream().write(photo);
        resp.getOutputStream().flush();
    }

    @GetMapping("/export")
    public ResponseEntity<?> exportBooksToExcel() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        ByteArrayInputStream inputStream = bookService.exportBookToExcel();
        headers.add("Content-Disposition", "attachment; filename=data.xlsx");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(inputStream));
    }

    @PostMapping("/import")
    public HttpEntity<?> importExcelToDb(@RequestParam("file") MultipartFile file) throws IOException {
        bookService.importExcelToDb(file);
        return ResponseEntity.ok("book is successfully saved ");
    }

}
