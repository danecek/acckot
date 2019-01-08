package acc.richclient.controller.menus;

import acc.richclient.controller.actions.InitCreateAction;
import acc.richclient.controller.actions.TransactionCreateAction;
import acc.richclient.controller.actions.TransactionDeleteAction;
import acc.richclient.controller.actions.InitsShowAction;
import acc.richclient.controller.actions.TransactionsShowAction;
import acc.richclient.controller.actions.UpdateTransactionAction;
import acc.util.Messages;
import javafx.scene.control.Menu;
import javafx.scene.control.SeparatorMenuItem;

public class TransactionMenu extends Menu {

    public TransactionMenu() {
        super(Messages.Transakce.cm(), null,
                TransactionsShowAction.instance.createMenuItem(),
                TransactionCreateAction.instance.createMenuItem(),
                UpdateTransactionAction.instance.createMenuItem(),
                TransactionDeleteAction.instance.createMenuItem(),
                new SeparatorMenuItem(),
                InitsShowAction.instance.createMenuItem(),
                InitCreateAction.instance.createMenuItem()
        );
    }

}
