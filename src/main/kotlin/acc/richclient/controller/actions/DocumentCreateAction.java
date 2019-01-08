package acc.richclient.controller.actions;

import acc.richclient.dialogs.DocumentCreateDialog;
import acc.util.Messages;

public class DocumentCreateAction extends AbstrAction {

    public static final DocumentCreateAction instance = new DocumentCreateAction();

    private DocumentCreateAction() {
        super(Messages.Vytvor_doklad.cm());
    }

    @Override
    public void execute() {
        new DocumentCreateDialog().execute();
    }

}
