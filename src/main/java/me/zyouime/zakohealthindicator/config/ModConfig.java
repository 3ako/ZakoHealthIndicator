package me.zyouime.zakohealthindicator.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "zakohealthindicator")
public class ModConfig implements ConfigData {

    public boolean enabled = true;
    public float normalScale = 1.3f;
    public float lowHpScale = 2f;
    public long targetDelay = 10000L;
    public float relativeX = 0.5f;
    public float relativeY = 0.53f;
}
