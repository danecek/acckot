package acc.richclient.controller.menus;

import acc.richclient.controller.actions.PrintBalanceAction;
import acc.util.Messages;
import javafx.scene.control.Menu;

public class PrintMenu extends Menu {

    public PrintMenu() {
        super(Messages.Tisk.cm(), null,
                PrintBalanceAction.instance.createMenuItem()
        );
    }

}
