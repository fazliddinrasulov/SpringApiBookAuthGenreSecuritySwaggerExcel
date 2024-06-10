package uz.pdp.back.model.dto;

import java.util.UUID;

public record BookRespDto (UUID id, String title,UUID attachmentId, UUID authorId){
}
