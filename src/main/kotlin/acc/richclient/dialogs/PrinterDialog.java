/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acc.richclient.dialogs;

/**
 *
 * @author danecek
 */
public class PrinterDialog {//extends AbstractDialog {

  /*  private ComboBox<Month> monthCB;
    private ComboBox<Printer> printerCB;
    private ComboBox<Printer.MarginType> marginCB;

    public PrinterDialog() throws AccException {
        super(Messages.Tisk_rozvahy.cm());
    }

    @Override
    protected Node createContent() {
        GridPane gp = genGP();
        int row = 0;
        gp.add(new Label(Messages.Mesic.cm() + DEL), 0, row);
        gp.add(monthCB = monthCB(), 1, row);
        row++;
        gp.add(new Label(Messages.Tiskarny.cm() + DEL), 0, row);
        printerCB = new ComboBox(FXCollections.observableArrayList(Printer.getAllPrinters()));
        printerCB.setValue(Printer.getDefaultPrinter());
        gp.add(printerCB, 1, row);
        row++;

        gp.add(new Label(Messages.Margins.cm() + DEL), 0, row);
        marginCB = new ComboBox(FXCollections.observableList(Arrays.asList(Printer.MarginType.values())));
        marginCB.setValue(Printer.MarginType.DEFAULT);
        gp.add(marginCB, 1, row);
        row++;

        return gp;
    }

    @Override
    public void ok() throws AccException {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4,
                PageOrientation.PORTRAIT, marginCB.getValue());

        Stage st = new Stage(StageStyle.DECORATED);
        BalancePane bp = new BalancePane(monthCB.getValue());
        st.setScene(new Scene(bp));//, pageLayout.getPrintableWidth(), pageLayout.getPrintableHeight()));
        PrinterJob job = PrinterJob.createPrinterJob(printerCB.getValue());
        st.show();
        if (job != null) {
            boolean successPrintDialog = job.showPrintDialog(MainWindow.getInstance().getPrimaryStage());
            if (successPrintDialog) {
                double scaleX = pageLayout.getPrintableWidth() / bp.getWidth();
                double scaleY = pageLayout.getPrintableHeight() / bp.getHeight();
                bp.getTransforms().add(new Scale(scaleX, scaleY));
                boolean success = job.printPage(pageLayout, bp);
                if (success) {
                    st.hide();
                    job.endJob();
                }
            }
        }
    }

*/
}
