package hw.zako.zakohealthindicator;


import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;


public class ZakoHealthIndicator implements ModInitializer {

    @Override
    public void onInitialize() {
        Config config = Config.getInstance();
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(
                ClientCommandManager.literal("crosshairhealth")
                    .executes(c -> {
                        config.setCrosshair(!config.isCrosshair());
                        MinecraftClient.getInstance().player.sendMessage(Text.literal("Ok: " + config.isCrosshair()).formatted(Formatting.AQUA));
                        return 1;
                    })
        ));
    }
}