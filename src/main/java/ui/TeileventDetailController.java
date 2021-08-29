package ui;

import de.dhbwka.swe.utils.app.SlideShowComponentApp;
import de.dhbwka.swe.utils.gui.SlideshowComponent;
import de.dhbwka.swe.utils.model.ImageElement;
import de.dhbwka.swe.utils.util.ImageLoader;
import model.Event;
import model.Status;
import model.TeilEvent;
import ui.base.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TeileventDetailController extends Controller<TeileventDetailView> {

    private static final SimpleDateFormat INPUT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private Event event;
    private TeilEvent teilEvent;

    private List<String> slideshowImagePaths = new ArrayList<>();

    public TeileventDetailController(TeileventDetailView view, Event event, TeilEvent teilEvent) {
        super(view);
        this.event = event;
        this.teilEvent = teilEvent;
    }

    @Override
    public void init() {
        if (teilEvent.getBilder() != null)
            slideshowImagePaths.addAll(teilEvent.getBilder());
        loadImages();

        updateTeilEventData();

        view.zurueckButton.addActionListener(e -> {
            application.setView(new EventDetailView(event));
        });

        view.addBildButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Bilddatei (*.jpg, *.jpeg, *.png)", "jpg", "jpeg", "png"));
            var result = fileChooser.showOpenDialog(view.addBildButton);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    slideshowImagePaths.add(fileChooser.getSelectedFile().getCanonicalPath());
                    loadImages();
                } catch (IOException ioException) {
                    System.out.println("Unable to get file path");
                }
            }
        });

        view.removeBildButton.addActionListener(e -> {
            try {
                var imagePosField = SlideshowComponent.class.getDeclaredField("imagePosition");
                imagePosField.setAccessible(true);
                int position = (int)imagePosField.get(view.slideshow);
                slideshowImagePaths.remove(position);
                loadImages();
            } catch (NoSuchFieldException | IllegalAccessException noSuchFieldException) {
                // ignore
            }
        });

        view.speichernButton.addActionListener(e -> {
            if (isValid())
                saveData();
        });
    }

    private void loadImages() {
        try {
            // Load default image from jar resources if no images are defined
            if (slideshowImagePaths == null || slideshowImagePaths.size() == 0) {
                System.out.println("No images defined for TeilEvent with ID " + teilEvent.getName());
                var missingImage = ImageIO.read(SlideShowComponentApp.class.getResourceAsStream("/images/missingimage.png"));
                view.slideshow.setImageElements(new ImageElement[]{new ImageElement(missingImage, "/images/missingimage.png")});
                return;
            }

            var pathArray = slideshowImagePaths.toArray(new String[0]);
            for (var path : pathArray) {
                System.out.println("Loading image from path: " + path);
            }
            var images = ImageLoader.loadImageElements(pathArray);
            view.slideshow.setImageElements(images);
        } catch (Exception e) {
            System.out.println("Unable to load images for TeilEvent with ID " + teilEvent.getName());
        }
    }

    private void updateTeilEventData() {
        view.beschreibungTextArea.setText(teilEvent.getBeschreibung());
        view.startTextField.setText(INPUT_DATE_FORMAT.format(teilEvent.getStart()));
        view.endeTextField.setText(INPUT_DATE_FORMAT.format(teilEvent.getEnd()));
        view.statusComboBox.setSelectedItem(teilEvent.getStatus());
        view.nameTextField.setText(teilEvent.getName());
    }

    private boolean isValid() {

        boolean valid = true;
        var errorMessageBuilder = new StringBuilder();

        if (view.nameTextField.getText().isBlank()) {
            valid = false;
            errorMessageBuilder.append("Der Name darf nicht leer sein.\n");
        }

        try{
            INPUT_DATE_FORMAT.parse(view.startTextField.getText());
        } catch (ParseException e) {
            valid = false;
            errorMessageBuilder.append("Startzeitpunkt hat kein valides Format.\n");
        }

        try{
            INPUT_DATE_FORMAT.parse(view.endeTextField.getText());
        } catch (ParseException e) {
            valid = false;
            errorMessageBuilder.append("Endzeitpunkt hat kein valides Format.\n");
        }

        if (!valid) {
            JOptionPane.showMessageDialog(null, errorMessageBuilder, "Fehler beim Speichern!", JOptionPane.ERROR_MESSAGE);
        }

        return valid;
    }

    private void saveData() {
        teilEvent.setBeschreibung(view.beschreibungTextArea.getText());
        teilEvent.setName(view.nameTextField.getText());
        teilEvent.setStatus((Status)view.statusComboBox.getSelectedItem());
        teilEvent.setBilder(new ArrayList<>(slideshowImagePaths));
        try {
            teilEvent.setStart(INPUT_DATE_FORMAT.parse(view.startTextField.getText()));
            teilEvent.setEnd(INPUT_DATE_FORMAT.parse(view.endeTextField.getText()));
            application.getEventEntityManager().saveToJson();
            JOptionPane.showMessageDialog(null, "Änderungen erfolgreich gespeichert!", "Gespeichert", JOptionPane.INFORMATION_MESSAGE);
        } catch (ParseException | IOException e) {
            System.out.println("Unable to save events.");
        }
    }

}
