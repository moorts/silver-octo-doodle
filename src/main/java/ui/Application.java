package ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import model.HilfsmittelEntityManager;
import persistenz.EventEntityManager;
import ui.base.View;

import javax.swing.*;
import java.io.IOException;

public class Application {

    private static JFrame frame;

    private View currentView;
    private JPanel currentViewPanel;

    // Entity Managers
    private EventEntityManager eventEntityManager;
    private HilfsmittelEntityManager hilfsmittelEntityManager;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel( new FlatDarculaLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        var gui = new Application();
    }

    private Application() {
        frame = new JFrame();
        frame.setSize(1280, 720);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Event Planner");

        initializeEntityManagers();

        setView(new MainMenuView());
    }

    private void initializeEntityManagers() {
        eventEntityManager = new EventEntityManager();
        try {
            eventEntityManager.loadFromJson();
            System.out.println("Successfully loaded " + eventEntityManager.getAll().size() + " events!");
        } catch (IOException e) {
            System.out.println("Unable to load events.json");
        }
        hilfsmittelEntityManager = new HilfsmittelEntityManager();
        try {
            hilfsmittelEntityManager.loadFromJson();
            System.out.println("Successfully loaded " + hilfsmittelEntityManager.getAll().size() + " events!");
        } catch (IOException e) {
            System.out.println("Unable to load hilfsmittel.json");
        }
    }

    public void setView(View view) {
        if (currentViewPanel != null) {
            frame.remove(currentViewPanel);
        }

        currentView = view;
        currentViewPanel = currentView.buildUI();
        currentView.setApplication(this);
        currentView.initController();
        frame.add(currentViewPanel);
        frame.invalidate();
        frame.validate();
        frame.repaint();
    }

    public EventEntityManager getEventEntityManager() {
        return eventEntityManager;
    }

    public HilfsmittelEntityManager getHilfsmittelEntityManager() {
        return hilfsmittelEntityManager;
    }
}
