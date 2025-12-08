package io.github.robak132.libgui_forgified.client;

import com.google.gson.Gson;
import lombok.Data;
import lombok.Getter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

@Data
public final class LibGuiConfig {
    @Getter
    private static LibGuiConfig instance;

    private boolean darkMode = false;

    private LibGuiConfig () {}

    public static boolean isDarkMode() {
        return getInstance().darkMode;
    }

    public static void setDarkMode(boolean darkMode) {
        getInstance().darkMode = darkMode;
    }

    public static void save(){
        Gson gson = new Gson();
        String blockColoursJson = gson.toJson(instance);
        writeJson(blockColoursJson, "./config/mcrgb_forge/", "config.json");
    }

    public static void load(){
        Gson gson = new Gson();
        instance = gson.fromJson(readJson("./config/mcrgb_forge/config.json"), LibGuiConfig.class);
        if(instance == null){
            instance = new LibGuiConfig();
            save();
        }

    }

    public static void writeJson(String str, String path, String fileName) {
        File dir = new File(path);
        File file = new File(dir, fileName);
        try (FileWriter fw = new FileWriter(file)) {
            if (!dir.exists()) {
                dir.mkdir();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            for (int i = 0; i < str.length(); i++) {
                fw.write(str.charAt(i));
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    static String readJson(String path) {
        try (FileReader fileReader = new FileReader(path)) {
            int i;
            StringBuilder str = new StringBuilder();
            // Using read method
            while ((i = fileReader.read()) != -1) {
                str.append((char) i);
            }
            return str.toString();
        } catch (Exception e) {
            return "";
        }
    }
}
