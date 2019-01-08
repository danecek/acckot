/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acc.richclient.dialogs;

import acc.business.Facade;
import acc.business.MadatiDal;
import acc.richclient.MainWindow;
import acc.util.AccException;
import acc.util.Messages;
import java.util.Optional;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public abstract class InitAbstrDialog extends AbstractDialog {

    protected RadioButton madati;
    protected RadioButton dal;
    protected TextField amountTF;
    protected AccountCB accountCB;
    protected ToggleGroup group;

    public InitAbstrDialog(String title) throws AccException {
        super(title);
    }

    @Override
    protected Node createContent() {
        GridPane gp = genGP();
        int row = 0;
        gp.add(new Label(Messages.Castka.cm()), 0, row);
        gp.add(amountTF = new TextField(), 1, row);
        amountTF.textProperty().addListener(this);
        row++;
        gp.add(new Label(Messages.Ucet.cm() + DEL), 0, row);
        gp.add(accountCB = new AccountCB(), 1, row);

        accountCB.valueProperty().addListener(this);
        row++;
        group = new ToggleGroup();
        gp.add(madati = new RadioButton(Messages.Ma_dati.cm()), 1, row++);
        madati.setUserData(MadatiDal.MA_DATI);
        gp.add(dal = new RadioButton(Messages.Dal.cm()), 1, row);
        dal.setUserData(MadatiDal.DAL);
        group.getToggles().addAll(madati, dal);
        try {
            accountCB.getItems().setAll(Facade.instance.getBalanceAccounts());
        } catch (AccException ex) {
            MainWindow.showException(ex);
        }
        return gp;
    }

    protected Optional<String> err() {
        try {
            Long.parseLong(amountTF.getText());
        } catch (NumberFormatException ex) {
            return Optional.of(Messages.neplatna_castka.cm());
        }
        if (accountCB.getValue() == null) {
            return Optional.of(Messages.Ucet.cm());
        }
        if (group.getSelectedToggle() == null) {
            return Optional.of(Messages.Ma_dati.cm() + "-" + Messages.Dal.cm());
        }
        return Optional.empty();
    }

    @Override
    public void validate() {
        errorPane.setError(err());
    }

    @Override
    public void ok() {
        try {
            Facade.instance.createInit(Long.parseLong(amountTF.getText()),
                    accountCB.getValue(), (MadatiDal) group.getSelectedToggle().getUserData());
            //     MainWindow.getInstance().refreshTransactionPanes();
        } catch (AccException ex) {
            MainWindow.getInstance().showException(ex);
        }
    }

}
