package ui;

import de.dhbwka.swe.utils.app.SlideShowComponentApp;
import de.dhbwka.swe.utils.gui.SlideshowComponent;
import de.dhbwka.swe.utils.model.ImageElement;
import de.dhbwka.swe.utils.util.ImageLoader;
import model.Event;
import model.Status;
import model.ui.KontaktinformationenTableModel;
import ui.base.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventDetailController extends Controller<EventDetailView> {

    public static final String LABEL_HEADER = "<html><u>%s</u></html>";
    public static final String LABEL_START_DATE = "<html><u>Start:</u> %s</html>";
    public static final String LABEL_END_DATE = "<html><u>Ende:</u> %s</html>";
    public static final String LABEL_STATUS = "<html><u>Status:</u> %s</html>";
    private static final SimpleDateFormat INPUT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private List<String> slideshowImagePaths = new ArrayList<>();

    private Event event;

    public EventDetailController(EventDetailView view, Event event) {
        super(view);
        this.event = event;
    }

    @Override
    public void init() {
        if (event.getBilder() != null)
            slideshowImagePaths.addAll(event.getBilder());
        updateBasicEventData();
        updateEventDetails();
        updateEventElements();

        view.backButton.addActionListener(e -> {
            application.setView(new MainMenuView());
        });

        view.saveEventDetailsButton.addActionListener(e -> {
            if (isEventDetailsValid())
                saveEventDetails();
        });

        view.neuerKontaktButton.addActionListener(e -> {
            ((KontaktinformationenTableModel)view.kontakteTable.getModel()).addNewRow();
            view.kontakteTable.updateUI();
        });

        view.kontaktLoeschenButton.addActionListener(e -> {
            var selectedRow = view.kontakteTable.getSelectedRow();
            if (selectedRow != -1) {
                ((KontaktinformationenTableModel)view.kontakteTable.getModel()).removeRow(selectedRow);
                view.kontakteTable.updateUI();
            }
        });

        view.bildHinzufuegenButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Bilddatei (*.jpg, *.jpeg, *.png)", "jpg", "jpeg", "png"));
            var result = fileChooser.showOpenDialog(view.bildHinzufuegenButton);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    slideshowImagePaths.add(fileChooser.getSelectedFile().getCanonicalPath());
                    loadImages();
                } catch (IOException ioException) {
                    System.out.println("Unable to get file path");
                }
            }
        });

        view.bildLoeschenButton.addActionListener(e -> {
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
    }

    private void updateBasicEventData() {
        loadImages();
        view.headerLabel.setText(String.format(LABEL_HEADER, event.getTitel()));
        if (event.getStart() != null)
            view.startDateLabel.setText(String.format(LABEL_START_DATE, DateFormat.getInstance().format(event.getStart())));
        if (event.getEnde() != null)
            view.endDateLabel.setText(String.format(LABEL_END_DATE, DateFormat.getInstance().format(event.getEnde())));
        if (event.getKontakte() != null)
            view.kontaktpersonenTextArea.setText(event.getKontakte().stream().map(k -> k.getName() + " - " + k.getEmail() + " - " + k.getTelnr()).collect(Collectors.joining("\n")));
        view.beschreibungReadOnlyTextArea.setText(event.getBeschreibung());
        if (event.getStatus() != null)
            view.statusLabel.setText(String.format(EventDetailController.LABEL_STATUS, event.getStatus().getHtmlString()));
    }

    private void updateEventDetails() {
        view.titelTextField.setText(event.getTitel());
        view.kategorieTextField.setText(event.getKategorie());
        view.beschreibungTextArea.setText(event.getBeschreibung());
        view.statusComboBox.setSelectedItem(event.getStatus());
        view.kontakteTable.setModel(new KontaktinformationenTableModel(event.getKontakte()));
        view.startTextField.setText(INPUT_DATE_FORMAT.format(event.getStart()));
        view.endeTextField.setText(INPUT_DATE_FORMAT.format(event.getEnde()));
    }

    private void updateEventElements() {
        if (event.getTeilEvents() != null)
            view.addEventElementDetailPanels(event.getTeilEvents());
    }

    private boolean isEventDetailsValid() {

        boolean valid = true;
        var errorMessageBuilder = new StringBuilder();

        if (view.titelTextField.getText().isBlank()) {
            valid = false;
            errorMessageBuilder.append("Der Titel darf nicht leer sein.\n");
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

    private void saveEventDetails() {
        event.setTitel(view.titelTextField.getText());
        event.setKategorie(view.kategorieTextField.getText());
        event.setBeschreibung(view.beschreibungTextArea.getText());
        event.setStatus((Status)view.statusComboBox.getSelectedItem());
        event.setKontakte(new ArrayList<>(((KontaktinformationenTableModel)view.kontakteTable.getModel()).getAllRows()));
        event.setBilder(slideshowImagePaths);
        try {
            event.setStart(INPUT_DATE_FORMAT.parse(view.startTextField.getText()));
            event.setEnde(INPUT_DATE_FORMAT.parse(view.endeTextField.getText()));
            application.getEventEntityManager().saveToJson();
        } catch (ParseException e) {
            System.out.println("Unable to parse start or end date.");
        } catch (IOException e) {
            System.out.println("Unable to save events.");
        }
        updateBasicEventData();
    }

    private void loadImages() {
        try {
            // Load default image from jar resources if no images are defined
            if (slideshowImagePaths == null || slideshowImagePaths.size() == 0) {
                System.out.println("No images defined for event with ID " + event.getId());
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
            System.out.println("Unable to load images for event with ID " + event.getId());
        }
    }
}
