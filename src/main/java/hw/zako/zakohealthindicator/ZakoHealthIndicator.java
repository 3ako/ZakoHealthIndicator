package hw.zako.zakohealthindicator;

import net.minecraftforge.fml.common.Mod;

@Mod(ZakoHealthIndicator.MODID)
public class ZakoHealthIndicator {
    public static final String MODID = "zakohealthindicator";

    public ZakoHealthIndicator() {
        Config.getInstance();
    }
}
