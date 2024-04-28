package hw.zako.zakohealthindicator.client.mixin;

import hw.zako.zakohealthindicator.client.ui.HealthBarGUI;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class ImGameGudMixin {
    private final HealthBarGUI hud = new HealthBarGUI(MinecraftClient.getInstance());

    @Inject(method = "renderHealthBar", at = @At(value = "HEAD"))
    private void render(DrawContext context, PlayerEntity player,
                        int x, int y, int lines, int regeneratingHeartIndex,
                        float maxHealth, int lastHealth, int health, int absorption,
                        boolean blinking, CallbackInfo ci) {


        hud.render(context);

    }
}
