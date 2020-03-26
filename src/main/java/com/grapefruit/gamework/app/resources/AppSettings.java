package com.grapefruit.gamework.app.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public abstract class AppSettings {


    private static final Path SETTINGS_FOLDER = Paths.get( System.getProperty("user.home") + "/.gfGamework");
    private static final Path SETTINGS_JSON = Paths.get(SETTINGS_FOLDER + "/settings.json");
    private static Settings SETTINGS;

    public static void saveSettings(){

    }

    public static Settings getSettings(){
        fetchSettings();
        return SETTINGS;
    }


    private static boolean checkFiles(){
        try {
            if (Files.notExists(SETTINGS_FOLDER)) {
                if (!(new File(SETTINGS_FOLDER.toUri()).mkdir())) {
                    return false;
                }
            }
            File settingsJson = new File(SETTINGS_JSON.toUri());
            if (settingsJson.exists()) {
                if (settingsJson.length() > 0) {
                    return true;
                } else {
                    return createSettingsJson();
                }
            } else {
                if (settingsJson.createNewFile()) {
                    return createSettingsJson();
                }
            }
            return false;
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    private static boolean createSettingsJson(){
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        Gson gson = builder.setPrettyPrinting().create();
        try {
            Settings settings = new Settings();
            FileWriter writer  = new FileWriter(SETTINGS_JSON.toString());
            gson.toJson(settings, writer);
            writer.flush();
        }
        catch (IOException e){
            return false;
        }
        return true;
    }

    private static void fetchSettings(){
        if (checkFiles()) {
            Gson gson = new Gson();
            try {
                SETTINGS = gson.fromJson(new FileReader(SETTINGS_JSON.toString()), Settings.class);
            }
            catch (FileNotFoundException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public static class Settings{
        private ArrayList<Server> servers;
        private ArrayList<User> users;

        public Settings(){
            servers = new ArrayList<>();
            users = new ArrayList<>();


        }
    }

    public class Server{
        private String name;
        private String ip;
        private String port;
    }

    public class User{
        private String username;
    }
}
