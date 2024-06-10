package uz.pdp.back.service;

import org.springframework.stereotype.Service;
import uz.pdp.back.entity.Attachment;

import java.util.UUID;

@Service
public interface AttachmentService {
    Attachment save(Attachment attachment);

    Attachment findByBookId(UUID bookId);

}
