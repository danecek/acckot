package acc.richclient.controller.actions;

import acc.richclient.dialogs.TransactionCreateDialog;
import acc.util.Messages;

public class TransactionCreateAction extends AbstrAction {

    public static final TransactionCreateAction instance = new TransactionCreateAction();

    private TransactionCreateAction() {
        super(Messages.Vytvor_transakci.cm());
    }

    @Override
    public void execute() {
        new TransactionCreateDialog().execute();
    }

}
