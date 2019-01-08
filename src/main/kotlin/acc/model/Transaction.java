package acc.model;

import java.time.LocalDate;
import java.util.Optional;
import acc.util.Global;

public class Transaction {

    public Transaction(TransactionId id, long amount,
            AnalAcc maDati, AnalAcc dal, Optional<Document> document,
            Optional<Document> bindingDocument) {
        this.id = id;
        this.amount = amount;
        this.maDati = maDati;
        this.dal = dal;
        this.document = document;
        this.relatedDocument = bindingDocument;
    }

    public final TransactionId id;
    public final long amount;
    public final AnalAcc maDati;
    protected final AnalAcc dal;
    protected final Optional<Document> document;
    protected final Optional<Document> relatedDocument;

    /**
     * @return the id
     */
    public TransactionId getId() {
        return id;
    }

    /**
     * @return the date
     */
    public Optional<LocalDate> getDate() {
        if (!document.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(document.get().getDate());
    }

    public String getDateText() {
        if (!document.isPresent()) {
            return "";
        }
        return document.get().getDate().format(Global.instance.df());
    }

    /**
     * @return the amount
     */
    public long getAmount() {
        return amount;
    }

    /**
     * @return the maDati
     */
    public AnalAcc getMaDati() {
        return maDati;
    }

    /**
     * @return the dal
     */
    public AnalAcc getDal() {
        return dal;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Transaction)) {
            return false;
        }
        Transaction a = (Transaction) obj;
        return getId().equals(a.getId());

    }

    public Optional<Document> getDocument() {
        return document;
    }

    public Optional<Document> getRelatedDocument() {
        return relatedDocument;
    }

    public boolean isInit() {
        return !document.isPresent();
    }

}
