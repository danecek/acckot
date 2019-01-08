/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acc.business;

import java.time.LocalDate;
import java.util.Optional;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

public class AccDatePicker extends DatePicker {

    public AccDatePicker(LocalDate localDate) {
        this();
        setValue(localDate);
    }

    public AccDatePicker() {

        setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return Global.instance.df().format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, Global.instance.df());
                } else {
                    return null;
                }
            }
        });
    }

    public Optional<LocalDate> getOptDate() {
        return Optional.ofNullable(getValue());
    }

}
