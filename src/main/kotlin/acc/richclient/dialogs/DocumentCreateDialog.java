package acc.richclient.dialogs;

import acc.business.Facade;
import acc.richclient.MainWindow;
import acc.util.AccException;
import acc.util.Messages;

public class DocumentCreateDialog extends DocumentAbstractDialog {

    public DocumentCreateDialog() {
        super(Messages.Vytvor_doklad.cm());
        registerFields();
    }

    @Override
    public void ok() throws AccException {
        Facade.INSTANCE.createDocument(
                docTypeCB.getValue(),
                nameTF.getText(),
                date.getValue(),
                dscTF.getText());
        MainWindow.instance.refreshDocumentPanes();
    }

    @Override
    public void validate() {
        // setError(message);
    }

}
