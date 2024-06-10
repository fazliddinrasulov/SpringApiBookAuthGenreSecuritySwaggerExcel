package uz.pdp.back.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.back.entity.Attachment;

import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    Attachment findByBookId(UUID bookId);
}