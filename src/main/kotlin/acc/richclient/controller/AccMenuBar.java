package acc.richclient.controller;

import acc.richclient.controller.menus.VystupMenu;
import acc.richclient.controller.menus.TransactionMenu;
import acc.richclient.controller.menus.PrintMenu;
import acc.richclient.controller.menus.FileMenu;
import acc.richclient.controller.menus.AccountMenu;
import acc.richclient.controller.menus.DocumentMenu;
import javafx.scene.control.MenuBar;

public class AccMenuBar extends MenuBar {

    public AccMenuBar() {
        super(new FileMenu(),
                new AccountMenu(),
                new DocumentMenu(),
                new TransactionMenu(),
                new PrintMenu(),
                new VystupMenu());
    }

}
