package acc.richclient.controller.menus;

import acc.richclient.controller.actions.BalanceCreateAction;
import acc.util.Messages;
import javafx.scene.control.Menu;

public class VystupMenu extends Menu {

    public VystupMenu() {
        super(Messages.Vystupy.cm(), null,
                BalanceCreateAction.instance.createMenuItem()
        );
    }

}
