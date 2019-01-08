/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acc.richclient.view;

import acc.business.Global;
import acc.model.Document;
import acc.model.Transaction;
import acc.richclient.dialogs.AbstractDialog;
import java.time.LocalDate;
import java.util.Optional;

import acc.util.AccException;
import acc.util.Messages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public abstract class TransactionsAbstractPane extends AbstrPane {

    public static class TransactionP extends Transaction {

        public TransactionP(Transaction t) {
            super(t.getId(), t.getAmount(), t.getMaDati(),
                    t.getDal(), t.getDocument(), t.getRelatedDocument());

        }

        public String getMaDatiNumber() {
            return maDati.getNumber();
        }

        public String getMaDatiName() {
            return maDati.getName();
        }

        public String getDalNumber() {
            return dal.getNumber();
        }

        public String getDalName() {
            return dal.getName();
        }

        public String getDocumentName() {
            return document.isPresent() ? document.get().getName():"";
        }

        public String getRelatedDocumentName() {
            return relatedDocument.isPresent() ? relatedDocument.get().getName():"";
        }
    }

    protected static String title(Optional<LocalDate> oD, Optional<LocalDate> dO,
            Optional<Document> document, Optional<Document> relatedDocument) {
        String SP = "  ";
        StringBuilder sb = new StringBuilder(Messages.Transakce.cm() + AbstractDialog.DEL);
        if (oD.isPresent()) {
            sb.append(Messages.Od.cm()).append(AbstractDialog.DEL).append(oD.get().format(Global.instance.df()));
        }
        if (dO.isPresent()) {
            sb.append(SP).append(Messages.Do.cm()).append(AbstractDialog.DEL).append(dO.get().format(Global.instance.df()));
        }
        if (document.isPresent()) {
            sb.append(SP).append(Messages.pro_doklad.cm())
                    .append(AbstractDialog.DEL)
                    .append(document.get().getName());
        }
        if (document.isPresent()) {
            sb.append(SP).append(Messages.pro_souvisejici_doklad.cm())
                    .append(AbstractDialog.DEL).append(document.get().getName());
        }
        return sb.toString();
    }

    class TransactionColumn extends PaneColumn {

        public TransactionColumn(String text, int prefWidth, TransactionColumn... cols) {
            super(text, prefWidth, cols);
        }

        public TransactionColumn(String text, TransactionColumn... cols) {
            super(text, cols);
        }

        public TransactionColumn(String text, String field) {
            super(text);
            setCellValueFactory(new PropertyValueFactory<Transaction, String>(field));

        }

    }

    protected final ObservableList<Transaction> transactions = FXCollections.observableArrayList();
    protected TableView<Transaction> tableView;
    protected Optional<LocalDate> optOd = Optional.empty();
    protected Optional<LocalDate> optDo = Optional.empty();
    protected Optional<Document> optDocument = Optional.empty();
    protected Optional<Document> optRelatedDocument = Optional.empty();

    public TransactionsAbstractPane() throws AccException {
        super(Messages.Transakce.cm());
    }

    public Optional<Transaction> getSelected() {
        return Optional.ofNullable(tableView.getSelectionModel().getSelectedItem());
    }


}
