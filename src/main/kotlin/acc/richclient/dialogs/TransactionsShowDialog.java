/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acc.richclient.dialogs;

import acc.business.AccDatePicker;
import acc.business.Facade;
import acc.richclient.MainWindow;
import acc.richclient.view.TransactionsPane;
import acc.util.AccException;
import acc.util.Messages;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class TransactionsShowDialog extends AbstractDialog {

    private AccDatePicker fromDp;
    private AccDatePicker toDp;
    private DocumentCB documentCB;
    private DocumentCB relatedDocumentCB;
    TextField commentRegexp;

    public TransactionsShowDialog() throws AccException {
        super(Messages.Zobraz_transakce.cm());
    }

    @Override
    protected Node createContent() {

        GridPane gp = genGP();
        int row = 0;
        gp.add(new Label(Messages.Od.cm() + DEL), 0, row);
        gp.add(fromDp = new AccDatePicker(), 1, row);
        row++;
        gp.add(new Label(Messages.Do.cm() + DEL), 0, row);
        gp.add(toDp = new AccDatePicker(), 1, row);
        row++;
        gp.add(new Label(Messages.Pridruzeny_doklad.cm() + DEL), 0, row);
        try {
            gp.add(documentCB = new DocumentCB(Facade.instance.getAllDocuments()), 1, row);
        } catch (AccException ex) {
            MainWindow.showException(ex);
        }
        row++;
        gp.add(new Label(Messages.Souvisejici_doklad.cm() + DEL), 0, row);
        try {
            gp.add(relatedDocumentCB = new DocumentCB(Facade.instance.getAllDocuments()), 1, row);
        } catch (AccException ex) {
            MainWindow.showException(ex);
        }
        return gp;
    }

    @Override
    public void ok() throws AccException {
        TransactionsPane tp
                = new TransactionsPane(fromDp.getOptDate(),
                        toDp.getOptDate(),
                        documentCB.getOptDocument(),
                        relatedDocumentCB.getOptDocument());
        tp.addToMain();
    }

    @Override
    public void validate() {
    }

}
