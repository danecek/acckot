package acc.richclient.controller.actions;

import acc.richclient.view.DocumentPane;
import acc.util.Messages;

public class DocumentsShowAction extends AbstrAction {

    public static final DocumentsShowAction instance = new DocumentsShowAction();

    private DocumentsShowAction() {
        super(Messages.Zobraz_doklady.cm());
    }

    @Override
    public void execute() {
        new DocumentPane().addToMain();
    }

}
