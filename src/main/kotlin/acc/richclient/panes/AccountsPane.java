package acc.richclient.panes;

import acc.business.Facade;
import acc.model.AnalAcc;
import acc.richclient.MainWindow;
import acc.richclient.controller.actions.AcountDeleteAction;
import acc.util.AccException;
import acc.util.Messages;
import java.util.Optional;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public final class AccountsPane extends TitledPane {

    private ObservableList<AnalAcc> accounts = FXCollections.observableArrayList();
    TableView<AnalAcc> tableView;

    public AccountsPane() {
        super(Messages.Ucty.cm(), null);
        setContent(tableView = createContent());
        refresh();
    }

    private TableView<AnalAcc> createContent() {
        TableView<AnalAcc> tw = new TableView<>();
        tw.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        TableColumn<AnalAcc, Object> idCol = new TableColumn<>(Messages.Id.cm());
        TableColumn<AnalAcc, Object> cisloCol = new TableColumn<>(Messages.Cislo.cm());
        TableColumn<AnalAcc, Object> nameCol = new TableColumn<>(Messages.Jmeno.cm());

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        cisloCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AnalAcc, Object>, ObservableValue<Object>>() {
            @Override
            public ObservableValue<Object> call(TableColumn.CellDataFeatures<AnalAcc, Object> p) {
                return new SimpleObjectProperty(p.getValue().getNumber());
            }
        });
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        tw.getColumns().addAll(idCol, cisloCol, nameCol);
        tw.getColumns().stream().forEach((c) -> {
            c.setPrefWidth(200);
        });
        tw.setItems(accounts);
        tw.setRowFactory(new Callback<TableView<AnalAcc>, TableRow<AnalAcc>>() {
            @Override
            public TableRow<AnalAcc> call(TableView<AnalAcc> param) {
                TableRow<AnalAcc> tr = new TableRow<>();
                tr.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getButton() == MouseButton.SECONDARY) {
//                            ContextMenu cm = new ContextMenu(
//                                    AcountDeleteAction.instance.createMenuItem());
//                            cm.show(MainWindow.getInstance().getPrimaryStage());
                        }
                    }
                });
                return tr;
            }
        });
        return tw;
    }

    public void refresh() {
        try {
            accounts.setAll(Facade.INSTANCE.getAllAccounts());
        } catch (AccException ex) {
            MainWindow.getInstance().showException(ex);
        }
    }

    public Optional<AnalAcc> selected() {
        return Optional.ofNullable(tableView.getSelectionModel().getSelectedItem());
    }

}
