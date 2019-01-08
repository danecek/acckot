package acc.richclient.dialogs;

import acc.business.Global;
import acc.richclient.MainWindow;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

import acc.util.AccException;
import acc.util.Messages;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public abstract class AbstractDialog extends Dialog<ButtonType> implements InvalidationListener {

    public static final DateTimeFormatter monthFormater = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).ofPattern("MMMM").withLocale(Global.instance.getLocale());

    protected ComboBox<Month> monthCB() {
        ComboBox<Month> monthCB = new ComboBox<Month>(FXCollections.observableArrayList(Month.values()));
        monthCB.setConverter(new StringConverter<Month>() {

            @Override
            public String toString(Month object) {
                return monthFormater.format(object);
            }

            @Override
            public Month fromString(String string) {
                return (Month) monthFormater.parse(string);
            }
        });
        monthCB.setOnAction((e) -> {
            validate();
        });
        monthCB.setValue(Month.DECEMBER);
        return monthCB;
    }

    public class ErrorPane extends HBox {

        public void setError(Optional<String> message) {
            errorText.setText(message.orElse(""));
            getDialogPane().lookupButton(ButtonType.OK).setDisable(message.isPresent());
            setVisible(message.isPresent());
        }

        public void callSetVisible(boolean b) {
            getDialogPane().lookupButton(ButtonType.OK).setDisable(b);
            setVisible(b);
        }

        private final Label errorText;

        public ErrorPane() {
            Label l = new Label(Messages.Chyba + DEL);
            l.setStyle("-fx-text-fill:red;");
            errorText = new Label();
            errorText.setStyle("-fx-text-fill:red;");
            this.getChildren().addAll(l, errorText);
            setVisible(false);
        }

    }

    protected GridPane genGP() {
        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        return gp;
    }

    public void setError(Optional<String> message) {
        errorPane.setError(message);
    }

    protected ErrorPane errorPane;
    public static final String DEL = ": ";

    public AbstractDialog(String title) {
        setTitle(title);
        getDialogPane().setContent(new VBox(10, createContent(), errorPane = new ErrorPane()));
        getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
        setResizable(true);
        getDialogPane().getStylesheets()
                .addAll(getClass().getResource("/acc/richclient/css.css").toExternalForm());
        //validate();
    }

    protected abstract Node createContent();

    public abstract void ok() throws AccException;

    public void execute() {
        try {
            Optional<ButtonType> btn = showAndWait();
            if (!btn.isPresent() || btn.get() == ButtonType.CANCEL) {
                return;
            }
            ok();
        } catch (AccException ex) {
            MainWindow.showException(ex);
        }
    }

    public abstract void validate();

    @Override
    public void invalidated(Observable observable) {
        validate();
    }

}
