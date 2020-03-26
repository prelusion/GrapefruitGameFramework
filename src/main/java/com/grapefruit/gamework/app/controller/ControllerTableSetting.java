package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelGameTile;
import com.grapefruit.gamework.app.model.ModelTableSetting;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

public class ControllerTableSetting implements IController{


    private ModelTableSetting model;

    @FXML
    private TableView settingTable;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    public ControllerTableSetting()
    {
    }

    private void initialize()
    {

    }

    @Override
    public void setModel(IModel model) {
        this.model = (ModelTableSetting) model;
        updateTable();
        initialize();
    }

    private void updateTable(){
        TableColumn keys = new TableColumn(model.getKeyName());
        TableColumn values = new TableColumn(model.getValueName());

        final ObservableList<Setting> settings = FXCollections.observableArrayList();
        Iterator iterator = model.getRows().entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry setting = (Map.Entry)iterator.next();
            settings.add(new Setting((String)setting.getKey(), (String)setting.getValue()));
        }

        keys.setCellValueFactory(new PropertyValueFactory<Setting,String>("key"));
        keys.setCellValueFactory(new PropertyValueFactory<Setting,String>("value"));

        settingTable.getItems().removeAll(settingTable.getItems());
        settingTable.getColumns().removeAll(settingTable.getColumns());
        settingTable.getItems().add(settings);
        settingTable.getColumns().addAll(keys, values);
    }

    public static class Setting {

        private final SimpleStringProperty key;
        private final SimpleStringProperty value;

        private Setting(String key, String value) {
            this.key = new SimpleStringProperty(key);
            this.value = new SimpleStringProperty(value);
        }

        public String getKey() {
            return key.get();
        }

        public SimpleStringProperty keyProperty() {
            return key;
        }

        public void setKey(String key) {
            this.key.set(key);
        }

        public String getValue() {
            return value.get();
        }

        public SimpleStringProperty valueProperty() {
            return value;
        }

        public void setValue(String value) {
            this.value.set(value);
        }
    }
}
