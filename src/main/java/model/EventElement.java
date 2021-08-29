package model;

import javax.swing.*;

public abstract class EventElement {
    public abstract ElementType getType();
    public abstract JPanel buildUI();
    public abstract void saveChanges();
}
