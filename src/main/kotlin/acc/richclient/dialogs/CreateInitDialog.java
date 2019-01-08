package acc.richclient.dialogs;

import acc.util.AccException;
import acc.util.Messages;

public class CreateInitDialog extends InitAbstrDialog {

    public CreateInitDialog() throws AccException {
        super(Messages.Nastav_pocatecni_stav.cm());
    }

}
