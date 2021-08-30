package ui;

import de.dhbwka.swe.utils.app.SlideShowComponentApp;
import de.dhbwka.swe.utils.gui.SlideshowComponent;
import de.dhbwka.swe.utils.model.ImageElement;
import de.dhbwka.swe.utils.util.ImageLoader;
import model.Hilfsmittel;
import ui.base.Controller;
import utilities.HilfsmittelManagement;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HilfsmittelDetailController extends Controller<HilfsmittelDetailView> {

    private Hilfsmittel hilfsmittel;

    public static final String LABEL_HEADER = "<html><u>%s</u></html>";
    public static final String LABEL_CURRENT = "<html><u>Aktuell verfügbar:</u> %s</html>";
    public static final String LABEL_ALL = "<html><u>Insgesamt Verfügbar:</u> %s</html>";
    public static final String LABEL_STATUS = "<html><u>Status:</u> %s</html>";

    private List<String> slideshowImagePaths = new ArrayList<>();

    public HilfsmittelDetailController(HilfsmittelDetailView view, Hilfsmittel hilfsmittel) {
        super(view);
        this.hilfsmittel = hilfsmittel;
    }

    @Override
    public void init() {
        if (hilfsmittel.getBilder() != null)
            slideshowImagePaths.addAll(hilfsmittel.getBilder());
        updateHilfsmittelDetails();
        updateBasicHilfsmittelData();

        view.backButton.addActionListener(e -> {
            application.setView(new MainMenuView());
        });

        view.saveHilfsmittelDetailsButton.addActionListener(e -> {
            if (isHilfsmittelDetailsValid())
                saveHilfsmittelDetails();
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

    private void updateBasicHilfsmittelData() {
        HilfsmittelManagement management = application.getHilfsmittelManagement();
        management.updateHilfsmittel(new Date(), new Date());
        loadImages();
        view.headerLabel.setText(String.format(LABEL_HEADER, hilfsmittel.getName()));
        view.anzahlAktuellLabel.setText(String.format(LABEL_CURRENT, hilfsmittel.getAktuellVerfuegbar()));
        view.anzahlInsgesamtLabel.setText(String.format(LABEL_ALL, hilfsmittel.getInsgesamtVerfuegbar()));
        view.beschreibungReadOnlyTextArea.setText(hilfsmittel.getBeschreibung());
    }

    private void saveHilfsmittelDetails() {
        hilfsmittel.setName(view.nameTextField.getText());
        hilfsmittel.setBeschreibung(view.beschreibungTextArea.getText());
        hilfsmittel.setInsgesamtVerfuegbar((Integer) view.anzahlInsgesamtSpinner.getValue());
        hilfsmittel.setBilder(slideshowImagePaths);

        try {
            application.getHilfsmittelEntityManager().saveToJson();
        } catch(IOException e) {
            System.out.println("Unable to save Hilfsmittel");
        }
        updateBasicHilfsmittelData();
    }

    private boolean isHilfsmittelDetailsValid() {
        boolean valid = true;
        var errorMessageBuilder = new StringBuilder();


        if (view.nameTextField.getText().isBlank()) {
            valid = false;
            errorMessageBuilder.append("Der Titel darf nicht leer sein.\n");
        }

        try {
            view.anzahlInsgesamtSpinner.commitEdit();
        } catch (ParseException e) {
            valid = false;
            errorMessageBuilder.append("Invalide Anzahl eingegeben.\n");
        }
        if ((Integer) view.anzahlInsgesamtSpinner.getValue() < hilfsmittel.getInsgesamtVerfuegbar() - hilfsmittel.getAktuellVerfuegbar()) {
            valid = false;
            errorMessageBuilder.append("Insgesamte Anzahl darf nicht höher sein, als momentan verwendete Hilfsmittel\n");
        }

        if(!valid) {
            JOptionPane.showMessageDialog(null, errorMessageBuilder, "Fehler beim Speichern!", JOptionPane.ERROR_MESSAGE);
        }

        return valid;
    }

    private void loadImages() {
        try {
            // Load default image from jar resources if no images are defined
            if (slideshowImagePaths == null || slideshowImagePaths.size() == 0) {
                System.out.println("No images defined for event with ID " + hilfsmittel.getId());
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
            System.out.println("Unable to load images for event with ID " + hilfsmittel.getId());
        }
    }

    private void updateHilfsmittelDetails() {
        view.nameTextField.setText(hilfsmittel.getName());
        view.beschreibungTextArea.setText(hilfsmittel.getBeschreibung());
        view.anzahlInsgesamtSpinner.setValue(hilfsmittel.getInsgesamtVerfuegbar());
    }
}
