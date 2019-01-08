package acc.richclient.controller.actions;

import acc.model.Document;
import acc.richclient.dialogs.TransactionCreateDialog;
import acc.richclient.MainWindow;
import acc.util.Messages;

public class TransactionCreateByInvoiceAction extends AbstrAction {

    public static final TransactionCreateByInvoiceAction instance = new TransactionCreateByInvoiceAction();

    private TransactionCreateByInvoiceAction() {
        super(Messages.Vytvor_transakci.cm());
    }

    @Override
    public void execute() {
        MainWindow.instance.getSelectedTab(Messages.Doklady.cm())
                .ifPresent(ap -> {
                    ap.getSelected()
                            .ifPresent(d -> new TransactionCreateDialog((Document)d).execute());
                });
    }

}
