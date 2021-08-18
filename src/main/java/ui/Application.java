package ui;

import com.formdev.flatlaf.FlatLightLaf;
import ui.base.View;

import javax.swing.*;
import java.awt.*;

public class Application {

    private static JFrame frame;

    private View currentView;
    private JPanel currentViewPanel;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
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

        setView(new MainMenuView());
    }

    public void setView(View view) {
        if (currentViewPanel != null) {
            frame.remove(currentViewPanel);
        }

        currentView = view;
        currentViewPanel = currentView.buildUI();
        currentView.setApplication(this);
        frame.add(currentViewPanel);
        frame.invalidate();
        frame.validate();
        frame.repaint();
    }
}
