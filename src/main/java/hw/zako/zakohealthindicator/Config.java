package hw.zako.zakohealthindicator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
    private static Config instance;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final File CONFIG_DIR = new File(MinecraftClient.getInstance().runDirectory, "config");
    private static final File CONFIG_FILE = new File(CONFIG_DIR, "zhindicator.json");

    private boolean crosshair = true;

    public boolean isCrosshair() {
        return crosshair;
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public Config() {
        createConfigDirIfNeeded();
    }

    private void createConfigDirIfNeeded() {
        if (!CONFIG_DIR.exists() && (!CONFIG_DIR.mkdirs())) {
            return;
        }
        loadConfig();
    }

    public void setCrosshair(boolean crosshair) {
        this.crosshair = crosshair;
        this.saveConfig();
    }

    public void loadConfig() {
        if (!CONFIG_FILE.exists()) {
            return;
        }

        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject json = GSON.fromJson(reader, JsonObject.class);
            if (json.has("crosshair")) {
                crosshair = json.get("crosshair").getAsBoolean();
            }

        } catch (IOException | JsonParseException e) {
        }
    }

    public void saveConfig() {
        JsonObject json = new JsonObject();
        json.addProperty("crosshair", crosshair);
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(json, writer);
        } catch (IOException e) {
        }
    }
}
