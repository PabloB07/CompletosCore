package me.blancocl.completoscore.settings;

import lombok.Getter;
import me.blancocl.completoscore.CompletosCore;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public final class CompletosCoreSettings {
    @Getter
    private final static CompletosCoreSettings instance = new CompletosCoreSettings();

    private File file;
    private YamlConfiguration config;
    @Getter
    private List<String> headerLines;
    @Getter
    private List<String> footerLines;

    private CompletosCoreSettings() {
    }

    public void load() {
        file = new File(CompletosCore.getInstance().getDataFolder(), "settings.yml");

        if (!file.exists())
            CompletosCore.getInstance().saveResource("settings.yml", true);

        config = new YamlConfiguration();

        try {
            config.options().parseComments(true);
        } catch (Throwable t) {
            // Unsupported
        }

        try {
            config.load(file);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        headerLines = config.getStringList("Tablist.Header");
        footerLines = config.getStringList("Tablist.Footer");
    }

    public void save() {
        try {
            config.save(file);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void set(String path, Object value) {
        config.set(path, value);

        save();
    }


}
