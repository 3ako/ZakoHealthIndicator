package hw.zako.zakohealthindicator.client;

import hw.zako.zakohealthindicator.Config;
import hw.zako.zakohealthindicator.ZakoHealthIndicator;
import hw.zako.zakohealthindicator.client.ui.HealthBarGUI;
import hw.zako.zakohealthindicator.util.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ZakoHealthIndicator.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEventHandler {

    private static HealthBarGUI hud;

    private static HealthBarGUI getHud() {
        if (hud == null) hud = new HealthBarGUI(Minecraft.getInstance());
        return hud;
    }

    @SubscribeEvent
    public static void onAttack(AttackEntityEvent event) {
        if (!event.getPlayer().level.isClientSide) return;
        if (event.getTarget() instanceof Player) {
            getHud().onAttack((Player) event.getTarget());
        } else {
            getHud().reset();
        }
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) return;
        if (!Config.getInstance().isCrosshair()) return;
        getHud().render(event.getMatrixStack());
    }

    @SubscribeEvent
    public static void onRenderNameplate(RenderNameplateEvent event) {
        if (event.getEntity().getType() != EntityType.PLAYER) return;
        Player player = (Player) event.getEntity();
        float health = player.getHealth();
        Component original = event.getContent();
        MutableComponent newName = original.copy()
                .append(new TextComponent(" " + ColorUtil.getColor(health) + String.format("%.1f", health)));
        event.setContent(newName);
    }
}
