package acc.richclient;

import acc.richclient.panes.AccountsPane;
import acc.richclient.dialogs.AccAlert;
import acc.richclient.panes.AbstrPane;
import acc.richclient.panes.DocumentPane;
import acc.richclient.panes.TransactionsPane;
import acc.util.Messages;
import java.util.Optional;
import java.util.stream.Stream;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWindow extends Application {

    public static MainWindow instance;
    private Stage primaryStage;
    private TabPane tabPane;

    public static MainWindow getInstance() {
        return instance;
    }

/*    public Stream<Node> getTransactionPanes() {
        return tabPane.getTabs().stream()
                .filter((t) -> t.getText().equals(Messages.Transakce.cm()))
                .map((t) -> t.getContent());
    }*

    public void refreshTransactionPanes() {
        getTransactionPanes().forEach((p) -> ((TransactionsPane) p).refresh());
    }
*/
    public Optional<TransactionsPane> getSelectedTransactionPane() {
        Tab i = tabPane.getSelectionModel().getSelectedItem();
        if (i == null || !i.getText().equals(Messages.Transakce.cm())) {
            return Optional.empty();
        }
        return Optional.of((TransactionsPane) i.getContent());
    }

    public Optional<AccountsPane> getAccountPane() {
        return tabPane.getTabs().stream()
                .filter((t) -> t.getText().equals(Messages.Ucty.cm()))
                .map((t) -> (AccountsPane) t.getContent()).findFirst();
    }

    public void refreshAccountPane() {
        getAccountPane().ifPresent((ap) -> (ap).refresh());
    }

    public Stream<Tab> getTabByName(String title) {
        return tabPane.getTabs().stream()
                .filter((tab) -> tab.getText().equals(title));
    }

    public Stream<AbstrPane> getPaneByName(String title) {
        return getTabByName(title).map(tab -> (AbstrPane) tab.getContent());
    }

    public Optional<AbstrPane> getSelectedTab(String title) {
        return getTabByName(title).filter(t -> t.isSelected())
                .map(tab -> (AbstrPane) tab.getContent()).
                findFirst();
    }

    public void refreshPanes(String title) {
        getTabByName(title).forEach((Tab ap) -> ((AbstrPane) ap.getContent()).refresh());
    }

    public Stream<DocumentPane> getDocumentPanes() {
        return tabPane.getTabs().stream()
                .filter((t) -> t.getText().equals(Messages.Doklady.cm()))
                .map((t) -> (DocumentPane) t.getContent());
    }

    public void refreshDocumentPanes() {
        getDocumentPanes().forEach(dp -> dp.refresh());
    }

    /**
     * @return the tabPane
     */
    public TabPane getTabPane() {
        return tabPane;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
//        System.out.println(TransactionDAODefault.instance);
//        System.out.println(DocumentDAO.instance);
//        System.out.println(AcountDAODefault.instance);
//        System.out.println(CompanyDAO.instance);

    }

    public static void showException(Exception ex) {
        Alert a = new AccAlert(Alert.AlertType.ERROR, ex.toString());
        a.getDialogPane().setFocusTraversable(true);
        a.setResizable(true);
        a.showAndWait();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setResizable(true);
        instance = this;
        primaryStage.setTitle(Messages.Ucetnictvi.cm());
        tabPane = new TabPane();
    //    VBox root = new VBox(libMenuBar = new AccMenuBarBak(), tabPane);
//       //primaryStage.getWidth()
//      //  Scene s = new Scene(root,  primaryStage.getMaxWidth(), primaryStage.getMaxHeight());
//        s.getStylesheets().addAll(getClass()
//                .getResource("/acc/richclient/css.css").toExternalForm());
//        primaryStage.setScene(s);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }


}
