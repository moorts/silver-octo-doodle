package ui;

import de.dhbwka.swe.utils.gui.SlideshowComponent;
import model.Event;
import ui.base.View;

import javax.swing.*;
import java.awt.*;

public class EventDetailView extends View<EventDetailController> {
    public EventDetailView(Event event) {
        this.controller = new EventDetailController(this, event);
    }

    public SlideshowComponent slideshow;

    @Override
    public JPanel buildUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        JPanel leftPanel = buildLeftPanel();
        JPanel rightPanel = buildRightPanel();

        panel.add(leftPanel);
        panel.add(rightPanel);

        return panel;
    }

    private JPanel buildLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        slideshow = SlideshowComponent.builder("eventDetailSlideshow").build();
        panel.add(slideshow);

        return panel;
    }

    private JPanel buildRightPanel() {
        JPanel panel = new JPanel();
        panel.add(new JButton("Right Button"));
        return panel;
    }
}
