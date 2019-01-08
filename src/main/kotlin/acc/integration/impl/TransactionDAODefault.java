package acc.integration.impl;

import acc.integration.AccountDAO;
import acc.integration.TransactionDAO;
import acc.model.AnalAcc;
import acc.model.Document;
import acc.model.Transaction;
import acc.model.TransactionId;
import acc.util.AccException;

import java.time.LocalDate;
import java.util.List;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class TransactionDAODefault extends TransactionDAO {

    static int keyC = 1;

    private final NavigableMap<TransactionId, Transaction> transactionMap = new TreeMap<>();

    public TransactionDAODefault() {
        try {
            AnalAcc dodavatele = AccountDAO.instance.getByNumber("321001").get();
            AnalAcc fio = AccountDAO.instance.getByNumber("221001").get();
            AnalAcc material = AccountDAO.instance.getByNumber("501001").get();
            Document f1 = DocumentDAO.instance.getByRegexp("F1").get(0);
            Document v1 = DocumentDAO.instance.getByRegexp("V1").get(0);

            // init
            createInitDal(100L, dodavatele);
            createInitMaDati(500L, fio);

            create(100L, material, dodavatele, Optional.of(f1), Optional.empty());
            create(100L, dodavatele, fio, Optional.of(v1), Optional.of(f1));
//            create(Optional.of(LocalDate.of(2019, 1, 6)), 50L, material, dodavatele, "f2", "faktura", "");
        } catch (AccException ex) {
            Logger.getLogger(TransactionDAODefault.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createInitMaDati(long amount, AnalAcc maDati) {
        try {
            create(amount, maDati, AcountDAODefault.instance.getPocatecniUcetRozvazny(), Optional.empty(), Optional.empty());
        } catch (AccException ex) {
            Logger.getLogger(TransactionDAODefault.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createInitDal(long amount, AnalAcc dal) {
        try {
            create(amount, AcountDAODefault.instance.getPocatecniUcetRozvazny(), dal, Optional.empty(), Optional.empty());
        } catch (AccException ex) {
            Logger.getLogger(TransactionDAODefault.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void create(long amount, AnalAcc madati,
            AnalAcc dal, Optional<Document> document, Optional<Document> bindingDocument) throws AccException {
        Transaction r = new Transaction(new TransactionId(keyC++), amount,
                madati, dal, document, bindingDocument);
        transactionMap.put(r.getId(), r);
    }

    @Override
    public List<Transaction> getAll() throws AccException {
        return transactionMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public void delete(TransactionId id) throws AccException {
        transactionMap.remove(id);
    }

    @Override
    public void update(TransactionId id, long amount,
            AnalAcc madati, AnalAcc dal, Optional<Document> document,
            Optional<Document> bindingDocument) throws AccException {
        Transaction r = new Transaction(id, amount, madati,
                dal, document, bindingDocument);
        transactionMap.replace(id, r);
    }

    private static <T> boolean impl(Optional<T> x, Optional<T> y) {
        return !x.isPresent() || x.equals(y);
    }

    @Override
    public List<Transaction> get(Optional<LocalDate> optOd, Optional<LocalDate> optDo,
            Optional<AnalAcc> acc, Optional<Document> optDocument,
            Optional<Document> optRelatedDocument) throws AccException {
        return transactionMap.values().stream()
                .filter(t -> !optOd.isPresent() || t.getDate().isPresent())
                .filter(t -> !optOd.isPresent()
                || !t.getDate().isPresent()
                || t.getDate().get().isAfter(optOd.get().minusDays(1)))
                .filter(t -> !optDo.isPresent()
                || !t.getDate().isPresent()
                || t.getDate().isPresent() && t.getDate().get().isBefore(optDo.get().plusDays(1)))
                .filter(t -> {
                    if (!acc.isPresent()) {
                        return true;
                    }
                    return acc.get().getId().equals(t.getMaDati().getId())
                            || acc.get().getId().equals(t.getDal().getId());
                })
                .filter(t -> impl(optDocument, t.getDocument()))
                .filter(t -> impl(optRelatedDocument, t.getRelatedDocument()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> getInits(Optional<AnalAcc> acc) throws AccException {
        return transactionMap.values().stream()
                .filter((t) -> t.getDate() == null)
                .filter((t) -> {
                    if (!acc.isPresent()) {
                        return true;
                    }
                    return acc.get().getId().equals(t.getMaDati().getId())
                            || acc.get().getId().equals(t.getDal().getId());
                })
                .collect(Collectors.toList());
    }

}
