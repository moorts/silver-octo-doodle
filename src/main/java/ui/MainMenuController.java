package ui;

import model.ui.EventTableModel;
import ui.base.Controller;

public class MainMenuController extends Controller<MainMenuView> {

    public MainMenuController(MainMenuView view) {
        super(view);
    }

    @Override
    public void init() {
        view.eventTable.setModel(new EventTableModel(this.application.getEventEntityManager().getAll()));
    }

}
