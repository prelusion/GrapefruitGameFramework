package com.grapefruit.example.observable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Player {

    private StringProperty name = new SimpleStringProperty();
    private BooleanProperty isMale = new SimpleBooleanProperty(true);

    public Player(String name, boolean male) {
        this.name.set(name);
        this.isMale.set(male);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty getNameProperty() {
        return this.name;
    }

    public boolean isMale() {
        return isMale.get();
    }

    public void setIsMale(boolean male) {
        this.isMale.set(male);
    }
}
