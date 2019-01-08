package acc.richclient.controller.menus;

import acc.richclient.controller.actions.ExitAction;
import acc.util.Messages;
import javafx.scene.control.Menu;

public class FileMenu extends Menu {

    public FileMenu() {
        super(Messages.File.cm(), null,
             ExitAction.instance.createMenuItem()
             );
    }

}
