package com.grapefruit.gamework.app.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class AppSettings {


    private static final Path SETTINGS_FOLDER = Paths.get( System.getProperty("user.home") + "/.gfGamework");
    private static final Path SETTINGS_JSON = Paths.get(SETTINGS_FOLDER + "/settings.json");
    private static Settings SETTINGS;

    /**
     * @param settings
     * Saves settings to json in home dir.
     */
    public static void saveSettings(Settings settings){
        checkFiles();
        createSettingsJson(settings);
    }

    /**
     * @return SETTINGS
     * Fetches settings from home dir.
     */
    public static Settings getSettings(){
        fetchSettings();
        return SETTINGS;
    }


    /**
     * @return Whether files are in order
     * Checks if the files in the home folder are set up properly.
     */
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
                    return createSettingsJson(null);
                }
            } else {
                if (settingsJson.createNewFile()) {
                    return createSettingsJson(null);
                }
            }
            return false;
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Created new json in home dir.
     * @param settings
     * @return Whether or not creation was successful.
     */
    private static boolean createSettingsJson(Settings settings){
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        Gson gson = builder.setPrettyPrinting().create();
        try {
            if (settings == null){
                settings = new Settings();
            }
            FileWriter writer  = new FileWriter(SETTINGS_JSON.toString());
            gson.toJson(settings, writer);
            writer.flush();
        }
        catch (IOException e){
            return false;
        }
        return true;
    }

    /**
     * Fetches settings from home dir.
     */
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
        private Server defaultServer;
        private User defaultUser;
        private Integer timeout;
        private Integer port;

        public Settings(){
            servers = new ArrayList<>();
            users = new ArrayList<>();

            Server defaultServer = new Server("Hanze", "145.33.225.170");
            timeout = 10;
            port = 7789;
            setDefaultServer(defaultServer);
            servers.add(defaultServer);
        }

        public void setDefaultServer(Server defaultServer) {
            this.defaultServer = defaultServer;
        }

        public void setDefaultUser(User defaultUser) {
            this.defaultUser = defaultUser;
        }

        public Server getDefaultServer() {
            return defaultServer;
        }

        public User getDefaultUser() {
            return defaultUser;
        }

        public void addServer(Server server){
            servers.add(server);
        }

        public void removeServer(Server server){
            servers.remove(server);
        }

        public ArrayList<Server> getServers() {
            return servers;
        }

        public ArrayList<User> getUsers() {
            return users;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public void setTimeout(Integer timeout) {
            this.timeout = timeout;
        }

        public Integer getTimeout() {
            return timeout;
        }
    }

    public static class Server{
        private String name;
        private String ip;

        public Server(){}

        public Server(String name, String ip){
            this.name = name;
            this.ip = ip;
        }

        public String getName() {
            return name;
        }

        public String toString(){
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }
    }

    public static class User{

        public User(){
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        private String username;
    }
}
