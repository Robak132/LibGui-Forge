package io.github.robak132.libgui_forge.client;

import static io.github.robak132.libgui_forge.LibGui.MOD_ID;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import lombok.Data;
import lombok.Getter;

@Data
public final class LibGuiConfig {
    @Getter
    private static final LibGuiConfig instance = new LibGuiConfig();

    private boolean darkMode = false;

    private LibGuiConfig() {}

    void setSettings(LibGuiConfig config) {
        this.darkMode = config.darkMode;
    }

    public static boolean isDarkMode() {
        return getInstance().darkMode;
    }

    public static void setDarkMode(boolean darkMode) {
        getInstance().darkMode = darkMode;
    }

    public static void saveConfig() {
        String blockColoursJson = new Gson().toJson(instance);
        writeJson(blockColoursJson, "./config/%s/".formatted(MOD_ID), "config.json");
    }

    public static void loadConfig() {
        LibGuiConfig config = new Gson().fromJson(readJson("./config/%s/config.json".formatted(MOD_ID)), LibGuiConfig.class);
        if (config != null) {
            instance.setSettings(config);
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
