package acc.richclient.panes;

import acc.business.Facade;
import acc.business.balance.BalanceItem;
import acc.model.AbstrGroup;
import acc.richclient.MainWindow;
import acc.richclient.dialogs.AbstractDialog;
import acc.util.AccException;
import acc.util.Messages;
import java.time.Month;
import java.util.Optional;
import java.util.stream.Stream;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public final class BalancePane extends AbstrPane<BalanceItem> {

    private final ObservableList<BalanceItem> balanceItems = FXCollections.observableArrayList();
    private TableView<BalanceItem> tableView;
    private Month month;
    TableView<BalanceItem> tw;

    static String title(Month month) {
        return Messages.Rozvaha_pro_mesic.cm() + " : "
                + AbstractDialog.monthFormater.format(month);
    }

    public BalancePane(Month month) {
        super(Messages.Rozvaha.cm());
        this.month = month;
        setText(title(month));
        setContent(tableView = createContent());
        refresh();
    }

    @Override
    public void refresh() {
        try {
            balanceItems.setAll(Facade.INSTANCE.createBalance(month));
        } catch (AccException ex) {
            MainWindow.showException(ex);
        }
    }

    @Override
    public Optional<BalanceItem> getSelected() {
        return Optional.of(tw.getSelectionModel().getSelectedItem());
    }

    public class BalanceColumn extends AbstrPane.PaneColumn {

        public BalanceColumn(String text, int prefWidth) {
            this(text);
            setPrefWidth(prefWidth);
        }

        public BalanceColumn(String text) {
            super(text);
        }

    }

    @Override
    protected TableView<BalanceItem> createContent() {
        tw = new TableView<>();
        tw.setPrefHeight(2000);
        tw.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        BalanceColumn groupCol = new BalanceColumn(Messages.Synteticky_ucet.cm());
        BalanceColumn analCol = new BalanceColumn(Messages.Analytika.cm());
        BalanceColumn nameCol = new BalanceColumn(Messages.Jmeno.cm());

        BalanceColumn initAssetsCol = new BalanceColumn(Messages.Aktiva.cm());
        BalanceColumn initLiabilitiesCol = new BalanceColumn(Messages.Pasiva.cm());
        BalanceColumn assetsCol = new BalanceColumn(Messages.Ma_dati.cm());
        BalanceColumn liabilitiesCol = new BalanceColumn(Messages.Dal.cm());
        BalanceColumn assetsSumCol = new BalanceColumn(Messages.Ma_dati.cm());
        BalanceColumn liabilitiesSumCol = new BalanceColumn(Messages.Dal.cm());
        BalanceColumn finalAssetsCol = new BalanceColumn(Messages.Aktiva.cm());
        BalanceColumn finalLiabilitiesCol = new BalanceColumn(Messages.Pasiva.cm());

        groupCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BalanceItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BalanceItem, String> param) {
                return new SimpleStringProperty(param.getValue().getNumber());
            }
        });
        groupCol.setCellFactory(new Callback<TableColumn<BalanceItem, String>, TableCell<BalanceItem, String>>() {
            @Override
            public TableCell<BalanceItem, String> call(TableColumn<BalanceItem, String> param) {
                return new TableCell<BalanceItem, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            return;
                        }
                        setText(item);
                        BalanceItem value = getTableView().getItems().get(getIndex());
                        value.getGroup().ifPresent((AbstrGroup gr) -> {
                            TableRow tr = getTableRow();
                            switch (gr.getGroupType()) {
                                case ANAL:
                                    tr.setStyle("-fx-background-color:lightgray");
                                    break;
                            }
                        });
                    }
                };
            }
        });

        analCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BalanceItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BalanceItem, String> param) {
                String a = "";
                Optional<AbstrGroup> g = param.getValue().getGroup();
                if (g.isPresent()) {
                    AbstrGroup group = g.get();
                }
                return new SimpleStringProperty(a);
            }
        });

        nameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BalanceItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BalanceItem, String> param) {
                return new SimpleStringProperty(param.getValue().getName());
            }
        });
        initAssetsCol.setCellValueFactory(new PropertyValueFactory<>("initAssets"));
        assetsCol.setCellValueFactory(new PropertyValueFactory<>("assets"));
        assetsSumCol.setCellValueFactory(new PropertyValueFactory<>("assetsSum"));
        finalAssetsCol.setCellValueFactory(new PropertyValueFactory<>("finalAssets"));
        //************************************************************************
        //liabilitiesCol.setCellValueFactory(new PropertyValueFactory<>("liabilities"));
        initLiabilitiesCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BalanceItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BalanceItem, String> param) {
                return new SimpleStringProperty(Long.toString(-param.getValue().getInitLiabilities()));
            }
        });
        liabilitiesCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BalanceItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BalanceItem, String> param) {
                return new SimpleStringProperty(Long.toString(-param.getValue().getLiabilities()));
            }
        });
        liabilitiesSumCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BalanceItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BalanceItem, String> param) {
                return new SimpleStringProperty(Long.toString(-param.getValue().getLiabilitiesSum()));
            }
        });
        finalLiabilitiesCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BalanceItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BalanceItem, String> param) {
                return new SimpleStringProperty(Long.toString(-param.getValue().getFinalLiabilities()));
            }
        });
        // nested columns: *****************************************************************
        BalanceColumn accCol = new BalanceColumn(Messages.Ucet.cm());
        accCol.getColumns().addAll(groupCol, analCol, nameCol);
        BalanceColumn initCol = new BalanceColumn(Messages.Pocatecni.cm());
        initCol.getColumns().addAll(initAssetsCol, initLiabilitiesCol);
        BalanceColumn obratCol = new BalanceColumn(Messages.Obrat.cm());
        obratCol.getColumns().addAll(assetsCol, liabilitiesCol);
        BalanceColumn obratNacitanyCol = new BalanceColumn(Messages.Obrat_nacitany.cm());
        obratNacitanyCol.getColumns().addAll(assetsSumCol, liabilitiesSumCol);
        BalanceColumn finalCol = new BalanceColumn(Messages.Konecna.cm());
        finalCol.getColumns().addAll(finalAssetsCol, finalLiabilitiesCol);
        // ************************************************************************************
        Stream.of(accCol, initCol, obratCol, obratNacitanyCol, finalCol)
                .forEach((c) -> tw.getColumns().add(c));
        tw.setItems(balanceItems);
        return tw;
    }

}
