package ui.base;

import ui.Application;

public abstract class Controller {
    protected Application application;

    public final void setApplication(Application app) {
        application = app;
    }
}
