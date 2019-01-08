package acc.richclient.controller.menus;

import acc.richclient.controller.actions.DocumentCreateAction;
import acc.richclient.controller.actions.DocumentDeleteAction;
import acc.richclient.controller.actions.DocumentUpdateAction;
import acc.richclient.controller.actions.DocumentsShowAction;
import acc.util.Messages;
import javafx.scene.control.Menu;

public class DocumentMenu extends Menu {

    public DocumentMenu() {
        super(Messages.Doklady.cm(), null,
                DocumentsShowAction.instance.createMenuItem(),
                DocumentCreateAction.instance.createMenuItem(),
                DocumentUpdateAction.instance.createMenuItem(),
                DocumentDeleteAction.instance.createMenuItem()
        );
    }

}
