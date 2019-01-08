/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acc.richclient.dialogs;

import static acc.richclient.dialogs.AbstractDialog.monthFormater;
import java.time.Month;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class MonthCB extends ComboBox<Month> {

    public MonthCB(ObservableList<Month> items) {
        super(FXCollections.observableArrayList(Month.values()));
        setConverter(new StringConverter<Month>() {

            @Override
            public String toString(Month object) {
                return monthFormater.format(object);
            }

            @Override
            public Month fromString(String string) {
                return (Month) monthFormater.parse(string);
            }
        });
        setValue(Month.DECEMBER);

    }
}
