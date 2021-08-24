package ui;

import de.dhbwka.swe.utils.app.SlideShowComponentApp;
import de.dhbwka.swe.utils.model.ImageElement;
import de.dhbwka.swe.utils.util.ImageLoader;
import model.Event;
import ui.base.Controller;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.List;

public class EventDetailController extends Controller<EventDetailView> {

    private Event event;

    public EventDetailController(EventDetailView view, Event event) {
        super(view);
        this.event = event;
    }

    @Override
    public void init() {
        loadImages();
    }

    private void loadImages() {
        List<String> paths = event.getBilder();
        try {
            if (paths == null || paths.size() == 0) {
                System.out.println("No images defined for event with ID " + event.getId());
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
            System.out.println("Unable to load images for event with ID " + event.getId());
        }
    }
}