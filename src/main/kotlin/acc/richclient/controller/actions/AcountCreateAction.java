package acc.richclient.controller.actions;

import acc.richclient.dialogs.AccountCreateDialog;
import acc.richclient.MainWindow;
import acc.util.AccException;
import acc.util.Messages;

public class AcountCreateAction extends AbstrAction {

    public static final AcountCreateAction instance = new AcountCreateAction();

    private AcountCreateAction() {
        super(Messages.Vytvor_ucet.cm());
    }

    @Override
    public void execute() {
        try {
            new AccountCreateDialog().execute();
        } catch (AccException ex) {
            MainWindow.getInstance().showException(ex);
        }
    }

}
