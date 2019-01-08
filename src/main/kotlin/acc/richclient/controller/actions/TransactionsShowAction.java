package acc.richclient.controller.actions;

import acc.richclient.MainWindow;
import acc.richclient.dialogs.TransactionsShowDialog;
import acc.util.AccException;
import acc.util.Messages;

public class TransactionsShowAction extends AbstrAction {

    public static final TransactionsShowAction instance = new TransactionsShowAction();

    private TransactionsShowAction() {
        super(Messages.Zobraz_transakce.cm());
    }

    @Override
    public void execute() {
        try {
            new TransactionsShowDialog().execute();
        } catch (AccException ex) {
            MainWindow.getInstance().showException(ex);
        }
    }

}
