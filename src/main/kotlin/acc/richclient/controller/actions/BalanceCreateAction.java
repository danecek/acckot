package acc.richclient.controller.actions;

import acc.richclient.dialogs.BalanceCreateDialog;
import acc.richclient.MainWindow;
import acc.util.AccException;
import acc.util.Messages;

public class BalanceCreateAction extends AbstrAction {

    public static final BalanceCreateAction instance = new BalanceCreateAction();

    private BalanceCreateAction() {
        super(Messages.Vytvor_rozvahu.cm());
    }

    @Override
    public void execute() {
        try {
            new BalanceCreateDialog().execute();
        } catch (AccException ex) {
            MainWindow.getInstance().showException(ex);
        }
    }

}
