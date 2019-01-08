package acc.richclient.controller;

import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Printing {

    public static void printSetup(Node node, Stage owner) {
        // Create the PrinterJob		
        PrinterJob job = PrinterJob.createPrinterJob();

        if (job == null) {
            return;
        }

        // Show the print setup dialog
        boolean proceed = job.showPrintDialog(owner);

        if (proceed) {
            print(job, node);
        }
    }

    public static void print(PrinterJob job, Node node) {
        // Set the Job Status Message
        //  jobStatus.textProperty().bind(job.jobStatusProperty().asString());

        // Print the node
        boolean printed = job.printPage(node);

        if (printed) {
            job.endJob();
        }
    }
}
