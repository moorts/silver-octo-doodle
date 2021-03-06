package ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import persistenz.HilfsmittelEntityManager;
import persistenz.EventEntityManager;
import ui.base.View;
import utilities.HilfsmittelManagement;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Application {

    private static JFrame frame;

    private View currentView;
    private JPanel currentViewPanel;

    // Entity Managers
    private EventEntityManager eventEntityManager;
    private HilfsmittelEntityManager hilfsmittelEntityManager;
    private HilfsmittelManagement hilfsmittelManagement;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize Look and Feel" );
        }
        var gui = new Application();
    }

    private Application() {
        frame = new JFrame();
        frame.setSize(1600, 900);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Event Planner");

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

        initializeEntityManagers();

        this.hilfsmittelManagement = new HilfsmittelManagement(this.hilfsmittelEntityManager);
        for(model.Event e : eventEntityManager.getAll()) {
            hilfsmittelManagement.loadEvent(e);
        }

        setView(new MainMenuView());
    }

    private void initializeEntityManagers() {
        eventEntityManager = new EventEntityManager();
        try {
            eventEntityManager.loadFromJson();
            System.out.println("Successfully loaded " + eventEntityManager.getAll().size() + " Events!");
        } catch (IOException e) {
            System.out.println("Unable to load events.json");
        }
        hilfsmittelEntityManager = new HilfsmittelEntityManager();
        try {
            hilfsmittelEntityManager.loadFromJson();
            System.out.println("Successfully loaded " + hilfsmittelEntityManager.getAll().size() + " Hilfsmittel!");
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

    public HilfsmittelManagement getHilfsmittelManagement() {
        return hilfsmittelManagement;
    }
}
