package acc.richclient.dialogs;

import acc.business.Facade;
import acc.model.AnalAcc;
import acc.model.Document;
import acc.model.Transaction;
import acc.richclient.MainWindow;

import java.util.List;
import java.util.Optional;

import acc.util.AccException;
import acc.util.Messages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public abstract class TransactionAbstractDialog extends AbstractDialog {

  //  protected DatePicker datePicker;
    protected TextField amountTF;
    protected AccountCB madatiCB;
    protected AccountCB dalCB;
    protected DocumentCB documentCB;
    protected DocumentCB bindingDocumentCB;

    void set(Transaction t) {
        amountTF.setText(Long.toString(t.getAmount()));
        madatiCB.setValue(t.getMaDati());
        dalCB.setValue(t.getDal());
        t.getDocument().ifPresent(doc -> {
            documentCB.setValue(doc);
        });
        t.getRelatedDocument().ifPresent(doc -> {
            bindingDocumentCB.setValue(doc);
        });
    }

    void init() {
   ///     datePicker.setValue(LocalDate.now());
        ObservableList<AnalAcc> accounts;
        try {
            accounts = FXCollections.observableArrayList(Facade.instance.getAllAccounts());
            madatiCB.getItems().addAll(accounts);
            dalCB.getItems().addAll(accounts);
        } catch (AccException ex) {
            MainWindow.showException(ex);
        }
        List<Document> documents;
        try {
            documents = Facade.instance.getAllDocuments();
            bindingDocumentCB.setItems(FXCollections.observableArrayList(documents));
            documentCB.setItems(FXCollections.observableArrayList(documents));
        } catch (AccException ex) {
            MainWindow.showException(ex);
        }

    }

    public TransactionAbstractDialog(String title) {
        super(title);
    }

    @Override
    protected Node createContent() {
    //    datePicker = new AccDatePicker(LocalDate.ofYearDay(Global.instance.getYear(), 1));
        amountTF = new TextField();
        dalCB = new AccountCB();
        madatiCB = new AccountCB();
        documentCB = new DocumentCB();
        bindingDocumentCB = new DocumentCB();
      //  datePicker.valueProperty().addListener(this);
        madatiCB.valueProperty().addListener(this);
        dalCB.valueProperty().addListener(this);
        amountTF.textProperty().addListener(this);
        GridPane gp = genGP();

        int row = 0;
//        gp.add(new Label(Messages.Datum.cm() + DEL), 0, row);
//        gp.add(datePicker, 1, row);
//        row++;
        gp.add(new Label(Messages.Castka.cm()), 0, row);
        gp.add(amountTF, 1, row);
        row++;
        gp.add(new Label(Messages.Ma_dati.cm() + DEL), 0, row);
        gp.add(madatiCB, 1, row);
        row++;
        gp.add(new Label(Messages.Dal.cm() + DEL), 0, row);
        gp.add(dalCB, 1, row);
        row++;
        gp.add(new Label(Messages.Doklad.cm()), 0, row);
        gp.add(documentCB, 1, row);
        row++;
        gp.add(new Label(Messages.Parovy_doklad.cm()), 0, row);
        gp.add(bindingDocumentCB, 1, row);
        return gp;
    }

    protected Optional<String> err() {
//        if (datePicker.getValue().getYear() != Global.instance.getYear()) {
//            return Optional.of(Messages.rok_musi_byt_letosni.cm());
//        }
        try {
            Long.parseLong(amountTF.getText());
        } catch (NumberFormatException ex) {
            return Optional.of(Messages.neplatna_castka.cm());
        }
        if (madatiCB.getValue() == null) {
            return Optional.of(Messages.Ma_dati.cm());
        }
        if (dalCB.getValue() == null) {
            return Optional.of(Messages.Dal.cm());
        }
        return Optional.empty();
    }

    @Override
    public void validate() {
        errorPane.setError(err());
    }

}
