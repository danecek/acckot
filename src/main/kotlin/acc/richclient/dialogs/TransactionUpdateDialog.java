package acc.richclient.dialogs;

import acc.business.Facade;
import acc.model.Transaction;
import acc.richclient.MainWindow;
import acc.util.AccException;
import acc.util.Messages;

import java.util.Optional;

public class TransactionUpdateDialog extends TransactionAbstractDialog {

    private Transaction t;

    public TransactionUpdateDialog(Transaction t) throws AccException {
        super(Messages.Zmen_transakci.cm());
      this.t = t;
        init();
        set(t);
//        datePicker.setValue(t.getDate().get());
//        amountTF.setText(Long.toString(t.getAmount()));
//        ObservableList<AnalAcc> accounts = 
//                FXCollections.observableArrayList(Facade.instance.getAllAccounts());
//        madatiCB.getItems().addAll(accounts);
//        madatiCB.setValue(t.getMaDati());
//        dalCB.getItems().addAll(accounts);
//        dalCB.setValue(t.getDal());
//        documentCB.setValue(t.getDocument().get());
//        documentCB.setEditable(true);
//        bindingDocumentCB.setValue(t.getBindingDocument().get());
//        bindingDocumentCB.setEditable(true);
//        List<Document> documents = Facade.instance.getAllDocuments();
//        bindingDocumentCB.setItems(FXCollections.observableArrayList(documents));
    }

    @Override
    public void ok() throws AccException {
        try {
            Facade.INSTANCE.updateTransaction(t.getId(),
                    //datePicker.getValue(),
                    Long.parseLong(amountTF.getText()),
                    madatiCB.getValue(), dalCB.getValue(),
                    Optional.ofNullable(documentCB.getValue()),
                    Optional.ofNullable(bindingDocumentCB.getValue()));
            MainWindow.getInstance().refreshTransactionPanes();
        } catch (AccException ex) {
            MainWindow.getInstance().showException(ex);
        }
    }

}
