/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acc.richclient.dialogs;

import java.util.List;
import java.util.Optional;

import acc.model.AnalAcc;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class AccountCB extends ComboBox<AnalAcc> {

    public AccountCB(List<AnalAcc> accounts) {
        this();
     //   getItems().addAll(FXCollections.observableArrayList(accounts));
    }

    public AccountCB() {
        super();
        setConverter(new StringConverter<AnalAcc>() {
            @Override
            public String toString(AnalAcc account) {
                return account.getNumber() + " - " + account.getName();
            }

            @Override
            public AnalAcc fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    public Optional<AnalAcc> getOptAccount() {
        return Optional.ofNullable(getValue());
    }
}
