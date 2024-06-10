package uz.pdp.back.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.back.entity.Genre;
import uz.pdp.back.model.dto.AuthorDto;
import uz.pdp.back.service.GenreService;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/genre")
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public HttpEntity<?> getAllGenres(@RequestParam(required = false) String[] result) {
        return ResponseEntity.ok(genreService.findAll());
    }

    @PostMapping
    public HttpEntity<?> createAuthor(@RequestBody AuthorDto authorDto) {
        Genre genre = genreService.saveFromString(authorDto.name());
        return ResponseEntity.ok(genre);
    }

}
