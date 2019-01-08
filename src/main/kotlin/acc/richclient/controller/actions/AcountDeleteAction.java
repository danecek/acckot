package acc.richclient.controller.actions;

import acc.richclient.MainWindow;
import acc.richclient.dialogs.AccDeleteDialog;
import acc.richclient.view.AccountsPane;
import acc.util.AccException;
import acc.util.Messages;

public class AcountDeleteAction extends AbstrAction {

    public static final AcountDeleteAction instance = new AcountDeleteAction();

    private AcountDeleteAction() {
        super(Messages.Zrus_ucet.cm());
    }

    @Override
    public void execute() {
        MainWindow.getInstance().getAccountPane()
                .ifPresent((AccountsPane ap) -> {
                    ap.selected()
                            .ifPresent((ap1) -> {
                                try {
                                    new AccDeleteDialog(ap1).execute();
                                } catch (AccException ex) {
                                    MainWindow.getInstance().showException(ex);
                                }

                            });
                });
    }

}
