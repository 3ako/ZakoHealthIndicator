package hw.zako.zakohealthindicator.util;

import net.minecraft.util.Formatting;

public class ColorUtil {
    public static Formatting getColor(float health) {
        if (health <= 5.0f) {
            return Formatting.RED;
        }
        if (health <= 10.0f) {
            return Formatting.GOLD;
        }
        if (health <= 15.0f) {
            return Formatting.YELLOW;
        }
        if (health <= 20.0f) {
            return Formatting.GREEN;
        }
        return Formatting.DARK_GREEN;
    }
}
