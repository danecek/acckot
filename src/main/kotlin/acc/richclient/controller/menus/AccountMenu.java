package acc.richclient.controller.menus;

import acc.richclient.controller.actions.AcountDeleteAction;
import acc.richclient.controller.actions.AcountCreateAction;
import acc.richclient.controller.actions.AccountsShowAction;
import acc.util.Messages;
import javafx.scene.control.Menu;

public class AccountMenu extends Menu {

    public AccountMenu() {
        super(Messages.Ucty.cm(), null,
                AccountsShowAction.instance.createMenuItem(),
                AcountCreateAction.instance.createMenuItem(),
                AcountDeleteAction.instance.createMenuItem()
        );
    }

}
