/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acc.richclient.dialogs;

import acc.model.Document;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class DocumentCB extends ComboBox<Document> {

    public DocumentCB(List<Document> documents) {
        this();
        getItems().addAll(FXCollections.observableArrayList(documents));
    }

    public DocumentCB() {
        super();
        setConverter(new StringConverter<Document>() {
            @Override
            public String toString(Document document) {
                if (document == null) {
                    return null;
                }
                return document.getName() + " - " + document.getDescription();
            }

            @Override
            public Document fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    public Optional<Document> getOptDocument() {
        return Optional.ofNullable(getValue());
    }
}
