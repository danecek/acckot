package acc.richclient.dialogs;

import acc.business.Facade;
import acc.model.Transaction;
import acc.model.TransactionId;
import acc.richclient.MainWindow;
import acc.richclient.views.PaneTabs;
import acc.util.AccException;
import acc.util.Messages;

import java.util.Optional;

public class TransactionDeleteDialog extends TransactionAbstractDialog {

    TransactionId id;

    public TransactionDeleteDialog(Transaction t) throws AccException {
        super(Messages.Zrus_transakci.cm());
        id = t.getId();
        set(t);
        //       datePicker.setDisable(true);
        amountTF.setDisable(true);
        madatiCB.setDisable(true);
        dalCB.setDisable(true);
        documentCB.setDisable(true);
        bindingDocumentCB.setDisable(true);
    }

    @Override
    protected Optional<String> err() {
        return Optional.empty();
    }

    @Override
    public void ok() throws AccException {
        try {
            Facade.INSTANCE.deleteTransaction(id);
            PaneTabs.Companion.refreshTransactionPanes();
        } catch (AccException ex) {
            MainWindow.getInstance().showException(ex);
        }
    }

}
