package ui;

import ui.base.View;

import javax.swing.*;

public class EventDetailView extends View<EventDetailController> {
    public EventDetailView() {
        this.controller = new EventDetailController(this);
    }

    @Override
    public JPanel buildUI() {
        JPanel panel = new JPanel();

        JButton testButton = new JButton("Press me too");
        testButton.addActionListener(e -> {
            application.setView(new MainMenuView());
        });
        panel.add(testButton);

        return panel;
    }
}
