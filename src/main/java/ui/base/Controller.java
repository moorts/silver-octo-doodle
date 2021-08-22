package ui.base;

import ui.Application;

public abstract class Controller<T extends View> {
    protected Application application;
    protected T view;

    public Controller(T view) {
        this.view = view;
    }

    public final void setApplication(Application app) {
        application = app;
    }

    public abstract void init();
}
