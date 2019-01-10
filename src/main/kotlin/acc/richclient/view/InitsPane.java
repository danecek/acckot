package acc.richclient.view;

import acc.business.Facade;
import acc.model.AnalAcc;
import acc.model.Transaction;
import acc.richclient.MainWindow;
import acc.richclient.controller.actions.TransactionDeleteAction;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public final class InitsPane extends AbstrPane {
    
    private final ObservableList<Transaction> transactions = FXCollections.observableArrayList();
    private final TableView<Transaction> tableView;
    private Optional<AnalAcc> acc;
    
    static class InitColumn extends TableColumn<Transaction, String> {
        
        public InitColumn(String text, int prefWidth) {
            this(text);
            setPrefWidth(prefWidth);
        }
        
        public InitColumn(String text) {
            super(text);
        }
        
    }
    
    public InitsPane(Optional<AnalAcc> acc) throws AccException {
        super(Messages.Pocatecni_stavy.cm());
        this.acc = acc;
        setContent((tableView = createContent()));
        transactions.setAll(Facade.INSTANCE.getInits(acc));
    }
    
    public Optional<Transaction> getSelected() {
        return Optional.ofNullable(tableView.getSelectionModel().getSelectedItem());
    }
    
    protected TableView<Transaction> createContent() {
        TableView<Transaction> tw = new TableView<>();
        tw.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        InitColumn idCol = new InitColumn(Messages.Id.cm(), 100);
        InitColumn amountCol = new InitColumn(Messages.Castka.cm(), 200);
        InitColumn madatiNumberCol = new InitColumn(Messages.Cislo.cm(), 100);
        InitColumn madatiNameCol = new InitColumn(Messages.Jmeno.cm(), 400);
        InitColumn dalNumberCol = new InitColumn(Messages.Cislo.cm(), 100);
        InitColumn dalNameCol = new InitColumn(Messages.Jmeno.cm(), 400);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        madatiNumberCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Transaction, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Transaction, String> param) {
                AnalAcc maDatiAcc = param.getValue().getMaDati();
                //  if ()
                return new SimpleObjectProperty(maDatiAcc.getNumber());
            }
        });
        madatiNameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Transaction, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Transaction, String> param) {
                AnalAcc maDatiAcc = param.getValue().getMaDati();
                return new SimpleObjectProperty(maDatiAcc.getName());
            }
        });
        dalNumberCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Transaction, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Transaction, String> param) {
                AnalAcc dalAcc = param.getValue().getDal();
                return new SimpleObjectProperty(dalAcc.getNumber());
            }
        });
        dalNameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Transaction, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Transaction, String> param) {
                AnalAcc dalAcc = param.getValue().getDal();
                return new SimpleObjectProperty(dalAcc.getName());
            }
        });
        
        tw.setRowFactory(new Callback<TableView<Transaction>, TableRow<Transaction>>() {
            @Override
            public TableRow<Transaction> call(TableView<Transaction> param) {
                TableRow<Transaction> tr = new TableRow<>();
                tr.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getButton() == MouseButton.SECONDARY) {
                            new ContextMenu(
                                    TransactionDeleteAction.instance.createMenuItem())
                                    .show(MainWindow.getInstance().getPrimaryStage());
                        }
                    }
                });
                return tr;
            }
        });
        
        InitColumn madatiCol = new InitColumn(Messages.Ma_dati.cm());
        madatiCol.getColumns().addAll(madatiNumberCol, madatiNameCol);
        InitColumn dalCol = new InitColumn(Messages.Dal.cm());
        dalCol.getColumns().addAll(dalNumberCol, dalNameCol);
        tw.getColumns().addAll(idCol, amountCol, madatiCol, dalCol);
        tw.setItems(transactions);
        return tw;
    }
    
    @Override
    public void refresh() {
        try {
            transactions.setAll(Facade.INSTANCE.getInits(acc));
        } catch (AccException ex) {
            MainWindow.showException(ex);
        }
    }
    
}
