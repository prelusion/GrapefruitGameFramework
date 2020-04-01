package com.grapefruit.example.observable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Player {

    private StringProperty name = new SimpleStringProperty();

    public Player(String name, boolean male) {
        this.name.set(name);
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
}
