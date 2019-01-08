package acc.richclient.controller.actions;

import acc.util.Messages;
import java.util.logging.Logger;
import javafx.application.Platform;

public class ExitAction extends AbstrAction {

    private static final Logger LOG = Logger.getLogger(ExitAction.class.getName());
    public static final ExitAction instance = new ExitAction();


    private ExitAction() {
        super(Messages.Ukonceni.cm());
    }

    @Override
    public void execute() {
        Platform.exit();
    }

}
