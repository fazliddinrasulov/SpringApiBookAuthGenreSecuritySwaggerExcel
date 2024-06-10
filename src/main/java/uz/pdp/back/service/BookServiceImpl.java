package uz.pdp.back.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.back.entity.Attachment;
import uz.pdp.back.entity.Author;
import uz.pdp.back.entity.Book;
import uz.pdp.back.entity.Genre;
import uz.pdp.back.model.projection.BookForExcelProjection;
import uz.pdp.back.model.dto.AddBookDto;
import uz.pdp.back.repo.BookRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final AttachmentService attachmentService;

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAllByOrderByCreatedAt();
    }

    @Override
    public List<Book> findByAuthorId(UUID authorId) {
        return bookRepository.findByAuthorId(authorId);
    }

    @Override
    public List<Book> findByGenreIdAndAuthorId(UUID genreId, UUID authorId) {
        if (authorId == null) {
            return bookRepository.findByGenreId(genreId);

        } else {
            return bookRepository.findByAuthorIdAndGenreId(authorId, genreId);
        }
    }

    @Override
    public Book saveFromAddBookDto(byte[] bytes, AddBookDto addBookDto) {
        Book book = bookRepository.save(Book.builder()
                .title(addBookDto.title())
                .genre(genreService.findById(addBookDto.genreId()))
                .author(authorService.findById(addBookDto.authorId()))
                .build());
        attachmentService.save(Attachment.builder()
                .book(book)
                .data(bytes)
                .build());
        return book;
    }

    @Override
    public Book findById(UUID id) {
        return bookRepository.findById(id).get();
    }

    @Override
    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }

    @SneakyThrows
    @Override
    public ByteArrayInputStream exportBookToExcel() {
        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            List<BookForExcelProjection> books = bookRepository.findAllBookAsExcel();
            //name of excel
            Sheet sheet = workbook.createSheet("Books");
            Row headerRow = sheet.createRow(0);

            //columns
            String[] columns = {"ID", "AuthorId", "GenreId", "Title", "AuthorName", "GenreName"};

            //Header style
            CellStyle headerCellStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerCellStyle.setFont(headerFont);
            for (int col = 0; col < columns.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
                cell.setCellStyle(headerCellStyle);
            }

            // Populate data rows
            int rowIdx = 1;
            for (BookForExcelProjection item : books) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(item.getId());
                row.createCell(1).setCellValue(item.getAuthorId());
                row.createCell(2).setCellValue(item.getGenreId());
                row.createCell(3).setCellValue(item.getTitle());
                row.createCell(4).setCellValue(item.getAuthor());
                row.createCell(5).setCellValue(item.getGenre());
            }

            // Resize columns to fit content
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            // Write workbook to output stream
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    @SneakyThrows
    @Override
    public void importExcelToDb(MultipartFile file) {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {

            if(row.getCell(0).getStringCellValue().equals("ID")){
                continue;
            }
            Book book = findById(UUID.fromString(row.getCell(0).getStringCellValue()));
            Author author = authorService.findById(UUID.fromString(row.getCell(1).getStringCellValue()));
            Genre genre = genreService.findById(UUID.fromString(row.getCell(2).getStringCellValue()));
            book.setTitle(row.getCell(3).getStringCellValue());
            author.setName(row.getCell(4).getStringCellValue());
            genre.setName(row.getCell(5).getStringCellValue());
            book.setAuthor(author);
            book.setGenre(genre);
            save(book);
        }
        workbook.close();
    }
}
