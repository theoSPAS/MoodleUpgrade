package mk.ukim.finki.moodle.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="documents", schema = "third")
@Embeddable
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String docName;

    private String docType;

    @Lob
    private byte[] data;


    public Document(){}

    public Document(String docName, String docType, byte[] data) {
        this.docName = docName;
        this.docType = docType;
        this.data = data;
    }
}
