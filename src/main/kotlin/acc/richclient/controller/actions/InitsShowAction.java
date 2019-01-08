package acc.richclient.controller.actions;

import acc.richclient.MainWindow;
import acc.richclient.dialogs.InitShowDialog;
import acc.util.AccException;
import acc.util.Messages;

public class InitsShowAction extends AbstrAction {

    public static final InitsShowAction instance = new InitsShowAction();

    private InitsShowAction() {
        super(Messages.Zobraz_pocatecni_stavy.cm());
    }

    @Override
    public void execute() {
        try {
            new InitShowDialog().execute();
        } catch (AccException ex) {
            MainWindow.getInstance().showException(ex);
        }
    }

}
