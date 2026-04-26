package me.zyouime.zakohealthindicator;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.zyouime.zakohealthindicator.command.ModCommand;
import me.zyouime.zakohealthindicator.config.ModConfig;
import me.zyouime.zakohealthindicator.render.HealthHudRender;
import net.fabricmc.api.ClientModInitializer;

public class Main implements ClientModInitializer {

    private static ModConfig config;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        new HealthHudRender(config);
        new ModCommand(config);
    }

    public static ModConfig getConfig() {
        return config;
    }
}
