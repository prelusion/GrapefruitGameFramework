package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.app.games.Game;

import java.util.ArrayList;
import java.util.HashMap;

public class ModelTableSetting implements IModel {

    private String keyName;
    private String valueName;
    private HashMap<String, String> rows;

    public ModelTableSetting(HashMap<String, String> rows, String keyName, String valueName){
        this.rows = rows;
        this.keyName = keyName;
        this.valueName = valueName;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public HashMap<String, String> getRows() {
        return rows;
    }

    public void setRows(HashMap<String, String> rows) {
        this.rows = rows;
    }
}
