package acc.richclient.dialogs;

import acc.business.Facade;
import acc.model.AnalAcc;
import acc.model.Document;
import acc.model.Osnova;
import acc.richclient.MainWindow;
import acc.util.AccException;
import acc.util.Messages;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionCreateDialog extends TransactionAbstractDialog {

    public TransactionCreateDialog(Document d) {
        this();
        documentCB.setValue(d);
        switch (d.getType()) {
            case INVOICE: {
                try {
                    List<AnalAcc> dodavatele
                            = Facade.instance.getAccountsByClass(Osnova.instance.getTridaZuctovaciVztahy());
                    madatiCB.getItems().setAll(dodavatele);
                    if (dodavatele.size() == 1) {
                        madatiCB.setValue(dodavatele.get(0));
                    }
                    List<AnalAcc> acs = Facade.instance.getAccountsByClass(Osnova.instance.getTridaNaklady());
                    dalCB.getItems().setAll(acs);
                    if (acs.size() == 1) {
                        dalCB.setValue(acs.get(0));
                    }
                } catch (AccException ex) {
                    Logger.getLogger(TransactionCreateDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case BANK_STATEMENT: {
                break;
            }
        }
    }

    public TransactionCreateDialog() {
        super(Messages.Vytvor_transakci.cm());
        init();
        validate();
    }

    @Override
    public void ok() {
        try {
            Facade.instance.createTransaction(
//datePicker.getValue(),
                    Long.parseLong(amountTF.getText()),
                    madatiCB.getValue(), dalCB.getValue(), documentCB.getOptDocument(),
                    bindingDocumentCB.getOptDocument());
            MainWindow.getInstance().refreshTransactionPanes();
        } catch (AccException ex) {
            MainWindow.showException(ex);
        }
    }

}
