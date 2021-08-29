package model.factory;

import model.*;

public class EventElementFactory {
    public EventElement createEventElement(ElementType type) {
        switch (type) {
            case CATERING:
                return new Catering();
            case MUSIK:
                return new Musik();
            case LOCATION:
                return new Location();
        }

        return null;
    }
}
