package acc.integration;

import acc.integration.impl.TransactionDAODefault;
import acc.model.AnalAcc;
import acc.model.Document;
import acc.model.Transaction;
import acc.model.TransactionId;
import acc.util.AccException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public abstract class TransactionDAO {

    public static TransactionDAO instance = new TransactionDAODefault();

    public abstract void create(long amount, AnalAcc madati,
            AnalAcc dal, Optional<Document> document, Optional<Document> bindingDocument) throws AccException;

    public abstract void update(TransactionId id, long amount,
            AnalAcc madati, AnalAcc dal, Optional<Document> document,
            Optional<Document> bindingDocument) throws AccException;

    public abstract void delete(TransactionId id) throws AccException;

    public abstract List<Transaction> getAll() throws AccException;

    public abstract List<Transaction> get(Optional<LocalDate> optOd, Optional<LocalDate> optDo,
            Optional<AnalAcc> acc, Optional<Document> optDocument, Optional<Document> optRelatedDocument) throws AccException;

    public abstract List<Transaction> getInits(Optional<AnalAcc> acc) throws AccException;
}
