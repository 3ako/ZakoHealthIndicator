package hw.zako.zakohealthindicator.client.mixin;

import hw.zako.zakohealthindicator.Config;
import hw.zako.zakohealthindicator.client.ui.HealthBarGUI;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class ImGameGudMixin {
    private final Config config = Config.getInstance();
    private final HealthBarGUI hud = new HealthBarGUI(MinecraftClient.getInstance());

    @Inject(method = "renderHotbar", at = @At(value = "HEAD"))
    private void renderHotbar(float tickDelta, MatrixStack matrices, CallbackInfo ci) {
        if (config.isCrosshair())
            hud.render(matrices);
    }
}
