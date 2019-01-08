package acc.richclient.controller.actions;

import acc.richclient.MainWindow;
import acc.richclient.dialogs.PrinterDialog;
import acc.util.AccException;
import acc.util.Messages;

public class PrintBalanceAction extends AbstrAction {
    
    public static final PrintBalanceAction instance = new PrintBalanceAction();
    
    private PrintBalanceAction() {
        super(Messages.Tisk_rozvahy.cm());
    }
    
    @Override
    public void execute() {
        try {
            new PrinterDialog().execute();
        } catch (AccException ex) {
            MainWindow.showException(ex);
        }
    }
    
}
