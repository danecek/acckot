package acc.richclient.controller.actions;

import acc.richclient.MainWindow;
import acc.richclient.dialogs.TransactionUpdateDialog;
import acc.util.AccException;
import acc.util.Messages;

public class UpdateTransactionAction extends AbstrAction {

    public static final UpdateTransactionAction instance = new UpdateTransactionAction();

    private UpdateTransactionAction() {
        super(Messages.Zmen_transakci.cm());
    }

    @Override
    public void execute() {
        MainWindow.getInstance().getSelectedTransactionPane()
                .ifPresent((tp) -> {
                    tp.getSelected()
                            .ifPresent((t) -> {
                                try {
                                    new TransactionUpdateDialog(t).execute();
                                } catch (AccException ex) {
                                    MainWindow.getInstance().showException(ex);
                                }
                            });
                });
    }

}
