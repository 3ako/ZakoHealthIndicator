package hw.zako.zakohealthindicator.util;

import net.minecraft.ChatFormatting;

public class ColorUtil {
    public static ChatFormatting getColor(float health) {
        if (health <= 5.0f) {
            return ChatFormatting.RED;
        }
        if (health <= 10.0f) {
            return ChatFormatting.GOLD;
        }
        if (health <= 15.0f) {
            return ChatFormatting.YELLOW;
        }
        if (health <= 20.0f) {
            return ChatFormatting.GREEN;
        }
        return ChatFormatting.DARK_GREEN;
    }
}
