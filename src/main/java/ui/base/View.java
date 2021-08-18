package ui.base;

import ui.Application;

import javax.swing.*;

public abstract class View<T extends Controller> {
    protected T controller;
    protected Application application;

    public abstract JPanel buildUI();

    public final void setApplication(Application app) {
        application = app;
        if (controller != null) controller.setApplication(app);
    }
}
