package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelSettingsWindow;
import com.grapefruit.gamework.app.model.ModelTableSetting;
import com.grapefruit.gamework.app.resources.AppSettings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.apache.commons.validator.routines.InetAddressValidator;

import java.net.URL;
import java.util.*;

public class ControllerTableSetting implements IController{

    @FXML
    private Text keyName;

    @FXML
    private Text valueName;

    @FXML
    private Button removeButton;

    @FXML
    private TextField keyInput;

    @FXML
    private TextField valueInput;

    @FXML
    private Button addServerButton;

    private ModelTableSetting model;

    @FXML
    private TableView settingTable;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    /**
     * Required for FXML
     */
    public ControllerTableSetting()
    {
    }

    /**
     * Required for FXML
     */
    private void initialize()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModel(IModel model) {
        this.model = (ModelTableSetting) model;
        updateTable();
        initialize();
        checkRemoveButton();
    }

    /**
     * Updates the TableView and fetches (new) values from the model.
     */
    private void updateTable(){
        TableColumn keys = new TableColumn(model.getKeyName());
        TableColumn values = new TableColumn(model.getValueName());

        final ObservableList<Setting> settings = FXCollections.observableArrayList();
        for (AppSettings.Server server: model.getSettings().getServers()){
            settings.add(new Setting(server.getName(), server.getIp()));
        }

        keys.setCellValueFactory(new PropertyValueFactory<Setting,String>("key"));
        values.setCellValueFactory(new PropertyValueFactory<Setting,String>("value"));

        settingTable.getItems().removeAll(settingTable.getItems());
        settingTable.getColumns().removeAll(settingTable.getColumns());
        settingTable.setItems(settings);
        settingTable.getColumns().addAll(keys, values);

        keyName.setText(model.getKeyName() + ":");
        valueName.setText(model.getValueName() + ":");
        settingTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        ObservableList<Setting> selectedItems = settingTable.getSelectionModel().getSelectedItems();

        selectedItems.addListener(new ListChangeListener<Setting>() {
            @Override
            public void onChanged(Change<? extends Setting> change) {
                checkRemoveButton();
            }
        });
    }

    /**
     * Removes currently selected setting.
     */
    @FXML
    private void onRemoveSetting(){
        Integer selectedIndex = settingTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex != null){
            if (model.getPreset() == ModelTableSetting.TableSettingPreset.SERVERS)
            model.getSettings().removeServer(model.getSettings().getServers().get(selectedIndex));
            updateTable();
        }
    }

    /**
     * Checks if a setting is selected that can be removed in order to disable or enable the remove button.
     */
    private void checkRemoveButton(){
        if (settingTable.getSelectionModel().getSelectedCells().size() > 0){
            removeButton.setDisable(false);
        } else {
            removeButton.setDisable(true);
        }
    }

    /**
     * Adds a to the Configuration.
     * Server object is built from input values.
     */
    @FXML
    private void onAddServer(){
        InetAddressValidator validator = new InetAddressValidator();
        String errorInfo = null;


        if (validator.isValid(valueInput.getText())){
            String name = keyInput.getText();
            if (name == null){
                errorInfo = "Please enter a name.";
            } else {
                if (name.equals("")){
                    errorInfo = "Please enter a name.";
                } else {
                    if (name.length() > 20){
                        errorInfo = "Please enter a name shorter than 20 characters.";
                    }
                }
            }
        } else {
            errorInfo = "Please enter a valid IP adress";
        }

        if (errorInfo == null){
            model.getSettings().addServer(new AppSettings.Server(keyInput.getText(), valueInput.getText()));
            valueInput.setText("");
            keyInput.setText("");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong input entered.");
            alert.setContentText(errorInfo);
            alert.showAndWait();
        }
        updateTable();
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

        public void setKey(String key) {
            this.key.set(key);
        }

        public String getValue() {
            return value.get();
        }

        public void setValue(String value) {
            this.value.set(value);
        }
    }
}
