package ui;

import de.dhbwka.swe.utils.app.SlideShowComponentApp;
import de.dhbwka.swe.utils.model.ImageElement;
import de.dhbwka.swe.utils.util.ImageLoader;
import model.Hilfsmittel;
import ui.base.Controller;

import javax.imageio.ImageIO;
import java.util.List;

public class HilfsmittelDetailController extends Controller<HilfsmittelDetailView> {

    private Hilfsmittel hilfsmittel;

    public static final String LABEL_HEADER = "<html><u>%s</u></html>";
    public static final String LABEL_CURRENT = "<html><u>Aktuell verfügbar:</u> %s</html>";
    public static final String LABEL_ALL = "<html><u>Insgesamt Verfügbar:</u> %s</html>";
    public static final String LABEL_STATUS = "<html><u>Status:</u> %s</html>";

    public HilfsmittelDetailController(HilfsmittelDetailView view, Hilfsmittel hilfsmittel) {
        super(view);
        this.hilfsmittel = hilfsmittel;
    }

    @Override
    public void init() {
        updateHilfsmittelDetails();
        updateBasicEventData();

        view.backButton.addActionListener(e -> {
            application.setView(new MainMenuView());
        });
        /*
        view.saveHilfsmittelDetailsButton.addActionListener(e -> {
            if (isEventDetailsValid())
                saveEventDetails();
        });
         */
    }

    private void updateBasicEventData() {
        loadImages();
        view.headerLabel.setText(String.format(LABEL_HEADER, hilfsmittel.getName()));
        view.anzahlAktuellLabel.setText(String.format(LABEL_CURRENT, hilfsmittel.getAktuellVerfuegbar()));
        view.anzahlInsgesamtLabel.setText(String.format(LABEL_ALL, hilfsmittel.getInsgesamtVerfuegbar()));
        view.beschreibungReadOnlyTextArea.setText(hilfsmittel.getBeschreibung());
    }

    private void loadImages() {
        List<String> paths = hilfsmittel.getBilder();
        try {
            // Load default image from jar resources if no images are defined
            if (paths == null || paths.size() == 0) {
                System.out.println("No images defined for event with ID " + hilfsmittel.getId());
                var missingImage = ImageIO.read(SlideShowComponentApp.class.getResourceAsStream("/images/missingimage.png"));
                view.slideshow.setImageElements(new ImageElement[]{new ImageElement(missingImage, "/images/missingimage.png")});
                return;
            }

            var pathArray = paths.toArray(new String[0]);
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
        //view.nameTextField.setText(hilfsmittel.getName());
        //view.beschreibungTextArea.setText(hilfsmittel.getBeschreibung());
    }
}
