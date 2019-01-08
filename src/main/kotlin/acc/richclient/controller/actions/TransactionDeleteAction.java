package acc.richclient.controller.actions;

import acc.richclient.dialogs.TransactionDeleteDialog;
import acc.richclient.MainWindow;
import acc.util.AccException;
import acc.util.Messages;

public class TransactionDeleteAction extends AbstrAction {

    public static final TransactionDeleteAction instance = new TransactionDeleteAction();

    private TransactionDeleteAction() {
        super(Messages.Zrus_transakci.cm());
    }

    @Override
    public void execute() {
        MainWindow.getInstance().getSelectedTransactionPane()
                .ifPresent((tp) -> {
                    tp.getSelected()
                            .ifPresent((t) -> {
                                try {
                                    new TransactionDeleteDialog(t).execute();
                                } catch (AccException ex) {
                                    MainWindow.getInstance().showException(ex);
                                }
                            });
                });
    }

}
