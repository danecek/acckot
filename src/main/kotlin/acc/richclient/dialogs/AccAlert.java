package acc.richclient.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


public class AccAlert extends Alert {

    public AccAlert(AlertType alertType) {
        super(alertType);
        getDialogPane().getStylesheets()
                .addAll(getClass().getResource("/acc/richclient/css.css").toExternalForm());

    }

    public AccAlert(AlertType alertType, String contentText, ButtonType... buttons) {
        super(alertType, contentText, buttons);
        getDialogPane().getStylesheets()
                .addAll(getClass().getResource("/acc/richclient/css.css").toExternalForm());

    }

}
