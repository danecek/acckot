package acc.richclient.controller.actions;

import acc.richclient.MainWindow;
import acc.richclient.dialogs.CreateInitDialog;
import acc.util.AccException;
import acc.util.Messages;

public class InitCreateAction extends AbstrAction {

    public static final InitCreateAction instance = new InitCreateAction();

    private InitCreateAction() {
        super(Messages.Nastav_pocatecni_stav.cm());
    }

    @Override
    public void execute() {
        try {
            new CreateInitDialog().execute();
        } catch (AccException ex) {
            MainWindow.getInstance().showException(ex);
        }
    }

}
