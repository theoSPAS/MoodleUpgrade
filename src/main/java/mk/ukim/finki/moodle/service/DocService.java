package mk.ukim.finki.moodle.service;

import mk.ukim.finki.moodle.model.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface DocService {

    Optional<Document> getFile(Long id);

    List<Document> getFiles();

    Document findById(Long id);

    Document saveFile(MultipartFile file);
}
