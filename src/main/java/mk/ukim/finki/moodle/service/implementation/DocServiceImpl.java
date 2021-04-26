package mk.ukim.finki.moodle.service.implementation;

import mk.ukim.finki.moodle.model.Document;
import mk.ukim.finki.moodle.model.exceptions.DocumentNotFoundException;
import mk.ukim.finki.moodle.repository.DocRepository;
import mk.ukim.finki.moodle.service.DocService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class DocServiceImpl implements DocService {

    private final DocRepository docRepository;

    public DocServiceImpl(DocRepository docRepository) {
        this.docRepository = docRepository;
    }

    @Override
    public Optional<Document> getFile(Long id) {
        return this.docRepository.findById(id);
    }

    @Override
    public List<Document> getFiles() {
        return this.docRepository.findAll();
    }

    @Override
    public Document findById(Long id) {
        return this.docRepository.findById(id)
                .orElseThrow(()->new DocumentNotFoundException(id));
    }

    @Override
    public Document saveFile(MultipartFile file) {
        String docName = file.getOriginalFilename();
        try {
            Document doc = new Document(docName,file.getContentType(), file.getBytes());
            return this.docRepository.save(doc);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
