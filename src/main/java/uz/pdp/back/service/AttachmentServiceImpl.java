package uz.pdp.back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.back.entity.Attachment;
import uz.pdp.back.repo.AttachmentRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;

    @Override
    public Attachment save(Attachment attachment) {
        return attachmentRepository.save(attachment);
    }

    @Override
    public Attachment findByBookId(UUID bookId) {
        return attachmentRepository.findByBookId(bookId);
    }

}
