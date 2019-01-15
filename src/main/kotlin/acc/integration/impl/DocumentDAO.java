package acc.integration.impl;

import acc.model.Document;
import acc.model.DocumentId;
import acc.model.DocumentType;
import acc.util.AccException;
import java.time.LocalDate;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public final class DocumentDAO {

    public static final DocumentDAO instance = new DocumentDAO();

    static int keyC = 1;

    private final NavigableMap<DocumentId, acc.model.Document> documentMapByID = new TreeMap<>();

    private DocumentDAO() {
        create(DocumentType.INVOICE, "F1", LocalDate.now(), "bla");
        create(DocumentType.BANK_STATEMENT, "V1", LocalDate.now(), "bla");
    }

    public void create(DocumentType type, String name, LocalDate date,
            String description) {
        Document d = new Document(type, name, date,
                description);
        documentMapByID.put(d.getId(), d);
    }

    public void update(DocumentId id, LocalDate date,
            String description) {
        Document d = new Document(id.getType(), id.getName(), date, description);
        documentMapByID.replace(d.getId(), d);
    }

    public List<Document> getAll() throws AccException {
        return documentMapByID.values().stream().collect(Collectors.toList());
    }

    public List<Document> getByRegexp(String regexp) throws AccException {
        return documentMapByID.values().stream()
                .filter(d -> d.getNumber().matches(regexp))
                .collect(Collectors.toList());
    }

    public Document getById(DocumentId id) throws AccException {
        return documentMapByID.values().stream()
                .filter((d) -> d.getId().equals(id)).findFirst().get();
    }

    public void delete(DocumentId id) throws AccException {
        documentMapByID.remove(id);
    }

}
