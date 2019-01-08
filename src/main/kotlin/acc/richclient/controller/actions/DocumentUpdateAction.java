package acc.richclient.controller.actions;

import acc.richclient.MainWindow;
import acc.richclient.dialogs.DocumentUpdateDialog;
import acc.richclient.view.AbstrPane;
import acc.richclient.view.DocumentPane;
import acc.richclient.view.DocumentPane.DocumentP;
import acc.util.Messages;
import java.util.Optional;

public class DocumentUpdateAction extends AbstrAction {

    public static final DocumentUpdateAction instance = new DocumentUpdateAction();

    private DocumentUpdateAction() {
        super(Messages.Zmen_doklad.cm());
    }

    @Override
    public void execute() {
        Optional<AbstrPane> oap = MainWindow.instance.getSelectedTab(Messages.Doklady.cm());
        oap.ifPresent(ap -> {
            Optional<DocumentP> dp = ((DocumentPane) ap).getSelected();
            dp.ifPresent(d -> new DocumentUpdateDialog(d).execute());
        });
    }

}
