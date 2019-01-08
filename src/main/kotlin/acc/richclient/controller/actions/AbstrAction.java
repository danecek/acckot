/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acc.richclient.controller.actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

public abstract class AbstrAction {

    public AbstrAction(String name) {
        this.name = name;
    }

    protected String name;

    public abstract void execute();

    public MenuItem createMenuItem() {
        MenuItem mi = new MenuItem(name);
        mi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                execute();
            }
        });
        return mi;
    }

}
