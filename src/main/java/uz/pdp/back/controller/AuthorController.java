package uz.pdp.back.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.back.entity.Author;
import uz.pdp.back.model.dto.AuthorDto;
import uz.pdp.back.service.AuthorService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/author")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public HttpEntity<?> getAllAuthors() {
        return ResponseEntity.ok(authorService.findAll());
    }

    @PostMapping
    public HttpEntity<?> createAuthor(@RequestBody AuthorDto authorDto) {
        Author author = authorService.saveFromString(authorDto.name());
        return ResponseEntity.ok(author);
    }
}
