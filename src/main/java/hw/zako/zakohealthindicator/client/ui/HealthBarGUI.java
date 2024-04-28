package hw.zako.zakohealthindicator.client.ui;

import hw.zako.zakohealthindicator.util.ColorUtil;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class HealthBarGUI {

    private MinecraftClient client;
    private long lastAttack = 0;
    private PlayerEntity player = null;

    public HealthBarGUI(MinecraftClient client) {
        this.client = client;

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (entity.getType() != EntityType.PLAYER) {
                lastAttack = 0;
                return ActionResult.PASS;
            }
            lastAttack = System.currentTimeMillis();
            if (this.player != entity) {
                this.player = (PlayerEntity) entity;
            }
            return ActionResult.PASS;
        });
    }

    private long getLeft() {
        return System.currentTimeMillis() - this.lastAttack;
    }

    public void render(DrawContext context) {
        if (getLeft() > 10000) return;
        if (this.player == null) return;
        int health = (int) player.getHealth();

        String text = ColorUtil.getColor(health)+""+health;

        final var textW = client.textRenderer.getWidth(text);
        context.getMatrices().push();
        float scale = health < 10 ? 2 : 1;
        final int h = (int) (client.getWindow().getScaledHeight()/(scale*2));
        final int w = (int) (client.getWindow().getScaledWidth()/(scale*2)- textW/2);


        if (scale > 1)
            context.getMatrices().scale(scale, scale, scale);
        context.drawText(client.textRenderer, Text.literal(text), w, h+4, 0xFFFF0000, true);
        context.getMatrices().pop();
    }
}