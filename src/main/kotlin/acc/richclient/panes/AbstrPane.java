/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acc.richclient.panes;

import acc.richclient.MainWindow;

import java.util.Arrays;
import java.util.Optional;

import acc.richclient.controller.actions.AbstrAction;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public abstract class AbstrPane<T> extends TitledPane {

    private String name;

    public AbstrPane(String name) {
        this.name = name;
    }

    protected  PaneColumn propColumn(String title, String field) {
        PaneColumn nameCol = new PaneColumn(title);
        nameCol.setCellValueFactory(new PropertyValueFactory<>(field));
        return nameCol;
    }

    public abstract Optional<T> getSelected();

    public  AbstrPane<T> addToMain() {

        return this;

    }

    public class PaneColumn extends TableColumn<T, String> {

        public PaneColumn(String text, int prefWidth, TableColumn<T, String>... cols) {
            this(text);
            setPrefWidth(prefWidth);
            getColumns().addAll(Arrays.asList(cols));
        }

        public PaneColumn(String text, TableColumn<T, String>... cols) {
            super(text);
            getColumns().addAll(Arrays.asList(cols));
        }

    }

    protected abstract Node createContent();

    public abstract void refresh();

    Callback<TableView<T>, TableRow<T>> gencb(AbstrAction... ac) {
        return new Callback<TableView<T>, TableRow<T>>() {
            @Override
            public TableRow<T> call(TableView<T> param) {
                TableRow<T> tr = new TableRow<>();
                tr.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getButton() == MouseButton.SECONDARY) {
                            ContextMenu cm = new ContextMenu();
                            for (AbstrAction a : ac) {
                           //     cm.getItems().add(a.createMenuItem());
                            }
                            cm.show(MainWindow.getInstance().getPrimaryStage());
                        }
                    }
                });
                return tr;
            }
        };
    }

}
