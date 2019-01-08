package acc.richclient.controller.actions;

import acc.richclient.MainWindow;
import acc.richclient.view.AccountsPane;
import acc.util.Messages;
import javafx.scene.control.Tab;

public class AccountsShowAction extends AbstrAction {

    public static final AccountsShowAction instance = new AccountsShowAction();

    private AccountsShowAction() {
        super(Messages.Zobraz_ucty.cm());
    }

    @Override
    public void execute() {
        Tab t = new Tab(Messages.Ucty.cm(),
                new AccountsPane());
        MainWindow.getInstance().addTab(t);
    }

}
