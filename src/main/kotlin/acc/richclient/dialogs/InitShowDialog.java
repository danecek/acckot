package acc.richclient.dialogs;

import acc.business.Facade;
import acc.richclient.MainWindow;
import acc.richclient.view.InitsPane;
import acc.util.AccException;
import acc.util.Messages;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;

public class InitShowDialog extends AbstractDialog {

    private AccountCB accCB;

    public InitShowDialog() throws AccException {
        super(Messages.Zobraz_pocatecni_stavy.cm());
    }

    @Override
    protected Node createContent() {
        GridPane gp = genGP();
        int row = 0;
        gp.add(new Label(Messages.pro_ucet.cm() + DEL), 0, row);
        gp.add(accCB = new AccountCB(), 1, row);
        try {
            accCB.getItems().addAll(Facade.instance.getBalanceAccounts());
        } catch (AccException ex) {
            MainWindow.showException(ex);
        }
        return gp;
    }

    @Override
    public void ok() throws AccException {        
        Tab t = new Tab(Messages.Pocatecni_stavy.cm(),
                new InitsPane(accCB.getOptAccount()));
        MainWindow.getInstance().addTab(t);
    }

    @Override
    public void validate() {
    }
}
