package model;

import de.dhbwka.swe.utils.app.SlideShowComponentApp;
import de.dhbwka.swe.utils.gui.SlideshowComponent;
import de.dhbwka.swe.utils.model.ImageElement;
import de.dhbwka.swe.utils.util.ImageLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class TeilEvent {
    private String name;
    private Date start;
    private Date end;
    private Status status;
    private EventElement eventElement;
    private List<String> bilder;
    private List<Zuweisung> zuweisungen;

    // UI

    private SlideshowComponent slideshowComponent;

    public String getName() {
        return name;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public Status getStatus() {
        return status;
    }

    public EventElement getEventElement() {
        return eventElement;
    }

    public List<String> getBilder() {
        return bilder;
    }

    public List<Zuweisung> getZuweisungen() {
        return zuweisungen;
    }

    public JPanel getPreviewPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.setBorder(new JScrollPane().getBorder());

        panel.add(slideshowComponent = SlideshowComponent.builder("slideshow_" + name).build());
        // Fix slideshow button color for dark theme
        try {
            Field btnField = slideshowComponent.getClass().getDeclaredField("btnImage");
            btnField.setAccessible(true);
            JButton btn = (JButton)btnField.get(slideshowComponent);
            btn.setBackground(new JButton().getBackground());
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadImages();

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JLabel headerLabel;
        labelPanel.add(headerLabel = new JLabel("<html><u>" + name + "</u></html>"));
        headerLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        labelPanel.add(Box.createVerticalStrut(10));

        labelPanel.add(new JLabel("<html><u>Start:</u> " + (start != null ? DateFormat.getInstance().format(start) : "") + "</html"));
        labelPanel.add(new JLabel("<html><u>Ende:</u> " + (end != null ? DateFormat.getInstance().format(end) : "") + "</html"));

        panel.add(labelPanel);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        return panel;
    }

    private void loadImages() {
        try {
            // Load default image from jar resources if no images are defined
            if (bilder == null || bilder.size() == 0) {
                System.out.println("No images defined for the TeilEvent with name " + name);
                var missingImage = ImageIO.read(SlideShowComponentApp.class.getResourceAsStream("/images/missingimage.png"));
                slideshowComponent.setImageElements(new ImageElement[]{new ImageElement(missingImage, "/images/missingimage.png")});
                return;
            }

            var pathArray = bilder.toArray(new String[0]);
            for (var path : pathArray) {
                System.out.println("Loading image from path: " + path);
            }
            var images = ImageLoader.loadImageElements(pathArray);
            slideshowComponent.setImageElements(images);
        } catch (Exception e) {
            System.out.println("Unable to load images for the TeilEvent with name " + name);
        }
    }
}
