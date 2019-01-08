package acc.richclient.dialogs;

import acc.model.DocumentType;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class DocTypeCB extends ComboBox<DocumentType> {

    public DocTypeCB() {
        super(FXCollections.observableArrayList(DocumentType.values()));
        setValue(DocumentType.ELSE);
        setConverter(new StringConverter<DocumentType>() {
            @Override
            public String toString(DocumentType docType) {
               return docType.getText();
            }

            @Override
            public DocumentType fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

}
