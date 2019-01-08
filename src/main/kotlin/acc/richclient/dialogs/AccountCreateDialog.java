package acc.richclient.dialogs;

import acc.business.Facade;
import acc.model.AnalAcc;
import acc.model.AccGroup;
import acc.model.Osnova;
import acc.richclient.MainWindow;
import acc.util.AccException;
import acc.util.Messages;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class AccountCreateDialog extends AccountAbstractDialog {

    private ObservableList<AccGroup> groups;
    private final List<AnalAcc> accounts;

    public AccountCreateDialog() throws AccException {
        super(Messages.Vytvor_ucet.cm());
        accounts = Facade.instance.getAllAccounts();
        getDialogPane().setPadding(new Insets(5));
    }

    @Override
    protected Node createContent() {
        GridPane gp = genGP();
        gp.setHgap(5);
        gp.setVgap(5);
        groups = FXCollections.observableArrayList(Osnova.instance.syntAccounts());
        gp.add(new Label(Messages.Skupina.cm() + DEL), 0, 0);
        gp.add(agTF = new TextField(), 1, 0);
        gp.add(skupinaCB = new ComboBox<>(groups), 2, 0);
        skupinaCB.setEditable(false);
        skupinaCB.valueProperty().addListener(new ChangeListener<AccGroup>() {
            @Override
            public void changed(ObservableValue<? extends AccGroup> observable, AccGroup oldValue, AccGroup newValue) {
                if (newValue != null) {
                    agTF.setText(newValue.getNumber());
                }
            }
        });
        agTF.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                skupinaCB.getItems().setAll(Osnova.instance.getSubGroups(agTF.getText()));
            }
        });
        gp.add(new Label(Messages.Analytika.cm() + DEL), 0, 1);
        gp.add(analTF = new TextField("001"), 1, 1);
        gp.add(new Label(Messages.Nazev.cm() + DEL), 0, 2);
        gp.add(nameTF = new TextField(), 1, 2, 2, 1);

        nameTF.textProperty().addListener(this);
        analTF.textProperty().addListener(this);
        agTF.textProperty().addListener(this);
        skupinaCB.setOnAction((event) -> {
            validate();
        });
        return gp;
    }

    @Override
    public void ok() {
        try {
            Optional<AccGroup> ag = Osnova.instance.getGroup(agTF.getText());
            Facade.instance.createAccount(ag.get(),
                    analTF.getText(),
                    nameTF.getText());
            MainWindow.getInstance().getAccountPane().ifPresent((ap) -> ap.refresh());
        } catch (AccException ex) {
            MainWindow.getInstance().showException(ex);

        }
    }

    private Optional<String> error() {
        Optional<AccGroup> ag = Osnova.instance.getGroup(agTF.getText());
        if (!ag.isPresent()) {
            return Optional.of(Messages.neplatna_skupina.cm());
        }
        if (!Pattern.matches("\\d\\d\\d", analTF.getText())) {
            return Optional.of(Messages.neplatna_analytika.cm());
        }
        for (AnalAcc a : accounts) {
            if (a.getNumber().equals(ag.get().getNumber() + analTF.getText())) {
                return Optional.of(Messages.duplicitni_cislo_uctu.cm());
            }
        }
        if (nameTF.getText().trim().isEmpty()) {
            return Optional.of(Messages.prazdne_jmeno.cm());
        }
        return Optional.empty();
    }

    @Override
    public void validate() {
        Optional<String> message = error();
        errorPane.setError(message);
    }

}
