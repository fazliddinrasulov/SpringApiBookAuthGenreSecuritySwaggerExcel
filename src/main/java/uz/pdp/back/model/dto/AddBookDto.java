package uz.pdp.back.model.dto;

import java.util.UUID;

public record AddBookDto (String title, UUID authorId, UUID genreId) {
}
