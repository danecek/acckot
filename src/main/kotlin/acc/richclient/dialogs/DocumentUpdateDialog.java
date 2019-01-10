package acc.richclient.dialogs;

import acc.business.Facade;
import acc.model.Document;
import acc.richclient.MainWindow;
import acc.util.AccException;
import acc.util.Messages;

public class DocumentUpdateDialog extends DocumentAbstractDialog {

    Document d;

    public DocumentUpdateDialog(Document d) {
        super(Messages.Zmen_doklad.cm());
        this.d = d;
        registerFields();
        setFields(d);
    }

    @Override
    public void ok() throws AccException {
        Facade.INSTANCE.updateDocument(
                d.getId(),
                docTypeCB.getValue(),
                nameTF.getText(),
                date.getValue(),
                dscTF.getText());
        MainWindow.instance.refreshDocumentPanes();
    }

    @Override
    public void validate() {
    }

}
