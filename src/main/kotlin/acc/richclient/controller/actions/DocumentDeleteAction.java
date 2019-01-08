package acc.richclient.controller.actions;

import acc.richclient.MainWindow;
import acc.richclient.dialogs.DocumentDeleteDialog;
import acc.richclient.view.DocumentPane;
import acc.util.Messages;

public class DocumentDeleteAction extends AbstrAction {

    public static final DocumentDeleteAction instance = new DocumentDeleteAction();

    private DocumentDeleteAction() {
        super(Messages.Zrus_doklad.cm());
    }

    @Override
    public void execute() {
        MainWindow.instance.getSelectedTab(Messages.Doklady.cm())
                .ifPresent(ap -> {
                    ((DocumentPane) ap).getSelected()
                            .ifPresent(d -> new DocumentDeleteDialog(d).execute());
                });

    }

}
